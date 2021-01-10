package com.example.ezyfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MenuActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;

    Vector<Menu> drinkVector = new Vector<>();
    ArrayList<String> menu_name = new ArrayList<>();
    ArrayList<Integer> menu_images = new ArrayList<>();
    ArrayList<Integer> menu_price = new ArrayList<>();
    ArrayList<Integer> menu_stock = new ArrayList<>();
    RecyclerView recyclerView;
    int count[], countDrink[], countFood[], countSnack[];
    String menu_type, loc;
    int balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        SQLiteOpenHelper efDatabaseHelper = new EzyFoodyDatabaseHelper(this);
        try {
           db = efDatabaseHelper.getReadableDatabase();
           loc = getIntent().getStringExtra("chosen_branch");
            menu_type = getIntent().getStringExtra("menu_type");
            String sql = "SELECT * FROM MENU WHERE MENU_TYPE LIKE \"" +menu_type+ "\" AND LOCATION_NAME LIKE \"" +loc +"\"";
            Log.d("sql_statement", sql);
            cursor = db.rawQuery(sql, null);
            Log.d("cursor_test", "masuk");
            if(cursor.moveToFirst()){
                do{
                    Log.d("cursor_masuk", "masuk");
                    Log.d("cursor", cursor.getString(1));
                    drinkVector.add(new Menu(
                            cursor.getString(1), //location_name
                            cursor.getString(2), //menu_name
                            cursor.getInt(3), //stock
                            cursor.getInt(4), //price
                            cursor.getString(5), //menu_type
                            cursor.getInt(6) //picture
                    ));
                } while(cursor.moveToNext());
                cursor.close();

                loadData();

                Log.d("tag-sampai-111", "yea");
                recyclerView = findViewById(R.id.menu_recyclerview);
                countDrink = getIntent().getIntArrayExtra("menuCountArrDrink");
                countFood = getIntent().getIntArrayExtra("menuCountArrFood");
                countSnack = getIntent().getIntArrayExtra("menuCountArrSnack");

                if(countDrink == null){
                    countDrink = new int[menu_images.size()];
                }
                if(countFood == null){
                    countFood = new int[menu_images.size()];
                }
                if(countSnack == null){
                    countSnack = new int[menu_images.size()];
                }

                Log.d("count", loc);
                balance = getIntent().getIntExtra("balance", 0);
                Log.d("tag-sampai", "yea");
                MenuAdapter myAdapter = new MenuAdapter(this, loc, menu_type, balance,  menu_name, menu_images, menu_price, menu_stock, countDrink, countFood, countSnack);
                Log.d("tag-sampai-2", "yea");
                recyclerView.setAdapter(myAdapter);
                Log.d("tag-sampai-3", "yea");
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                Log.d("tag-sampai-4", "yea");
            }
        } catch (SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }


    }

    private void loadData() {
        for (Menu d: drinkVector) {
            Log.d("tag_baby", d.getMenu_name());
            menu_name.add(d.getMenu_name());
            menu_images.add(d.getPicture());
            menu_price.add(d.getPrice());
            menu_stock.add(d.getStock());
        }
    }

    public void viewMyOrder(View view) {
        Intent intent = new Intent(this, OrderActivity.class);
        intent.putExtra("balance", balance);
        intent.putExtra("chosen_branch", loc);
        intent.putExtra("menu_type", menu_type);
        intent.putExtra("menuCountArrDrink", countDrink);
        intent.putExtra("menuCountArrFood", countFood);
        intent.putExtra("menuCountArrSnack", countSnack);
        startActivity(intent);
    }
}