package com.varsity.dgmdashboard.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.databinding.RowAssignLeadBinding;
import com.varsity.dgmdashboard.databinding.RowProDetailsBinding;
import com.varsity.dgmdashboard.model.GetDGMAssignedLeadsListResponse;
import com.varsity.dgmdashboard.model.GetProDetailsLeadResponse;

import java.util.ArrayList;

public class LeadAllocationAdapter extends RecyclerView.Adapter<LeadAllocationAdapter.DataViewHolder> {


    private ArrayList<GetProDetailsLeadResponse> list;

    public LeadAllocationAdapter(ArrayList<GetProDetailsLeadResponse> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowAssignLeadBinding binding;
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.row_assign_lead, parent, false);
        return new LeadAllocationAdapter.DataViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LeadAllocationAdapter.DataViewHolder holder, int position) {
        holder.mBinding.tvUserName.setText(list.get(position).getName());
        String userName = list.get(position).getName();
        holder.mBinding.ivUser.setText(userName.trim().substring(0, 1));

        int total = list.get(position).getCommunicatedLeads() + list.get(position).getCompletedLeads() + list.get(position).getPendingLeads();
        holder.mBinding.tvTotalAllotmentCount.setText("" + total);

        holder.mBinding.tvCommunicated.setText("Communicated " + list.get(position).getCommunicatedLeads());
        holder.mBinding.tvConverted.setText("Converted " + list.get(position).getCompletedLeads());
        holder.mBinding.tvPending.setText("Pending " + list.get(position).getPendingLeads());
    }

    @Override
    public int getItemCount() {
      return list != null ? list.size() : 0;
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        private RowAssignLeadBinding mBinding;

        public DataViewHolder(RowAssignLeadBinding mBinding) {
            super(mBinding.clMainView);
            this.mBinding = mBinding;
        }
    }
}
