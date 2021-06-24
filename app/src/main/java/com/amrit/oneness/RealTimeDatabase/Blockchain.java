package com.amrit.oneness.RealTimeDatabase;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.amrit.oneness.Activity.EditProfile;
import com.amrit.oneness.Activity.Posts;
import com.amrit.oneness.Activity.Splashscreen;
import com.amrit.oneness.Customs.Keys;
import com.amrit.oneness.Customs.Utils;
import com.amrit.oneness.Models.BlockChainModel;
import com.amrit.oneness.Models.PostModel;
import com.amrit.oneness.Models.ProfileModel;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class Blockchain {

    String TAG = getClass().getSimpleName();
    DatabaseReference databaseReference;

    String blockChain= "blockchain";

    public Blockchain()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void addItem(String itemName,String hashKey) {

        Item item = new Item(itemName,getRandomString());
        BlockChainModel blockChainModel= new BlockChainModel(getRandomString(),item);
        databaseReference.child(blockChain).setValue(blockChainModel);
        FirebaseDatabase.getInstance().getReference().child(blockChain).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Utils.print(TAG,"add item was clicked");
                        if(dataSnapshot.exists()){
                            String randomOne = getRandomString();
                        }
                        else
                        {
                            Item item = new Item(itemName,getRandomString());
                            BlockChainModel blockChainModel= new BlockChainModel(getRandomString(),item);
                            databaseReference.child(blockChain).setValue(blockChainModel);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Utils.print(TAG,"Profile not found"+databaseError.toString());
                    }
                });
    }

    public String getRandomString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }
}
