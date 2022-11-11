package pl.kpkpur.zsbddatagenerator.generator;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;
import pl.kpkpur.zsbddatagenerator.model.Employee;
import pl.kpkpur.zsbddatagenerator.model.Screening;
import pl.kpkpur.zsbddatagenerator.model.ScreeningEmployee;
import pl.kpkpur.zsbddatagenerator.model.ScreeningEmployeeId;
import pl.kpkpur.zsbddatagenerator.model.enums.ScreeningEmployeeResponsibility;

import java.util.*;

import static pl.kpkpur.zsbddatagenerator.model.enums.ScreeningEmployeeResponsibility.*;
import static pl.kpkpur.zsbddatagenerator.model.enums.ScreeningEmployeeResponsibility.CLEANER;

@Component
public class ScreeningEmployeeGenerator extends FakerGenerator<ScreeningEmployee> {

    public ScreeningEmployeeGenerator(Faker faker) {
        super(faker);
    }

    @Override
    public ScreeningEmployee generate() {
        throw new IllegalCallerException("Nope");
    }

    @Override
    public List<ScreeningEmployee> generateMultiple(int count) {
        throw new IllegalCallerException("Nope");
    }

    public List<ScreeningEmployee> generateMultiple(List<Screening> screenings, List<Employee> employees) {
        return screenings.stream()
                .flatMap(screening -> generateEmployeesForScreening(screening, employees).stream())
                .toList();
    }

    private List<ScreeningEmployee> generateEmployeesForScreening(Screening screening, List<Employee> employees) {
        Set<Employee> candidateEmployees = new HashSet<>();
        Queue<ScreeningEmployeeResponsibility> responsibilities = new LinkedList<>(
                List.of(CLEANER, CLEANER, ROOM_CONTROLLER)
        );
        while (candidateEmployees.size() != 3) {
            candidateEmployees.add(employees.get(faker.random().nextInt(employees.size() - 1)));
        }
        return candidateEmployees.stream()
                .map(emp -> generate(screening, emp, responsibilities.poll()))
                .toList();
    }

    private ScreeningEmployee generate(Screening screening, Employee employee,
                                       ScreeningEmployeeResponsibility responsibility) {
        return new ScreeningEmployee(
                new ScreeningEmployeeId(employee.getId(), screening.getId()),
                responsibility
        );
    }
}
