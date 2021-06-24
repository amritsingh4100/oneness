package com.amrit.oneness.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.amrit.oneness.Adapters.ShowProfileAdapter;
import com.amrit.oneness.Adapters.ZoomOutPageTransformer;
import com.amrit.oneness.Customs.Keys;

import com.amrit.oneness.RealTimeDatabase.ProfileRealtimeDatabase;
import com.amrit.oneness.Interfaces.ProfileListener;
import com.amrit.oneness.Models.ProfileModel;
import com.amrit.oneness.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;


public class ShowProfile extends AppCompatActivity implements ProfileListener {

    String TAG = getClass().getSimpleName();
    ViewPager2 viewPager;
    ShowProfileAdapter showProfileAdapter;
    List<String> tabNames;

    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_profile_layout);
        initControls();

        // getting unique user id
        String userID= getIntent().getStringExtra(Keys.userID);

        // making names for the tabs
        tabNames = new ArrayList<>();
        tabNames.add("Profile");
        tabNames.add("Interests");
        tabNames.add("About");

        ProfileRealtimeDatabase profileRealtimeDatabase = new ProfileRealtimeDatabase();
        profileRealtimeDatabase.getProfileFromUserID(userID).singleProfileListener(this);
    }

    private void initControls()
    {
        viewPager=findViewById(R.id.show_profile_viewpager);
        viewPager.setPageTransformer(new ZoomOutPageTransformer());
        tabLayout = findViewById(R.id.show_profile_tab_layout);
    }

    @Override
    public void updatedProfileData(ProfileModel profileModel) {
        showProfileAdapter =new ShowProfileAdapter(ShowProfile.this,profileModel);
        viewPager.setAdapter(showProfileAdapter);

        // setting up tablayou a
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(tabNames.get(position))
        ).attach();
    }
}