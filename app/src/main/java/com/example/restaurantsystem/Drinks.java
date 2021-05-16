package com.example.restaurantsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Drinks extends AppCompatActivity {
    DatabaseReference databaseReference;
    ListView listView;

    List<String> menuImageList;
    List<String> menuTitleList;
    List<String> menuPriceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        listView = findViewById(R.id.drinks_listView);

        menuTitleList =new ArrayList<>();
        menuPriceList =new ArrayList<>();
        menuImageList =new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("DrinkMenu");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                menuImageList.clear();
                menuPriceList.clear();
                menuTitleList.clear();

                for(DataSnapshot menuItemSnap : snapshot.getChildren()){
                    MyMenuItemModel myMenuItemModel = menuItemSnap.getValue(MyMenuItemModel.class);

                    String menuImage= myMenuItemModel.getImage();
                    menuImageList.add(menuImage);

                    String menuPrice= myMenuItemModel.getPrice();
                    menuPriceList.add(menuPrice);

                    String menuTitle= myMenuItemModel.getTitle();
                    menuTitleList.add(menuTitle);


                }

                MenuItemsAdapter menuItemsAdapter=new MenuItemsAdapter(getApplicationContext(), menuTitleList, menuImageList, menuPriceList);
                listView.setAdapter(menuItemsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}