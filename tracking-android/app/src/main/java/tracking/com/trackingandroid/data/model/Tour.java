package tracking.com.trackingandroid.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Tour implements Serializable {
    @Expose
    private Integer tourId;
    @Expose
    private String timeStart;
    @Expose
    private String timeFinish;
    @Expose
    private List<Location> locations;
    @Expose
    private Float distanceBetween;

    public Tour(String timeStart, String timeFinish, List<Location> locations, Float distanceBetween) {
        this.timeStart = timeStart;
        this.timeFinish = timeFinish;
        this.locations = locations;
        this.distanceBetween = distanceBetween;
    }

    public Integer getTourId() {
        return tourId;
    }

    public void setTourId(Integer tourId) {
        this.tourId = tourId;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeFinish() {
        return timeFinish;
    }

    public void setTimeFinish(String timeFinish) {
        this.timeFinish = timeFinish;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public Float getDistanceBetween() {
        return distanceBetween;
    }

    public void setDistanceBetween(Float distanceBetween) {
        this.distanceBetween = distanceBetween;
    }

    @Override
    public String toString() {
        return "Tour{" +
                "timeStart='" + timeStart + '\'' +
                ", timeFinish='" + timeFinish + '\'' +
                ", locations=" + locations +
                '}';
    }
}
