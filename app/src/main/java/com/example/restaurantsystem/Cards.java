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

public class Cards extends AppCompatActivity {
    private FirebaseAuth auth;
    EditText cardName, cardNumber;
    Button addCardButton;
    String name, number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);
        auth = FirebaseAuth.getInstance();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        cardName = findViewById(R.id.textView_cardName);
        cardNumber = findViewById(R.id.textView_cardNumber);
        addCardButton = findViewById(R.id.add_card_button);

        addCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                while (cardName.getText().toString().equals("")||cardNumber.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"please fill in all fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                    name= cardName.getText().toString();
                    number= cardNumber.getText().toString();
                    addCard(name,number);
            }
        });

    }

    public void addCard(String name, String number){

        final DatabaseReference cardListRef = FirebaseDatabase.getInstance().getInstance().getReference().child("Users");
        final HashMap<String, Object> cardMap = new HashMap<>();
        cardMap.put("name", name);
        cardMap.put("number", number);


        String a=auth.getCurrentUser().getUid();

        String cardID = "card "+name;

        cardListRef.child(a)
                .child("Cards")
                .child(cardID)
                .updateChildren(cardMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"AddedToCard",Toast.LENGTH_SHORT).show();
                    }
                });
    }

}