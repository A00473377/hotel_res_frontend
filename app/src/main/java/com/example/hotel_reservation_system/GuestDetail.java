package com.example.hotel_reservation_system;

public class GuestDetail {
    private String guestName;
    private String guestGender;

    public GuestDetail(String guestName, String guestGender) {
        this.guestName = guestName;
        this.guestGender = guestGender;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getGuestGender() {
        return guestGender;
    }

    public void setGuestGender(String guestGender) {
        this.guestGender = guestGender;
    }
}

