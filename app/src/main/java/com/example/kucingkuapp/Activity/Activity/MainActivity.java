package com.example.kucingkuapp.Activity.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kucingkuapp.Activity.Adapter.PlacesAdapter;
import com.example.kucingkuapp.Activity.Database.Place;
import com.example.kucingkuapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView recyclerViewCategory; // Changed to Category RecyclerView
    private ProgressBar progressBarCategory; // Changed to Category ProgressBar
    private List<Place> placesList;
    private PlacesAdapter placesAdapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewCategory = findViewById(R.id.recyclerViewCategory); // Changed to Category RecyclerView
        progressBarCategory = findViewById(R.id.progressBarCategory); // Changed to Category ProgressBar

        // Setup RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewCategory.setLayoutManager(layoutManager); // Changed to Category RecyclerView

        // Initialize adapter with empty list
        placesList = new ArrayList<>();
        placesAdapter = new PlacesAdapter(placesList);
        recyclerViewCategory.setAdapter(placesAdapter); // Changed to Category RecyclerView

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("PlacesSet");

        // Load data from Firebase
        loadDataFromFirebase();
    }

    private void loadDataFromFirebase() {
        progressBarCategory.setVisibility(View.VISIBLE); // Changed to Category ProgressBar

        // Add listener to fetch data
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                placesList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Place place = dataSnapshot.getValue(Place.class);
                    if (place != null) {
                        placesList.add(place);
                        Log.d(TAG, "Place added: " + place.getPlName());
                    }
                }

                // Notify adapter that data has changed
                placesAdapter.notifyDataSetChanged();

                // Hide ProgressBar
                progressBarCategory.setVisibility(View.GONE); // Changed to Category ProgressBar
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
                progressBarCategory.setVisibility(View.GONE); // Changed to Category ProgressBar
                Toast.makeText(MainActivity.this, "Failed to load places: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Failed to load places", error.toException());
            }
        });
    }
}
