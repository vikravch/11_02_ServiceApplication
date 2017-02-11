package com.example.vitaly.serviceapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyService extends Service {

    private String time;
    private MyBinder mb = new MyBinder();

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(Const.LOG_TAG,"onCreate");

        new Thread(new Runnable() {
            @Override
            public void run() {
                counter();
            }
        }).start();

    }

    private void counter(){
        for (int i = 0; i < 1;) {

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)+3);
            SimpleDateFormat format = new SimpleDateFormat("dd:MM HH:mm:ss ");
            time = format.format(calendar.getTime());
            Log.d(Const.LOG_TAG,"count - "+time);
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    public String getTime(){
        return time;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mb;
    }

    public class MyBinder extends Binder {
        MyService getService(){
            return MyService.this;
        }
    }
}
