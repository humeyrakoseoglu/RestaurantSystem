package com.example.restaurantsystem;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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

import java.util.ArrayList;


public class FragmentHome extends Fragment {
    private ActionBar actionBar;
    private ViewPager viewPager;
    private ArrayList<MyCampaignModel> modelArrayList;
    private CampaignAdapter campaignAdapter;
    private FirebaseAuth mAuth;

    ListView listView;
    String[] events = {"Krystian Zimerman Recital!","Asian Food Days!","Turkish Food Days!"};
    String[] description={"World famous pianist Krystian Zimerman is with us on 30th June.","Great tastes from traditional Asian cuisine are at our restaurant on July 17th.","Great tastes from traditional Turkish cuisine are at our restaurant on August 19th."};
    int[] images={R.drawable.ic_baseline_event_note_24,R.drawable.ic_baseline_event_note_24,R.drawable.ic_baseline_event_note_24};





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        TextView tw = (TextView) view.findViewById(R.id.fragmentHome_textView_pastEvents);
        tw.setPaintFlags(tw.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        mAuth = FirebaseAuth.getInstance();
        tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
                Intent intent = new Intent(getActivity(), PastEvents.class);
                startActivity(intent);
            }

        }
        );
        TextView logout = (TextView) view.findViewById(R.id.fragmenthome_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });


        listView =  view.findViewById(R.id.fragmenthome_listView);
        EventAdapter adapter = new EventAdapter(getActivity(),events,description,images);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    Toast.makeText(getActivity(),"June 17 2021",Toast.LENGTH_SHORT).show();
                }  if(position == 0){
                    Toast.makeText(getActivity(),"July 30 2021",Toast.LENGTH_SHORT).show();
                }  if(position == 0){
                    Toast.makeText(getActivity(),"August 10 2021",Toast.LENGTH_SHORT).show();
                }

            }
        });


      /*  ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1, events
        );

        listView.setAdapter(listViewAdapter);*/

        actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        viewPager = view.findViewById(R.id.fragmenthome_viewPager);

        loadCards();

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
    private void loadCards() {
        modelArrayList = new ArrayList<>();
        modelArrayList.add(new MyCampaignModel("Taurus Stars Campaign!","Complete 5 Taurus constellation, one of which is earned with each order, and get a 75% discount on your next order.",
                R.drawable.steak));
        modelArrayList.add(new MyCampaignModel("Birthday Campaign!","A Mushroom Truffle Burger is a gift for every user on their birthday.",
                R.drawable.burger_birthday));
        modelArrayList.add(new MyCampaignModel(
                "Visa Card Campaign!","Order from Taurus Steakhouse mobile application between May 1-30 and earn 10% VisaPuan for every order of 75 TL or more with Visa Card!",
                R.drawable.visa));
        modelArrayList.add(new MyCampaignModel("Big Sunday Sale!","In honor of the 35th anniversary of our restaurant, there is a 35% discount on orders of 150 TL and above every Sunday throughout 2021!",
                R.drawable.sundaysale));
        modelArrayList.add(new MyCampaignModel("Additional 10% Discount for Adidas!","With the 4-digit special code on the invoice of every order you make from our restaurant, we offer an additional 10% discount advantage to the big discount of up to 70% on single-use Adidas.",
                R.drawable.adidas));

        campaignAdapter = new CampaignAdapter(getContext(),modelArrayList);
        viewPager.setAdapter(campaignAdapter);
        viewPager.setPadding(100,0,100,0);

    }

    class EventAdapter extends ArrayAdapter<String>{
        Context context;
        String rTitle[];
        String rDescription[];
        int [] rImgs;
        EventAdapter (Context c, String []title, String[]description, int[]imgs){
            super(c,R.layout.custom,R.id.custom_textView_Title,title);
            this.context=c;
            this.rTitle=title;
            this.rDescription=description;
            this.rImgs=imgs;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) ((AppCompatActivity)getActivity()).getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View custom = layoutInflater.inflate(R.layout.custom,parent,false);
            ImageView images = custom.findViewById(R.id.custom_image);
            TextView myTitle = custom.findViewById(R.id.custom_textView_Title);
            TextView myDescription = custom.findViewById(R.id.custom_textView_SubTitle);

            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);
            return custom;
        }
    }

}