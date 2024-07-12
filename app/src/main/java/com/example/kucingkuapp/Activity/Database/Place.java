// Place.java
package com.example.kucingkuapp.Activity.Database;

import java.io.Serializable;

public class Place implements Serializable {

    private String plName;
    private String plAddress;
    private String plOwner;
    private float rating;
    private String plImage;

    public Place() {
        // Default constructor required for calls to DataSnapshot.getValue(Place.class)
    }

    public Place(String plName, String plAddress, String plOwner, float rating, String plImage) {
        this.plName = plName;
        this.plAddress = plAddress;
        this.plOwner = plOwner;
        this.rating = rating;
        this.plImage = plImage;
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getPlImage() {
        return plImage;
    }

    public void setPlImage(String plImage) {
        this.plImage = plImage;
    }
}
