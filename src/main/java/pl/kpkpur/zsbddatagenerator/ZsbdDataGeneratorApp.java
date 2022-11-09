package pl.kpkpur.zsbddatagenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.fastnate.generator.EntitySqlGenerator;
import org.fastnate.generator.context.GeneratorContext;
import org.fastnate.generator.dialect.OracleDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.kpkpur.zsbddatagenerator.generator.*;
import pl.kpkpur.zsbddatagenerator.model.*;

@SpringBootApplication
public class ZsbdDataGeneratorApp implements CommandLineRunner {
  private static final String GENERATION_DIRECTORY_PATH = "./generation_output";
  private static final String FILE_PATH = GENERATION_DIRECTORY_PATH + "/insert_generated_data.sql";
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
  ScreeningEmployeeGenerator screeningEmployeeGenerator;

  @Autowired
  TicketGenerator ticketGenerator;
  @Autowired
  ReviewGenerator reviewGenerator;
  public static void main(String[] args) {
    SpringApplication.run(ZsbdDataGeneratorApp.class, args);
  }

  @Override
  public void run(String... args) throws IOException {
    Files.createDirectories(Path.of(GENERATION_DIRECTORY_PATH));
    Files.deleteIfExists(Path.of(FILE_PATH));
    BufferedWriter fileWriter = new BufferedWriter(new FileWriter(FILE_PATH, true));
    GeneratorContext generatorContext = new GeneratorContext(new OracleDialect());
    try (EntitySqlGenerator sqlGenerator = new EntitySqlGenerator(generatorContext, fileWriter)) {
      fileWriter.write("SET DEFINE OFF;\n");
      fileWriter.write("ALTER SESSION SET cursor_sharing = force;\n");

      List<Employee> employees = employeeGenerator.generateMultiple(10000);
      System.out.println("Generated employees: " + employees.size());
      sqlGenerator.write(employees);

      List<Room> rooms = roomGenerator.generateMultiple(200);
      System.out.println("Generated rooms: " + rooms.size());
      sqlGenerator.write(rooms);

      List<Movie> movies = movieGenerator.generateMultiple(10000);
      System.out.println("Generated movies: " + movies.size());
      sqlGenerator.write(movies);

      List<MovieVersion> movieVersions = movieVersionGenerator.generateMultiple(movies);
      System.out.println("Generated movie versions: " + movieVersions.size());
      sqlGenerator.write(movieVersions);

      List<Screening> screenings = screeningGenerator.generateMultiple(rooms, movieVersions);
      System.out.println("Generated screenings: " + screenings.size());
      sqlGenerator.write(screenings);

      List<ScreeningEmployee> screeningEmployees = screeningEmployeeGenerator.generateMultiple(screenings, employees);
      System.out.println("Generated screening employees: " + screeningEmployees.size());
      sqlGenerator.write(screeningEmployees);

      List<Customer> customers = customerGenerator.generateMultiple(100000);
      System.out.println("Generated customers: " + customers.size());
      sqlGenerator.write(customers);

      List<Ticket> tickets = ticketGenerator.generateMultiple(customers, screenings, employees);
      System.out.println("Generated tickets: " + tickets.size());
      sqlGenerator.write(tickets);

      List<Review> reviews = reviewGenerator.generateMultiple(movies, customers);
      System.out.println("Generated reviews: " + reviews.size());
      sqlGenerator.write(reviews);

      fileWriter.write("ALTER SESSION SET cursor_sharing = exact;\n");
      fileWriter.write("\nSET DEFINE ON;");
      fileWriter.write("\nCOMMIT;");
    }
  }
}
