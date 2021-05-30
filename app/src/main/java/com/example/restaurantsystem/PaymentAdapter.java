package com.example.restaurantsystem;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class PaymentAdapter extends ArrayAdapter<String> {
    Context context;

    List<String> orderTitleList;
    List<String> orderPriceList;
    List<String> orderQuantityList;

    public PaymentAdapter(Context context, List<String> orderPriceList,  List<String> orderTitleList, List<String> orderQuantityList) {
        super(context,R.layout.activity_payment,R.id.textView_payment,orderTitleList);
        this.context = context;
        this.orderPriceList = orderPriceList;
        this.orderTitleList = orderTitleList;
        this.orderQuantityList = orderQuantityList;
    }
}
