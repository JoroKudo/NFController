package ch.bbcag.NFController.MapActivities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ch.bbcag.NFController.Const;
import ch.bbcag.NFController.R;

public class SelectGeofencingRadiusActivity extends AppCompatActivity {

    private Intent intent;
    private EditText editText;
    private int radius;

    public SelectGeofencingRadiusActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_geofencing_radius);
        editText = findViewById(R.id.radius_input_field);

        editText.setHint(getResources().getString(R.string.radius_input_field_hint));

        FloatingActionButton floatingActionButton = findViewById(R.id.continue_to_expiration_time);
        floatingActionButton.setOnClickListener(v -> {
            String temp = editText.getText().toString();
            try {
                radius = Integer.parseInt(temp);
                if (radius >= 100) {
                    Const.fulltask[5] = String.valueOf(radius);
                    intent = new Intent(SelectGeofencingRadiusActivity.this, SelectGeofencingExpirationTimeActivity.class);
                    startActivity(intent);
                } else
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.geofencing_value_too_small_toast), Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.geofencing_wrong_data_type_toast), Toast.LENGTH_SHORT).show();
            }


        });
    }

    public int getRadius() {
        return radius;
    }
}