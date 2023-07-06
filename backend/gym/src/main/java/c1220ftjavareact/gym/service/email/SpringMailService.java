package c1220ftjavareact.gym.service;

import c1220ftjavareact.gym.service.interfaces.MailService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Arrays;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
@Getter
public class SpringMailService implements MailService {
    private static final File[] NO_ATTACHMENTS = null;
    private final JavaMailSender sender;

    /**
     * Configuracion del sender
     */
    @Value("${spring.mail.username}")
    private String from;

    @Override
    public Boolean send(final String to, final String subject, final String textMessage) {
        return this.send(to, subject, textMessage, NO_ATTACHMENTS);
    }

    @Override
    public Boolean send(final String to, final String subject, final String textMessage, File... attachments) {
        var send = false;
        Assert.hasLength(to, "email 'to' needed");
        Assert.hasLength(subject, "email 'subject' needed");
        Assert.hasLength(textMessage, "email 'text' needed");

        final MimeMessage message = sender.createMimeMessage();
        try {
            final MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom(getFrom());
            helper.setText(textMessage);

            if (attachments != null) preparingAttachment(Arrays.stream(attachments), helper);

            this.sender.send(message);
            send = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return send;
    }

    private void preparingAttachment(Stream<File> attachments, MimeMessageHelper helper) {
        attachments.forEach(attachment -> {
            try {
                FileSystemResource file = new FileSystemResource(attachment);
                helper.addAttachment(attachment.getName(), file);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });
    }

}
