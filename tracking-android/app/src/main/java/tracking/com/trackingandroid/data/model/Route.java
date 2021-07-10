package tracking.com.trackingandroid.data.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;
public class Route implements Serializable {


        @Expose
        private Integer routeId;
        @Expose
        private String spotStart;
        @Expose
        private String spotFinish;
        @Expose
        private List<Location> locations;
        @Expose
        private Float distanceBetween;

        public Route(String spotStart, String spotFinish, List<Location> locations, Float distanceBetween) {
            this.spotStart = spotStart;
            this.spotFinish = spotFinish;
            this.locations = locations;
            this.distanceBetween = distanceBetween;
        }

        public Integer getRouteId() {
            return routeId;
        }

        public void setRouteId(Integer routeId) {
            this.routeId = routeId;
        }

        public String getspotStart() {
            return spotStart;
        }

        public void setspotStart(String spotStart) {
            this.spotStart = spotStart;
        }

        public String getspotFinish() {
            return spotFinish;
        }

        public void setspotFinish(String spotFinish) {
            this.spotFinish = spotFinish;
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


