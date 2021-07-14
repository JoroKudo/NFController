package ch.bbcag.NFController.AttributeFragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.fragment.app.Fragment;

import java.util.Arrays;

import ch.bbcag.NFController.R;

public class TimerAttribute extends Fragment {

    NumberPicker hourPicker;
    NumberPicker minutePicker;
    NumberPicker secondPicker;
    EditText timerMessage;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.attribute_timer, container,
                false);
        hourPicker = view.findViewById(R.id.NPhours);
        minutePicker = view.findViewById(R.id.NPminutes);
        secondPicker = view.findViewById(R.id.NPseconds);
        timerMessage = view.findViewById(R.id.Timermessage);


        for (NumberPicker numberPicker : Arrays.asList(hourPicker, minutePicker, secondPicker)) {
            numberPicker.setTextColor(Color.WHITE);
            numberPicker.setMinValue(0);
            if (numberPicker == hourPicker) {
                numberPicker.setMaxValue(23);
            } else {
                numberPicker.setMaxValue(59);
            }
        }


        return view;

    }

    public String[] getTimer() {

        return new String[]
                {Integer.toString(hourPicker.getValue()),
                        Integer.toString(minutePicker.getValue()),
                        Integer.toString(secondPicker.getValue()),
                        timerMessage.getText().toString()};
    }


}