package com.varsity.dgmdashboard.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.databinding.RowCallStatusBinding;
import com.varsity.dgmdashboard.databinding.RowChartCallStatusBinding;
import com.varsity.dgmdashboard.model.DGMDashboardResponse;

import java.util.ArrayList;

public class ChartCallStatusListAdapter extends RecyclerView.Adapter<ChartCallStatusListAdapter.DataViewHolder> {

    private ArrayList<DGMDashboardResponse.CallStatus> list;

    public ChartCallStatusListAdapter(ArrayList<DGMDashboardResponse.CallStatus> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowChartCallStatusBinding binding;
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.row_chart_call_status, parent, false);
        return new ChartCallStatusListAdapter.DataViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChartCallStatusListAdapter.DataViewHolder holder, int position) {
        holder.mBinding.tvStatusName.setText(list.get(position).getCallStatusName());
        Log.e("COLOR", "" + list.get(position).getColourCode().trim());
        holder.mBinding.ivStatusColor.setBackgroundColor(Color.parseColor(list.get(position).getColourCode().trim()));

        float per = list.get(position).getPercentage().floatValue();
        holder.mBinding.tvCount.setText(String.format("%.2f", per)+"%");

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;

    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        private RowChartCallStatusBinding mBinding;

        public DataViewHolder(RowChartCallStatusBinding mBinding) {
            super(mBinding.clMainView);
            this.mBinding = mBinding;

        }
    }
}
