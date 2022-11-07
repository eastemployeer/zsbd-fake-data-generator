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
@Table(name = "RESTAURANT")
@NoArgsConstructor
public class Restaurant {
  public Restaurant(
      Long restaurantId,
      Long addressId,
      Integer seatsCount,
      Integer floorArea,
      Date dateOfOpening,
      String name) {
    this.restaurantId = restaurantId;
    this.addressId = addressId;
    this.seatsCount = seatsCount;
    this.floorArea = floorArea;
    this.dateOfOpening = dateOfOpening;
    this.name = name;
  }

  @Id
  @Column(name = "RESTAURANT_ID")
  private Long restaurantId;

  @Column(name = "ADDRESS_ID")
  private Long addressId;

  @Column(name = "SEATS_COUNT")
  private Integer seatsCount;

  @Column(name = "FLOOR_AREA")
  private Integer floorArea;

  @Column(name = "DATE_OF_OPENING")
  private java.sql.Date dateOfOpening;

  @Column(name = "NAME")
  private String name;
}
