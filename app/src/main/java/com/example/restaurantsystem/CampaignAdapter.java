package com.example.restaurantsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class CampaignAdapter extends PagerAdapter{
    Context context;
    private ArrayList<MyCampaignModel> modelArrayList;

    public CampaignAdapter(Context context, ArrayList<MyCampaignModel> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @Override
    public int getCount() {
        return modelArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item2, container,false);

        ImageView bannerIv = view.findViewById(R.id.bannerIv);
        TextView titleTv = view.findViewById(R.id.titleTv);
        TextView descriptionTv = view.findViewById(R.id.descriptionTv);

        MyCampaignModel model = modelArrayList.get(position);
        String title = model.getTitle();
        String description = model.getDescription();
        int image = model.getImage();

        bannerIv.setImageResource(image);
        titleTv.setText(title);
        descriptionTv.setText(description);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,title+"\n"+ description,Toast.LENGTH_SHORT).show();
            }
        });

        container.addView(view,position);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}

