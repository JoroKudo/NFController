package ch.bbcag.NFController;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import ch.bbcag.NFController.MapActivities.MapsActivity;
import ch.bbcag.NFController.MapActivities.SelectGeofencingRadiusActivity;


public class HomeTab2 extends Fragment {

    public HomeTab2(){}

    // Array of strings...
    String[] mobileArray = {"BlueTooth", "WiFi", "TONE", "MUTE",
            "VIBRATE", "VOL", "TTS", "OpenApp", "set Alarm", "set Timer", "Location", "FLASHLIGHT", "SendWhatsapp", "OpenWebsite", "Geofencing"};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.task_list, container,
                false);
        ListView listView = view.findViewById(R.id.mobile_list);
        ArrayAdapter adapter = new ArrayAdapter<>(getContext(), R.layout.task_list_item, mobileArray);


        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, viewv, position, id) -> {
                Const.fulltask[0] = Const.TASKS[position];
            Intent intent;
            if (Const.fulltask[0] != "geofencing"){
                intent = new Intent(getContext(), AttributeSetter.class);
            }else {
                intent = new Intent(getContext(), MapsActivity.class);
            }
            startActivity(intent);


        });

        return view;

    }


}
