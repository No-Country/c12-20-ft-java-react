package c1220ftjavareact.gym;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDateTime;

/*plan = ENUM('YEARLY', 'MONTHLY', 'WEEKLY') */
@SpringBootApplication
public class GymApplication {

    public static void main(String[] args) {
        SpringApplication.run(GymApplication.class, args);
    }

}
