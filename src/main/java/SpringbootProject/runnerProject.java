package SpringbootProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class runnerProject {

	public static void main(String[] args) {
		SpringApplication.run(runnerProject.class, args);

	}

}
