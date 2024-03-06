package com.example.javaquizhub.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;

public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = determineErrorMessage(exception);

        redirectStrategy.sendRedirect(request, response, "/login?error=" + errorMessage);
    }

    private String determineErrorMessage(AuthenticationException exception) {
        if (exception instanceof DisabledException) {
            return "This account is not activated. " +
                    "To complete the registration, follow the link in the letter that was sent to your email address. " +
                    "We also recommend checking the 'Spam' category.";
        } else if (exception instanceof BadCredentialsException) {
            return "Invalid username or password.";
        } else {
            return "Authentication failed: " + exception.getMessage();
        }
    }
}
