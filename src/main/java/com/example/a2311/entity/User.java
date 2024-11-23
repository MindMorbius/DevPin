package com.example.a2311.entity;


import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("user")
public class User {

    private String id;

    @Indexed //mongodb索引
    private String username;
    private String password;
    private String email;
    private String identity;

    public User(String username, String password, String email, String identity) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.identity = identity;
    }

    public String getPassword() {
        return password;
    }
}
