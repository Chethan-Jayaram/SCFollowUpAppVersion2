package com.varsity.dgmdashboard.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.databinding.RowAssignedLeadsBinding;
import com.varsity.dgmdashboard.databinding.RowNotesBinding;
import com.varsity.dgmdashboard.model.GetDGMAssignedLeadsListResponse;
import com.varsity.dgmdashboard.model.LeadStatusListResponseModel;

import java.util.ArrayList;

public class DGMAssignedLeadsAdapter extends RecyclerView.Adapter<DGMAssignedLeadsAdapter.DataViewHolder> {

    private ArrayList<GetDGMAssignedLeadsListResponse> list;

    public DGMAssignedLeadsAdapter(ArrayList<GetDGMAssignedLeadsListResponse> list) {
        this.list = list;
    }
    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowAssignedLeadsBinding binding;
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.row_assigned_leads, parent, false);
        return new DGMAssignedLeadsAdapter.DataViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DGMAssignedLeadsAdapter.DataViewHolder holder, int position) {
        holder.mBinding.tvTotalAllotmentCount.setText(""+list.get(position).getAssignCount());
        holder.mBinding.tvAreaName.setText(list.get(position).getDistrictName());
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        private RowAssignedLeadsBinding mBinding;

        public DataViewHolder(RowAssignedLeadsBinding mBinding) {
            super(mBinding.clMainView);
            this.mBinding = mBinding;

        }
    }
}
