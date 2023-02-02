package com.parkit.parkingsystem.model;

import java.util.Calendar;
import java.util.Date;

public class Ticket {
    private int id;
    private ParkingSpot parkingSpot;
    private String vehicleRegNumber;
    private double price;
    private double discount;
    private Date inTime;

    private Date outTime;
    private boolean isReccuring;


    public boolean isReccuring() {
        return isReccuring;
    }

    public void setReccuring(boolean reccuring) {
        isReccuring = reccuring;
    }


    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ParkingSpot getParkingSpot() {
       return new ParkingSpot(parkingSpot.getId(), parkingSpot.getParkingType(), parkingSpot.isAvailable());
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {;
        this.parkingSpot = new ParkingSpot(parkingSpot.getId(), parkingSpot.getParkingType(), parkingSpot.isAvailable());
    }

    public String getVehicleRegNumber() {
        return vehicleRegNumber;
    }

    public void setVehicleRegNumber(String vehicleRegNumber) {
        this.vehicleRegNumber = vehicleRegNumber;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getInTime() {
        return inTime == null ? null : ( Date ) inTime.clone();
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime == null ? null : ( Date ) inTime.clone();
    }

    public Date getOutTime() {
        return outTime == null ? null : (Date) outTime.clone();
    }


    public void setOutTime(Date outTime) {
        this.outTime = outTime == null ? null : ( Date ) outTime.clone();
    }

    }


