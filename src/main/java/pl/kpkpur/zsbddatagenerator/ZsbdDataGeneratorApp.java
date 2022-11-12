package pl.kpkpur.zsbddatagenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.kpkpur.zsbddatagenerator.generator.*;
import pl.kpkpur.zsbddatagenerator.model.*;

import static pl.kpkpur.zsbddatagenerator.config.GenerationConfig.*;


@SpringBootApplication
public class ZsbdDataGeneratorApp implements CommandLineRunner {
    private static final String GENERATION_DIRECTORY_PATH = "./generation_output";

    @Autowired
    EmployeeGenerator employeeGenerator;

    @Autowired
    RoomGenerator roomGenerator;

    @Autowired
    MovieGenerator movieGenerator;

    @Autowired
    MovieVersionGenerator movieVersionGenerator;

    @Autowired
    CustomerGenerator customerGenerator;

    @Autowired
    ScreeningGenerator screeningGenerator;

    @Autowired
    ReviewGenerator reviewGenerator;

    @Autowired
    ScreeningEmployeeGenerator screeningEmployeeGenerator;

    @Autowired
    TicketGenerator ticketGenerator;

    public static void main(String[] args) {
        SpringApplication.run(ZsbdDataGeneratorApp.class, args);
    }

    @Override
    public void run(String... args) throws IOException {
        Files.createDirectories(Path.of(GENERATION_DIRECTORY_PATH));

        // CUSTOMER -------------------------------------------------------------------
        // File creation
        String CUSTOMER_FILE_PATH = GENERATION_DIRECTORY_PATH + "/customers.csv";
        Files.deleteIfExists(Path.of(CUSTOMER_FILE_PATH));
        BufferedWriter customerFileWriter = new BufferedWriter(new FileWriter(CUSTOMER_FILE_PATH, true));

        // Data generation
        List<Customer> customers = customerGenerator.generateMultiple(CUSTOMER_COUNT);
        System.out.println("Generated customers: " + customers.size());

        // Write CSV
        try (CSVPrinter customerCsvPrinter = new CSVPrinter(customerFileWriter, CSVFormat.DEFAULT)) {
            customerCsvPrinter.printRecord("id", "name", "surname", "email", "password", "gender", "birth_date");
            for (Customer customer : customers) {
                customerCsvPrinter.printRecord(
                        customer.getId(),
                        customer.getName(),
                        customer.getSurname(),
                        customer.getEmail(),
                        customer.getPassword(),
                        customer.getGender(),
                        customer.getBirthDate()
                );
            }
        }

        // EMPLOYEE -------------------------------------------------------
        // File creation
        String EMPLOYEE_FILE_PATH = GENERATION_DIRECTORY_PATH + "/employees.csv";
        Files.deleteIfExists(Path.of(EMPLOYEE_FILE_PATH));
        BufferedWriter employeeFileWriter = new BufferedWriter(new FileWriter(EMPLOYEE_FILE_PATH, true));

        // Data generation
        List<Employee> employees = employeeGenerator.generateMultiple(EMPLOYEE_COUNT);
        System.out.println("Generated employees: " + employees.size());

        // Write CSV
        try (CSVPrinter employeeCsvPrinter = new CSVPrinter(employeeFileWriter, CSVFormat.DEFAULT)) {
            employeeCsvPrinter.printRecord("id", "name", "surname", "email", "password", "salary", "date_of_employment", "supervisor_id");
            for (Employee employee : employees) {
                employeeCsvPrinter.printRecord(
                        employee.getId(),
                        employee.getName(),
                        employee.getSurname(),
                        employee.getEmail(),
                        employee.getPassword(),
                        String.format("%.2f", employee.getSalary()),
                        employee.getDateOfEmployment(),
                        employee.getSupervisorId());
            }
        }

        // MOVIE -------------------------------------------------------------------
        // File creation
        String MOVIE_FILE_PATH = GENERATION_DIRECTORY_PATH + "/movies.csv";
        Files.deleteIfExists(Path.of(MOVIE_FILE_PATH));
        BufferedWriter movieFileWriter = new BufferedWriter(new FileWriter(MOVIE_FILE_PATH, true));

        // Data generation
        List<Movie> movies = movieGenerator.generateMultiple(MOVIE_COUNT);
        System.out.println("Generated movies: " + movies.size());

        // Write CSV
        try (CSVPrinter movieCsvPrinter = new CSVPrinter(movieFileWriter, CSVFormat.DEFAULT)) {
            movieCsvPrinter.printRecord("id", "title", "director", "length", "premiere_date", "genre");
            for (Movie movie : movies) {
                movieCsvPrinter.printRecord(
                        movie.getId(),
                        movie.getTitle(),
                        movie.getDirector(),
                        movie.getLength(),
                        movie.getPremiereDate(),
                        movie.getGenre()
                );
            }
        }

        // MOVIE VERSION -------------------------------------------------------------------
        // File creation
        String MOVIE_VERSION_FILE_PATH = GENERATION_DIRECTORY_PATH + "/movie_versions.csv";
        Files.deleteIfExists(Path.of(MOVIE_VERSION_FILE_PATH));
        BufferedWriter movieVersionFileWriter = new BufferedWriter(new FileWriter(MOVIE_VERSION_FILE_PATH, true));

        // Data generation
        List<MovieVersion> movieVersions = movieVersionGenerator.generateMultiple(movies);
        System.out.println("Generated movie versions: " + movieVersions.size());

        // Write CSV
        try (CSVPrinter movieVersionCsvPrinter = new CSVPrinter(movieVersionFileWriter, CSVFormat.DEFAULT)) {
            movieVersionCsvPrinter.printRecord("id", "movie_id", "language", "has_subtitles", "is_3d", "is_atmos");
            for (MovieVersion movieVersion : movieVersions) {
                movieVersionCsvPrinter.printRecord(
                        movieVersion.getId(),
                        movieVersion.getMovie().getId(),
                        movieVersion.getLanguage(),
                        movieVersion.getHasSubtitles(),
                        movieVersion.getIs3d(),
                        movieVersion.getIsAtmos()
                );
            }
        }

        // REVIEW -------------------------------------------------------------------
        // File creation
        String REVIEW_FILE_PATH = GENERATION_DIRECTORY_PATH + "/reviews.csv";
        Files.deleteIfExists(Path.of(REVIEW_FILE_PATH));
        BufferedWriter reviewFileWriter = new BufferedWriter(new FileWriter(REVIEW_FILE_PATH, true));

        // Data generation
        List<Review> reviews = reviewGenerator.generateMultiple(movies, customers);
        System.out.println("Generated reviews: " + reviews.size());

        // Write CSV
        try (CSVPrinter reviewCsvPrinter = new CSVPrinter(reviewFileWriter, CSVFormat.DEFAULT)) {
            reviewCsvPrinter.printRecord("customer_id", "movie_id", "description", "rating", "date", "likes");
            for (Review review : reviews) {
                reviewCsvPrinter.printRecord(
                        review.getId().getCustomerId(),
                        review.getId().getMovieId(),
                        review.getDescription(),
                        review.getRating(),
                        review.getDate(),
                        review.getLikes()
                );
            }
        }

        // ROOM -------------------------------------------------------------------
        // File creation
        String ROOM_FILE_PATH = GENERATION_DIRECTORY_PATH + "/rooms.csv";
        Files.deleteIfExists(Path.of(ROOM_FILE_PATH));
        BufferedWriter roomFileWriter = new BufferedWriter(new FileWriter(ROOM_FILE_PATH, true));

        // Data generation
        List<Room> rooms = roomGenerator.generateMultiple(ROOM_COUNT);
        System.out.println("Generated rooms: " + rooms.size());

        // Write CSV
        try (CSVPrinter roomCsvPrinter = new CSVPrinter(roomFileWriter, CSVFormat.DEFAULT)) {
            roomCsvPrinter.printRecord("name", "seats_in_row", "rows", "sponsor", "is_3d", "is_vip");
            for (Room room : rooms) {
                roomCsvPrinter.printRecord(
                        room.getName(),
                        room.getSeatsInRow(),
                        room.getRows(),
                        room.getSponsor(),
                        room.getIs3d(),
                        room.getIsVip()
                );
            }
        }

        // SCREENING -------------------------------------------------------------------
        // File creation
        String SCREENING_FILE_PATH = GENERATION_DIRECTORY_PATH + "/screenings.csv";
        Files.deleteIfExists(Path.of(SCREENING_FILE_PATH));
        BufferedWriter screeningFileWriter = new BufferedWriter(new FileWriter(SCREENING_FILE_PATH, true));

        // Data generation
        List<Screening> screenings = screeningGenerator.generateMultiple(rooms, movieVersions);
        System.out.println("Generated screenings: " + screenings.size());

        // Write CSV
        try (CSVPrinter screeningCsvPrinter = new CSVPrinter(screeningFileWriter, CSVFormat.DEFAULT)) {
            screeningCsvPrinter.printRecord("id", "room_name", "movie_version_id", "datetime", "is_premiere", "is_discountable", "ads_length", "price");
            for (Screening screening : screenings) {
                screeningCsvPrinter.printRecord(
                        screening.getId(),
                        screening.getRoom().getName(),
                        screening.getMovieVersionId(),
                        screening.getDatetime(),
                        screening.getIsPremiere(),
                        screening.getIsDiscountable(),
                        screening.getAdsLength(),
                        String.format("%.2f", screening.getPrice())
                );
            }
        }

        // SCREENING EMPLOYEE -------------------------------------------------------------------
        // File creation
        String SCREENING_EMPLOYEE_FILE_PATH = GENERATION_DIRECTORY_PATH + "/screening_employees.csv";
        Files.deleteIfExists(Path.of(SCREENING_EMPLOYEE_FILE_PATH));
        BufferedWriter screeningEmployeeFileWriter = new BufferedWriter(new FileWriter(SCREENING_EMPLOYEE_FILE_PATH, true));

        // Data generation
        List<ScreeningEmployee> screeningEmployees = screeningEmployeeGenerator.generateMultiple(screenings, employees);
        System.out.println("Generated screening employees: " + screeningEmployees.size());

        // Write CSV
        try (CSVPrinter screeningEmployeeCsvPrinter = new CSVPrinter(screeningEmployeeFileWriter, CSVFormat.DEFAULT)) {
            screeningEmployeeCsvPrinter.printRecord("screening_id", "employee_id", "responsibility");
            for (ScreeningEmployee screeningEmployee : screeningEmployees) {
                screeningEmployeeCsvPrinter.printRecord(
                        screeningEmployee.getScreeningEmployeeId().getScreeningId(),
                        screeningEmployee.getScreeningEmployeeId().getEmployeeId(),
                        screeningEmployee.getResponsibility()
                );
            }
        }

        // TICKET -------------------------------------------------------------------
        // File creation
        String TICKET_FILE_PATH = GENERATION_DIRECTORY_PATH + "/tickets.csv";
        Files.deleteIfExists(Path.of(TICKET_FILE_PATH));
        BufferedWriter ticketFileWriter = new BufferedWriter(new FileWriter(TICKET_FILE_PATH, true));

        // Data generation
        List<Ticket> tickets = ticketGenerator.generateMultiple(customers, screenings, employees);
        System.out.println("Generated tickets: " + tickets.size());

        // Write CSV
        try (CSVPrinter ticketCsvPrinter = new CSVPrinter(ticketFileWriter, CSVFormat.DEFAULT)) {
            ticketCsvPrinter.printRecord("id" ,"screening_id", "customer_id", "employee_id", "row", "seat", "discount", "purchase_datetime");
            for (Ticket ticket : tickets) {
                ticketCsvPrinter.printRecord(
                        ticket.getId(),
                        ticket.getScreeningId(),
                        ticket.getCustomerId(),
                        ticket.getEmployeeId(),
                        ticket.getRow(),
                        ticket.getSeat(),
                        String.format("%.2f", ticket.getDiscount()),
                        ticket.getPurchaseDatetime()
                );
            }
        }
    }
}