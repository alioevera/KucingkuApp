package com.example.kucingkuapp.Activity.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kucingkuapp.Activity.Database.Reservation;
import com.example.kucingkuapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class ReserveActivity extends AppCompatActivity {

    private EditText inputName, placeName, reservationDate, startTime;
    private Button dateButton, timeButton, submitBtn;
    private Spinner durationSpinner;
    private ImageButton backButton;
    private DatabaseReference reservationReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserve_activity);

        inputName = findViewById(R.id.inputName);
        placeName = findViewById(R.id.placeName);
        reservationDate = findViewById(R.id.reservationDate);
        startTime = findViewById(R.id.startTime);
        dateButton = findViewById(R.id.dateButton);
        timeButton = findViewById(R.id.timeButton);
        durationSpinner = findViewById(R.id.durationSpinner);
        submitBtn = findViewById(R.id.submitBtn);
        backButton = findViewById(R.id.ic_back);

        // Set up the duration spinner
        String[] durationOptions = {"3 hours - 300k", "4 hours - 400k", "5 hours - 500k", "48 hours - 4800k"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, durationOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        durationSpinner.setAdapter(adapter);

        reservationReference = FirebaseDatabase.getInstance().getReference("reservations");

        // Retrieve the place name from the intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("PLACE_NAME")) {
            String place = intent.getStringExtra("PLACE_NAME");
            placeName.setText(place);
            placeName.setEnabled(false); // Disable editing
        }

        dateButton.setOnClickListener(v -> showDatePickerDialog());
        timeButton.setOnClickListener(v -> showTimePickerDialog());
        submitBtn.setOnClickListener(v -> submitReservation());
        backButton.setOnClickListener(v -> onBackPressed()); // Handle back navigation
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            reservationDate.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1);
        }, year, month, day);
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute1) -> {
            startTime.setText(String.format("%02d:%02d", hourOfDay, minute1));
        }, hour, minute, true);
        timePickerDialog.show();
    }

    private void submitReservation() {
        String name = inputName.getText().toString().trim();
        String place = placeName.getText().toString().trim();
        String date = reservationDate.getText().toString().trim();
        String time = startTime.getText().toString().trim();
        String duration = durationSpinner.getSelectedItem().toString();

        if (name.isEmpty() || place.isEmpty() || date.isEmpty() || time.isEmpty() || duration.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String reservationId = reservationReference.push().getKey();
        String status = "Progress"; // Set initial status to "Progress"
        Reservation reservation = new Reservation(reservationId, name, place, date, time, duration, status);

        reservationReference.child(reservationId).setValue(reservation).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(ReserveActivity.this, "Reservation successful", Toast.LENGTH_SHORT).show();
                finish(); // Navigate back to previous activity
            } else {
                Toast.makeText(ReserveActivity.this, "Reservation failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
