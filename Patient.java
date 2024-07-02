package com.example.skind;

public class Patient {
    private int id;
    private String name;
    private String address;
    private String email;
    private String phonenumber;
    private int age;
    private String gender;

    // Constructor
    public Patient(int id, String name, String address, String email, String phonenumber, int age, String gender) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phonenumber = phonenumber;

        this.age = age;
        this.gender = gender;
    }

    // Getter and setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phonenumber;
    }

    public void setPhoneNumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
