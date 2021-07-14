package ch.bbcag.NFController;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ch.bbcag.NFController.Const;
import ch.bbcag.NFController.R;
import ch.bbcag.NFController.TaskAdder;


@SuppressWarnings("rawtypes")
public class CustomListAdapter extends ArrayAdapter {
    private final String[] listEntries;

    private final Drawable[] icons;
    private final Activity context;
    private final int layout;


    public CustomListAdapter(Activity context, String[] countryNames, Drawable[] icons, int layout) {
        //noinspection unchecked
        super(context, R.layout.custom_list_item, countryNames);
        this.context = context;
        this.listEntries = countryNames;

        this.icons = icons;

        this.layout = layout;
    }


    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if (convertView == null)
            row = inflater.inflate(layout, null, true);
        TextView textViewCountry = row.findViewById(R.id.TV_ListEntry);

        ImageView imageFlag = row.findViewById(R.id.ImgV_icon);

        textViewCountry.setText(listEntries[position]);
        if (icons.length == 1) {
            imageFlag.setImageDrawable(icons[0]);
            imageFlag.setOnClickListener(v -> deletedialog(position));
        } else {
            imageFlag.setImageDrawable(icons[position]);
        }
        return row;
    }

    private void deletedialog(int position) {
        new AlertDialog.Builder(getContext())
                .setTitle("NFC is disabled")
                .setMessage("You must enable NFC to use this app.")

                .setPositiveButton("delete", (dialog, which) -> {


                    Const.taskcontainer.remove(position);
                    Intent intent = new Intent(getContext(), TaskAdder.class);

                    context.startActivity(intent);

                })

                .setNegativeButton("edit", (dialog, which) -> context.startActivity(new Intent(android.provider.Settings.ACTION_NFC_SETTINGS)))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}