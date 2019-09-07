package com.flickrapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flickrapp.R;

public class PhotoDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        //Set initial context for the new intent
        Intent intent = getIntent();
        //get the extra attributes passed from parent activity
        String imageUrl= intent.getStringExtra(MainActivity.EXTRA_URL);
        String imageTitle= intent.getStringExtra(MainActivity.EXTRA_TITLE);

        ImageView imageView = findViewById(R.id.flickr_image);
        TextView textView = findViewById(R.id.title);
        Glide.with(this).load(imageUrl).into(imageView);
        textView.setText(imageTitle);



    }
}
