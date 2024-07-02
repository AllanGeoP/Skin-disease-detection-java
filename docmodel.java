package com.example.skind;

public class docmodel {
    String id,name,email,phone,specialization,password;

    public docmodel(String id, String name, String email, String phone, String specialization, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.specialization = specialization;
        this.password=password;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getSpecialization() { return specialization;}

    public String getPassword() {
        return password;
    }
}

