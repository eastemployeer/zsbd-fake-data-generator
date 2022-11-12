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
import static pl.kpkpur.zsbddatagenerator.config.GenerationConfig.*;

@Component
public class ReviewGenerator extends FakerGenerator<Review> {

    public ReviewGenerator(Faker faker) {
        super(faker);
    }

    @Override
    public Review generate() {
        throw new IllegalCallerException("Cannot generate Review without passing lists of Movies and Customers!");
    }

    public Review generate(Movie movie, Long customerId) {
        return new Review(
                new ReviewId(customerId, movie.getId()),
                faker.lorem().paragraph(faker.number().numberBetween(MIN_REVIEW_DESCRIPTION_SENTENCES_NUMBER, MAX_REVIEW_DESCRIPTION_SENTENCES_NUMBER)),
                faker.number().numberBetween(1, 10),
                generateReviewDate(movie),
                faker.number().numberBetween(MIN_REVIEW_LIKES_NUMBER, MAX_REVIEW_LIKES_NUMBER)
        );
    }

    public List<Review> generateMultiple(List<Movie> movies, List<Customer> customers) {
        return movies.stream()
                .flatMap(movie -> generateReviewsForMovie(movie, customers).stream())
                .toList();
    }

    private List<Review> generateReviewsForMovie(Movie movie, List<Customer> customers) {
        List<Customer> unusedCustomers = new LinkedList<>(customers);

        int maxReviews = min(MAX_REVIEWS_PER_MOVIE, customers.size());

        return Stream.generate(
                        () -> generate(movie,
                                unusedCustomers
                                        .remove(faker.number().numberBetween(0, unusedCustomers.size()))
                                        .getId()))
                .limit(faker.number().numberBetween(MIN_REVIEWS_PER_MOVIE, maxReviews))
                .toList();
    }

    private Date generateReviewDate(Movie movie) {
        return Date.valueOf(
                LocalDate.ofInstant(
                        faker
                                .date()
                                .between(Date.valueOf(movie.getPremiereDate().toLocalDate()), Date.valueOf(NOW_DATE))
                                .toInstant(),
                        ZoneId.systemDefault()));
    }
}
