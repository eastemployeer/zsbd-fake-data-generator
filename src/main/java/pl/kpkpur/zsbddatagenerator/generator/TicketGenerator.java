package pl.kpkpur.zsbddatagenerator.generator;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;
import pl.kpkpur.zsbddatagenerator.model.Customer;
import pl.kpkpur.zsbddatagenerator.model.Employee;
import pl.kpkpur.zsbddatagenerator.model.Screening;
import pl.kpkpur.zsbddatagenerator.model.Ticket;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Stream;

import static pl.kpkpur.zsbddatagenerator.config.GenerationConfig.*;

@Component
public class TicketGenerator extends FakerGenerator<Ticket> {

    Map<Long, Set<String>> screeningToTakenSeat = new HashMap<>();

    public TicketGenerator(Faker faker) {
        super(faker);
    }

    @Override
    public Ticket generate() {
        throw new IllegalCallerException("Cannot generate Tickets without passing lists of Customers, Screenings and Employees!");
    }

    @Override
    public List<Ticket> generateMultiple(int count) {
        throw new IllegalCallerException("Cannot generate Tickets without passing lists of Customers, Screenings and Employees!");
    }

    public List<Ticket> generateMultiple(List<Customer> customers, List<Screening> screenings, List<Employee> employees) {
        return screenings
                .stream()
                .flatMap(screening -> generateTicketsForScreening(screening, customers, employees).stream())
                .toList();
    }

    private List<Ticket> generateTicketsForScreening(Screening screening, List<Customer> customers, List<Employee> employees) {
        return Stream.generate(() -> generate(screening,
                        customers.get(faker.random().nextInt(customers.size() - 1)),
                        employees.get(faker.random().nextInt(employees.size() - 1))))
                .limit(faker.random().nextInt(MIN_TICKETS_PER_SCREENING, MAX_TICKETS_PER_SCREENING))
                .toList();
    }

    private Ticket generate(Screening screening, Customer customer, Employee employee) {
        char row = (char) (faker.random().nextInt(screening.getRoom().getRows()) + 'a');
        int seat = faker.random().nextInt(screening.getRoom().getSeatsInRow());

        //we don't want to take the same seat again for the same screening
        screeningToTakenSeat.putIfAbsent(screening.getId(), new HashSet<>());
        var takenSeats = screeningToTakenSeat.get(screening.getId());
        if (takenSeats.contains("" + row + seat)) {
            return generate(screening, customer, employee);
        } else {
            takenSeats.add("" + row + seat);
        }

        var date = faker.date().between(
                Date.from(screening.getDatetime().toLocalDateTime().minusMonths(1).toInstant(ZoneOffset.UTC)),
                screening.getDatetime()
        ).toInstant();


        return new Ticket(
                getNextId(),
                screening.getId(),
                customer.getId(),
                employee.getId(),
                Character.toString(row),
                Integer.toString(seat),
                0.0,
                Timestamp.from(date)
        );
    }

    //TODO: Finish discount generation depending on the customer age
    private Double generateDiscount(Screening screening, Customer customer) {

        //if screening is premiere we don't allow discounts
        if (screening.getIsPremiere() == 1) {
            return 0.0;
        }

        LocalDate customerBirthLocalDate = LocalDate.ofInstant(customer.getBirthDate().toInstant(), ZoneId.systemDefault());

        int customerAgeInYears = Period.between(customerBirthLocalDate,
                LocalDate.ofInstant(java.sql.Date.valueOf(NOW_DATE).toInstant(), ZoneId.systemDefault())).getYears();

        if (customerAgeInYears < 18) {
            return 0.0;
        }

        return 0.0;
    }
}
