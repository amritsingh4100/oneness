package com.amrit.oneness.Customs;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.amrit.oneness.Models.InterestModel;
import com.amrit.oneness.R;
import com.amrit.oneness.RealTimeDatabase.ProfileRealtimeDatabase;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

public class CustomFlexBoxLayout {

    String TAG= getClass().getSimpleName();
    List<InterestModel> interestModelArrayList;
    List<InterestModel> selectedInterestArrayList = new ArrayList<>();
    Context context;
    boolean isClickable= false;

    public CustomFlexBoxLayout setInterestArrayListToShow(List<InterestModel> interestModelArrayList)
    {
        this.interestModelArrayList = interestModelArrayList;
        return this;
    }

    public CustomFlexBoxLayout(Context context)
    {
        this.context=context;
    }

    public CustomFlexBoxLayout isClickAble(boolean isClickable)
    {
        this.isClickable=isClickable;
        return this;
    }

    public void addProfileViews(FlexboxLayout flexboxLayout)
    {
        flexboxLayout.removeAllViews();
        int size = interestModelArrayList.size();
        //adding each view of the interest to the flex layout
        for (int i = 0; i < size ; i++) {

            InterestModel interestModel = interestModelArrayList.get(i);

            TextView textView = getTextViewToAdd(interestModel.getInterestName(),interestModel.isInterestSelected());
            int finalI = i;
            if(isClickable)
            {
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        List<InterestModel> realTimeInterestModelList = interestModelArrayList;

                        InterestModel interestModel = interestModelArrayList.get(finalI);

                        if (!interestModel.isInterestSelected()) { // make it selected here
                            selectedInterestArrayList.add(interestModel);
                            interestModel.setInterestSelected(true);
                            realTimeInterestModelList.get(finalI).setInterestSelected(true);
                            Utils.print(TAG,"added:" + interestModel.getInterestName());
                            Utils.print(TAG,"on click name : "+interestModel.getInterestName());
                            Utils.print(TAG,"on click isSelected : "+interestModel.isInterestSelected());
                            textView.setBackgroundResource(R.drawable.round_corners_background_blue);
                            textView.setPadding(23, 9, 23, 9);
                        } else {
                            selectedInterestArrayList.remove(interestModel);
                            interestModel.setInterestSelected(false);
                            realTimeInterestModelList.get(finalI).setInterestSelected(false);
                            Utils.print(TAG,"removed:" + interestModel.getInterestName());
                            Utils.print(TAG,"on click name : "+interestModel.getInterestName());
                            Utils.print(TAG,"on click isSelected : "+interestModel.isInterestSelected());
                            textView.setBackgroundResource(R.drawable.round_corners_background_grey);
                            textView.setPadding(23, 9, 23, 9);
                        }

                        ProfileRealtimeDatabase profileRealtimeDatabase = new ProfileRealtimeDatabase();
                        profileRealtimeDatabase.updateUserInterests(realTimeInterestModelList);

                    }
                });
            }

            flexboxLayout.addView(textView);
        }
    }

    public void addSelectableViews(FlexboxLayout flexboxLayout)
    {
        flexboxLayout .removeAllViews();
        int size = interestModelArrayList.size();
        //adding each view of the interest to the flex layout
        for (int i = 0; i < size ; i++) {

            InterestModel interestModel = interestModelArrayList.get(i);

            TextView textView = getTextViewToAdd(interestModel.getInterestName(),interestModel.isInterestSelected());
            int finalI = i;
            if(isClickable)
            {
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        List<InterestModel> realTimeInterestModelList = interestModelArrayList;

                        InterestModel interestModel = interestModelArrayList.get(finalI);

                        if (!interestModel.isInterestSelected()) { // make it selected here
                            selectedInterestArrayList.add(interestModel);
                            interestModel.setInterestSelected(true);
                            Utils.print(TAG,"added:" + interestModel.getInterestName());
                            Utils.print(TAG,"on click name : "+interestModel.getInterestName());
                            Utils.print(TAG,"on click isSelected : "+interestModel.isInterestSelected());
                            textView.setBackgroundResource(R.drawable.round_corners_background_blue);
                            textView.setPadding(23, 9, 23, 9);
                        } else {
                            selectedInterestArrayList.remove(interestModel);
                            interestModel.setInterestSelected(false);
                            Utils.print(TAG,"removed:" + interestModel.getInterestName());
                            Utils.print(TAG,"on click name : "+interestModel.getInterestName());
                            Utils.print(TAG,"on click isSelected : "+interestModel.isInterestSelected());
                            textView.setBackgroundResource(R.drawable.round_corners_background_grey);
                            textView.setPadding(23, 9, 23, 9);
                        }

                    }
                });
            }

            flexboxLayout.addView(textView);
        }
    }

    public List<InterestModel> getSelectedInterestArrayList() {
        return selectedInterestArrayList;
    }

    public TextView getTextViewToAdd(String textName,boolean isSelected){

        TextView textView = new TextView(context);
        if(isSelected) textView.setBackgroundResource(R.drawable.round_corners_background_blue);
        else textView.setBackgroundResource(R.drawable.round_corners_background_grey);
        textView.setPadding(23, 9, 23, 9);
        FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(20, 20, 20, 20);
        textView.setLayoutParams(layoutParams);
        textView.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        textView.setTextSize(14);
        textView.setTextColor(Color.WHITE);
        textView.setText(textName);

        return textView;
    }

    public void addViews(FlexboxLayout flexboxLayout) {

        flexboxLayout.removeAllViews();
        int size = interestModelArrayList.size();
        //adding each view of the interest to the flex layout
        for (int i = 0; i < size ; i++) {

            InterestModel interestModel = interestModelArrayList.get(i);

            TextView textView = getTextViewToAdd(interestModel.getInterestName(),interestModel.isInterestSelected());
            int finalI = i;
            if(isClickable)
            {
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        InterestModel interestModel = interestModelArrayList.get(finalI);

                        if (!interestModel.isInterestSelected()) { // make it selected here
                            selectedInterestArrayList.add(interestModel);
                            interestModel.setInterestSelected(true);Utils.print(TAG,"added:" + interestModel.getInterestName());
                            Utils.print(TAG,"on click name : "+interestModel.getInterestName());
                            Utils.print(TAG,"on click isSelected : "+interestModel.isInterestSelected());
                            textView.setBackgroundResource(R.drawable.round_corners_background_blue);
                            textView.setPadding(23, 9, 23, 9);
                        } else {
                            selectedInterestArrayList.remove(interestModel);
                            interestModel.setInterestSelected(false);
                            Utils.print(TAG,"removed:" + interestModel.getInterestName());
                            Utils.print(TAG,"on click name : "+interestModel.getInterestName());
                            Utils.print(TAG,"on click isSelected : "+interestModel.isInterestSelected());
                            textView.setBackgroundResource(R.drawable.round_corners_background_grey);
                            textView.setPadding(23, 9, 23, 9);
                        }

                    }
                });
            }

            flexboxLayout.addView(textView);
        }
    }


}
