package pl.kpkpur.zsbddatagenerator.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieVersion {
  private Long id;
  private Movie movie;
  private String language;
  private Integer hasSubtitles;
  private Integer is3d;
  private Integer isAtmos;
}
