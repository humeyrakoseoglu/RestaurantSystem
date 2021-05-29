package com.example.restaurantsystem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AddressAdapter extends ArrayAdapter<String> {
    Context context;
    List<String> name;
    List<String> address;


    public AddressAdapter(Context context, List<String> name, List<String> address) {
        super(context,R.layout.card_item_address,R.id.address_textView_name,name);
        this.context = context;
        this.name = name;
        this.address = address;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View custom = layoutInflater.inflate(R.layout.card_item_address,parent,false);

        TextView myName = custom.findViewById(R.id.address_textView_name);
        TextView myAddress = custom.findViewById(R.id.address_textView_fulladdress);

        myAddress.setText(name.get(position));
        myAddress.setText(address.get(position));
        custom.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        return custom;
    }


}
