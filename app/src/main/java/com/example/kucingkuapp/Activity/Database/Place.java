package com.example.kucingkuapp.Activity.Database;

public class Place {
    private String plName;
    private String plAddress;
    private String plOwner;
    private String plImage;
    private float rating; // Changed to float

    public Place() {
        // Default constructor required for calls to DataSnapshot.getValue(Place.class)
    }

    public String getPlName() {
        return plName;
    }

    public void setPlName(String plName) {
        this.plName = plName;
    }

    public String getPlAddress() {
        return plAddress;
    }

    public void setPlAddress(String plAddress) {
        this.plAddress = plAddress;
    }

    public String getPlOwner() {
        return plOwner;
    }

    public void setPlOwner(String plOwner) {
        this.plOwner = plOwner;
    }

    public String getPlImage() {
        return plImage;
    }

    public void setPlImage(String plImage) {
        this.plImage = plImage;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
