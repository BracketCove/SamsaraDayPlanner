package com.wiseassblog.samsaradayplanner.ui.managetaskview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wiseassblog.samsaradayplanner.R;

import java.util.List;

class IconSpinnerAdapter extends ArrayAdapter<IconSpinnerItem> {

    public IconSpinnerAdapter(Context context, List<IconSpinnerItem> items) {
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) convertView = LayoutInflater.from(getContext())
                .inflate(android.R.layout.simple_spinner_item, parent, false);

        IconSpinnerItem item = getItem(position);
        TextView itemText = convertView.findViewById(android.R.id.text1);

        if (item != null) {
            itemText.setText(item.getItemText());
        }

        return convertView;
    }

    @NonNull
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) convertView = LayoutInflater.from(getContext())
                .inflate(R.layout.spinner_icon_dropdown_item, parent, false);

        IconSpinnerItem item = getItem(position);

        ImageView itemIcon = convertView.findViewById(R.id.imv_spinner_item_icon);
        TextView itemText = convertView.findViewById(R.id.lbl_spinner_item);

        if (item != null) {
            itemIcon.setImageResource(item.getItemIconResId());
            itemText.setText(item.getItemText());
        }

        return convertView;
    }
}
