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

public class CartAdapter  extends ArrayAdapter<String> {
    Context context;
    List<String> title;
    List<String>image;
    List<String>price;
    List<String>quantity;
    Button removeProductButton;
    private FirebaseAuth mAuth;
    DatabaseReference cartListRef;
    List<Double> totalPriceList;


    public CartAdapter(Context context, List<String> title, List<String> image,List<String> price,List<String> quantity,  List<Double> totalPriceList) {
        super(context,R.layout.card_item_cart,R.id.cart_textView_Title,title);
        this.context = context;
        this.title = title;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.totalPriceList = totalPriceList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View custom = layoutInflater.inflate(R.layout.card_item_cart,parent,false);

        mAuth = FirebaseAuth.getInstance();
        String a=mAuth.getCurrentUser().getUid();

        cartListRef= FirebaseDatabase.getInstance().getReference().child("Users").child(a).child("Cart List");

        ImageView images = custom.findViewById(R.id.cart_custom_image);
        TextView myTitle = custom.findViewById(R.id.cart_textView_Title);
        TextView myQuantiy = custom.findViewById(R.id.quantity_textView);
        TextView myPrice = custom.findViewById(R.id.cart_textView_price);
        removeProductButton = custom.findViewById(R.id.cart_removetocart_btn);

        Picasso.get().load(image.get(position)).into(images);
        myTitle.setText(title.get(position));
        myPrice.setText(price.get(position));
        myQuantiy.setText(quantity.get(position));

        removeProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
                totalPriceList.clear();
            }
        });

        return custom;
    }

    // remove product from Cart List
    private void remove(int position) {
        String productName= title.get(position);
        cartListRef.child(productName).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getContext(),"Product removed successfully",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}