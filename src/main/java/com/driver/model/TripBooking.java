package com.driver.model;

import javax.persistence.*;

@Entity
@Table(name="TripBooking")
public class TripBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int tripBookingId;

    private String fromLocation;

    private String toLocation;

    private int distance;

    @Enumerated(value = EnumType.STRING)
    private TripStatus tripStatus;

    private int bill;


    @ManyToOne
    @JoinColumn
    private Customer customer;

    @ManyToOne
    @JoinColumn
    private Driver driver;

    public TripBooking() {

    }

    public TripBooking(String fromLocation, String toLocation, int distanceInKm, TripStatus confirmed) {
        this.fromLocation=fromLocation;
        this.toLocation=toLocation;
        this.distance=distanceInKm;
        this.tripStatus=confirmed;
    }

    public int getTripBookingId() {
        return tripBookingId;
    }

    public void setTripBookingId(int tripBookingId) {
        this.tripBookingId = tripBookingId;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public int getDistanceInKm() {
        return distance;
    }

    public void setDistanceInKm(int distance) {
        this.distance = distance;
    }

    public TripStatus getStatus() {
        return tripStatus;
    }

    public void setStatus(TripStatus tripStatus) {
        this.tripStatus = tripStatus;
    }

    public int getBill() {
        return bill;
    }

    public void setBill(int bill) {
        this.bill = bill;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public TripBooking(int tripBookingId, String fromLocation, String toLocation, int distance, TripStatus tripStatus, int bill, Customer customer, Driver driver) {
        this.tripBookingId = tripBookingId;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.distance = distance;
        this.tripStatus = tripStatus;
        this.bill = bill;
        this.customer = customer;
        this.driver = driver;
    }
}