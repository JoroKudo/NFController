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
public class CustomList extends ArrayAdapter {
    private final String[] listEntries;

    private final Drawable[] icons;
    private final Activity context;

    public CustomList(Activity context, String[] countryNames, Drawable[] icons) {
        //noinspection unchecked
        super(context, R.layout.custom_list_item, countryNames);
        this.context = context;
        this.listEntries = countryNames;

        this.icons = icons;

    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if (convertView == null)
            row = inflater.inflate(R.layout.custom_list_item, null, true);
        TextView textViewCountry = row.findViewById(R.id.TV_ListEntry);

        ImageView imageFlag = row.findViewById(R.id.ImgV_icon);

        textViewCountry.setText(listEntries[position]);

        imageFlag.setImageDrawable(icons[position]);
        return row;
    }
}