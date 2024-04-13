package com.example.hotel_reservation_system;

import com.example.hotel_reservation_system.models.CreateReservationDTO;
import com.example.hotel_reservation_system.models.FilterRequest;
import com.example.hotel_reservation_system.models.Hotel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.POST;
//import retrofit.Callback;
//import retrofit.http.Body;
//import retrofit.http.POST;

public interface ApiInterface {

//    // API's endpoints
//    @GET("/hotelsList")
//    public void getHotelsLists(Callback<List<HotelListData>> callback);


    //Adding new methods for API.
    @POST("hotels/filter")
    Call<List<Hotel>> filterHotels(@Body FilterRequest filterRequest);

//    ApiInterface create(Class<ApiInterface> apiInterfaceClass);
//
    @POST("reservation")
    Call<ResponseBody> makeReservation(@Body CreateReservationDTO reservationRequest);
//
//    @GET("getHotelById")
//    Call<Hotel> getHotelById(@Query("id") String id);
}
