package pl.kpkpur.zsbddatagenerator.generator;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;
import pl.kpkpur.zsbddatagenerator.model.*;
import pl.kpkpur.zsbddatagenerator.model.enums.ScreeningEmployeeResponsibility;
import pl.kpkpur.zsbddatagenerator.util.Enums;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

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
        List<Employee> unusedEmployees = new LinkedList<>(employees);

        return IntStream.of(1, 2, 3)
                .boxed()
                .map(num -> generate(screening, unusedEmployees.remove(faker.random().nextInt(unusedEmployees.size() - 1))))
                .toList();
    }

    private ScreeningEmployee generate(Screening screening, Employee employee) {
        return new ScreeningEmployee(
                new ScreeningEmployeeId(employee.getId(), screening.getId()),
                Enums.getRandomValue(ScreeningEmployeeResponsibility.class)
        );
    }
}
