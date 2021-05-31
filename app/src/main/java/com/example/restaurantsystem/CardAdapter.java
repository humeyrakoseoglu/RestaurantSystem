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

public class CardAdapter extends ArrayAdapter<String> {
    Context context;
    List<String> nameList;
    List<String> numberList;

    ImageView images;
    TextView myName ;
    TextView myNumber;
    Button deleteButton;

    private FirebaseAuth mAuth;
    DatabaseReference cardListRef;

    int icon;

    public CardAdapter(Context context, List<String> nameList, List<String> numberList,int icon) {
        super(context,R.layout.card_item_address,R.id.address_textView_name_cardItem,nameList);
        this.context = context;
        this.nameList = nameList;
        this.numberList = numberList;
        this.icon = icon;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View custom = layoutInflater.inflate(R.layout.card_item_address,parent,false);

        mAuth = FirebaseAuth.getInstance();
        String a=mAuth.getCurrentUser().getUid();

        cardListRef= FirebaseDatabase.getInstance().getReference().child("Users").child(a)
                .child("Credit Cards");

        images = custom.findViewById(R.id.address_image_cardItem);
        myName = custom.findViewById(R.id.address_textView_name_cardItem);
        myNumber = custom.findViewById(R.id.address_textView_fulladdress_cardItem);
        deleteButton = custom.findViewById(R.id.delete_carditem_button);

        images.setImageResource(icon);
        myName.setText(nameList.get(position));
        myNumber.setText(numberList.get(position));

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });

        return custom;
    }

    // remove card from Registered Card List
    private void remove(int position) {
        String cardID = "card "+nameList.get(position);
        cardListRef.child(cardID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getContext(),"Card removed successfully",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

