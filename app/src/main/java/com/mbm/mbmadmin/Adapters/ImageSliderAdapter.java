package com.mbm.mbmadmin.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.mbm.mbmadmin.R;
import com.zolad.zoominimageview.ZoomInImageView;

import java.util.ArrayList;

public class ImageSliderAdapter extends PagerAdapter {

    Context context;
    ArrayList<String>  imageslist;

    public ImageSliderAdapter(@NonNull Context context, @NonNull ArrayList<String> imageslist) {
        this.context = context;
        this.imageslist = imageslist;
    }

    @Override
    public int getCount() {
        return imageslist.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.customsliderimglayout,container,false);
        container.removeView(view);
        ImageView imageView = view.findViewById(R.id.slider_img);
        Uri uri = Uri.parse(imageslist.get(position));
        Glide.with(context).load(uri).into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
