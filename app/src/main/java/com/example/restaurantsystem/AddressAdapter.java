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

import java.util.List;

public class AddressAdapter extends ArrayAdapter<String> {
    Context context;
    List<String> name;
    List<String> address;

    ImageView images;
    TextView myName ;
    TextView myAddress;
    Button deleteButton;
    String addressName,fullAddress;

    private FirebaseAuth mAuth;
    DatabaseReference addressListRef;

     int icon;

    public AddressAdapter(Context context, List<String> name, List<String> address,int icon) {
        super(context,R.layout.card_item_address,R.id.address_textView_name_cardItem,name);
        this.context = context;
        this.name = name;
        this.address = address;
        this.icon = icon;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View custom = layoutInflater.inflate(R.layout.card_item_address,parent,false);

        mAuth = FirebaseAuth.getInstance();
        String a=mAuth.getCurrentUser().getUid();

        addressListRef= FirebaseDatabase.getInstance().getReference().child("Users").child(a)
                .child("Addresses");


        images = custom.findViewById(R.id.address_image_cardItem);
        myName = custom.findViewById(R.id.address_textView_name_cardItem);
        myAddress = custom.findViewById(R.id.address_textView_fulladdress_cardItem);
        deleteButton = custom.findViewById(R.id.delete_carditem_button);

        addressName = myName.getText().toString();
        fullAddress = myAddress.getText().toString();


        images.setImageResource(icon);
        myName.setText(name.get(position));
        myAddress.setText(address.get(position));
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });

        return custom;
    }

    private void remove(int position) {
        String addressID = "address "+name.get(position);
        addressListRef.child(addressID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getContext(),"Address removed successfully",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
