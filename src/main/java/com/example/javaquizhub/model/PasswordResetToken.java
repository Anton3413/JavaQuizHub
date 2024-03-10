package com.example.javaquizhub.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Data
@Table(name = "password_reset_token")
public class PasswordResetToken {

    private static final int EXPIRATION_24_HOURS = 60 * 24;
    private static final int EXPIRATION_15_MINUTES = 15;
    private static final int EXPIRATION_1_HOUR = 60;
    private static final int EXPIRATION_1_MINUTE = 1;

    public PasswordResetToken(){
        super();
    }

    public PasswordResetToken(User user, String token){
        super();
        this.user = user;
        this.token = token;
        this.expiryDate = this.calculateExpiryDate(EXPIRATION_1_MINUTE);
    }

    public PasswordResetToken(final String token) {
        super();
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION_1_MINUTE);
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "token")
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    private LocalDateTime calculateExpiryDate(int expiryTimeInMinutes) {
        return LocalDateTime.now().plus(expiryTimeInMinutes, ChronoUnit.MINUTES);
    }
    public void updateToken(String newToken){
        this.token = newToken;
        this.expiryDate = calculateExpiryDate(1);
    }
}