package c1220ftjavareact.gym.config;

import c1220ftjavareact.gym.security.exception.ResourceNotFoundException;
import c1220ftjavareact.gym.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationBeansConfig {
    private final UserRepository repository;

    /**
     * Crea un Bean con una implementacion Anonima de UserDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService() throws RuntimeException {
        return email -> repository
                .findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                                "El email no se encuentra registrado",
                                "Revisar bien el email enviado, o buscar si el registro esta eliminado"
                        )
                );
    }

    /**
     * Creo un Bean de PasswordEncoder implmentando Argon2
     */
    @Primary
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(16, 32, 1, 2048, 2);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Configura el Bean de JavaMailSender
     */
    @Bean
    public JavaMailSenderImpl javaMailSender() {
        var sender = new JavaMailSenderImpl();
        sender.setHost("smtp.gmail.com");
        sender.setPort(587);
        //Pass: Primefit1234
        sender.setUsername("gymprimefitnes@gmail.com");
        sender.setPassword("pgwltsgzkgbonmmr");

        var props = sender.getJavaMailProperties();

        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.ssl.trust", "*");
        return sender;
    }

    @Bean
    public ApplicationEventMulticaster applicationEventMulticaster() {
        var simpleEventMulticaster = new SimpleApplicationEventMulticaster();
        simpleEventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return simpleEventMulticaster;
    }
}
