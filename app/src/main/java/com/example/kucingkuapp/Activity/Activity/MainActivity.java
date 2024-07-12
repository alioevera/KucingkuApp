package com.example.kucingkuapp.Activity.Activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;

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
    private Button buttonSubmitReview; // Tambahkan deklarasi Button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewCategory = findViewById(R.id.recyclerViewCategory);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        placesList = new ArrayList<>();
        placesAdapter = new PlacesAdapter(placesList, this);
        recyclerViewCategory.setAdapter(placesAdapter);

        spinnerPlace = findViewById(R.id.spinnerPlace);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextReview = findViewById(R.id.editTextReview);
        ratingBar = findViewById(R.id.ratingBar);
        buttonSubmitReview = findViewById(R.id.buttonSubmitReview); // Inisialisasi Button

        // Fetch data from Firebase
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

                // Set data to Spinner
                List<String> placeNames = new ArrayList<>();
                for (Place place : placesList) {
                    placeNames.add(place.getPlName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, placeNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerPlace.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error fetching data.", Toast.LENGTH_SHORT).show();
            }
        });

        // Set OnClickListener untuk tombol submit review
        buttonSubmitReview.setOnClickListener(v -> submitReview());
    }

    private void submitReview() {
        String username = editTextUsername.getText().toString().trim();
        String reviewText = editTextReview.getText().toString().trim();
        float rating = ratingBar.getRating();
        String placeName = spinnerPlace.getSelectedItem() != null ? spinnerPlace.getSelectedItem().toString() : "";

        // Validasi input
        if (username.isEmpty() || reviewText.isEmpty() || placeName.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Buat objek Review baru
        Review review = new Review(username, reviewText, rating, placeName);

        // Simpan review ke Firebase Database
        DatabaseReference reviewsReference = FirebaseDatabase.getInstance().getReference("reviews");
        reviewsReference.push().setValue(review)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(MainActivity.this, "Review submitted successfully!", Toast.LENGTH_SHORT).show();
                    // Clear input fields after successful submission
                    editTextUsername.setText("");
                    editTextReview.setText("");
                    ratingBar.setRating(0);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MainActivity.this, "Failed to submit review. Please try again.", Toast.LENGTH_SHORT).show();
                });
    }
}
