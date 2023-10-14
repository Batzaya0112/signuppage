package com.example.application.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "userssignup")
public class Signup{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable=false)
    private String firstName = "";
    @Column(nullable=false)
    private String lastName = "";
    @Column(nullable=false)
    private String username = "";
    @Column(nullable=false)
    private String email = "";
    @Column(nullable=false)
    private String password = "";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
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
