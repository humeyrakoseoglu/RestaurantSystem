package com.example.restaurantsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import java.util.List;

public class Food extends AppCompatActivity {
    DatabaseReference databaseReference;
    private FirebaseAuth auth;

    List<String> menuImageList;
    List<String> menuTitleList;
    List<String> menuPriceList;

    TextView productTitle, productPrice;
    ImageView productImage;
    Button cartButton;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        listView = findViewById(R.id.food_listView);

        auth = FirebaseAuth.getInstance();

        productImage = findViewById(R.id.bannerIv_carditem_menu);
        productTitle = findViewById(R.id.titleTv);
        cartButton = findViewById(R.id.menu_addtocart_btn);
        productPrice = findViewById(R.id.custom_textView_price);


        /************************* Retrieve Details Firebase****************/
        retrieveFromFirebase();
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
}