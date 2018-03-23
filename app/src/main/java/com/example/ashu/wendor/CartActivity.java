package com.example.ashu.wendor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class CartActivity extends AppCompatActivity {
    public static ArrayList<CartItems> cartList;
    public static int totalValue;
    static TextView total;
    CartAdapter itemsAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartList = MainActivity.cartlist;

        itemsAdapter = new CartAdapter(CartActivity.this, cartList);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(itemsAdapter);

        total = findViewById(R.id.total);
        totalValue = 0;
        for (int i = 0; i < cartList.size(); i++) {
            totalValue += cartList.get(i).getTotalEach();
        }
        total.setText("Total = Rs." + totalValue);
    }


    public void checkout(View view) {
    }
}
