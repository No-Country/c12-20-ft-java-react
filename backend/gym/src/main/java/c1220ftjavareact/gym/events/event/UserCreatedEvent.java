package c1220ftjavareact.gym.events.event;

import c1220ftjavareact.gym.email.TemplateStrategy;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

@Getter
@EqualsAndHashCode
@ToString
public final class UserCreatedEvent extends ApplicationEvent {
    private final String email;
    private final String name;
    private final String lastName;
    private final String password;
    private final TemplateStrategy strategy;


    public UserCreatedEvent(Object source, String email, String name, String lastName, String password, TemplateStrategy strategy) {
        super(source);

        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.strategy = strategy;
    }
}
