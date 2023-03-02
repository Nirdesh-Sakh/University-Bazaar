package com.example.universitybazaarsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.MyViewHolder> {

    private List<SalesModel> listData;

    public SalesAdapter(List<SalesModel> listData) {
        this.listData = listData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SalesModel model = listData.get(position);
        holder.txtsname.setText(model.getSellerName());
        holder.txtiname.setText(model.getItemName());
        holder.txtprice.setText(model.getItemPrice());
        holder.txtlocation.setText(model.getLocation());
        holder.txtdescription.setText(model.getDescription());

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txtiname, txtsname, txtprice, txtlocation, txtdescription;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtiname = (TextView) itemView.findViewById(R.id.inametxt);
            txtsname = (TextView) itemView.findViewById(R.id.snametxt);
            txtprice = (TextView) itemView.findViewById(R.id.pricetxt);
            txtlocation = (TextView) itemView.findViewById(R.id.locationtxt);
            txtdescription = (TextView) itemView.findViewById(R.id.descriptiontxt);
        }
    }
}