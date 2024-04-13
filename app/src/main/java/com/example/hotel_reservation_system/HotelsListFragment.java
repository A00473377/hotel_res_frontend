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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.text.ParseException;


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
    String checkInDate,checkOutDate;
    String startDate, endDate;

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

        checkInDate = getArguments().getString("check in date");
        checkOutDate = getArguments().getString("check out date");
        numberOfGuests = getArguments().getString("number of guests");

        // Log received data
        Log.d("HotelsListFragment", "Received Data - CheckIn: " + checkInDate + ", CheckOut: " + checkOutDate + ", Guests: " + numberOfGuests);
        //Set up the header
        headingTextView.setText("Welcome user, displaying hotel for " + numberOfGuests + " guests staying from " + checkInDate +
                " to " + checkOutDate);

        getHotelsListsData();
    }


    private void getHotelsListsData() {
        progressBar.setVisibility(View.VISIBLE); // Show progress bar when the request starts
        Log.d("Inside methos","Inside method");
        // Create an instance of the API service
        ApiInterface apiService = Api.getClient().create(ApiInterface.class);
//        // Prepare your request parameters
//        String startDate = "2024-04-01T12:00:00Z";
//        String endDate = "2024-04-10T12:00:00Z";
        startDate = convertToIso8601(checkInDate);
        endDate = convertToIso8601(checkOutDate);
        FilterRequest request = new FilterRequest(startDate, endDate);

        // Make the API call
        Call<List<Hotel>> call = apiService.filterHotels(request);
        call.enqueue(new Callback<List<Hotel>>() {
            @Override
            public void onResponse(Call<List<Hotel>> call, Response<List<Hotel>> response) {
                progressBar.setVisibility(View.GONE); // Hide progress bar on response
                if (response.isSuccessful() && response.body() != null) {
                    List<Hotel> hotels = response.body(); // This is safe, response is typed
                    userListResponseData = new ArrayList<>(); // Create a new list or clear the existing one
                    for (Hotel hotel : hotels) {
                        // Convert and add to the list
                        userListResponseData.add(convertHotelToHotelListData(hotel));
                    }
                    // Set up the RecyclerView with the new data
                    setupRecyclerView();
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


    //method to setup the hote list data.
    private void setupRecyclerView() {
        progressBar.setVisibility(View.GONE);
        RecyclerView recyclerView = view.findViewById(R.id.hotel_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        HotelListAdapter hotelListAdapter = new HotelListAdapter(getActivity(), userListResponseData);
        recyclerView.setAdapter(hotelListAdapter);

        //Bind the click listener
        hotelListAdapter.setClickListener(this);
    }

    @Override
    public void onClick(View view, int position) {
        Log.d("HotelsListFragment", "onClick received for position: " + position);
        HotelListData hotelListData = userListResponseData.get(position);

        String hotelName = hotelListData.getHotel_name();
        String price = hotelListData.getPrice();
        String availability = hotelListData.getAvailability();
        String hotelId  =hotelListData.getHotel_id();

        Log.d("HotelsListFragment", "Preparing to send data to HotelGuestDetailsFragment");


        Bundle bundle = new Bundle();
        bundle.putString("hotel name", hotelName);
        bundle.putString("hotel price", price);
        bundle.putString("hotel availability", availability);
        bundle.putString("number of guests",numberOfGuests);
        bundle.putString("hotel_id",hotelId);
        bundle.putString("start date",startDate);
        bundle.putString("end date",endDate);

        HotelGuestDetailsFragment hotelGuestDetailsFragment = new HotelGuestDetailsFragment();
        hotelGuestDetailsFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.remove(HotelsListFragment.this);
        fragmentTransaction.replace(R.id.frame_layout, hotelGuestDetailsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();

        Log.d("HotelsListFragment", "Transaction committed to HotelGuestDetailsFragment");
    }

        //Method to convert the API response to HotelListData Structure
        private HotelListData convertHotelToHotelListData(Hotel hotel) {
        //taking assumption that the API will only return the list of available hotels.
            return new HotelListData(
                    hotel.getHotelName(),
                    hotel.getHotelPricePerNight().toString(),
                    "true",
                    hotel.getHotelId()
            );
        }

        //format the date to be passed in the API properly.
    private String convertToIso8601(String dateInput) {
        // Parse the input date in "dd-MM-yyyy" format
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        // Define the output format for ISO 8601
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        outputFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // Set UTC timezone for ISO 8601 format

        try {
            // Parse the input date string into a Date object
            Date date = inputFormat.parse(dateInput);

            // Format the Date object into an ISO 8601 formatted string
            return outputFormat.format(date);
        } catch (ParseException e) {
            // Handle the possibility that the input doesn't match the expected format
            e.printStackTrace();
            return null;
        }
    }
}
