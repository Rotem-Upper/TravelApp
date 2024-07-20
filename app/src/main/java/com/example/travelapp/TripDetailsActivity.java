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

        ivBackIcon.setOnClickListener(view -> onBackPressed());
        btnBookNow.setOnClickListener(view -> {
            // Implement booking logic here
        });
    }
}
