package com.varsity.dgmdashboard.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.activity.AddressCollectionActivity;
import com.varsity.dgmdashboard.activity.CallDialFeedbackAddressActivity;
import com.varsity.dgmdashboard.databinding.RowAddressListBinding;
import com.varsity.dgmdashboard.listener.PhoneCallListener;
import com.varsity.dgmdashboard.model.CallStatusListResponseModel;
import com.varsity.dgmdashboard.model.GetProDetailsLeadResponse;

import java.util.ArrayList;
import java.util.Random;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.DataViewHolder> {

    private ArrayList<CallStatusListResponseModel> list;


    public AddressListAdapter(ArrayList<CallStatusListResponseModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowAddressListBinding binding;
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.row_address_list, parent, false);
        return new AddressListAdapter.DataViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressListAdapter.DataViewHolder holder, int position) {
        holder.mBinding.tvUserName.setText(list.get(position).getStudentName());
        holder.mBinding.tvMobileNo.setText("" + list.get(position).getMobileNo());
        holder.mBinding.ivUser.setText(list.get(position).getStudentName().trim().substring(0, 1));
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        GradientDrawable bgShape = (GradientDrawable) holder.mBinding.ivUser.getBackground();
        bgShape.setColor(color);

        holder.mBinding.ivAdd.setOnClickListener(view -> {
            String name = list.get(position).getStudentName();
            String mobileNo = String.valueOf(list.get(position).getMobileNo());
            Context context = holder.mBinding.getRoot().getContext();
            String addressData=new Gson().toJson(list.get(position));
            context.startActivity(new Intent(context, AddressCollectionActivity.class).putExtra("Address_Data", addressData));
        });

        holder.mBinding.ivCall.setOnClickListener(v -> {
            Context context = holder.mBinding.getRoot().getContext();
            Intent intent=new Intent(context, CallDialFeedbackAddressActivity.class);
            intent.putExtra("MOBILE_NO","" + list.get(position).getMobileNo());
            intent.putExtra("Lead_ID",list.get(position).getLeadId());
            intent.putExtra("STUDENT_NAME",list.get(position).getStudentName());
            intent.putExtra("PARENT_NAME",list.get(position).getParentName());
            String addressData=new Gson().toJson(list.get(position));
            intent.putExtra("Address_Data",addressData);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;

    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        private RowAddressListBinding mBinding;

        public DataViewHolder(RowAddressListBinding mBinding) {
            super(mBinding.clMainView);
            this.mBinding = mBinding;

        }
    }

    public void updateList(ArrayList<CallStatusListResponseModel> newList){
        list = newList;
        notifyDataSetChanged();
    }
}
