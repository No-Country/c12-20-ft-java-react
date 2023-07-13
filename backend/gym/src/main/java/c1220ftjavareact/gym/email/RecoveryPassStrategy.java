package c1220ftjavareact.gym.email;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecoveryPassStrategy implements TemplateStrategy {
    @Override
    public String buildTemplate() {
        return """
                <!DOCTYPE html>
                <html lang="es">
                <head>
                    <meta charset="UTF-8">
                    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                    <style>
                        body {
                            background-color: #f5f5f5;
                        }
                        \s
                        .container {
                            max-width: 500px;
                            padding: 20px;
                            border: 1px solid #ddd;
                            border-radius: 4px;
                            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                            color: #222227;
                            text-align: center;
                            margin: 0 auto;
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
                        .text-button {
                            text-align: center;
                        }
                        \s
                        div p .button {
                            display: inline-block;
                            text-decoration: none;
                            background-color: #337ab7;
                            color: #ffffff;
                            padding: 10px 20px;
                            border-radius: 5px;
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h1>Recuperación de contraseña</h1>
                        <p>Hola <span>%s</span>,</p>
                        <p>Has solicitado restablecer tu contraseña.</p>
                         <p>Haz clic en el siguiente botón para continuar:</p>
                        <p class="text-button">
                            <a href="%s" class="button">Restablecer contraseña</a>
                        </p>
                                
                        <p>
                            Si no solicitaste esta acción, puedes ignorar este correo. 
                            </br>
                            El link sera valido durante 1 hora.
                        </p>
                        <p>Atentamente, El equipo de PrimeFit</p>
                    </div>
                </body>
                </html>   
                """;
    }

    @Override
    public String replaceParameters(Object... values) {
        return String.format(buildTemplate(), values);
    }
}
