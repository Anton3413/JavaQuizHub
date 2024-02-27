package com.example.javaquizhub.security;

import com.example.javaquizhub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringWebSecurityConfigurer {

    private final UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       http
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
                       .defaultSuccessUrl("/")
                       .permitAll())
               .logout(logout ->logout
                       .logoutUrl("/logout")
                       .logoutSuccessUrl("/login")
                       .invalidateHttpSession(true)
                       .deleteCookies("JSESSIONID")
                       .permitAll())
                    .oauth2Login( oauth2->oauth2
                            .loginPage("/login"));
                          /*  .defaultSuccessUrl("/")
                            .userInfoEndpoint(userInfo-> userInfo
                                    .oidcUserService(oidcUserService())));*/

       return http.build();

    }

    /*private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService(){
         return userRequest ->{
            String email = userRequest.getIdToken().getClaim("email");
            UserDetails userDetails = userService.loadUserByUsername(email);


             DefaultOidcUser oidcUser = new DefaultOidcUser(userDetails.getAuthorities(), userRequest.getIdToken());
             return new DefaultOidcUser(null,null);
         }
    }*/


}
