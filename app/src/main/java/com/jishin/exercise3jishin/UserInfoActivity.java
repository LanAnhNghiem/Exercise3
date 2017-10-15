package com.jishin.exercise3jishin;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.InputStream;
public class UserInfoActivity extends AppCompatActivity {
    private TextView txtUsername;
    private TextView txtEmail;
    private TextView txtPass;
    private ImageView imgPhoto;
    public static String TAG = UserInfoActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        txtUsername = (TextView) findViewById(R.id.txtUsername);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtPass = (TextView) findViewById(R.id.txtPass);
        imgPhoto = (ImageView) findViewById(R.id.photo);
        Intent intent = getIntent();
        if(intent.hasExtra("username")){
            txtUsername.setText("Username: "+intent.getStringExtra("username"));
            txtEmail.setText("Email: "+intent.getStringExtra("email"));
            txtPass.setText("Pass: "+intent.getStringExtra("pass"));
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        startService(new Intent(UserInfoActivity.this, MyServices.class));
    }
    public class MyService extends Service {
        @Override
        public void onCreate() {
            super.onCreate();
            Toast.makeText(this, "Create Service", Toast.LENGTH_LONG).show();
            try{
                new DownloadImage().execute();
            }catch (Exception e){
                e.printStackTrace();
                Log.d(TAG, "failed");
            }
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            Log.d(TAG, "start");
            Toast.makeText(this, "Start Service", Toast.LENGTH_SHORT).show();
            return super.onStartCommand(intent, flags, startId);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Toast.makeText(this, "Destroy", Toast.LENGTH_SHORT).show();
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... URL) {

            String imageURL = "https://cdn.designsmaz.com/wp-content/uploads/2014/08/z53.jpg";

            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            imgPhoto.setImageBitmap(result);
            stopService(new Intent(UserInfoActivity.this, MyService.class));
        }
    }
}
