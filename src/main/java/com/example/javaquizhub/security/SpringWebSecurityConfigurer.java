package com.example.javaquizhub.security;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.Principal;

@Configuration
@EnableWebSecurity
public class SpringWebSecurityConfigurer {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

       http.authorizeHttpRequests((authorize)->authorize.anyRequest().authenticated())
               .formLogin(login ->login
                       .loginPage("/login")
                       .defaultSuccessUrl("/books")
                       .permitAll());
       return http.build();

    }

}
