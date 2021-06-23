package ch.bbcag.NFController.MapActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import ch.bbcag.NFController.AttributeSetter;
import ch.bbcag.NFController.Const;
import ch.bbcag.NFController.R;

public class SelectGeoFencingFeatureActivity extends AppCompatActivity {

    String[] mobileArray = {"Bluetooth", "WiFi", "TONE", "MUTE",
            "VIBRATE", "VOL", "OpenApp", "FLASHLIGHT", "OpenWebsite"};

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
        ListView listView = findViewById(R.id.mobile_list);
        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.task_list_item, mobileArray);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Const.fulltask[0] = Const.GEOTASKS[position];
            Intent intent;
            intent = new Intent(this, MapsActivity.class);
            startActivity(intent);


        });
    }




}