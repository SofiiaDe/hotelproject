package com.epam.javacourse.hotel.model;

import java.io.Serializable;

public class User extends Entity implements Serializable {

    private static final long serialVersionUID = -1L;

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String country;
//    private Role role;
    private String role;


    public User() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

//    public Role getRole() {
//        return role;
//    }
//
//    public void setRole(Role role) {
//        this.role = role;
//    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

//    public static User createUser(String email) {
//        User user = new User();
//        user.setId(0);
//        user.setEmail(email);
//        return user;
//    }
}
