package com.varsity.dgmdashboard.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.activity.CallDialActivity;
import com.varsity.dgmdashboard.activity.DoorStepActivity;
import com.varsity.dgmdashboard.activity.ProAddNotesActivity;
import com.varsity.dgmdashboard.databinding.RowFollowUpListBinding;
import com.varsity.dgmdashboard.model.CallStatusListResponseModel;
import com.varsity.dgmdashboard.model.LeadStatusListResponseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FollowupListAdapter extends RecyclerView.Adapter<FollowupListAdapter.DataViewHolder> {

    private ArrayList<LeadStatusListResponseModel> list;

    public FollowupListAdapter(ArrayList<LeadStatusListResponseModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowFollowUpListBinding binding;
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.row_follow_up_list, parent, false);
        return new FollowupListAdapter.DataViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowupListAdapter.DataViewHolder holder, int position) {
        holder.mBinding.tvUserName.setText(list.get(position).getName());
        holder.mBinding.tvMobileNo.setText("" + list.get(position).getMobile());
        if (list.get(position).getName() != null) {
            holder.mBinding.ivUser.setText(list.get(position).getName().trim().substring(0, 1));
        }

        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        GradientDrawable bgShape = (GradientDrawable) holder.mBinding.ivUser.getBackground();
        bgShape.setColor(color);

        holder.mBinding.ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = holder.mBinding.getRoot().getContext();
                Intent intent=new Intent(context, CallDialActivity.class);
                intent.putExtra("MOBILE_NO",list.get(position).getMobile());
                intent.putExtra("Lead_ID",list.get(position).getLeadId());
                intent.putExtra("Assigned_ID",list.get(position).getAssignedTo());
                intent.putExtra("STUDENT_NAME",list.get(position).getName());
                intent.putExtra("PARENT_NAME",list.get(position).getParentName());
                context.startActivity(intent);
            }
        });

        holder.mBinding.ivNoteAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = holder.mBinding.getRoot().getContext();
                context.startActivity(new Intent(context, ProAddNotesActivity.class).putExtra("LEAD_DATA",new Gson().toJson(list.get(position))));
            }
        });

        holder.mBinding.ivDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = holder.mBinding.getRoot().getContext();
                context.startActivity(new Intent(context, DoorStepActivity.class).putExtra("LEAD_DATA",new Gson().toJson(list.get(position))));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;

    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        private RowFollowUpListBinding mBinding;

        public DataViewHolder(RowFollowUpListBinding mBinding) {
            super(mBinding.clMainView);
            this.mBinding = mBinding;

        }
    }

    public void addItems(List<LeadStatusListResponseModel> postItems) {
        list.addAll(postItems);
    }

    public void updateList(ArrayList<LeadStatusListResponseModel> newList){
        list = newList;
        notifyDataSetChanged();
    }
}
