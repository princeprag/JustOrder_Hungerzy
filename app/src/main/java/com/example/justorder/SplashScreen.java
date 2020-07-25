package com.example.justorder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    Handler handler;
    ImageView img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        handler=new Handler();

        img= (ImageView)findViewById(R.id.imageView2);
        img.setImageResource(R.drawable.splash);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


               users myuser = new users(getApplicationContext());

              boolean b=  myuser.checkloginstatus();
              String s= Boolean.toString(b);
                //Toast.makeText(SplashScreen.this,s, Toast.LENGTH_SHORT).show();

                if(myuser.checkloginstatus()){
                    startActivity(new Intent(SplashScreen.this, MainFeed.class));
                }
                else {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        },5000);
    }
}
