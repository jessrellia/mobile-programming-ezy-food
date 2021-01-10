package com.example.ezyfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuDetailsActivity extends AppCompatActivity {

    ImageView menuImage;
    TextView menuName, menuPrice, menuCount;
    String name, location, type;
    int price, image, countDrink, countFood, countSnack, countArrDrink[], countArrFood[], countArrSnack[], balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_details);
        Log.d("jalan", "jalan");

        menuImage = findViewById(R.id.detail_img);
        menuName = findViewById(R.id.detail_name);
        menuPrice = findViewById(R.id.detail_price);
        menuCount = findViewById(R.id.detail_quantity);

        getData();
        setData();
    }

    public void getData(){
        if(getIntent().hasExtra("menu_names")
                && getIntent().hasExtra("menu_price")
                && getIntent().hasExtra("menu_image")){
            name = getIntent().getStringExtra("menu_names");
            price = getIntent().getIntExtra("menu_price", 1);
            image = getIntent().getIntExtra("menu_image", 1);
            type = getIntent().getStringExtra("menu_type");
            countArrDrink = getIntent().getIntArrayExtra("menuCountArrDrink");
            countArrFood = getIntent().getIntArrayExtra("menuCountArrFood");
            countArrSnack = getIntent().getIntArrayExtra("menuCountArrSnack");
            countDrink = getIntent().getIntExtra("menu_count_drink", 1);
            countFood = getIntent().getIntExtra("menu_count_food", 1);
            countSnack = getIntent().getIntExtra("menu_count_snack", 1);
            location = getIntent().getStringExtra("location");
            balance = getIntent().getIntExtra("balance", 0);
        }
    }

    private void setData(){
        menuName.setText(name);
        menuPrice.setText(price+"");
        menuImage.setImageResource(image);
        if(type.equals("Drink")){
            menuCount.setText(countDrink+"");
        } else if(type.equals("Food")){
            menuCount.setText(countFood+"");
        } else if(type.equals("Snack")){
            menuCount.setText(countSnack+"");
        }
    }

    public void chooseMenu(View view) {
        Log.d("yeaah", "yeahh");
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("menuCountArrDrink", countArrDrink);
        intent.putExtra("menuCountArrFood", countArrFood);
        intent.putExtra("menuCountArrSnack", countArrSnack);
        intent.putExtra("chosen_branch", location);
        intent.putExtra("menu_type", type);
        intent.putExtra("balance", balance);
        startActivity(intent);
    }

    public void viewMyOrder(View view) {
        Intent intent = new Intent(this, OrderActivity.class);
        intent.putExtra("menuCountArrDrink", countArrDrink);
        intent.putExtra("menuCountArrFood", countArrFood);
        intent.putExtra("menuCountArrSnack", countArrSnack);
        intent.putExtra("chosen_branch", location);
        intent.putExtra("menu_type", type);
        intent.putExtra("balance", balance);
        startActivity(intent);
    }
}