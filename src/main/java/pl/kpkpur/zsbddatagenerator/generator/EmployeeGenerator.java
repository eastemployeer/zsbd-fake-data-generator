package pl.kpkpur.zsbddatagenerator.generator;

import com.github.javafaker.Faker;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.stereotype.Component;
import pl.kpkpur.zsbddatagenerator.model.Employee;
import pl.kpkpur.zsbddatagenerator.model.enums.EmployeeRole;

@Component
public class EmployeeGenerator extends FakerGenerator<Employee> {

  public EmployeeGenerator(Faker faker) {
    super(faker);
  }

  public Employee generate() {
    var firstName = faker.name().firstName();
    var lastName = faker.name().lastName();
    return new Employee(
            getNextId(),
            firstName,
            lastName,
            faker.internet().emailAddress(
                    firstName.toLowerCase() + "."
                            + lastName.toLowerCase() + faker.number().digits(4)),
            generatePassword(),
            (double) faker.number().numberBetween(800, 8000),
            generateDateOfEmployment()
    );
  }

  private String generatePassword() {
    return faker.artist().name().replace(" ", "") + ((int) (Math.random() * 10));
  }

  private Date generateDateOfEmployment() {
    return Date.valueOf(
        LocalDate.ofInstant(
            faker
                .date()
                .between(Date.valueOf("2010-01-01"), Date.valueOf("2022-10-31"))
                .toInstant(),
            ZoneId.systemDefault()));
  }

}
