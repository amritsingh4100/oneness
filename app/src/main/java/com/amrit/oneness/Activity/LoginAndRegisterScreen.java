package com.amrit.oneness.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.amrit.oneness.Adapters.LoginAndRegisterFragmentAdapter;
import com.amrit.oneness.Adapters.ZoomOutPageTransformer;
import com.amrit.oneness.Customs.Utils;
import com.amrit.oneness.Interfaces.FragmentInterface;
import com.amrit.oneness.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginAndRegisterScreen extends AppCompatActivity implements FragmentInterface {

    String TAG= getClass().getSimpleName();
    FirebaseAuth mAuth;

   ViewPager2 viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_and_register_layout);
        intializeViews();
    }

    private void intializeViews()
    {
        mAuth = FirebaseAuth.getInstance();
        viewPager= findViewById(R.id.login_and_register_viewpager_id);
        LoginAndRegisterFragmentAdapter loginAndRegisterFragmentAdapter =new LoginAndRegisterFragmentAdapter(this,this);
        viewPager.setPageTransformer(new ZoomOutPageTransformer());
        viewPager.setUserInputEnabled(false);
        viewPager.setAdapter(loginAndRegisterFragmentAdapter);
    }

    @Override
    public void switchTo(int number) {
        viewPager.setCurrentItem(number);
    }
}