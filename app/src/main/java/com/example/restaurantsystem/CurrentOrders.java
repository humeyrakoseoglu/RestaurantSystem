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

public class CurrentOrders extends AppCompatActivity {
    private FirebaseAuth auth;

    ListView listView;
    CurrentOrderAdapter currentOrderAdapter;

    List<String> currentOrderDateList;
    List<String> currentOrderTimeList;
    List<String> currentOrderPriceList;
    List<String> currentOrderCardList;
    List<String> currentOrderAddressList;

    DatabaseReference databaseReference;

    int icon = R.drawable.current_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_orders);
        auth = FirebaseAuth.getInstance();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        listView =  findViewById(R.id.currentorders_listView);

        currentOrderPriceList = new ArrayList<>();
        currentOrderDateList =new ArrayList<>();
        currentOrderTimeList =new ArrayList<>();
        currentOrderCardList =new ArrayList<>();
        currentOrderAddressList =new ArrayList<>();

        String a=auth.getCurrentUser().getUid();

        //retrieve current order information from firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(a).child("Order List").child("Current Orders");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentOrderDateList.clear();
                currentOrderTimeList.clear();
                currentOrderPriceList.clear();
                currentOrderCardList.clear();
                currentOrderAddressList.clear();
                for(DataSnapshot currentOrderDataSnap : snapshot.getChildren()){
                    MyOrderModel myOrderModel = currentOrderDataSnap.getValue(MyOrderModel.class);

                    String date= myOrderModel.getDate();
                    currentOrderDateList.add(date);

                    String time= myOrderModel.getTime();
                    currentOrderTimeList.add(time);

                    String totalPrice= myOrderModel.getTotalPrice();
                    currentOrderPriceList.add(totalPrice);

                    String cardName= myOrderModel.getCardName();
                    currentOrderCardList.add(cardName);

                    String addressName= myOrderModel.getAddressName();
                    currentOrderAddressList.add(addressName);
                }

                currentOrderAdapter= new CurrentOrderAdapter(CurrentOrders.this,currentOrderDateList,currentOrderTimeList,icon,currentOrderPriceList,currentOrderCardList,currentOrderAddressList);
                listView.setAdapter(currentOrderAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}