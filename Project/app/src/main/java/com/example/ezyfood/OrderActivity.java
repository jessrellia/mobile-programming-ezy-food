package com.example.ezyfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {
    RecyclerView orderRecyclerView;
    int totalPrice = 0, balance = 0;
    public static OrderAdapter myOrderAdapter;
    int count[], countArrDrink[], countArrFood[], countArrSnack[];
    String loc, type, address;
    private static ArrayList<Order> myOrders = new ArrayList<>();
    EditText ed_alamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ArrayList<Order> myOrder = MenuAdapter.getArrayList();
        for(int i=0; i<myOrder.size(); i++){
            totalPrice += myOrder.get(i).getPrice() * myOrder.get(i).getQty();
        }
        TextView totalPriceView = (TextView) findViewById(R.id.total_price);
        totalPriceView.setText(totalPrice+"");

        balance = getIntent().getIntExtra("balance", 0);
        loc = getIntent().getStringExtra("chosen_branch");
        type = getIntent().getStringExtra("menu_type");
        countArrDrink = getIntent().getIntArrayExtra("menuCountArrDrink");
        countArrFood = getIntent().getIntArrayExtra("menuCountArrFood");
        countArrSnack = getIntent().getIntArrayExtra("menuCountArrSnack");

        Log.d("balance-order-activity", balance+"");
        TextView balanceView = (TextView) findViewById(R.id.balance);
        balanceView.setText(balance+"");

        ed_alamat = findViewById(R.id.address);

        orderRecyclerView = findViewById(R.id.myOrder_recycler);

        myOrderAdapter = new OrderAdapter(this, balance, myOrder);

        orderRecyclerView.setAdapter(myOrderAdapter);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    public void payOrder(View view) {
        int flag = 0;
        if(totalPrice > balance){
            Toast toast = Toast.makeText(this, "Balance is not enough, please top up first!", Toast.LENGTH_SHORT);
            toast.show();
            flag = 1;
        }

        address = ed_alamat.getText().toString();
        Log.d("address-2", address);

        if(address.length() == 0){
            Toast toast = Toast.makeText(this, "Please fill the address!", Toast.LENGTH_SHORT);
            toast.show();
            flag = 1;
        }

        if(flag!=1){
            Intent intent = new Intent(this, OrderCompleteActivity.class);
            intent.putExtra("totalPrice", totalPrice);
            intent.putExtra("balance", balance);
            intent.putExtra("chosen_branch", loc);
            intent.putExtra("menu_type", type);
            intent.putExtra("address", address);
            intent.putExtra("menuCountArrDrink", countArrDrink);
            intent.putExtra("menuCountArrFood", countArrFood);
            intent.putExtra("menuCountArrSnack", countArrSnack);
            startActivity(intent);
        }
    }
}