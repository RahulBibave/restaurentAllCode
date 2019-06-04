package com.resmenu.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.resmenu.Database.Entity.OrderTable;
import com.resmenu.Database.Entity.UserTable;
import com.resmenu.Database.RestaurentMenuDatabase;
import com.resmenu.R;
import com.resmenu.activity.ActivityKitchen;
import com.resmenu.activity.Activity_WaiterLanding;
import com.resmenu.activity.TablesActivity;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class LiveTableAdapter extends RecyclerView.Adapter<LiveTableAdapter.TableViewHolder> {
    Context mContext;
    RestaurentMenuDatabase restaurentMenuDatabase;
    UserTable userTable = new UserTable();
    private SharedPreferences mSharedeSharedPreferences;
    List<OrderTable> orderTables;
    int count;

    public LiveTableAdapter(Context mContext, int count) {
        this.mContext = mContext;
        this.count = count;
        restaurentMenuDatabase = RestaurentMenuDatabase.getInstance(mContext);
        mSharedeSharedPreferences = mContext.getSharedPreferences("restaurant", MODE_PRIVATE);
    }

    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_table_item, viewGroup, false);
        return new TableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder tableViewHolder, final int i) {
        int x = i + 1;
        tableViewHolder.txtTableNo.setText("" + x);

        if (TablesActivity.role.equals("1")) {
            if (i == 0 || i == 1) {
                tableViewHolder.mLinOuter.setBackground(mContext.getResources().getDrawable(R.drawable.table_checked_outer));
                tableViewHolder.mLinInner.setBackground(mContext.getResources().getDrawable(R.drawable.table_checked_inner));
                tableViewHolder.mLinOuter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TablesActivity.role.equals("1")) {
                            Intent intentNext = new Intent(mContext, ActivityKitchen.class);
                            mContext.startActivity(intentNext);
                        } else {

                            Intent intentNext = new Intent(mContext, Activity_WaiterLanding.class);
                            mContext.startActivity(intentNext);
                        }

                    }
                });
            }

        } else {
            //setTableStatus();
            int y=i+1;
            try {
                orderTables = restaurentMenuDatabase.myOrderDao().getDataByTableNo(y);
                for (int j=0;j<orderTables.size();j++){
                    if ( orderTables.get(0).getTableStatus()==true){
                        tableViewHolder.mLinOuter.setBackground(mContext.getResources().getDrawable(R.drawable.table_checked_outer));
                        tableViewHolder.mLinInner.setBackground(mContext.getResources().getDrawable(R.drawable.table_checked_inner));
                    }
                }

            }catch (NullPointerException e){
                e.printStackTrace();
            }


            tableViewHolder.mLinOuter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TablesActivity.role.equals("1")) {
                        Intent intentNext = new Intent(mContext, ActivityKitchen.class);
                        mContext.startActivity(intentNext);
                    } else {

                        SharedPreferences.Editor editor = mContext.getSharedPreferences("restaurant", MODE_PRIVATE).edit();
                        editor.putInt("table_no", i + 1);
                        editor.apply();

                        Intent intentNext = new Intent(mContext, Activity_WaiterLanding.class);
                        userTable.setTableNo(i + 1);
                        restaurentMenuDatabase.myUserTableDao().insert(userTable);
                        mContext.startActivity(intentNext);


                    }

                }
            });
        }

    }

    private void setTableStatus() {

        orderTables = restaurentMenuDatabase.myOrderDao().getDataByTableNo(1);
        orderTables.get(0).getTableStatus();

    }

    @Override
    public int getItemCount() {
        return count;
    }


    public class TableViewHolder extends RecyclerView.ViewHolder {
        TextView txtTableNo;
        LinearLayout mLinInner, mLinOuter;

        public TableViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTableNo = itemView.findViewById(R.id.txtTableNo);
            mLinInner = itemView.findViewById(R.id.linear_inner);
            mLinOuter = itemView.findViewById(R.id.linear_outer);
        }
    }
}
