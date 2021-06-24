package com.amrit.oneness.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.amrit.oneness.Customs.Utils;
import com.amrit.oneness.Models.ProfileModel;
import com.amrit.oneness.R;
import com.bumptech.glide.Glide;

public class ShowProfileOneFragment extends Fragment implements View.OnClickListener {

    String TAG= getClass().getSimpleName();
    Context context;
    View profileOneView;
    ImageView userProfileImage;
    TextView userNameEdit,userEmailEdit,userMobileEdit;
    ProgressBar loadingImageProgressBar;
    // object holding all data for user
    ProfileModel profileModel;

    public ShowProfileOneFragment(ProfileModel profileModel)
    {
     this.profileModel = profileModel;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        profileOneView=inflater.inflate(R.layout.show_profile_one_layout,container,false);
         intializeViews();
         setData();

        return profileOneView;
    }

    public void setData()
    {
        userNameEdit.setText(profileModel.getUserName());
        userEmailEdit.setText(profileModel.getUserEmail());
        userMobileEdit.setText(profileModel.getUserMobileNumber());
        Glide.with(getContext()).load(profileModel.getUserImageLink()).placeholder(R.drawable.blue_gradient_background).centerCrop().into(userProfileImage);
    }

    private void intializeViews()
    {
        context= getContext();
        userProfileImage = profileOneView.findViewById(R.id.profile_user_thumbnail_image_id);
        userProfileImage.setOnClickListener(this);
        userNameEdit=profileOneView.findViewById(R.id.user_name_profile);
        userNameEdit.setText(profileModel.getUserName());
        userEmailEdit=profileOneView.findViewById(R.id.user_email_profile);
        userMobileEdit=profileOneView.findViewById(R.id.user_mobile_profile);
    }

    @Override
    public void onClick(View view) {

        if(R.id.profile_user_thumbnail_image_id==view.getId())
        {
            Utils.openZoomableImage(context,profileModel.getUserImageLink());
        }
    }
}