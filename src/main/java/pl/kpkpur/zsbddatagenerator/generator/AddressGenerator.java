package pl.kpkpur.zsbddatagenerator.generator;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;
import pl.kpkpur.zsbddatagenerator.model.Address;

@Component
public class AddressGenerator extends FakerGenerator<Address> {
  public AddressGenerator(Faker faker) {
    super(faker);
  }

  @Override
  public Address generate() {
    var fakerAddress = faker.address();

    return new Address(
        getNextId(),
        fakerAddress.country(),
        fakerAddress.state(),
        fakerAddress.city(),
        fakerAddress.zipCode(),
        fakerAddress.streetName(),
        fakerAddress.streetAddressNumber());
  }
}
