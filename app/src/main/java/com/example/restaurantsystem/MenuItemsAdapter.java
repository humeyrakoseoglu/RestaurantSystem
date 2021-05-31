package com.example.restaurantsystem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MenuItemsAdapter extends ArrayAdapter<String>{
    Context context;
    List<String> title;
    List<String>image;
    List<String>price;
    List<String>countlist = new ArrayList<>();
    List<Integer>countlist2 = new ArrayList<>();

    TextView productTitle, productPrice;
    ImageView productImage;
    Button cartButton;
    Button addFavoritesButton;
    ElegantNumberButton elegantNumberButton;


    private FirebaseAuth auth;



    public MenuItemsAdapter(Context context, List<String> title, List<String> image, List<String> price) {
        super(context,R.layout.card_item_menu,R.id.bannerIv_carditem_menu,title);
        this.context = context;
        this.title = title;
        this.image = image;
        this.price = price;
    }

    @Override
    public int getPosition(@Nullable String item) {
        return super.getPosition(item);
    }
    @Override
    public int getCount() {
        return super.getCount();
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View custom = layoutInflater.inflate(R.layout.card_item_menu, parent, false);

        productImage = custom.findViewById(R.id.bannerIv_carditem_menu);
        productTitle = custom.findViewById(R.id.titleTv);
        productPrice = custom.findViewById(R.id.custom_textView_price);
        cartButton = custom.findViewById(R.id.menu_addtocart_btn);
        elegantNumberButton = custom.findViewById(R.id.number_button);
        addFavoritesButton = custom.findViewById(R.id.add_favorite_button);
        countlist.add(elegantNumberButton.getNumber());

        auth = FirebaseAuth.getInstance();

        Picasso.get().load(image.get(position)).into(productImage);
        productTitle.setText(title.get(position));
        productPrice.setText(price.get(position));

        countlist2.add(Integer.parseInt(elegantNumberButton.getNumber()));

        elegantNumberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                Log.d(TAG, String.format("oldValue: %d   newValue: %d", oldValue, newValue));
                countlist.set(position,String.valueOf(newValue));
            }
        });

            cartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addtoCart(position, countlist);
                }
            });

            addFavoritesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addtoFavorites(position);
                }
            });

        return custom;
    }

    private void addtoFavorites(int position) {

        String a=auth.getCurrentUser().getUid();
        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getInstance().getReference().child("Users").child(a)
               .child("Favorites List");
        final HashMap<String, Object> favoritesMap = new HashMap<>();
        favoritesMap.put("title", title.get(position));
        favoritesMap.put("image", image.get(position));
        favoritesMap.put("price", price.get(position));
        String favoritesId = title.get(position);
        cartListRef.child(favoritesId)
                .updateChildren(favoritesMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context,"Added To Favorites",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addtoCart(int position, List<String>countlist) {
        String saveCurrentTime, saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        String a=auth.getCurrentUser().getUid();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getInstance().getReference().child("Users").child(a).child("Cart List");
        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("title", title.get(position));
        cartMap.put("image", image.get(position));
        cartMap.put("price", price.get(position));
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);
        cartMap.put("quantity", countlist.get(position));

        String productID = title.get(position);
        cartListRef.child(productID)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context,"AddedToCart",Toast.LENGTH_SHORT).show();
                    }
                });
    }

}