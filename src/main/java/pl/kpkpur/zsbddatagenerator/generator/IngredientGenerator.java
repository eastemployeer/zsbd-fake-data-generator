package pl.kpkpur.zsbddatagenerator.generator;

import com.github.javafaker.Faker;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;
import pl.kpkpur.zsbddatagenerator.model.Dish;
import pl.kpkpur.zsbddatagenerator.model.ScreeningEmployee;
import pl.kpkpur.zsbddatagenerator.model.ScreeningEmployeeId;
import pl.kpkpur.zsbddatagenerator.model.Product;

@Component
public class IngredientGenerator extends FakerGenerator<ScreeningEmployee> {
  public IngredientGenerator(Faker faker) {
    super(faker);
  }

  public ScreeningEmployee generate(long productId, long dishId) {
    return new ScreeningEmployee(
        new ScreeningEmployeeId(productId, dishId), faker.number().numberBetween(1L, 201L));
  }

  public List<ScreeningEmployee> generateMultiple(List<Dish> dishes, List<Product> products) {
    return dishes.stream()
        .map(Dish::getDishId)
        .flatMap(dishId -> generateIngredientsForDish(dishId, products).stream())
        .toList();
  }

  @Override
  public ScreeningEmployee generate() {
    return new ScreeningEmployee();
  }

  private List<ScreeningEmployee> generateIngredientsForDish(long dishId, List<Product> products) {
    List<Product> unusedProducts = new LinkedList<>(products);

    return Stream.generate(
            () ->
                generate(
                    unusedProducts
                        .remove(faker.number().numberBetween(0, unusedProducts.size()))
                        .getProductId(),
                    dishId))
        .limit(faker.number().numberBetween(1, 16))
        .toList();
  }
}
