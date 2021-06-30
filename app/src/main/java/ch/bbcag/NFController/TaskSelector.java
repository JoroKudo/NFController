package ch.bbcag.NFController;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import ch.bbcag.NFController.MapActivities.MapsActivity;


public class TaskSelector extends AppCompatActivity {



    // Array of strings...


    String[] fulltask = {"", "", "", "", "", "", "", "", "", ""};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list);
        ListView listView = findViewById(R.id.mobile_list);

        ArrayAdapter<?> adapter = new ArrayAdapter<>(this, R.layout.task_list_item, Const.tasknames);


        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, viewv, position, id) -> {
            fulltask[0]=Const.TASKS.get(position);

            Intent intent;
            if (! fulltask[0].equals("geofencing")){

                intent = new Intent(this, AttributeSetter.class);
                intent.putExtra("FULL_TASK", fulltask);
            }else {
                intent = new Intent(this, MapsActivity.class);
            }
            startActivity(intent);


        });


    }




}

