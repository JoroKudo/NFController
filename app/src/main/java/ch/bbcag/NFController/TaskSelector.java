package ch.bbcag.NFController;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import ch.bbcag.NFController.MapActivities.MapsActivity;

public class TaskSelector extends AppCompatActivity {
    final String[] fulltask = {"", "", "", "", "", "", "", "", "", ""};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list);
        ListView listView = findViewById(R.id.mobile_list);
        ArrayAdapter<?> adapter = new ArrayAdapter<>(this, R.layout.task_list_item, Const.tasknames);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent;
            if (Const.tasknames[position].equals("Custom procedure")) {
                fulltask[0] = "blue";
                intent = new Intent(this, ProcedureSelector.class);
            } else {
                fulltask[0] = Const.TASKS[position];
                if (!fulltask[0].equals("geofencing")) {
                    intent = new Intent(this, AttributeSetter.class);
                    intent.putExtra("FULL_TASK", fulltask);
                } else {
                    intent = new Intent(this, MapsActivity.class);
                }
            }
            startActivity(intent);
            finish();
        });
    }
}