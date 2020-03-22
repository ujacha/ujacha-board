package net.ujacha.board.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class UjachaBoardApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UjachaBoardApiApplication.class, args);
	}

}
