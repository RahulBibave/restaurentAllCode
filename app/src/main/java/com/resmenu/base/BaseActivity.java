package com.resmenu.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.resmenu.R;


import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

    protected Router mRouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mRouter = new Router(this);


//        setContentView(R.layout.activity_base);

    }

    public Router getRouter(){
        return mRouter;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
