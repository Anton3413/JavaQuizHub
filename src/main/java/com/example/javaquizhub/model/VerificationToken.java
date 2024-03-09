package com.example.javaquizhub.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@Entity
@Data
@Table(name = "verification_token")
public class VerificationToken {
    private static final int EXPIRATION_24_HOURS = 60 * 24;

    private static final int EXPIRATION_15_MINUTES = 15;

    private static final int EXPIRATION_1_HOUR = 60;

    private static final int EXPIRATION_1_MINUTE = 1;

    public VerificationToken(){
        this.expiryDate = calculateExpiryDate(EXPIRATION_1_MINUTE);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "verification_token_id_seq")
    @SequenceGenerator(name = "verification_token_id_seq", sequenceName = "verification_token_id_seq", allocationSize = 1)
    private Integer id;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private LocalDateTime expiryDate;

    private LocalDateTime calculateExpiryDate(int expiryTimeInMinutes) {
        return LocalDateTime.now().plus(expiryTimeInMinutes, ChronoUnit.MINUTES);
    }
    public void updateToken(String newToken){
        this.token = newToken;
        this.expiryDate = calculateExpiryDate(1);
    }

}
