package com.example.restaurantsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.nfc.cardemulation.CardEmulation;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Cards extends AppCompatActivity {
    private FirebaseAuth auth;
    EditText cardName, cardNumber;
    Button addCardButton;
    String name, number;
    ListView listView;
    CardAdapter cardAdapter;
    List<String> cardNameList;
    List<String> cardNumberList;
    int icon = R.drawable.ic_baseline_credit_card_24;
    boolean b;

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

        listView =  findViewById(R.id.card_listView);
        cardNameList =new ArrayList<>();
        cardNumberList =new ArrayList<>();

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
                    b=true;
            }
        });

        if(b=true){
            String a=auth.getCurrentUser().getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(a).child("Credit Cards");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    cardNameList.clear();
                    cardNumberList.clear();

                    for(DataSnapshot cardDataSnap : snapshot.getChildren()){
                        MyCardModel myCardModel = cardDataSnap.getValue(MyCardModel.class);

                        String name= myCardModel.getName();
                        cardNameList.add(name);

                        String address= myCardModel.getNumber();
                        cardNumberList.add(address);

                    }
                    cardAdapter= new CardAdapter(getApplicationContext(),cardNameList,cardNumberList,icon);
                    listView.setAdapter(cardAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else{
            return;
        }
    }

    public void addCard(String name, String number){

        final DatabaseReference cardListRef = FirebaseDatabase.getInstance().getInstance().getReference().child("Users");
        final HashMap<String, Object> cardMap = new HashMap<>();
        cardMap.put("name", name);
        cardMap.put("number", number);


        String a=auth.getCurrentUser().getUid();

        String cardID = "card "+name;

        cardListRef.child(a)
                .child("Credit Cards")
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