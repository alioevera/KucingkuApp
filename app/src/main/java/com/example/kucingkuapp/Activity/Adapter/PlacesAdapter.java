package com.example.kucingkuapp.Activity.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kucingkuapp.Activity.Database.Place;
import com.example.kucingkuapp.R;

import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder> {

    private List<Place> placesList;

    public PlacesAdapter(List<Place> placesList) {
        this.placesList = placesList;
    }

    @NonNull
    @Override
    public PlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_item, parent, false);
        return new PlacesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlacesViewHolder holder, int position) {
        Place place = placesList.get(position);

        holder.placeName.setText(place.getPlName());
        holder.placeAddress.setText(place.getPlAddress());
        holder.placeOwner.setText("Owner: " + place.getPlOwner());
        holder.placeRating.setText("Rating: " + place.getRating());

        // Load image using Glide
        Glide.with(holder.itemView.getContext())
                .load(place.getPlImage())
                .into(holder.placeImage);
    }

    @Override
    public int getItemCount() {
        return placesList.size();
    }

    public static class PlacesViewHolder extends RecyclerView.ViewHolder {

        ImageView placeImage;
        TextView placeName;
        TextView placeAddress;
        TextView placeOwner;
        TextView placeRating;

        public PlacesViewHolder(@NonNull View itemView) {
            super(itemView);
            placeImage = itemView.findViewById(R.id.placeImage);
            placeName = itemView.findViewById(R.id.placeName);
            placeAddress = itemView.findViewById(R.id.placeAddress);
            placeOwner = itemView.findViewById(R.id.placeOwner);
            placeRating = itemView.findViewById(R.id.placeRating);
        }
    }
}
