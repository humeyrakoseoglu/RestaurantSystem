package com.example.restaurantsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Payment extends AppCompatActivity {

    private FirebaseAuth auth;
    Button payButton;

    Spinner spinnerCard;
    Spinner spinnerAddress;

    List<String> orderTitleList;
    List<String> orderPriceList;
    List<String> orderQuantityList;

    String total;
    String cardName;
    String addressName;

    TextView textViewCard;
    TextView textViewAddress;

    EditText cvc;
    EditText expirationDate;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private ArrayList<String> cardNameList = new ArrayList<>();
    private ArrayList<String> addressNameList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        spinnerCard = findViewById(R.id.spinner_card);
        spinnerAddress = findViewById(R.id.spinner_address);
        textViewCard = findViewById(R.id.textView_cards_payment);
        cvc = findViewById(R.id.editTextNumber_payment);
        expirationDate = findViewById(R.id.editTextDate_payment);
        textViewAddress = findViewById(R.id.textView_address_payment);
        payButton = findViewById(R.id.payment_button);
        auth = FirebaseAuth.getInstance();

        orderTitleList =new ArrayList<>();
        orderPriceList =new ArrayList<>();
        orderQuantityList =new ArrayList<>();

        showCardNameSpinner();
        showAddressNameSpinner();

        Intent intent = getIntent();
        total=intent.getStringExtra("totalPrice");
        payButton.setText("Pay  $"+total);

        auth = FirebaseAuth.getInstance();

        String a=auth.getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(a).child("Cart List");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderTitleList.clear();
                orderPriceList.clear();
                orderQuantityList.clear();

                for(DataSnapshot cartDataSnap : snapshot.getChildren()){
                    MyCartModel myCartModel = cartDataSnap.getValue(MyCartModel.class);

                    String title= myCartModel.getTitle();
                    orderTitleList.add(title);

                    String quantity= myCartModel.getQuantity();
                    orderQuantityList.add(quantity);

                    String price= myCartModel.getPrice();
                    orderPriceList.add(price);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                while (textViewCard.getText().toString().equals("Choose Card")||textViewAddress.getText().toString().equals("Choose Address")||cvc.getText().toString().equals("")||expirationDate.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"please fill in all fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                cardName= spinnerCard.getSelectedItem().toString();
                addressName= spinnerAddress.getSelectedItem().toString();
                addtoOrders();
                clearCart();
            }
        });
    }

    private void clearCart() {
        databaseReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Cart cleared successfully",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showAddressNameSpinner() {
        String a=auth.getCurrentUser().getUid();
        databaseReference.child("Users").child(a).child("Addresses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                addressNameList.clear();
                addressNameList.add("Choose Address");
                for(DataSnapshot item:snapshot.getChildren()){
                    addressNameList.add(item.child("name").getValue(String.class));
                }
                ArrayAdapter<String>arrayAdapter = new ArrayAdapter<>(Payment.this,R.layout.style_spinner,addressNameList);
                spinnerAddress.setAdapter(arrayAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showCardNameSpinner() {
        String a=auth.getCurrentUser().getUid();
        databaseReference.child("Users").child(a).child("Credit Cards").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cardNameList.clear();
                cardNameList.add("Choose Card");
                for(DataSnapshot item:snapshot.getChildren()){
                    cardNameList.add(item.child("name").getValue(String.class));
                }
                ArrayAdapter<String>arrayAdapter = new ArrayAdapter<>(Payment.this,R.layout.style_spinner,cardNameList);
                spinnerCard.setAdapter(arrayAdapter);
                //textView.setText(spinnerCard.getSelectedItem().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addtoOrders() {
        String saveCurrentTime, saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        String a=auth.getCurrentUser().getUid();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getInstance().getReference().child("Users").child(a).child("Order List");
        final HashMap<String, Object> productMap = new HashMap<>();
        final HashMap<String, Object> orderMap = new HashMap<>();
        for(int i=0;i<orderTitleList.size();i++) {
            productMap.put("title", orderTitleList.get(i));
            productMap.put("price", orderPriceList.get(i));
            productMap.put("quantity", orderQuantityList.get(i));

            String productID = orderTitleList.get(i);
            String orderID = "created on "+saveCurrentDate+" "+saveCurrentTime;
          cartListRef.child("Current Orders").child(orderID).child("Products").child(productID)
                .updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
    }
        orderMap.put("date",saveCurrentDate);
        orderMap.put("time",saveCurrentTime);
        orderMap.put("totalPrice",total);
        orderMap.put("cardName",cardName);
        orderMap.put("addressName",addressName);

        String orderID = "created on "+saveCurrentDate+" "+saveCurrentTime;
        cartListRef.child("Current Orders").child(orderID).updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(),"AddedToOrder",Toast.LENGTH_SHORT).show();
            }
        });

    }
}