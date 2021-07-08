package ch.bbcag.NFController.AttributeFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.fragment.app.Fragment;

import ch.bbcag.NFController.R;

public  class IOAttribute extends Fragment implements View.OnClickListener {
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

        RadioButton radioOn = view.findViewById(R.id.RadioOn);
        radioOn.setOnClickListener(this);

        RadioButton radioOff = view.findViewById(R.id.RadioOff);
        radioOff.setOnClickListener(this);
        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.RadioOn:
                if (checked)
                    state = "1";

                break;
            case R.id.RadioOff:
                if (checked)
                    state = "0";

                break;
        }
    }

    public String getstate() {
        return state;
    }

}
