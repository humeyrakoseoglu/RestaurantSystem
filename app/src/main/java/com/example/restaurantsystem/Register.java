package com.example.restaurantsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        imageView=findViewById(R.id.imageView_register);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        DatabaseReference childReference = databaseReference.child("BlurBackgroundImage").child("image");


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



    public void login(View view){
        Intent intent= new Intent(this,Login.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void createUser(View view){
        EditText editTextEmail = (EditText) findViewById(R.id.register_email);
        String email = editTextEmail.getText().toString();

        EditText editTextPassword = (EditText) findViewById(R.id.register_password);
        String password = editTextPassword.getText().toString();

        EditText editTextConfirmPassword = (EditText) findViewById(R.id.register_confirmpassword);
        String confirmPassword = editTextConfirmPassword.getText().toString();

        EditText editTextName = (EditText) findViewById(R.id.register_PersonName);
        String name = editTextName.getText().toString();

        EditText editTextSurname = (EditText) findViewById(R.id.register_surname);
        String surname = editTextSurname.getText().toString();

        EditText editTextPhone = (EditText) findViewById(R.id.register_phone);
        String phone = editTextPhone.getText().toString();

        EditText editTextBirthday = (EditText) findViewById(R.id.register_birthdate);
        String birthday = editTextBirthday.getText().toString();

        Intent intentlogin=new Intent(this,Login.class);
        progressBar.setVisibility(View.VISIBLE);
        if(!password.equals(confirmPassword)) {
            Toast.makeText(Register.this, "Your password and confirmation password do not match.", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                User user=new User(name,surname,email,phone,birthday,password);
                                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            startActivity(intentlogin);
                                            progressBar.setVisibility(View.VISIBLE);
                                        }else{
                                            Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });
                            }

                        }
                    });
        }


    }
}