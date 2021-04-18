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

import com.squareup.picasso.Picasso;

import java.util.List;

public class CampaignAdapter extends PagerAdapter{
    Context context;

    List<String> campaignTitleList;
    List<String> campaignDescriptionList;
    List<String>campaignImage;

    public CampaignAdapter(Context context, List<String> campaignTitleList, List<String> campaignDescriptionList, List<String> campaignImage) {
        this.context = context;
        this.campaignTitleList = campaignTitleList;
        this.campaignDescriptionList = campaignDescriptionList;
        this.campaignImage = campaignImage;
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public int getCount() {
        return campaignTitleList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item2, container,false);

        ImageView bannerIv = view.findViewById(R.id.bannerIv_carditem2_campaign);
        TextView titleTv = view.findViewById(R.id.titleTv);
        TextView descriptionTv = view.findViewById(R.id.descriptionTv);

        Picasso.get().load(campaignImage.get(position)).into(bannerIv);
        descriptionTv.setText(campaignDescriptionList.get(position));
        titleTv.setText(campaignTitleList.get(position));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, titleTv.getText() + "\n" + descriptionTv.getText(), Toast.LENGTH_SHORT).show();
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