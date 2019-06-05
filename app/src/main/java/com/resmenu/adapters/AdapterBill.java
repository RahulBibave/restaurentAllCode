package com.resmenu.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.resmenu.POJO.Bill;
import com.resmenu.R;
import com.resmenu.customViews.CustomTextView;

import java.util.ArrayList;

public class AdapterBill extends RecyclerView.Adapter<AdapterBill.ViewHolderBill>{
    private ArrayList<Bill>mBillArrayList;
    Context mContext;

    public AdapterBill(ArrayList<Bill> mBillArrayList, Context mContext) {
        this.mBillArrayList = mBillArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolderBill onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.bill_view,viewGroup,false);
        return new ViewHolderBill(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderBill viewHolderBill, int i) {
        Bill bill=mBillArrayList.get(i);
        viewHolderBill.txtName.setText(bill.getItemName()+"");
        viewHolderBill.txtQtym.setText(bill.getQuantity()+"");
        viewHolderBill.txtAmount.setText(bill.getAmount()+"");

    }

    @Override
    public int getItemCount() {
        return mBillArrayList.size();
    }

    public class ViewHolderBill extends RecyclerView.ViewHolder{
        CustomTextView txtName,txtQtym,txtAmount;
        public ViewHolderBill(@NonNull View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.txt_billitemName);
            txtAmount=itemView.findViewById(R.id.txt_billitemAmount);
            txtQtym=itemView.findViewById(R.id.txt_billitemQty);
        }
    }
}
