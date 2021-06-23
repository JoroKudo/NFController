package ch.bbcag.NFController.MapActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ch.bbcag.NFController.R;

public class SelectGeoFencingFeatureActivity extends AppCompatActivity {

    String selectedFeature;
    double radius;
    double expirationTimeInMiliseconds;
    double Latitude;
    double Longitude;

    MapsActivity mapsActivity = new MapsActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_geo_fencing_feature);
    }
}