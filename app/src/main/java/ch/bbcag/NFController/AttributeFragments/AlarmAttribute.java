package ch.bbcag.NFController.AttributeFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;

import ch.bbcag.NFController.R;

public  class AlarmAttribute extends Fragment {
    EditText alertMessage;
    TimePicker timePicker;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.attribute_alarm, container,
                false);

        timePicker = view.findViewById(R.id.timePicker1);
        alertMessage = view.findViewById(R.id.AlertMessage);


        return view;

    }

    public String[] setAlarm() {
        return new String[]
                {timePicker.getCurrentHour().toString(),
                        timePicker.getCurrentMinute().toString(),
                        alertMessage.getText().toString()};

    }


}
