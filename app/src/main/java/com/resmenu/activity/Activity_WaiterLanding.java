package com.resmenu.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.resmenu.Database.Entity.OrderTable;
import com.resmenu.Database.Entity.UserTable;
import com.resmenu.Database.RestaurentMenuDatabase;
import com.resmenu.R;
import com.resmenu.constants.ApiUrls;
import com.resmenu.customViews.CustomButton;
import com.resmenu.customViews.CustomEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.resmenu.activity.MainActivity.ACCESS_TOKEN;
import static com.resmenu.activity.MainActivity.PREF_NAME;

public class Activity_WaiterLanding extends AppCompatActivity {

    private LinearLayout mMenu, mFeedBack, mLLView_bill,mLinMyOreder;
    private RequestQueue mRequestQueue;
    private Spinner spinner;
    ArrayList<String> arrayListStaffID;
    ArrayList<String> arrayListName;
    private String accesstoken;
    int waiter_ID;
    private CustomButton mBtnContinue;
    private CustomEditText mEdtName, mEdtEmail, mEdtMobile;
    private String flag = "";
    public static String Cu_name, email, mobile;
    public static int  tableNO;
    public static int waiterID=0;
    RestaurentMenuDatabase restaurentMenuDatabase;
    Context mContext;
    private List<OrderTable> userTables;
    private SharedPreferences mSharedeSharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter_landing);
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        accesstoken = prefs.getString(ACCESS_TOKEN, null);

        mContext = this;
        mSharedeSharedPreferences = getSharedPreferences("restaurant", MODE_PRIVATE);

        restaurentMenuDatabase = RestaurentMenuDatabase.getInstance(Activity_WaiterLanding.this);
        getStaff();
        init();
        tableNO=mSharedeSharedPreferences.getInt("table_no", 0);

        if (restaurentMenuDatabase.myOrderDao().orderCount(mSharedeSharedPreferences.getInt("table_no", 0)) > 0){
            SharedPreferences mSharedeSharedPreferences = getSharedPreferences("restaurant", MODE_PRIVATE);
            userTables=new ArrayList<>();
            userTables = restaurentMenuDatabase.myOrderDao().getDataByTableNo(mSharedeSharedPreferences.getInt("table_no", 0));
            mEdtEmail.setText(userTables.get(0).getUserEmail());
            mEdtName.setText(userTables.get(0).getUserName());
            mEdtMobile.setText(userTables.get(0).getMobileNo());
            waiter_ID=userTables.get(0).getWaiterId();
            flag="1";
        }
        mMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag.equals("1")) {
                    if (waiter_ID==0){

                    }else {
                        Intent intent = new Intent(Activity_WaiterLanding.this, Activity_Menu.class);
                        startActivity(intent);
                    }

                }
            }
        });

        mFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag.equals("1")) {
                    Intent intent = new Intent(Activity_WaiterLanding.this, Activity_Feedback.class);
                    startActivity(intent);
                }

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                waiter_ID = Integer.parseInt(arrayListStaffID.get(position));
                //Toast.makeText(Activity_WaiterLanding.this,waiter_ID+"", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mLinMyOreder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("mycartlist", "count " + restaurentMenuDatabase.myOrderDao().orderCount(mSharedeSharedPreferences.getInt("table_no", 0)));
                if (restaurentMenuDatabase.myOrderDao().orderCount(mSharedeSharedPreferences.getInt("table_no", 0)) > 0) {
                    Intent intent = new Intent(Activity_WaiterLanding.this, ActivityViewOrder.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(mContext,"No order still !!!!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        mLLView_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (restaurentMenuDatabase.myOrderDao().orderCount(mSharedeSharedPreferences.getInt("table_no", 0)) > 0) {
                    Intent intent = new Intent(Activity_WaiterLanding.this, ActivityViewMyBill.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(mContext,"No order still !!!!",Toast.LENGTH_SHORT).show();
                }
            }
        });


        mBtnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!mEdtName.getText().toString().equals("")) && (!mEdtEmail.getText().toString().equals("")) && (!mEdtMobile.getText().toString().equals(""))) {
                    Cu_name = mEdtName.getText().toString();
                    email = mEdtEmail.getText().toString();
                    mobile = mEdtMobile.getText().toString();
                    waiterID = waiter_ID;

                    UserTable userTable = new UserTable();
                    userTable.setUserName(Cu_name);
                    userTable.setUserEmail(email);
                    userTable.setMobileNo(mobile);
                    userTable.setTableStatus(true);
                    //userTable.setTableNo(tableNO);
                    restaurentMenuDatabase.myUserTableDao().insert(userTable);
                    flag = "1";
                    mBtnContinue.setBackgroundColor(Color.GRAY);
                    try {
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private void init() {
        mLLView_bill = findViewById(R.id.ll_view_bill);
        mMenu = findViewById(R.id.lin_menu);
        mFeedBack = findViewById(R.id.lin_feedback);
        spinner = findViewById(R.id.spinner);
        mEdtName = findViewById(R.id.edtName);
        mEdtEmail = findViewById(R.id.edtEmial);
        mEdtMobile = findViewById(R.id.edtMobile);
        mBtnContinue = findViewById(R.id.btnContinue);
        mLinMyOreder=findViewById(R.id.my_orders);
         //setUserValues();
    }

    private void setUserValues() {
        //UserTable userTable = new UserTable();
        List<UserTable> userTables = new ArrayList<>();

        userTables = restaurentMenuDatabase.myUserTableDao().getDataByTableNo(tableNO);
        for (int i = 0; i <= userTables.size(); i++) {
            System.out.println(userTables.get(i).getTableNo());
        }
        if (tableNO == userTables.get(0).getTableNo()) {

            mEdtName.setText(userTables.get(0).getUserName());
            mEdtEmail.setText(userTables.get(0).getUserEmail());
        }
    }

    public void getStaff() {
        mRequestQueue = Volley.newRequestQueue(Activity_WaiterLanding.this);
        final StringRequest request = new StringRequest(Request.Method.GET, ApiUrls.mUrlStaff, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                Log.e("sadddsasasa", "" + s.toString());
                arrayListStaffID = new ArrayList<>();
                arrayListName = new ArrayList<>();
                try {

                    JSONObject object = new JSONObject(s);
                    Boolean sucess_code = object.getBoolean("Status");
                    if (sucess_code.equals(true)) {
                        JSONArray array = object.getJSONArray("Data");
                        Log.e("dddddddddd", "" + array.toString());
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject = array.getJSONObject(i);
                            int id = jsonObject.getInt("WaiterId");
                            String name = jsonObject.getString("WaiterName");
                            // Staff staff = new Staff(name, id);
                            arrayListStaffID.add(id + "");
                            arrayListName.add(name);

                        }

                        ArrayAdapter<String> adapter =
                                new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayListName);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        spinner.setAdapter(adapter);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
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


        };

        mRequestQueue.add(request);
    }

    public void displayList() {

    }
/*
    private LinearLayout mMenu, mFeedBack;
    private RequestQueue mRequestQueue;
    private TextView txtWaiter;
    ArrayList<String> arrayListStaff;
    private LinearLayout myOrders;
    private String accesstoken;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter_landing);
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        accesstoken = prefs.getString(ACCESS_TOKEN, null);
        getStaff();
        init();
        mMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_WaiterLanding.this, Activity_Menu.class);
                startActivity(intent);
            }
        });

        mFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_WaiterLanding.this, Activity_Feedback.class);
                startActivity(intent);

            }
        });

        myOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_WaiterLanding.this, MyCartActivity.class);
                startActivity(intent);
            }
        });

    }

    private void init() {
        mMenu = findViewById(R.id.lin_menu);
        mFeedBack = findViewById(R.id.lin_feedback);
        txtWaiter = findViewById(R.id.txt_waiter);
        myOrders = findViewById(R.id.my_orders);
        txtWaiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               displayList();
            }
        });
    }

    public void getStaff() {
        mRequestQueue = Volley.newRequestQueue(Activity_WaiterLanding.this);
        final StringRequest request = new StringRequest(Request.Method.GET, ApiUrls.mUrlStaff, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                Log.e("sadddsasasa", "" + s.toString());
                arrayListStaff = new ArrayList<>();
                try {

                    JSONObject object = new JSONObject(s);
                    int sucess_code = object.getInt("success");
                    if (sucess_code == 1) {
                        JSONArray array = object.getJSONArray("result");
                        Log.e("dddddddddd", "" + array.toString());
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject = array.getJSONObject(i);
                            int id = jsonObject.getInt("Staffid");
                            String name = jsonObject.getString("StaffName");
                            Staff staff = new Staff(name, id);
                            arrayListStaff.add(id+"");

                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("saddd", volleyError.toString());

            }
        }){


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                // Removed this line if you dont need it or Use application/json
                params.put("Authorization","Bearer "+ accesstoken);
                return params;
            }


        };
        mRequestQueue.add(request);
    }

    public void displayList() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an animal");

// add a list
        final String[] animals = {"1", "2", "3", "4"};

        builder.setItems(animals, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                txtWaiter.setText(animals[which]);
                *//*switch (which) {
                    case 0: // horse
                    case 1: // cow
                    case 2: // camel
                    case 3: // sheep
                    case 4: // goat
                }*//*
            }
        });

// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }*/
}
