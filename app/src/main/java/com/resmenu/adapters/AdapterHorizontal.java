package com.resmenu.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
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
    private int selectedItem ;

    public AdapterHorizontal(Context mContext, ArrayList<String> arrayListMenus, String menu_name, AdapterHorizontal.onClickMenu onClickMenu) {
        this.mContext = mContext;
        this.arrayListMenus = arrayListMenus;
        this.menu_name = menu_name;
        this.onClickMenu = onClickMenu;
    }

/*    @Override
    public void onAttachedToRecyclerView(@NonNull final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();

                // Return false if scrolled to the bounds and allow focus to move off the list
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                        return tryMoveSelection(lm, 1);
                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                        return tryMoveSelection(lm, -1);
                    }
                }

                return false;
            }
        });
    }*/


  /*  private boolean tryMoveSelection(RecyclerView.LayoutManager lm, int direction) {
        int nextSelectItem = selectedItem + direction;

        // If still within valid bounds, move the selection, notify to redraw, and scroll
        if (nextSelectItem = 0 ; nextSelectItem ; getItemCount()) {
            notifyItemChanged(selectedItem);
            selectedItem = nextSelectItem;
            notifyItemChanged(selectedItem);
            lm.scrollToPosition(selectedItem);
            return true;
        }


        return false;
    }*/

    @NonNull
    @Override
    public ViewHolderKitchen onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view= LayoutInflater.from(mContext).inflate(R.layout.menu_tital,viewGroup,false);
        return new ViewHolderKitchen(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderKitchen viewHolderKitchen, final int position) {
           viewHolderKitchen.itemView.setSelected(selectedItem == position);
            viewHolderKitchen.tv_menu_name.setText(arrayListMenus.get(position));

            final int menu_type = viewHolderKitchen.getAdapterPosition();

            if (menu_name.equalsIgnoreCase(arrayListMenus.get(position))){
                viewHolderKitchen.tv_menu_name.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            }

            viewHolderKitchen.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickMenu.itemClicked(v,position);
                    selectedItem=position;
                    notifyDataSetChanged();
                    //viewHolderKitchen.tv_menu_name.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                   // Toast.makeText(mContext, ""+menu_type, Toast.LENGTH_SHORT).show();

                }
            });


            if(selectedItem==position){
             //  holder.row_linearlayout.setBackgroundColor(Color.parseColor("#567845"));
                viewHolderKitchen.tv_menu_name.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            }
             else
           {
              //  holder.row_linearlayout.setBackgroundColor(Color.parseColor("#ffffff"));
               viewHolderKitchen.tv_menu_name.setTextColor(Color.parseColor("#000000"));
           }


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
            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Redraw the old selection and the new
                    notifyItemChanged(selectedItem);
                    selectedItem = getLayoutPosition();
                    notifyItemChanged(selectedItem);
                }
            });*/
        }


    }

    public  interface onClickMenu{
        void itemClicked(View view,int pos);
    }
    public interface onAddClick{
        void itemAddClick(View view);
    }
}
