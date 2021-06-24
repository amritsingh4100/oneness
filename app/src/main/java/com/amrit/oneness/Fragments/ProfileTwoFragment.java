package com.amrit.oneness.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.amrit.oneness.Customs.CustomFlexBoxLayout;
import com.amrit.oneness.RealTimeDatabase.ProfileRealtimeDatabase;

import com.amrit.oneness.Interfaces.ProfileListener;
import com.amrit.oneness.Models.ProfileModel;
import com.amrit.oneness.R;
import com.google.android.flexbox.FlexboxLayout;

public class ProfileTwoFragment extends Fragment implements ProfileListener {

    String TAG= getClass().getSimpleName();
    Context context;
    View profileTwoView;

    FlexboxLayout flexboxLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        profileTwoView=inflater.inflate(R.layout.profile_two_layout,container,false);
         intializeViews();

        return profileTwoView;
    }

    private void intializeViews()
    {
        context= getContext();
        flexboxLayout = profileTwoView.findViewById(R.id.profile_two_flexbox);
        ProfileRealtimeDatabase profileRealtimeDatabase = new ProfileRealtimeDatabase();
        profileRealtimeDatabase.liveProfileListener(this);
    }

    @Override
    public void updatedProfileData(ProfileModel profileModel) {
        CustomFlexBoxLayout customFlexBoxLayout = new CustomFlexBoxLayout(context);
        customFlexBoxLayout.setInterestArrayListToShow(profileModel.getInterestModelList()).isClickAble(true)
                .addProfileViews(flexboxLayout);
    }
}