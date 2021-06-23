package ch.bbcag.NFController;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;

import com.hbb20.CountryCodePicker;

import java.util.Arrays;
import java.util.List;

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

    public static class AppSelector extends Fragment {
        private List<ResolveInfo> pkgAppsList;
        private String[] lol;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            pkgAppsList = getContext().getPackageManager().queryIntentActivities(mainIntent, 0);
            lol = new String[pkgAppsList.size()];
            for (int i = 0; i < pkgAppsList.size()-1; i++) {
                lol[i] = pkgAppsList.get(i).resolvePackageName;
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.task_list, container,
                    false);

            ListView listView = view.findViewById(R.id.mobile_list);
            ArrayAdapter adapter = new ArrayAdapter<>(getContext(), R.layout.task_list_item, pkgAppsList);


            listView.setAdapter(adapter);
            listView.setOnItemClickListener((parent, viewv, position, id) -> {
                Const.fulltask[0] = Const.TASKS[position];

            });
            return view;
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

    public static class WhatsAppAttribute extends Fragment {
        EditText msgp;
        CountryCodePicker ccp;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.attribute_whatsapp, container,
                    false);
            msgp = view.findViewById(R.id.wowidc);

            ccp = (CountryCodePicker) view.findViewById(R.id.ccp);
            ccp.registerCarrierNumberEditText(view.findViewById(R.id.wew));
            return view;

        }


        public void setmessage() {
            Const.fulltask[1] = ccp.getFullNumberWithPlus();
            Const.fulltask[2] = msgp.getText().toString();


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
