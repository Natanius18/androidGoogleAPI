package com.ccc.google_sheets_api;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonListItem = findViewById(R.id.btn_list_items);
        buttonListItem.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ListItem.class);
            startActivity(intent);
        });
    }
}