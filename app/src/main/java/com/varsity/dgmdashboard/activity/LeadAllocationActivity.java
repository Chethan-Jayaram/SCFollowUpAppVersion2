package com.varsity.dgmdashboard.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.varsity.dgmdashboard.DGMDashboardApplication;
import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.adapter.DistrictListAdapter;
import com.varsity.dgmdashboard.adapter.LeadAllocationAdapter;
import com.varsity.dgmdashboard.databinding.ActivityLeadAllocationBinding;
import com.varsity.dgmdashboard.model.AssignLeadRequest;
import com.varsity.dgmdashboard.model.GetProDetailsLeadResponse;
import com.varsity.dgmdashboard.utils.SnackBar;
import com.varsity.dgmdashboard.viewmodel.DGMDashboardViewModel;

import java.util.ArrayList;

public class LeadAllocationActivity extends AppCompatActivity {

    private ActivityLeadAllocationBinding mBinding;
    private View snakBarView;
    private DGMDashboardViewModel dashboardViewModel;
    private LeadAllocationAdapter leadAllocationAdapter;
    private DistrictListAdapter districtListAdapter;
    private AssignLeadRequest request;
    private int totalLeadCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_lead_allocation);
        init();
    }

    private void init() {
        request = new AssignLeadRequest();

        snakBarView = findViewById(android.R.id.content);
        dashboardViewModel = new DGMDashboardViewModel(this);
        setToolbar();
        getAreaList();

    }

    private void setToolbar() {
        mBinding.llToolbar.tvToolbarTitle.setText(R.string.lead_allocation);
        mBinding.llToolbar.llToolbarLeft.setVisibility(View.VISIBLE);
        mBinding.llToolbar.ivToolbarLeft.setImageResource(R.drawable.ic_back);

        mBinding.llToolbar.llToolbarRight.setVisibility(View.VISIBLE);
        mBinding.llToolbar.ivToolbarRight.setImageResource(R.drawable.ic_notifications);

        mBinding.llToolbar.ivToolbarLeft.setOnClickListener(view -> onBackPressed());

        mBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalLeadCount != 0 && request != null) {
                    submitLead();
                } else {
                    SnackBar.showError(LeadAllocationActivity.this, snakBarView, "No Leads found");
                }
            }
        });
    }


    private void setLeadData() {
        if (DGMDashboardApplication.getInstance().isNetworkAvailable()) {
            dashboardViewModel.getDGMProDetailsData(snakBarView).observe(this, responseModel -> {
                if (responseModel != null) {
                    if (responseModel.size() != 0) {
                        mBinding.rvLead.setVisibility(View.VISIBLE);
                        mBinding.tvNoData.setVisibility(View.GONE);
                        leadAllocationAdapter = new LeadAllocationAdapter(responseModel);
                        mBinding.rvLead.setAdapter(leadAllocationAdapter);
                        mBinding.btnSubmit.setEnabled(true);
                        countTotalLeads(responseModel);
                    } else {
                        mBinding.rvLead.setVisibility(View.GONE);
                        mBinding.tvNoData.setVisibility(View.VISIBLE);
                        mBinding.btnSubmit.setEnabled(false);
                    }
                }
            });
        } else {
            SnackBar.showInternetError(this, snakBarView);
        }
    }

    private void countTotalLeads(ArrayList<GetProDetailsLeadResponse> list) {
        int total = 0;
        ArrayList<AssignLeadRequest.AssignTo> assignToArrayList = new ArrayList<>();
        for (GetProDetailsLeadResponse data : list) {
            AssignLeadRequest.AssignTo assignTo = new AssignLeadRequest.AssignTo();
            assignTo.setEmpID(data.getAssignedTo());
            assignToArrayList.add(assignTo);

            int newTotal = data.getCommunicatedLeads() + data.getCompletedLeads() + data.getPendingLeads();
            total = total + newTotal;
        }

        request.setAssignTo(assignToArrayList);
        request.setTotalLead(total);
        totalLeadCount = total;
        mBinding.tvAllotmentCount.setText("" + total);
    }

    private void getAreaList() {
        if (DGMDashboardApplication.getInstance().isNetworkAvailable()) {
            dashboardViewModel.getDistrictListData(snakBarView).observe(this, responseModel -> {
                if (responseModel != null) {
                    if (responseModel.size() != 0) {
                        districtListAdapter = new DistrictListAdapter(this, responseModel);
                        mBinding.spArea.setAdapter(districtListAdapter);
                        setLeadData();
                    } else {
                        mBinding.llArea.setVisibility(View.GONE);
                    }
                } else {
                    mBinding.llArea.setVisibility(View.GONE);
                }
            });
        } else {
            SnackBar.showInternetError(this, snakBarView);
        }
    }

    private void submitLead() {
        if (DGMDashboardApplication.getInstance().isNetworkAvailable()) {

            dashboardViewModel.submitLead(snakBarView, request).observe(this, responseModel -> {
                AlertDialog.Builder builderSaved = new AlertDialog.Builder(this, R.style.MaterialAlertDialogStyle);
                builderSaved.setTitle("Lead Allocation");
                builderSaved.setMessage("Details successfully submitted");
                builderSaved.setCancelable(false);
                builderSaved.setPositiveButton("OK", (dialogInterface, i) -> {
                    setResult(333, new Intent());
                    finish();
                });
                builderSaved.show();
                builderSaved.create();
            });
        } else {
            SnackBar.showInternetError(this, snakBarView);
        }
    }

}