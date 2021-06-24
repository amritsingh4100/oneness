package com.amrit.oneness.Fragments;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import com.amrit.oneness.Activity.EditProfile;
import com.amrit.oneness.Activity.ImagePreview;
import com.amrit.oneness.Customs.Keys;
import com.amrit.oneness.RealTimeDatabase.ProfileRealtimeDatabase;
import com.amrit.oneness.Customs.Utils;
import com.amrit.oneness.Interfaces.ProfileListener;
import com.amrit.oneness.Models.ProfileModel;
import com.amrit.oneness.R;

import com.amrit.oneness.RealTimeDatabase.SpData;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.textfield.TextInputLayout;

public class ProfileOneFragment extends Fragment implements View.OnClickListener, ProfileListener{

    String TAG = getClass().getSimpleName();
    Context context;
    View profileOneView;
    ImageView userProfileImage;
    TextInputLayout nameInputLayout, emailInputLayout, mobileInputLayout;
    ProgressBar loadingImageProgressBar;

    String defaultImageLink = "";

    ProfileRealtimeDatabase profileRealtimeDatabase;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        profileOneView = inflater.inflate(R.layout.profile_one_layout, container, false);
        intializeViews();

        return profileOneView;
    }

    private void intializeViews() {
        context = getContext();
        userProfileImage = profileOneView.findViewById(R.id.profile_user_thumbnail_image_id);
        userProfileImage.setOnClickListener(this);

        nameInputLayout= profileOneView.findViewById(R.id.full_name_input_layout);
        emailInputLayout= profileOneView.findViewById(R.id.email_input_layout);
        mobileInputLayout= profileOneView.findViewById(R.id.mobile_input_layout);

        loadingImageProgressBar = profileOneView.findViewById(R.id.loading_image_progress_bar);
        loadingImageProgressBar.setVisibility(View.GONE);

        profileRealtimeDatabase = new ProfileRealtimeDatabase();
        profileRealtimeDatabase.singleProfileListener(this);

        editNameWatcher();
        editEmailWatcher();
        editMobileWatcher();

    }

    private void editNameWatcher() {

        nameInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            // ...
            @SuppressLint("ResourceAsColor")
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                if (text.toString().isEmpty() || text.length() < 4) {
                    nameInputLayout.setError("Name should be 3 letter or more");
                    nameInputLayout.setErrorEnabled(true);
                } else {
                    profileRealtimeDatabase.updateUserName(text.toString());
                    SpData spData= new SpData(context);
                    spData.setUserName(text.toString());
                    nameInputLayout.setErrorEnabled(false);

                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void editEmailWatcher() {

        emailInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            // ...
            @SuppressLint("ResourceAsColor")
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                if (Utils.validateEmail(text.toString())) {
                    emailInputLayout.setError("Invalid email!");
                    emailInputLayout.setErrorEnabled(true);
                } else {
                    profileRealtimeDatabase.updateUserEmail(text.toString());
                    emailInputLayout.setErrorEnabled(false);

                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void editMobileWatcher() {

        mobileInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            // ...
            @SuppressLint("ResourceAsColor")
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                if (text.toString().isEmpty() || text.length() < 10) {
                    mobileInputLayout.setError("Mobile number should be more than 10 digits!");
                    mobileInputLayout.setErrorEnabled(true);
                } else {
                    profileRealtimeDatabase.updateMobileNumber(text.toString());
                    mobileInputLayout.setErrorEnabled(false);

                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.profile_user_thumbnail_image_id) {
            // add photo was clicked
            Intent openImagePreview = new Intent(getActivity(), ImagePreview.class);
            openImagePreview.putExtra(Keys.requestCode, EditProfile.requestCode);
            startActivityForResult(openImagePreview, EditProfile.requestCode);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == 200) {
            defaultImageLink = data.getStringExtra(Keys.previewImageLink);
            SpData spData = new SpData(context);
            spData.setUserImageLink(defaultImageLink);
            // setting design here
            loadingImageProgressBar.setVisibility(View.VISIBLE);
            profileRealtimeDatabase.updateUserImageLink(defaultImageLink);
            Glide.with(context).load(defaultImageLink).centerCrop().listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    loadingImageProgressBar.setVisibility(View.GONE);
                    Toast.makeText(context, "Image load fail!", Toast.LENGTH_SHORT).show();
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    loadingImageProgressBar.setVisibility(View.GONE);
                    return false;
                }
            }).into(userProfileImage);
            Utils.print(TAG, "Request was approved : " + requestCode + " image link is: " + defaultImageLink);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void updatedProfileData(ProfileModel profileModel) {
        nameInputLayout.getEditText().setText(profileModel.getUserName());
        emailInputLayout.getEditText().setText(profileModel.getUserEmail());
        mobileInputLayout.getEditText().setText(profileModel.getUserMobileNumber());
        Glide.with(context).load(profileModel.getUserImageLink()).centerCrop().placeholder(R.drawable.blue_gradient_background).into(userProfileImage);
    }
}