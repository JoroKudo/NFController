package ch.bbcag.NFController.AttributeFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.hbb20.CountryCodePicker;

import ch.bbcag.NFController.R;

public class MessageParam extends Fragment {
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

        ccp = view.findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(view.findViewById(R.id.wew));
        return view;

    }


    public String[] getMessage() {
        return new String[]{ccp.getFullNumberWithPlus(), msgInput.getText().toString()};


    }

}
