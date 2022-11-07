package pl.kpkpur.zsbddatagenerator.generator;

import static java.time.temporal.ChronoUnit.DAYS;

import com.github.javafaker.Faker;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;
import pl.kpkpur.zsbddatagenerator.model.Restaurant;
import pl.kpkpur.zsbddatagenerator.model.Review;

@Component
public class ReviewGenerator extends FakerGenerator<Review> {
  public ReviewGenerator(Faker faker) {
    super(faker);
  }

  @Override
  public Review generate() {
    return new Review();
  }

  public Review generate(Restaurant restaurant) {
    return new Review(
        getNextId(),
        restaurant.getRestaurantId(),
        faker.number().numberBetween(1L, 6L),
        faker.lorem().paragraph(2),
        generateReviewDate(restaurant),
        faker.name().fullName());
  }

  private Date generateReviewDate(Restaurant restaurant) {
    return Date.valueOf(
        LocalDate.ofInstant(
            faker
                .date()
                .between(restaurant.getDateOfOpening(), Date.valueOf(LocalDate.now()))
                .toInstant(),
            ZoneId.systemDefault()));
  }

  public List<Review> generateMultiple(List<Restaurant> restaurants) {
    return restaurants.stream()
        .map(this::generateReviewsForRestaurant)
        .flatMap(List::stream)
        .toList();
  }

  private List<Review> generateReviewsForRestaurant(Restaurant restaurant) {
    return Stream.generate(() -> generate(restaurant))
        .limit(generateReviewsCount(restaurant))
        .toList();
  }

  private long generateReviewsCount(Restaurant restaurant) {
    long daysSinceOpening =
        DAYS.between(restaurant.getDateOfOpening().toLocalDate(), LocalDate.now());

    double reviewCount =
        restaurant.getSeatsCount()
            * daysSinceOpening
            / 800.0
            * ThreadLocalRandom.current().nextDouble(0.8, 1.2);

    return (long) reviewCount;
  }
}
