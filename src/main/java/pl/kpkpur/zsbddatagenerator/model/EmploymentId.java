package pl.kpkpur.zsbddatagenerator.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
public class EmploymentId implements Serializable {

  @Column(name = "RESTAURANT_ID")
  private Long restaurantId;

  @Column(name = "EMPLOYEE_ID")
  private Long employeeId;

  public EmploymentId(Long restaurantId, Long employeeId) {
    this.restaurantId = restaurantId;
    this.employeeId = employeeId;
  }
}
