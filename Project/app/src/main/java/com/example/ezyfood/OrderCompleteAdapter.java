package com.example.ezyfood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderCompleteAdapter extends RecyclerView.Adapter<OrderCompleteAdapter.MyViewHolder> {

    Context context;
    ArrayList<Order> myCompletedOrder;

    public OrderCompleteAdapter(Context ct, ArrayList<Order> completedOrder){
        context = ct;
        myCompletedOrder = completedOrder;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.order_complete_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Order curr = myCompletedOrder.get(position);
        holder.img.setImageResource(curr.getImg());
        holder.qty.setText(curr.getQty()+"");
        holder.price.setText(curr.getPrice()+"");
        holder.name.setText(curr.getName());
    }

    @Override
    public int getItemCount() {
        return myCompletedOrder.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView qty, price, name;
        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.corder_details_img);
            qty = itemView.findViewById(R.id.corder_details_qty);
            price = itemView.findViewById(R.id.corder_details_price);
            name = itemView.findViewById(R.id.corder_details_name);
        }
    }
}
