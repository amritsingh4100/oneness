package com.amrit.oneness.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.amrit.oneness.Customs.Keys;
import com.amrit.oneness.Customs.Utils;
import com.amrit.oneness.Models.InterestModel;
import com.amrit.oneness.R;
import com.amrit.oneness.Interfaces.UtilInterface;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Splashscreen extends AppCompatActivity implements UtilInterface {

    String TAG= getClass().getSimpleName();
    ImageView splashOnenessImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen_layout);
        splashOnenessImg=findViewById(R.id.splash_oneness_img_id);
        FirebaseApp.initializeApp(this);
        //FirebaseAuth.getInstance().signOut();
        playAnimDelay();
        createOfflineInterestForRealTimeDatabase();
        // logout user

    }
    // change it after, but need it right now
    public static void createOfflineInterestForRealTimeDatabase()
    {
        ArrayList<InterestModel> interestModelArrayList=new ArrayList<>();
        InterestModel interestModel1 = new InterestModel(Keys.interestOne,false);
        InterestModel interestModel2 = new InterestModel(Keys.interestTwo,false);
        InterestModel interestModel3 = new InterestModel(Keys.interestThree,false);
        InterestModel interestModel4 = new InterestModel(Keys.interestFour,false);
        InterestModel interestModel5 = new InterestModel(Keys.interestFive,false);
        InterestModel interestModel6 = new InterestModel(Keys.interestSix,false);
        InterestModel interestModel7 = new InterestModel(Keys.interestSeven,false);
        InterestModel interestModel8 = new InterestModel(Keys.interestEight,false);
        InterestModel interestModel9 = new InterestModel(Keys.interestNine,false);
        InterestModel interestModel10 = new InterestModel(Keys.interestTen,false);
        interestModelArrayList.add(interestModel1);
        interestModelArrayList.add(interestModel2);
        interestModelArrayList.add(interestModel3);
        interestModelArrayList.add(interestModel4);
        interestModelArrayList.add(interestModel5);
        interestModelArrayList.add(interestModel6);
        interestModelArrayList.add(interestModel7);
        interestModelArrayList.add(interestModel8);
        interestModelArrayList.add(interestModel9);
        interestModelArrayList.add(interestModel10);

        // change for dynamic UI design.
        for(int i=0;i<30;i++)
        {
            interestModelArrayList.add(interestModel1);
        }

        FirebaseDatabase.getInstance().getReference().child(Keys.nodeInterests).setValue(interestModelArrayList);
    }
    private boolean isUserLoggedIn() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return true;
        } else return false;
    }

    public void checkForloginStatus()
    {

        if(isUserLoggedIn())
        {
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            if(firebaseUser!=null) checkIfUserProfileExists(firebaseUser.getUid());
        }
        else {
            Intent openLoginAndRegisterIntent = new Intent(this, LoginAndRegisterScreen.class);
            startActivity(openLoginAndRegisterIntent);
            finish();
        }
    }

    private void checkIfUserProfileExists(String userID) {
        FirebaseDatabase.getInstance().getReference().child(Keys.nodeProfiles).child(userID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){

                            Intent openPost = new Intent(Splashscreen.this, Posts.class);
                            startActivity(openPost);
                            finish();
                        }
                        else
                        {
                            Intent openProfile = new Intent(Splashscreen.this, EditProfile.class);
                            startActivity(openProfile);
                            finish();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Utils.print(TAG,"Profile not found"+databaseError.toString());
                    }
                });
    }

    private void playAnimDelay()
    {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(2000);
        splashOnenessImg.startAnimation(alphaAnimation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Utils.isConnectedToInternet(Splashscreen.this,Splashscreen.this);
            }
        },2500);
    }

    @Override
    public void ifPositive() {
        checkForloginStatus();
    }

    @Override
    public void ifNegative() {

        Utils.print(TAG,"not connected to int");

        Utils.showCustomAlert(this, "Not connected to internet", "Retry", "Cancel", new UtilInterface() {
            @Override
            public void ifPositive() {
                Utils.print(TAG,"retry chosen");
                Utils.isConnectedToInternet(Splashscreen.this,Splashscreen.this);
            }

            @Override
            public void ifNegative() {
               Utils.print(TAG,"cancel chosen");
               finish();
            }
        });

    }
}