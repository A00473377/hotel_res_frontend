package com.example.hotel_reservation_system.models;

public class Hotel {

    private String hotelId;
    private String hotelName;
    private String hotelLocation;
    private Double hotelPricePerNight;

    public Hotel(String hotelId, String hotelName, String hotelLocation, Double hotelPricePerNight) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.hotelLocation = hotelLocation;
        this.hotelPricePerNight = hotelPricePerNight;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelLocation() {
        return hotelLocation;
    }

    public void setHotelLocation(String hotelLocation) {
        this.hotelLocation = hotelLocation;
    }

    public Double getHotelPricePerNight() {
        return hotelPricePerNight;
    }

    public void setHotelPricePerNight(Double hotelPricePerNight) {
        this.hotelPricePerNight = hotelPricePerNight;
    }
}
