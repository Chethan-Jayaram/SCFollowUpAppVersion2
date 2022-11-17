package com.varsity.dgmdashboard.adapter;

import android.graphics.Color;
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

public class ChartDispostionsListAdapter extends RecyclerView.Adapter<ChartDispostionsListAdapter.DataViewHolder> {

    private ArrayList<DGMDashboardResponse.Dispostions> list;

    public ChartDispostionsListAdapter(ArrayList<DGMDashboardResponse.Dispostions> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowChartCallStatusBinding binding;
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.row_chart_call_status, parent, false);
        return new ChartDispostionsListAdapter.DataViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChartDispostionsListAdapter.DataViewHolder holder, int position) {
        holder.mBinding.tvStatusName.setText(list.get(position).getDispostionName());
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
