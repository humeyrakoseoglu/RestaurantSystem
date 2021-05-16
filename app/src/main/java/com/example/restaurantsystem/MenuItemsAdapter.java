package com.example.restaurantsystem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MenuItemsAdapter extends ArrayAdapter<String>{
    Context context;
    List<String> title;
    List<String>image;
    List<String>price;

    public MenuItemsAdapter(Context context, List<String> title, List<String> image, List<String> price) {
        super(context,R.layout.card_item_menu,R.id.bannerIv_carditem_menu,title);
        this.context = context;
        this.title = title;
        this.image = image;
        this.price = price;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View custom = layoutInflater.inflate(R.layout.card_item_menu,parent,false);

        ImageView images = custom.findViewById(R.id.bannerIv_carditem_menu);
        TextView myTitle = custom.findViewById(R.id.titleTv);
        TextView myprice = custom.findViewById(R.id.custom_textView_price);

        Picasso.get().load(image.get(position)).into(images);
        myTitle.setText(title.get(position));
        myprice.setText(price.get(position));

        return custom;
    }
}