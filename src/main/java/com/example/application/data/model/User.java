package com.example.application.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "users")
public class User{
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
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(nullable=false)
    private Set<Role> roles;
    @Column(nullable=false)
    private String hashedPassword = "";

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
    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {this.roles = roles;}
    public String getPassword(){return hashedPassword;}
    public void setPassword(String hashedPassword){this.hashedPassword = hashedPassword;}
}
