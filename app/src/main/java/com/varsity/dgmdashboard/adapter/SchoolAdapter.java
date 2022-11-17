package com.varsity.dgmdashboard.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SchoolAdapter extends ArrayAdapter<String> {
    private final Context context;
    private List<String> schoolDTOS = new ArrayList<>();

    public SchoolAdapter(@NonNull Context context, int resource, @NonNull List<String> schoolDTOS) {
        super(context, resource, schoolDTOS);
        this.context = context;
        this.schoolDTOS = schoolDTOS;

    }

    @Override
    public int getCount() {
        return schoolDTOS.size();
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(context.getResources().getColor(android.R.color.black));
        label.setText((CharSequence) schoolDTOS.get(position));
        label.setPadding(20, 20, 20, 20);
        return label;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return schoolDTOS.get(position);


    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(context.getResources().getColor(android.R.color.black));
        label.setPadding(20, 20, 20, 20);
        label.setText(schoolDTOS.get(position));
        return label;
    }




}
