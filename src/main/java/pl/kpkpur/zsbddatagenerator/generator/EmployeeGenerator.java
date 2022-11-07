package pl.kpkpur.zsbddatagenerator.generator;

import com.github.javafaker.Faker;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import org.springframework.stereotype.Component;
import pl.kpkpur.zsbddatagenerator.model.Address;
import pl.kpkpur.zsbddatagenerator.model.Employee;

@Component
public class EmployeeGenerator extends FakerGenerator<Employee> {

  public EmployeeGenerator(Faker faker) {
    super(faker);
  }

  public Employee generate(Address address) {
    return new Employee(
        getNextId(),
        address.getAddressId(),
        faker.name().firstName(),
        faker.name().lastName(),
        generateDateOfBirth(),
        faker.demographic().sex(),
        faker.phoneNumber().cellPhone());
  }

  private Date generateDateOfBirth() {
    return Date.valueOf(
        LocalDate.ofInstant(
            faker
                .date()
                .between(Date.valueOf("1960-01-01"), Date.valueOf("2001-12-31"))
                .toInstant(),
            ZoneId.systemDefault()));
  }

  public List<Employee> generateMultiple(List<Address> addresses) {
    return addresses.stream().map(this::generate).toList();
  }

  @Override
  public Employee generate() {
    return new Employee();
  }
}
