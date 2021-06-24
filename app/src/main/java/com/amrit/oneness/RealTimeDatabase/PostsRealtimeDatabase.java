package com.amrit.oneness.RealTimeDatabase;


import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.amrit.oneness.Activity.Posts;
import com.amrit.oneness.Customs.Keys;
import com.amrit.oneness.Customs.Utils;
import com.amrit.oneness.Interfaces.PostsListener;
import com.amrit.oneness.Interfaces.ProfileListener;
import com.amrit.oneness.Models.CommentModel;
import com.amrit.oneness.Models.InterestModel;
import com.amrit.oneness.Models.PostModel;
import com.amrit.oneness.Models.ProfileModel;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PostsRealtimeDatabase {

    String TAG = getClass().getSimpleName();
    DatabaseReference databaseReference;
    String postKey;

    ProfileModel profileModel;
    Context context;

    public PostsRealtimeDatabase(Context context)
    {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        this.context=context;
    }

    public PostsRealtimeDatabase getPost (String postKey)
    {
        this.postKey=postKey;
        return this;
    }

    public void allPostsListener(PostsListener postsListener)
    {
        databaseReference.child(Keys.nodePosts)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        List<PostModel> postModelArrayList = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String postKey = snapshot.getKey();
                            PostModel postModel = snapshot.getValue(PostModel.class);
                            postModel.setPostKey(postKey);

                            postModelArrayList.add(postModel);
                        }
                        postsListener.updatePostsList(postModelArrayList);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Utils.print(TAG,"Could not load posts data :"+databaseError.getDetails());
                    }
                });
    }

    public void postListener(PostsListener postsListener)
    {
        databaseReference.child(Keys.nodePosts).child(postKey)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        PostModel postModel = dataSnapshot.getValue(PostModel.class);

                        postsListener.updatedPost(postModel);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Utils.print(TAG,"Could not load posts data :"+databaseError.getDetails());
                    }
                });
    }



    public void addCommentToPost(String comment)
    {
        databaseReference.child(Keys.nodePosts).child(postKey).child(Keys.postCommentsList).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        List<CommentModel> commentModelList = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            CommentModel commentModel = snapshot.getValue(CommentModel.class);
                            commentModelList.add(commentModel);
                        }
                        SpData spData = new SpData(context);
                        CommentModel currentCommentModel = new CommentModel(spData.getUserName(),comment,Utils.getCurrentTimeInString(),spData.getUserImageLink());

                        commentModelList.add(currentCommentModel);
                        databaseReference.child(Keys.nodePosts).child(postKey).child(Keys.postCommentsList).setValue(commentModelList);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Utils.print(TAG,"Could not load posts data :"+databaseError.getDetails());
                    }
                });
    }
}
