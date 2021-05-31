package com.example.restaurantsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PastOrders extends AppCompatActivity {
    private FirebaseAuth auth;

    ListView listView;
    PastOrderAdapter pastOrderAdapter;
    List<String> pastOrderDateList;
    List<String> pastOrderTimeList;
    List<String> pastOrderPriceList;
    List<String> pastOrderCardList;
    List<String> pastOrderAddressList;

    DatabaseReference databaseReference;

    int icon = R.drawable.past_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_orders);
        auth = FirebaseAuth.getInstance();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        listView =  findViewById(R.id.pastorders_listView);

        pastOrderDateList =new ArrayList<>();
        pastOrderTimeList =new ArrayList<>();
        pastOrderPriceList = new ArrayList<>();
        pastOrderCardList = new ArrayList<>();
        pastOrderAddressList = new ArrayList<>();

        String a=auth.getCurrentUser().getUid();

        // retrieve past orders information from Past Orders in firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(a).child("Order List").child("Past Orders");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pastOrderDateList.clear();
                pastOrderTimeList.clear();
                pastOrderPriceList.clear();
                pastOrderCardList.clear();
                pastOrderAddressList.clear();
                for(DataSnapshot currentOrderDataSnap : snapshot.getChildren()){
                    MyOrderModel myOrderModel = currentOrderDataSnap.getValue(MyOrderModel.class);

                    String date= myOrderModel.getDate();
                    pastOrderDateList.add(date);

                    String time= myOrderModel.getTime();
                    pastOrderTimeList.add(time);

                    String totalPrice= myOrderModel.getTotalPrice();
                    pastOrderPriceList.add(totalPrice);

                    String cardName= myOrderModel.getCardName();
                    pastOrderCardList.add(cardName);

                    String addressName= myOrderModel.getAddressName();
                    pastOrderAddressList.add(addressName);
                }
                pastOrderAdapter= new PastOrderAdapter(getApplicationContext(),pastOrderDateList,pastOrderTimeList,pastOrderPriceList,icon,pastOrderCardList,pastOrderAddressList);
                listView.setAdapter(pastOrderAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}