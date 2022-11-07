package pl.kpkpur.zsbddatagenerator.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
public class OrderedDishId implements Serializable {
  @Column(name = "RESTAURANT_ORDER_ID")
  private Long restaurantOrderId;

  @Column(name = "DISH_ID")
  private Long dishId;

  public OrderedDishId(Long restaurantOrderId, Long dishId) {
    this.restaurantOrderId = restaurantOrderId;
    this.dishId = dishId;
  }
}
