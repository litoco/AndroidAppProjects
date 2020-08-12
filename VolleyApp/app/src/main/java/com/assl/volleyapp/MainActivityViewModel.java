package com.assl.volleyapp;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {

    private String email="", password="", firstName = "", lastName = "", username="", dob="", phoneNumber="";
    private final String BASE_URL = "https://web.api.baf-erp.com/006-V1-eCommerce/04-Consumer/Registration/Registration/";

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public MutableLiveData<String> signInUser(SignInUserDetails signInUserDetails, Context context) {
        VolleyRequest volleyRequest = new VolleyRequest(context);
        String url = BASE_URL+"IsValidUser";
        return volleyRequest.signInUserRequest(signInUserDetails, url);
    }

    public MutableLiveData<String> createUser(RegistrationBody registrationBody, Context context){
        VolleyRequest volleyRequest = new VolleyRequest(context);
        String url = BASE_URL+"SignUp?ProfileCode";
        return volleyRequest.createNewRegistrationRequest(url, registrationBody);
    }
}
