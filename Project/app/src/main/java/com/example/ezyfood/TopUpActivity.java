package com.example.ezyfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class TopUpActivity extends AppCompatActivity {
    int balance;
    EditText ed;
    String menu_type, location_name, chosen_branch;
    int countArrDrink[], countArrFood[], countArrSnack[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);
//        SQLiteOpenHelper efDatabaseHelper = new EzyFoodyDatabaseHelper(this);

        balance = getIntent().getIntExtra("balance", 0);
        location_name = getIntent().getStringExtra("chosen_branch");
        menu_type = getIntent().getStringExtra("menu_type");
        countArrDrink = getIntent().getIntArrayExtra("menuCountArrDrink");
        countArrFood = getIntent().getIntArrayExtra("menuCountArrFood");
        countArrSnack = getIntent().getIntArrayExtra("menuCountArrSnack");

        ed = findViewById(R.id.topupamount);
        TextView curr_balView = (TextView) findViewById(R.id.curr_balance);
        curr_balView.setText(balance+"");

    }

    public void topUpBalance(View view) {
        Integer bal = Integer.parseInt(ed.getText().toString());

        if(bal > 2000000){
            Toast toast = Toast.makeText(this, "Maximum top up is 2 million!", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            balance += bal;
            TextView curr_balView = (TextView) findViewById(R.id.curr_balance);
            curr_balView.setText(balance+"");
            ed.getText().clear();

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("balance", balance);
            intent.putExtra("chosen_branch", location_name);
            intent.putExtra("menu_type", menu_type);
            intent.putExtra("menuCountArrDrink", countArrDrink);
            intent.putExtra("menuCountArrFood", countArrFood);
            intent.putExtra("menuCountArrSnack", countArrSnack);
            startActivity(intent);
        }
    }

}