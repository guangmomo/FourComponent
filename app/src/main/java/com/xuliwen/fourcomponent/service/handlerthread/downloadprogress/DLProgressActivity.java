package com.xuliwen.fourcomponent.service.handlerthread.downloadprogress;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.xuliwen.fourcomponent.R;

public class DLProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dlprogress);
    }

    public void handlerThreadDL(View view) {
        startActivity(new Intent(this,DLHandlerThreadActivity.class));
    }
}
