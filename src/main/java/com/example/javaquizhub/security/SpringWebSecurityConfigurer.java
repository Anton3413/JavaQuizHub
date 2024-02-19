package com.example.javaquizhub.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SpringWebSecurityConfigurer {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
       http
               .csrf(csrf->csrf.disable())
               .authorizeHttpRequests(authorize -> authorize
                       .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                       .permitAll()
                       .requestMatchers("/book/{bookId}/start").authenticated()
                       .anyRequest().permitAll())
               //.httpBasic(Customizer.withDefaults());
               .formLogin(login ->login
                       .loginPage("/login")
                       .defaultSuccessUrl("/")
                       .permitAll())
               .logout(logout ->logout
                       .logoutUrl("/logout")
                       .logoutSuccessUrl("/login?logout")
                       .invalidateHttpSession(true)
                       .deleteCookies("JSESSIONID")
                       .permitAll());

       return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
