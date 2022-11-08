package pl.kpkpur.zsbddatagenerator.model;

import java.sql.Date;
import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "REVIEW")
@NoArgsConstructor
public class Review {
  public Review(
      ReviewId id,
      Integer rating,
      String description,
      Date date,
      Integer likes) {
    this.id = id;
    this.rating = rating;
    this.description = description;
    this.date = date;
    this.likes = likes;
  }

  @EmbeddedId
  private ReviewId id;

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "RATING")
  private Integer rating;

  @Column(name = "DATE")
  private java.sql.Date date;

  @Column(name = "LIKES")
  private Integer likes;
}
