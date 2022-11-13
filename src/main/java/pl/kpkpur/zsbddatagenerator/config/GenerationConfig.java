package pl.kpkpur.zsbddatagenerator.config;

public class GenerationConfig {

    public static final String GENERATION_DIRECTORY_PATH = "./generation_output";
    public static final String FILE_PATH = GENERATION_DIRECTORY_PATH + "/insert_generated_data.sql";

    public static final int NUMBER_OF_EMPLOYEES = 10_000;
    public static final int NUMBER_OF_ROOMS = 200;
    public static final int NUMBER_OF_MOVIES = 10_000;
    public static final int NUMBER_OF_CUSTOMERS = 100_000;

    public static final int NUMBER_OF_SCREENING_DAYS = 30;
    public static final int MAX_NUMBER_OF_SUBORDINATES = 3;
}
