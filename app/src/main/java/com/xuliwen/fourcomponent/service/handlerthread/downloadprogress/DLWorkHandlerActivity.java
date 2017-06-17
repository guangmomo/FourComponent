package com.xuliwen.fourcomponent.service.handlerthread.downloadprogress;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xuliwen.fourcomponent.R;

import org.w3c.dom.Text;

public class DLWorkHandlerActivity extends AppCompatActivity {

    private Handler workHandler;
    private TextView workHandlerTextView;
    private Thread workThread;
    private Handler uiHandler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dlwork_handler);
        workHandlerTextView= (TextView) findViewById(R.id.workHandler_textView);
    }

    public void download(View view) {
        workThread=new MyThread();
        workThread.start();

    }

    public void cancelDownload(View view) {
        workHandler.getLooper().quit();
    }


    class MyThread extends Thread{

        private int progress;

        @Override
        public void run() {
            Looper.prepare();
             workHandler=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            workHandlerTextView.setText("下载进度为："+(++progress));
                        }
                    });
                    workHandler.sendEmptyMessageDelayed(0,500);
                }
            };
            workHandler.sendEmptyMessage(0);
            Looper.loop();
        }
    }

}
