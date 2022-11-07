package pl.kpkpur.zsbddatagenerator.generator;

import com.github.javafaker.Faker;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;
import pl.kpkpur.zsbddatagenerator.model.Employee;
import pl.kpkpur.zsbddatagenerator.model.Employment;
import pl.kpkpur.zsbddatagenerator.model.EmploymentId;
import pl.kpkpur.zsbddatagenerator.model.Restaurant;

@Component
public class EmploymentGenerator extends FakerGenerator<Employment> {
  public EmploymentGenerator(Faker faker) {
    super(faker);
  }

  public Employment generate(Restaurant restaurant, Employee employee) {
    Date startDate = generateStartDate(restaurant, employee);
    Date endDate = generateEndDate(startDate);

    return new Employment(
        new EmploymentId(restaurant.getRestaurantId(), employee.getEmployeeId()),
        faker.number().randomDouble(2, 2000, 6000),
        startDate,
        endDate,
        faker.number().numberBetween(20, 40),
        faker.job().title());
  }

  private Date generateStartDate(Restaurant restaurant, Employee employee) {
    LocalDate employeeAdulthoodDate = employee.getDateOfBirth().toLocalDate().plusYears(18);
    Date minStartDate = restaurant.getDateOfOpening();

    if (employeeAdulthoodDate.isAfter(minStartDate.toLocalDate())) {
      minStartDate = new Date(employeeAdulthoodDate.toEpochDay());
    }

    return Date.valueOf(
        LocalDate.ofInstant(
            faker
                .date()
                .between(minStartDate, Date.valueOf(LocalDate.now().minusMonths(1)))
                .toInstant(),
            ZoneId.systemDefault()));
  }

  private Date generateEndDate(Date startDate) {
    boolean stillEmployed = faker.number().numberBetween(0, 3) == 0;
    Date endDate = null;

    if (!stillEmployed) {
      endDate =
          Date.valueOf(
              LocalDate.ofInstant(
                  faker.date().between(startDate, Date.valueOf(LocalDate.now())).toInstant(),
                  ZoneId.systemDefault()));
    }

    return endDate;
  }

  public List<Employment> generateMultiple(List<Restaurant> restaurants, List<Employee> employees) {
    return employees.stream()
        .flatMap(
            employee ->
                Stream.generate(
                        () ->
                            generate(
                                restaurants.get(
                                    faker.number().numberBetween(0, restaurants.size())),
                                employee))
                    .limit(faker.number().numberBetween(1, 4)))
        .toList();
  }

  @Override
  public Employment generate() {
    return new Employment();
  }
}
