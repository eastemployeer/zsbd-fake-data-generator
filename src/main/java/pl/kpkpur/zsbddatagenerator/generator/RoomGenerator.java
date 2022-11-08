package pl.kpkpur.zsbddatagenerator.generator;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;
import pl.kpkpur.zsbddatagenerator.model.Room;

@Component
public class RoomGenerator extends FakerGenerator<Room> {

    public RoomGenerator(Faker faker) {
        super(faker);
    }

    @Override
    public Room generate() {
        return new Room(
                faker.hipster().word(),
                (long) faker.number().numberBetween(10, 20),
                (long) faker.number().numberBetween(10, 20),
                faker.company().name(),
                faker.number().numberBetween(0, 2),
                faker.number().numberBetween(0, 2)
        );
    }
}
