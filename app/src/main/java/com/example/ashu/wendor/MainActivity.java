package com.example.ashu.wendor;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashu.wendor.Images.CustomSwipeAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    static ViewPager viewPager;
    static ArrayList<Items> list;
    static ArrayList<String> cartlist;
    static int currentPos;
    CustomSwipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        list = new ArrayList<>();
        cartlist = new ArrayList<>();
        getSupportLoaderManager().initLoader(0, null, this);

        viewPager = findViewById(R.id.view_pager);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemSelected = item.getItemId();
        if (itemSelected == R.id.cartmenu) {
            Intent i = new Intent(this, CartActivity.class);
            i.putStringArrayListExtra("cartList", cartlist);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);

    }

    public void addToCartClicked(View view) {
        currentPos = viewPager.getCurrentItem();
        Items currentItem = list.get(currentPos);

        final int itemId = currentItem.getItemId();
        final String itemName = currentItem.getName();
        int price = currentItem.getPrice();
        final int leftQty = currentItem.getLeftUnit();

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_layout, null);
        mBuilder.setView(mView);

        final TextView tvNameDialog = (TextView) mView.findViewById(R.id.tvName);
        final TextView tvPriceDialog = (TextView) mView.findViewById(R.id.tvPrice);
        final EditText mQty = (EditText) mView.findViewById(R.id.etQty);
        Button btnYes = (Button) mView.findViewById(R.id.btnYes);
        Button btnNo = (Button) mView.findViewById(R.id.btnNo);

        tvNameDialog.setText(itemName);
        tvPriceDialog.setText("Rs. " + price);

        mQty.setHint(leftQty + " quantities left.");

        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qtyEntered = 0;
                try {
                    qtyEntered = Integer.parseInt(mQty.getText().toString());
                    if (qtyEntered > 0 && qtyEntered <= leftQty) {

                        cartlist.add(itemName);
                        dialog.dismiss();
                    } else {
                        Toast.makeText(MainActivity.this,
                                "Please Enter Correct Qty",
                                Toast.LENGTH_SHORT).show();
                    }
                    /*Intent i=new Intent(MainActivity.this,CartActivity.class);
                    i.putStringArrayListExtra("cartList",cartlist);
                        startActivity(i);*/
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this,
                            "Please Enter Correct Qty",
                            Toast.LENGTH_SHORT).show();

                }

            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mBuilder.setView(null);


        // Toast.makeText(this,""+currentItem.getItemId(),Toast.LENGTH_SHORT).show();
/*
      Intent i=new Intent(this,CartActivity.class);
        i.putExtra("postiton", currentItem.getName());
        startActivity(i);*/
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, WendorContentProvider.CONTENT_URI, null, null, null, WendorContentProvider.name);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() == 0) {
            Toast.makeText(this, "Count==0", Toast.LENGTH_SHORT).show();
        }
        if (data.moveToFirst()) {
            do {

                String name = data.getString(data.getColumnIndex(WendorContentProvider.name));
                String imageUrl = data.getString(data.getColumnIndex(WendorContentProvider.imageUrl));
                String imagePath = data.getString(data.getColumnIndex(WendorContentProvider.imagePath));
                int totUnits = data.getInt(data.getColumnIndex(WendorContentProvider.totUnits));
                int leftUnits = data.getInt(data.getColumnIndex(WendorContentProvider.leftUnits));
                int price = data.getInt(data.getColumnIndex(WendorContentProvider.price));
                int itemId = data.getInt(data.getColumnIndex(WendorContentProvider.itemId));

                Items item = new Items();
                item.setItemId(itemId);
                item.setImgPath(imagePath);
                item.setName(name);
                item.setImgUrl(imageUrl);
                item.setLeftUnit(leftUnits);
                item.setTotUnits(totUnits);
                item.setPrice(price);
                list.add(item);

                // loadImages(list);
                //Toast.makeText(this,""+imagePath,Toast.LENGTH_SHORT).show();
            } while (data.moveToNext());

            adapter = new CustomSwipeAdapter(this, list);
            viewPager.setAdapter(adapter);

        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

