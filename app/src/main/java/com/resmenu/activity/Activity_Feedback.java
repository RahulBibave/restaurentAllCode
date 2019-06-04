package com.resmenu.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.resmenu.R;
import com.resmenu.customViews.CustomButton;

public class Activity_Feedback extends AppCompatActivity {

    CustomButton mBtnSubmitFeedback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.layout_feedback);
    }
}
