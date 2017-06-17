package com.xuliwen.fourcomponent.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.xuliwen.fourcomponent.R;
import com.xuliwen.fourcomponent.utils.L;

public class ServiceTestActivity1 extends AppCompatActivity {
    private BackGroundService.MyBinder binder;
    private boolean isBind;//标志service是否已绑定

    private ServiceConnection serviceConnection=new ServiceConnection() {

        /**
         * Activity bind到Service后执行，多次bindService，只会在第一次bind才会执行
         * 用于获取Binder对象，这样就可以操作Binder中的方法了
         * @param name
         * @param service
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            L.l("onServiceConnected");
            binder= (BackGroundService.MyBinder) service;
            binder.download();
            isBind=true;
        }

        /**
         * 正常情况下不会被执行，service被异常销毁才会被执行（比如内存不足，service被销毁）
         * @param name
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            L.l("onServiceDisconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_test);
    }

    public void startService(View view) {
        startService(new Intent(ServiceTestActivity1.this,BackGroundService.class));
    }

    public void stopService(View view) {
        stopService(new Intent(ServiceTestActivity1.this,BackGroundService.class));
    }

    public void bindService(View view) {
        //bindService一般是在onCreate中执行
        bindService(new Intent(this,BackGroundService.class),serviceConnection,BIND_AUTO_CREATE);

        //将service绑定在context上，就需要在context上去解绑service，否则报错Service not registered
       // getApplicationContext(). bindService(new Intent(this,BackGroundService.class),serviceConnection,BIND_AUTO_CREATE);
    }

    public void unbindService(View view) {
        //unbindService一般是在onDestroy中调用的，多次调用unbindService会出错：Service not registered
        if(isBind){
            unbindService(serviceConnection);
            isBind=false;
        }

        //context上解绑service
       // getApplicationContext().unbindService(serviceConnection);
    }

    public void startForegroundService(View view) {
        startService(new Intent(ServiceTestActivity1.this,ForegroundService.class));
    }

    public void stopForegroundService(View view) {
        stopService(new Intent(ServiceTestActivity1.this,ForegroundService.class));
    }

    public void toBindServiceTest(View view) {
        startActivity(new Intent(this,ServiceTestActivity2.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unbindService一般是在onDestroy中调用的，多次调用unbindService会出错：Service not registered
        //如果在Activity调用onDestroy没有unbindService，将会报错（虽然不会崩溃，虽然service会照样destroy）
       /* if(isBind){
            L.l("serviceTestActivity+onDestroy");
            unbindService(serviceConnection);
            isBind=false;
        }*/
    }

    public void startDownload(View view) {
        binder.download();//如果unbindService后，没有将binder置为null，那binder依然可以使用
    }

    public void startIntentService(View view) {
        startService(new Intent(this,MyIntentService.class));
        MyIntentService.startActionFoo(getApplicationContext(),"param1","param2");
        MyIntentService.startActionBaz(getApplicationContext(),"param1","param2");
    }
}
