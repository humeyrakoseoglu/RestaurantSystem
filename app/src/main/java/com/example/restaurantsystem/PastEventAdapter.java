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

public class PastEventAdapter extends PagerAdapter {
    Context context;
    List<String> pastEventdatelist;
    List<String> pastEventTitlelist;
    List<String> pastEventDescriptionlist;
    List<String> pastEventImage;

    public PastEventAdapter(Context context, List<String> pastEventdatelist, List<String> pastEventTitlelist, List<String> pastEventDescriptionlist, List<String> pastEventImage) {
        this.context = context;
        this.pastEventdatelist = pastEventdatelist;
        this.pastEventTitlelist = pastEventTitlelist;
        this.pastEventDescriptionlist = pastEventDescriptionlist;
        this.pastEventImage = pastEventImage;
    }

    @Override
    public int getCount() {
        return pastEventTitlelist.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item, container,false);

        ImageView bannerIv = view.findViewById(R.id.bannerIv_carditem1_pastEvent);
        TextView titleTv = view.findViewById(R.id.titleTv);
        TextView descriptionTv = view.findViewById(R.id.descriptionTv);
        TextView dateTv = view.findViewById(R.id.custom_textView_date);


        Picasso.get().load(pastEventImage.get(position)).into(bannerIv);

        descriptionTv.setText(pastEventDescriptionlist.get(position));
        titleTv.setText(pastEventTitlelist.get(position));
        dateTv.setText(pastEventdatelist.get(position));

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