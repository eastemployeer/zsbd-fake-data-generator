package pl.kpkpur.zsbddatagenerator.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "ORDERED_DISH")
@NoArgsConstructor
public class OrderedDish implements Serializable {
  public OrderedDish(OrderedDishId orderedDishId, Integer amount, String remarks) {
    this.orderedDishId = orderedDishId;
    this.amount = amount;
    this.remarks = remarks;
  }

  @EmbeddedId private OrderedDishId orderedDishId;

  @Column(name = "AMOUNT")
  private Integer amount;

  @Column(name = "REMARKS")
  private String remarks;
}
