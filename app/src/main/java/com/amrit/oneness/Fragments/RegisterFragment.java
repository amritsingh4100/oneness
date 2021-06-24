package com.amrit.oneness.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.amrit.oneness.Customs.Utils;
import com.amrit.oneness.Interfaces.FragmentInterface;
import com.amrit.oneness.Interfaces.UtilInterface;
import com.amrit.oneness.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;

import java.util.concurrent.Executor;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    String TAG= getClass().getSimpleName();

    View registerFragmentView;
    EditText emailEdit,passwordEdit;
    FirebaseAuth mAuth;
    ImageView backImg;
    FragmentInterface fragmentInterface;
    CardView registerBt;
    Context context;

    public RegisterFragment(FragmentInterface fragmentInterface) {
        this.fragmentInterface=fragmentInterface;
        // make connect here
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        registerFragmentView=inflater.inflate(R.layout.register_layout,container,false);
        intializeViews();

        return registerFragmentView;
    }

    private void intializeViews()
    {
        context=getContext();
        mAuth = FirebaseAuth.getInstance();
        emailEdit=registerFragmentView.findViewById(R.id.register_email_edit_id);
        passwordEdit=registerFragmentView.findViewById(R.id.register_password_edit_id);

        backImg=registerFragmentView.findViewById(R.id.register_back_id);
        backImg.setOnClickListener(this);
        registerBt=registerFragmentView.findViewById(R.id.register_register_bt_id);
        registerBt.setOnClickListener(this);
    }

    public void checkForLogin(String emailString,String passwordString)
    {
        if(Utils.validateEmail(emailString))
        {
            Utils.showCustomAlert(getContext(),"Invalid email");
        }
        else if(passwordString.isEmpty())
        {
            Utils.showCustomAlert(getContext(),"Password cannot be empty");
        }
        else if(passwordString.length()<7)
        {
            Utils.showCustomAlert(getContext(),"Password should be more than 6 characters");
        }
        else
        {
            checkEmailExistsOrNot(emailString,passwordString);
        }
    }

    void checkEmailExistsOrNot(String emailString, String passwordString){
        mAuth.fetchSignInMethodsForEmail(emailEdit.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {

            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                Log.d(TAG,""+task.getResult().getSignInMethods().size());
                if (task.getResult().getSignInMethods().size() == 0){
                    //create account here
                    createAccount(emailString,passwordString);
                    // email not existed
                }else {
                    Utils.showCustomAlert(context, "email already exists!", "Forgot Password", "Cancel", new UtilInterface() {
                        @Override
                        public void ifPositive() {
                            // intitlize the forgot password query
                        }

                        @Override
                        public void ifNegative() {
                            // do nothing
                        }
                    });
                    // email existed
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onClick(View view) {

        if(R.id.register_back_id==view.getId())
        {
            fragmentInterface.switchTo(0);
        }
        else if(R.id.register_register_bt_id==view.getId())
        {
            checkForLogin(emailEdit.getText().toString(),passwordEdit.getText().toString());

        }
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

    private void createAccount(String email, String password) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Utils.showCustomAlert(context, "Register successful", "Okay", "", new UtilInterface() {
                                @Override
                                public void ifPositive() {
                                    fragmentInterface.switchTo(0);
                                }

                                @Override
                                public void ifNegative() {

                                }
                            });
                        } else {
                            Utils.showCustomAlert(context,"Something went wrong! please try again.");
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        }
                    }
                });
        // [END create_user_with_email]
    }
}