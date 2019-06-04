package com.resmenu.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.resmenu.Database.Entity.MyCart;
import com.resmenu.Database.RestaurentMenuDatabase;
import com.resmenu.POJO.MenuItem;
import com.resmenu.R;
import com.resmenu.activity.Activity_WaiterLanding;
import com.resmenu.customViews.CustomButton;
import com.resmenu.customViews.CustomTextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

public class AdapterSubCat extends RecyclerView.Adapter<AdapterSubCat.ViewHolderKitchen> {

    private Context mContext;
    private ArrayList<MenuItem> menuItemArrayList;

    public AdapterSubCat(Context mContext, ArrayList<MenuItem> menuItemArrayList) {
        this.mContext = mContext;
        this.menuItemArrayList = menuItemArrayList;
    }

    @NonNull
    @Override
    public ViewHolderKitchen onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.menu_cat_view, viewGroup, false);
        return new ViewHolderKitchen(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderKitchen viewHolderKitchen, final int i) {
        final int position = viewHolderKitchen.getAdapterPosition();
        viewHolderKitchen.tvMenuTitle.setText(menuItemArrayList.get(position).getItemName());
        viewHolderKitchen.mTvMenuSubTitle.setText(menuItemArrayList.get(position).getItemDescription());
        viewHolderKitchen.mTvPrice.setText("\u20B9" + menuItemArrayList.get(position).getPrice());
        viewHolderKitchen.mBtnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCart myCart = new MyCart();
                myCart.setMenuName(menuItemArrayList.get(position).getItemName());
                myCart.setMenuPrice(menuItemArrayList.get(position).getPrice());
                myCart.setItemQuantity(1);
                // todo need to update with real time data
                myCart.setWaiterId(1);
                myCart.setTableNo(Activity_WaiterLanding.tableNO);

                RestaurentMenuDatabase menuDatabase;
                menuDatabase = RestaurentMenuDatabase.getInstance(mContext);
                menuDatabase.myCartDao().insert(myCart);

                viewHolderKitchen.mBtnAddToCart.setEnabled(false);
                Toast.makeText(mContext, "Added to cart", Toast.LENGTH_SHORT).show();
            }
        });
        //  viewHolderKitchen.imageView.clearColorFilter();
        byte[] decodedString = Base64.decode(menuItemArrayList.get(position).getImg(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        viewHolderKitchen.imageView.setImageBitmap(decodedByte);

       // viewHolderKitchen.imageView.setImageBitmap(bm);
       /* Picasso.with(mContext).load(menuItemArrayList.get(position).getImg()).into(viewHolderKitchen.imageView, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                viewHolderKitchen.imageView.setImageDrawable(mContext.getDrawable(R.drawable.facebook));
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return menuItemArrayList.size();
    }


    class ViewHolderKitchen extends RecyclerView.ViewHolder {
        ImageView imageView;
        CustomButton mBtnAddToCart;
        CustomTextView tvMenuTitle, mTvMenuSubTitle, mTvPrice;
        RatingBar mRatingBarMenu;

        ViewHolderKitchen(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_menu);
            tvMenuTitle = itemView.findViewById(R.id.textView_menu_title);
            mRatingBarMenu = itemView.findViewById(R.id.ratingBarMenu);
            mTvMenuSubTitle = itemView.findViewById(R.id.textView_menu_description);
            mTvPrice = itemView.findViewById(R.id.textView_total_price);
            mBtnAddToCart = itemView.findViewById(R.id.add_cart);

        }
    }
}
