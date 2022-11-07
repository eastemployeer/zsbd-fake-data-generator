package pl.kpkpur.zsbddatagenerator.generator;

import com.github.javafaker.Faker;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;
import pl.kpkpur.zsbddatagenerator.model.Address;
import pl.kpkpur.zsbddatagenerator.model.Restaurant;

@Component
public class RestaurantGenerator extends FakerGenerator<Restaurant> {
  private final Set<String> alreadyGeneratedNames = new HashSet<>();

  public RestaurantGenerator(Faker faker) {
    super(faker);
  }

  public Restaurant generate(Address address) {
    return new Restaurant(
        getNextId(),
        address.getAddressId(),
        faker.number().numberBetween(7, 131),
        faker.number().numberBetween(100, 501),
        generateOpeningDate(),
        generateUniqueName());
  }

  private Date generateOpeningDate() {
    return Date.valueOf(
        LocalDate.ofInstant(
            faker
                .date()
                .between(Date.valueOf("2000-01-01"), Date.valueOf(LocalDate.now().minusMonths(2)))
                .toInstant(),
            ZoneId.systemDefault()));
  }

  public List<Restaurant> generateMultiple(List<Address> addresses) {
    return addresses.stream().map(this::generate).toList();
  }

  @Override
  public Restaurant generate() {
    return new Restaurant();
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
    return faker.company().name()
        + " - "
        + faker.food().dish()
        + ", "
        + faker.food().fruit()
        + " & "
        + faker.food().vegetable();
  }
}
