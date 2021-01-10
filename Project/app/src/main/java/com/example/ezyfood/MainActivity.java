package com.example.ezyfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    String chosen_branch, menu_type;
    int countArrDrink[], countArrFood[], countArrSnack[], balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countArrDrink = getIntent().getIntArrayExtra("menuCountArrDrink");
        countArrFood = getIntent().getIntArrayExtra("menuCountArrFood");
        countArrSnack = getIntent().getIntArrayExtra("menuCountArrSnack");
        balance = getIntent().getIntExtra("balance", 0);
    }

    public void chooseDrink(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        chosen_branch = getIntent().getStringExtra("chosen_branch");
        Log.d("chosen-branch", chosen_branch);
        intent.putExtra("chosen_branch", chosen_branch);
        intent.putExtra("menu_type", "Drink");
        intent.putExtra("menuCountArrDrink", countArrDrink);
        intent.putExtra("menuCountArrFood", countArrFood);
        intent.putExtra("menuCountArrSnack", countArrSnack);
        intent.putExtra("balance", balance);
        Log.d("chosen_branch_intent_2", chosen_branch+"");
        startActivity(intent);
    }

    public void chooseFood(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        chosen_branch = getIntent().getStringExtra("chosen_branch");
        intent.putExtra("chosen_branch", chosen_branch);
        intent.putExtra("menu_type", "Food");
        intent.putExtra("menuCountArrDrink", countArrDrink);
        intent.putExtra("menuCountArrFood", countArrFood);
        intent.putExtra("menuCountArrSnack", countArrSnack);
        intent.putExtra("balance", balance);
        Log.d("chosen_branch_intent_2", chosen_branch+"");
        startActivity(intent);
    }

    public void chooseSnack (View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        chosen_branch = getIntent().getStringExtra("chosen_branch");
        intent.putExtra("chosen_branch", chosen_branch);
        intent.putExtra("menu_type", "Snack");
        intent.putExtra("menuCountArrDrink", countArrDrink);
        intent.putExtra("menuCountArrFood", countArrFood);
        intent.putExtra("menuCountArrSnack", countArrSnack);
        intent.putExtra("balance", balance);
        Log.d("chosen_branch_intent_2", chosen_branch+"");
        startActivity(intent);
    }


    public void viewMyOrder(View view) {
        Intent intent = new Intent(this, OrderActivity.class);
        chosen_branch = getIntent().getStringExtra("chosen_branch");
        intent.putExtra("balance", balance);
        intent.putExtra("chosen_branch", chosen_branch);
        intent.putExtra("menuCountArrDrink", countArrDrink);
        intent.putExtra("menuCountArrFood", countArrFood);
        intent.putExtra("menuCountArrSnack", countArrSnack);
        startActivity(intent);
    }

    public void topUp(View view) {
        menu_type = getIntent().getStringExtra("menu_type");
        chosen_branch = getIntent().getStringExtra("chosen_branch");
        Intent intent = new Intent(this, TopUpActivity.class);
        intent.putExtra("chosen_branch", chosen_branch);
        intent.putExtra("menu_type", menu_type);
        intent.putExtra("balance", balance);
        intent.putExtra("menuCountArrDrink", countArrDrink);
        intent.putExtra("menuCountArrFood", countArrFood);
        intent.putExtra("menuCountArrSnack", countArrSnack);
        startActivity(intent);
    }

    public void viewHistory(View view) {
        Intent intent = new Intent(this, OrderHistoryActivity.class);
        intent.putExtra("chosen_branch", chosen_branch);
        intent.putExtra("menu_type", menu_type);
        intent.putExtra("balance", balance);
        intent.putExtra("menuCountArrDrink", countArrDrink);
        intent.putExtra("menuCountArrFood", countArrFood);
        intent.putExtra("menuCountArrSnack", countArrSnack);
        startActivity(intent);
    }
}