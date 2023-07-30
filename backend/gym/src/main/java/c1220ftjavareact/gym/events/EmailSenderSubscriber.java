package c1220ftjavareact.gym.events;

import c1220ftjavareact.gym.email.MailService;
import c1220ftjavareact.gym.events.event.RecoveryPasswordEvent;
import c1220ftjavareact.gym.events.event.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSenderSubscriber {
    private final MailService service;

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
     * Envia el email con el codigo para actualizar su contraseña
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
                        "http://localhost:5173/change-password/" + event.getId() + "?code=" + event.getCode()
                )
        );
    }
}
