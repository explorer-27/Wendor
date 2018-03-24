package com.example.ashu.wendor;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by ashu on 24/3/18.
 */

public class CommonAdapter extends ArrayAdapter<Items> {
    static ArrayList<Items> ItemstItem;
    TextView tv;
    ImageView iv;

    public CommonAdapter(@NonNull Context context, ArrayList<Items> arrayList) {
        super(context, 0, arrayList);
        ItemstItem = arrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.single_image, parent, false);
        }
        Items currentItem = new Items();
        currentItem = ItemstItem.get(position);


        iv = listItemView.findViewById(R.id.singleImage);
        tv = listItemView.findViewById(R.id.sinngleImagePrice);

        tv.setText("" + currentItem.getPrice());
        Picasso.with(getContext()).load(currentItem.getImgUrl()).fit().into(iv);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "img", Toast.LENGTH_SHORT).show();
            }
        });

        return listItemView;
    }
}
