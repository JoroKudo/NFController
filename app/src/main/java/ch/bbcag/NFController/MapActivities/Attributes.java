package ch.bbcag.NFController.MapActivities;

import java.io.Serializable;

public class Attributes implements Serializable {

    private int radius = 5;
    private double longitude = 3.2541;

    public int getRadius() {
        return radius;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
