package c1220ftjavareact.gym.events.event;

import c1220ftjavareact.gym.email.TemplateStrategy;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

@Getter
@EqualsAndHashCode
@ToString
public class RecoveryPasswordEvent extends ApplicationEvent {
    private final String id;
    private final String email;
    private final String fullName;
    private final String code;
    private final TemplateStrategy strategy;

    public RecoveryPasswordEvent(Object source, String id, String email, String fullName, String code, TemplateStrategy strategy) {
        super(source);
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.code = code;
        this.strategy = strategy;
    }
}
