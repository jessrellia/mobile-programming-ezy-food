package com.example.ezyfood;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

    ArrayList<String> menu_names = new ArrayList<>();
    ArrayList<Integer> menu_images = new ArrayList<>();
    ArrayList<Integer> menu_stock = new ArrayList<>();
    ArrayList<Integer> menu_price = new ArrayList<>();
    Context context;
    int[] count,  countDrink, countFood, countSnack;
    String loc, type;
    private static ArrayList<Order> myOrders = new ArrayList<>();
    int balance;
    int flag = 0;

    public  MenuAdapter(Context ct,  String location, String menu_type, Integer bal, ArrayList<String> menu_name, ArrayList<Integer> img, ArrayList<Integer> price, ArrayList<Integer> stock, int[] cntDrink, int[] cntFood, int[] cntSnack){
        context = ct;
        menu_names = menu_name;
        menu_images = img;
        menu_price = price;
        menu_stock = stock;
        countDrink = cntDrink;
        countFood = cntFood;
        countSnack = cntSnack;
        loc = location;
        type = menu_type;
        balance = bal;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.menu_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.menuName.setText(menu_names.get(position));
        holder.menuPrice.setText(menu_price.get(position)+"");
        holder.menuImage.setImageResource(menu_images.get(position));
        Log.d("jalan-3", "jalan");
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MenuDetailsActivity.class);
                if(type.equals("Drink")){
                    countDrink[position]++;
                    if(countDrink[position] > menu_stock.get(position)){
                        Toast toast = Toast.makeText(context, "Not enough quantity!", Toast.LENGTH_SHORT);
                        toast.show();
                        flag = 1;
                    }
                } else if(type.equals("Food")) {
                    countFood[position]++;
                    if(countFood[position] > menu_stock.get(position)){
                        Toast toast = Toast.makeText(context, "Not enough quantity!", Toast.LENGTH_SHORT);
                        toast.show();
                        flag = 1;
                    }
                } else if(type.equals("Snack")){
                    countSnack[position]++;
                    if(countSnack[position] > menu_stock.get(position)){
                        Toast toast = Toast.makeText(context, "Not enough quantity!", Toast.LENGTH_SHORT);
                        toast.show();
                        flag = 1;
                    }
                }

                if(flag!=1){
                    intent.putExtra("menu_names", menu_names.get(position));
                    intent.putExtra("menu_price", menu_price.get(position));
                    intent.putExtra("menu_image", menu_images.get(position));
                    intent.putExtra("menu_stock", menu_stock.get(position));
                    intent.putExtra("menu_count_food", countFood[position]);
                    intent.putExtra("menuCountArrFood", countFood);
                    intent.putExtra("menu_count_drink", countDrink[position]);
                    intent.putExtra("menuCountArrDrink", countDrink);
                    intent.putExtra("menu_count_snack", countSnack[position]);
                    intent.putExtra("menuCountArrSnack", countSnack);
                    intent.putExtra("balance", balance);
                    intent.putExtra("menu_type", type);
                    intent.putExtra("location", loc);

                    int flag = 0;
                    if(myOrders.size() > 0){
                        for(int i=0; i< myOrders.size(); i++){
                            if(myOrders.get(i).getName().equals(menu_names.get(position))) {
                                if(myOrders.get(i).getType().equals("Drink")){
                                    myOrders.get(i).setQty(countDrink[position]);
                                } else if(myOrders.get(i).getType().equals("Food")){
                                    myOrders.get(i).setQty(countFood[position]);
                                } else if(myOrders.get(i).getType().equals("Snack")){
                                    myOrders.get(i).setQty(countSnack[position]);
                                }
                                flag = 1;
                            }
                        }
                        if(flag == 0){
                            if(type.equals("Drink")){
                                Order order = new Order(menu_names.get(position), countDrink[position], menu_price.get(position), menu_images.get(position), type);
                                myOrders.add(order);
                            } else if(type.equals("Food")){
                                Order order = new Order(menu_names.get(position), countFood[position], menu_price.get(position), menu_images.get(position), type);
                                myOrders.add(order);
                            } else if(type.equals("Snack")){
                                Order order = new Order(menu_names.get(position), countSnack[position], menu_price.get(position), menu_images.get(position), type);
                                myOrders.add(order);
                            }
                        }

                    } else {
                        if(type.equals("Drink")){
                            Order order = new Order(menu_names.get(position), countDrink[position], menu_price.get(position), menu_images.get(position), type);
                            myOrders.add(order);
                        } else if(type.equals("Food")){
                            Order order = new Order(menu_names.get(position), countFood[position], menu_price.get(position), menu_images.get(position), type);
                            myOrders.add(order);
                        } else if(type.equals("Snack")){
                            Order order = new Order(menu_names.get(position), countSnack[position], menu_price.get(position), menu_images.get(position), type);
                            myOrders.add(order);
                        }
                    }
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return menu_names.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView menuName, menuPrice;
        ImageView menuImage;
        ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            menuName = itemView.findViewById(R.id.menu_name);
            menuImage = itemView.findViewById(R.id.menu_image);
            menuPrice = itemView.findViewById(R.id.menu_price);
            mainLayout = itemView.findViewById(R.id.menuItems);
        }

    }

    public static ArrayList<Order> getArrayList(){
        return myOrders;
    }
}