package pl.kpkpur.zsbddatagenerator.model;

import java.sql.Date;
import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kpkpur.zsbddatagenerator.model.enums.EmployeeRole;

@Data
@Entity
@Table(name = "EMPLOYEE")
@NoArgsConstructor
public class Employee {
  public Employee(
      Long id,
      String name,
      String surname,
      String email,
      String password,
      EmployeeRole role,
      Double salary,
      Date dateOfEmployment,
      Long supervisorId) {
    this.id = id;
    this.name = name;
    this.surname = surname;
    this.email = email;
    this.password = password;
    this.role = role;
    this.salary = salary;
    this.dateOfEmployment = dateOfEmployment;
    this.supervisorId = supervisorId;
  }

  @Id
  @Column(name = "ID")
  private Long id;

  @Column(name = "NAME")
  private String name;

  @Column(name = "SURNAME")
  private String surname;

  @Column(name = "EMAIL")
  private String email;

  @Column(name = "PASSWORD")
  private String password;
  @Enumerated(EnumType.STRING)
  @Column(name = "ROLE")
  private EmployeeRole role;

  @Column(name = "SALARY")
  private Double salary;

  @Column(name = "DATE_OF_EMPLOYMENT")
  private java.sql.Date dateOfEmployment;

  @Column(name = "SUPERVISOR_ID")
  private Long supervisorId;
}
