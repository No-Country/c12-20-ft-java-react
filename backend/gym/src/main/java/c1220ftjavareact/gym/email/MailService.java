package c1220ftjavareact.gym.email;

import java.io.File;

public interface MailService {
    Boolean send(String to, String subject, String textMessage);

    Boolean send(String to, String subject, String textMessage, File... attachments);

    void setTemplateStrategy(TemplateStrategy strategy);

    String executeTemplate(String... values);
}
