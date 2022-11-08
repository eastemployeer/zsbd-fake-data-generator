package pl.kpkpur.zsbddatagenerator.model;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kpkpur.zsbddatagenerator.model.enums.ScreeningEmployeeResponsibility;

@Data
@Entity
@Table(name = "SCREENINGEMPLOYEE")
@NoArgsConstructor
public class ScreeningEmployee {

  public ScreeningEmployee(ScreeningEmployeeId screeningEmployeeId, ScreeningEmployeeResponsibility responsibility) {
    this.screeningEmployeeId = screeningEmployeeId;
    this.responsibility = responsibility;
  }

  @EmbeddedId private ScreeningEmployeeId screeningEmployeeId;
  @Enumerated(EnumType.STRING)
  @Column(name = "RESPONSIBILITY")
  private ScreeningEmployeeResponsibility responsibility;
}
