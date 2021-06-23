package ch.bbcag.NFController.MapActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ch.bbcag.NFController.R;

public class SelectGeofencingRadiusActivity extends AppCompatActivity {

    Intent intent;
    EditText editText;
    double radius;
    FloatingActionButton floatingActionButton;

    public SelectGeofencingRadiusActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_geofencing_radius);

        floatingActionButton.setOnClickListener(v -> {
            editText = findViewById(R.id.radius_input_field);
            String temp = editText.getText().toString();
            try {
                radius = Double.parseDouble(temp);
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.geofencing_wrong_data_type_toast), Toast.LENGTH_SHORT).show();
            }
            if (radius < 100) {
                intent = new Intent(SelectGeofencingRadiusActivity.this, SelectGeofencingExpirationTimeActivity.class);
                getApplicationContext().startActivity(intent);
            } else
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.geofencing_value_too_small_toast), Toast.LENGTH_SHORT).show();


        });
    }

    public double getRadius() {
        return radius;
    }
}