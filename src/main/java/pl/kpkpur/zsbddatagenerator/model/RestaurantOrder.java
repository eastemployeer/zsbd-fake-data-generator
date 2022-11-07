package pl.kpkpur.zsbddatagenerator.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kpkpur.zsbddatagenerator.model.enums.PaymentMethod;

@Data
@Entity
@Table(name = "RESTAURANT_ORDER")
@NoArgsConstructor
public class RestaurantOrder {
  public RestaurantOrder(
      Long restaurantOrderId,
      Long restaurantId,
      Long waiterId,
      Integer tableNo,
      Timestamp orderTime,
      Timestamp deliveryTime,
      Long tip,
      PaymentMethod paymentMethod,
      String remarks) {
    this.restaurantOrderId = restaurantOrderId;
    this.restaurantId = restaurantId;
    this.waiterId = waiterId;
    this.tableNo = tableNo;
    this.orderTime = orderTime;
    this.deliveryTime = deliveryTime;
    this.tip = tip;
    this.paymentMethod = paymentMethod;
    this.remarks = remarks;
  }

  @Id
  @Column(name = "RESTAURANT_ORDER_ID")
  private Long restaurantOrderId;

  @Column(name = "RESTAURANT_ID")
  private Long restaurantId;

  @Column(name = "WAITER_ID")
  private Long waiterId;

  @Column(name = "TABLE_NO")
  private Integer tableNo;

  @Column(name = "ORDER_TIME")
  private java.sql.Timestamp orderTime;

  @Column(name = "DELIVERY_TIME")
  private java.sql.Timestamp deliveryTime;

  @Column(name = "TIP")
  private Long tip;

  @Enumerated(EnumType.STRING)
  @Column(name = "PAYMENT_METHOD")
  private PaymentMethod paymentMethod;

  @Column(name = "REMARKS")
  private String remarks;
}
