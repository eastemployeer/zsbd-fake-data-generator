package pl.kpkpur.zsbddatagenerator.generator;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;
import pl.kpkpur.zsbddatagenerator.model.Ticket;

@Component
public class TicketGenerator extends FakerGenerator<Ticket> {

    public TicketGenerator(Faker faker) {
        super(faker);
    }

    @Override
    public Ticket generate() {
        return new Ticket();
    }
}
