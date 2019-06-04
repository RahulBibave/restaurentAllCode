package com.resmenu.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.resmenu.Database.Entity.MyCart;
import com.resmenu.Database.RestaurentMenuDatabase;
import com.resmenu.R;
import com.resmenu.activity.Activity_WaiterLanding;
import com.resmenu.customViews.CustomTextView;
import com.resmenu.interfaces.DataTransfer;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolderMyCart> {


    Context mContext;
    List<MyCart> myCartArrayList;
    int quantity;
    private RestaurentMenuDatabase restaurentMenuDatabase;
    DataTransfer dtInterface;


    public MyCartAdapter(Context mContext, List<MyCart> myCartArrayList, RestaurentMenuDatabase restaurentMenuDatabase ,  DataTransfer dtInterface) {
        this.mContext = mContext;
        this.myCartArrayList = myCartArrayList;
        this.restaurentMenuDatabase = restaurentMenuDatabase;
        this.dtInterface = dtInterface;
        totalPriceCount();
    }

    @NonNull
    @Override
    public ViewHolderMyCart onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.mycart_item_view, viewGroup, false);
        return new ViewHolderMyCart(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderMyCart holder, final int position) {

//        final int holder.getAdapterPosition() = i;
        quantity = myCartArrayList.get(holder.getAdapterPosition()).getItemQuantity();

        holder.mItemName.setText(myCartArrayList.get(holder.getAdapterPosition()).getMenuName());
        double price = quantity * myCartArrayList.get(holder.getAdapterPosition()).getMenuPrice();
        holder.mTotal.setText("" + price);
        holder.mQuantity.setText("" + quantity);

        Log.d("TAG", "quantity :- " +quantity+" holder.getAdapterPosition():- "+holder.getAdapterPosition());



    }
    @Override
    public int getItemCount() {
        return myCartArrayList.size();
    }

    public class ViewHolderMyCart extends RecyclerView.ViewHolder {

        CustomTextView mItemName, mQuantity, mTotal;
        ImageView mImgCancle,mMinus,mPlus;

        public ViewHolderMyCart(@NonNull View itemView) {
            super(itemView);
            mItemName = itemView.findViewById(R.id.txt_item_names);
            mMinus = itemView.findViewById(R.id.minus);
            mPlus = itemView.findViewById(R.id.plus);
            mQuantity = itemView.findViewById(R.id.quantity);
            mTotal = itemView.findViewById(R.id.total);
            mImgCancle = itemView.findViewById(R.id.imageView_cancle);

            mPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quantity = myCartArrayList.get(getAdapterPosition()).getItemQuantity();
                    if (quantity >= 0) {
                        quantity = quantity+1;
                        Toast.makeText(mContext, "Plus", Toast.LENGTH_SHORT).show();
                        mQuantity.setText("" + quantity);
                        myCartArrayList.get(getAdapterPosition()).setItemQuantity(quantity);
                        mMinus.setEnabled(true);
                        double total = quantity * myCartArrayList.get(getAdapterPosition()).getMenuPrice();
                        mTotal.setText("" + total);
                        updateDatabase(getAdapterPosition(), quantity , Double.parseDouble(mTotal.getText().toString()));

                    }
                }
            });

            mImgCancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        Log.d("TAG", "before delete :- " + restaurentMenuDatabase.myCartDao().getAll().size());
                        Log.d("TAG", "holder.getAdapterPosition() :- " +  getAdapterPosition() + "  " + myCartArrayList.size());
                        if ( getAdapterPosition() < myCartArrayList.size() &&  getAdapterPosition() != -1) {
                            restaurentMenuDatabase.myCartDao().deleteByUserId(myCartArrayList.get(getAdapterPosition()).getId());
                            Log.d("TAG", "After delete :- " + restaurentMenuDatabase.myCartDao().getAll().size());
                            myCartArrayList.remove( getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                            totalPriceCount();
                        }
                    }catch (ArrayIndexOutOfBoundsException ex){
                        ex.printStackTrace();
                    }
                }
            });

            mMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quantity = myCartArrayList.get(getAdapterPosition()).getItemQuantity();
                    if (quantity <= 0) {
                        Toast.makeText(mContext, "Minus", Toast.LENGTH_SHORT).show();
                        mQuantity.setText("" + quantity);
                        mMinus.setEnabled(false);
                    } else {
                        quantity = quantity-1;
                        mQuantity.setText("" + quantity);
                        double total = quantity * myCartArrayList.get(getAdapterPosition()).getMenuPrice();
                        mTotal.setText("" + total);
                        updateDatabase(getAdapterPosition() , quantity , total);
                        if (quantity == 0) {
                            mMinus.setEnabled(false);
                        } else {
                            myCartArrayList.get(getAdapterPosition()).setItemQuantity(quantity);
                        }
                    }
                }
            });

        }
    }

    private void totalPriceCount() {
        // todo update table id here
        dtInterface.setValues(restaurentMenuDatabase.myCartDao().getTotal(Activity_WaiterLanding.tableNO));
    }

    private void updateDatabase(int position ,int quantity ,double total){
        // Log.d("TAG", "ID :- " + myCartArrayList.get(position).getId()+ "  position " + position);
        Log.d("TAG", " holder.getAdapterPosition():- "+position+" getMenuPrice:- "+myCartArrayList.get(position).getMenuPrice() );
        restaurentMenuDatabase.myCartDao().updateById(myCartArrayList.get(position).getId(),quantity);
        totalPriceCount();
    }
  /*  Context mContext;
    List<MyCart> myCartArrayList;
    int quantity;
    private RestaurentMenuDatabase restaurentMenuDatabase;
    DataTransfer dtInterface;


    public MyCartAdapter(Context mContext, List<MyCart> myCartArrayList, RestaurentMenuDatabase restaurentMenuDatabase ,  DataTransfer dtInterface) {
        this.mContext = mContext;
        this.myCartArrayList = myCartArrayList;
        this.restaurentMenuDatabase = restaurentMenuDatabase;
        this.dtInterface = dtInterface;
        totalPriceCount();
    }

    @NonNull
    @Override
    public ViewHolderMyCart onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.mycart_item_view, viewGroup, false);
        return new ViewHolderMyCart(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderMyCart holder, final int position) {

//        final int holder.getAdapterPosition() = i;
        quantity = myCartArrayList.get(holder.getAdapterPosition()).getItemQuantity();

        holder.mItemName.setText(myCartArrayList.get(holder.getAdapterPosition()).getMenuName());
        double price = quantity * myCartArrayList.get(holder.getAdapterPosition()).getMenuPrice();
        holder.mTotal.setText("" + price);
        holder.mQuantity.setText("" + quantity);

        Log.d("TAG", "quantity :- " +quantity+" holder.getAdapterPosition():- "+holder.getAdapterPosition());



    }
    @Override
    public int getItemCount() {
        return myCartArrayList.size();
    }

    public class ViewHolderMyCart extends RecyclerView.ViewHolder {

        CustomTextView mItemName, mMinus, mPlus, mQuantity, mTotal;
        ImageView mImgCancle;

        public ViewHolderMyCart(@NonNull View itemView) {
            super(itemView);
            mItemName = itemView.findViewById(R.id.txt_item_name);
            mMinus = itemView.findViewById(R.id.minus);
            mPlus = itemView.findViewById(R.id.plus);
            mQuantity = itemView.findViewById(R.id.quantity);
            mTotal = itemView.findViewById(R.id.total);
            mImgCancle = itemView.findViewById(R.id.imageView_cancle);

            mPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quantity = myCartArrayList.get(getAdapterPosition()).getItemQuantity();
                    if (quantity >= 0) {
                        quantity = quantity+1;
                        Toast.makeText(mContext, "Plus", Toast.LENGTH_SHORT).show();
                        mQuantity.setText("" + quantity);
                        myCartArrayList.get(getAdapterPosition()).setItemQuantity(quantity);
                        mMinus.setEnabled(true);
                        double total = quantity * myCartArrayList.get(getAdapterPosition()).getMenuPrice();
                        mTotal.setText("" + total);
                        updateDatabase(getAdapterPosition() , quantity , total);

                    }
                }
            });

            mImgCancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        Log.d("TAG", "before delete :- " + restaurentMenuDatabase.myCartDao().getAll().size());
                        Log.d("TAG", "holder.getAdapterPosition() :- " +  getAdapterPosition() + "  " + myCartArrayList.size());
                        if ( getAdapterPosition() < myCartArrayList.size() &&  getAdapterPosition() != -1) {
                             restaurentMenuDatabase.myCartDao().deleteByUserId(myCartArrayList.get(getAdapterPosition()).getId());
                            Log.d("TAG", "After delete :- " + restaurentMenuDatabase.myCartDao().getAll().size());
                            myCartArrayList.remove( getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                            totalPriceCount();
                        }
                    }catch (ArrayIndexOutOfBoundsException ex){
                        ex.printStackTrace();
                    }
                }
            });

            mMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quantity = myCartArrayList.get(getAdapterPosition()).getItemQuantity();
                    if (quantity <= 0) {
                        Toast.makeText(mContext, "Minus", Toast.LENGTH_SHORT).show();
                        mQuantity.setText("" + quantity);
                        mMinus.setEnabled(false);
                    } else {
                        quantity = quantity-1;
                        mQuantity.setText("" + quantity);
                        double total = quantity * myCartArrayList.get(getAdapterPosition()).getMenuPrice();
                        mTotal.setText("" + total);
                        updateDatabase(getAdapterPosition() , quantity , total);
                        if (quantity == 0) {
                            mMinus.setEnabled(false);
                        } else {
                            myCartArrayList.get(getAdapterPosition()).setItemQuantity(quantity);
                        }
                    }
                }
            });

        }
    }

    private void totalPriceCount() {
        dtInterface.setValues(restaurentMenuDatabase.myCartDao().getTotal(1));
    }

    private void updateDatabase(int position ,int quantity ,double total){
       // Log.d("TAG", "ID :- " + myCartArrayList.get(position).getId()+ "  position " + position);
        Log.d("TAG", " holder.getAdapterPosition():- "+position+" getMenuPrice:- "+myCartArrayList.get(position).getMenuPrice() );
        restaurentMenuDatabase.myCartDao().updateById(myCartArrayList.get(position).getId(),quantity);
        totalPriceCount();
    }
*/}
