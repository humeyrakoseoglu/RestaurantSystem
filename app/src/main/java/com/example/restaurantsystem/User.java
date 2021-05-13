package com.example.restaurantsystem;

public class User {

    public String name, surname, email, phone, birthday, password;

    public User(){
    }

    public User(String name, String surname, String email, String phone, String birthday, String password){
        this.name=name;
        this.surname=surname;
        this.email=email;
        this.phone=phone;
        this.birthday=birthday;
        this.password=password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}