package com.resmenu.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.resmenu.POJO.MenuItem;
import com.resmenu.R;
import com.resmenu.adapters.AdapterHorizontal;
import com.resmenu.adapters.AdapterSubCat;
import com.resmenu.constants.ApiUrls;
import com.resmenu.customViews.CustomButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.resmenu.activity.MainActivity.ACCESS_TOKEN;
import static com.resmenu.activity.MainActivity.PREF_NAME;

public class AllMenuActivity extends AppCompatActivity implements AdapterHorizontal.onClickMenu{
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerMenuList;
    private RequestQueue mRequestQueue;
    ArrayList<String> arrayListMenus;
    ArrayList<Integer>arrayList;

    HashMap<String, String> mSubCategory;

    private AdapterHorizontal adapterHorizontal;
    private AdapterSubCat adapterSubCat;

    public static final String TAG = AllMenuActivity.class.getSimpleName();
    String menu_name;
    Integer menu_type;
    ArrayList<MenuItem> menuItemArrayList;

    CustomButton mBtnConfirmOrder;
    CustomButton mBtnViewCart;
    String accesstoken;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_menu);
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        accesstoken = prefs.getString(ACCESS_TOKEN, null);

        mSubCategory = new HashMap<>();

        Bundle bundle = getIntent().getExtras();
        menu_type = bundle.getInt("menu_id");
        menu_name = bundle.getString("menu_name");
        Log.e(TAG, "Menu_type  :- " + menu_type);
        arrayListMenus = bundle.getStringArrayList("menu_list");
        arrayList=bundle.getIntegerArrayList("menuIDList");

        mBtnConfirmOrder = findViewById(R.id.btn_confirm_order);
        mBtnViewCart = findViewById(R.id.btn_viweCart);
        mBtnConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(AllMenuActivity.this, Activity_WaiterLanding.class));
            }
        });
        mBtnViewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllMenuActivity.this, MyCartActivity.class));
            }
        });
        init();
    }

    private void init() {
        mRecyclerView = findViewById(R.id.recycler_menu_tital);
        mRecyclerMenuList = findViewById(R.id.recycler_menu_List);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerMenuList.setLayoutManager(new LinearLayoutManager(this));

        adapterHorizontal = new AdapterHorizontal(this, arrayListMenus, menu_name,this);
        mRecyclerView.setAdapter(adapterHorizontal);


        getMenuItem( menu_type);

    }

    public void getMenuItem(final int menuID) {
        mRequestQueue = Volley.newRequestQueue(this);
        final StringRequest request = new StringRequest(Request.Method.POST, ApiUrls.mUrlSubCategories, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progressDialog.dismiss();
                Log.e("sadddsasasa", "" + s.toString());

                try {

                    JSONObject object = new JSONObject(s);
                    Boolean sucess_code = object.getBoolean("Status");
                    if (sucess_code.equals(true)) {
                        menuItemArrayList = new ArrayList<>();
                        JSONArray array = object.getJSONArray("Data");

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject = array.getJSONObject(i);
                            int id = jsonObject.getInt("ItemId");
                           // int ItemRating = jsonObject.getInt("ItemRating");
                            String ItemName = jsonObject.getString("ItemName");
                            String ItemDescription = jsonObject.getString("ItemDescription");
                            Double ItemPrize = jsonObject.getDouble("Price");
                            Double discount = jsonObject.getDouble("Disscount");
                            Boolean isAcive = jsonObject.getBoolean("IsActive");
                            String image =jsonObject.getString("Img");

                            Double quantity = jsonObject.getDouble("Quantity");
                            Log.e("dddddddddd", "" + image);
                            MenuItem menuItem = new MenuItem(id, ItemName , ItemDescription, ItemPrize, discount, isAcive, quantity,image);
                            menuItemArrayList.add(menuItem);

                        }
                        adapterSubCat = new AdapterSubCat(AllMenuActivity.this, menuItemArrayList);
                        mRecyclerMenuList.setAdapter(adapterSubCat);
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

                String str = "{\"MenuId\":\""+menuID+"\"}";

                return str.getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        mRequestQueue.add(request);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    @Override
    public void itemClicked(View view, int pos) {
        getMenuItem(arrayList.get(pos));
    }
}