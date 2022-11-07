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
@Table(name = "MOVIE_VERSION")
@NoArgsConstructor
public class MovieVersion {
  public MovieVersion(
      Long id,
      Long movieId,
      String language,
      Integer hasSubtitles,
      Integer is3d,
      Integer isAtmos) {
    this.id = id;
    this.movieId = movieId;
    this.language = language;
    this.hasSubtitles = hasSubtitles;
    this.is3d = is3d;
    this.isAtmos = isAtmos;
  }

  @Id
  @Column(name = "ID")
  private Long id;

  @Column(name = "MOVIE_ID")
  private Long movieId;

  @Column(name = "LANGUAGE")
  private String language;

  @Column(name = "HAS_SUBTITLES")
  private Integer hasSubtitles;

  @Column(name = "IS_3D")
  private Integer is3d;

  @Column(name = "IS_ATMOS")
  private Integer isAtmos;
}
