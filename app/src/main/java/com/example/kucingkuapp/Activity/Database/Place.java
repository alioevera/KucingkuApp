package com.example.kucingkuapp.Activity.Database;

//package com.example.kucingkuapp.Activity.Database;
//
//public class Place {
//    private String plName;
//    private String plAddress;
//    private String plOwner;
//    private Float rating;
//    private String plImage;
//
//    public Place() {
//        // Default constructor required for calls to DataSnapshot.getValue(Place.class)
//    }
//
//    public String getPlName() {
//        return plName;
//    }
//
//    public void setPlName(String plName) {
//        this.plName = plName;
//    }
//
//    public String getPlAddress() {
//        return plAddress;
//    }
//
//    public void setPlAddress(String plAddress) {
//        this.plAddress = plAddress;
//    }
//
//    public String getPlOwner() {
//        return plOwner;
//    }
//
//    public void setPlOwner(String plOwner) {
//        this.plOwner = plOwner;
//    }
//
//    public float getRating() {
//        return rating;
//    }
//
//    public void setRating(Float rating) {
//        this.rating = rating;
//    }
//
//    public String getPlImage() {
//        return plImage;
//    }
//
//    public void setPlImage(String plImage) {
//        this.plImage = plImage;
//    }
//}
public class Place {
    private String plName;
    private String plAddress;
    private String plOwner;
    private String plImage;
    private Long rating;

    public Place() {
        // Default constructor required for calls to DataSnapshot.getValue(Place.class)
    }

    public Place(String plName, String plAddress, String plOwner, String plImage, Long rating) {
        this.plName = plName;
        this.plAddress = plAddress;
        this.plOwner = plOwner;
        this.plImage = plImage;
        this.rating = rating;

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

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public String getPlImage() {
        return plImage;
    }

    public void setPlImage(String plImage) {
        this.plImage = plImage;
    }


}