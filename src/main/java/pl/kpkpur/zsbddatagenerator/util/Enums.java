package pl.kpkpur.zsbddatagenerator.util;

import java.util.Random;

public final class Enums {
    private static final Random random = new Random();

    private Enums() {}

    public static <T extends Enum<?>> T getRandomValue(Class<T> enumClass) {
        T[] enumConstants = enumClass.getEnumConstants();

        return enumConstants[random.nextInt(enumConstants.length)];
    }
}
