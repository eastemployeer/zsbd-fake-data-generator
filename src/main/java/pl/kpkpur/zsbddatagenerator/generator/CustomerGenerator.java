package pl.kpkpur.zsbddatagenerator.generator;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;
import pl.kpkpur.zsbddatagenerator.model.Customer;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

import static pl.kpkpur.zsbddatagenerator.config.GenerationConfig.*;

@Component
public class CustomerGenerator extends FakerGenerator<Customer> {

    private final Set<String> alreadyGeneratedEmails = new HashSet<>();

    public CustomerGenerator(Faker faker) {
        super(faker);
    }

    @Override
    public Customer generate() {
        var firstName = faker.name().firstName();
        var lastName = faker.name().lastName();
        return new Customer(
                getNextId(),
                firstName,
                lastName,
                generateUniqueEmail(firstName, lastName),
                faker.internet().password(),
                faker.demographic().sex(),
                generateBirthDate()
        );
    }

    private String generateUniqueEmail(String firstName, String lastName) {
        String email = generateEmail(firstName, lastName);
        while (alreadyGeneratedEmails.contains(email)) {
            email = generateEmail(firstName, lastName);
        }
        alreadyGeneratedEmails.add(email);

        return email;
    }

    private String generateEmail(String firstName, String lastName) {
        return faker.internet().emailAddress(
                firstName.toLowerCase() + "."
                        + lastName.toLowerCase() + faker.number().digits(4));
    }

    private Date generateBirthDate() {
        return Date.valueOf(
                LocalDate.ofInstant(
                        faker
                                .date()
                                .between(Date.valueOf(CUSTOMER_BIRTHDATE_START), Date.valueOf(CUSTOMER_BIRTHDATE_END))
                                .toInstant(),
                        ZoneId.systemDefault()));
    }
}
