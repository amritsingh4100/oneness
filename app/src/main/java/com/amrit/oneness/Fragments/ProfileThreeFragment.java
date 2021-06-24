package com.amrit.oneness.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.amrit.oneness.RealTimeDatabase.ProfileRealtimeDatabase;
import com.amrit.oneness.Interfaces.ProfileListener;
import com.amrit.oneness.Models.ProfileModel;
import com.amrit.oneness.R;

public class ProfileThreeFragment extends Fragment implements ProfileListener, TextWatcher {

    // fixed variable
    String TAG= getClass().getSimpleName();
    Context context;

    View profileThreeView;
    EditText userAboutEdit;

    ProfileRealtimeDatabase profileRealtimeDatabase;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        profileThreeView=inflater.inflate(R.layout.profile_three_layout,container,false);
         intializeViews();

        return profileThreeView;
    }

    private void intializeViews()
    {
        context= getContext();
        userAboutEdit = profileThreeView.findViewById(R.id.profile_three_about);
        //
        profileRealtimeDatabase= new ProfileRealtimeDatabase();
        profileRealtimeDatabase.singleProfileListener(this);
        userAboutEdit.addTextChangedListener(this);

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        String currentString = userAboutEdit.getText().toString();
        //Utils.print(TAG,"Currently tpyed :"+currentString);
        profileRealtimeDatabase.updateAbout(currentString);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void updatedProfileData(ProfileModel profileModel) {
        userAboutEdit.setText(profileModel.getUserAbout());
    }
}