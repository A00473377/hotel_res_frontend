package com.example.hotel_reservation_system.models;
import com.example.hotel_reservation_system.GuestDetail;

import java.util.List;

public class CreateReservationDTO {
    private String resHotelId;
    private List<GuestDetail> resGuestList;
    private String resCheckinDate;
    private String resCheckoutDate;
    private Integer resGuestCount;

    public CreateReservationDTO(String resHotelId, List<GuestDetail> resGuestList, String resCheckinDate, String resCheckoutDate, Integer resGuestCount) {
        this.resHotelId = resHotelId;
        this.resGuestList = resGuestList;
        this.resCheckinDate = resCheckinDate;
        this.resCheckoutDate = resCheckoutDate;
        this.resGuestCount = resGuestCount;
    }

    public String getResHotelId() {
        return resHotelId;
    }

    public void setResHotelId(String resHotelId) {
        this.resHotelId = resHotelId;
    }

    public List<GuestDetail> getResGuestList() {
        return resGuestList;
    }

    public void setResGuestList(List<GuestDetail> resGuestList) {
        this.resGuestList = resGuestList;
    }

    public String getResCheckinDate() {
        return resCheckinDate;
    }

    public void setResCheckinDate(String resCheckinDate) {
        this.resCheckinDate = resCheckinDate;
    }

    public String getResCheckoutDate() {
        return resCheckoutDate;
    }

    public void setResCheckoutDate(String resCheckoutDate) {
        this.resCheckoutDate = resCheckoutDate;
    }

    public Integer getResGuestCount() {
        return resGuestCount;
    }

    public void setResGuestCount(Integer resGuestCount) {
        this.resGuestCount = resGuestCount;
    }
}