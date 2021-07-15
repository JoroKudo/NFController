package ch.bbcag.NFController.MapActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import javax.inject.Inject;

import ch.bbcag.NFController.AppDataManager;
import ch.bbcag.NFController.Const;
import ch.bbcag.NFController.Dagger2.NFControllerApplication;
import ch.bbcag.NFController.R;

public class GeofencingFeatureSelector extends Fragment {
    private final String[] entries = {"Bluetooth off", "Bluetooth on", "WiFi off ", "WiFi on ", "TONE", "MUTE",
            "VIBRATE", "VOL", "OpenApp", "FLASHLIGHT", "OpenWebsite"};
    @Inject
    AppDataManager appDataManager;
    private int option;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        ((NFControllerApplication) requireContext().getApplicationContext()).appComponent.inject(this);

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_list, container,
                false);
        ListView listView = view.findViewById(R.id.mobile_list);

        ArrayAdapter<?> adapter = new ArrayAdapter<>(getContext(), R.layout.task_list_item, entries);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, clickView, position, id) -> {
            appDataManager.getSplitted()[7] = Const.GEOTASKS[position];
            appDataManager.getSplitted()[9] = entries[position];

            areOptionsAvailable(position);
            Intent intent = new Intent();
            intent.setClass(getContext(), FinalGeoFencingViewActivity.class);
            intent.putExtra("FinalView", "FromFeatureSelector"); // TODO key -> Const file
            startActivity(intent);
        });
        return view;
    }

    private void areOptionsAvailable(int position) {
        if (appDataManager.getSplitted()[7].equals("blue") || appDataManager.getSplitted()[7].equals("wifi") || appDataManager.getSplitted()[7].equals("flash")) {
            setOption(position);
        }
    }

    private void setOption(int position) {
        if (position == 0 || position == 2) {
            option = 0;
        } else if (position == 1 || position == 3 || position == 9) {
            option = 1;
        }
        appDataManager.getSplitted()[8] = Integer.toString(option);
    }

}