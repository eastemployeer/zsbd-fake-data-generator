package pl.kpkpur.zsbddatagenerator.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class Screening {
  private Long id;
  private Room room;
  private Long movieVersionId;
  private Timestamp datetime;
  private Integer isPremiere;
  private Integer isDiscountable;
  private Integer adsLength;
  private Double price;
}
