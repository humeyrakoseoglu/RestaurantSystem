package com.example.restaurantsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import java.util.List;

public class PastOrderAdapter  extends ArrayAdapter<String> {
    Context context;
    List<String> date;
    List<String> time;
    List<String> productsList;

    ImageView images;
    TextView myDate;
    TextView myTime;

    String createdDate,createdTime;

    int icon;

    public PastOrderAdapter(Context context, List<String> date, List<String> time,int icon) {
        super(context,R.layout.card_item_pastorders,R.id.pastorders_textView_date_cardItem,date);
        this.context = context;
        this.date = date;
        this.time = time;
        this.icon = icon;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View custom = layoutInflater.inflate(R.layout.card_item_pastorders,parent,false);

        images = custom.findViewById(R.id.pastorders_image_cardItem);
        myDate = custom.findViewById(R.id.pastorders_textView_date_cardItem);
        myTime = custom.findViewById(R.id.pastorders_textView_time_cardItem);

        createdDate = myDate.getText().toString();
        createdTime = myTime.getText().toString();

        images.setImageResource(icon);
        myDate.setText(date.get(position));
        myTime.setText(time.get(position));


        return custom;
    }

}
