package com.jishin.exercise3jishin;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by LanAnh on 14/10/2017.
 */

public class MyServices extends Service {
    public static final String TAG = MyServices.class.getSimpleName();
    public class MainBinder extends Binder {
        MyServices getService(){
            return MyServices.this;
        }
    }
    IBinder iBinder = new MainBinder();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        Toast.makeText(this, "Service onBind", Toast.LENGTH_SHORT).show();
        return iBinder;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Create Service", Toast.LENGTH_LONG).show();
    }
        @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Start Service", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }
}
