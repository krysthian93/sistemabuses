package com.tracking.tracking.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="location")
public class Location implements Serializable {

    @Id
    @GeneratedValue
    @Column(name="location_id", unique = true)
    private Long locationId;

    @JoinColumn(name = "tour_id", referencedColumnName = "tour_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonBackReference
    private Tour tour;

    private String latitud;

    private String longitud;

    public Location() {
    }

    public Location(String latitud, String longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
