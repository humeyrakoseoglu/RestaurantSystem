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

public class Favorite extends AppCompatActivity {

    FavoriteAdapter favoriteAdapter;

    List<String> favTitleList;
    List<String> favImagesList;
    List<String> favPriceList;


    private FirebaseAuth auth;

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        listView = findViewById(R.id.favorites_listView);
        favTitleList =new ArrayList<>();
        favImagesList =new ArrayList<>();
        favPriceList =new ArrayList<>();


        auth = FirebaseAuth.getInstance();

        String a=auth.getCurrentUser().getUid();

        //retrieve favorite products information from firebase
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(a)
                .child("Favorites List");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                favTitleList.clear();
                favImagesList.clear();
                favPriceList.clear();

                for(DataSnapshot favDataSnap : snapshot.getChildren()){
                    MyFavoritesModel myFavoritesModel = favDataSnap.getValue(MyFavoritesModel.class);

                    String title= myFavoritesModel.getTitle();
                    favTitleList.add(title);

                    String image= myFavoritesModel.getImage();
                    favImagesList.add(image);


                    String price= myFavoritesModel.getPrice();
                    favPriceList.add(price);


                }

                favoriteAdapter= new FavoriteAdapter(getApplicationContext(),favTitleList,favImagesList,favPriceList);
                listView.setAdapter(favoriteAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}