package com.example.ashu.wendor;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashu.wendor.Adapters.CommonAdapter;
import com.example.ashu.wendor.Adapters.CustomSwipeAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    static ViewPager viewPager;
    static ArrayList<Items> list;
    static ArrayList<CartItems> mCartList;
    static int currentPos;
    static CustomSwipeAdapter adapter;
    Button addToCart;
    // ListView listView;
    Context context;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getSupportLoaderManager().restartLoader(0, null, this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        mRecyclerView = findViewById(R.id.myRecyclerView);

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        list = new ArrayList<>();
        mCartList = new ArrayList<>();


        getSupportLoaderManager().initLoader(0, null, this);


        viewPager = findViewById(R.id.view_pager);
        addToCart = findViewById(R.id.add_to_cart);

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPos = viewPager.getCurrentItem();
                Items currentItem = list.get(currentPos);

                final int itemId = currentItem.getItemId();
                final String itemName = currentItem.getName();
                final int price = currentItem.getPrice();
                final int leftQty = currentItem.getLeftUnit();

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_layout, null);
                mBuilder.setView(mView);

                final TextView tvNameDialog = mView.findViewById(R.id.tvName);
                final TextView tvPriceDialog = mView.findViewById(R.id.tvPrice);
                final EditText mQty = mView.findViewById(R.id.etQty);
                Button btnYes = mView.findViewById(R.id.btnYes);
                Button btnNo = mView.findViewById(R.id.btnNo);

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

                                CartItems cartItem = new CartItems();
                                cartItem.setItemId(itemId);
                                cartItem.setItemName(itemName);
                                cartItem.setPrice(price);
                                cartItem.setQtyEntered(qtyEntered);
                                cartItem.setTotalEach(qtyEntered * price);

                                mCartList.add(cartItem);
                                dialog.dismiss();
                            } else {
                                Toast.makeText(MainActivity.this,
                                        "Please Enter Correct Qty",
                                        Toast.LENGTH_SHORT).show();
                            }

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
            }
        });


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

            Intent intent = new Intent(MainActivity.this, CartActivity.class);

            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, WendorContentProvider.CONTENT_URI, null, null, null, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() == 0) {
            Toast.makeText(this, "Count==0", Toast.LENGTH_SHORT).show();
        }
        if (data.moveToFirst()) {
            do {

                String name = data.getString(data.getColumnIndex(WendorContentProvider.DBHelper.name));
                String imageUrl = data.getString(data.getColumnIndex(WendorContentProvider.DBHelper.imageUrl));
                String imagePath = data.getString(data.getColumnIndex(WendorContentProvider.DBHelper.imagePath));
                int totUnits = data.getInt(data.getColumnIndex(WendorContentProvider.DBHelper.totUnits));
                int leftUnits = data.getInt(data.getColumnIndex(WendorContentProvider.DBHelper.leftUnits));
                int price = data.getInt(data.getColumnIndex(WendorContentProvider.DBHelper.price));
                int itemId = data.getInt(data.getColumnIndex(WendorContentProvider.DBHelper.itemId));

                Items item = new Items();
                item.setItemId(itemId);
                item.setImgPath(imagePath);
                item.setName(name);
                item.setImgUrl(imageUrl);
                item.setLeftUnit(leftUnits);
                item.setTotUnits(totUnits);
                item.setPrice(price);
                list.add(item);

            } while (data.moveToNext());


        }
        adapter = new CustomSwipeAdapter(context, list);
        viewPager.setAdapter(adapter);


        mAdapter = new CommonAdapter(context, list);
        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

