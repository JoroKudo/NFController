package ch.bbcag.NFController;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;

import java.util.Arrays;

public class AttributeFragments {
    public static class IOAttribute extends Fragment implements View.OnClickListener {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.attribute_io, container,
                    false);

            RadioButton radioon = view.findViewById(R.id.radioon);
            radioon.setOnClickListener(this);

            RadioButton radiooff = view.findViewById(R.id.radiooff);
            radiooff.setOnClickListener(this);
            return view;
        }

        @Override
        public void onClick(View view) {
            boolean checked = ((RadioButton) view).isChecked();
            switch (view.getId()) {
                case R.id.radioon:
                    if (checked)
                        Const.fulltask[1] = "1";

                    break;
                case R.id.radiooff:
                    if (checked)
                        Const.fulltask[1] = "0";

                    break;
            }
        }


    }

    public static class TextAttribute extends Fragment {
        EditText attributer;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.attribute_text, container,
                    false);
            attributer = view.findViewById(R.id.wowidc);


            return view;

        }

        public void setText() {
            Const.fulltask[1] = attributer.getText().toString();

        }

    }

    public static class AlarmAttribute extends Fragment {
        EditText alertmessage;
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
            alertmessage = view.findViewById(R.id.Alarmmessage);


            return view;

        }

        public void setAlarm() {
            Const.fulltask[1] = timePicker.getCurrentHour().toString();
            Const.fulltask[2] = timePicker.getCurrentMinute().toString();
            Const.fulltask[3] = alertmessage.getText().toString();
        }


    }

    public static class TimerAttribute extends Fragment {

        NumberPicker hourpicker;
        NumberPicker minutepicker;
        NumberPicker secondpicker;
        EditText timermessage;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.attribute_timer, container,
                    false);
            hourpicker = view.findViewById(R.id.NPhours);
            minutepicker = view.findViewById(R.id.NPminutes);
            secondpicker = view.findViewById(R.id.NPseconds);
            timermessage = view.findViewById(R.id.Timermessage);


            for (NumberPicker numberPicker : Arrays.asList(hourpicker, minutepicker, secondpicker)) {
                numberPicker.setTextColor(Color.WHITE);
                numberPicker.setMinValue(0);
                if (numberPicker == hourpicker) {
                    numberPicker.setMaxValue(23);
                } else {
                    numberPicker.setMaxValue(59);
                }
            }


            return view;

        }

        public void setTimer() {

            Const.fulltask[1] = Integer.toString(hourpicker.getValue());
            Const.fulltask[2] = Integer.toString(minutepicker.getValue());
            Const.fulltask[3] = Integer.toString(secondpicker.getValue());
            Const.fulltask[4] = timermessage.getText().toString();
        }


    }
}
