package com.example.restaurantsystem;

import android.content.Context;
import android.content.Intent;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class CurrentOrderAdapter  extends ArrayAdapter<String> {
    Context context;
    List<String> date;
    List<String> time;
    List<String> productsList;

    ImageView images;
    TextView myDate;
    TextView myTime;
    TextView details;

    Button cancelButton;
    String createdDate,createdTime;

    private FirebaseAuth mAuth;
    DatabaseReference currentOrderListRef;

    double totalPrice=0.0;
    int icon;

    public CurrentOrderAdapter(Context context, List<String> date, List<String> time, List<String> productsList,int icon) {
        super(context,R.layout.card_item_currentorder,R.id.currentorders_textView_date_cardItem,date);
        this.context = context;
        this.date = date;
        this.time = time;
        this.productsList = productsList;
        this.icon = icon;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View custom = layoutInflater.inflate(R.layout.card_item_currentorder,parent,false);

        mAuth = FirebaseAuth.getInstance();
        String a=mAuth.getCurrentUser().getUid();

       /* FirebaseDatabase firebaseDatabase2 = FirebaseDatabase.getInstance();
        databaseReference2 = firebaseDatabase2.getReference();*/

        currentOrderListRef= FirebaseDatabase.getInstance().getReference().child("Users").child(a)
                .child("Order List").child("Current Orders");

        images = custom.findViewById(R.id.currentorders_image_cardItem);
        myDate = custom.findViewById(R.id.currentorders_textView_date_cardItem);
        myTime = custom.findViewById(R.id.currentorders_textView_time_cardItem);

        details = custom.findViewById(R.id.textView_products_currentorders);
        String product="";
        for (int i =0;i<productsList.size();i++){
            product+=productsList.get(i)+" ";
        }
        details.setText(product);

        cancelButton = custom.findViewById(R.id.cancel_carditem_button_currentOrders);


        images.setImageResource(icon);
        myDate.setText(date.get(position));
        myTime.setText(time.get(position));

        createdDate = myDate.getText().toString();
        createdTime = myTime.getText().toString();

        String saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        if(!createdDate.equals(saveCurrentDate)){
            movePastOrder(position);
        }
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });

        return custom;
    }

    private void movePastOrder(int position) {
        final HashMap<String, Object> orderMap = new HashMap<>();

        String a=mAuth.getCurrentUser().getUid();
        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getInstance().getReference().child("Users").child(a).child("Order List");
        orderMap.put("date",date.get(position));
        orderMap.put("time",time.get(position));
        //orderMap.put("totalPrice",totalPrice);

        String orderID = "created on "+date.get(position)+" "+time.get(position);
        cartListRef.child("Past Orders").child(orderID).updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getContext(),"aa",Toast.LENGTH_SHORT).show();
            }
        });

        //remove from current order
        currentOrderListRef.child(orderID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getContext(),"Order removed successfully",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /* private void getProductsTitle(int position, List<String>productsListt) {
         String a=mAuth.getCurrentUser().getUid();
         String orderID = "created on "+date.get(position)+" "+time.get(position);
         currentOrderListRef.child(orderID).child("Products");
         currentOrderListRef.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 productsListt.clear();
                 for(DataSnapshot currentOrderDataSnap : snapshot.getChildren()){
                     MyOrderModel myOrderModel = currentOrderDataSnap.getValue(MyOrderModel.class);
                     String title= myOrderModel.getTitle();
                     productsListt.add(title);
                 }
             }
             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });
     }
 */
    private void remove(int position) {
        String orderID = "created on "+date.get(position)+" "+time.get(position);
        currentOrderListRef.child(orderID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getContext(),"Order removed successfully",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}