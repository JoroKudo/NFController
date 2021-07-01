package ch.bbcag.NFController;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        String state;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.attribute_io, container,
                    false);

            RadioButton radioOn = view.findViewById(R.id.radioon);
            radioOn.setOnClickListener(this);

            RadioButton radioOff = view.findViewById(R.id.radiooff);
            radioOff.setOnClickListener(this);
            return view;
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            boolean checked = ((RadioButton) view).isChecked();
            switch (view.getId()) {
                case R.id.radioon:
                    if (checked)
                        state = "1";

                    break;
                case R.id.radiooff:
                    if (checked)
                        state = "0";

                    break;
            }
        }

        public String getstate() {
            return state;
        }

    }

    public static class AppSelector extends Fragment {

        private String[] pkgNames;
        private String[] appNames;
        private Drawable[] appIcons;
        String app;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            List<ResolveInfo> pkgAppsList = requireContext().getPackageManager().queryIntentActivities(mainIntent, 0);
            appNames = new String[pkgAppsList.size()];
            pkgNames = new String[pkgAppsList.size()];
            appIcons = new Drawable[pkgAppsList.size()];
            for (int i = 0; i < pkgAppsList.size(); i++) {
                pkgNames[i] = pkgAppsList.get(i).toString().split(" ")[1].split("/")[0];
                PackageManager packageManager = requireActivity().getApplicationContext().getPackageManager();

                try {
                    appIcons[i] = packageManager.getApplicationIcon(pkgNames[i]);
                    appNames[i] = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(pkgNames[i], PackageManager.GET_META_DATA));
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }


            }
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.task_list, container,
                    false);

            ListView listView = view.findViewById(R.id.mobile_list);


            // For populating list data
            CustomList appList = new CustomList(getActivity(), appNames, appIcons);
            listView.setAdapter(appList);


            listView.setOnItemClickListener((parent, clickView, position, id) -> {

                app = pkgNames[position];
                for (int i = 0; i < parent.getChildCount(); i++) {
                    parent.getChildAt(i).setBackgroundColor(android.R.color.transparent);
                }
                clickView.setBackgroundColor(Color.BLUE);

            });
            return view;
        }

        public String getapp() {
            return app;

        }


    }

    public static class TextParameter extends Fragment {
        EditText textInput;


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.attribute_text, container,
                    false);
            textInput = view.findViewById(R.id.wowidc);


            return view;

        }


        public String getTextinput() {
            return textInput.getText().toString();

        }

    }

    public static class MessageParameter extends Fragment {
        EditText msgInput;
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
            msgInput = view.findViewById(R.id.wowidc);

            ccp = (CountryCodePicker) view.findViewById(R.id.ccp);
            ccp.registerCarrierNumberEditText(view.findViewById(R.id.wew));
            return view;

        }


        public String[] getMessage() {
            return new String[]{ccp.getFullNumberWithPlus(), msgInput.getText().toString()};


        }

    }

    public static class AlarmAttribute extends Fragment {
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
            alertMessage = view.findViewById(R.id.Alarmmessage);


            return view;

        }

        public String[] setAlarm() {
            return new String[]
                    {timePicker.getCurrentHour().toString(),
                            timePicker.getCurrentMinute().toString(),
                            alertMessage.getText().toString()};

        }


    }

    public static class TimerAttribute extends Fragment {

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
}
