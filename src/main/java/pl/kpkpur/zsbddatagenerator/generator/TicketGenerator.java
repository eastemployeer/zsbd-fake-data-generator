package pl.kpkpur.zsbddatagenerator.generator;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;
import pl.kpkpur.zsbddatagenerator.model.Customer;
import pl.kpkpur.zsbddatagenerator.model.Employee;
import pl.kpkpur.zsbddatagenerator.model.Screening;
import pl.kpkpur.zsbddatagenerator.model.Ticket;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Stream;

@Component
public class TicketGenerator extends FakerGenerator<Ticket> {

    Map<Long, Set<String>> screeningToTakenSeat = new HashMap<>();

    public TicketGenerator(Faker faker) {
        super(faker);
    }

    @Override
    public Ticket generate() {
        throw new IllegalCallerException("Nope");
    }

    @Override
    public List<Ticket> generateMultiple(int count) {
        throw new IllegalCallerException("Nope");
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
                .limit(faker.random().nextInt(10, 100))
                .toList();
    }

    private Ticket generate(Screening screening, Customer customer, Employee employee) {
        Character row = (char) (faker.random().nextInt(10) + 'a');
        Integer seat = faker.random().nextInt(10);


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
                row.toString(),
                seat.toString(),
                0.0,
                Timestamp.from(date)
        );
    }
}
