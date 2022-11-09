package pl.kpkpur.zsbddatagenerator.generator;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;
import pl.kpkpur.zsbddatagenerator.config.GenerationConfig;
import pl.kpkpur.zsbddatagenerator.model.MovieVersion;
import pl.kpkpur.zsbddatagenerator.model.Room;
import pl.kpkpur.zsbddatagenerator.model.Screening;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Component
public class ScreeningGenerator extends FakerGenerator<Screening> {

    public ScreeningGenerator(Faker faker) {
        super(faker);
    }

    public Screening generate(Room room, MovieVersion movieVersion, LocalDateTime dateTime) {
        var isPremiere = movieVersion.getMovie().getPremiereDate().toLocalDate().equals(dateTime.toLocalDate());
        var isDiscountable = !isPremiere;
        var price = isPremiere ? 29.99 : List.of(14.99, 19.99, 22.99).get(faker.random().nextInt(2));
        return new Screening(
                getNextId(),
                room.getName(),
                movieVersion.getId(),
                Timestamp.valueOf(dateTime),
                isPremiere ? 1 : 0,
                isDiscountable ? 1 : 0,
                (long) faker.number().numberBetween(10, 30),
                price
        );
    }

    @Override
    public Screening generate() {
        throw new IllegalCallerException("Nope");
    }

    @Override
    public List<Screening> generateMultiple(int count) {
        throw new IllegalCallerException("Nope");
    }

    public List<Screening> generateMultiple(List<Room> rooms, List<MovieVersion> movieVersions) {
        return rooms.stream()
                .flatMap(room -> generateScreeningsForRoom(room, movieVersions).stream())
                .toList();
    }

    private List<Screening> generateScreeningsForRoom(Room room, List<MovieVersion> movieVersions) {
        var dateTimes = IntStream.range(0, GenerationConfig.NUMBER_OF_SCREENING_DAYS)
                .boxed()
                .map(dayNo -> LocalDate.now().minusDays(dayNo))
                .flatMap(day -> IntStream.of(0, 1, 2)
                        .boxed()
                        .map(screeningNo -> LocalDateTime.of(day, LocalTime.NOON).plusHours(screeningNo * 3L)))
                .toList();

        return dateTimes
                .stream()
                .map(dt -> generate(room, movieVersions.get(faker.random().nextInt(movieVersions.size() - 1)), dt))
                .toList();
    }
}
