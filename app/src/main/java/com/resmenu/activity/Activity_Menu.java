package com.resmenu.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
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
import com.resmenu.R;
import com.resmenu.adapters.AdapterMenu;
import com.resmenu.constants.ApiUrls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.resmenu.activity.MainActivity.ACCESS_TOKEN;
import static com.resmenu.activity.MainActivity.PREF_NAME;

public class Activity_Menu extends AppCompatActivity {

    private RecyclerView mRecyclerViewMenu;
    RequestQueue mRequestQueue;
    private ArrayList<Menu> arrayList;
    private AdapterMenu adapterMenu;
    String accesstoken;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        accesstoken = prefs.getString(ACCESS_TOKEN, null);

        init();
    }

    private void init() {
        mRecyclerViewMenu=findViewById(R.id.recycler_menu);
        mRecyclerViewMenu.setLayoutManager(new LinearLayoutManager(this));

        mRequestQueue = Volley.newRequestQueue(Activity_Menu.this);
        final StringRequest request = new StringRequest(Request.Method.GET, ApiUrls.mUrlMenuList, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                Log.e("saddd",""+s);
                   arrayList=new ArrayList<Menu>();
                try {
                    JSONObject object = new JSONObject(s);
                    Boolean Status = object.getBoolean("Status");

                    if (Status.equals(true)) {


                    JSONArray array =object.getJSONArray("Data");
                    Log.e("dddddddddd",""+array.toString());
                    for (int i = 0 ; i<array.length();i++){
                        JSONObject jsonObject=array.getJSONObject(i);
                        int id=jsonObject.getInt("MenuId");
                        String ItemTypeName=jsonObject.getString("MenuName");
                        String categoryDesc = jsonObject.getString("MenuDescription");
//                        String pic=jsonObject.getString("");
                        Boolean Menustatus = jsonObject.getBoolean("IsActive");
                        Menu menu=new Menu(id,ItemTypeName,categoryDesc,Menustatus);
                        arrayList.add(menu);
                    }
                    }

                    adapterMenu=new AdapterMenu(arrayList,Activity_Menu.this);
                    mRecyclerViewMenu.setAdapter(adapterMenu);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("saddd",volleyError.toString());

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
}
