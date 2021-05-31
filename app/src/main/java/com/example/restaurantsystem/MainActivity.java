package com.example.restaurantsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * @author Ceyda Elmas and Humeyra Koseoglu
 * @since 16.04.2021
 * 20180808037 - 20180808015
 */

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        imageView=findViewById(R.id.imageView_mainActivity);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        DatabaseReference childReference = databaseReference.child("BackgroundImage").child("image");

        childReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String message = snapshot.getValue(String.class);

                Picasso.get().load(message).into(imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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