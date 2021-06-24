package com.amrit.oneness.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.amrit.oneness.Activity.EditProfile;
import com.amrit.oneness.Activity.Posts;
import com.amrit.oneness.Customs.Keys;
import com.amrit.oneness.Customs.Utils;
import com.amrit.oneness.Interfaces.FragmentInterface;
import com.amrit.oneness.Models.CommentModel;
import com.amrit.oneness.Models.InterestModel;
import com.amrit.oneness.Models.PostModel;
import com.amrit.oneness.Models.ProfileModel;
import com.amrit.oneness.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginFragment extends Fragment implements View.OnClickListener {

    String TAG= getClass().getSimpleName();
    FirebaseAuth mAuth;
    Activity activity;
    EditText emailEdit,passwordEdit;
    View fragmentOneView;
    CardView cardView;
    TextView registerTxt;
    ImageView cancelImgBt;
    FragmentInterface fragmentInterface;

    public LoginFragment(FragmentInterface fragmentInterface) {
        // make connect here
        this.fragmentInterface=fragmentInterface;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         fragmentOneView=inflater.inflate(R.layout.login_layout,container,false);
         intializeViews();

        return fragmentOneView;
    }

    private void intializeViews()
    {
        mAuth = FirebaseAuth.getInstance();
        activity=getActivity();

        cancelImgBt=fragmentOneView.findViewById(R.id.cancel_login_id);
        cancelImgBt.setOnClickListener(this);

        registerTxt=fragmentOneView.findViewById(R.id.login_register_txt_id);
        registerTxt.setOnClickListener(this);

        cardView=fragmentOneView.findViewById(R.id.login_bt_id);
        cardView.setOnClickListener(this);

        emailEdit=fragmentOneView.findViewById(R.id.login_email_edit_id);
        passwordEdit= fragmentOneView.findViewById(R.id.login_password_edit_id);

    }

    public void checkForLogin(String emailString,String passwordString)
    {
        if(Utils.validateEmail(emailString))
        {
            Utils.showCustomAlert(activity,"Invalid email");
        }
        else if(passwordString.isEmpty())
        {
            Utils.showCustomAlert(activity,"Password cannot be empty");
        }
        else if(passwordString.length()<6)
        {
            Utils.showCustomAlert(activity,"Password should be more than 6 characters");
        }
        else
        {
            loginUser(emailString,passwordString);
        }
    }


    private void createANewPost(FirebaseUser firebaseUser) {
        List<InterestModel> interestModelList = new ArrayList<>();
        ProfileModel profileModel = new ProfileModel(firebaseUser.getEmail(), firebaseUser.getEmail(), "", "", "", interestModelList);

        FirebaseDatabase.getInstance().getReference().child(Keys.nodeProfiles).child(firebaseUser.getUid()).setValue(profileModel);
        Intent openPost = new Intent(getActivity(), Posts.class);
        startActivity(openPost);
        getActivity().finish();
    }



    private void loginUser(String mail, final String password) {

        mAuth.signInWithEmailAndPassword(mail, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            createANewPost(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Utils.print(TAG,task.getException().toString());
                            Utils.showCustomAlert(getContext(),"Username and password do not match!");
                        }
                    }
                });
    }

    private void checkIfUserProfileExists(String userID) {
        FirebaseDatabase.getInstance().getReference().child(Keys.nodeProfiles).child(userID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){


                        }
                        else
                        {
                            Intent openProfile = new Intent(getActivity(), EditProfile.class);
                            startActivity(openProfile);
                            getActivity().finish();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Utils.print(TAG,"Profile not found"+databaseError.toString());
                    }
                });
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            //reload();
        }
    }


    @Override
    public void onClick(View view) {

        if(R.id.login_bt_id==view.getId())
        {
           checkForLogin(emailEdit.getText().toString(),passwordEdit.getText().toString());
        }
        else if(R.id.login_register_txt_id==view.getId())
        {
            Utils.print(TAG,"in login txt click");
            fragmentInterface.switchTo(1);

        }
        else if(R.id.cancel_login_id==view.getId())
        {
            getActivity().finish();
        }
        else
        {

        }
    }
}