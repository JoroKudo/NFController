package ch.bbcag.NFController.AttributeFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import ch.bbcag.NFController.R;

public class VolumeModeParam extends Fragment {
    String state;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.attribute_volmode, container,
                false);

        Spinner dropdown = view.findViewById(R.id.spinner1);
        String[] items = new String[]{"mute", "vibrate", "tone"};
        ArrayAdapter<?> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                state = items[pos];

            }

            public void onNothingSelected(AdapterView<?> parent) {
                state = items[0];
            }
        });


        return view;
    }


    public String getVolMode() {
        return state;
    }

}