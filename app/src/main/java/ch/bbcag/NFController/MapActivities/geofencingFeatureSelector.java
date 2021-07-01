package ch.bbcag.NFController.MapActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import ch.bbcag.NFController.Const;
import ch.bbcag.NFController.R;

public class geofencingFeatureSelector extends Fragment {
    private final String[] entries = {"Bluetooth off", "Bluetooth on", "WiFi off ", "WiFi on ", "TONE", "MUTE",
            "VIBRATE", "VOL", "OpenApp", "FLASHLIGHT", "OpenWebsite"};
    private int option;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_list, container,
                false);
        ListView listView = (ListView) view.findViewById(R.id.mobile_list);

        // For populating list data
        ArrayAdapter adapter = new ArrayAdapter<>(getContext(), R.layout.task_list_item, entries);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, clickView, position, id) -> {
            Const.fulltask[7] = Const.GEOTASKS[position];
            Const.fulltask[9] = entries[position];
            areOptionsAvailable(position);
            Intent intent = new Intent(getContext(), FinalGeoFencingViewActivity.class);
            startActivity(intent);
        });
        return view;
    }

    private void areOptionsAvailable(int position) {
        if (Const.fulltask[7] == "blue" || Const.fulltask[5] == "wifi") {
            setOption(position);
        }
    }

    private void setOption(int position) {
        if (position == 0 || position == 2) {
            option = 0;
        } else if (position == 1 || position == 3) {
            option = 1;
        }
        Const.fulltask[8] = Integer.toString(option);
    }

}