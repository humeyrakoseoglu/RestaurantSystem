package com.example.restaurantsystem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter  extends ArrayAdapter<String> {
    Context context;
    List<String> title;
    List<String>image;
    List<String>price;
    List<String>quantity;


    public CartAdapter(Context context, List<String> title, List<String> image,List<String> price,List<String> quantity) {
        super(context,R.layout.card_item_cart,R.id.cart_textView_Title,title);
        this.context = context;
        this.title = title;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View custom = layoutInflater.inflate(R.layout.card_item_cart,parent,false);

        ImageView images = custom.findViewById(R.id.cart_custom_image);
        TextView myTitle = custom.findViewById(R.id.cart_textView_Title);
        TextView myQuantiy = custom.findViewById(R.id.quantity_textView);
        TextView myPrice = custom.findViewById(R.id.cart_textView_price);

        Picasso.get().load(image.get(position)).into(images);
        myTitle.setText(title.get(position));
        myPrice.setText(price.get(position));
        myQuantiy.setText(quantity.get(position));

        return custom;
    }

}