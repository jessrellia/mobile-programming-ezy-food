package com.example.ezyfood;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.MyViewHolder>{

    ArrayList<History> historyList;
    Context context;

    public OrderHistoryAdapter(Context ct, ArrayList<History> history){
        context = ct;
        historyList = history;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.order_history_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryAdapter.MyViewHolder holder, int position) {

        final History curr = historyList.get(position);
        holder.address.setText(curr.getAddress());
        holder.total.setText(curr.getTotal()+"");
        holder.date.setText(curr.getDate());

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return historyList.size();
    }
    public  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView address, total, date;
        ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address_hist);
            total = itemView.findViewById(R.id.total_hist);
            date = itemView.findViewById(R.id.date);
            mainLayout = itemView.findViewById(R.id.cons_hist);
        }
    }

}
