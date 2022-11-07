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
@Table(name = "REVIEW")
@NoArgsConstructor
public class Review {
  public Review(
      Long reviewId,
      Long restaurantId,
      Long rating,
      String reviewContent,
      Date reviewDate,
      String reviewerName) {
    this.reviewId = reviewId;
    this.restaurantId = restaurantId;
    this.rating = rating;
    this.reviewContent = reviewContent;
    this.reviewDate = reviewDate;
    this.reviewerName = reviewerName;
  }

  @Id
  @Column(name = "REVIEW_ID")
  private Long reviewId;

  @Column(name = "RESTAURANT_ID")
  private Long restaurantId;

  @Column(name = "RATING")
  private Long rating;

  @Column(name = "REVIEW_CONTENT")
  private String reviewContent;

  @Column(name = "REVIEW_DATE")
  private java.sql.Date reviewDate;

  @Column(name = "REVIEWER_NAME")
  private String reviewerName;
}
