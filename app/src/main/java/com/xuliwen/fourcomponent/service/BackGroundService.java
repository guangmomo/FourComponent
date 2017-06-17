package com.xuliwen.fourcomponent.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.xuliwen.fourcomponent.utils.L;

public class BackGroundService extends Service {

    private IBinder myBinder=new MyBinder();

    public BackGroundService() {
    }

    /**
     * 只有第一次创建的时候会被调用，第一次调用startService或者bindService
     */
    @Override
    public void onCreate() {
        L.l("onCreate");
        super.onCreate();
    }

    /**
     * 每次startService都会被调用
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        L.l("onStartCommand");
        new Thread(){
            @Override
            public void run() {
               while (true){
                   L.l("BackGroundService+thread");
                   try {
                       Thread.sleep(1000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
            }
        }.start();
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * service被停止的时候的回调
     * 1）调用stopService,unBindService
     * 2）在应用详情页停止service
     *
     */
    @Override
    public void onDestroy() {
        L.l("onDestroy");
        super.onDestroy();
    }

    /**
     * 第一次调用bindService时被调用
     * 顺序：
     * onCreate
     * onBind
     * onStartCommand
     *
     *
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        L.l("onBind");
        return myBinder;
    }



    /**
     * Binder是Service的非静态内部类，这样Binder可以访问Service的任何变量和方法，这样外界可以通过
     * Binder来控制Service
     */
    class MyBinder extends Binder{
        int i=0;

        /**
         * Binder中创建一些方法，提供给外部调用，从而控制service
         */
        public void download(){
            L.l("start download"+(i++));
        }


    }

}
