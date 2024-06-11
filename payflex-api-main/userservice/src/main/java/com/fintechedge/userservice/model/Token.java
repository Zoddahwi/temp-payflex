package com.fintechedge.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(value = "tbl_tokens", schema = "newuser")
public class Token {
    @Id
    @Column("id")
    private UUID id;
    @Column("email")
    private String email;
    @Column("access_token")
    private String accessToken;
    @Column("expires_in")
    private Duration expiresIn;
    @Column("token_type")
    private String tokenType;
    @Column("refresh_token")
    private String refreshToken;
    @Column("scope")
    private String scope;





    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

}
