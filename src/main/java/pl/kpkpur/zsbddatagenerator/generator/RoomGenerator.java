package pl.kpkpur.zsbddatagenerator.generator;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;
import pl.kpkpur.zsbddatagenerator.model.Room;

import java.util.HashSet;
import java.util.Set;

@Component
public class RoomGenerator extends FakerGenerator<Room> {

    public RoomGenerator(Faker faker) {
        super(faker);
    }

    Set<String> generatedNames = new HashSet<>();

    @Override
    public Room generate() {
        String name;
        do {
            name = faker.artist().name();
        } while(generatedNames.contains(name));
        return new Room(
                name,
                (long) faker.number().numberBetween(10, 20),
                (long) faker.number().numberBetween(10, 20),
                faker.company().name(),
                faker.number().numberBetween(0, 2),
                faker.number().numberBetween(0, 2)
        );
    }
}
