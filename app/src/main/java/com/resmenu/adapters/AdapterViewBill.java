package com.resmenu.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.resmenu.Database.Entity.OrderTable;
import com.resmenu.R;

import java.util.List;

public class AdapterViewBill extends RecyclerView.Adapter<AdapterViewBill.ViewHolderKitchen> {

    private Context mContext;
    private List<OrderTable> list;

    public AdapterViewBill(Context mContext, List<OrderTable> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterViewBill.ViewHolderKitchen onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_bill, viewGroup, false);
        return new AdapterViewBill.ViewHolderKitchen(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewBill.ViewHolderKitchen viewHolderKitchen, int i) {
        viewHolderKitchen.txtName.setText(list.get(i).getMenuName());
        viewHolderKitchen.txtQnt.setText(String.valueOf(list.get(i).getItemQuantity()));
        viewHolderKitchen.itemPrice.setText(String.valueOf(list.get(i).getMenuPrice()));
        Log.e("ssssssssssssssssssss",""+ list.get(i).getTableStatus());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolderKitchen extends RecyclerView.ViewHolder {
        TextView itemPrice, txtName, txtQnt;

        ViewHolderKitchen(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtItemName);
            itemPrice = itemView.findViewById(R.id.txtItmPrice);
            txtQnt = itemView.findViewById(R.id.txtQnt);
        }
    }
}
