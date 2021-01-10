package com.example.ezyfood;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder>{
    Context context;
    int balance;
    private static ArrayList<Order> myOrderArray = new ArrayList<>();

    public OrderAdapter(Context ct, Integer bal, ArrayList<Order>myOrder){
        context = ct;
        balance = bal;
        myOrderArray = myOrder;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =  inflater.inflate(R.layout.order_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Order curr = myOrderArray.get(position);
        holder.qty.setText(curr.getQty()+"");
        holder.img.setImageResource(curr.getImg());
        holder.price.setText(curr.getPrice()+"");
        holder.name.setText(curr.getName());
        final ArrayList<Order> myOrder = MenuAdapter.getArrayList();
        holder.delete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                myOrderArray.remove(curr);
                OrderActivity.myOrderAdapter.notifyDataSetChanged();
                Intent i = new Intent(v.getContext(), OrderActivity.class);
                i.putExtra("balance", balance);
                v.getContext().startActivity(i);
                if (context instanceof Activity) {
                    ((Activity) context).overridePendingTransition(0, 0);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return myOrderArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView qty, price, name;
        ImageView img;
        Button delete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.order_details_img);
            qty = itemView.findViewById(R.id.order_details_qty);
            price = itemView.findViewById(R.id.order_details_price);
            name = itemView.findViewById(R.id.order_details_name);
            delete = itemView.findViewById(R.id.deleteButton);
        }
    }

    public  static ArrayList<Order> getArrayList(){
        return myOrderArray;
    }
}
