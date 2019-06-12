package com.resmenu.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.resmenu.POJO.GetOrder;
import com.resmenu.R;
import com.resmenu.adapters.AdapterKitchen;
import com.resmenu.constants.ApiUrls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.resmenu.activity.MainActivity.ACCESS_TOKEN;
import static com.resmenu.activity.MainActivity.PREF_NAME;

public class ActivityKitchen extends AppCompatActivity {

    private RecyclerView mRecyclerViewKitchen;
    int x;
    private String accesstoken;
    private RequestQueue mRequestQueue;
    private ArrayList<GetOrder>getOrders;
    private Button mBtnSubmit,mBtnMinimize;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        accesstoken = prefs.getString(ACCESS_TOKEN, null);
        x=getIntent().getIntExtra("tableNo",1);
        init();
        getOrder();
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmOrder();
            }
        });
        mBtnMinimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void ConfirmOrder() {
        mRequestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, ApiUrls.mUrlConfirmOrder, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                // Removed this line if you dont need it or Use application/json
                params.put("Authorization", "Bearer " + accesstoken);
                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {

                String str = "{\"TableId\":\"" + x + "\"}";

                return str.getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";

            }


        };
        mRequestQueue.add(stringRequest);

    }

    private void init() {
        mRecyclerViewKitchen=findViewById(R.id.recycler_kitchen);
        mRecyclerViewKitchen.setLayoutManager(new LinearLayoutManager(this));
        mBtnSubmit=findViewById(R.id.btnKitchenSubmit);
        mBtnMinimize=findViewById(R.id.btnMinimize);

    }
    public void getOrder(){
        mRequestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, ApiUrls.mUrlGetOrder, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    Boolean Status=object.getBoolean("Status");
                    if (Status==true){
                        getOrders=new ArrayList<>();
                        JSONObject array = object.getJSONObject("Data");
                        JSONArray jsonArray = array.getJSONArray("Items");
                        Log.e("saaaaaaaaaaaaaaaa",""+jsonArray);
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObjectInner=jsonArray.getJSONObject(i);
                            int ItemId=jsonObjectInner.getInt("ItemId");
                            int CategoryId=jsonObjectInner.getInt("CategoryId");
                            int TableId=jsonObjectInner.getInt("TableId");
                            int Quantity=jsonObjectInner.getInt("Quantity");
                            String ItemName=jsonObjectInner.getString("ItemName");
                            String CategoryName=jsonObjectInner.getString("CategoryName");
                            String WaiterId=jsonObjectInner.getString("WaiterId");
                            String tableName=jsonObjectInner.getString("tableName");

                            GetOrder getOrder=new GetOrder(ItemId,CategoryId,TableId,Quantity,ItemName,CategoryName,WaiterId,tableName);
                            getOrders.add(getOrder);

                        }
                        AdapterKitchen adapterKitchen=new AdapterKitchen(ActivityKitchen.this,getOrders);
                        mRecyclerViewKitchen.setAdapter(adapterKitchen);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                // Removed this line if you dont need it or Use application/json
                params.put("Authorization", "Bearer " + accesstoken);
                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {

                String str = "{\"TableId\":\"" + x + "\"}";

                return str.getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";

            }


        };
        mRequestQueue.add(stringRequest);

    }
}
