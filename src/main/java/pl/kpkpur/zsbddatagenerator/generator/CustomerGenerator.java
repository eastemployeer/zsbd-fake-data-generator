package pl.kpkpur.zsbddatagenerator.generator;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;
import pl.kpkpur.zsbddatagenerator.model.Customer;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

@Component
public class CustomerGenerator extends FakerGenerator<Customer> {

    public CustomerGenerator(Faker faker) {
        super(faker);
    }

    @Override
    public Customer generate() {
        var firstName = faker.name().firstName();
        var lastName = faker.name().lastName();
        return new Customer(
                getNextId(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(
                        firstName.toLowerCase() + "."
                                + lastName.toLowerCase() + faker.number().digits(4)),
                generatePassword(),
                faker.demographic().sex(),
                generateDateOfBirth()
        );
    }

    private String generatePassword() {
        return faker.artist().name().replace(" ", "") + ((int) (Math.random() * 10));
    }

    private Date generateDateOfBirth() {
        return Date.valueOf(
                LocalDate.ofInstant(
                        faker
                                .date()
                                .between(Date.valueOf("1960-01-01"), Date.valueOf("2012-12-31"))
                                .toInstant(),
                        ZoneId.systemDefault()));
    }
}
