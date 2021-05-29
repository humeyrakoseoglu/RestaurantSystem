package com.example.restaurantsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfile extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        mAuth = FirebaseAuth.getInstance();
        ImageButton settingsButton;
        settingsButton = findViewById(R.id.settings_imageButton);


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Users").child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    User user = task.getResult().getValue(User.class);
                    TextView userName=findViewById(R.id.profile_textView_userName);
                    TextView userPhone=findViewById(R.id.profile_textView_phone);
                    userName.setText(user.getName()+user.getSurname());
                    userPhone.setText(user.getPhone());
                }else{
                    System.out.println(task.getException().getMessage());
                }
            }
        });
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
                Intent intent = new Intent(getApplicationContext(), UserSettings.class);
                startActivity(intent);
            }
        });
    }

    public void goFavorites(View view){
        Intent intent= new Intent(this, Favorite.class);
        startActivity(intent);
    }

    public void goPastOrders(View view){
        Intent intent= new Intent(this, PastOrders.class);
        startActivity(intent);
    }

    public void goCurrentOrders(View view){
        Intent intent= new Intent(this, CurrentOrders.class);
        startActivity(intent);
    }

    public void goReservations(View view){
        Intent intent= new Intent(this, CurrentReservations.class);
        startActivity(intent);
    }

    public void goSettings(View view){
        Intent intent= new Intent(this, UserSettings.class);
        startActivity(intent);
    }


}
