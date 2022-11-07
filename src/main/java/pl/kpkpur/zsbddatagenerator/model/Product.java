package pl.kpkpur.zsbddatagenerator.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kpkpur.zsbddatagenerator.model.enums.ProductCategory;
import pl.kpkpur.zsbddatagenerator.model.enums.StoringConditions;
import pl.kpkpur.zsbddatagenerator.model.enums.StoringUnit;

@Data
@Entity
@Table(name = "PRODUCT")
@NoArgsConstructor
public class Product {
  public Product(
      Long productId,
      String name,
      Long caloriesPer100G,
      StoringConditions storingConditions,
      StoringUnit storingUnit,
      ProductCategory category) {
    this.productId = productId;
    this.name = name;
    this.caloriesPer100G = caloriesPer100G;
    this.storingConditions = storingConditions;
    this.storingUnit = storingUnit;
    this.category = category;
  }

  @Id
  @Column(name = "PRODUCT_ID")
  private Long productId;

  @Column(name = "NAME")
  private String name;

  @Column(name = "CALORIES_PER_100G")
  private Long caloriesPer100G;

  @Enumerated(EnumType.STRING)
  @Column(name = "STORING_CONDITIONS")
  private StoringConditions storingConditions;

  @Enumerated(EnumType.STRING)
  @Column(name = "STORING_UNIT")
  private StoringUnit storingUnit;

  @Enumerated(EnumType.STRING)
  @Column(name = "CATEGORY")
  private ProductCategory category;
}
