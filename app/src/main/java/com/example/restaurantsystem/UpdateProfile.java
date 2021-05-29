package com.example.restaurantsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateProfile extends AppCompatActivity {
    private FirebaseAuth mAuth;
    String name,surname,phone,email,birthdate,password;
    TextView userName,userSurname,userPhone,userEmail,userBirthdate,userPassword;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        mAuth = FirebaseAuth.getInstance();

        reference= FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid());
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.child("Users").child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if(task.isSuccessful()){
                    User user = task.getResult().getValue(User.class);
                     userName=findViewById(R.id.update_PersonName);
                     userSurname=findViewById(R.id.update_surname);
                     userPhone=findViewById(R.id.update_phone);
                     userEmail=findViewById(R.id.update_email);
                     userBirthdate=findViewById(R.id.update_birthdate);
                     userPassword=findViewById(R.id.update_password);

                    name=userName.getText().toString();
                    surname=userSurname.getText().toString();
                    phone=userPhone.getText().toString();
                    email=userEmail.getText().toString();
                    birthdate=userBirthdate.getText().toString();
                    password=userPassword.getText().toString();


                    userName.setText(user.getName());
                    userSurname.setText(user.getSurname());
                    userEmail.setText(user.getEmail());
                    userBirthdate.setText(user.getBirthday());
                    userPhone.setText(user.getPhone());
                    userPassword.setText(user.getPassword());
                }else{
                    System.out.println(task.getException().getMessage());
                }
            }
        });



    }

    public void update(View view){

        if(isNameChanged()|| isSurnameChanged()||isEmailChanged()||isBirthdateChanged()||isPhoneChanged()||isPasswordChanged()){
            Toast.makeText(this,"Profile informations have been updated.",Toast.LENGTH_SHORT).show();
            
        }else{
            Toast.makeText(this,"Profile informations have not been updated.",Toast.LENGTH_SHORT).show();

        }
    }

    private boolean isPasswordChanged() {
        String password2=userPassword.getText().toString();
        if(!password.equals(password2)){
            reference.child("password").setValue(userPassword.getText().toString());
            password=password2;
            return true;
        }else{
            return false;
        }
    }

    private boolean isPhoneChanged() {
        String phone2=userPhone.getText().toString();
        if(!phone.equals(phone2)){
            reference.child("phone").setValue(userPhone.getText().toString());
            phone=phone2;
            return true;
        }else{
            return false;
        }
    }

    private boolean isBirthdateChanged() {
        String birthdate2=userBirthdate.getText().toString();
        if(!birthdate.equals(birthdate2)){
            reference.child("birthday").setValue(userBirthdate.getText().toString());
            birthdate=birthdate2;
            return true;
        }else{
            return false;
        }
    }

    private boolean isEmailChanged() {
        String email2=userEmail.getText().toString();
        if(!email.equals(email2)){
            reference.child("email").setValue(userEmail.getText().toString());
            email=email2;
            return true;
        }else{
            return false;
        }
    }

    private boolean isSurnameChanged() {
        String surname2=userSurname.getText().toString();
        if(!surname.equals(surname2)){
            reference.child("surname").setValue(userSurname.getText().toString());
            surname=surname2;
            return true;
        }else{
            return false;
        }
    }

    private boolean isNameChanged() {
        String name2=userName.getText().toString();
        if(!name.equals(name2)){
           reference.child("name").setValue(userName.getText().toString());
            name=name2;
            return true;
        }else{
            return false;
        }
    }
}