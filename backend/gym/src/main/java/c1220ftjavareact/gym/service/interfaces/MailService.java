package c1220ftjavareact.gym.service.interfaces;

import java.io.File;

public interface MailService {
    Boolean send(String to, String subject, String textMessage);

    Boolean send(String to, String subject, String textMessage, File... attachments);
}
