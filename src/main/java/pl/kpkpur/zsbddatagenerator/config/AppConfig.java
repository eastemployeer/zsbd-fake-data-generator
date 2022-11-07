package pl.kpkpur.zsbddatagenerator.config;

import com.github.javafaker.Faker;
import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  @Bean
  public Faker faker() {
    return new Faker(Locale.ENGLISH);
  }
}
