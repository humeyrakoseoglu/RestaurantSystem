package com.example.restaurantsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PastEvents extends AppCompatActivity {
    private ActionBar actionBar;
    private ViewPager viewPager;
    DatabaseReference databaseReference;

    List<String> pastEventDateList;
    List<String> pastEventTitlelist;
    List<String> pastEventDescriptionlist;
    List<String> pastEventImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        actionBar = getSupportActionBar();

        viewPager = findViewById(R.id.events_viewPager);

        pastEventTitlelist=new ArrayList<>();
        pastEventDescriptionlist=new ArrayList<>();
        pastEventDateList =new ArrayList<>();
        pastEventImage = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("PastEvents");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pastEventDateList.clear();
                pastEventDescriptionlist.clear();
                pastEventTitlelist.clear();
                pastEventImage.clear();
                for(DataSnapshot pastEventSnap : snapshot.getChildren()){
                    MyPastEventModel myPastEventModel = pastEventSnap.getValue(MyPastEventModel.class);

                    String pastEventTitle= myPastEventModel.getTitle();
                    pastEventTitlelist.add(pastEventTitle);

                    String pastEventDescription= myPastEventModel.getDescription();
                    pastEventDescriptionlist.add(pastEventDescription);

                    String pastEventDate= myPastEventModel.getDate();
                    pastEventDateList.add(pastEventDate);

                    String image= myPastEventModel.getImage();
                    pastEventImage.add(image);
                }

                PastEventAdapter pastEventAdapter=new PastEventAdapter(getApplicationContext(),pastEventDateList,pastEventTitlelist,pastEventDescriptionlist,pastEventImage);
                viewPager.setAdapter(pastEventAdapter);
                viewPager.setPadding(100,0,100,0);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                String title = pastEventTitlelist.get(position);
                actionBar.setTitle(title);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}