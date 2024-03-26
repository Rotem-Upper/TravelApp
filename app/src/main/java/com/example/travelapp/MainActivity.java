package com.example.travelapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.GridLayoutManager;


public class MainActivity extends AppCompatActivity {

    private RecyclerView tripsRecyclerView;
    private TripAdapter tripAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the RecyclerView and set its layout manager and adapter
        tripsRecyclerView = findViewById(R.id.tripsRecyclerView); // Replace with your actual RecyclerView ID
        tripsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // Set the number of columns in the grid
        tripAdapter = new TripAdapter();
        tripsRecyclerView.setAdapter(tripAdapter);
    }
}
