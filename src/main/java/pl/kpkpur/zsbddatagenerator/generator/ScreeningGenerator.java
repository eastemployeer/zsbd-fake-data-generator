package pl.kpkpur.zsbddatagenerator.generator;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;
import pl.kpkpur.zsbddatagenerator.model.MovieVersion;
import pl.kpkpur.zsbddatagenerator.model.Room;
import pl.kpkpur.zsbddatagenerator.model.Screening;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static pl.kpkpur.zsbddatagenerator.config.GenerationConfig.*;

@Component
public class ScreeningGenerator extends FakerGenerator<Screening> {

    public ScreeningGenerator(Faker faker) {
        super(faker);
    }

    public Screening generate(Room room, MovieVersion movieVersion, LocalDateTime screeningDateTime) {
        var isPremiere = movieVersion.getMovie().getPremiereDate().toLocalDate().equals(screeningDateTime.toLocalDate());
        var isDiscountable = !isPremiere;
        var price = isPremiere ? SCREENING_PREMIERE_PRICE : SCREENING_NORMAL_PRICES_LIST[faker.random().nextInt(2)]; //TODO: Set the price depending on the weekend?
        return new Screening(
                getNextId(),
                room,
                movieVersion.getId(),
                Timestamp.valueOf(screeningDateTime),
                isPremiere ? 1 : 0,
                isDiscountable ? 1 : 0,
                faker.number().numberBetween(MIN_SCREENING_ADS_LENGTH_IN_MINUTES, MAX_SCREENING_ADS_LENGTH_IN_MINUTES),
                price
        );
    }

    @Override
    public Screening generate() {
        throw new IllegalCallerException("Cannot generate Screenings without passing lists of Rooms and MovieVersions!");
    }

    @Override
    public List<Screening> generateMultiple(int count) {
        throw new IllegalCallerException("Cannot generate Screenings without passing lists of Rooms and MovieVersions!");
    }

    public List<Screening> generateMultiple(List<Room> rooms, List<MovieVersion> movieVersions) {
        return rooms.stream()
                .flatMap(room -> generateScreeningsForRoom(room, movieVersions).stream())
                .toList();
    }

    private List<Screening> generateScreeningsForRoom(Room room, List<MovieVersion> movieVersions) {
        var dateTimes = IntStream.range(0, SCREENING_NUMBER_OF_DAYS_PER_ROOM)
                .boxed()
                .map(dayNo -> Date.valueOf(NOW_DATE).toLocalDate().minusDays(dayNo)) //Screenings for n days in the past since today
                .flatMap(day -> Arrays.stream(SCREENING_DAILY_SCREENINGS_LIST) //n screenings per day
                        .boxed()
                        .map(screeningNo -> LocalDateTime.of(day, SCREENING_TIME_START) //first screening at hour
                                .plusHours(screeningNo * SCREENING_TIME_INTERVAL_IN_HOURS))) //add screening every n hours
                .toList();

        return dateTimes
                .stream()
                .map(dt -> generate(room, movieVersions.get(faker.random().nextInt(movieVersions.size() - 1)), dt))
                .toList();
    }
}
