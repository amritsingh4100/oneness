package com.amrit.oneness.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.amrit.oneness.Customs.CustomFlexBoxLayout;
import com.amrit.oneness.Customs.Keys;
import com.amrit.oneness.Customs.Utils;
import com.amrit.oneness.Models.CommentModel;
import com.amrit.oneness.Models.InterestModel;
import com.amrit.oneness.Models.PostModel;
import com.amrit.oneness.Models.ProfileModel;
import com.amrit.oneness.R;
import com.bumptech.glide.Glide;
import com.google.android.flexbox.FlexboxLayout;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thorny.photoeasy.PhotoEasy;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class CreatePost extends AppCompatActivity implements View.OnClickListener {

    String TAG = getClass().getSimpleName();
    FirebaseAuth mAuth;

    EditText titleEdit, detailsEdit;
    ImageView cancelCreatePost;
    LinearLayout addANewImageHolder;
    Button createPostBt;
    DatabaseReference databaseReference;

   FlexboxLayout flexboxLayout;

    // interest array here
    ArrayList<InterestModel> interestModelArrayList=new ArrayList<>();
    CustomFlexBoxLayout customFlexBoxLayout;
    LinearLayout loadingSmokeScreen;
    PhotoEasy photoEasy;

    ImageView addPhotoShow;
    LinearProgressIndicator linearImageUploadProgressIndicator;

    int requestCode = 100;

    // user details here in profile object
    ProfileModel profileModel;
    String imageLink = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_post_layout);
        intializeViews();
        interestListener();

        // find user who is logged in
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        profileListener(user.getUid());

    }

    private void profileListener(String userID)
    {

        FirebaseDatabase.getInstance().getReference().child(Keys.nodeProfiles).child(userID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Utils.print(TAG,"profile for user id:" +userID);
                        profileModel = (ProfileModel) dataSnapshot.getValue(ProfileModel.class);
                        Utils.print(TAG,""+profileModel.getUserName());

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(CreatePost.this, "Could not find user, please contact support!", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void intializeViews() {
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        titleEdit = findViewById(R.id.create_post_title_edit_id);
        detailsEdit = findViewById(R.id.create_post_details_id);
        cancelCreatePost = findViewById(R.id.create_post_cancel);
        cancelCreatePost.setOnClickListener(this);
        addANewImageHolder = findViewById(R.id.create_post_add_a_photo_id);
        addANewImageHolder.setOnClickListener(this);
        createPostBt = findViewById(R.id.create_post_post_bt_id);
        createPostBt.setOnClickListener(this);
        flexboxLayout= findViewById(R.id.create_post_flexbox_id);
        // hidden layout
        loadingSmokeScreen = findViewById(R.id.create_post_hide_id);
        addPhotoShow=findViewById(R.id.create_post_show_image);
        linearImageUploadProgressIndicator=findViewById(R.id.upload_image_linear_progress);
    }

    @Override
    public void onClick(View view) {

        if(R.id.create_post_cancel==view.getId())
        {
            finish();
        }
        else if(R.id.create_post_add_a_photo_id==view.getId())
        {
            Intent openImagePreview = new Intent(this, ImagePreview.class);
            openImagePreview.putExtra(Keys.requestCode,requestCode);
            startActivityForResult(openImagePreview,requestCode);
        }
        else if(R.id.create_post_post_bt_id==view.getId())
        {
            if(validatePost(titleEdit.getText().toString(),detailsEdit.getText().toString())) createANewPost();
        }
        else {
            // do somethng here

            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Utils.print(TAG,"result code: "+resultCode+" -- "+requestCode);

        if(resultCode==100)
        {
            imageLink= data.getStringExtra(Keys.previewImageLink);
            addPhotoShow.setVisibility(View.VISIBLE);
            Glide.with(CreatePost.this).load(imageLink).centerCrop().placeholder(R.drawable.blue_gradient_background).into(addPhotoShow);
        }
    }

    private void interestListener() {
        FirebaseDatabase.getInstance().getReference().child(Keys.nodeInterests)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        loadingSmokeScreen.setVisibility(View.GONE);
                        Utils.print(TAG,dataSnapshot.getKey());

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            InterestModel interestModel = snapshot.getValue(InterestModel.class);
                            Utils.print(TAG,"interest : "+interestModel.getInterestName()+" is selected : "+interestModel.isInterestSelected());
                            interestModelArrayList.add(interestModel);
                        }

                        // updating flexbox layout

                        customFlexBoxLayout = new CustomFlexBoxLayout(CreatePost.this);
                        customFlexBoxLayout.setInterestArrayListToShow(interestModelArrayList).isClickAble(true).addViews(flexboxLayout);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }


    private long getEpochTime()
    {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        return calendar.getTimeInMillis() / 1000L;
    }

    public boolean validatePost(String postTitle, String postDetails)
    {
        if(postTitle.isEmpty())
        {
            Utils.showCustomAlert(this,"Title cannot be empty!");
            return false;
        }
        else if(postDetails.isEmpty())
        {
            Utils.showCustomAlert(this,"Details cannot be empty!");
            return false;
        }
        else return true;
    }

    private void createANewPost()
    {
        String titleString = titleEdit.getText().toString();
        String detailsString = detailsEdit.getText().toString();

        ArrayList<CommentModel> blanksCommentModels =new ArrayList<>();

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        PostModel postModel =new PostModel(titleString,detailsString,profileModel.getUserImageLink(),imageLink,
                profileModel.getUserName(),firebaseUser.getUid(),getEpochTime(), blanksCommentModels,customFlexBoxLayout.getSelectedInterestArrayList());

        databaseReference.child(Keys.nodePosts).push().setValue(postModel);
        finish();
    }
}