package com.java.Linkly.model;

public record CreateUserCmd(String email, String password,
        String name, Role role) {
}
