package com.example.restaurantsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class EventAdapter extends ArrayAdapter<String> {
    Context context;
    List<String> date;
    List<String> description;
    List<String> title;
    int [] rImgs;

    EventAdapter (Context c, int[]imgs,List<String> date,List<String> title,List<String> description){
        super(c,R.layout.custom,R.id.address_textView_name,title);
        this.context=c;
        this.date=date;
        this.title=title;
        this.description=description;
        this.rImgs=imgs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View custom = layoutInflater.inflate(R.layout.custom,parent,false);

        ImageView images = custom.findViewById(R.id.address_image);
        TextView myTitle = custom.findViewById(R.id.address_textView_name);
        TextView myDescription = custom.findViewById(R.id.address_textView_fulladdress);
        TextView myDate= custom.findViewById(R.id.custom_textView_date);

        images.setImageResource(rImgs[position]);
        myTitle.setText(title.get(position));
        myDate.setText(date.get(position));
        myDescription.setText(description.get(position));

        custom.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(context, myDate.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        return custom;
    }
}