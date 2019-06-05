package com.resmenu.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.resmenu.Database.Entity.MyCart;
import com.resmenu.Database.Entity.OrderTable;
import com.resmenu.Database.Entity.UserTable;
import com.resmenu.Database.RestaurentMenuDatabase;
import com.resmenu.POJO.MenuItem;
import com.resmenu.R;
import com.resmenu.adapters.AdapterSubCat;
import com.resmenu.adapters.MyCartAdapter;
import com.resmenu.constants.ApiUrls;
import com.resmenu.customViews.CustomButton;
import com.resmenu.customViews.CustomTextView;
import com.resmenu.interfaces.DataTransfer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.resmenu.activity.MainActivity.ACCESS_TOKEN;
import static com.resmenu.activity.MainActivity.PREF_NAME;

public class MyCartActivity extends AppCompatActivity implements DataTransfer {


    private RecyclerView mRecyclerViewCart;
    private MyCartAdapter myCartAdapter;
    private List<MyCart> myCartArrayList;
    RestaurentMenuDatabase restaurentMenuDatabase;

    private CustomButton mBtnPproceedtopay;
    private CustomButton mBtnContinueorde;
    private CustomTextView mTvNoItems, mTvTotalAMount;
    private RequestQueue mRequestQueue;
    private String accesstoken;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        accesstoken = prefs.getString(ACCESS_TOKEN, null);

        mBtnPproceedtopay = findViewById(R.id.btn_proceedtopay);
        mBtnContinueorde = findViewById(R.id.btn_continueorder);
        mTvTotalAMount = findViewById(R.id.tv_amount);

        mTvNoItems = findViewById(R.id.tv_no_items);

        mRecyclerViewCart = findViewById(R.id.recycler_virw_cart);
        myCartArrayList = new ArrayList<>();

        getDatabaseList();

        mBtnContinueorde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        mBtnPproceedtopay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOrder();
            }
        });

    }

    private void getDatabaseList() {

        restaurentMenuDatabase = RestaurentMenuDatabase.getInstance(this);
        myCartArrayList = restaurentMenuDatabase.myCartDao().getAll();

        if (myCartArrayList.size() == 0 && myCartArrayList.isEmpty()) {
            mTvNoItems.setVisibility(View.VISIBLE);
            mBtnPproceedtopay.setEnabled(false);
            mBtnContinueorde.setEnabled(false);
        } else {
            myCartAdapter = new MyCartAdapter(this, myCartArrayList, restaurentMenuDatabase, this);
            mRecyclerViewCart.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerViewCart.setAdapter(myCartAdapter);
        }
    }

    @Override
    public void setValues(double total) {
        mTvTotalAMount.setText("" + total);
    }

    public void submitOrder() {

        mRequestQueue = Volley.newRequestQueue(this);
        final StringRequest request = new StringRequest(Request.Method.POST, ApiUrls.mUrlSubmitOrder, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progressDialog.dismiss();
                RestaurentMenuDatabase menuDatabase;

                Log.e("sadddsasasa", "" + s.toString());

                try {

                    JSONObject object = new JSONObject(s);
                    Boolean sucess_code = object.getBoolean("Status");
                    String msg = object.getString("Message");
                    if (sucess_code.equals(true)) {

                       copyValuesToDatabase(myCartArrayList);

                        AlertDialog.Builder builder = new AlertDialog.Builder(MyCartActivity.this);
                        builder.setMessage(msg)
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                        //do things
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                Log.e("saddd", volleyError.toString());

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

                JSONObject jsonObject = new JSONObject();
               /* try {
                    jsonObject.put("TotalAmmount", Double.parseDouble(mTvTotalAMount.getText().toString()));
                    jsonObject.put("tableId",Activity_WaiterLanding.tableNO);
                    jsonObject.put("orderBy",Activity_WaiterLanding.waiterID);
                    jsonObject.put("customerEmailId",Activity_WaiterLanding.email);
                    jsonObject.put("MobileNumber","9808982015");
                    jsonObject.put("CustomerName",Activity_WaiterLanding.Cu_name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/

                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < myCartArrayList.size(); i++) {
                    JSONObject jsonObject1 = new JSONObject();
                    try {
                        double diss = 0.0;
                        jsonObject1.put("ItemId", myCartArrayList.get(i).getId() + "");
                        jsonObject1.put("CategoryId", "");
                        jsonObject1.put("TableId", Activity_WaiterLanding.tableNO +"");
                         jsonObject1.put("ItemName",myCartArrayList.get(i).getMenuName()+"");
                        jsonObject1.put("WaiterId", Activity_WaiterLanding.waiterID + "");
                        jsonObject1.put("Quantity", myCartArrayList.get(i).getItemQuantity() + "");
                       /* UserTable userTable = new UserTable();
                        userTable.setItemId(myCartArrayList.get(i).getId()+"");
                        userTable.setItemQuantity(myCartArrayList.get(i).getItemQuantity());
                        userTable.setMenuName(myCartArrayList.get(i).getMenuName());
                        userTable.setMenuPrice(myCartArrayList.get(i).getMenuPrice());
                        restaurentMenuDatabase.myUserTableDao().insert(userTable);*/

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                    jsonArray.put(jsonObject1);
                }

                try {
                    jsonObject.put("Items", jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.e("xxxxxxxxxxxxxx", "" + jsonObject.toString());
                String str = jsonObject.toString();
                return str.getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        mRequestQueue.add(request);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    private void copyValuesToDatabase(List<MyCart> myCartArrayList) {
        SharedPreferences  mSharedeSharedPreferences = getSharedPreferences("restaurant", MODE_PRIVATE);
        for (int i = 0; i < myCartArrayList.size(); i++) {
            OrderTable userTable = new OrderTable();
            userTable.setTableNo(mSharedeSharedPreferences.getInt("table_no",0));
            userTable.setItemId("" + myCartArrayList.get(i).getId());
            userTable.setItemQuantity(myCartArrayList.get(i).getItemQuantity());
            userTable.setMenuName(myCartArrayList.get(i).getMenuName());
            userTable.setMenuPrice(myCartArrayList.get(i).getMenuPrice());
            userTable.setTableStatus(true);
            userTable.setWaiterId(Activity_WaiterLanding.waiterID);
            userTable.setUserName(Activity_WaiterLanding.Cu_name);
            userTable.setMobileNo(Activity_WaiterLanding.mobile);
            userTable.setUserEmail(Activity_WaiterLanding.email);
            restaurentMenuDatabase.myOrderDao().insert(userTable);
        }

        restaurentMenuDatabase.myCartDao().deleteAll();
        myCartArrayList.clear();
    }
}
