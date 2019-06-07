package com.resmenu.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.resmenu.Database.Entity.MyCart;
import com.resmenu.Database.Entity.OrderTable;
import com.resmenu.Database.RestaurentMenuDatabase;
import com.resmenu.POJO.MenuItem;
import com.resmenu.R;
import com.resmenu.adapters.AdapterHorizontal;
import com.resmenu.adapters.AdapterSubCat;
import com.resmenu.adapters.MyCartAdapter;
import com.resmenu.constants.ApiUrls;
import com.resmenu.customViews.CustomButton;
import com.resmenu.customViews.CustomEditText;
import com.resmenu.customViews.CustomTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.resmenu.activity.MainActivity.ACCESS_TOKEN;
import static com.resmenu.activity.MainActivity.PREF_NAME;

public class AllMenuActivity extends AppCompatActivity implements AdapterHorizontal.onClickMenu,AdapterHorizontal.onAddClick{
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
    CustomTextView customTextView;
    private List<MyCart> myCartArrayList;
    RestaurentMenuDatabase restaurentMenuDatabase;
    private ImageView ibsearch,imgCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_menu);
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        accesstoken = prefs.getString(ACCESS_TOKEN, null);
        customTextView=findViewById(R.id.toolbar_title);
        customTextView.setText("Menu List");
        ibsearch=findViewById(R.id.ibsearch);
        imgCart=findViewById(R.id.ibmycart);


        mSubCategory = new HashMap<>();

        Bundle bundle = getIntent().getExtras();
        menu_type = bundle.getInt("menu_id");
        menu_name = bundle.getString("menu_name");
        Log.e(TAG, "Menu_type  :- " + menu_type);
        arrayListMenus = bundle.getStringArrayList("menu_list");
        arrayList=bundle.getIntegerArrayList("menuIDList");

        mBtnConfirmOrder = findViewById(R.id.btn_confirm_order);
        mBtnViewCart = findViewById(R.id.btn_viweCart);
        getDatabaseList();
        mBtnConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOrder();
//                startActivity(new Intent(AllMenuActivity.this, Activity_WaiterLanding.class));
            }
        });
        mBtnViewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllMenuActivity.this, MyCartActivity.class));
            }
        });
        ibsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*final AlertDialog.Builder builder = new AlertDialog.Builder(AllMenuActivity.this);

                 View view = LayoutInflater.from(AllMenuActivity.this).inflate(R.layout.dialog_search, null);

                final CustomEditText title = (CustomEditText) view.findViewById(R.id.title);
                CustomButton customButton = (CustomButton) view.findViewById(R.id.btnExit);
                customButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getSearch(title.getText().toString());

                    }
                });*/
                // custom dialog
                final Dialog dialog = new Dialog(AllMenuActivity.this);
                dialog.setContentView(R.layout.dialog_search);
                dialog.setTitle("Title...");

                // set the custom dialog components - text, image and button
               final CustomEditText text = (CustomEditText) dialog.findViewById(R.id.title);
               // text.setText("Android custom dialog example!");
                CustomButton customButton = (CustomButton) dialog.findViewById(R.id.btnExit);



                // if button is clicked, close the custom dialog
                customButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (text.getText().toString().equals("")){
                            dialog.dismiss();
                        }
                        else {
                            getSearch(text.getText().toString());
                        }
                        dialog.dismiss();

                    }
                });

                dialog.show();

            }
        });
        imgCart.setOnClickListener(new View.OnClickListener() {
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
                            String CategoryName=jsonObject.getString("CategoryName");

                            Double quantity = jsonObject.getDouble("Quantity");
                            Log.e("xxxxxxxxxxx", "" + CategoryName);
                            MenuItem menuItem = new MenuItem(id, ItemName , ItemDescription, ItemPrize, discount, isAcive, quantity,image,CategoryName);
                            menuItemArrayList.add(menuItem);

                        }
                        adapterSubCat = new AdapterSubCat(AllMenuActivity.this, menuItemArrayList,AllMenuActivity.this);
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
        getDatabaseList();
    }

    public void getSearch(final String txtSearch){
        mRequestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, ApiUrls.mUrlSearch, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("aaaaaaaaaaaaaaaaaaaaaaaaaaaa",""+response);
                    JSONObject object = new JSONObject(response);
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
                            String CategoryName=jsonObject.getString("CategoryName");

                            Double quantity = jsonObject.getDouble("Quantity");
                            Log.e("dddddddddd", "" + image);
                            MenuItem menuItem = new MenuItem(id, ItemName , ItemDescription, ItemPrize, discount, isAcive, quantity,image,CategoryName);
                            menuItemArrayList.add(menuItem);

                        }
                        adapterSubCat = new AdapterSubCat(AllMenuActivity.this, menuItemArrayList,AllMenuActivity.this);
                        mRecyclerMenuList.setAdapter(adapterSubCat);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

                String str = "{\"ItemName\":\""+txtSearch+"\"}";

                return str.getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        mRequestQueue.add(stringRequest);
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

                        AlertDialog.Builder builder = new AlertDialog.Builder(AllMenuActivity.this);
                        builder.setMessage(msg)
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent=new Intent(AllMenuActivity.this,TablesActivity.class);
                                        startActivity(intent);
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
                        jsonObject1.put("TableId", myCartArrayList.get(i).getTableNo() +"");
                        jsonObject1.put("ItemName",myCartArrayList.get(i).getMenuName()+"");
                        jsonObject1.put("WaiterId", Activity_WaiterLanding.waiterID + "");
                        jsonObject1.put("Quantity", myCartArrayList.get(i).getItemQuantity() + "");
                       /* UserTable userTable = new UserTable();
                        userTable.setItemId(myCartArrayList.get(i).getId()+"");
                        userTable.setItemQuantity(myCartArrayList.get(i).getItemQuantity());
                        userTable.setMenuName(myCartArrayList.get(i).getMenuName());
                        userTable.setMenuPrice(myCartArrayList.get(i).getMenuPrice());
                        restaurentMenuDatabase.myUserTableDao().insert(userTable);*/
                        Log.e("zaaaaaaaaaaaaaaaaaaaaaaaaaaa",Activity_WaiterLanding.waiterID + ""+myCartArrayList.get(i).getId() + ",,"+myCartArrayList.get(i).getTableNo() +"77"+myCartArrayList.get(i).getMenuName()+"444  "+myCartArrayList.get(i).getItemQuantity() + "");

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


    private void getDatabaseList() {

        restaurentMenuDatabase = RestaurentMenuDatabase.getInstance(this);
        myCartArrayList = restaurentMenuDatabase.myCartDao().getAll();

        if (myCartArrayList.size() == 0 && myCartArrayList.isEmpty()) {
           mBtnConfirmOrder.setEnabled(false);
        }else {
            mBtnConfirmOrder.setEnabled(true);
        }
    }

    @Override
    public void itemAddClick(View view) {
        getDatabaseList();
    }
}