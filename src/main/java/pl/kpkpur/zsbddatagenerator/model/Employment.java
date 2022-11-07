package pl.kpkpur.zsbddatagenerator.model;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "EMPLOYMENT")
@NoArgsConstructor
public class Employment implements Serializable {
  public Employment(
      EmploymentId employmentId,
      Double salary,
      Date startDate,
      Date endDate,
      Integer weeklyHours,
      String position) {
    this.employmentId = employmentId;
    this.salary = salary;
    this.startDate = startDate;
    this.endDate = endDate;
    this.weeklyHours = weeklyHours;
    this.position = position;
  }

  @EmbeddedId private EmploymentId employmentId;

  @Column(name = "SALARY")
  private Double salary;

  @Column(name = "START_DATE")
  private java.sql.Date startDate;

  @Column(name = "END_DATE")
  private java.sql.Date endDate;

  @Column(name = "WEEKLY_HOURS")
  private Integer weeklyHours;

  @Column(name = "POSITION")
  private String position;
}
