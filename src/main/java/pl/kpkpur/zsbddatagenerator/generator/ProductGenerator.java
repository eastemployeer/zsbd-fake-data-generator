package pl.kpkpur.zsbddatagenerator.generator;

import static pl.kpkpur.zsbddatagenerator.util.Enums.getRandomValue;

import com.github.javafaker.Faker;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;
import pl.kpkpur.zsbddatagenerator.model.Product;
import pl.kpkpur.zsbddatagenerator.model.enums.ProductCategory;
import pl.kpkpur.zsbddatagenerator.model.enums.StoringConditions;
import pl.kpkpur.zsbddatagenerator.model.enums.StoringUnit;

@Component
public class ProductGenerator extends FakerGenerator<Product> {
  private final Set<String> alreadyGeneratedNames = new HashSet<>();

  public ProductGenerator(Faker faker) {
    super(faker);
  }

  @Override
  public Product generate() {
    return new Product(
        getNextId(),
        generateUniqueName(),
        faker.number().numberBetween(5L, 200L),
        getRandomValue(StoringConditions.class),
        getRandomValue(StoringUnit.class),
        getRandomValue(ProductCategory.class));
  }

  private String generateUniqueName() {
    String name = generateName();
    while (alreadyGeneratedNames.contains(name)) {
      name = generateName();
    }
    alreadyGeneratedNames.add(name);

    return name;
  }

  private String generateName() {
    return faker.color().name()
        + "-"
        + faker.color().name()
        + "-"
        + faker.color().name()
        + " "
        + faker.food().ingredient();
  }
}
