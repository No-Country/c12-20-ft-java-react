package c1220ftjavareact.gym.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@Component
@Slf4j
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(@NotNull HttpServletRequest httpServletRequest,
                         @NotNull HttpServletResponse httpServletResponse,
                         @NotNull AuthenticationException e)
            throws IOException, ServletException {
        log.error("Responding with unauthorized error. Message - {}", e.getMessage());
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Sorry, You're not authorized to access this resource.");
    }
}
