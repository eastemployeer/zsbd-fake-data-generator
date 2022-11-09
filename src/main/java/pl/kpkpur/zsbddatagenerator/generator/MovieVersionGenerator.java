package pl.kpkpur.zsbddatagenerator.generator;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;
import pl.kpkpur.zsbddatagenerator.model.Movie;
import pl.kpkpur.zsbddatagenerator.model.MovieVersion;

import java.util.List;
import java.util.stream.Stream;

@Component
public class  MovieVersionGenerator extends FakerGenerator<MovieVersion> {

    public MovieVersionGenerator(Faker faker) {
        super(faker);
    }

    @Override
    public MovieVersion generate() {
        return new MovieVersion();
    }

    public MovieVersion generate(Movie movie) {
        return new MovieVersion(
                getNextId(),
                movie,
                faker.nation().language(),
                faker.number().numberBetween(0,2),
                faker.number().numberBetween(0,2),
                faker.number().numberBetween(0,2)
        );
    }

    public List<MovieVersion> generateMultiple(List<Movie> movies) {
        return movies.stream()
                .map(this::generateMovieVersionsForMovies)
                .flatMap(List::stream)
                .toList();
    }

    private List<MovieVersion> generateMovieVersionsForMovies(Movie movie) {
        return Stream.generate(() -> generate(movie))
                .limit(faker.number().numberBetween(1, 3))
                .toList();
    }
}
