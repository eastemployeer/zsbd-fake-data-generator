package pl.kpkpur.zsbddatagenerator.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "INGREDIENT")
@NoArgsConstructor
public class ScreeningEmployee {

  public ScreeningEmployee(ScreeningEmployeeId screeningEmployeeId, Long amount) {
    this.screeningEmployeeId = screeningEmployeeId;
    this.amount = amount;
  }

  @EmbeddedId private ScreeningEmployeeId screeningEmployeeId;

  @Column(name = "AMOUNT")
  private Long amount;
}
