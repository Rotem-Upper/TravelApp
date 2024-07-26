package com.example.travelapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private RecyclerView tripsRecyclerView;
    private TripAdapter tripAdapter;
    private List<Trip> tripsList;
    private FirebaseFirestore db;
    private ImageView profileIcon;
    private String username;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        tripsRecyclerView = findViewById(R.id.tripsRecyclerView);
        profileIcon = findViewById(R.id.profileIcon);
        executorService = Executors.newSingleThreadExecutor();

        tripsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        tripsList = new ArrayList<>();
        tripAdapter = new TripAdapter(this, tripsList);
        tripsRecyclerView.setAdapter(tripAdapter);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        if (username != null) {
            loadProfileImage(username);
        }

        profileIcon.setOnClickListener(view -> {
            Intent profileIntent = new Intent(MainActivity.this, ProfileActivity.class);
            profileIntent.putExtra("username", username);
            startActivity(profileIntent);
        });

        fetchTripsFromFirestore();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload the profile image each time the activity is resumed
        if (username != null) {
            loadProfileImage(username);
        }
    }

    private void loadProfileImage(String username) {
        db.collection("users").document(username).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String profileImageUrl = document.getString("profileImage");
                    if (profileImageUrl != null && !profileImageUrl.isEmpty()) {
                        loadImageFromUrl(profileImageUrl);
                    } else {
                        profileIcon.setImageResource(R.drawable.user); // Default image
                    }
                }
            } else {
                Log.w("MainActivity", "Error getting documents.", task.getException());
            }
        });
    }

    private void loadImageFromUrl(String url) {
        executorService.execute(() -> {
            try {
                URL imageUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                runOnUiThread(() -> profileIcon.setImageBitmap(bitmap));
            } catch (Exception e) {
                Log.e("MainActivity", "Failed to load image", e);
            }
        });
    }

    private void fetchTripsFromFirestore() {
        db.collection("trips")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            int imageResource = getResources().getIdentifier(
                                    document.getString("imageResource"), "drawable", getPackageName());

                            Trip trip = new Trip(
                                    imageResource != 0 ? imageResource : R.drawable.applogo,
                                    document.getString("tripName"),
                                    document.getString("country"),
                                    document.getString("location"),
                                    document.getString("date"),
                                    document.getString("price"),
                                    document.getString("description")
                            );
                            tripsList.add(trip);
                        }
                        tripAdapter.notifyDataSetChanged();
                    } else {
                        Log.w("MainActivity", "Error getting documents.", task.getException());
                    }
                });
    }
}
