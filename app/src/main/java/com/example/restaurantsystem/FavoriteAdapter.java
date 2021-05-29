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
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteAdapter extends ArrayAdapter<String> {
    Context context;
    List<String> title;
    List<String>image;
    List<String>price;
    Button removeFavoriteButton;
    private FirebaseAuth mAuth;
    DatabaseReference favoritesListRef;


    public FavoriteAdapter(Context context, List<String> title, List<String> image,List<String> price) {
        super(context,R.layout.card_item_favorites,R.id.cart_textView_Title,title);
        this.context = context;
        this.title = title;
        this.image = image;
        this.price = price;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View custom = layoutInflater.inflate(R.layout.card_item_favorites,parent,false);

        mAuth = FirebaseAuth.getInstance();
        String a=mAuth.getCurrentUser().getUid();

        favoritesListRef= FirebaseDatabase.getInstance().getReference().child("Users").child(a)
                .child("Favorites List");

        ImageView images = custom.findViewById(R.id.bannerIv_carditem_favorites);
        TextView myTitle = custom.findViewById(R.id.titleTv_favorites);
        TextView myPrice = custom.findViewById(R.id.favorites_textView_price);
        removeFavoriteButton = custom.findViewById(R.id.remove_favorite_button);

        Picasso.get().load(image.get(position)).into(images);
        myTitle.setText(title.get(position));
        myPrice.setText(price.get(position));

        removeFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });

        return custom;
    }

    private void remove(int position) {
        String productName= title.get(position);
        favoritesListRef.child(productName).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getContext(),"Favorite product removed successfully",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
