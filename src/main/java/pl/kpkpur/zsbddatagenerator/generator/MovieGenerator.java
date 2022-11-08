package pl.kpkpur.zsbddatagenerator.generator;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;
import pl.kpkpur.zsbddatagenerator.model.Movie;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
@Component
public class MovieGenerator extends FakerGenerator<Movie> {

    private final Set<String> alreadyGeneratedTitles = new HashSet<>();

    public MovieGenerator(Faker faker) {
        super(faker);
    }
    @Override
    public Movie generate() {
        return new Movie(
                getNextId(),
                generateUniqueTitle(),
                faker.book().author(),
                faker.number().numberBetween(15, 180),
                generatePremiereDate(),
                faker.book().genre()
        );
    }

    private String generateUniqueTitle() {
        String title = generateTitle();
        while (alreadyGeneratedTitles.contains(title)) {
            title = generateTitle();
        }
        alreadyGeneratedTitles.add(title);

        return title;
    }

    private String generateTitle() {
        return faker.book().title()
                + " "
                + faker.number().numberBetween(1, 15)
                + " - "
                + faker.book().title();
    }

    private Date generatePremiereDate() {
        return Date.valueOf(
                LocalDate.ofInstant(
                        faker
                                .date()
                                .between(Date.valueOf("1960-01-01"), Date.valueOf("2001-12-31"))
                                .toInstant(),
                        ZoneId.systemDefault()));
    }
}
