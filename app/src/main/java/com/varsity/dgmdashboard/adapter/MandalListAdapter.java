package com.varsity.dgmdashboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.model.DistrictsListResponseModel;
import com.varsity.dgmdashboard.model.MandalListResponse;

import java.util.ArrayList;

public class MandalListAdapter extends ArrayAdapter<MandalListResponse> {

    public MandalListAdapter(Context context,
                             ArrayList<MandalListResponse> mList)
    {
        super(context, 0, mList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_sppinner_view, parent, false);
        }

        TextView tvValue = convertView.findViewById(R.id.tvValue);
        MandalListResponse currentItem = getItem(position);
        if (currentItem != null) {
            tvValue.setText(currentItem.getMandalName());
        }
        return convertView;
    }
}
