package com.amrit.oneness.Models;

import java.util.ArrayList;

public class InterestModel {
    String interestName;
    boolean isInterestSelected;

    public InterestModel()
    {

    }
    public InterestModel(String interestName,boolean isInterestSelected)
    {
        this.interestName=interestName;
        this.isInterestSelected = isInterestSelected;
    }

    public String getInterestName() {
        return interestName;
    }

    public void setInterestName(String interestName) {
        this.interestName = interestName;
    }

    public boolean isInterestSelected() {
        return isInterestSelected;
    }

    public void setInterestSelected(boolean interestSelected) {
        isInterestSelected = interestSelected;
    }
}
