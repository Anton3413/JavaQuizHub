package com.example.javaquizhub.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import java.io.IOException;

public class CustomSimpleUrlAuthenticationHandler implements AuthenticationFailureHandler {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private String defaultFailureUrl = "/login";
    private boolean forwardToDestination = false;
    private boolean allowSessionCreation = true;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public CustomSimpleUrlAuthenticationHandler() {
    }

    public CustomSimpleUrlAuthenticationHandler(String defaultFailureUrl) {
        this.setDefaultFailureUrl(defaultFailureUrl);
    }

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (this.defaultFailureUrl == null) {
            if (this.logger.isTraceEnabled()) {
                this.logger.trace("Sending 401 Unauthorized error since no failure URL is set");
            } else {
                this.logger.debug("Sending 401 Unauthorized error");
            }

            response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        } else {
            this.saveException(request, exception);
            if (this.forwardToDestination) {
                this.logger.debug("Forwarding to " + this.defaultFailureUrl);
                request.getRequestDispatcher(this.defaultFailureUrl).forward(request, response);
            } else {
                this.redirectStrategy.sendRedirect(request, response, this.defaultFailureUrl);
            }

        }
    }

    protected final void saveException(HttpServletRequest request, AuthenticationException exception) {
        if (this.forwardToDestination) {
            request.setAttribute("SPRING_SECURITY_LAST_EXCEPTION", exception);
        } else {
            HttpSession session = request.getSession(true);
            if (session != null || this.allowSessionCreation) {
                request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION", exception);
            }
        }
    }

    public void setDefaultFailureUrl(String defaultFailureUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(defaultFailureUrl), () -> {
            return "'" + defaultFailureUrl + "' is not a valid redirect URL";
        });
        this.defaultFailureUrl = defaultFailureUrl;
    }

    protected boolean isUseForward() {
        return this.forwardToDestination;
    }

    public void setUseForward(boolean forwardToDestination) {
        this.forwardToDestination = forwardToDestination;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected RedirectStrategy getRedirectStrategy() {
        return this.redirectStrategy;
    }

    protected boolean isAllowSessionCreation() {
        return this.allowSessionCreation;
    }

    public void setAllowSessionCreation(boolean allowSessionCreation) {
        this.allowSessionCreation = allowSessionCreation;
    }
}

