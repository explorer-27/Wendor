package com.example.ashu.wendor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Intent intent = getIntent();
        ArrayList<String> cartList = intent.getStringArrayListExtra("cartList");
        TextView tv = findViewById(R.id.tvCart);
        String text = "";


        for (int i = 0; i < cartList.size(); i++) {
            text = text + "\n" + cartList.get(i);
        }

        tv.setText(text);


        //.makeText(this,"",LENGTH_SHORT).show();

    }
}
