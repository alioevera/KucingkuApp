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
    private RecyclerView recyclerViewRecommended;
    private ProgressBar progressBarRecommended;
    private List<Place> placesList;
    private PlacesAdapter placesAdapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewRecommended = findViewById(R.id.recyclerViewRecommended);
        progressBarRecommended = findViewById(R.id.progressBarRecommended);

        // Setup RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewRecommended.setLayoutManager(layoutManager);

        // Initialize adapter with empty list
        placesList = new ArrayList<>();
        placesAdapter = new PlacesAdapter(placesList);
        recyclerViewRecommended.setAdapter(placesAdapter);

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("PlacesSet");

        // Load data from Firebase
        loadDataFromFirebase();
    }

    private void loadDataFromFirebase() {
        progressBarRecommended.setVisibility(View.VISIBLE);

        // Add listener to fetch data
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                placesList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Place place = dataSnapshot.getValue(Place.class);
                    if (place != null) {
                        placesList.add(place);
                        Log.d(TAG, "Place added: ");
                    }
                }

                // Notify adapter that data has changed
                placesAdapter.notifyDataSetChanged();

                // Hide ProgressBar
                progressBarRecommended.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
                progressBarRecommended.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Failed to load places: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Failed to load places", error.toException());
            }
        });
    }
}
