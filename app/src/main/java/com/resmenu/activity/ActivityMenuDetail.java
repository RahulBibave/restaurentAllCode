package com.resmenu.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.resmenu.Database.Entity.MyCart;
import com.resmenu.Database.RestaurentMenuDatabase;
import com.resmenu.R;
import com.resmenu.customViews.CustomTextView;

public class ActivityMenuDetail extends AppCompatActivity {
    private String name,Desc,Img;
    private Double price;
    int table_no;
    ImageView imgDetails;
    CustomTextView txtTital,txtPrice,txtDesc,quantity;
    ImageView plus,minus;
    int x=1;
    Button cancle,addtoCart;
    ImageView ibmycart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);
        name=getIntent().getStringExtra("name");
        Desc=getIntent().getStringExtra("Desc");
        price=getIntent().getDoubleExtra("price",0.0);
        table_no=getIntent().getIntExtra("table_no",0);
        Img=getIntent().getStringExtra("Img");
        init();
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (x>0){
                    x=x+1;
                    quantity.setText(x+"");
                }
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (x>1){
                    x=x-1;
                    quantity.setText(x+"");
                }

            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCart myCart = new MyCart();
                myCart.setMenuName(name);
                myCart.setMenuPrice(price);
                myCart.setItemQuantity(Integer.parseInt(quantity.getText().toString()));
                // todo need to update with real time data
                myCart.setWaiterId(Activity_WaiterLanding.waiterID);
                myCart.setTableNo(Activity_WaiterLanding.tableNO);

                RestaurentMenuDatabase menuDatabase;
                menuDatabase = RestaurentMenuDatabase.getInstance(ActivityMenuDetail.this);
                menuDatabase.myCartDao().insert(myCart);
                finish();
            }
        });
        ibmycart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityMenuDetail.this, MyCartActivity.class));
                finish();
            }
        });

    }

    private void init() {
        imgDetails=findViewById(R.id.imgDetails);
        byte[] decodedString = Base64.decode(Img, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imgDetails.setImageBitmap(decodedByte);
        txtTital=findViewById(R.id.txtTital);
        txtTital.setText(name);
        txtPrice=findViewById(R.id.txtPrice);
        txtPrice.setText("\u20B9" +price);
        txtDesc=findViewById(R.id.txtDesc);
        txtDesc.setText(Desc);
        plus=findViewById(R.id.plus);
        minus=findViewById(R.id.minus);
        quantity=findViewById(R.id.quantity);
        addtoCart=findViewById(R.id.btnAdd);
        cancle=findViewById(R.id.btnCancle);
        ibmycart=findViewById(R.id.ibmycart);
    }
}
