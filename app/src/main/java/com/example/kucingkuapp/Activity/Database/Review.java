package com.example.kucingkuapp.Activity.Database;

public class Review {
    private String username;
    private String reviewText;
    private float rating; // Pastikan ini adalah float
    private String placeName;

    public Review() {
        // Default constructor
    }

    public Review(String username, String reviewText, float rating, String placeName) {
        this.username = username;
        this.reviewText = reviewText;
        this.rating = rating;
        this.placeName = placeName;
    }

    public Review(String username, String reviewText, String s, String placeName) {
    }

    public String getUsername() {
        return username;
    }

    public String getReviewText() {
        return reviewText;
    }

    public float getRating() {
        return rating; // Pastikan ini benar
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getUsReview() {
        return reviewText; // Kembalikan reviewText sebagai UsReview
    }
}
