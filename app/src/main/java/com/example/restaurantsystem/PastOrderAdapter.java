package com.example.restaurantsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class PastOrderAdapter  extends ArrayAdapter<String> {
    Context context;
    List<String> date;
    List<String> time;
    List<String> priceList;
    List<String> cardList;
    List<String> addressList;

    ImageView images;
    TextView myDate;
    TextView myTime;
    TextView myPrice;
    TextView myCard;
    TextView myAddress;

    String createdDate,createdTime;

    int icon;

    public PastOrderAdapter(Context context, List<String> date, List<String> time, List<String> priceList,int icon,  List<String> cardList, List<String> addressList) {
        super(context,R.layout.card_item_pastorders,R.id.textView_pastcardName,cardList);
        this.context = context;
        this.date = date;
        this.time = time;
        this.icon = icon;
        this.priceList = priceList;
        this.cardList = cardList;
        this.addressList = addressList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View custom = layoutInflater.inflate(R.layout.card_item_pastorders,parent,false);

        images = custom.findViewById(R.id.pastorders_image_cardItem);
        myDate = custom.findViewById(R.id.pastorders_textView_date_cardItem);
        myTime = custom.findViewById(R.id.pastorders_textView_time_cardItem);
        myPrice = custom.findViewById(R.id.textView_totalPrice_pastorder);
        myCard = custom.findViewById(R.id.textView_pastcardName);
        myAddress = custom.findViewById(R.id.textView_pastaddress);

        createdDate = myDate.getText().toString();
        createdTime = myTime.getText().toString();

        images.setImageResource(icon);
        myDate.setText(date.get(position));
        myTime.setText(time.get(position));
        myPrice.setText("$"+priceList.get(position));
        myCard.setText("Card Name: "+cardList.get(position));
        myAddress.setText("Address Name: " +addressList.get(position));

        return custom;
    }
}