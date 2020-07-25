package com.example.justorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import static android.provider.LiveFolders.INTENT;

public class aboutUs extends AppCompatActivity {
    ImageView fb_image,wa_image,insta_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        fb_image=(ImageView)findViewById(R.id.img_fb);
        wa_image=(ImageView)findViewById(R.id.img_wa);
        insta_image=(ImageView)findViewById(R.id.img_insta);

        wa_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String dial="tel:"+"6204774832";
                        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));

                    }
                }
        );

        fb_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String link="https://www.facebook.com/SatifyYourHunger";
                        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(link)));
                    }
                }
        );


        insta_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String link="https://instagram.com/hungerzy?igshid=1txl8h3g3q9dp";
                        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(link)));
                    }
                }
        );



    }
}
