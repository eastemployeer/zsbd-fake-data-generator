package pl.kpkpur.zsbddatagenerator.generator;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;
import pl.kpkpur.zsbddatagenerator.model.Customer;
import pl.kpkpur.zsbddatagenerator.model.Movie;
import pl.kpkpur.zsbddatagenerator.model.Review;
import pl.kpkpur.zsbddatagenerator.model.ReviewId;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.Integer.min;

@Component
public class ReviewGenerator extends FakerGenerator<Review> {

    public ReviewGenerator(Faker faker) {
        super(faker);
    }

    @Override
    public Review generate() {
        return new Review();
    }

    public Review generate(Movie movie, Long customerId) {
        return new Review(
                new ReviewId(customerId, movie.getId()),
                faker.number().numberBetween(1,10),
                faker.lorem().paragraph(2),
                generateReviewDate(movie),
                faker.number().numberBetween(0,10000)
        );
    }

    public List<Review> generateMultiple(List<Movie> movies, List<Customer> customers) {
        return movies.stream()
                .flatMap(movie -> generateReviewsForMovie(movie, customers).stream())
                .toList();
    }

    private List<Review> generateReviewsForMovie(Movie movie, List<Customer> customers) {
        List<Customer> unusedCustomers = new LinkedList<>(customers);

        int maxReviews = min(500, customers.size());

        return Stream.generate(
                        () -> generate(movie,
                                unusedCustomers
                                        .remove(faker.number().numberBetween(0, unusedCustomers.size()))
                                        .getId()))
                .limit(faker.number().numberBetween(0, maxReviews))
                .toList();
    }

    private Date generateReviewDate(Movie movie) {
        return Date.valueOf(
                LocalDate.ofInstant(
                        faker
                                .date()
                                .between(movie.getPremiereDate(), Date.valueOf(LocalDate.now()))
                                .toInstant(),
                        ZoneId.systemDefault()));
    }
}
