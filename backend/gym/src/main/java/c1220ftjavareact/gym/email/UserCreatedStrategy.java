package c1220ftjavareact.gym.email;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCreatedStrategy implements TemplateStrategy {
    @Override
    public String buildTemplate() {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <style>
                        .container {
                                max-width: 500px;
                                margin: 0 auto;
                                padding: 20px;
                                background-color: #ffffff;
                                border: 1px solid #ddd;
                                border-radius: 4px;
                                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                                color: #222227;
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h1>%s espero se encuentre bien</h1>
                        </hr>
                        <p>Se ha creado su cuenta en PrimeFit</p>
                        <h3>Sus credenciales</h3>
                        <h4>Email: <strong>%s</strong></h4>
                        <h4>Password: <strong>%s</strong></h4>
                        <p>Al iniciar sesion con nuestro sistema puedes cambiar este contraseña</p>
                        </hr>
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
