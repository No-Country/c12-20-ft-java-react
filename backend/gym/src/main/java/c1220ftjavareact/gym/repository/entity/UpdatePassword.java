package c1220ftjavareact.gym.repository.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter(AccessLevel.PRIVATE)
@Entity
@Table(name = "update_password")
public class UpdatePassword {

    @Id
    private Long id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "enable", nullable = false)
    private boolean enable;

    @Column(name = "expiration_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm")
    private LocalDateTime expirationDate;

    @OneToOne
    @JoinColumn(name = "id_user")
    private User user;

    public void chageState() {
        this.setEnable(this.isEnable() ? false : true);
    }

    public void code(String code) {
        this.setCode(code);
    }


    public String editPasswordMessage(String fullname, String url) {
        var message = this.generatePasswordMessage();
        message.replace("[nombre]", fullname);
        message.replace("[url]", fullname);

        return message;
    }

    public String generatePasswordMessage() {
        var message = """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background-color: #f5f5f5;
                            color: #333;
                        }
                       \s
                        .container {
                            max-width: 500px;
                            margin: 0 auto;
                            padding: 20px;
                            background-color: #ffffff;
                            border: 1px solid #ddd;
                            border-radius: 4px;
                            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                        }
                       \s
                        h1 {
                            color: #333;
                            font-size: 24px;
                            margin-bottom: 20px;
                        }
                       \s
                        p {
                            margin-bottom: 15px;
                        }
                       \s
                        .button {
                            display: inline-block;
                            background-color: #337ab7;
                            color: #ffffff;
                            text-decoration: none;
                            padding: 10px 20px;
                            border-radius: 4px;
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h1>Recuperación de contraseña</h1>
                        <p>Hola <span>[nombre]</span>,</p>
                        <p>Has solicitado restablecer tu contraseña. Haz clic en el siguiente botón para continuar:</p>
                        <p>
                            <a href="[url]" class="button">Restablecer contraseña</a>
                        </p>
                        <p>Si no solicitaste esta acción, puedes ignorar este correo.</p>
                        <p>Atentamente,<br>El equipo de Gym Fit</p>
                    </div>
                </body>
                </html>   
                """;
        return message;

    }


}
