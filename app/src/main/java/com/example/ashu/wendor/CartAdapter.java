package com.example.ashu.wendor;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by ashu on 22/3/18.
 */

public class CartAdapter extends ArrayAdapter<CartItems> {
    static ArrayList<CartItems> cartItemstItem;

    public CartAdapter(@NonNull Context context, @NonNull ArrayList<CartItems> cartItem) {
        super(context, 0, cartItem);
        cartItemstItem = cartItem;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.single_item, parent, false);
        }
        final CartItems currentItem = getItem(position);

        TextView tvPrice, tvQty, tvName, tvTotalEach;
        tvName = (TextView) listItemView.findViewById(R.id.itemNameCartItem);
        tvPrice = (TextView) listItemView.findViewById(R.id.priceCartItem);
        tvQty = (TextView) listItemView.findViewById(R.id.qtyCartItem);
        tvTotalEach = (TextView) listItemView.findViewById(R.id.totalEachCartItem);

        ImageButton remove = (ImageButton) listItemView.findViewById(R.id.removeButton);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), currentItem.getItemName() + " Removed", Toast.LENGTH_SHORT).show();

                cartItemstItem.remove(position);
                notifyDataSetChanged();

                CartActivity.totalValue = CartActivity.totalValue - currentItem.getTotalEach();
                CartActivity.total.setText("Total = Rs." + CartActivity.totalValue);

            }
        });


        tvName.setText(currentItem.getItemName());
        tvPrice.setText("Rs." + currentItem.getPrice());
        tvTotalEach.setText(" = Rs." + currentItem.getTotalEach());
        tvQty.setText(" X " + currentItem.getQtyEntered());

        return listItemView;
    }

}
