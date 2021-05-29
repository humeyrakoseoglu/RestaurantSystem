package com.example.restaurantsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Addresses extends AppCompatActivity {
    private FirebaseAuth auth;
    EditText addressName, fullAddress;
    Button addAddressButton;
    String name, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresses);
        auth = FirebaseAuth.getInstance();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        addressName = findViewById(R.id.textView_addressName);
        fullAddress = findViewById(R.id.textView_fulladdress);
        addAddressButton = findViewById(R.id.add_address_button);

        addAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                while (addressName.getText().toString().equals("")||fullAddress.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"please fill in all fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                name= addressName.getText().toString();
                address= fullAddress.getText().toString();
                addAddress(name,address);
            }
        });

    }

    public void addAddress(String name, String address){

        final DatabaseReference addressListRef = FirebaseDatabase.getInstance().getInstance().getReference().child("Users");
        final HashMap<String, Object> addressMap = new HashMap<>();
        addressMap.put("name", name);
        addressMap.put("address", address);


        String a=auth.getCurrentUser().getUid();

        String addressID = "address "+name;

        addressListRef.child(a)
                .child("Addresses")
                .child(addressID)
                .updateChildren(addressMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"AddedToAddress",Toast.LENGTH_SHORT).show();
                    }
                });
    }

}