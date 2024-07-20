package com.example.travelapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView tripsRecyclerView;
    private TripAdapter tripAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tripsRecyclerView = findViewById(R.id.tripsRecyclerView);
        tripsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        tripAdapter = new TripAdapter(this);
        tripsRecyclerView.setAdapter(tripAdapter);

        findViewById(R.id.profileIcon).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }
}
