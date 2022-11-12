package pl.kpkpur.zsbddatagenerator.generator;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;
import pl.kpkpur.zsbddatagenerator.model.Employee;
import pl.kpkpur.zsbddatagenerator.model.Screening;
import pl.kpkpur.zsbddatagenerator.model.ScreeningEmployee;
import pl.kpkpur.zsbddatagenerator.model.ScreeningEmployeeId;
import pl.kpkpur.zsbddatagenerator.model.enums.ScreeningEmployeeResponsibility;

import java.util.*;

import static pl.kpkpur.zsbddatagenerator.config.GenerationConfig.SCREENING_RESPONSIBILITIES_LIST;

@Component
public class ScreeningEmployeeGenerator extends FakerGenerator<ScreeningEmployee> {

    public ScreeningEmployeeGenerator(Faker faker) {
        super(faker);
    }

    @Override
    public ScreeningEmployee generate() {
        throw new IllegalCallerException("Cannot generate ScreeningEmployee without passing lists of Screenings and Employees!");
    }

    @Override
    public List<ScreeningEmployee> generateMultiple(int count) {
        throw new IllegalCallerException("Cannot generate ScreeningEmployee without passing lists of Screenings and Employees!");
    }

    public List<ScreeningEmployee> generateMultiple(List<Screening> screenings, List<Employee> employees) {
        return screenings.stream()
                .flatMap(screening -> generateEmployeesForScreening(screening, employees).stream())
                .toList();
    }

    private List<ScreeningEmployee> generateEmployeesForScreening(Screening screening, List<Employee> employees) {
        List<Employee> unusedEmployees = new ArrayList<>(employees);
        List<Employee> candidateEmployees = new ArrayList<>();

        Queue<ScreeningEmployeeResponsibility> responsibilities = new LinkedList<>(Arrays.asList(SCREENING_RESPONSIBILITIES_LIST));

        while (candidateEmployees.size() != responsibilities.size()) {
            candidateEmployees.add(unusedEmployees.remove(faker.random().nextInt(unusedEmployees.size() - 1)));
        }
        return candidateEmployees.stream()
                .map(emp -> generate(screening, emp, responsibilities.remove()))
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
