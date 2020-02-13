package com.yura.repair.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN("role.admin"),
    MASTER("role.master"),
    CLIENT("role.client");

    private String localeDescription;

    Role(String localeDescription) {
        this.localeDescription = localeDescription;
    }

    @Override
    public String getAuthority() {
        return name();
    }

    public String getLocaleDescription() {
        return localeDescription;
    }
}
