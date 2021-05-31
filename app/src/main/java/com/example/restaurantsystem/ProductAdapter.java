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

    public class ProductAdapter  extends ArrayAdapter<String> {
        Context context;
        List<String> productList;

        TextView myProduct;
        int icon;
        ImageView productimage;

        public ProductAdapter(Context context, List<String> productList) {
            super(context,R.layout.card_item_product,R.id.product_textView_name_cardItem,productList);
            this.context = context;
            this.productList = productList;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View custom = layoutInflater.inflate(R.layout.card_item_product,parent,false);

            icon=R.drawable.productbasket;
            myProduct = custom.findViewById(R.id.product_textView_name_cardItem);
            productimage = custom.findViewById(R.id.imageView_product);

            myProduct.setText(productList.get(position));
            productimage.setImageResource(icon);

            return custom;
        }

    }