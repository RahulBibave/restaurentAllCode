package com.resmenu.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.resmenu.POJO.Table;
import com.resmenu.R;
import com.resmenu.activity.ActivityKitchen;

import java.util.ArrayList;

public class adapter_kitchenmain extends RecyclerView.Adapter<adapter_kitchenmain.ViewHolderMain> {
    private Context mContext;
    private ArrayList<Table>arrayList;

    public adapter_kitchenmain(Context mContext, ArrayList<Table> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolderMain onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.layout_table_item,viewGroup,false);
        return new ViewHolderMain(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMain viewHolderMain,final int i) {

        viewHolderMain.txtTableNo.setText(arrayList.get(i).getTableId()+"");
        if (arrayList.get(i).getBusy()==true){
            viewHolderMain.mLinOuter.setBackground(mContext.getResources().getDrawable(R.drawable.table_checked_outer));
            viewHolderMain.mLinInner.setBackground(mContext.getResources().getDrawable(R.drawable.table_checked_inner));
        }
        viewHolderMain.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, ActivityKitchen.class);
                intent.putExtra("tableNo",arrayList.get(i).getTableId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolderMain extends RecyclerView.ViewHolder {
        TextView txtTableNo;
        LinearLayout mLinInner,mLinOuter;
        public ViewHolderMain(@NonNull View itemView) {
            super(itemView);
            txtTableNo=itemView.findViewById(R.id.txtTableNo);
            mLinInner=itemView.findViewById(R.id.linear_inner);
            mLinOuter=itemView.findViewById(R.id.linear_outer);

        }
    }
}
