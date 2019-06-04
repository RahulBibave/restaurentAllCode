package com.resmenu.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.resmenu.R;
import com.resmenu.customViews.CustomTextView;

import java.util.ArrayList;

public class AdapterHorizontal extends RecyclerView.Adapter<AdapterHorizontal.ViewHolderKitchen> {

    Context mContext;
    ArrayList<String> arrayListMenus;
    String menu_name;
    onClickMenu onClickMenu;

    public AdapterHorizontal(Context mContext, ArrayList<String> arrayListMenus, String menu_name, AdapterHorizontal.onClickMenu onClickMenu) {
        this.mContext = mContext;
        this.arrayListMenus = arrayListMenus;
        this.menu_name = menu_name;
        this.onClickMenu = onClickMenu;
    }



    @NonNull
    @Override
    public ViewHolderKitchen onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view= LayoutInflater.from(mContext).inflate(R.layout.menu_tital,viewGroup,false);
        return new ViewHolderKitchen(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderKitchen viewHolderKitchen, final int position) {

            viewHolderKitchen.tv_menu_name.setText(arrayListMenus.get(position));

            final int menu_type = viewHolderKitchen.getAdapterPosition();

            if (menu_name.equalsIgnoreCase(arrayListMenus.get(position))){
                //viewHolderKitchen.tv_menu_name.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            }

            viewHolderKitchen.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickMenu.itemClicked(v,position);
                    //viewHolderKitchen.tv_menu_name.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                   // Toast.makeText(mContext, ""+menu_type, Toast.LENGTH_SHORT).show();

                }
            });


    }

    @Override
    public int getItemCount() {
        return arrayListMenus.size();
    }


    public class ViewHolderKitchen extends RecyclerView.ViewHolder{
        CustomTextView tv_menu_name;
        public ViewHolderKitchen(@NonNull View itemView) {
            super(itemView);
            tv_menu_name = itemView.findViewById(R.id.tv_menu_name);
        }
    }

    public  interface onClickMenu{
        void itemClicked(View view,int pos);
    }
}
