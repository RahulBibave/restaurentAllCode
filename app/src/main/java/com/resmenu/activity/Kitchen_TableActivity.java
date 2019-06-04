package com.resmenu.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.resmenu.POJO.Menu;
import com.resmenu.POJO.Table;
import com.resmenu.R;
import com.resmenu.adapters.adapter_kitchenmain;
import com.resmenu.constants.ApiUrls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.resmenu.activity.MainActivity.ACCESS_TOKEN;
import static com.resmenu.activity.MainActivity.PREF_NAME;

public class Kitchen_TableActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    ArrayList<Table> tableArrayList;
    String accesstoken;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen__table);
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        accesstoken = prefs.getString(ACCESS_TOKEN, null);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        getTable();
    }

    public void getTable() {
        mRequestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiUrls.mUrlTableList, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object = null;
                tableArrayList = new ArrayList<>();
                try {
                    object = new JSONObject(response);
                    Boolean sucess_code = object.getBoolean("Status");
                    if (sucess_code.equals(true)) {
                        JSONArray array = object.getJSONArray("Data");
                        Log.e("dddddddddd", "" + array.toString());
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject = array.getJSONObject(i);
                            int TableId = jsonObject.getInt("TableId");
                            String TableName = jsonObject.getString("TableName");
                            String TableDescription = jsonObject.getString("TableDescription");
                            Boolean IsActive = jsonObject.getBoolean("IsActive");
                            Boolean IsBusy = jsonObject.getBoolean("IsBusy");
                            Table table = new Table(TableId, TableName, TableDescription, IsActive, IsBusy);
                            tableArrayList.add(table);
                        }
                        adapter_kitchenmain adapter_kitchenmain = new adapter_kitchenmain(Kitchen_TableActivity.this, tableArrayList);
                        mRecyclerView.setAdapter(adapter_kitchenmain);

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


        };
        mRequestQueue.add(stringRequest);
    }

}
