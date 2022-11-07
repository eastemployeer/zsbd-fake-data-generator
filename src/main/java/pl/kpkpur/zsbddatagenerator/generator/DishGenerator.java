package pl.kpkpur.zsbddatagenerator.generator;

import static pl.kpkpur.zsbddatagenerator.util.Enums.getRandomValue;

import com.github.javafaker.Faker;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;
import pl.kpkpur.zsbddatagenerator.model.Dish;
import pl.kpkpur.zsbddatagenerator.model.enums.DishCategory;

@Component
public class DishGenerator extends FakerGenerator<Dish> {
  private final Set<String> alreadyGeneratedNames = new HashSet<>();

  public DishGenerator(Faker faker) {
    super(faker);
  }

  @Override
  public Dish generate() {
    return new Dish(
        getNextId(),
        faker.number().randomDouble(2, 6, 81),
        faker.lorem().sentence(),
        faker.number().numberBetween(0, 2),
        faker.number().numberBetween(0, 2),
        faker.number().numberBetween(0, 5),
        generateUniqueName(),
        getRandomValue(DishCategory.class));
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
    return faker.food().dish()
        + " with "
        + faker.food().dish()
        + ", "
        + faker.food().dish()
        + " and "
        + faker.food().dish();
  }
}
