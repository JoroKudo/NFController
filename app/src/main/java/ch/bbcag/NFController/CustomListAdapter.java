package ch.bbcag.NFController;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


@SuppressWarnings("rawtypes")
public class CustomListAdapter extends ArrayAdapter {
    private final String[] listEntries;

    private final Drawable[] icons;
    private final Activity act;
    private final int layout;


    public CustomListAdapter(Activity act, String[] countryNames, Drawable[] icons, int layout) {
        //noinspection unchecked
        super(act, R.layout.custom_list_item, countryNames);
        this.act = act;
        this.listEntries = countryNames;

        this.icons = icons;

        this.layout = layout;
    }


    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = act.getLayoutInflater();
        if (convertView == null)
            row = inflater.inflate(layout, null, true);
        TextView textViewCountry = row.findViewById(R.id.TV_ListEntry);

        ImageView imageFlag = row.findViewById(R.id.ImgV_icon);

        textViewCountry.setText(listEntries[position]);
        if (icons.length == 1) {
            imageFlag.setImageDrawable(icons[0]);
            imageFlag.setOnClickListener(v -> new Alerts().deletedialog(position, act));
        } else {
            imageFlag.setImageDrawable(icons[position]);
        }
        return row;
    }


}