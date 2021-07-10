package com.tracking.tracking.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="tour")
public class Tour implements Serializable {
    @GeneratedValue
    @Id
    @Column(name="tour_id", unique = true)
    private Long tourId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStart;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timeFinish;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private Long timeTravel;

    private Float distanceBetween;

    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Location> locations = new ArrayList<>();


    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Date getTimeFinish() {
        return timeFinish;
    }

    public void setTimeFinish(Date timeFinish) {
        this.timeFinish = timeFinish;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Float getDistanceBetween() {
        return distanceBetween;
    }

    public void setDistanceBetween(Float distanceBetween) {
        this.distanceBetween = distanceBetween;
    }

    public Long getTimeTravel() {
        return timeTravel;
    }

    public void setTimeTravel(Long timeTravel) {
        this.timeTravel = timeTravel;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    @Override
    public String toString() {
        return "Tour{" +
                "tourId=" + tourId +
                ", timeStart=" + timeStart +
                ", timeFinish=" + timeFinish +
                ", user=" + user +
                ", locations=" + locations +
                '}';
    }
}
