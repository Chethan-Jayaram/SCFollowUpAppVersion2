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

import java.util.ArrayList;

public class MasterSpinnerAdapter extends ArrayAdapter<String> {

    private Context mContext;
    public MasterSpinnerAdapter(Context context,
                                ArrayList<String> mList)
    {
        super(context, 0, mList);
        this.mContext=context;
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
       String currentItem = getItem(position);
        if (currentItem != null) {
            tvValue.setText(currentItem);
        }

        if (currentItem.equalsIgnoreCase("Select Dispositions") || currentItem.equalsIgnoreCase("Select here")){
            tvValue.setTextColor(mContext.getResources().getColor(R.color.dark_gray_text));
        }else {
            tvValue.setTextColor(mContext.getResources().getColor(R.color.black));
        }
        return convertView;
    }
}
