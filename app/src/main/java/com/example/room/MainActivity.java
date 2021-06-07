package com.example.room;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ProductsDB products_db;
    CategoryDB category_db;

    SimpleAdapter simple_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        products_db = ProductsDB.create(this, false);
        category_db = CategoryDB.create(this, false);
        simple_adapter = new SimpleAdapter(getApplicationContext(), this);

        showC();
    }

    public void showAllProducts() {
        Cursor product_c = products_db.query("SELECT * FROM Products", null);

        SimpleCursorAdapter adapter = simple_adapter.productsAdapter(product_c);

        ListView product_lv = findViewById(R.id.listview);
        product_lv.setAdapter(adapter);

        product_lv.setOnItemClickListener(getProductlistener(adapter));
    }

    public AdapterView.OnItemClickListener getProductlistener(final SimpleCursorAdapter adapter) {
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ColorDrawable d = (ColorDrawable) view.getBackground();

                if (d == null) {
                    view.setBackgroundColor(getResources().getColor(R.color.teal_200));
                }

                if (d != null && d.getColor() == getResources().getColor(R.color.teal_200)) {
                    Cursor c = adapter.getCursor();
                    int index = c.getPosition() + 1;
                    showAllProducts();
                }
            }
        };

        return listener;
    }

    public void showProductsbyCategory(int i) {
        Cursor product_c = products_db.query("SELECT * FROM Products WHERE category_id="+String.valueOf(i), null);

        SimpleCursorAdapter adapter = simple_adapter.categoryProductsAdapter(product_c);

        ListView product_lv = findViewById(R.id.listview);
        product_lv.setAdapter(adapter);
        product_lv.setOnItemClickListener(getProductlistener(adapter));
    }

    public void showC() {
        Cursor category_c = category_db.query("SELECT * FROM categories", null);

        final SimpleCursorAdapter category_adapter = simple_adapter.categoryAdapter(category_c);

        ListView category_lv = findViewById(R.id.listview);
        category_lv.setAdapter(category_adapter);

        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor cur = category_adapter.getCursor();
                int index = cur.getInt(0);
                showProductsbyCategory(index);
            }
        };

        category_lv.setOnItemClickListener(getCategoryListener(category_adapter));
    }

    public AdapterView.OnItemClickListener getCategoryListener(final SimpleCursorAdapter adapter) {
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cur = adapter.getCursor();
                int index = cur.getInt(0);
                showProductsbyCategory(index);
            }
        };
        return listener;
    }

    public void setCursorInUIThread(final Cursor c) {
        Context ctx = getApplicationContext();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SimpleCursorAdapter adapter = simple_adapter.productsAdapter(c);
                ListView lv = findViewById(R.id.listview);
                lv.setAdapter(adapter);
            }
        });

    }

    public void showAll(View v) {
        showAllProducts();
    }

    public void goToMainActivity(View v) {
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
