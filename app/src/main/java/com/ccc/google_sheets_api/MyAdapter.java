package com.ccc.google_sheets_api;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final ArrayList<HashMap<String, String>> info;

    MyAdapter(Context context, ArrayList<HashMap<String, String>> info) {
        this.info = info;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        HashMap<String, String> item = info.get(position);
        holder.itemName.setText(item.get("itemName"));
        holder.brand.setText(item.get("brand"));
        holder.price.setText(item.get("price"));
    }

    @Override
    public int getItemCount() {
        return info.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView itemName;
        final TextView brand;
        final TextView price;
        ViewHolder(View view){
            super(view);
            itemName = view.findViewById(R.id.item_name);
            brand = view.findViewById(R.id.brand);
            price = view.findViewById(R.id.price);
        }
    }
}


