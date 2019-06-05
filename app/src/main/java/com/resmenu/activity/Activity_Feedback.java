package com.resmenu.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.resmenu.Database.RestaurentMenuDatabase;
import com.resmenu.R;
import com.resmenu.constants.ApiUrls;
import com.resmenu.customViews.CustomButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.resmenu.activity.MainActivity.ACCESS_TOKEN;
import static com.resmenu.activity.MainActivity.PREF_NAME;

public class Activity_Feedback extends AppCompatActivity {

    CustomButton mBtnSubmitFeedback;
    private RequestQueue mRequestQueue;
    private ProgressDialog progressDialog;
    String accesstoken;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_feedback);
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        accesstoken = prefs.getString(ACCESS_TOKEN, null);
        mBtnSubmitFeedback=findViewById(R.id.btn_submit_feedback);
        mBtnSubmitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFeedBack();
            }
        });
    }
    public void submitFeedBack() {

        mRequestQueue = Volley.newRequestQueue(this);
        final StringRequest request = new StringRequest(Request.Method.POST, ApiUrls.mUrlSubmitFeedBack, new Response.Listener<String>() {
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Feedback.this);

                        View view = LayoutInflater.from(Activity_Feedback.this).inflate(R.layout.custom_layout, null);

                        CustomButton title = (CustomButton) view.findViewById(R.id.btnExit);
                        ImageButton imageButton = (ImageButton) view.findViewById(R.id.image);
                        title.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(Activity_Feedback.this,Activity_WaiterLanding.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                        imageButton.setImageResource(R.drawable.dialog_img);



                        builder.setView(view);
                        builder.show();

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
                try {
                    jsonObject.put("Freshness", 4);
                    jsonObject.put("Taste", 4);
                    jsonObject.put("ValueForMoney", 4);
                    jsonObject.put("StaffCourtesy", 1);
                    jsonObject.put("Staffknowledge", 4);
                    jsonObject.put("StaffCooperative", 4);
                    jsonObject.put("Environment", 4);
                    jsonObject.put("Presentation", 4);
                    jsonObject.put("DigitalService", 4);
                    jsonObject.put("PersonalOpinion", "nice");
                    jsonObject.put("WaiterId", Activity_WaiterLanding.waiterID);

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
}
