package com.shahediqbal.springajaxformvalidation.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class Student {
    @NotEmpty(message = "Please enter first name.")
    private String firstName;
    @NotEmpty(message = "Please enter last name.")
    private String lastName;
    @NotEmpty(message = "Please enter email address.")
    @Email(message = "Please enter a valid email address.")
    private String email;

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
}
