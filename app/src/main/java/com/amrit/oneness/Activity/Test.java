package com.amrit.oneness.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amrit.oneness.Customs.Keys;
import com.amrit.oneness.Customs.Utils;
import com.amrit.oneness.Interfaces.UtilInterface;
import com.amrit.oneness.Models.InterestModel;
import com.amrit.oneness.R;
import com.amrit.oneness.RealTimeDatabase.Blockchain;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Test extends AppCompatActivity {

    String TAG= getClass().getSimpleName();
    ImageView splashOnenessImg;
    Blockchain blockchain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        splashOnenessImg=findViewById(R.id.splash_oneness_img_id);
        FirebaseApp.initializeApp(this);

        blockchain = new Blockchain();
        blockchain.addItem("batman","hashkey");

        snapCrackle();

    }

    public void onBlockChainClicked(View view)
    {
        blockchain.addItem("batman","hashkey");

    }

    private void snapCrackle()
    {
        for(int n=1;n<=100;n++)
        {
            StringBuilder stringBuilder = new StringBuilder();
            String numberString = String.valueOf(n);

            if(n%3==0||numberString.contains("3")) stringBuilder.append(" snap");

            if(n%7==0||numberString.contains("7")) stringBuilder.append(" crackle");

            Utils.print(TAG,"number : "+n+stringBuilder);
        }
    }

   // The most technically complex app that I had to make was on based on real-time database using firebase for the first time, so the problem was that if any other device would make the change to anywhere in the database it would trigger a refresh on all devices for the UI in real time, which would make other users lose what they were reading in recyclerview or anywhere else on the screen because the UI would update whenever data was changed, which was a problem, I solved it by making my custom library class  and object based functions which would further use interface to pass the data only when certain conditions are met.
}