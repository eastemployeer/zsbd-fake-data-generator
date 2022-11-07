package pl.kpkpur.zsbddatagenerator.generator;

import com.github.javafaker.Faker;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;
import pl.kpkpur.zsbddatagenerator.model.Dish;
import pl.kpkpur.zsbddatagenerator.model.MovieVersion;
import pl.kpkpur.zsbddatagenerator.model.OrderedDish;
import pl.kpkpur.zsbddatagenerator.model.OrderedDishId;

@Component
public class OrderedDishGenerator extends FakerGenerator<OrderedDish> {
  public OrderedDishGenerator(Faker faker) {
    super(faker);
  }

  public OrderedDish generate(long orderId, long dishId) {
    String remarks = faker.lorem().sentence(-1, 5);

    return new OrderedDish(
        new OrderedDishId(orderId, dishId),
        faker.number().numberBetween(1, 5),
        remarks.substring(0, remarks.length() - 1));
  }

  public List<OrderedDish> generateMultiple(
          List<MovieVersion> movieVersions, List<Dish> dishes) {
    return movieVersions.stream()
        .map(MovieVersion::getRestaurantOrderId)
        .flatMap(dishId -> generateOrderedDishesForOrder(dishId, dishes).stream())
        .toList();
  }

  @Override
  public OrderedDish generate() {
    return new OrderedDish();
  }

  private List<OrderedDish> generateOrderedDishesForOrder(long orderId, List<Dish> dishes) {
    List<Dish> notOrderedDishes = new LinkedList<>(dishes);

    return Stream.generate(
            () ->
                generate(
                    orderId,
                    notOrderedDishes
                        .remove(faker.number().numberBetween(0, notOrderedDishes.size()))
                        .getDishId()))
        .limit(faker.number().numberBetween(1, 4))
        .toList();
  }
}
