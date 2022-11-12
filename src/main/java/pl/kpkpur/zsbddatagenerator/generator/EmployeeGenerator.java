package pl.kpkpur.zsbddatagenerator.generator;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;
import pl.kpkpur.zsbddatagenerator.model.Employee;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static pl.kpkpur.zsbddatagenerator.config.GenerationConfig.*;

@Component
public class EmployeeGenerator extends FakerGenerator<Employee> {
    public EmployeeGenerator(Faker faker) {
        super(faker);
    }
    List<Employee> generatedEmployees = new ArrayList<>();
    Integer currentSupervisorIndex = null;
    Integer currentSupervisorSubordinatesAmount = 0;

    public Employee generate() {
        var firstName = faker.name().firstName();
        var lastName = faker.name().lastName();

        Long id = getNextId();
        Long supervisorId = null;

        if (generatedEmployees.isEmpty()) {
            currentSupervisorIndex = 0;
            currentSupervisorSubordinatesAmount = 0;
        } else {
            if (currentSupervisorSubordinatesAmount >= MAX_EMPLOYEE_SUBORDINATES) {
                currentSupervisorIndex += 1;
                currentSupervisorSubordinatesAmount = 0;
            }
            supervisorId = generatedEmployees.get(currentSupervisorIndex).getId();
            currentSupervisorSubordinatesAmount += 1;
        }

        var salary = 800.0 + (8000 - 800)
                * ((double)(EMPLOYEE_COUNT - id) / EMPLOYEE_COUNT);

        var employee = new Employee(
                id,
                firstName,
                lastName,
                faker.internet().emailAddress(
                        firstName.toLowerCase() + "."
                                + lastName.toLowerCase() + faker.number().digits(4)),
                faker.internet().password(),
                salary,
                generateEmploymentDate(),
                supervisorId
        );
        generatedEmployees.add(employee);
        return employee;
    }

    private Date generateEmploymentDate() {
        return Date.valueOf(
                LocalDate.ofInstant(
                        faker
                                .date()
                                .between(Date.valueOf(EMPLOYEE_EMPLOYMENT_DATE_START), Date.valueOf(EMPLOYEE_EMPLOYMENT_DATE_END))
                                .toInstant(),
                        ZoneId.systemDefault()));
    }
}
