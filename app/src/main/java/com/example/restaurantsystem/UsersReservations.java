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

public class UsersReservations extends AppCompatActivity {

    UsersReservationAdapter usersReservationAdapter;

    List<String> reservationDateList;
    List<String> reservationTimeList;
    List<String> reservationPeopleNumber;
    int icon=R.drawable.reserved;

    private FirebaseAuth auth;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_reservations);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        listView = findViewById(R.id.userreservation_listView);
        reservationDateList =new ArrayList<>();
        reservationTimeList =new ArrayList<>();
        reservationPeopleNumber =new ArrayList<>();

        auth = FirebaseAuth.getInstance();

        String a=auth.getCurrentUser().getUid();

        //retrieve reservations information from User Reservation List in Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(a)
                .child("Reservation List");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reservationDateList.clear();
                reservationTimeList.clear();
                reservationPeopleNumber.clear();

                for(DataSnapshot reservationDataSnap : snapshot.getChildren()){
                    MyReservationModel myReservationModel = reservationDataSnap.getValue(MyReservationModel.class);

                    String date= myReservationModel.getDate();
                    reservationDateList.add(date);

                    String time= myReservationModel.getTime();
                    reservationTimeList.add(time);

                    String peopleNum= myReservationModel.getPeopleNumber();
                    reservationPeopleNumber.add(peopleNum);
                }

                usersReservationAdapter= new UsersReservationAdapter(UsersReservations.this,reservationDateList,reservationTimeList,reservationPeopleNumber,icon);
                listView.setAdapter(usersReservationAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}