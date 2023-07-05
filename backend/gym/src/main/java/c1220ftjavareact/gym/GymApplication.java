package c1220ftjavareact.gym;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
@Slf4j
public class GymApplication {

    public static void main(String[] args) {
        log.info("Tiempo ahora mismo: {}", LocalDateTime.now());
        log.info("Tiempo en 24 horas: {}", LocalDateTime.now().plusHours(24));
        SpringApplication.run(GymApplication.class, args);
    }

}
