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

        http.cors().configurationSource(new CorsConfig().corsConfigurationSource());

        http.exceptionHandling().authenticationEntryPoint(entryPoint);

        http.authorizeRequests(configurer -> configurer
                .antMatchers(HttpMethod.POST, "/api/v1/users/customers").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/users/customers/google").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/users/admins").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/users/authentication").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/users/authentication/google").permitAll()
                .antMatchers("/api/v1/passwords/**").permitAll()
                /// <-------------Testear sin pasar por login------------->
                .antMatchers("/api/v1/rooms/**").permitAll()
                .antMatchers("/api/v1/activities/**").permitAll()
                .antMatchers("/api/v1/sessions/**").permitAll()
                .antMatchers("/api/v1/subscriptions/**").permitAll()
                .antMatchers("/api/v1/payments/**").permitAll()
                .antMatchers("/api/v1/active/**").permitAll()
                /// <------------------------------------------------------>
                .antMatchers(HttpMethod.GET, "/api/v1/activities/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/sessions/**").permitAll()
                .antMatchers("/api/v1/active/**").permitAll()
                .antMatchers("/api/v1/users/employees/**").permitAll()
                .antMatchers("/api/v1/users/**").permitAll()
                .anyRequest().authenticated()
        );

        http.authenticationManager(manager);

        http.addFilterBefore(this.jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
