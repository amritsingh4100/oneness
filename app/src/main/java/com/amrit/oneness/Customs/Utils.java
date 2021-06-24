package com.amrit.oneness.Customs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.amrit.oneness.Activity.EditProfile;
import com.amrit.oneness.Activity.ShowProfile;
import com.amrit.oneness.Activity.Splashscreen;
import com.amrit.oneness.Activity.ZoomableImageActivity;
import com.amrit.oneness.Interfaces.UtilInterface;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static String TAG= "Utils";

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return !matcher.find();
    }

    public static void isConnectedToInternet(Splashscreen getApplicationContext , UtilInterface utilInterface) {
        boolean status = false;

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (cm != null && cm.getActiveNetwork() != null && cm.getNetworkCapabilities(cm.getActiveNetwork()) != null) {
                // connected to the internet
                status = true;
            }
        } else {
            if (cm != null && cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting()) {
                // connected to the internet
                status = true;
            }
        }

        if(status) utilInterface.ifPositive();
        else utilInterface.ifNegative();
    }

    public static void showCustomAlert(Context context, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void showCustomAlert(Context context, String message, String positiveMessage, String negativeMessage, UtilInterface utilInterface)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(positiveMessage, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                utilInterface.ifPositive();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(negativeMessage, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                utilInterface.ifNegative();
                dialog.dismiss();
                // User cancelled the dig
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static String timeAgo(long secondsAgo) {

        Date pastDate = new Date(secondsAgo);

        Date currentDate = new Date();
        long milliSecPerMinute = 60 * 1000; //Milliseconds Per Minute
        long milliSecPerHour = milliSecPerMinute * 60; //Milliseconds Per Hour
        long milliSecPerDay = milliSecPerHour * 24; //Milliseconds Per Day
        long milliSecPerMonth = milliSecPerDay * 30; //Milliseconds Per Month
        long milliSecPerYear = milliSecPerDay * 365; //Milliseconds Per Year
        //Difference in Milliseconds between two dates
        long msExpired = currentDate.getTime() - pastDate.getTime();

        //Second or Seconds ago calculation
        if (msExpired < milliSecPerMinute) {
            if (Math.round(msExpired / 1000) == 1) {
                return String.valueOf(Math.round(msExpired / 1000)) + " second ago... ";
            } else {
                return String.valueOf(Math.round(msExpired / 1000) + " seconds ago...");
            }
        }

        //Minute or Minutes ago calculation
        else if (msExpired < milliSecPerHour) {
            if (Math.round(msExpired / milliSecPerMinute) == 1) {
                return String.valueOf(Math.round(msExpired / milliSecPerMinute)) + " minute ago... ";
            } else {
                return String.valueOf(Math.round(msExpired / milliSecPerMinute)) + " minutes ago... ";
            }
        }

        //Hour or Hours ago calculation
        else if (msExpired < milliSecPerDay) {
            if (Math.round(msExpired / milliSecPerHour) == 1) {
                return String.valueOf(Math.round(msExpired / milliSecPerHour)) + " hour ago... ";
            } else {
                return String.valueOf(Math.round(msExpired / milliSecPerHour)) + " hours ago... ";
            }
        }

        //Day or Days ago calculation
        else if (msExpired < milliSecPerMonth) {
            if (Math.round(msExpired / milliSecPerDay) == 1) {
                return String.valueOf(Math.round(msExpired / milliSecPerDay)) + " day ago... ";
            } else {
                return String.valueOf(Math.round(msExpired / milliSecPerDay)) + " days ago... ";
            }
        }
        //Month or Months ago calculation
        else if (msExpired < milliSecPerYear) {
            if (Math.round(msExpired / milliSecPerMonth) == 1) {
                return String.valueOf(Math.round(msExpired / milliSecPerMonth)) + "  month ago... ";
            } else {
                return String.valueOf(Math.round(msExpired / milliSecPerMonth)) + "  months ago... ";
            }
        }
        //Year or Years ago calculation
        else {
            if (Math.round(msExpired / milliSecPerYear) == 1) {
                return String.valueOf(Math.round(msExpired / milliSecPerYear)) + " year ago...";
            } else {
                return String.valueOf(Math.round(msExpired / milliSecPerYear)) + " years ago...";
            }
        }

    }

    public static void openProfilePage(Context context, String userID)
    {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if(currentUserId.equals(userID))
        {
            Intent openCreateProfile = new Intent(context, EditProfile.class);
            context.startActivity(openCreateProfile);
        }
        else
        {
            Intent openShowProfile = new Intent(context, ShowProfile.class);
            openShowProfile.putExtra(Keys.userID,userID);
            context.startActivity(openShowProfile);
        }
    }

    public static void openZoomableImage(Context context,String imageLink)
    {
        Intent openZoomImage = new Intent(context, ZoomableImageActivity.class);
        openZoomImage.putExtra(Keys.previewImageLink,imageLink);
        context.startActivity(openZoomImage);
    }
    public static long getCurrentTime()
    {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        return calendar.getTimeInMillis() / 1000L;
    }

    public static String getCurrentTimeInString()
    {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        return String.valueOf(calendar.getTimeInMillis() / 1000L);
    }

    public static void print(String className,String messageToPrint)
    {
        Log.e(TAG +" -> found in class: "+className,messageToPrint);
    }

    public static void myToast(Context context,String message)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
