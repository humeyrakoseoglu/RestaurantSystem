package com.example.restaurantsystem;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FragmentHome extends Fragment {
    private ActionBar actionBar;
    private ViewPager viewPager;
    private CampaignAdapter campaignAdapter;
    private EventAdapter eventAdapter;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    ListView listView;
    List<String> eventTitleList;
    List<String> eventDateList;
    List<String> eventDescriptionList;

    List<String> campaignTitleList;
    List<String> campaignDescriptionList;
    List<String> campaignImagesList;

    int[] eventIcons={R.drawable.ic_baseline_event_note_24,R.drawable.ic_baseline_event_note_24,R.drawable.ic_baseline_event_note_24};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mAuth = FirebaseAuth.getInstance();

        TextView tw = (TextView) view.findViewById(R.id.fragmentHome_textView_pastEvents);
        tw.setPaintFlags(tw.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
                Intent intent = new Intent(getActivity(), PastEvents.class);
                startActivity(intent);
            }

        });


        // Log Out Button
        TextView logout = (TextView) view.findViewById(R.id.fragmenthome_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });


        //Events Listview
        listView =  view.findViewById(R.id.fragmenthome_listView);
        eventTitleList =new ArrayList<>();
        eventDescriptionList =new ArrayList<>();
        eventDateList =new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Events");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eventTitleList.clear();
                eventDateList.clear();
                eventDescriptionList.clear();
                for(DataSnapshot eventsDataSnap : snapshot.getChildren()){
                    Events events= eventsDataSnap.getValue(Events.class);

                    String title= events.getTitle();
                    eventTitleList.add(title);

                    String date= events.getDate();
                    eventDateList.add(date);

                    String description= events.getDescription();
                    eventDescriptionList.add(description);
                }
                eventAdapter= new EventAdapter(getActivity(),eventIcons, eventDateList, eventTitleList, eventDescriptionList);
                listView.setAdapter(eventAdapter);
                viewPager.setPadding(100,0,100,0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Campaigns ViewPager Slider
        actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        viewPager = view.findViewById(R.id.events_viewPager);

        campaignDescriptionList=new ArrayList<>();
        campaignTitleList=new ArrayList<>();
        campaignImagesList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Campaign");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                campaignTitleList.clear();
                campaignDescriptionList.clear();
                campaignImagesList.clear();

                for(DataSnapshot campaignDataSnap : snapshot.getChildren()){
                    MyCampaignModel myCampaignModel= campaignDataSnap.getValue(MyCampaignModel.class);

                    String title= myCampaignModel.getTitle();
                    campaignTitleList.add(title);

                    String description= myCampaignModel.getDescription();
                    campaignDescriptionList.add(description);

                    String image= myCampaignModel.getImage();
                    campaignImagesList.add(image);
                }

                campaignAdapter= new CampaignAdapter(getActivity(),campaignTitleList,campaignDescriptionList, campaignImagesList);
                viewPager.setAdapter(campaignAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        return view;
    }

}