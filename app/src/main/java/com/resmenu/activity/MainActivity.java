package com.resmenu.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.resmenu.R;
import com.resmenu.constants.ApiUrls;
import com.resmenu.constants.AppConstants;
import com.resmenu.constants.SharedPreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button button_login;
    private RequestQueue mRequestQueue;
    private EditText mEdtID, mEdtPass;
    String userid = "101-101", userPass = "Admin";
    //  String userid = "", userPass = "";
    private ProgressDialog progressDialog;
    SharedPreferenceManager sharedPreferenceManager;

    public static final String ACCESS_TOKEN = null;
    public static final String PREF_NAME = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferenceManager = new SharedPreferenceManager();

        init();
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });

        // all views will be initialize here


    }

    private void init() {
        button_login = findViewById(R.id.button_login);
        mEdtID = findViewById(R.id.et_restaurent_id);
        mEdtPass = findViewById(R.id.et_restaurent_password);
        mEdtID.setText(userid);
        mEdtPass.setText(userPass);
    }

    public void showAlertDialogButtonClicked() {

        // setup the alert builder
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Role");

        // add a list
        String[] roles = {"Kitchen", "Waiter"};
        builder.setItems(roles, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: {
                        // 1 for kitchen
                        Intent intent = new Intent(MainActivity.this, TablesActivity.class);
                        intent.putExtra("role", "1");
//                        sharedPreferenceManager.storeInt(AppConstants.SHaredPrefKeys.RoleKey,1);
                        startActivity(intent);
                        break;
                    }
                    case 1: {
                        // 2 for waiter

                        Intent intent = new Intent(MainActivity.this, TablesActivity.class);
                        intent.putExtra("role", "2");
//                        sharedPreferenceManager.storeInt(AppConstants.SHaredPrefKeys.RoleKey,2);
                        startActivity(intent);
                        break;
                    }

                }
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void showAlertDialogWaiter() {

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Waiter");

        // add a list
        String[] animals = {"Waiter 1", "Waiter 2", "Waiter 3", "Waiter 4", "Waiter 5", "Waiter 6"};
        builder.setItems(animals, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: {
                        Intent intent = new Intent(MainActivity.this, TablesActivity.class);
                        startActivity(intent);
                        break;


                    }
                    case 1: {

                    }

                }
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void Login() {

        mRequestQueue = Volley.newRequestQueue(this);
        final StringRequest request = new StringRequest(Request.Method.POST, ApiUrls.mUrlLogin, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progressDialog.dismiss();
                Log.e("Login responce", "String responce:- " + s);

                try {

                    JSONObject object = new JSONObject(s);
                    String access_token = object.getString("access_token");
                    String token_type = object.getString("token_type");
                    String expires_in = object.getString("expires_in");
                    String userName = object.getString("userName");
                    String RoleName = object.getString("RoleName");
                    String connectionString = object.getString("connectionString");
                    String issued = object.getString(".issued");
                    String expires = object.getString(".expires");

                    SharedPreferences.Editor editor = getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit();
                    editor.putString(ACCESS_TOKEN, access_token);
                    editor.apply();


                    if (access_token != null) {
                        Intent intent = new Intent(MainActivity.this, TablesActivity.class);
//                        Intent intent = new Intent(MainActivity.this, Kitchen_TableActivity.class);
                        intent.putExtra("role", "2");
                        startActivity(intent);
//                        finish();
                    }

                    Log.e("Login responce", "String responce:- " + access_token);

                } catch (JSONException e) {
                    e.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Login Failed !!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // finish();
                                    //do things
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Login Failed !!")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // finish();
                                //do things
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                Log.e("saddd", volleyError.toString());

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", mEdtID.getText().toString());
                params.put("password", mEdtPass.getText().toString());
                params.put("grant_type", "password");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                // Removed this line if you dont need it or Use application/json
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }


        };
        mRequestQueue.add(request);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    public static void store(String key, String value) {


    }
}
/*   params.put("username","username");
                params.put("password","Admin");
                params.put("grant_type","password");
*/