package com.amrit.oneness.RealTimeDatabase;

import android.content.Context;
import android.content.SharedPreferences;

import com.amrit.oneness.Customs.Keys;

public class SpData {
    String TAG = getClass().getSimpleName();
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    String userNameKey="username";
    String userPhotoLinkKey= "userphotolink";
    String loginstatus="loginstatus";

    public SpData(Context context)
    {
        sharedPreferences = context.getSharedPreferences(Keys.spTableKey,0);
        editor=sharedPreferences.edit();
    }

    public void setUserName(String userName)
    {
        editor.putString(userNameKey,userName);
        editor.commit();
    }

    public String getUserName()
    {
        return sharedPreferences.getString(userNameKey,"");
    }

    public void setUserImageLink(String imageLink)
    {
        editor.putString(userPhotoLinkKey,imageLink);
        editor.commit();
    }

    public String getUserImageLink()
    {
        return sharedPreferences.getString(userPhotoLinkKey,"");
    }

    public void setLogin(boolean loginStatus)
    {
        editor.putBoolean(loginstatus,loginStatus);
        editor.commit();
    }

    public boolean isLoggedIn()
    {
        return sharedPreferences.getBoolean(loginstatus,false);
    }
}
