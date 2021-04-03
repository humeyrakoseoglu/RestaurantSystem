package com.example.restaurantsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth= FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart(){
        super.onStart();
        TextView textViewUser=(TextView) findViewById(R.id.home_textView2);
        textViewUser.setText("Welcome, \n"+mAuth.getCurrentUser().getEmail());
    }

    public void logout(View view){
        mAuth.signOut();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}