package com.example.demo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "Users")  // MongoDB collection name
public class User {

    @Id
    private String id;
    private String name;
    private String password;
    private String email;
    private long mobile;  // better to use long for phone numbers

    // --- Constructors ---
    public User() {
    }

    public User(String id, String name, String password, String email, long mobile) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.mobile = mobile;
    }

    // --- Getters & Setters ---
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getMobile() {
        return mobile;
    }

    public void setMobile(long mobile) {
        this.mobile = mobile;
    }

    // --- Optional: toString for debugging ---
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobile=" + mobile +
                '}';
    }
}
