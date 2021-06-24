package com.amrit.oneness.RealTimeDatabase;


import androidx.annotation.NonNull;

import com.amrit.oneness.Customs.Keys;
import com.amrit.oneness.Interfaces.ProfileListener;
import com.amrit.oneness.Models.InterestModel;
import com.amrit.oneness.Models.ProfileModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProfileRealtimeDatabase {

    String userID="";
    String userEmail="";
    DatabaseReference databaseReference;

    public ProfileRealtimeDatabase()
    {
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void createProfile(ProfileModel profileModel)
    {
        databaseReference.child(Keys.nodeProfiles).child(userID).setValue(profileModel);
    }

    public void updateUserName(String userName)
    {
        databaseReference.child(Keys.nodeProfiles).child(userID).child(Keys.profileUserName).setValue(userName);
    }
    public void updateUserEmail(String userEmail)
    {
        databaseReference.child(Keys.nodeProfiles).child(userID).child(Keys.profileUserEmail).setValue(userEmail);
    }
    public void updateMobileNumber(String userMobileNumber)
    {
        databaseReference.child(Keys.nodeProfiles).child(userID).child(Keys.profileUserMobileNumber).setValue(userMobileNumber);
    }
    public void updateAbout(String userAbout)
    {
        databaseReference.child(Keys.nodeProfiles).child(userID).child(Keys.profileUserAbout).setValue(userAbout);
    }
    public void updateUserInterests(List<InterestModel> interestModelList)
    {
        databaseReference.child(Keys.nodeProfiles).child(userID).child(Keys.profileUserInterestList).setValue(interestModelList);
    }

    public void liveProfileListener(ProfileListener profileListener)
    {
        databaseReference.child(Keys.nodeProfiles).child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ProfileModel profileModel = snapshot.getValue(ProfileModel.class);
                profileListener.updatedProfileData(profileModel);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void singleProfileListener(ProfileListener profileListener)
    {
        databaseReference.child(Keys.nodeProfiles).child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
                    ProfileModel profileModel = snapshot.getValue(ProfileModel.class);
                    profileListener.updatedProfileData(profileModel);
                }
                else
                {
                    downloadAllAvailableInterests();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public ProfileRealtimeDatabase getProfileFromUserID(String userID)
    {
        this.userID=userID; // override the default userID with your own user ID
        return this;
    }


    public void downloadAllAvailableInterests()
    {
        databaseReference.child(Keys.nodeInterests).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<InterestModel> interestModelArrayList = new ArrayList<>();

                for (DataSnapshot objectSnapshot : snapshot.getChildren()) {
                    InterestModel interestModel = objectSnapshot.getValue(InterestModel.class);
                    interestModelArrayList.add(interestModel);
                }

                ProfileModel profileModel = new ProfileModel(userEmail,userEmail,"","","",interestModelArrayList);
                createProfile(profileModel);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateUserImageLink(String userImageLink)
    {
        databaseReference.child(Keys.nodeProfiles).child(userID).child(Keys.profileUserImageLink).setValue(userImageLink);
    }
}
