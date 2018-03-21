package com.example.ashu.wendor.Images;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.ashu.wendor.Items;
import com.example.ashu.wendor.MainActivity;
import com.example.ashu.wendor.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by ashu on 20/3/18.
 */

public class CustomSwipeAdapter extends PagerAdapter {
    static Context context;
    ArrayList<Items> imgResources;
    LayoutInflater layoutInflater;

    public CustomSwipeAdapter(Context ctx, ArrayList<Items> arrayList) {
        imgResources = arrayList;
        context = ctx;
    }

    @Override
    public int getCount() {
        return imgResources.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout, container, false);

        ImageView imageView = item_view.findViewById(R.id.swipeImageView);

        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File myImageFile = new File(directory, imgResources.get(position).getImgPath());

        Picasso.with(context).load(myImageFile).into(imageView);

        container.addView(item_view);

        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}


