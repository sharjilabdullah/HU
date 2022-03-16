package com.example.visudhaajivamapp.DoctorConsultation.DateTimeInfo;

import java.util.ArrayList;

public class DateModel {
    String date;
    ArrayList<String> timeList;

    public DateModel(){

    }
    public DateModel(String date, ArrayList<String> timeList) {
        this.date = date;
        this.timeList = timeList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<String> getTimeList() {
        return timeList;
    }

    public void setTimeList(ArrayList<String> timeList) {
        this.timeList = timeList;
    }
    }

