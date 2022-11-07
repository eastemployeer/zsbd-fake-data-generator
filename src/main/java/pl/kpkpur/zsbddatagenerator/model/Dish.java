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

@Data
@Entity
@Table(name = "DISH")
@NoArgsConstructor
public class Dish {
  public Dish(
      Long dishId,
      Double price,
      String description,
      Integer vegetarian,
      Integer vegan,
      Integer spiciness,
      String name,
      DishCategory category) {
    this.dishId = dishId;
    this.price = price;
    this.description = description;
    this.vegetarian = vegetarian;
    this.vegan = vegan;
    this.spiciness = spiciness;
    this.name = name;
    this.category = category;
  }

  @Id
  @Column(name = "DISH_ID")
  private Long dishId;

  @Column(name = "PRICE")
  private Double price;

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "VEGETARIAN")
  private Integer vegetarian;

  @Column(name = "VEGAN")
  private Integer vegan;

  @Column(name = "SPICINESS")
  private Integer spiciness;

  @Column(name = "NAME")
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "CATEGORY")
  private DishCategory category;
}
