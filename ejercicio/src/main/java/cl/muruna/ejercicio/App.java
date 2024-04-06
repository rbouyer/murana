package cl.muruna.ejercicio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"cl.muruna.ejercicio"})
@EnableJpaRepositories(basePackages = {"cl.muruna.ejercicio.repository"})
@EntityScan("cl.muruna.ejercicio.model")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
