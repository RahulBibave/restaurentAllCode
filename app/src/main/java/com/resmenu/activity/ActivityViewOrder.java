package com.resmenu.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.resmenu.Database.Entity.OrderTable;
import com.resmenu.Database.RestaurentMenuDatabase;
import com.resmenu.R;
import com.resmenu.adapters.AdapterViewBill;
import com.resmenu.customViews.CustomTextView;

import java.util.ArrayList;
import java.util.List;

public class ActivityViewOrder extends AppCompatActivity {

    private RestaurentMenuDatabase restaurentMenuDatabase;
    private List<OrderTable> userTables;
    private CustomTextView mAmountTextView;
    private RecyclerView mRecyclerViewKitchen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bill);

        mAmountTextView = findViewById(R.id.tv_amount);
        userTables = new ArrayList<>();

        mRecyclerViewKitchen = findViewById(R.id.recycler_virw_cart);
        mRecyclerViewKitchen.setLayoutManager(new LinearLayoutManager(this));

        restaurentMenuDatabase = RestaurentMenuDatabase.getInstance(ActivityViewOrder.this);
        getBill();
    }

    public void getBill() {

        SharedPreferences mSharedeSharedPreferences = getSharedPreferences("restaurant", MODE_PRIVATE);

        userTables = restaurentMenuDatabase.myOrderDao().getDataByTableNo(mSharedeSharedPreferences.getInt("table_no", 0));
        mAmountTextView.setText(String.valueOf(restaurentMenuDatabase.myOrderDao().getTotal(mSharedeSharedPreferences.getInt("table_no", 0))));

        AdapterViewBill adapterViewBill = new AdapterViewBill(this, userTables);
        mRecyclerViewKitchen.setAdapter(adapterViewBill);
    }
}
