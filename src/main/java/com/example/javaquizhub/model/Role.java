package com.example.javaquizhub.model;



import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    USER,
    ADMIN,
    MODERATOR;

    public String getAuthority() {
        return name();
    }

}
