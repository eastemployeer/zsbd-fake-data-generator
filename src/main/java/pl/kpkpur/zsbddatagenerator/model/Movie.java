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
@Table(name = "MOVIE")
@NoArgsConstructor
public class Movie {
  public Movie(
      Long id,
      String title,
      String director,
      Long length,
      Date premiereDate,
      String genre) {
    this.id = id;
    this.title = title;
    this.director = director;
    this.length = length;
    this.premiereDate = premiereDate;
    this.genre = genre;
  }

  @Id
  @Column(name = "ID")
  private Long id;

  @Column(name = "TITLE")
  private String title;

  @Column(name = "DIRECTOR")
  private String director;

  @Column(name = "LENGTH")
  private Long length;

  @Column(name = "PREMIERE_DATE")
  private java.sql.Date premiereDate;

  @Column(name = "GENRE")
  private String genre;
}
