package com.example.hotel_reservation_system;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hotel_reservation_system.models.CreateReservationDTO;
import com.example.hotel_reservation_system.models.Hotel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelGuestDetailsFragment extends Fragment {

    View view;

    //declaring the number in Integer for now
    int numberOfGuests=0;
    String hotel_id,startDate,endDate;
    ArrayList<GuestDetail> finalGuests;
    ProgressBar progressBar;
    String bookingResponse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.hotel_guest_details_fragment, container, false);
        String getNumberOfGuests = getArguments().getString("number of guests");

        if(getNumberOfGuests!=null){
            numberOfGuests=Integer.parseInt(getNumberOfGuests);
        }

        LinearLayout guestDetailsContainer = view.findViewById(R.id.guestDetailsContainer);
        for (int i = 0; i < numberOfGuests; i++) {
            View guestForm = inflater.inflate(R.layout.guest_detail_form, guestDetailsContainer, false);
            guestDetailsContainer.addView(guestForm);
        }
//        submitGuestDetails(guestDetailsContainer,numberOfGuests);
        Button confirmButton = view.findViewById(R.id.confirmButton);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView hotelRecapTextView = view.findViewById(R.id.hotel_recap_text_view);
        progressBar = view.findViewById(R.id.progress_bar);

        String hotelName = getArguments().getString("hotel name");
        String hotelPrice = getArguments().getString("hotel price");
        String hotelAvailability = getArguments().getString("hotel availability");
        hotel_id = (String) getArguments().get("hotel_id");
        startDate = getArguments().getString("start date");
        endDate = getArguments().getString("end date");
        Button confirmButton = view.findViewById(R.id.confirmButton);

        hotelRecapTextView.setText("You have selected " +hotelName+ ". The cost will be $ "+hotelPrice+ " and availability is " +hotelAvailability);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                navigateToBookingConfirmationScreen();
                submitGuestDetails(); // Call this method when the button is clicked
            }
        });

    }
    private void submitGuestDetails() {
        ArrayList<GuestDetail> guestDetails = new ArrayList<>();
        LinearLayout guestDetailsContainer = view.findViewById(R.id.guestDetailsContainer);
        for (int i = 0; i < numberOfGuests; i++) {
            View guestForm = guestDetailsContainer.getChildAt(i);
            EditText guestNameInput = guestForm.findViewById(R.id.guestNameInput);
            Spinner guestGenderSpinner = guestForm.findViewById(R.id.guestGenderSpinner);

            String name = guestNameInput.getText().toString();
            String gender = guestGenderSpinner.getSelectedItem().toString();

            guestDetails.add(new GuestDetail(name, gender));
        }
        finalGuests=guestDetails;
        // Call the API method only after gathering all guest details
        navigateToBookingConfirmationScreen(finalGuests);

        // TODO: Implement API call with guestDetails
    }

    // Method to navigate
    private void navigateToBookingConfirmationScreen(ArrayList<GuestDetail> guestDetails) {
        //progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = Api.getClient().create(ApiInterface.class);
        CreateReservationDTO request = new CreateReservationDTO(hotel_id,finalGuests,startDate,endDate,numberOfGuests);

        Call<ResponseBody> call = apiService.makeReservation(request);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //progressBar.setVisibility(View.GONE); // Hide progress bar on response
                if (response.isSuccessful() && response.body() != null) {
                    bookingResponse = String.valueOf(response.body());

                    Log.d(String.valueOf(response),"API responSe");

                    //moving code to on response only.
                    BookingConfirmationFragment fragment = new BookingConfirmationFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("bookingId", bookingResponse);
                    fragment.setArguments(bundle);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, fragment) // Use your actual container ID
                            .addToBackStack(null)
                            .commit();

                } else {
                    Log.d(String.valueOf(response),"API responSe");
                    // Handle request errors depending on error response code
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE); // Hide progress bar on failure
                Log.d(String.valueOf(t),"API responSe");
                // Handle failure, such as a network error
            }
        });

    }
}
