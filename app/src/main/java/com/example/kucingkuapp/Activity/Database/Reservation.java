package com.example.kucingkuapp.Activity.Database;

public class Reservation {
    private String reservationId;
    private String userName;
    private String placeName;
    private String reservationDate;
    private String startTime;
    private String duration;
    private String status; // Tambahkan status jika dibutuhkan

    public Reservation() {
        // Konstruktor default diperlukan untuk Firebase
    }

    public Reservation(String reservationId, String userName, String placeName, String reservationDate, String startTime, String duration, String status) {
        this.reservationId = reservationId;
        this.userName = userName;
        this.placeName = placeName;
        this.reservationDate = reservationDate;
        this.startTime = startTime;
        this.duration = duration;
        this.status = status; // Inisialisasi status
    }

    // Getters dan Setters
    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
