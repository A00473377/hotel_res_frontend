package com.example.hotel_reservation_system;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotel_reservation_system.models.FilterRequest;
import com.example.hotel_reservation_system.models.Hotel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import retrofit.Callback;
//import retrofit.RetrofitError;
//import retrofit.client.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelsListFragment extends Fragment implements ItemClickListener {

    View view;
    TextView headingTextView;
    ProgressBar progressBar;
    List<HotelListData> userListResponseData;
    String numberOfGuests;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.hotel_list_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //heading text view
        headingTextView = view.findViewById(R.id.heading_text_view);
        progressBar = view.findViewById(R.id.progress_bar);

        String checkInDate = getArguments().getString("check in date");
        String checkOutDate = getArguments().getString("check out date");
        numberOfGuests = getArguments().getString("number of guests");

        // Log received data
        Log.d("HotelsListFragment", "Received Data - CheckIn: " + checkInDate + ", CheckOut: " + checkOutDate + ", Guests: " + numberOfGuests);
        //Set up the header
        headingTextView.setText("Welcome user, displaying hotel for " + numberOfGuests + " guests staying from " + checkInDate +
                " to " + checkOutDate);


//comment these lines when using getHotelslistsData method
        // Set up the RecyclerView
        //ArrayList<HotelListData> hotelListData = initHotelListData();
        //we need to delete below when we will implement API.
        userListResponseData = initHotelListData();
        RecyclerView recyclerView = view.findViewById(R.id.hotel_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        HotelListAdapter hotelListAdapter = new HotelListAdapter(getActivity(), userListResponseData);
        recyclerView.setAdapter(hotelListAdapter);
        //Bind the click listener
        hotelListAdapter.setClickListener(this);

        //commment till here
        getHotelsListsData();
    }

    public ArrayList<HotelListData> initHotelListData() {
        ArrayList<HotelListData> list = new ArrayList<>();

        list.add(new HotelListData("Halifax Regional Hotel", "2000$", "true"));
        list.add(new HotelListData("Hotel Pearl", "500$", "false"));
        list.add(new HotelListData("Hotel Amano", "800$", "true"));
        list.add(new HotelListData("San Jones", "250$", "false"));
        list.add(new HotelListData("Halifax Regional Hotel", "2000$", "true"));
        list.add(new HotelListData("Hotel Pearl", "500$", "false"));
        list.add(new HotelListData("Hotel Amano", "800$", "true"));
        list.add(new HotelListData("San Jones", "250$", "false"));
        list.add(new HotelListData("Hotel XYZ", "1200$", "true"));
        list.add(new HotelListData("Luxury Hotel", "1500$", "false"));
        list.add(new HotelListData("Beach Resort", "1800$", "true"));
        list.add(new HotelListData("City Hotel", "1000$", "false"));
        list.add(new HotelListData("Mountain Lodge", "800$", "true"));
        list.add(new HotelListData("Hotel 123", "2000$", "false"));
        list.add(new HotelListData("Seaside Inn", "1600$", "true"));
        list.add(new HotelListData("Downtown Hotel", "1400$", "false"));
        list.add(new HotelListData("Riverfront Resort", "1700$", "true"));
        list.add(new HotelListData("Country Retreat", "900$", "false"));

        Log.d("HotelsListFragment", "Hotel data initialized. Size: " + list.size());
        return list;
    }

    private void getHotelsListsData() {
        progressBar.setVisibility(View.VISIBLE); // Show progress bar when the request starts
        Log.d("Inside methos","Inside method");
        // Create an instance of the API service
        ApiInterface apiService = Api.getClient().create(ApiInterface.class);

        // Prepare your request parameters
        String startDate = "2024-04-01T12:00:00Z";
        String endDate = "2024-04-10T12:00:00Z";
        FilterRequest request = new FilterRequest(startDate, endDate);

        // Make the API call
        Call<List<Hotel>> call = apiService.filterHotels(request);
        call.enqueue(new Callback<List<Hotel>>() {
            @Override
            public void onResponse(Call<List<Hotel>> call, Response<List<Hotel>> response) {
                progressBar.setVisibility(View.GONE); // Hide progress bar on response
                if (response.isSuccessful()) {
                    List<Hotel> hotels = response.body(); // This is safe, response is typed
                    System.out.println(response);
                    Log.d(String.valueOf(response),"API responSe");
                    // Process the list of hotels here
                } else {
                    Log.d(String.valueOf(response),"API responSe");
                    // Handle request errors depending on error response code
                }
            }

            @Override
            public void onFailure(Call<List<Hotel>> call, Throwable t) {
                progressBar.setVisibility(View.GONE); // Hide progress bar on failure
                Log.d(String.valueOf(t),"API responSe");
                // Handle failure, such as a network error
            }
        });
    }

//
//    private void getHotelsListsData() {
//        progressBar.setVisibility(View.VISIBLE);
////        Api.getClient().getHotelsLists(new Callback<List<HotelListData>>() {
//
//        //Trying API
//        ApiInterface apiService = Api.getClient().create(ApiInterface.class);
//        String startDate = "2024-04-01T12:00:00Z";
//        String endDate ="2024-04-10T12:00:00Z";
//        FilterRequest request = new FilterRequest(startDate, endDate);
//        Call<List<Hotel>> call = apiService.filterHotels((retrofit.Callback<List<Hotel>>) request);
//        call.enqueue(new Callback<List<Hotel>>() {
//            @Override
//            public void onResponse(Call<List<Hotel>> call, Response response) {
//                if (response.isSuccessful()) {
//                    List<Hotel> hotels = response.body();
//                    // Process the list of hotels
//                } else {
//                    // Handle request errors depending on error response code
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Hotel>> call, Throwable t) {
//                // Handle failure, such as a network error
//            }
////        });
////        Api.getClient().filterHotels(new Callback<List<Hotel>>() {
////
////            @Override
////            public void success(List<Hotel> hotels, Response response) {
////                System.out.println(response);
////            }
////
////            @Override
////            public void failure(RetrofitError error) {
////
////            }
////        });
//    }
//
//    private void setupRecyclerView() {
//        progressBar.setVisibility(View.GONE);
//        RecyclerView recyclerView = view.findViewById(R.id.hotel_list_recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        HotelListAdapter hotelListAdapter = new HotelListAdapter(getActivity(), userListResponseData);
//        recyclerView.setAdapter(hotelListAdapter);
//
//        //Bind the click listener
//        hotelListAdapter.setClickListener(this);
//    }
//
//
    @Override
    public void onClick(View view, int position) {
        Log.d("HotelsListFragment", "onClick received for position: " + position);
        HotelListData hotelListData = userListResponseData.get(position);

        String hotelName = hotelListData.getHotel_name();
        String price = hotelListData.getPrice();
        String availability = hotelListData.getAvailability();

        Log.d("HotelsListFragment", "Preparing to send data to HotelGuestDetailsFragment");


        Bundle bundle = new Bundle();
        bundle.putString("hotel name", hotelName);
        bundle.putString("hotel price", price);
        bundle.putString("hotel availability", availability);
        bundle.putString("number of guests",numberOfGuests);

        HotelGuestDetailsFragment hotelGuestDetailsFragment = new HotelGuestDetailsFragment();
        hotelGuestDetailsFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.remove(HotelsListFragment.this);
        fragmentTransaction.replace(R.id.frame_layout, hotelGuestDetailsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();

        Log.d("HotelsListFragment", "Transaction committed to HotelGuestDetailsFragment");
    }


}
