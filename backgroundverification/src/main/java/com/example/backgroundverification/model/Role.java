package com.example.backgroundverification.model;

public enum Role {
    ROLE_USER("User"),
    ROLE_HR("HR"),
    ROLE_ADMIN("Admin");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

