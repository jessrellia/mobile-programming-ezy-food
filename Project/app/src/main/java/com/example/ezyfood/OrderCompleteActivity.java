package com.example.ezyfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class OrderCompleteActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    int totalPrice, balance;
    TextView totalPrice_fin;
    ArrayList<Order> myOrder;
    int count[], countArrDrink[], countArrFood[], countArrSnack[];
    String loc, type, address;
    SQLiteDatabase db;
    EzyFoodyDatabaseHelper dbHelper;
    SQLiteDatabase db2;
    private  static ArrayList<History> historyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_complete);

        totalPrice = getIntent().getIntExtra("totalPrice", 0);
        myOrder = OrderAdapter.getArrayList();

        OrderCompleteAdapter orderCompleteAdapter = new OrderCompleteAdapter(this, myOrder);
        recyclerView = findViewById(R.id.order_complete_recycler);
        recyclerView.setAdapter(orderCompleteAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        totalPrice_fin = findViewById(R.id.totalPrice_fin);
        totalPrice_fin.setText(totalPrice+"");

        balance = getIntent().getIntExtra("balance", 0);
        loc = getIntent().getStringExtra("chosen_branch");
        type = getIntent().getStringExtra("menu_type");
        countArrDrink = getIntent().getIntArrayExtra("menuCountArrDrink");
        countArrFood = getIntent().getIntArrayExtra("menuCountArrFood");
        countArrSnack = getIntent().getIntArrayExtra("menuCountArrSnack");
        address = getIntent().getStringExtra("address");
        balance -= totalPrice;
    }

    public void goToMain(View view) {

        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        historyList.add(new History(address, date, totalPrice));
        Intent intent = new Intent(this, MainActivity.class);
        myOrder.removeAll(myOrder);
        intent.putExtra("balance", balance);
        intent.putExtra("chosen_branch", loc);
        intent.putExtra("menu_type", type);
        intent.putExtra("menuCountArrDrink", countArrDrink);
        intent.putExtra("menuCountArrFood", countArrFood);
        intent.putExtra("menuCountArrSnack", countArrSnack);
        startActivity(intent);
    }

    public static ArrayList<History> getHistoryList(){
        return historyList;
    }
}