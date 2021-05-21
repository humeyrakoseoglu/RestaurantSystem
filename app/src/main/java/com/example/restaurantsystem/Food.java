package com.example.restaurantsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Food extends AppCompatActivity {
    DatabaseReference databaseReference;
    private FirebaseAuth auth;



    List<String> menuImageList;
    List<String> menuTitleList;
    List<String> menuPriceList;

    TextView productTitle, productPrice;
    ElegantNumberButton numberButton;
    ImageView productImage;
    Button cartButton;
    ListView listView;
    String productID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        listView = findViewById(R.id.food_listView);


        auth = FirebaseAuth.getInstance();


        productImage = (ImageView) findViewById(R.id.bannerIv_carditem_menu);
        productTitle = (TextView) findViewById(R.id.titleTv);
        cartButton = (Button) findViewById(R.id.menu_addtocart_btn);
        productPrice = (TextView) findViewById(R.id.custom_textView_price);


        /************************* Retrieve Details Firebase****************/
        retrieveFromFirebase();

        /*cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Drinks.this,"AddedToCart",Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private void retrieveFromFirebase(){
        menuTitleList = new ArrayList<>();
        menuPriceList = new ArrayList<>();
        menuImageList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("DishesMenu");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                menuImageList.clear();
                menuPriceList.clear();
                menuTitleList.clear();

                for (DataSnapshot menuItemSnap : snapshot.getChildren()) {
                    MyItemModel myMenuItemModel = menuItemSnap.getValue(MyItemModel.class);

                    String menuImage = myMenuItemModel.getImage();
                    menuImageList.add(menuImage);

                    String menuPrice = myMenuItemModel.getPrice();
                    menuPriceList.add(menuPrice);

                    String menuTitle = myMenuItemModel.getTitle();
                    menuTitleList.add(menuTitle);

                }

                MenuItemsAdapter menuItemsAdapter = new MenuItemsAdapter(getApplicationContext(), menuTitleList, menuImageList, menuPriceList);
                listView.setAdapter(menuItemsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

   /* private void addtoCart() {
        String saveCurrentTime, saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        //final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("CartList");

        final HashMap<String, Object> cartMap = new HashMap<>();

        cartMap.put("ptitle", productTitle.getText().toString());
        cartMap.put("price", productPrice.getText().toString());
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);
        cartMap.put("quantity", numberButton.getNumber());
        firestore.collection("Add").document(auth.getCurrentUser().getUid())
                .collection("CurrentUser").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(Drinks.this,"AddedToCart",Toast.LENGTH_SHORT).show();
            }
        });*/
}







