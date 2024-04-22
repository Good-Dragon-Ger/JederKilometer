package de.gooddragon.jederkilometer.adapter.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecuConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity chainBuilder) throws Exception {
        chainBuilder.authorizeHttpRequests(
                        configurer -> configurer.requestMatchers("/", "/login", "/oauth2/authorization/**", "/css/**", "/img/**", "/js/**").permitAll()
                                .anyRequest().authenticated()
                )
                .csrf(c -> c.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .logout(l -> l.logoutSuccessUrl("/").permitAll())
                .oauth2Login(Customizer.withDefaults());
        return chainBuilder.build();
    }

}
