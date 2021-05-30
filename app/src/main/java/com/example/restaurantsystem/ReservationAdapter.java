package com.example.restaurantsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ReservationAdapter  extends ArrayAdapter<String>  {
    Context context;
    String date;
    String time;
    String peopleNumber;
    private FirebaseAuth auth;
    Button reservationButton;


    public ReservationAdapter(Context context, String date, String time,String peopleNumber) {
        super(context,R.layout.activity_reservation,R.id.reservationTime);
        this.context = context;
        this.date = date;
        this.time = time;
        this.peopleNumber = peopleNumber;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View custom = layoutInflater.inflate(R.layout.activity_reservation,parent,false);

        EditText myDate = custom.findViewById(R.id.reservationDate);
        EditText myTime = custom.findViewById(R.id.reservationTime);
        EditText myPeopleNumber = custom.findViewById(R.id.reservationPeopleNumber);
        reservationButton = custom.findViewById(R.id.button_update);

        myDate.setText(date);
        myTime.setText(time);
        myPeopleNumber.setText(peopleNumber);

        return custom;
    }

}
