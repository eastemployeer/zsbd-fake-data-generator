package pl.kpkpur.zsbddatagenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.fastnate.generator.EntitySqlGenerator;
import org.fastnate.generator.context.GeneratorContext;
import org.fastnate.generator.dialect.OracleDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.kpkpur.zsbddatagenerator.generator.AddressGenerator;
import pl.kpkpur.zsbddatagenerator.generator.DishGenerator;
import pl.kpkpur.zsbddatagenerator.generator.EmployeeGenerator;
import pl.kpkpur.zsbddatagenerator.generator.EmploymentGenerator;
import pl.kpkpur.zsbddatagenerator.generator.IngredientGenerator;
import pl.kpkpur.zsbddatagenerator.generator.OrderedDishGenerator;
import pl.kpkpur.zsbddatagenerator.generator.ProductGenerator;
import pl.kpkpur.zsbddatagenerator.generator.RestaurantGenerator;
import pl.kpkpur.zsbddatagenerator.generator.RestaurantOrderGenerator;
import pl.kpkpur.zsbddatagenerator.generator.ReviewGenerator;
import pl.kpkpur.zsbddatagenerator.model.Address;
import pl.kpkpur.zsbddatagenerator.model.Dish;
import pl.kpkpur.zsbddatagenerator.model.Employee;
import pl.kpkpur.zsbddatagenerator.model.Employment;
import pl.kpkpur.zsbddatagenerator.model.ScreeningEmployee;
import pl.kpkpur.zsbddatagenerator.model.OrderedDish;
import pl.kpkpur.zsbddatagenerator.model.Product;
import pl.kpkpur.zsbddatagenerator.model.Restaurant;
import pl.kpkpur.zsbddatagenerator.model.MovieVersion;
import pl.kpkpur.zsbddatagenerator.model.Review;

@SpringBootApplication
public class ZsbdDataGeneratorApp implements CommandLineRunner {
  private static final String GENERATION_DIRECTORY_PATH = "./generation_output";
  private static final String FILE_PATH = GENERATION_DIRECTORY_PATH + "/insert_generated_data.sql";

  @Autowired AddressGenerator addressGenerator;
  @Autowired ProductGenerator productGenerator;
  @Autowired DishGenerator dishGenerator;
  @Autowired IngredientGenerator ingredientGenerator;
  @Autowired RestaurantGenerator restaurantGenerator;
  @Autowired ReviewGenerator reviewGenerator;
  @Autowired EmployeeGenerator employeeGenerator;
  @Autowired EmploymentGenerator employmentGenerator;
  @Autowired RestaurantOrderGenerator restaurantOrderGenerator;
  @Autowired OrderedDishGenerator orderedDishGenerator;

  public static void main(String[] args) {
    SpringApplication.run(ZsbdDataGeneratorApp.class, args);
  }

  @Override
  public void run(String... args) throws IOException {
    Files.createDirectories(Path.of(GENERATION_DIRECTORY_PATH));
    Files.deleteIfExists(Path.of(FILE_PATH));
    BufferedWriter fileWriter = new BufferedWriter(new FileWriter(FILE_PATH, true));
    GeneratorContext generatorContext = new GeneratorContext(new OracleDialect());
    try (EntitySqlGenerator sqlGenerator = new EntitySqlGenerator(generatorContext, fileWriter)) {
      fileWriter.write("SET DEFINE OFF;\n");
      fileWriter.write("ALTER SESSION SET cursor_sharing = force;\n");

      List<Product> products = productGenerator.generateMultiple(800);
      System.out.println("Generated products: " + products.size());
      sqlGenerator.write(products);

      List<Dish> dishes = dishGenerator.generateMultiple(400);
      System.out.println("Generated dishes: " + dishes.size());
      sqlGenerator.write(dishes);

      List<ScreeningEmployee> screeningEmployees = ingredientGenerator.generateMultiple(dishes, products);
      System.out.println("Generated ingredients: " + screeningEmployees.size());
      sqlGenerator.write(screeningEmployees);
      products = null;
      screeningEmployees = null;

      List<Address> restaurantAddresses = addressGenerator.generateMultiple(500);
      System.out.println("Generated restaurantAddresses: " + restaurantAddresses.size());
      sqlGenerator.write(restaurantAddresses);

      List<Restaurant> restaurants = restaurantGenerator.generateMultiple(restaurantAddresses);
      System.out.println("Generated restaurants: " + restaurants.size());
      sqlGenerator.write(restaurants);
      restaurantAddresses = null;

      List<Review> reviews = reviewGenerator.generateMultiple(restaurants);
      System.out.println("Generated reviews: " + reviews.size());
      sqlGenerator.write(reviews);

      List<Address> employeeAddresses = addressGenerator.generateMultiple(restaurants.size() * 40);
      System.out.println("Generated employeeAddresses: " + employeeAddresses.size());
      sqlGenerator.write(employeeAddresses);

      List<Employee> employees = employeeGenerator.generateMultiple(employeeAddresses);
      System.out.println("Generated employees: " + employees.size());
      sqlGenerator.write(employees);
      employeeAddresses = null;

      List<Employment> employments = employmentGenerator.generateMultiple(restaurants, employees);
      System.out.println("Generated employments: " + employments.size());
      sqlGenerator.write(employments);
      employees = null;

      List<MovieVersion> movieVersions =
          restaurantOrderGenerator.generateMultiple(restaurants, employments, reviews);
      System.out.println("Generated restaurantOrders: " + movieVersions.size());
      System.out.println(
          "Min tip: "
              + movieVersions.stream().mapToLong(MovieVersion::getTip).min().orElse(0));
      System.out.println(
          "Max tip: "
              + movieVersions.stream().mapToLong(MovieVersion::getTip).max().orElse(0));
      sqlGenerator.write(movieVersions);
      restaurants = null;
      reviews = null;
      employments = null;

      List<OrderedDish> orderedDishes =
          orderedDishGenerator.generateMultiple(movieVersions, dishes);
      System.out.println("Generated orderedDishes: " + orderedDishes.size());
      sqlGenerator.write(orderedDishes);
      movieVersions = null;
      dishes = null;
      orderedDishes = null;

      fileWriter.write("ALTER SESSION SET cursor_sharing = exact;\n");
      fileWriter.write("\nSET DEFINE ON;");
      fileWriter.write("\nCOMMIT;");
    }
  }
}
