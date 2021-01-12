package quince_it.pquince;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class PquinceApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(PquinceApplication.class, args);
	}

}
