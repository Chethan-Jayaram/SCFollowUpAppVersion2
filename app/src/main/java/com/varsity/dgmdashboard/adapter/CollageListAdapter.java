package com.varsity.dgmdashboard.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.databinding.RowCollageListBinding;
import com.varsity.dgmdashboard.databinding.RowHistoryBinding;
import com.varsity.dgmdashboard.model.CollageListResponseModel;
import com.varsity.dgmdashboard.model.LeadStatusListResponseModel;

import java.util.ArrayList;
import java.util.Random;

public class CollageListAdapter extends RecyclerView.Adapter<CollageListAdapter.DataViewHolder> {

    private ArrayList<CollageListResponseModel.CollageData> list;

    public CollageListAdapter(ArrayList<CollageListResponseModel.CollageData> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowCollageListBinding binding;
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.row_collage_list, parent, false);
        return new CollageListAdapter.DataViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CollageListAdapter.DataViewHolder holder, int position) {
        CollageListResponseModel.CollageData collageData=list.get(position);
        holder.mBinding.tvCollageName.setText(collageData.getCollege());
        holder.mBinding.tvCategory.setText(collageData.getCategory());
        holder.mBinding.tvMarks.setText(collageData.getMarks());
    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        private RowCollageListBinding mBinding;

        public DataViewHolder(RowCollageListBinding mBinding) {
            super(mBinding.clMainView);
            this.mBinding = mBinding;

        }
    }
}
