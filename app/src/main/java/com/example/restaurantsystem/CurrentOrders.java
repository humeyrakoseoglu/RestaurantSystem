package com.example.restaurantsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CurrentOrders extends AppCompatActivity {
    private FirebaseAuth auth;

    ListView listView;
    CurrentOrderAdapter currentOrderAdapter;

    List<String> currentOrderDateList;
    List<String> currentOrderTimeList;
    List<String> currentOrderPriceList;
    List<String> productsList;

    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    int icon = R.drawable.current_order;
    String total="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_orders);
        auth = FirebaseAuth.getInstance();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseDatabase firebaseDatabase2 = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference2 = firebaseDatabase2.getReference();


        listView =  findViewById(R.id.currentorders_listView);


        currentOrderPriceList = new ArrayList<>();
        currentOrderDateList =new ArrayList<>();
        currentOrderTimeList =new ArrayList<>();
        productsList =new ArrayList<>();

        String a=auth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(a).child("Order List").child("Current Orders");
        databaseReference2 = firebaseDatabase2.getInstance().getReference("Users").child(a).child("Order List").child("Current Orders");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentOrderDateList.clear();
                currentOrderTimeList.clear();
                currentOrderPriceList.clear();
                for(DataSnapshot currentOrderDataSnap : snapshot.getChildren()){
                    MyOrderModel myOrderModel = currentOrderDataSnap.getValue(MyOrderModel.class);

                    String date= myOrderModel.getDate();
                    currentOrderDateList.add(date);

                    String time= myOrderModel.getTime();
                    currentOrderTimeList.add(time);

                    String totalPrice= myOrderModel.getTotalPrice();
                    currentOrderPriceList.add(totalPrice);
                }

         /*       for (int i =0;i<currentOrderDateList.size();i++){
                    String orderID = "created on "+currentOrderDateList.get(i)+" "+currentOrderTimeList.get(i);
                    databaseReference2.child(orderID).child("Products");
                    databaseReference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot currentOrderProductDataSnap : snapshot.getChildren()){
                                MyGetProductModel myGetProductModel = currentOrderProductDataSnap.getValue(MyGetProductModel.class);
                                String title= myGetProductModel.getTitle();
                                productsList.add(title);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }*/
                currentOrderAdapter= new CurrentOrderAdapter(getApplicationContext(),currentOrderDateList,currentOrderTimeList,productsList,icon,currentOrderPriceList);
                listView.setAdapter(currentOrderAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


       /* databaseReference2=FirebaseDatabase.getInstance().getReference("Users").child(a).child("Order List").child("Current Orders").child("Products");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productsList.clear();
                for(DataSnapshot currentOrderDataSnap : snapshot.getChildren()){
                    MyOrderModel myOrderModel = currentOrderDataSnap.getValue(MyOrderModel.class);

                    String title= myOrderModel.getTitle();
                    productsList.add(title);

                }
                currentOrderAdapter= new CurrentOrderAdapter(getApplicationContext(),productsList);
                listView.setAdapter(currentOrderAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }
}