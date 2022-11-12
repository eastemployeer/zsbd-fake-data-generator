package pl.kpkpur.zsbddatagenerator.generator;

import java.util.List;

public interface Generator<T> {
    T generate();
    List<T> generateMultiple(int count);
}
