package com.example.restaurantsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UserSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
    }

    public void goCards(View view){
        Intent intent= new Intent(this, Cards.class);
        startActivity(intent);
    }

    public void goLocations(View view){
        Intent intent= new Intent(this, Addresses.class);
        startActivity(intent);
    }

    public void goUpdateProfile(View view){
        Intent intent= new Intent(this, UpdateProfile.class);
        startActivity(intent);
    }

}