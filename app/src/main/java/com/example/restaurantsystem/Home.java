package com.example.restaurantsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();

        BottomNavigationView bottomNavigationView=findViewById(R.id.main_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListner);
        getSupportFragmentManager().beginTransaction().replace(R.id.home_frame_layout, new FragmentHome()).commit();
        }

        BottomNavigationView.OnNavigationItemSelectedListener navListner = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectFlag = null;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        selectFlag = new FragmentHome();
                        break;
                    case R.id.nav_menu:
                        selectFlag = new FragmentMenu();
                        break;
                    case R.id.nav_cart:
                        selectFlag = new FragmentCart();
                        break;
                    case R.id.nav_chat:
                        selectFlag = new FragmentChat();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.home_frame_layout, selectFlag).commit();
                return true;
            }
        };

    @Override
    protected void onStart(){
        super.onStart();
      /*  TextView textViewUser=(TextView) findViewById(R.id.home_textView2);
        textViewUser.setText("Welcome, \n"+mAuth.getCurrentUser().getEmail());*/
    }

}