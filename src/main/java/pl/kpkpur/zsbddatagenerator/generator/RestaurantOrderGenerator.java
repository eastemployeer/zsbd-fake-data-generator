package pl.kpkpur.zsbddatagenerator.generator;

import static java.time.temporal.ChronoUnit.DAYS;

import com.github.javafaker.Faker;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;
import pl.kpkpur.zsbddatagenerator.model.Employment;
import pl.kpkpur.zsbddatagenerator.model.EmploymentId;
import pl.kpkpur.zsbddatagenerator.model.Restaurant;
import pl.kpkpur.zsbddatagenerator.model.MovieVersion;
import pl.kpkpur.zsbddatagenerator.model.Review;
import pl.kpkpur.zsbddatagenerator.model.enums.PaymentMethod;
import pl.kpkpur.zsbddatagenerator.util.Enums;

@Component
public class RestaurantOrderGenerator extends FakerGenerator<MovieVersion> {
  public RestaurantOrderGenerator(Faker faker) {
    super(faker);
  }

  public MovieVersion generate(Restaurant restaurant, long employeeId, double avgRating) {
    Timestamp orderTime =
        generateOrderTime(
            Instant.ofEpochMilli(restaurant.getDateOfOpening().getTime()),
            Instant.now().minusSeconds(50000));
    Timestamp deliveryTime =
        generateOrderTime(
            orderTime.toInstant().plusSeconds(1000), orderTime.toInstant().plusSeconds(7200));
    String remarks = faker.lorem().sentence(-1, 10);

    return new MovieVersion(
        getNextId(),
        restaurant.getRestaurantId(),
        employeeId,
        faker.number().numberBetween(1, restaurant.getSeatsCount() + 1),
        orderTime,
        deliveryTime,
        generateTip(avgRating),
        Enums.getRandomValue(PaymentMethod.class),
        remarks.substring(0, remarks.length() - 1));
  }

  public List<MovieVersion> generateMultiple(
      List<Restaurant> restaurants, List<Employment> employments, List<Review> reviews) {
    return restaurants.stream()
        .map(
            restaurant ->
                generateOrdersForRestaurant(
                    restaurant,
                    employments.stream()
                        .map(Employment::getEmploymentId)
                        .filter(
                            employmentId ->
                                employmentId.getRestaurantId().equals(restaurant.getRestaurantId()))
                        .map(EmploymentId::getEmployeeId)
                        .toList(),
                    reviews.stream()
                        .filter(
                            review -> review.getRestaurantId().equals(restaurant.getRestaurantId()))
                        .mapToLong(Review::getRating)
                        .average()
                        .orElse(0)))
        .flatMap(List::stream)
        .toList();
  }

  private List<MovieVersion> generateOrdersForRestaurant(
      Restaurant restaurant, List<Long> restaurantEmployeeIds, double avgRating) {
    if (restaurantEmployeeIds.isEmpty()) {
      return Collections.emptyList();
    }

    return Stream.generate(
            () ->
                generate(
                    restaurant,
                    restaurantEmployeeIds.get(
                        faker.number().numberBetween(0, restaurantEmployeeIds.size())),
                    avgRating))
        .limit(generateOrderCount(restaurant))
        .toList();
  }

  @Override
  public MovieVersion generate() {
    return new MovieVersion();
  }

  private long generateOrderCount(Restaurant restaurant) {
    long daysSinceOpening =
        DAYS.between(restaurant.getDateOfOpening().toLocalDate(), LocalDate.now());

    double orderCount =
        restaurant.getSeatsCount()
            * daysSinceOpening
            / 300.0
            * ThreadLocalRandom.current().nextDouble(0.8, 1.2);

    return (long) orderCount;
  }

  private Timestamp generateOrderTime(Instant startInclusive, Instant endExclusive) {
    long startSeconds = startInclusive.getEpochSecond();
    long endSeconds = endExclusive.getEpochSecond();
    long random = ThreadLocalRandom.current().nextLong(startSeconds, endSeconds);

    return Timestamp.from(Instant.ofEpochSecond(random));
  }

  private long generateTip(double avgRating) {
    double stddev = 25;
    double variance = stddev * stddev;
    double tip =
        ThreadLocalRandom.current()
                .nextGaussian((faker.number().numberBetween(0, 11) * avgRating) - variance, stddev)
            + variance;

    if (tip < 0) {
      tip = 0;
    }
    if (tip > variance * 2) {
      tip = variance * 2;
    }

    return (long) (tip) * ThreadLocalRandom.current().nextInt(2);
  }
}
