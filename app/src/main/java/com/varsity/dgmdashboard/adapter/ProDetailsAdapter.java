package com.varsity.dgmdashboard.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.varsity.dgmdashboard.DGMDashboardApplication;
import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.activity.CallDialActivity;
import com.varsity.dgmdashboard.activity.MainActivity;
import com.varsity.dgmdashboard.activity.NoteListActivity;
import com.varsity.dgmdashboard.databinding.RowProDetailsBinding;
import com.varsity.dgmdashboard.listener.PhoneCallListener;
import com.varsity.dgmdashboard.model.GetProDetailsLeadResponse;

import java.util.ArrayList;

public class ProDetailsAdapter extends RecyclerView.Adapter<ProDetailsAdapter.DataViewHolder> {

    private ArrayList<GetProDetailsLeadResponse> list;
    private PhoneCallListener listener;

    public ProDetailsAdapter(ArrayList<GetProDetailsLeadResponse> list,PhoneCallListener listener) {
        this.list = list;
        this.listener=listener;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowProDetailsBinding binding;
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.row_pro_details, parent, false);
        return new ProDetailsAdapter.DataViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProDetailsAdapter.DataViewHolder holder, int position) {

        holder.mBinding.tvUserName.setText(list.get(position).getName());

        String userName = list.get(position).getName();
        holder.mBinding.ivUser.setText(userName.trim().substring(0, 1));

        int total = list.get(position).getCommunicatedLeads() + list.get(position).getCompletedLeads() + list.get(position).getPendingLeads();
        holder.mBinding.tvTotalAllotmentCount.setText("" + total);

        holder.mBinding.tvCommunicated.setText("Communicated " + list.get(position).getCommunicatedLeads());
        holder.mBinding.tvConverted.setText("Converted " + list.get(position).getCompletedLeads());
        holder.mBinding.tvPending.setText("Pending " + list.get(position).getPendingLeads());

        holder.mBinding.ivNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String leadsData=new Gson().toJson(list.get(position));
                holder.mBinding.getRoot().getContext().startActivity(new Intent(holder.mBinding.getRoot().getContext(), NoteListActivity.class).putExtra("DATA",leadsData));
            }
        });

        holder.mBinding.ivCall.setOnClickListener(v -> {
            Context context = holder.mBinding.getRoot().getContext();
            Intent intent=new Intent(context, CallDialActivity.class);
            intent.putExtra("MOBILE_NO",list.get(position).getMobile());
            intent.putExtra("Lead_ID",list.get(position).getId());
            intent.putExtra("Assigned_ID",list.get(position).getAssignedTo());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;

    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        private RowProDetailsBinding mBinding;

        public DataViewHolder(RowProDetailsBinding mBinding) {
            super(mBinding.clMainView);
            this.mBinding = mBinding;
        }
    }

    public void updateList(ArrayList<GetProDetailsLeadResponse> newList){
        list = newList;
        notifyDataSetChanged();
    }
}
