package com.assl.volleyapp;

public class RegistrationBody {

    
    private String firstName;

    private String lastName;

    private String username;

    private String emailID;

    private int isEmailVerified;

    private String mobileNumber;

    private int isMobileVerified;

    private String dateOfBirth;

    private String password;

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

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public int getIsEmailVerified() {
        return isEmailVerified;
    }

    public void setIsEmailVerified(int isEmailVerified) {
        this.isEmailVerified = isEmailVerified;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public int getIsMobileVerified() {
        return isMobileVerified;
    }

    public void setIsMobileVerified(int isMobileVerified) {
        this.isMobileVerified = isMobileVerified;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
