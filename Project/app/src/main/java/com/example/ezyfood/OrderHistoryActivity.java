package com.example.ezyfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class OrderHistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SQLiteDatabase db;
    ArrayList<String> history_names;
    ArrayList<Integer> history_total;
    ArrayList<String> history_date;
    String menu_type, location_name, chosen_branch;
    int countArrDrink[], countArrFood[], countArrSnack[], balance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        recyclerView = findViewById(R.id.hist_recycler);

        balance = getIntent().getIntExtra("balance", 0);
        location_name = getIntent().getStringExtra("chosen_branch");
        menu_type = getIntent().getStringExtra("menu_type");
        countArrDrink = getIntent().getIntArrayExtra("menuCountArrDrink");
        countArrFood = getIntent().getIntArrayExtra("menuCountArrFood");
        countArrSnack = getIntent().getIntArrayExtra("menuCountArrSnack");


        OrderHistoryAdapter myAdapter = new OrderHistoryAdapter(this, OrderCompleteActivity.getHistoryList());
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }
}