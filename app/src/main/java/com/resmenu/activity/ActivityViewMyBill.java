package com.resmenu.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.resmenu.Database.Entity.OrderTable;
import com.resmenu.Database.RestaurentMenuDatabase;
import com.resmenu.POJO.Bill;
import com.resmenu.R;
import com.resmenu.adapters.AdapterBill;
import com.resmenu.constants.ApiUrls;
import com.resmenu.customViews.CustomButton;
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

public class ActivityViewMyBill extends AppCompatActivity {
    private RestaurentMenuDatabase restaurentMenuDatabase;
    private List<OrderTable> userTables;
    private CustomTextView mAmountTextView;
    private RecyclerView mRecyclerViewKitchen;
    private RequestQueue mRequestQueue;
    private ProgressDialog progressDialog;
    private ArrayList<Bill>billArrayList;
    private String accesstoken;
    SharedPreferences mSharedeSharedPreferences;
    TextView txtTotal,txtDiss,txtGst,txtfinal;
    CustomButton btnPay;
    int tableNO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_bill);
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        accesstoken = prefs.getString(ACCESS_TOKEN, null);
        mSharedeSharedPreferences = getSharedPreferences("restaurant", MODE_PRIVATE);
        tableNO=mSharedeSharedPreferences.getInt("table_no", 0);
        txtTotal=findViewById(R.id.total);
        txtDiss=findViewById(R.id.txtDiss);
        txtGst=findViewById(R.id.txtGST);
        txtfinal=findViewById(R.id.txtFInal);
        btnPay=findViewById(R.id.btnPay);
       // mAmountTextView = findViewById(R.id.tv_amount);
        userTables = new ArrayList<>();

        mRecyclerViewKitchen = findViewById(R.id.recycler_bill);
        mRecyclerViewKitchen.setLayoutManager(new LinearLayoutManager(this));

        restaurentMenuDatabase = RestaurentMenuDatabase.getInstance(ActivityViewMyBill.this);
        getBill();
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO delete by table no
                restaurentMenuDatabase.myOrderDao().deleteByTableId(tableNO);
                restaurentMenuDatabase.myOrderDao().getAll();
                Intent intent=new Intent(ActivityViewMyBill.this,TablesActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void getBill() {
        userTables = restaurentMenuDatabase.myOrderDao().getDataByTableNo(mSharedeSharedPreferences.getInt("table_no", 0));
       // mAmountTextView.setText(String.valueOf(restaurentMenuDatabase.myOrderDao().getTotal(mSharedeSharedPreferences.getInt("table_no", 0))));
        get_Bill();
    }
    public void get_Bill(){
        mRequestQueue = Volley.newRequestQueue(this);
        final StringRequest request = new StringRequest(Request.Method.POST, ApiUrls.mUrlGetBill, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progressDialog.dismiss();
                billArrayList=new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(s);
                    Boolean sucess_code = object.getBoolean("Status");

                    if (sucess_code.equals(true)) {
                        JSONObject array = object.getJSONObject("Data");
                        String OrderId=array.getString("OrderId");
                        int tableId=array.getInt("tableId");
                        String OrderDate=array.getString("OrderDate");
                        String OrderNumber=array.getString("OrderNumber");
                        String orderBy =array.getString("orderBy");
                        String CustomerName=array.getString("CustomerName");
                        String customerEmailId=array.getString("customerEmailId");
                        String MobileNumber=array.getString("MobileNumber");
                        Double TotalAmmount=array.getDouble("TotalAmmount");
                        Double GSTRate=array.getDouble("GSTRate");
                        Double GSTAmount=array.getDouble("GSTAmount");
                        Double finalAmount=array.getDouble("finalAmount");

                        JSONArray jsonArray = array.getJSONArray("item");
                        Log.e("sadassssssdd",""+jsonArray);
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObjectInner=jsonArray.getJSONObject(i);
                            int item=jsonObjectInner.getInt("ItemId");
                            String ItemName=jsonObjectInner.getString("ItemName");
                            Double Price=jsonObjectInner.getDouble("Price");
                            Double Amount=jsonObjectInner.getDouble("Amount");
                            int Quantity=jsonObjectInner.getInt("Quantity");
                            Double Disscount=jsonObjectInner.getDouble("Disscount");
                            Bill bill=new Bill(item,Quantity,ItemName,Price,Amount,Disscount);
                            billArrayList.add(bill);
                        }
                        Log.e("sadassssssdd",""+billArrayList.size());
                        AdapterBill adapterBill=new AdapterBill(billArrayList,ActivityViewMyBill.this);
                        mRecyclerViewKitchen.setAdapter(adapterBill);

                        txtTotal.setText("\u20B9 "+TotalAmmount);
                        txtDiss.setText("\u20B9 "+GSTRate);
                        txtGst.setText("\u20B9 "+GSTAmount);
                        txtfinal.setText("\u20B9 "+finalAmount);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }





            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();


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
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("tableId", mSharedeSharedPreferences.getInt("table_no", 0));
                    jsonObject.put("orderBy",123);
                    jsonObject.put("CustomerName",userTables.get(0).getUserName());
                    jsonObject.put("customerEmailId",userTables.get(0).getUserEmail());
                    jsonObject.put("MobileNumber",userTables.get(0).getMobileNo());
                    jsonObject.put("PaymentType","cash");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONArray jsonArray=new JSONArray();
                for (int i=0;i<userTables.size();i++){
                    JSONObject jsonObject1=new JSONObject();
                    try {
                        jsonObject1.put("ItemId",userTables.get(i).getItemId());
                        jsonObject1.put("Price",userTables.get(i).getMenuPrice());
                        jsonObject1.put("ItemName",userTables.get(i).getMenuName());
                        jsonObject1.put("Quantity",userTables.get(i).getItemQuantity());

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                    jsonArray.put(jsonObject1);
                }

                try {
                    jsonObject.put("Item",jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.e("xxxxxxxxxxxxxx",""+jsonObject.toString());
                String str=jsonObject.toString();

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
}
