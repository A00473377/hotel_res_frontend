package com.example.hotel_reservation_system;

//import retrofit.RestAdapter;
//
//public class Api {
//
//    public static ApiInterface getClient() {
//
//        // change your base URL
//        RestAdapter adapter = new RestAdapter.Builder()
////                .setEndpoint("http://Hotelreservation-env.eba-avazyhss.us-east-1.elasticbeanstalk.com/") //Set the Root URL
//                .setEndpoint("http://10.0.2.2:8080/api") //Set the Root URL
//                .build(); //Finally building the adapter
//
//        //Creating object for our interface
//        ApiInterface api = adapter.create(ApiInterface.class);
//        return api; // return the APIInterface object
//    }
//}

////REPLACING ENTIRE CODE FOR retrofit2
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    private static final String BASE_URL = "http://10.0.2.2:8080/api/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
