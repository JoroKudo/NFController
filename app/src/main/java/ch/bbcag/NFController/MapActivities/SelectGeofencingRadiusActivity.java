package ch.bbcag.NFController.MapActivities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import ch.bbcag.NFController.AppDataManager;
import ch.bbcag.NFController.Dagger2.NFControllerApplication;
import ch.bbcag.NFController.R;

public class SelectGeofencingRadiusActivity extends AppCompatActivity {

    @Inject
    AppDataManager appDataManager;
    private Intent intent;
    private EditText editText;
    private int radius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((NFControllerApplication) getApplicationContext()).appComponent.inject(this);

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
                    appDataManager.getSplitted()[5] = String.valueOf(radius);
                    intent = new Intent(SelectGeofencingRadiusActivity.this, SelectGeofencingExpirationTimeActivity.class);
                    startActivity(intent);
                } else
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.geofencing_value_too_small_toast), Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.geofencing_wrong_data_type_toast), Toast.LENGTH_SHORT).show();
            }
        });
    }
}