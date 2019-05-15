package cl.aws.demo.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"cl.aws.demo"})
@EnableJpaRepositories("cl.aws.demo")
@EntityScan("cl.aws.demo")
public class DemoAppStartup extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(DemoAppStartup.class, args);
	}
}