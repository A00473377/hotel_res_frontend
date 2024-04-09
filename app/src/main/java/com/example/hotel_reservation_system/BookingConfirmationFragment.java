package com.example.hotel_reservation_system;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BookingConfirmationFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.booking_confirmation_fragment, container, false);

        TextView confirmationMessage = view.findViewById(R.id.confirmationMessage);
        TextView bookingIdText = view.findViewById(R.id.bookingId);

//        // Retrieve and display booking ID
        String bookingId = getArguments().getString("bookingId", "N/A");
        bookingIdText.setText("Booking ID: " + bookingId);

        return view;
    }
}

