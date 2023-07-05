package c1220ftjavareact.gym.security.filter;

import c1220ftjavareact.gym.domain.entity.User;
import c1220ftjavareact.gym.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsService userService;
    private final JwtService<UserDetails> jwtService;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        if(this.isNotRequiredFilter(authorizationHeader)){
            filterChain.doFilter(request, response);
            return;
        }
        final String jwt = authorizationHeader.substring(7);
        final String userEmail = jwtService.extractSubject(jwt);

        var user =
                (userEmail != null)
                        ? this.userService.loadUserByUsername(userEmail)
                        : User.builder().build();
        if(jwtService.isTokenValid(jwt, user) && SecurityContextHolder.getContext().getAuthentication() == null){
            var authUser = new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    user.getPassword(),
                    user.getAuthorities()
            );
            authUser.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authUser);
        }
        filterChain.doFilter(request, response);
    }

    private Boolean isNotRequiredFilter(String authHeader){
        return authHeader == null || authHeader.equals("") || !authHeader.startsWith("Bearer ");
    }
}
