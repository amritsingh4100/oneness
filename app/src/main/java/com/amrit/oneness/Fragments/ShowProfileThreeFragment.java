package com.amrit.oneness.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.amrit.oneness.Models.ProfileModel;
import com.amrit.oneness.R;

public class ShowProfileThreeFragment extends Fragment{

    // fixed variable
    String TAG= getClass().getSimpleName();
    Context context;
    View profileThreeView;

    //layout variable
    EditText userAboutEdit;

    ProfileModel profileModel;

    public ShowProfileThreeFragment(ProfileModel profileModel)
    {
        this.profileModel = profileModel;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        profileThreeView=inflater.inflate(R.layout.show_profile_three_layout,container,false);
         intializeViews();

        return profileThreeView;
    }

    private void intializeViews()
    {
        context= getContext();
        userAboutEdit = profileThreeView.findViewById(R.id.profile_three_about);
        userAboutEdit.setText(profileModel.getUserAbout());
    }
}