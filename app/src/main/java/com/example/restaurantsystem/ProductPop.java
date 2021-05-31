package com.example.restaurantsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

// When click details button in Current Orders Page, show pop up Products Page
public class ProductPop extends Activity {
    private FirebaseAuth auth;

    ListView listView;
    ProductAdapter productAdapter;
    List<String> productsList;
    DatabaseReference databaseReference;
    String orderID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popwindow);

        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int height=dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.6));
        auth = FirebaseAuth.getInstance();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference();
        listView =  findViewById(R.id.productorders_listView);

        Intent intent = getIntent();
        orderID=intent.getStringExtra("orderID");

        productsList = new ArrayList<>();
        String a=auth.getCurrentUser().getUid();

        //retrieve products title from Current Order in Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(a).child("Order List").child("Current Orders").child(orderID).child("Products");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                productsList.clear();
                for(DataSnapshot orderProductDataSnap : snapshot.getChildren()){
                    MyGetProductModel myGetProductModel = orderProductDataSnap.getValue(MyGetProductModel.class);

                    String title= myGetProductModel.getTitle();
                    productsList.add(title);
                }
                productAdapter= new ProductAdapter(getApplicationContext(),productsList);
                listView.setAdapter(productAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}