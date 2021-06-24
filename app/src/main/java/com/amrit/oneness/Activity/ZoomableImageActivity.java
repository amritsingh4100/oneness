package com.amrit.oneness.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.amrit.oneness.Customs.Keys;
import com.amrit.oneness.Customs.Utils;
import com.amrit.oneness.Customs.ZoomableImageView;
import com.amrit.oneness.Interfaces.UtilInterface;
import com.amrit.oneness.Models.InterestModel;
import com.amrit.oneness.R;
import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ZoomableImageActivity extends AppCompatActivity {

    String TAG= getClass().getSimpleName();
    ZoomableImageView zoomImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zoom_image_layout);
        zoomImage =findViewById(R.id.zoom_image_id);
        // logout user

        String imageLinkClicked = getIntent().getStringExtra(Keys.previewImageLink);
        Glide.with(this).load(imageLinkClicked).centerInside().placeholder(R.drawable.blue_gradient_background).into(zoomImage);

    }
}