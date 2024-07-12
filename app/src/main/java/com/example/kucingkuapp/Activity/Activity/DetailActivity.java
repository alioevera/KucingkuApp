package com.example.kucingkuapp.Activity.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kucingkuapp.Activity.Adapter.ReviewAdapter;
import com.example.kucingkuapp.Activity.Database.Place;
import com.example.kucingkuapp.Activity.Database.Review;
import com.example.kucingkuapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private TextView placeName, placeAddress, placeOwner, placeRating;
    private ImageView placeImage;
    private RecyclerView reviewRecyclerView;
    private ReviewAdapter reviewAdapter;
    private List<Review> reviewList;
    private DatabaseReference reviewReference;
    private Place place;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        placeName = findViewById(R.id.placeName);
        placeAddress = findViewById(R.id.placeAddress);
        placeOwner = findViewById(R.id.placeOwner);
        placeRating = findViewById(R.id.placeRating);
        placeImage = findViewById(R.id.placeImage);
        reviewRecyclerView = findViewById(R.id.reviewRecyclerView);
        backButton = findViewById(R.id.ic_back);

        place = (Place) getIntent().getSerializableExtra("place");

        placeName.setText(place.getPlName());
        placeAddress.setText(place.getPlAddress());
        placeOwner.setText("Owner: " + place.getPlOwner());
        placeRating.setText("Rating: " + place.getRating());

        Glide.with(this)
                .load(place.getPlImage())
                .into(placeImage);

        // Set up RecyclerView for reviews
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewList = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(reviewList);
        reviewRecyclerView.setAdapter(reviewAdapter);

        // Fetch reviews from Firebase
        reviewReference = FirebaseDatabase.getInstance().getReference("reviews");
        reviewReference.orderByChild("placeName").equalTo(place.getPlName()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reviewList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Review review = dataSnapshot.getValue(Review.class);
                    if (review != null) {
                        Log.d("DetailActivity", "Review fetched: " + review.getUsReview());
                        reviewList.add(review);
                    }
                }
                reviewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DetailActivity", "Database error: " + error.getMessage());
            }
        });

        // Set onClickListener for back button
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(DetailActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Optionally close DetailActivity
        });
    }
}
