package com.xuliwen.fourcomponent.service.handlerthread.downloadprogress;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xuliwen.fourcomponent.R;
import com.xuliwen.fourcomponent.utils.L;

public class DLHandlerThreadActivity extends AppCompatActivity {

    private Handler uiHandler=new Handler();
    private Handler workHandler;
    private int progress;
    private TextView handlerThreadTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dlhandler_thread);
        handlerThreadTextView= (TextView) findViewById(R.id.handlerThread_textView);
        newWorkHandler();
    }

    private void newWorkHandler(){
        final HandlerThread handlerThread=new HandlerThread("downloadThread");
        handlerThread.start();
        workHandler=new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        handlerThreadTextView.setText("下载进度为"+String.valueOf(++progress));
                    }
                });
                workHandler.sendEmptyMessageDelayed(0,500);//模拟下载任务，每下载100kB的时候，就sendMes来更新进度
            }
        };
    }

    public void download(View view) {
        workHandler.sendEmptyMessage(0);
    }




    public void cancelDownload(View view) {
        if(workHandler!=null){
            workHandler.getLooper().quit();
        }
    }
}
