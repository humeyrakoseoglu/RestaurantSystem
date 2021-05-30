package com.example.restaurantsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class FragmentCart extends Fragment {
    CartAdapter cartAdapter;

    List<String> menuTitleList;
    List<String> menuImagesList;
    List<String> menuPriceList;
    List<String> menuQuantityList;

    List<Double> totalPriceList;
    Button payButton;

    double total;

    private FirebaseAuth auth;

    ListView listView;
    String total2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_cart,container,false);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        listView =  view.findViewById(R.id.fragmentcart_listView);
        menuTitleList =new ArrayList<>();
        menuImagesList =new ArrayList<>();
        menuPriceList =new ArrayList<>();
        menuQuantityList =new ArrayList<>();
        totalPriceList = new ArrayList<>();

        TextView totalPricetext = view.findViewById(R.id.totalPrice_textView);
        payButton = view.findViewById(R.id.cart_button);

        auth = FirebaseAuth.getInstance();

        String a=auth.getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(a).child("Cart List");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                menuTitleList.clear();
                menuImagesList.clear();
                menuPriceList.clear();
                menuQuantityList.clear();

                for(DataSnapshot cartDataSnap : snapshot.getChildren()){
                    MyCartModel myCartModel = cartDataSnap.getValue(MyCartModel.class);

                    String title= myCartModel.getTitle();
                    menuTitleList.add(title);

                    String image= myCartModel.getImage();
                    menuImagesList.add(image);

                    String quantity= myCartModel.getQuantity();
                    menuQuantityList.add(quantity);

                    String price= myCartModel.getPrice();
                    menuPriceList.add(price);

                    double quantityInt = Double.parseDouble(quantity);
                    double priceInt = Double.parseDouble(price);
                    double pricequantity= quantityInt*priceInt;
                    totalPriceList.add(pricequantity);

                }
                total=0;
                for(int i=0;i<totalPriceList.size();i++){
                    total+=totalPriceList.get(i);

                }
                NumberFormat formatter = new DecimalFormat("#0.00");

                total2 = String.valueOf(formatter.format(total));
                totalPricetext.setText("Total Price: $"+total2);

                cartAdapter= new CartAdapter(getActivity(),menuTitleList,menuImagesList,menuPriceList,menuQuantityList,totalPriceList);
                listView.setAdapter(cartAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);
                Intent intent = new Intent(getContext(), Payment.class);
                String totalPrice = String.valueOf(total2);
                intent.putExtra("totalPrice", totalPrice);
                startActivity(intent);

            }
        });
        return view;
    }
}

