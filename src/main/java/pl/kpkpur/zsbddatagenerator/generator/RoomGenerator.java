package pl.kpkpur.zsbddatagenerator.generator;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;
import pl.kpkpur.zsbddatagenerator.model.Room;

import static pl.kpkpur.zsbddatagenerator.config.GenerationConfig.*;

@Component
public class RoomGenerator extends FakerGenerator<Room> {

    public RoomGenerator(Faker faker) {
        super(faker);
    }

    @Override
    public Room generate() {
        return new Room(
                "Room " + getNextId(),
                faker.number().numberBetween(MIN_ROOM_SEATS_IN_ROW, MAX_ROOM_SEATS_IN_ROW),
                faker.number().numberBetween(MIN_ROOM_ROWS, MAX_ROOM_ROWS),
                faker.company().name(),
                faker.number().numberBetween(0, 2),
                faker.number().numberBetween(0, 2)
        );
    }
}
