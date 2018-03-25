package com.example.ashu.wendor;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashu.wendor.Adapters.CartAdapter;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    public static ArrayList<CartItems> cartList;
    public static int totalValue;
    public static TextView total;
    CartAdapter itemsAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartList = MainActivity.mCartList;

        itemsAdapter = new CartAdapter(CartActivity.this, cartList);
        listView = findViewById(R.id.listViewCart);
        listView.setAdapter(itemsAdapter);

        total = findViewById(R.id.total);
        totalValue = 0;
        for (int i = 0; i < cartList.size(); i++) {
            totalValue += cartList.get(i).getTotalEach();
        }
        total.setText("Total = Rs." + totalValue);
    }


    public void checkout(View view) {


        ContentValues mUpdateValues = new ContentValues();

        for (int i = 0; i < cartList.size(); i++) {
            int qtyEntered = cartList.get(i).getQtyEntered();
            int id = cartList.get(i).getItemId();

            int TotUnits = MainActivity.list.get(id).getLeftUnit();

            int leftUnits = TotUnits - qtyEntered;
// Defines an object to contain the updated values
            mUpdateValues.put("leftUnits", leftUnits);

            Uri uri = Uri.parse("content://com.example.ashu.wendor/items/" + id);
            int rowsUpdated = getContentResolver().update(uri, mUpdateValues, null, null);

            //Toast.makeText(CartActivity.this,id+" "+rowsUpdated,Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(CartActivity.this, MainActivity.class);
            startActivity(intent);


        }

    }
}
