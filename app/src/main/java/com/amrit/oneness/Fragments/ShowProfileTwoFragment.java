package com.amrit.oneness.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.amrit.oneness.Customs.CustomFlexBoxLayout;

import com.amrit.oneness.Models.ProfileModel;
import com.amrit.oneness.R;
import com.google.android.flexbox.FlexboxLayout;

public class ShowProfileTwoFragment extends Fragment implements View.OnClickListener {

    String TAG= getClass().getSimpleName();
    Context context;

    View profileTwoView;
    CustomFlexBoxLayout customFlexBoxLayout;
    FlexboxLayout flexboxLayout;

    ProfileModel profileModel;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        profileTwoView=inflater.inflate(R.layout.show_profile_two_layout,container,false);
         intializeViews();
         addInterests();

        return profileTwoView;
    }

    public ShowProfileTwoFragment(ProfileModel profileModel)
    {
        this.profileModel = profileModel;
    }

    private void intializeViews()
    {
        context = getContext();
        flexboxLayout = profileTwoView.findViewById(R.id.profile_two_flexbox);
    }

    private void addInterests()
    {
        customFlexBoxLayout = new CustomFlexBoxLayout(context);
        customFlexBoxLayout.setInterestArrayListToShow(profileModel.getInterestModelList()).addViews(flexboxLayout);
    }

    @Override
    public void onClick(View view) {

    }
}