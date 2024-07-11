package com.example.kucingkuapp.Activity.Database;

import com.google.firebase.database.ServerValue;

public class HelperClass {
    private String name;
    private String email;
    private String password;
    private Object timestamp; // Ensure private access modifiers for encapsulation

    public HelperClass() {
        // Default constructor required for calls to DataSnapshot.getValue(HelperClass.class)
    }

    public HelperClass(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.timestamp = ServerValue.TIMESTAMP; // Initialize the timestamp
    }

    // Getters and setters (optional based on your needs)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }
}
