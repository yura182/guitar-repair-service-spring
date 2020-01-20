package com.yura.repairservice.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.Collections;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    private Integer id;

    @NotEmpty(message = "Please enter name")
    @Pattern(regexp = "[a-zA-Zа-яА-Яієї']{2,25}", message = "Name should be at " +
            "least two characters long without numbers")
    private String name;

    @NotEmpty(message = "Please enter surname")
    @Pattern(regexp = "[a-zA-Zа-яА-Яієї']{2,25}", message = "Surname should be at " +
            "least two characters long without numbers")
    private String surname;

    @NotEmpty(message = "Please enter email")
    @Pattern(regexp = "(\\w{2,})@(\\w+\\.)([a-z]{2,4})", message = "Email is not correct")
    private String email;

    @NotEmpty(message = "Please enter password")
    @Pattern(regexp = "[A-Za-z0-9]{8,}", message = "Password must be longer than 8 characters")
    private String password;

    @NotEmpty(message = "Please enter phone number")
    @Pattern(regexp = "[0-9]{10}", message = "Format 0995005050")
    private String phone;

    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(getRole());
    }

    @Override
    public String getUsername() {
        return getEmail();
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
}
