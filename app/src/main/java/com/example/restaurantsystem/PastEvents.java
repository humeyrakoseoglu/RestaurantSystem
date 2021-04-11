package com.example.restaurantsystem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import java.util.ArrayList;

public class PastEvents extends AppCompatActivity {
    private ActionBar actionBar;
    private ViewPager viewPager;
    private ArrayList<MyPastEventModel> modelArrayList;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        actionBar = getSupportActionBar();

        viewPager = findViewById(R.id.fragmenthome_viewPager);

        loadCards();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                String title = modelArrayList.get(position).getTitle();
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

    private void loadCards() {
        modelArrayList = new ArrayList<>();
        modelArrayList.add(new MyPastEventModel("Karsu Dönmez Recital!","We had the honor of listening to the world-famous Turkish artist Karsu Dönmez's recital at our restaurant.",
                "04/01/2021",R.drawable.karsu));
        modelArrayList.add(new MyPastEventModel("Fazıl Say Concert!","We had the honor of listening to the world-famous Turkish pianist Fazıl Say's concert in our restaurant.",
                "03/02/2021",R.drawable.fazilsay));
        modelArrayList.add(new MyPastEventModel("Mexican Food Days!","Great tastes from traditional Mexican cuisine were at our restaurant.",
                "02/01/2021",R.drawable.meksika_fajita));
        modelArrayList.add(new MyPastEventModel("Nordic Food Days","Great tastes from traditional Nordic cuisine were at our restaurant.",
                "01/02/2021",R.drawable.iskandinav));

        myAdapter = new MyAdapter(this,modelArrayList);
        viewPager.setAdapter(myAdapter);
        viewPager.setPadding(100,0,100,0);

    }



}