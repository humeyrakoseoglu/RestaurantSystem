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

        reservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReservation();
            }
        });

        return custom;
    }

    public void addReservation(  ){
        String saveCurrentTime, saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        //final DatabaseReference reservationListRef = FirebaseDatabase.getInstance().getReference().child("CartList");
        final DatabaseReference reservationListRef = FirebaseDatabase.getInstance().getInstance().getReference().child("Reservation List");
        final HashMap<String, Object> reservationMap = new HashMap<>();
        reservationMap.put("date", date);
        reservationMap.put("time", time);
        reservationMap.put("peopleNumber", peopleNumber);
        reservationMap.put("creationDate", saveCurrentDate);
        reservationMap.put("creationTime", saveCurrentTime);


        String a=auth.getCurrentUser().getUid();

        String reservationtID = time;
        reservationListRef.child("user "+a)
                .child(reservationtID)
                .updateChildren(reservationMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context,"AddedToReservation",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
