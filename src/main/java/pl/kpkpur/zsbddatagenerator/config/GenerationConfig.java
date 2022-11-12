package pl.kpkpur.zsbddatagenerator.config;

import org.springframework.context.annotation.Configuration;
import pl.kpkpur.zsbddatagenerator.model.enums.ScreeningEmployeeResponsibility;

import java.time.LocalTime;

import static pl.kpkpur.zsbddatagenerator.model.enums.ScreeningEmployeeResponsibility.CLEANER;
import static pl.kpkpur.zsbddatagenerator.model.enums.ScreeningEmployeeResponsibility.ROOM_CONTROLLER;

@Configuration
public class GenerationConfig {
    //DATE
    public static final String NOW_DATE = "2022-11-01";

    //GENERATION SIZE
    public static final int CUSTOMER_COUNT = 100_000;
    public static final int EMPLOYEE_COUNT = 1000;
    public static final int MOVIE_COUNT = 10000;

    // MOVIE_VERSION_COUNT = MOVIE_COUNT * RANDOM_FOR_EVERY_MOVIE(1 - 5)

    // REVIEW_COUNT = MOVIE_COUNT * RANDOM_FOR_EVERY_MOVIE (0 - 200)

    public static final int ROOM_COUNT = 100;

    // SCREENING_COUNT = ROOM_COUNT * SCREENING_NUMBER_OF_DAYS_PER_ROOM (30) * SCREENING_DAILY_SCREENINGS_LIST.SIZE() (3)

    // SCREENING_EMPLOYEE_COUNT = SCREENING_COUNT * SCREENING_RESPONSIBILITIES_LIST.SIZE() (3)

    // TICKET_COUNT = SCREENING_COUNT * RANDOM_FOR_EVERY_SCREENING (10 - 100)

    //CUSTOMER
    public static final String CUSTOMER_BIRTHDATE_START = "1960-01-01";
    public static final String CUSTOMER_BIRTHDATE_END = "2012-12-31";

    //EMPLOYEE
    public static final int MAX_EMPLOYEE_SUBORDINATES = 3;
    public static final String EMPLOYEE_EMPLOYMENT_DATE_START = "2010-01-01";
    public static final String EMPLOYEE_EMPLOYMENT_DATE_END = "2022-10-31";

    //MOVIE
    public static final int MIN_MOVIE_LENGTH_IN_MINUTES = 15;
    public static final int MAX_MOVIE_LENGTH_IN_MINUTES = 180;
    public static final String MOVIE_PREMIERE_DATE_START = "1960-01-01";
    public static final String MOVIE_PREMIERE_DATE_END = "2001-12-31";

    //MOVIE_VERSION
    public static final int MIN_MOVIE_VERSIONS_PER_MOVIE = 1;
    public static final int MAX_MOVIE_VERSIONS_PER_MOVIE = 5;

    //REVIEW
    public static final int MIN_REVIEW_DESCRIPTION_SENTENCES_NUMBER = 0;
    public static final int MAX_REVIEW_DESCRIPTION_SENTENCES_NUMBER = 10;
    public static final int MIN_REVIEW_LIKES_NUMBER = 0;
    public static final int MAX_REVIEW_LIKES_NUMBER = 10000;
    public static final int MIN_REVIEWS_PER_MOVIE = 0;
    public static final int MAX_REVIEWS_PER_MOVIE = 200; //Or less if number of Customers is less than set value

    //ROOM
    public static final int MIN_ROOM_SEATS_IN_ROW = 10;
    public static final int MAX_ROOM_SEATS_IN_ROW = 20;
    public static final int MIN_ROOM_ROWS = 10;
    public static final int MAX_ROOM_ROWS = 20;

    //SCREENING EMPLOYEE
    public static final ScreeningEmployeeResponsibility[] SCREENING_RESPONSIBILITIES_LIST = {CLEANER, CLEANER, ROOM_CONTROLLER};

    //SCREENING
    public static final double SCREENING_PREMIERE_PRICE = 29.99;
    public static final double[] SCREENING_NORMAL_PRICES_LIST = {14.99, 19.99, 22.99}; //MUST be 3 prices
    public static final int MIN_SCREENING_ADS_LENGTH_IN_MINUTES = 10;
    public static final int MAX_SCREENING_ADS_LENGTH_IN_MINUTES = 30;
    public static final int SCREENING_NUMBER_OF_DAYS_PER_ROOM = 30;
    public static final int[] SCREENING_DAILY_SCREENINGS_LIST = {0, 1, 2};
    public static final LocalTime SCREENING_TIME_START = LocalTime.NOON;
    public static final long SCREENING_TIME_INTERVAL_IN_HOURS = 3L;

    //TICKET
    public static final int MIN_TICKETS_PER_SCREENING = 10;
    public static final int MAX_TICKETS_PER_SCREENING = 100;


}