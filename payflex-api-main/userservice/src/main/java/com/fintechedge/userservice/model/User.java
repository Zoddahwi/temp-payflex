package com.fintechedge.userservice.model;

import com.fintechedge.userservice.user.Role;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.util.ProxyUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.time.Instant;
import java.util.*;

@Table(name = "tbl_newuser")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @Column("id")
    private UUID id;
    @Column("email")
    private String email;
    @Column("token")
    private String token;
    @Column("password")
    private String password;
    @Column("role")
    private Role role;
    @Getter
    @Setter
    @Column("verified")
    private boolean verified;
    @Getter
    @Setter
    @Column("firstName")
    private String firstName;
    @Getter
    @Setter
    @Column("lastName")
    private String lastName;
    @Setter
    @Column("username")
    private String username;
    @Getter
    @Setter
    @Column("phoneNumber")
    private String phoneNumber;
    @Getter
    @Setter
    @Column("isPhoneNumberVerified")
    private boolean isPhoneNumberVerified;
    @Getter
    @Setter
    @Column("passwordResetToken")
    private String passwordResetToken;
    @Getter
    @Setter
    private List<String> roles;


    @CreatedDate
    private Instant createdAt;


    @LastModifiedDate
    private Instant updatedAt;

    public User(UUID user) {
        this.id = user;
    }


//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority(role.name()));
//    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role != null ? List.of(new SimpleGrantedAuthority(role.name())) : Collections.emptyList();
    }

    public User withToken(String token) {
        this.token = token;
        return this;
    }

    public void setIsPhoneNumberVerified(boolean phoneNumberVerified) {
        this.isPhoneNumberVerified = phoneNumberVerified;
    }

    public void setPhoneNumberVerified(boolean phoneNumberVerified) {
        this.isPhoneNumberVerified = phoneNumberVerified;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || ProxyUtils.getUserClass(this) != ProxyUtils.getUserClass(o))
            return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public final int hashCode() {
        return ProxyUtils.getUserClass(this).hashCode();
    }
}
