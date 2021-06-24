package com.amrit.oneness.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.amrit.oneness.Customs.Keys;
import com.amrit.oneness.Customs.Utils;
import com.amrit.oneness.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.thorny.photoeasy.OnPictureReady;
import com.thorny.photoeasy.PhotoEasy;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

public class ImagePreview extends AppCompatActivity implements OnProgressListener<UploadTask.TaskSnapshot>, OnFailureListener, OnSuccessListener<UploadTask.TaskSnapshot>, View.OnClickListener {

    String TAG = getClass().getSimpleName(),publicImageLink="";
    ImageView previewImage;
    CardView showEdit;
    LinearLayout showProgress;
    LinearProgressIndicator imageUploadLinearProgress;
    TextView progressTxt;
    PhotoEasy photoEasy;

    StorageReference imageReference;
    Button selectBt;
    int requestCodeReceived;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_image_layout);
        initControls();

        String[] appPermissionArray = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    Utils.print(TAG,"camera permission boolean:"+checkAndRequestPermission(appPermissionArray));
        openCamera();
        if(checkAndRequestPermission(appPermissionArray)){
            openCamera();
        }
        requestCodeReceived = getIntent().getIntExtra(Keys.requestCode,0);
    }

    private void initControls()
    {
        previewImage = findViewById(R.id.preview_image);
        showEdit=findViewById(R.id.show_edit_bt);
        showEdit.setOnClickListener(this);
        showProgress=findViewById(R.id.show_progress_bar_holder);
        imageUploadLinearProgress=findViewById(R.id.upload_image_linear_progress);
        progressTxt=findViewById(R.id.image_progress_txt);
        selectBt=findViewById(R.id.preview_image_select_bt);
        selectBt.setOnClickListener(this);
    }

    public boolean checkAndRequestPermission(String[] appPermissions)
    {
        List<String> permissionNeededList= new ArrayList<>();
        // check which permission are needed to work
        for(String singlePermission : appPermissions)
        {

            boolean isPermissionNeeded = ContextCompat.checkSelfPermission(this,singlePermission)!=PackageManager.PERMISSION_GRANTED;
            Utils.print(TAG,"is permission needed: "+isPermissionNeeded);
            if(ContextCompat.checkSelfPermission(this,singlePermission)!= PackageManager.PERMISSION_GRANTED)
            {
                permissionNeededList.add(singlePermission);
            }
        }

        if(!permissionNeededList.isEmpty())
        {
            // if not empty, ask for remaining permissions
            ActivityCompat.requestPermissions(this, permissionNeededList.toArray(new String[0]),000);
            return false;
        }

        //app already has all the permission, green signal!
        return true;
    }

    public void openCamera() {
        photoEasy = PhotoEasy.builder()
                .setActivity(this)
                .setMimeType(PhotoEasy.MimeType.imagePng)
                .setStorageType(PhotoEasy.StorageType.internal)
                .build();
        photoEasy.startActivityForResult(this);
    }

    public void showProgressBar(int progress)
    {
        imageUploadLinearProgress.setProgress(progress);
        progressTxt.setText("Progress "+progress+"%");
        if(progress==100) showEdit();
    }
    public void showEdit()
    {
        showProgress.setVisibility(View.GONE);
        showEdit.setVisibility(View.VISIBLE);
        showEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera();
            }
        });

        imageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                publicImageLink = task.getResult().toString();
                selectBt.setVisibility(View.VISIBLE);
                Utils.print(TAG, "image link:"+ task.getResult().toString());
            }
        });

    }

    private long getRandomTimeStamp()
    {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        return calendar.getTimeInMillis()+ new Random().nextInt(999999) + 999999;
    }

    public void uploadImageToStorage(Bitmap bitmap)
    {
         imageReference = FirebaseStorage.getInstance().getReference().child("Images/"+getRandomTimeStamp());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imageReference.putBytes(data);
        // listen to the progress
        uploadTask.addOnProgressListener(this);

        // listen to failure
        uploadTask.addOnFailureListener(this);

        // listen to success
        uploadTask.addOnSuccessListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            photoEasy.onActivityResult(requestCode, resultCode, new OnPictureReady() {
                @Override
                public void onFinish(Bitmap thumbnail) {
                    if(thumbnail!=null)
                    {
                        previewImage.setImageBitmap(thumbnail);
                        uploadImageToStorage(thumbnail);
                    }
                    else finish();
                }
            });
    }

    @Override
    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
        long totalToTransfer = snapshot.getTotalByteCount();
        long hasTransferred = snapshot.getBytesTransferred();

        int percentage = (int)((float)hasTransferred/totalToTransfer*100);
        showProgress.setVisibility(View.VISIBLE);
        showEdit.setVisibility(View.GONE);
        showProgressBar(percentage);
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        Toast.makeText(this, "Could not upload Image, try again! ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
        showEdit();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.preview_image_select_bt){
            Intent intent=new Intent();
            intent.putExtra(Keys.previewImageLink,publicImageLink);
            setResult(requestCodeReceived,intent);
            finish();
        }
        else if(view.getId()==R.id.show_edit_bt)
        {
            openCamera();
        }
        else finish();
    }
}