package quince_it.pquince;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableAsync
@EnableScheduling
public class PquinceApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(PquinceApplication.class, args);
	}

}
