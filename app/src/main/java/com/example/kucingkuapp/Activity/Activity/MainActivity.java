package com.example.kucingkuapp.Activity.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kucingkuapp.Activity.Adapter.PlacesAdapter;
import com.example.kucingkuapp.Activity.Database.Place;
import com.example.kucingkuapp.Activity.Database.Review;
import com.example.kucingkuapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCategory;
    private PlacesAdapter placesAdapter;
    private List<Place> placesList;
    private DatabaseReference databaseReference;
    private Spinner spinnerPlace;
    private EditText editTextUsername, editTextReview;
    private RatingBar ratingBar;
    private Button buttonSubmitReview;
    private ProgressBar progressBarCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBarCategory = findViewById(R.id.progressBarCategory);

        recyclerViewCategory = findViewById(R.id.recyclerViewCategory);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        placesList = new ArrayList<>();
        placesAdapter = new PlacesAdapter(placesList, this);
        recyclerViewCategory.setAdapter(placesAdapter);

        spinnerPlace = findViewById(R.id.spinnerPlace);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextReview = findViewById(R.id.editTextReview);
        ratingBar = findViewById(R.id.ratingBar);
        buttonSubmitReview = findViewById(R.id.buttonSubmitReview);

        progressBarCategory.setVisibility(View.VISIBLE);

        databaseReference = FirebaseDatabase.getInstance().getReference("PlacesSet");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                placesList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Place place = dataSnapshot.getValue(Place.class);
                    if (place != null) {
                        placesList.add(place);
                    }
                }
                placesAdapter.notifyDataSetChanged();

                List<String> placeNames = new ArrayList<>();
                for (Place place : placesList) {
                    placeNames.add(place.getPlName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, placeNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerPlace.setAdapter(adapter);

                progressBarCategory.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error fetching data.", Toast.LENGTH_SHORT).show();
                progressBarCategory.setVisibility(View.GONE);
            }
        });

        buttonSubmitReview.setOnClickListener(v -> submitReview());

        // Setup BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_history) {
                startActivity(new Intent(MainActivity.this, HistoryActivity.class));
                return true;
            } else if (item.getItemId() == R.id.navigation_settings) {
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                return true;
            }
            // Handle other menu items if needed
            return false;
        });

    }

    private void submitReview() {
        String username = editTextUsername.getText().toString().trim();
        String reviewText = editTextReview.getText().toString().trim();
        float rating = ratingBar.getRating();
        String placeName = spinnerPlace.getSelectedItem() != null ? spinnerPlace.getSelectedItem().toString() : "";

        if (username.isEmpty() || reviewText.isEmpty() || placeName.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        Review review = new Review(username, reviewText, rating, placeName);

        DatabaseReference reviewsReference = FirebaseDatabase.getInstance().getReference("reviews");
        reviewsReference.push().setValue(review)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(MainActivity.this, "Review submitted successfully!", Toast.LENGTH_SHORT).show();
                    editTextUsername.setText("");
                    editTextReview.setText("");
                    ratingBar.setRating(0);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MainActivity.this, "Failed to submit review. Please try again.", Toast.LENGTH_SHORT).show();
                });
    }
}
