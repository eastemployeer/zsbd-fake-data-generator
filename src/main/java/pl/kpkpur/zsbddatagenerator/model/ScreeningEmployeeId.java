package pl.kpkpur.zsbddatagenerator.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
public class ScreeningEmployeeId implements Serializable {
  @Column(name = "EMPLOYEE_ID")
  private Long employeeId;

  @Column(name = "SCREENING_ID")
  private Long screeningId;

  public ScreeningEmployeeId(Long employeeId, Long screeningId) {
    this.employeeId = employeeId;
    this.screeningId = screeningId;
  }
}
