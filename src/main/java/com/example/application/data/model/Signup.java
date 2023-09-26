package com.example.application.data.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.dom4j.tree.AbstractEntity;



public class Signup extends AbstractEntity {

    @NotEmpty
    private String firstName = "";
    @NotEmpty
    private String lastName = "";

    @NotEmpty
    private String username = "";
    @NotEmpty
    @Email
    private String email = "";

    @NotEmpty
    private String password = "";

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return username;
    }
    public void setUserName(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword(){return password;}

    public void setPassword(String password){this.password = password;}
}
