package com.example.restaurantsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Reservation extends AppCompatActivity {
    private static final String TAG = "";
    EditText dateformat;
    EditText timeformat;
    EditText peopleNumberformat;

    int year;
    int month;
    int day;
    int hour,tminute;

    String time,date,peopleNumber;

    TimePickerDialog picker;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private FirebaseAuth auth;

    Button makereservation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        dateformat = findViewById(R.id.reservationDate);
        timeformat = findViewById(R.id.reservationTime);
        peopleNumberformat = findViewById(R.id.reservationPeopleNumber);
        makereservation = findViewById(R.id.button_update);

        timeformat.setInputType(InputType.TYPE_NULL);
        dateformat.setInputType(InputType.TYPE_NULL);


        auth = FirebaseAuth.getInstance();

        dateformat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Reservation.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d(TAG,"onDateSet: mm/dd/yyy "+month+"/"+dayOfMonth+"/"+year);
                String date = dayOfMonth+"/"+month+"/"+year;
                dateformat.setText(date);
            }
        };
        timeformat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                picker = new TimePickerDialog(Reservation.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                timeformat.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                picker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                picker.show();
            }
        });
        makereservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                while (dateformat.getText().toString().equals("")||timeformat.getText().toString().equals("")||peopleNumberformat.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"please fill in all fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Integer.parseInt(peopleNumberformat.getText().toString())>6){
                    Toast.makeText(getApplicationContext(),"Our tables are for a maximum of 6 people.",Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    date= dateformat.getText().toString();
                    time= timeformat.getText().toString();
                    peopleNumber=peopleNumberformat.getText().toString();
                    addReservation(date,time,peopleNumber);
                }
            }
        });
    }
    public void addReservation(String date, String time, String peopleNumber){
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

        String reservationtID = "created on "+saveCurrentDate+" "+saveCurrentTime;

        reservationListRef.child("user "+a)
                .child(reservationtID)
                .updateChildren(reservationMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"AddedToReservation",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}