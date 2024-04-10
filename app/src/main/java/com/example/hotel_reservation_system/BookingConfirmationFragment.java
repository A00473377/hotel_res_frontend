package com.example.hotel_reservation_system;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

        Button restartButton = view.findViewById(R.id.restartButton);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartApp();
            }
        });

        return view;
    }

    //method to restart the app
    private void restartApp() {
        // Context context = getActivity();
        // Ensure context is not null if using in a fragment
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish(); // Close the current activity
    }
}

