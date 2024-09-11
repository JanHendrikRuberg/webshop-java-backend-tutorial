package de.oncoding.webshop;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@OpenAPIDefinition(
		info = @Info(
				version = "0.01",
				title = "Webshop REST Services",
				description = "Beschreibung der Webshop REST API Services",
				contact = @Contact(name = "Webshop AG", email = "webshop@x.de")
		),
		servers = {
				@Server(description = "Localhost", url = "http://localhost:8080/api"),
				@Server(description = "Development", url = "webshoprest.x.dev")
		}
)

@SpringBootApplication
@EnableScheduling
public class WebshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebshopApplication.class, args);
	}

}
