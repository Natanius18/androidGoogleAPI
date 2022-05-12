package com.ccc.google_sheets_api;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executors;

public class ListItem extends AppCompatActivity {
    ListView listView;
    ListAdapter adapter;
    ProgressDialog loading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);
        setTitle("Каталог товаров");

        listView = findViewById(R.id.lv_items);

        getItems();

    }


    private void getItems() {
        loading = ProgressDialog.show(this, "Загрузка", "Сканирую все товары", false, true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbza7tfd-LRiYmsc4UZhmg9sWo3sJEzVIV_5XUzvMKg2CNPIWozDBzG6hKAmYHizcz4/exec?action=getItems",
                this::parseItems, error -> {
        }
        );

        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    private void parseItems(String jsonResponse) {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(jsonResponse);
            JSONArray array = obj.getJSONArray("items");

            for (int i = 0; i < array.length(); i++) {
                JSONObject jo = array.getJSONObject(i);
                String itemName = jo.getString("itemName");
                String brand = jo.getString("brand");
                String price = jo.getString("price");

                HashMap<String, String> item = new HashMap<>();
                item.put("itemName", itemName);
                item.put("brand", brand);
                item.put("price", price);

                Log.i("Natanius", itemName + " " + brand + ": $" + price);

                list.add(item);

                ItemDB database = Room.databaseBuilder(getApplicationContext(), ItemDB.class, "weather")
                        .fallbackToDestructiveMigration()
                        .build();


                Executors.newSingleThreadExecutor().execute(() -> {
                    Item itemEntity = new Item();
                    itemEntity.name = item.get("name");
                    itemEntity.brand = item.get("brand");
                    itemEntity.price = item.get("price");
                    database.itemDAO().insertItem(itemEntity);
                });

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new SimpleAdapter(this, list, R.layout.list_item_row,
                new String[]{"itemName", "brand", "price"}, new int[]{R.id.item_name, R.id.brand, R.id.price});

        listView.setAdapter(adapter);
        loading.dismiss();
    }
}