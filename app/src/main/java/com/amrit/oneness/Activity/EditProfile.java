package com.amrit.oneness.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.amrit.oneness.Adapters.EditProfileAdapter;
import com.amrit.oneness.Adapters.ZoomOutPageTransformer;
import com.amrit.oneness.Models.ProfileModel;
import com.amrit.oneness.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;


public class EditProfile extends AppCompatActivity{

    String TAG = getClass().getSimpleName();
    ViewPager2 viewPager;
    EditProfileAdapter editProfileAdapter;

    public static int requestCode = 200;

    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_profile_layout);

        initControls();
    }

    private void initControls()
    {
        viewPager=findViewById(R.id.create_profile_viewpager);
        viewPager.setPageTransformer(new ZoomOutPageTransformer());
        viewPager.setUserInputEnabled(true);
        tabLayout= findViewById(R.id.edit_profile_tab_layout);
        editProfileAdapter =new EditProfileAdapter(EditProfile.this);
        viewPager.setAdapter(editProfileAdapter);

        List<String> tabNames = new ArrayList<>();
        tabNames.add("Interests");
        tabNames.add("Profile");
        tabNames.add("About");

        // setting up tabLayout
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(tabNames.get(position))
        ).attach();
    }
}