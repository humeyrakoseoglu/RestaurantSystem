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

}
