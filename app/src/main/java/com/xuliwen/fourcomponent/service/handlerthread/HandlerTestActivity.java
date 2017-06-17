package com.xuliwen.fourcomponent.service.handlerthread;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xuliwen.fourcomponent.R;
import com.xuliwen.fourcomponent.utils.L;

public class HandlerTestActivity extends AppCompatActivity {

    /**
     * 一个线程可以有多个handler
     */

    private Handler handler1=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            L.l("handler1"+msg.what);
        }
    };

    private Handler handler2=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            L.l("handler2"+msg.what);
        }
    };

    private Handler workHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_test);
    }


    /**
     * UI线程系统会创建好Looper
     * @param view
     */
    public void uiHandlerTest(View view){
        Message message1=new Message();
        message1.what=1;
        handler1.sendMessage(message1);

        //Looper.prepare(); 如果再给主线程创建looper，将报错崩溃


        Message message2=new Message();
        message2.what=2;
        handler2.sendMessage(message2);

        boolean result=handler1.getLooper()==handler2.getLooper();
        L.l("handler1.getLooper()==handler2.getLooper(): "+result);

    }


    /**
     * 子线程需要自己手动创建Looper
     */
    private void newWorkHandler(){
        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                workHandler=new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        L.l("workHandler+handleMessage");
                        workHandler.sendEmptyMessageDelayed(1,5000);//意思是5s后才会将消息加入到消息队列，并不是说立即加入消息队列，5s后再进行处理
                    }
                };
                Looper.loop();
                L.l("after Looper.loop()");//在looper被关闭之前，Looper.loop();不会被执行
            }
        }.start();
    }

    public void workHandlerTest(View view) {
        newWorkHandler();
        try {
            Thread.sleep(1000);//延时1s，保证子线程的handler创建好
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        workHandler.sendEmptyMessage(1);
    }

    public void quit(View view) {
        workHandler.getLooper().quit();//立即退出
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void quitSafely(View view) {
        workHandler.getLooper().quitSafely();//处理完消息队列的消息后就退出，未加入消息队列的消息不再加入（比如sendEmptyMessageDelayed）
    }
}
