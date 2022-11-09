package pl.kpkpur.zsbddatagenerator.generator;

import com.github.javafaker.Faker;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import org.springframework.stereotype.Component;
import pl.kpkpur.zsbddatagenerator.model.Employee;
import pl.kpkpur.zsbddatagenerator.model.enums.EmployeeRole;

@Component
public class EmployeeGenerator extends FakerGenerator<Employee> {

  public EmployeeGenerator(Faker faker) {
    super(faker);
  }

  private static class HierarchyNode {

      private static int MAX_SUBORDINATES = 3;

      @Getter
      private Long employeeId;

      private List<HierarchyNode> subordinates = new LinkedList<>();

      public HierarchyNode(Long employeeId) {
          this.employeeId = employeeId;
      }

      public Integer size() {
          return 1 + subordinates.stream().mapToInt(HierarchyNode::size).sum();
      }

      public Long addNodeIdAndGetSupervisorId(Long employeeToAddId) {
          HierarchyNode node = new HierarchyNode(employeeToAddId);
          HierarchyNode supervisorNode = this;
          while(subordinates.size() == MAX_SUBORDINATES) {
              var optSup = supervisorNode.subordinates
                      .stream()
                      .filter(sn -> sn.subordinates.size() < MAX_SUBORDINATES)
                      .sorted(Comparator.comparingInt(s -> s.subordinates.size()))
                      .findAny();
              if (optSup.isPresent()) {
                  supervisorNode = optSup.get();
              }
              else {
                  supervisorNode = supervisorNode.subordinates.stream().findAny().get();
              }
          }
          supervisorNode.subordinates.add(node);
          return supervisorNode.employeeId;
      }

  }

  public HierarchyNode root;

  public Employee generate() {
    var firstName = faker.name().firstName();
    var lastName = faker.name().lastName();
    Long id = getNextId();
    Long supervisorId;
    if(root == null) {
        root = new HierarchyNode(id);
        supervisorId = null;
    } else {
        supervisorId = root.addNodeIdAndGetSupervisorId(id);
    }

    var employee = new Employee(
            id,
            firstName,
            lastName,
            faker.internet().emailAddress(
                    firstName.toLowerCase() + "."
                            + lastName.toLowerCase() + faker.number().digits(4)),
            generatePassword(),
            EmployeeRole.TICKETER,
            (double) faker.number().numberBetween(800, 8000),
            generateDateOfEmployment(),
            supervisorId
    );
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
