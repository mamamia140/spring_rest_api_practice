package tr.gov.bilgem.restpractice;

import java.io.IOException;
import java.util.Base64;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Launches the application.
 * 
 * @author Serdar Serpen
 * @date Nov 3, 2023
 * @since 1.0.0
 */
@SpringBootApplication
@EnableScheduling
public class Main {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(Main.class, args);
		
	}

}
