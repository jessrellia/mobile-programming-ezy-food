package com.example.ezyfood;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EzyFoodyDatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "ezyfoody";
    private static final int DB_VERSION = 1;

    EzyFoodyDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        updateMyDatabase(db, 0, DB_VERSION);
    }

    public static void insertLocation(SQLiteDatabase db, String branch_name, double latitude, double longitude){
        ContentValues locationValues = new ContentValues();
        locationValues.put("BRANCH_NAME", branch_name);
        locationValues.put("LATITUDE", latitude);
        locationValues.put("LONGITUDE", longitude);
        db.insert("LOCATIONS", null, locationValues);
    }

    public static  void insertMenu(SQLiteDatabase db, String location_name,  String menu_name, int stock, int price, String menu_type, int picture){
        ContentValues menuValues = new ContentValues();
        menuValues.put("LOCATION_NAME", location_name);
        menuValues.put("MENU_NAME", menu_name);
        menuValues.put("STOCK", stock);
        menuValues.put("PRICE", price);
        menuValues.put("MENU_TYPE", menu_type);
        menuValues.put("PICTURE", picture);
        db.insert("MENU", null, menuValues);
    }

    public void insertHistory(String address, String date, Integer total){
        SQLiteDatabase db2 = this.getWritableDatabase();
        ContentValues histValues = new ContentValues();
        histValues.put("ADDRESS", address);
        histValues.put("DATE", date);
        histValues.put("TOTAL", total);
        db2.insert("HISTORY", null, histValues);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion){
        if(oldVersion < 1){
            db.execSQL("CREATE TABLE LOCATIONS ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "BRANCH_NAME TEXT,"
                    + "LATITUDE DOUBLE,"
                    + "LONGITUDE DOUBLE);");

            db.execSQL("CREATE TABLE MENU ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "LOCATION_NAME REFERENCES LOCATIONS(BRANCH_NAME),"
                    + "MENU_NAME TEXT,"
                    + "STOCK INTEGER,"
                    + "PRICE INTEGER,"
                    + "MENU_TYPE TEXT,"
                    + "PICTURE INTEGER);");

            db.execSQL("CREATE TABLE HISTORY ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "ADDRESS TEXT,"
                    + "DATE NUMERIC,"
                    + "TOTAL INTEGER);");

            db.execSQL("CREATE TABLE ORDER_DETAIL ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "MENU_ID REFERENCES MENU(_id),"
                    + "QUANTITY INTEGER,"
                    + "TRANSACTION_ID REFERENCES HISTORY(_id));");

            insertLocation(db, "EzyFoody Binus Anggrek", -6.2019, 106.7809);
            insertLocation(db, "EzyFoody Binus Alam Sutera", -6.2247,  106.6503);
            insertLocation(db, "EzyFoody Binus Bekasi", -6.2201, 106.9997);
            insertLocation(db, "EzyFoody Institut Pertanian Bogor", -6.548705311731031, 106.72397081136913);
            insertLocation(db, "EzyFoody Universitas Prasetya Mulya", -6.300644686817748, 106.64002235765489);

            insertMenu(db, "EzyFoody Binus Anggrek", "Mineral Water", 12, 5000, "Drink", R.drawable.mineral_water);
            insertMenu(db, "EzyFoody Binus Anggrek", "Apple Juice", 32, 15000, "Drink", R.drawable.apple_juice);
            insertMenu(db, "EzyFoody Binus Anggrek", "Mango Juice", 10, 15000, "Drink", R.drawable.mango_juice);
            insertMenu(db, "EzyFoody Binus Anggrek", "Avocado Juice", 5, 15000, "Drink", R.drawable.avocado_juice);
            insertMenu(db, "EzyFoody Binus Anggrek", "Iced Tea", 5, 15000, "Drink", R.drawable.iced_tea);
            insertMenu(db, "EzyFoody Binus Anggrek", "Bubble Tea", 2, 20000, "Drink", R.drawable.bubble_tea);
            insertMenu(db, "EzyFoody Binus Anggrek", "Americano", 8, 20000, "Drink", R.drawable.americano);
            insertMenu(db, "EzyFoody Binus Anggrek", "Latte Macchiato", 22, 20000, "Drink", R.drawable.latte_macchiato);
            insertMenu(db, "EzyFoody Binus Anggrek", "Mac and Cheese", 33, 35000, "Food", R.drawable.mac_and_cheese);
            insertMenu(db, "EzyFoody Binus Anggrek", "Fish and Chips", 10, 50000, "Food", R.drawable.fish_and_chips);
            insertMenu(db, "EzyFoody Binus Anggrek", "Lasagna", 22, 55000, "Food", R.drawable.lasagna);
            insertMenu(db, "EzyFoody Binus Anggrek", "Skillet Steak", 50, 80000, "Food", R.drawable.skillet_steak);
            insertMenu(db, "EzyFoody Binus Anggrek", "Chocolate Cookies", 10, 15000, "Snack", R.drawable.chocolate_chips_cookies);
            insertMenu(db, "EzyFoody Binus Anggrek", "Lotus Biscoff Cookies", 15, 20000, "Snack", R.drawable.lotus_cookies);
            insertMenu(db, "EzyFoody Binus Anggrek", "Nutella Cookies", 20, 20000, "Snack", R.drawable.nutella_cookies);
            insertMenu(db, "EzyFoody Binus Anggrek", "Caramel Cookies", 20, 20000, "Snack", R.drawable.salted_caramel_cookies);

            insertMenu(db, "EzyFoody Binus Alam Sutera", "Mineral Water", 5, 5000, "Drink", R.drawable.mineral_water);
            insertMenu(db, "EzyFoody Binus Alam Sutera", "Apple Juice", 12, 15000, "Drink", R.drawable.apple_juice);
            insertMenu(db, "EzyFoody Binus Alam Sutera", "Mango Juice", 3, 15000, "Drink", R.drawable.mango_juice);
            insertMenu(db, "EzyFoody Binus Alam Sutera", "Avocado Juice", 2, 15000, "Drink", R.drawable.avocado_juice);
            insertMenu(db, "EzyFoody Binus Alam Sutera", "Iced Tea", 2, 15000, "Drink", R.drawable.iced_tea);
            insertMenu(db, "EzyFoody Binus Alam Sutera", "Bubble Tea", 1, 20000, "Drink", R.drawable.bubble_tea);
            insertMenu(db, "EzyFoody Binus Alam Sutera", "Americano", 5, 20000, "Drink", R.drawable.americano);
            insertMenu(db, "EzyFoody Binus Alam Sutera", "Latte Macchiato", 3, 20000, "Drink", R.drawable.latte_macchiato);
            insertMenu(db, "EzyFoody Binus Alam Sutera", "Mac and Cheese", 33, 35000, "Food", R.drawable.mac_and_cheese);
            insertMenu(db, "EzyFoody Binus Alam Sutera", "Fish and Chips", 10, 50000, "Food", R.drawable.fish_and_chips);
            insertMenu(db, "EzyFoody Binus Alam Sutera", "Lasagna", 22, 55000, "Food", R.drawable.lasagna);
            insertMenu(db, "EzyFoody Binus Alam Sutera", "Skillet Steak", 50, 80000, "Food", R.drawable.skillet_steak);
            insertMenu(db, "EzyFoody Binus Alam Sutera", "Chocolate Chips Cookies", 10, 15000, "Snack", R.drawable.chocolate_chips_cookies);
            insertMenu(db, "EzyFoody Binus Alam Sutera", "Lotus Biscoff Cookies", 15, 20000, "Snack", R.drawable.lotus_cookies);
            insertMenu(db, "EzyFoody Binus Alam Sutera", "Nutella Cookies", 20, 20000, "Snack", R.drawable.nutella_cookies);
            insertMenu(db, "EzyFoody Binus Alam Sutera", "Caramel Cookies", 20, 20000, "Snack", R.drawable.salted_caramel_cookies);

            insertMenu(db, "EzyFoody Binus Bekasi", "Mineral Water", 50, 5000, "Drink", R.drawable.mineral_water);
            insertMenu(db, "EzyFoody Binus Bekasi", "Apple Juice", 15, 15000, "Drink", R.drawable.apple_juice);
            insertMenu(db, "EzyFoody Binus Bekasi", "Mango Juice", 12, 15000, "Drink", R.drawable.mango_juice);
            insertMenu(db, "EzyFoody Binus Bekasi", "Avocado Juice", 12, 15000, "Drink", R.drawable.avocado_juice);
            insertMenu(db, "EzyFoody Binus Bekasi", "Iced Tea", 2, 15000, "Drink", R.drawable.iced_tea);
            insertMenu(db, "EzyFoody Binus Bekasi", "Bubble Tea", 11, 20000, "Drink", R.drawable.bubble_tea);
            insertMenu(db, "EzyFoody Binus Bekasi", "Americano", 10, 20000, "Drink", R.drawable.americano);
            insertMenu(db, "EzyFoody Binus Bekasi", "Latte Macchiato", 11, 20000, "Drink", R.drawable.latte_macchiato);
            insertMenu(db, "EzyFoody Binus Bekasi", "Mac and Cheese", 33, 35000, "Food", R.drawable.mac_and_cheese);
            insertMenu(db, "EzyFoody Binus Bekasi", "Fish and Chips", 10, 50000, "Food", R.drawable.fish_and_chips);
            insertMenu(db, "EzyFoody Binus Bekasi", "Lasagna", 22, 55000, "Food", R.drawable.lasagna);
            insertMenu(db, "EzyFoody Binus Bekasi", "Skillet Steak", 50, 80000, "Food", R.drawable.skillet_steak);
            insertMenu(db, "EzyFoody Binus Bekasi", "Chocolate Cookies", 10, 15000, "Snack", R.drawable.chocolate_chips_cookies);
            insertMenu(db, "EzyFoody Binus Bekasi", "Lotus Biscoff Cookies", 15, 20000, "Snack", R.drawable.lotus_cookies);
            insertMenu(db, "EzyFoody Binus Bekasi", "Nutella Cookies", 20, 20000, "Snack", R.drawable.nutella_cookies);
            insertMenu(db, "EzyFoody Binus Bekasi", "Caramel Cookies", 21, 20000, "Snack", R.drawable.salted_caramel_cookies);

            insertMenu(db, "EzyFoody Institut Pertanian Bogor", "Mineral Water", 20, 5000, "Drink", R.drawable.mineral_water);
            insertMenu(db, "EzyFoody Institut Pertanian Bogor", "Apple Juice", 11, 15000, "Drink", R.drawable.apple_juice);
            insertMenu(db, "EzyFoody Institut Pertanian Bogor", "Mango Juice", 32, 15000, "Drink", R.drawable.mango_juice);
            insertMenu(db, "EzyFoody Institut Pertanian Bogor", "Avocado Juice", 2, 15000, "Drink", R.drawable.avocado_juice);
            insertMenu(db, "EzyFoody Institut Pertanian Bogor", "Iced Tea", 2, 15000, "Drink", R.drawable.iced_tea);
            insertMenu(db, "EzyFoody Institut Pertanian Bogor", "Bubble Tea", 19, 20000, "Drink", R.drawable.bubble_tea);
            insertMenu(db, "EzyFoody Institut Pertanian Bogor", "Americano", 3, 20000, "Drink", R.drawable.americano);
            insertMenu(db, "EzyFoody Institut Pertanian Bogor", "Latte Macchiato", 10, 20000, "Drink", R.drawable.latte_macchiato);
            insertMenu(db, "EzyFoody Institut Pertanian Bogor", "Mac and Cheese", 80, 35000, "Food", R.drawable.mac_and_cheese);
            insertMenu(db, "EzyFoody Institut Pertanian Bogor", "Fish and Chips", 4, 50000, "Food", R.drawable.fish_and_chips);
            insertMenu(db, "EzyFoody Institut Pertanian Bogor", "Lasagna", 25, 55000, "Food", R.drawable.lasagna);
            insertMenu(db, "EzyFoody Institut Pertanian Bogor", "Skillet Steak", 12, 80000, "Food", R.drawable.skillet_steak);
            insertMenu(db, "EzyFoody Institut Pertanian Bogor", "Chocolate Cookies", 18, 15000, "Snack", R.drawable.chocolate_chips_cookies);
            insertMenu(db, "EzyFoody Institut Pertanian Bogor", "Lotus Biscoff Cookies", 22, 20000, "Snack", R.drawable.lotus_cookies);
            insertMenu(db, "EzyFoody Institut Pertanian Bogor", "Nutella Cookies", 23, 20000, "Snack", R.drawable.nutella_cookies);
            insertMenu(db, "EzyFoody Institut Pertanian Bogor", "Caramel Cookies", 25, 20000, "Snack", R.drawable.salted_caramel_cookies);

            insertMenu(db, "EzyFoody Universitas Prasetya Mulya", "Mineral Water", 19, 5000, "Drink", R.drawable.mineral_water);
            insertMenu(db, "EzyFoody Universitas Prasetya Mulya", "Apple Juice", 18, 15000, "Drink", R.drawable.apple_juice);
            insertMenu(db, "EzyFoody Universitas Prasetya Mulya", "Mango Juice", 12, 15000, "Drink", R.drawable.mango_juice);
            insertMenu(db, "EzyFoody Universitas Prasetya Mulya", "Avocado Juice", 33, 15000, "Drink", R.drawable.avocado_juice);
            insertMenu(db, "EzyFoody Universitas Prasetya Mulya", "Iced Tea", 2, 15000, "Drink", R.drawable.iced_tea);
            insertMenu(db, "EzyFoody Universitas Prasetya Mulya", "Bubble Tea", 14, 20000, "Drink", R.drawable.bubble_tea);
            insertMenu(db, "EzyFoody Universitas Prasetya Mulya", "Americano", 5, 20000, "Drink", R.drawable.americano);
            insertMenu(db, "EzyFoody Universitas Prasetya Mulya", "Latte Macchiato", 3, 20000, "Drink", R.drawable.latte_macchiato);
            insertMenu(db, "EzyFoody Universitas Prasetya Mulya", "Mac and Cheese", 12, 35000, "Food", R.drawable.mac_and_cheese);
            insertMenu(db, "EzyFoody Universitas Prasetya Mulya", "Fish and Chips", 30, 50000, "Food", R.drawable.fish_and_chips);
            insertMenu(db, "EzyFoody Universitas Prasetya Mulya", "Lasagna", 2, 55000, "Food", R.drawable.lasagna);
            insertMenu(db, "EzyFoody Universitas Prasetya Mulya", "Skillet Steak", 20, 80000, "Food", R.drawable.skillet_steak);
            insertMenu(db, "EzyFoody Universitas Prasetya Mulya", "Chocolate Cookies", 12, 15000, "Snack", R.drawable.chocolate_chips_cookies);
            insertMenu(db, "EzyFoody Universitas Prasetya Mulya", "Lotus Biscoff Cookies", 13, 20000, "Snack", R.drawable.lotus_cookies);
            insertMenu(db, "EzyFoody Universitas Prasetya Mulya", "Nutella Cookies", 18, 20000, "Snack", R.drawable.nutella_cookies);
            insertMenu(db, "EzyFoody Universitas Prasetya Mulya", "Caramel Cookies", 12, 20000, "Snack", R.drawable.salted_caramel_cookies);
        } else if(oldVersion < 2){

        };
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }
}
