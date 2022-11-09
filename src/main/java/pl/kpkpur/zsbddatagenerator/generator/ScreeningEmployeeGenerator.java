package pl.kpkpur.zsbddatagenerator.generator;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;
import pl.kpkpur.zsbddatagenerator.model.Employee;
import pl.kpkpur.zsbddatagenerator.model.Screening;
import pl.kpkpur.zsbddatagenerator.model.ScreeningEmployee;
import pl.kpkpur.zsbddatagenerator.model.ScreeningEmployeeId;
import pl.kpkpur.zsbddatagenerator.model.enums.ScreeningEmployeeResponsibility;
import pl.kpkpur.zsbddatagenerator.util.Enums;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
        Set<ScreeningEmployee> screeningEmployees = new HashSet<>();
        while(screeningEmployees.size() != 3) {
            screeningEmployees.add(
                    generate(screening, employees.get(faker.random().nextInt(employees.size() - 1)))
            );
        }
        return screeningEmployees.stream().toList();
    }

    private ScreeningEmployee generate(Screening screening, Employee employee) {
        return new ScreeningEmployee(
                new ScreeningEmployeeId(employee.getId(), screening.getId()),
                Enums.getRandomValue(ScreeningEmployeeResponsibility.class)
        );
    }
}
