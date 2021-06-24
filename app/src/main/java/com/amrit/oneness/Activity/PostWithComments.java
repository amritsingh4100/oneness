package com.amrit.oneness.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amrit.oneness.Adapters.PostCommentsRecyclerViewAdapter;
import com.amrit.oneness.Customs.Keys;
import com.amrit.oneness.Customs.Utils;
import com.amrit.oneness.Interfaces.PostsListener;
import com.amrit.oneness.Interfaces.RecyclerViewCallback;
import com.amrit.oneness.Models.CommentModel;
import com.amrit.oneness.Models.PostModel;
import com.amrit.oneness.Models.ProfileModel;
import com.amrit.oneness.R;
import com.amrit.oneness.RealTimeDatabase.PostsRealtimeDatabase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TimeZone;

public class PostWithComments extends AppCompatActivity implements View.OnClickListener, PostsListener {

    String TAG = getClass().getSimpleName();
    FirebaseAuth mAuth;
    RecyclerView postCommentsRecylerView;
    String postKey;
    RelativeLayout postCommentsHideLayout;
    LinearLayoutManager linearLayoutManager;

    // adapter class connecting data
    PostCommentsRecyclerViewAdapter postCommentsRecyclerViewAdapter;

    // comments objects
    EditText postCommentsContentEdit;
    TextView postCommentsAddCommentTxt;

    PostModel postModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posts_with_comments_layout);
        intializeViews();

        PostsRealtimeDatabase postsRealtimeDatabase = new PostsRealtimeDatabase(this);
        postKey=getIntent().getStringExtra(Keys.postId);
        postsRealtimeDatabase.getPost(postKey).postListener(this);

    }

    private void intializeViews() {

        mAuth = FirebaseAuth.getInstance();
        postCommentsRecylerView=findViewById(R.id.post_comments_recycler_id);
        postCommentsHideLayout=findViewById(R.id.post_comments_show_progress_id);

        // setting up the recyclerview

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        postCommentsRecylerView.setLayoutManager(linearLayoutManager);
        postCommentsRecylerView.setItemAnimator(new DefaultItemAnimator());
        postCommentsRecyclerViewAdapter=new PostCommentsRecyclerViewAdapter(this);
        postCommentsRecylerView.setAdapter(postCommentsRecyclerViewAdapter);

        // comments objects
        postCommentsContentEdit = findViewById(R.id.post_comments_content_edit);
        postCommentsAddCommentTxt = findViewById(R.id.post_comments_add_comment_txt);
        postCommentsAddCommentTxt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(R.id.post_comments_add_comment_txt==view.getId())
        {
            String commentContent = postCommentsContentEdit.getText().toString();
            if(commentContent.isEmpty()) Utils.showCustomAlert(this,"Comment cannot be empty");
            else {
                PostsRealtimeDatabase postsRealtimeDatabase = new PostsRealtimeDatabase(PostWithComments.this);
                postsRealtimeDatabase.getPost(postKey).addCommentToPost(commentContent);
                postCommentsContentEdit.setText("");

                // here is the size of the commentlist size
                int size = postModel.getCommentModelArrayList().size();
                postCommentsRecylerView.scrollToPosition(size+1);
            }
        }
    }

    @Override
    public void updatePostsList(List<PostModel> postModelList) {

    }

    @Override
    public void updatedPost(PostModel postModel) {
        this.postModel=postModel;
        postCommentsRecyclerViewAdapter.updateList(postModel);
    }
}