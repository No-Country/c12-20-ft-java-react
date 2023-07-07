package c1220ftjavareact.gym.security;

import c1220ftjavareact.gym.config.CorsConfig;
import c1220ftjavareact.gym.security.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationManager manager;
    private final JwtFilter jwtFilter;
    private final AuthenticationEntryPoint entryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        http.cors()
                .configurationSource(new CorsConfig().corsConfigurationSource());
        http.exceptionHandling().authenticationEntryPoint(entryPoint);

        http.authorizeRequests(configurer -> configurer
                .antMatchers(HttpMethod.POST, "/api/v1/customers").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/authentication").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/admins/create").permitAll()
                .antMatchers("/api/v1/passwords/**").permitAll()
                .anyRequest().authenticated()
        );

        http.authenticationManager(manager);

        http.addFilterBefore(this.jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
