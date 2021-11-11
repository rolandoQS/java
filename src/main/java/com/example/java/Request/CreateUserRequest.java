package com.example.java.Request;


import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
public class CreateUserRequest {


    private String name;

    private String email;

    private String password;

    private Collection<CreatePhoneRequest> phones;

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

    public Collection<CreatePhoneRequest> getPhones() {
        return phones;
    }

    public void setPhones(Collection<CreatePhoneRequest> phones) {
        this.phones = phones;
    }
}
