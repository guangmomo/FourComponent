package com.xuliwen.fourcomponent.service.handlerthread.downloadprogress;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xuliwen.fourcomponent.R;

public class CommonThreadActivity extends AppCompatActivity {

    private Handler uiHandler=new Handler();
    private TextView commonThreadTextView;
    private boolean downloadFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_thread);
        commonThreadTextView = (TextView) findViewById(R.id.commonThread_textView);
    }

    public void download(View view) {
        downloadFlag=true;
        new DownLoadThread().start();
    }

    public void cancelDownload(View view) {
        downloadFlag=false;
    }

    class DownLoadThread extends Thread {
        private int progress;

        @Override
        public void run() {
            while (downloadFlag){
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        commonThreadTextView.setText("下载进度为"+String.valueOf(++progress));
                    }
                });
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
