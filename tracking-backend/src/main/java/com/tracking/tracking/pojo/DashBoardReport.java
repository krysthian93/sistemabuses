package com.tracking.tracking.pojo;

import java.io.Serializable;
import java.util.List;

public class DashBoardReport implements Serializable {

    public List<IDistanceBetween> distanceBetween;
    public List<ITourPerMonth> tourPerMonth;
    public List<ITimeTraveled> timeTraveled;

    public DashBoardReport() {
    }

    public DashBoardReport(List<IDistanceBetween> distanceBetween, List<ITourPerMonth> tourPerMonth, List<ITimeTraveled> timeTraveled) {
        this.distanceBetween = distanceBetween;
        this.tourPerMonth = tourPerMonth;
        this.timeTraveled = timeTraveled;
    }

    public List<IDistanceBetween> getDistanceBetween() {
        return distanceBetween;
    }

    public void setDistanceBetween(List<IDistanceBetween> distanceBetween) {
        this.distanceBetween = distanceBetween;
    }

    public List<ITourPerMonth> getTourPerMonth() {
        return tourPerMonth;
    }

    public void setTourPerMonth(List<ITourPerMonth> tourPerMonth) {
        this.tourPerMonth = tourPerMonth;
    }

    public List<ITimeTraveled> getTimeTraveled() {
        return timeTraveled;
    }

    public void setTimeTraveled(List<ITimeTraveled> timeTraveled) {
        this.timeTraveled = timeTraveled;
    }
}
