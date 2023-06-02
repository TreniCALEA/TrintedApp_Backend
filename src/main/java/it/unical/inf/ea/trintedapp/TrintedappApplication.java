package it.unical.inf.ea.trintedapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration(exclude = {
	org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
@SpringBootApplication
public class TrintedappApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrintedappApplication.class, args);
	}

}
