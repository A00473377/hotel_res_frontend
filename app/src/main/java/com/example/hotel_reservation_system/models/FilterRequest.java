package com.example.hotel_reservation_system.models;

import java.util.Date;

public class FilterRequest {
    private String startDate;
    private String endDate;

    //constructor fro the class


    public FilterRequest(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //getter and setter

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
