package com.example.hotel_reservation_system;

public class HotelListData {

    String hotel_name;
    String price;
    String availability;
    String hotel_id;

    public HotelListData(String hotel_name, String price, String availability, String hotel_id) {
        this.hotel_name = hotel_name;
        this.price = price;
        this.availability = availability;
        this.hotel_id =hotel_id;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }
}
