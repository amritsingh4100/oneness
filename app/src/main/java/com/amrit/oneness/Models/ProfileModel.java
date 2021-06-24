package com.amrit.oneness.Models;

import java.util.ArrayList;
import java.util.List;

public class ProfileModel {
    String userName;
    String userEmail;
    String userImageLink;
    String userMobileNumber;
    String userAbout;
    List<InterestModel> interestModelList = new ArrayList<>();

    public ProfileModel()
    {

    }

    public ProfileModel(String userName, String userEmail, String userImageLink, String userMobileNumber, String userAbout, List<InterestModel> interestModelList) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userImageLink = userImageLink;
        this.userMobileNumber = userMobileNumber;
        this.userAbout = userAbout;
        this.interestModelList = interestModelList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMobileNumber() {
        return userMobileNumber;
    }

    public void setUserMobileNumber(String userMobileNumber) {
        this.userMobileNumber = userMobileNumber;
    }

    public String getUserAbout() {
        return userAbout;
    }

    public void setUserAbout(String userAbout) {
        this.userAbout = userAbout;
    }

    public List<InterestModel> getInterestModelList() {
        return interestModelList;
    }

    public void setInterestModelList(List<InterestModel> interestModelList) {
        this.interestModelList = interestModelList;
    }

    public String getUserImageLink() {
        return userImageLink;
    }

    public void setUserImageLink(String userImageLink) {
        this.userImageLink = userImageLink;
    }

}
