package com.resmenu.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.resmenu.POJO.GetOrder;
import com.resmenu.R;

import java.util.ArrayList;

public class AdapterKitchen extends RecyclerView.Adapter<AdapterKitchen.ViewHolderKitchen> {

    Context mContext;
    ArrayList<GetOrder>arrayList;

    public AdapterKitchen(Context mContext, ArrayList<GetOrder> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolderKitchen onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view= LayoutInflater.from(mContext).inflate(R.layout.order_received_kitchen_view,viewGroup,false);
        return new ViewHolderKitchen(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderKitchen viewHolderKitchen, int i) {
        GetOrder getOrder=arrayList.get(i);
        viewHolderKitchen.txtName.setText(getOrder.getItemName());
        viewHolderKitchen.txtNumber.setText((i+1)+"");
        viewHolderKitchen.txtQnt.setText(getOrder.getQuantity()+"");

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class ViewHolderKitchen extends RecyclerView.ViewHolder{
        TextView txtNumber,txtName,txtQnt;
        public ViewHolderKitchen(@NonNull View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.txtItemName);
            txtNumber=itemView.findViewById(R.id.txtNumber);
            txtQnt=itemView.findViewById(R.id.txtQnt);
        }
    }
}
