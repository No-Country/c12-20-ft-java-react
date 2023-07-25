package c1220ftjavareact.gym.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Arrays;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SpringMailService implements MailService {
    private static final File[] NO_ATTACHMENTS = null;
    private final JavaMailSender sender;
    private TemplateStrategy strategy;

    /**
     * Configuracion del sender
     */
    @Value("${spring.mail.username}")
    private String from;

    /**
     * Envia un Email
     *
     * @param to          Email al que va dirigido
     * @param subject     Titulo/Asunto del Email
     * @param textMessage Template del cuerpo del email
     * @return Estado de envio del correo
     */
    @Override
    public Boolean send(final String to, final String subject, final String textMessage) {
        return this.send(to, subject, textMessage, NO_ATTACHMENTS);
    }


    /**
     * Envia un Email
     *
     * @param to          Email al que va dirigido
     * @param subject     Titulo/Asunto del Email
     * @param textMessage Template del cuerpo del email
     * @param attachments Archivos a enviar
     * @return Estado de envio del correo
     */
    @Override
    public Boolean send(final String to, final String subject, final String textMessage, File... attachments) {
        var send = false;
        Assert.hasLength(to, () -> "Verifica que los valores no sean nulos, to is: " + to);
        Assert.hasLength(subject, () -> "Verifica que los valores no sean nulos, subject is: " + subject);
        Assert.hasLength(textMessage, () -> "Verifica que los valores no sean nulos");

        final MimeMessage message = sender.createMimeMessage();
        try {
            final MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom(new InternetAddress(this.from));
            helper.setText(textMessage, true);

            if (attachments != null) preparingAttachment(Arrays.stream(attachments), helper);

            this.sender.send(message);
            send = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return send;
    }

    /**
     * Cambia el Template que se utilizara
     *
     * @param strategy Estrategia a utilizar para el template
     */
    @Override
    public void setTemplateStrategy(TemplateStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Ejecuta el template y se pasan los valores para remplazar en el template
     *
     * @param values Valores dentro del template
     * @return Template del Email
     */
    @Override
    public String executeTemplate(String... values) {
        return this.strategy.replaceParameters(values);
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
