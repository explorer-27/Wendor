package com.example.ashu.wendor.Adapters;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.ashu.wendor.Items;
import com.example.ashu.wendor.R;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by ashu on 24/3/18.
 */

public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.ViewHolder> {
    public static ArrayList<Items> list;
    Context c;

    public CommonAdapter(Context context, ArrayList<Items> arrayList) {
        c = context;
        list = arrayList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("CommonAdapter", "ONCREATEVIEWHOLDER");
        //c=parent.getContext();
        View v = LayoutInflater.from(c)
                .inflate(R.layout.single_image, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tvPrice.setText("Rs: " + list.get(position).getPrice());
        ContextWrapper cw = new ContextWrapper(c);
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File myImageFile = new File(directory, list.get(position).getImgPath());
        String S = myImageFile.getAbsolutePath();
        if (S.isEmpty()) {

            Log.i("Context", "" + c);
        } else {
            Picasso.with(c).load(myImageFile).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvPrice;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.singleImage);
            tvPrice = itemView.findViewById(R.id.singleImagePrice);


        }
    }


}
