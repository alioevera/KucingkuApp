package com.example.kucingkuapp.Activity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kucingkuapp.Activity.Database.Place;
import com.example.kucingkuapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlaceViewHolder> {

    private List<Place> placesList;
    private Context context;

    public PlacesAdapter(List<Place> placesList) {
        this.placesList = placesList;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.place_item, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        Place place = placesList.get(position);
        holder.placeName.setText(place.getPlName());
        holder.placeAddress.setText(place.getPlAddress());
        holder.placeOwner.setText("Owner: " + place.getPlOwner());
        holder.placeRating.setText("Rating: " + place.getRating());

        // Load image using Picasso
        if (place.getPlImage() != null && !place.getPlImage().isEmpty()) {
            Picasso.get().load(place.getPlImage()).into(holder.placeImage);
        } else {
            // Set a default image if image URL is empty
            holder.placeImage.setImageResource(R.drawable.bg_banner);
        }
    }

    @Override
    public int getItemCount() {
        return placesList.size();
    }

//    public static class Place {
//        public String name;
//        public String address;
//        public String owner;
//        public String imageUrl;
//        public Long rating; // Changed to String
//
//
//        // No-argument constructor required for Firebase
//        public Place() {
//        }
//
//        // Constructor with parameters
//        public Place(String name, String address, String owner, String imageUrl, Long rating) {
//            this.name = name;
//            this.address = address;
//            this.owner = owner;
//            this.imageUrl = imageUrl;
//            this.rating = rating;
//
//        }
//    }
//    public class Place {
//        private String plName;
//        private String plAddress;
//        private String plOwner;
//        private Float rating;
//        private String plImage;
//
//        public Place() {
//            // Default constructor required for calls to DataSnapshot.getValue(Place.class)
//        }
//
//        public String getPlName() {
//            return plName;
//        }
//
//        public void setPlName(String plName) {
//            this.plName = plName;
//        }
//
//        public String getPlAddress() {
//            return plAddress;
//        }
//
//        public void setPlAddress(String plAddress) {
//            this.plAddress = plAddress;
//        }
//
//        public String getPlOwner() {
//            return plOwner;
//        }
//
//        public void setPlOwner(String plOwner) {
//            this.plOwner = plOwner;
//        }
//
//        public float getRating() {
//            return rating;
//        }
//
//        public void setRating(Float rating) {
//            this.rating = rating;
//        }
//
//        public String getPlImage() {
//            return plImage;
//        }
//
//        public void setPlImage(String plImage) {
//            this.plImage = plImage;
//        }
//
////        public Place(String plName, String plAddress, String plOwner, String plImage, Float rating) {
////                this.plName = plName;
////                this.plAddress = plAddress;
////                this.plOwner = plOwner;
////                this.plImage = plImage;
////                this.rating = rating;
////
////            }
//    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder {

        ImageView placeImage;
        TextView placeName, placeAddress, placeOwner, placeRating;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            placeImage = itemView.findViewById(R.id.placeImage);
            placeName = itemView.findViewById(R.id.placeName);
            placeAddress = itemView.findViewById(R.id.placeAddress);
            placeOwner = itemView.findViewById(R.id.placeOwner);
            placeRating = itemView.findViewById(R.id.placeRating);
        }
    }
}
