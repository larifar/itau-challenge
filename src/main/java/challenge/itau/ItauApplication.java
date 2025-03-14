package challenge.itau;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.OffsetDateTime;

@SpringBootApplication
public class ItauApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItauApplication.class, args);
		System.out.println(OffsetDateTime.now());
	}

}
