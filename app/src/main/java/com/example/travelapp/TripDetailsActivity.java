package com.example.travelapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TripDetailsActivity extends AppCompatActivity {
    private ImageView ivTripImage, ivBackIcon;
    private TextView tvTripTitle, tvTripLocation, tvTripDate, tvTripPrice, tvTripDescription;
    private Button btnBookNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        ivTripImage = findViewById(R.id.ivTripImage);
        ivBackIcon = findViewById(R.id.ivBackIcon);
        tvTripTitle = findViewById(R.id.tvTripTitle);
        tvTripLocation = findViewById(R.id.tvTripLocation);
        tvTripDate = findViewById(R.id.tvTripDate);
        tvTripPrice = findViewById(R.id.tvTripPrice);
        tvTripDescription = findViewById(R.id.tvTripDescription);
        btnBookNow = findViewById(R.id.btnBookNow);

        // Get data from intent
        int tripImage = getIntent().getIntExtra("tripImage", R.drawable.applogo);
        String tripName = getIntent().getStringExtra("tripName");
        String tripLocation = getIntent().getStringExtra("tripLocation");
        String tripDate = getIntent().getStringExtra("tripDate");
        String tripPrice = getIntent().getStringExtra("tripPrice");
        String tripDescription = getIntent().getStringExtra("tripDescription");

        // Set data to views
        ivTripImage.setImageResource(tripImage);
        tvTripTitle.setText(tripName);
        tvTripLocation.setText(tripLocation);
        tvTripDate.setText(tripDate);
        tvTripPrice.setText(tripPrice);
        tvTripDescription.setText(tripDescription);

        ivBackIcon.setOnClickListener(view -> onBackPressed());
        btnBookNow.setOnClickListener(view -> {
            // Implement booking logic here
        });
    }
}
