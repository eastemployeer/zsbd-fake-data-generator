package pl.kpkpur.zsbddatagenerator.generator;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;
import pl.kpkpur.zsbddatagenerator.config.GenerationConfig;
import pl.kpkpur.zsbddatagenerator.model.Employee;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeGenerator extends FakerGenerator<Employee> {

    public EmployeeGenerator(Faker faker) {
        super(faker);
    }

    List<Employee> employees = new ArrayList<>();

    Integer currentSupervisorIndex = null;
    Integer currentSupervisorSubordinates = 0;

    public Employee generate() {
        var firstName = faker.name().firstName();
        var lastName = faker.name().lastName();
        Long id = getNextId();
        Long supervisorId = null;
        if (employees.isEmpty()) {
            currentSupervisorIndex = 0;
            currentSupervisorSubordinates = 0;
        } else {
            if (currentSupervisorSubordinates >= GenerationConfig.MAX_NUMBER_OF_SUBORDINATES) {
                currentSupervisorIndex += 1;
                currentSupervisorSubordinates = 0;
            }
            supervisorId = employees.get(currentSupervisorIndex).getId();
            currentSupervisorSubordinates += 1;
        }
        var salary = 800.0 + (8000.0 - 800)
                * ((double)(GenerationConfig.NUMBER_OF_EMPLOYEES - id) / GenerationConfig.NUMBER_OF_EMPLOYEES);

        var employee = new Employee(
                id,
                firstName,
                lastName,
                faker.internet().emailAddress(
                        firstName.toLowerCase() + "."
                                + lastName.toLowerCase() + faker.number().digits(4)),
                generatePassword(),
                salary,
                generateDateOfEmployment(),
                supervisorId
        );
        employees.add(employee);
        return employee;
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
