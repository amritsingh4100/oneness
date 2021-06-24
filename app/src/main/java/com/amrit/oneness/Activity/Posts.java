package com.amrit.oneness.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amrit.oneness.Adapters.PostRecyclerViewAdapter;
import com.amrit.oneness.Customs.Keys;
import com.amrit.oneness.Customs.Utils;
import com.amrit.oneness.Interfaces.PostsListener;
import com.amrit.oneness.Interfaces.RecyclerViewCallback;
import com.amrit.oneness.Models.PostModel;
import com.amrit.oneness.Models.ProfileModel;
import com.amrit.oneness.R;
import com.amrit.oneness.RealTimeDatabase.PostsRealtimeDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Posts extends AppCompatActivity implements RecyclerViewCallback, View.OnClickListener, PostsListener {

    String TAG = getClass().getSimpleName();
    FirebaseAuth mAuth;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    PostRecyclerViewAdapter postRecyclerViewAdapter;
    FloatingActionButton createPostBt;

    //Actionbar views here
    ImageView profileImg, filterImg, notificationImg;
    TextView notificationCountTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_layout);
        //setting up action bar here
        setActionBar();
        // intializing all elements from the views
        intializeViews();
        //getting data from the firebase

        PostsRealtimeDatabase postsRealtimeDatabase = new PostsRealtimeDatabase(this);
        postsRealtimeDatabase.allPostsListener(this);
    }

    private void intializeViews() {
        mAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.post_recycelerview_id);
        createPostBt=findViewById(R.id.create_post_floating_bt);
        createPostBt.setOnClickListener(this);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        postRecyclerViewAdapter=new PostRecyclerViewAdapter(this,this);
        recyclerView.setAdapter(postRecyclerViewAdapter);

        //action views here
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onProfileIconClick(View view)
    {
        // when the icon is clicked open profile here
        Intent openCreateProfile = new Intent(this, EditProfile.class);
        startActivity(openCreateProfile, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

    }

    public void onPostsInterestsFilter(View view)
    {
       // when filters were clicked
    }

    public void onNotificationsClicked(View view)
    {
        // when notifications is clicked
    }

    private void setActionBar()
    {
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.posts_actionbar_layout);
        //getSupportActionBar().setElevation(0);
        View view = getSupportActionBar().getCustomView();
    }

    @Override
    public void itemClicked(String keyID) {

        Intent openPostWithCommentsScreen = new Intent(this,PostWithComments.class);
        openPostWithCommentsScreen.putExtra(Keys.postId,keyID);
        startActivity(openPostWithCommentsScreen);
    }

    @Override
    public void onClick(View view) {
        if(R.id.create_post_floating_bt==view.getId())
        {
            Intent intent = new Intent(this,CreatePost.class);
            startActivity(intent);
        }
    }

    @Override
    public void updatePostsList(List<PostModel> postModelList) {
        postRecyclerViewAdapter.updateList(postModelList);
    }

    @Override
    public void updatedPost(PostModel postModel) {

    }

}