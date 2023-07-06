package c1220ftjavareact.gym.service.interfaces;

import c1220ftjavareact.gym.service.email.TemplateStrategy;

import java.io.File;
import java.util.Map;

public interface MailService {
    Boolean send(String to, String subject, String textMessage);

    Boolean send(String to, String subject, String textMessage, File... attachments);

    void setTemplateStrategy(TemplateStrategy strategy);

    String executeTemplate(String... values);
}
