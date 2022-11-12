package pl.kpkpur.zsbddatagenerator.generator;

import com.github.javafaker.Faker;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class FakerGenerator<T> implements Generator<T> {
    protected final Faker faker;
    private Long idCounter = 0L;

    protected Long getNextId() {
        return ++idCounter;
    }

    @Override
    public List<T> generateMultiple(int count) {
        return Stream.generate(this::generate).limit(count).toList();
    }
}
