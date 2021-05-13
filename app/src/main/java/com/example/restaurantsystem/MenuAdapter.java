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

public class MenuAdapter  extends ArrayAdapter<String> {
    Context context;
    List<String> title;
    List<String>image;

    public MenuAdapter(Context context, List<String> title, List<String> image) {
        super(context,R.layout.menucustom,R.id.menucustom_textView_Title,title);
        this.context = context;
        this.title = title;
        this.image = image;

    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View custom = layoutInflater.inflate(R.layout.menucustom,parent,false);

        ImageView images = custom.findViewById(R.id.menu_custom_image);
        TextView myTitle = custom.findViewById(R.id.menucustom_textView_Title);

        Picasso.get().load(image.get(position)).into(images);
        myTitle.setText(title.get(position));
        custom.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (position==0){
                Intent intent = new Intent(context,Dessert.class);
                context.startActivity(intent);}
                if (position==1){
                Intent intent = new Intent(context,Drinks.class);
                context.startActivity(intent);}
                if (position==2){
                Intent intent = new Intent(context,Food.class);
                context.startActivity(intent);}
            }
        });

        return custom;
    }
}
