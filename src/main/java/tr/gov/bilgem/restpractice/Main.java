package tr.gov.bilgem.restpractice;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Launches the application.
 * 
 * @author Serdar Serpen
 * @date Nov 3, 2023
 * @since 1.0.0
 */
@SpringBootApplication
public class Main {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(Main.class, args);
		
	}

}
