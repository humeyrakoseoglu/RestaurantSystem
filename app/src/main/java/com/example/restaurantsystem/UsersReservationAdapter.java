package com.example.restaurantsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class UsersReservationAdapter extends ArrayAdapter<String> {
    Context context;
    List<String> date;
    List<String> time;
    List<String> peopleNumber;

    TextView myDate;
    TextView myTime;
    TextView myPeopleNumber;
    ImageView images;
    int icon;


    public UsersReservationAdapter(Context context, List<String> date, List<String> time, List<String> peopleNumber,int icon) {
        super(context,R.layout.card_item_reservations,R.id.card_reservationDate,date);
        this.context = context;
        this.date = date;
        this.time = time;
        this.peopleNumber = peopleNumber;
        this.icon = icon;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View custom = layoutInflater.inflate(R.layout.card_item_reservations,parent,false);

        images = custom.findViewById(R.id.cardimage_reservation);
        myDate = custom.findViewById(R.id.card_reservationDate);
        myTime = custom.findViewById(R.id.card_reservationtime);
        myPeopleNumber = custom.findViewById(R.id.carditem_peopleNum);

        images.setImageResource(icon);
        myDate.setText("Date: "+date.get(position));
        myTime.setText("Time: "+time.get(position));
        myPeopleNumber.setText("People Number: "+peopleNumber.get(position));

        return custom;
    }

}
