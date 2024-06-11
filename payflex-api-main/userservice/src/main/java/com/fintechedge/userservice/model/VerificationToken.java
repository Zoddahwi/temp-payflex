package com.fintechedge.userservice.model;

import com.fintechedge.userservice.model.User;
import lombok.*;
import reactor.core.publisher.Mono;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@Table(name = "tbl_verification_token")
public class VerificationToken {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Setter
    private String token;

    @Setter
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Setter
    private Date expiryDate;

    public VerificationToken() {
        super();
    }

    public VerificationToken(final String token, final User user){
        super();

        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    private Date calculateExpiryDate(final int expirationInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expirationInMinutes);
        return new Date(cal.getTime().getTime());
    }


    public void updateToken(final String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }
}
