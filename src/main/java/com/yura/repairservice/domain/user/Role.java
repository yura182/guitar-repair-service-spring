package com.yura.repairservice.domain.user;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,
    MASTER,
    CLIENT;

    @Override
    public String getAuthority() {
        return name();
    }

    // TODO to string
}
