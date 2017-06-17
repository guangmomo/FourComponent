package com.xuliwen.fourcomponent.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.xuliwen.fourcomponent.MainActivity;
import com.xuliwen.fourcomponent.R;

/**
 * Created by xlw on 2017/5/19.
 */

public class ForegroundService extends Service {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate() {
        super.onCreate();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);
        Notification.Builder builder=new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setTicker("有通知来");//MIUI不显示
        builder.setContentTitle("这是通知的标题");
        builder.setContentText("这是通知的内容");
        builder.setContentIntent(pendingIntent);
        Notification notification=builder.build();
        startForeground(1, notification);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
