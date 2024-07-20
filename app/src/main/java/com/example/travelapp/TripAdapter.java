package com.example.travelapp;

import android.content.Context;
import android.content.Intent;
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
    private Context context;

    // Constructor
    public TripAdapter(Context context) {
        this.context = context;
        this.tripsList = new ArrayList<>();
        // Initialize trips list with dummy data
        tripsList.add(new Trip(R.drawable.imgtrip1, "Abu Dhabi", "Dubai"));
        tripsList.add(new Trip(R.drawable.imgtrip2, "Turkey (Value Tour)", "Turkey"));
        tripsList.add(new Trip(R.drawable.imgtrip1, "Abu Dhabi", "Dubai"));
        tripsList.add(new Trip(R.drawable.imgtrip2, "Turkey (Value Tour)", "Turkey"));
        tripsList.add(new Trip(R.drawable.imgtrip1, "Abu Dhabi", "Dubai"));
        tripsList.add(new Trip(R.drawable.imgtrip2, "Turkey (Value Tour)", "Turkey"));
        tripsList.add(new Trip(R.drawable.imgtrip1, "Abu Dhabi", "Dubai"));
        tripsList.add(new Trip(R.drawable.imgtrip2, "Turkey (Value Tour)", "Turkey"));}

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
    class TripViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView imageView;
        private final TextView nameView;
        private final TextView countryView;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.tripImage);
            nameView = itemView.findViewById(R.id.tripName);
            countryView = itemView.findViewById(R.id.tripCountry);
            itemView.setOnClickListener(this);
        }

        void bind(Trip trip) {
            imageView.setImageResource(trip.getImageResource());
            nameView.setText(trip.getTripName());
            countryView.setText(trip.getCountry());
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, TripDetailsActivity.class);
            context.startActivity(intent);
        }
    }
}
