package com.example.hotel_reservation_system;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class HotelGuestDetailsFragment extends Fragment {

    View view;

    //declaring the number in Integer for now
    int numberOfGuests=0;

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

        Button confirmButton = view.findViewById(R.id.confirmButton);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView hotelRecapTextView = view.findViewById(R.id.hotel_recap_text_view);

        String hotelName = getArguments().getString("hotel name");
        String hotelPrice = getArguments().getString("hotel price");
        String hotelAvailability = getArguments().getString("hotel availability");
        Button confirmButton = view.findViewById(R.id.confirmButton);

        hotelRecapTextView.setText("You have selected " +hotelName+ ". The cost will be $ "+hotelPrice+ " and availability is " +hotelAvailability);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToBookingConfirmationScreen("sampleId");
            }
        });

    }
    private void submitGuestDetails(LinearLayout guestDetailsContainer, int numberOfGuests) {
        ArrayList<GuestDetail> guestDetails = new ArrayList<>();

        for (int i = 0; i < numberOfGuests; i++) {
            View guestForm = guestDetailsContainer.getChildAt(i);
            EditText guestNameInput = guestForm.findViewById(R.id.guestNameInput);
            Spinner guestGenderSpinner = guestForm.findViewById(R.id.guestGenderSpinner);

            String name = guestNameInput.getText().toString();
            String gender = guestGenderSpinner.getSelectedItem().toString();

            guestDetails.add(new GuestDetail(name, gender));
        }

        // TODO: Implement API call with guestDetails
    }

    // Method to navigate
    private void navigateToBookingConfirmationScreen(String bookingId) {
        BookingConfirmationFragment fragment = new BookingConfirmationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("bookingId", bookingId);
        fragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, fragment) // Use your actual container ID
                .addToBackStack(null)
                .commit();
    }
}
