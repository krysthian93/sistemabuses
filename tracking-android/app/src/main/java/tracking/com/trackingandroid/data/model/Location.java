package tracking.com.trackingandroid.data.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Location implements Serializable {
    @Expose
    private String latitud;
    @Expose
    private String longitud;

    public Location(String latitud, String longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
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
