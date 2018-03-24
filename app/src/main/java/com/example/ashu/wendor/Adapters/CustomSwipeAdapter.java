package com.example.ashu.wendor.Adapters;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
        return (view == object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout, container, false);

        ImageView imageView = item_view.findViewById(R.id.swipeImageView);
        TextView name = item_view.findViewById(R.id.swipeItemName);
        TextView price = item_view.findViewById(R.id.swipeItemPrice);
        name.setText(imgResources.get(position).getName());
        price.setText("Rs. " + imgResources.get(position).getPrice());

        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File myImageFile = new File(directory, imgResources.get(position).getImgPath());
        String S = myImageFile.getAbsolutePath();
        if (S.isEmpty()) {

            Log.i("Context", "" + context);
        } else {
            Picasso.with(context).load(myImageFile).into(imageView);

            Picasso.with(context).setLoggingEnabled(true);


        }
        container.addView(item_view);

        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}


