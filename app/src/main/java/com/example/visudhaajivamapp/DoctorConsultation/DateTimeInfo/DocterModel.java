package com.example.visudhaajivamapp.DoctorConsultation.DateTimeInfo;

import java.io.Serializable;

public class DocterModel implements Serializable {
    String docName;
    String docDesc;
    String docFees;

    String userName;
    String userEmail;
    String selectedDate;
    String selectedTime;
    String userMobile;
    String userProblem;

    String paymentMethod;

    public DocterModel(){

    }

    public DocterModel(String docName, String docDesc, String docFees, String userName, String userEmail, String selectedDate, String selectedTime, String userMobile, String userProblem, String paymentMethod) {
        this.docName = docName;
        this.docDesc = docDesc;
        this.docFees = docFees;
        this.userName = userName;
        this.userEmail = userEmail;
        this.selectedDate = selectedDate;
        this.selectedTime = selectedTime;
        this.userMobile = userMobile;
        this.userProblem = userProblem;
        this.paymentMethod = paymentMethod;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocDesc() {
        return docDesc;
    }

    public void setDocDesc(String docDesc) {
        this.docDesc = docDesc;
    }

    public String getDocFees() {
        return docFees;
    }

    public void setDocFees(String docFees) {
        this.docFees = docFees;
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

    public String getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }

    public String getSelectedTime() {
        return selectedTime;
    }

    public void setSelectedTime(String selectedTime) {
        this.selectedTime = selectedTime;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserProblem() {
        return userProblem;
    }

    public void setUserProblem(String userProblem) {
        this.userProblem = userProblem;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
