package c1220ftjavareact.gym.events;

import c1220ftjavareact.gym.events.event.UserCreatedEvent;
<<<<<<< HEAD
import c1220ftjavareact.gym.events.event.RecoveryPasswordEvent;
=======
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
import c1220ftjavareact.gym.email.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSenderSubscriber {
    private final MailService service;

    /**
     * Envia un email de aviso que se ha creado el usuario
     *
     * @param event
     */
    @Async
    @EventListener
    public void userCreatedEevent(UserCreatedEvent event) {
        service.setTemplateStrategy(event.getStrategy());
        service.send(
                event.getEmail(),
                "Se ha creado su cuenta en PrimeFit",
                service.executeTemplate(event.getName() + " " + event.getLastName(), event.getEmail(), event.getPassword()
                )
        );
    }

    /**
     * Envia el email con el codigo para actualizar su contraseña olvidada
     *
     * @param event
     */
    @Async
    @EventListener
    public void forgotPassswordEvent(RecoveryPasswordEvent event) {
        this.service.setTemplateStrategy(event.getStrategy());
        service.send(
                event.getEmail(),
                "CAMBIO DE CONTRASEÑA",
                this.service.executeTemplate(
                        event.getFullName(),
                        "http://localhost:3300/password?code=" + event.getCode() + "&id=" + event.getId()
                )
        );
    }
}
