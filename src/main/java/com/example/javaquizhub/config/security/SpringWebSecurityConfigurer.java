package com.example.javaquizhub.config.security;

import com.example.javaquizhub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import com.example.javaquizhub.model.User;

import java.util.List;



@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringWebSecurityConfigurer {
    private final UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
               .anonymous(AbstractHttpConfigurer::disable)
               .authorizeHttpRequests(authorize -> authorize
                       .requestMatchers(PathRequest
                               .toStaticResources()
                               .atCommonLocations())
                       .permitAll()
                       .requestMatchers("/book/{bookId}/testResults")
                       .authenticated()
                       .anyRequest().permitAll())
               .formLogin(login ->login
                       .loginPage("/login")
                       .failureHandler(simpleUrlAuthenticationFailureHandler())
                       .defaultSuccessUrl("/")
                       .permitAll())
                        .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(oidcUserService())))
                .build();

    }

    SimpleUrlAuthenticationFailureHandler simpleUrlAuthenticationFailureHandler(){
       SimpleUrlAuthenticationFailureHandler handler = new SimpleUrlAuthenticationFailureHandler();
       handler.setDefaultFailureUrl("/login?error");
       handler.setAllowSessionCreation(true);
       return handler;
    }

    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService(){
         return userRequest ->{

             String email = userRequest.getIdToken().getEmail();

             User user = userService.findByUsername(email);

             if(user==null){
                user =  userService.registerOauthUser(email);
             }

             return new DefaultOidcUser(List.of(),userRequest.getIdToken());
         };
    }
}
