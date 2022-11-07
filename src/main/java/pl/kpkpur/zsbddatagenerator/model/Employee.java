package pl.kpkpur.zsbddatagenerator.model;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "EMPLOYEE")
@NoArgsConstructor
public class Employee {
  public Employee(
      Long employeeId,
      Long addressId,
      String firstName,
      String lastName,
      Date dateOfBirth,
      String gender,
      String phoneNumber) {
    this.employeeId = employeeId;
    this.addressId = addressId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
    this.gender = gender;
    this.phoneNumber = phoneNumber;
  }

  @Id
  @Column(name = "EMPLOYEE_ID")
  private Long employeeId;

  @Column(name = "ADDRESS_ID")
  private Long addressId;

  @Column(name = "FIRST_NAME")
  private String firstName;

  @Column(name = "LAST_NAME")
  private String lastName;

  @Column(name = "DATE_OF_BIRTH")
  private java.sql.Date dateOfBirth;

  @Column(name = "GENDER")
  private String gender;

  @Column(name = "PHONE_NUMBER")
  private String phoneNumber;
}
