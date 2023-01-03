package com.varsity.dgmdashboard.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.varsity.dgmdashboard.DGMDashboardApplication;
import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.adapter.DGMAssignedLeadsAdapter;
import com.varsity.dgmdashboard.databinding.ActivityAssignedLeadsBinding;
import com.varsity.dgmdashboard.model.GetDGMAssignedLeadsListResponse;
import com.varsity.dgmdashboard.utils.SnackBar;
import com.varsity.dgmdashboard.viewmodel.DGMDashboardViewModel;

import java.util.ArrayList;

public class AssignedLeadsActivity extends AppCompatActivity {

    private ActivityAssignedLeadsBinding mBinding;
    private View snakBarView;
    private DGMDashboardViewModel dashboardViewModel;
    private DGMAssignedLeadsAdapter assignedLeadsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_assigned_leads);
        init();
    }

    private void init() {
        snakBarView = findViewById(android.R.id.content);
        dashboardViewModel = new DGMDashboardViewModel(this);
        setToolbar();
        getAssignedLeadsList();
    }

    private void setToolbar() {
        mBinding.llToolbar.tvToolbarTitle.setText(getString(R.string.assign_leads));
        mBinding.llToolbar.llToolbarLeft.setVisibility(View.VISIBLE);
        mBinding.llToolbar.ivToolbarLeft.setImageResource(R.drawable.ic_back);

        mBinding.llToolbar.llToolbarRight.setVisibility(View.GONE);
        mBinding.llToolbar.ivToolbarRight.setImageResource(R.drawable.ic_notifications);

        mBinding.llToolbar.tvUserName.setVisibility(View.VISIBLE);
        String userName = DGMDashboardApplication.getPreferencesManager().getUserData().getUserName();
        mBinding.llToolbar.tvUserName.setText(userName.trim().substring(0, 1));
        mBinding.llToolbar.tvUserName.setOnClickListener(view -> startActivity(new Intent(AssignedLeadsActivity.this, ProfileActivity.class)));
        mBinding.llToolbar.ivToolbarLeft.setOnClickListener(view -> onBackPressed());
    }

    private void getAssignedLeadsList() {
        if (DGMDashboardApplication.getInstance().isNetworkAvailable()) {
            dashboardViewModel.getDGMAssignLeads(snakBarView).observe(this, responseModel -> {
                if (responseModel != null) {
                    if (responseModel.size() != 0) {
                        mBinding.rvAssignedLeads.setVisibility(View.VISIBLE);
                        mBinding.tvNoData.setVisibility(View.GONE);
                        assignedLeadsAdapter = new DGMAssignedLeadsAdapter(responseModel);
                        mBinding.rvAssignedLeads.setAdapter(assignedLeadsAdapter);

                        countTotalLeads(responseModel);
                    }else {
                        mBinding.rvAssignedLeads.setVisibility(View.GONE);
                        mBinding.tvNoData.setVisibility(View.VISIBLE);
                    }
                }
            });
        } else {
            SnackBar.showInternetError(this, snakBarView);
        }
    }

    private void countTotalLeads(ArrayList<GetDGMAssignedLeadsListResponse> list){
        int total=0;
        for(GetDGMAssignedLeadsListResponse data:list){
           total=total+data.getAssignCount();
        }

        mBinding.tvAllotmentCount.setText(""+total);
    }
}