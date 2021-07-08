package ch.bbcag.NFController.AttributeFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import ch.bbcag.NFController.R;

public  class TextParam extends Fragment {
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
