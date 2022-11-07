package pl.kpkpur.zsbddatagenerator.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kpkpur.zsbddatagenerator.model.enums.DishCategory;

import java.sql.Time;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "SCREENING")
@NoArgsConstructor
public class Screening {
  public Screening(
      Long id,
      String roomName,
      Long movieVersionId,
      Timestamp datetime,
      Integer isPremiere,
      Integer isDiscountable,
      Long adsLength,
      Double price) {
    this.id = id;
    this.price = price;
    this.roomName = roomName;
    this.movieVersionId = movieVersionId;
    this.datetime = datetime;
    this.isPremiere = isPremiere;
    this.isDiscountable = isDiscountable;
    this.adsLength = adsLength;
  }

  @Id
  @Column(name = "ID")
  private Long id;

  @Column(name = "PRICE")
  private Double price;

  @Column(name = "ROOM_NAME")
  private String roomName;

  @Column(name = "IS_PREMIERE")
  private Integer isPremiere;

  @Column(name = "IS_DISCOUNTABLE")
  private Integer isDiscountable;

  @Column(name = "DATETIME")
  private Timestamp datetime;

  @Column(name = "ADS_LENGTH")
  private Long adsLength;

  @Column(name = "MOVIE_VERSION_ID")
  private Long movieVersionId;
}
