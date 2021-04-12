package com.example.restaurantsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
    }

    public void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null){
            Intent loginIntent = new Intent(this, Home.class);
            startActivity(loginIntent);
        }
    }

    public void login(View view){
        Intent intent= new Intent(this,Login.class);
        startActivity(intent);
    }

    public void register(View view){
        Intent intent= new Intent(this,Register.class);
        startActivity(intent);
    }




}