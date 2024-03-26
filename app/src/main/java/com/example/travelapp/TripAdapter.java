package com.example.travelapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    private List<Trip> tripsList;

    // Constructor
    public TripAdapter() {
        this.tripsList = new ArrayList<>();
        tripsList.add(new Trip(R.drawable.imgtrip1, "Abu Dhabi", "Dubai"));
        tripsList.add(new Trip(R.drawable.imgtrip2, "Turkey (Value Tour)", "Turkey"));
        tripsList.add(new Trip(R.drawable.imgtrip1, "Abu Dhabi", "Dubai"));
        tripsList.add(new Trip(R.drawable.imgtrip2, "Turkey (Value Tour)", "Turkey"));
        tripsList.add(new Trip(R.drawable.imgtrip1, "Abu Dhabi", "Dubai"));
        tripsList.add(new Trip(R.drawable.imgtrip2, "Turkey (Value Tour)", "Turkey"));
        tripsList.add(new Trip(R.drawable.imgtrip1, "Abu Dhabi", "Dubai"));
        tripsList.add(new Trip(R.drawable.imgtrip2, "Turkey (Value Tour)", "Turkey"));
        tripsList.add(new Trip(R.drawable.imgtrip1, "Abu Dhabi", "Dubai"));
        tripsList.add(new Trip(R.drawable.imgtrip2, "Turkey (Value Tour)", "Turkey"));
        tripsList.add(new Trip(R.drawable.imgtrip1, "Abu Dhabi", "Dubai"));
        tripsList.add(new Trip(R.drawable.imgtrip2, "Turkey (Value Tour)", "Turkey"));
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_item, parent, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        Trip currentTrip = tripsList.get(position);
        holder.bind(currentTrip);
    }

    @Override
    public int getItemCount() {
        return tripsList.size();
    }

    // ViewHolder inner-class
    static class TripViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView nameView;
        private final TextView countryView;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.tripImage);
            nameView = itemView.findViewById(R.id.tripName);
            countryView = itemView.findViewById(R.id.tripCountry);
        }

        void bind(Trip trip) {
            imageView.setImageResource(trip.getImageResource());
            nameView.setText(trip.getTripName());
            countryView.setText(trip.getCountry());
        }
    }
}
