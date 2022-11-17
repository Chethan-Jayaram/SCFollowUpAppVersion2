package com.varsity.dgmdashboard.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.varsity.dgmdashboard.DGMDashboardApplication;
import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.adapter.DispositionsListAdapter;
import com.varsity.dgmdashboard.adapter.MasterSpinnerAdapter;
import com.varsity.dgmdashboard.databinding.ActivityProAddNotesBinding;
import com.varsity.dgmdashboard.model.LeadStatusListResponseModel;
import com.varsity.dgmdashboard.model.ProAddNoteRequest;
import com.varsity.dgmdashboard.model.StatusListResponseModel;
import com.varsity.dgmdashboard.utils.AppConstant;
import com.varsity.dgmdashboard.utils.SnackBar;
import com.varsity.dgmdashboard.viewmodel.DashboardViewModel;

import java.util.ArrayList;

public class ProAddNotesActivity extends AppCompatActivity {

    private ActivityProAddNotesBinding mBinding;
    private View snakBarView;
    private DashboardViewModel dashboardViewModel;
    private DispositionsListAdapter dispositionsListAdapter;
    private LeadStatusListResponseModel leadStatusListResponseModel;
    private ArrayList<StatusListResponseModel> leadStatusList = new ArrayList<>();
    private ArrayList<String> selectList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_pro_add_notes);
        init();
    }

    private void init() {
        String leadData = getIntent().getStringExtra("LEAD_DATA");
        leadStatusListResponseModel = new Gson().fromJson(leadData, LeadStatusListResponseModel.class);
        snakBarView = findViewById(android.R.id.content);
        dashboardViewModel = new DashboardViewModel(this);
        setToolbar();
        setDispositions();
        setSelectList();

        mBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
            }
        });

        if (leadStatusListResponseModel!=null){
            mBinding.edtStudentName.setText(leadStatusListResponseModel.getName());
            mBinding.edtPhoneNo.setText(leadStatusListResponseModel.getMobile());
            mBinding.edtParentsName.setText(leadStatusListResponseModel.getParentName());
        }
    }

    private void setToolbar() {
        mBinding.llToolbar.tvToolbarTitle.setText("Notes -" + leadStatusListResponseModel.getName());
        mBinding.llToolbar.llToolbarLeft.setVisibility(View.VISIBLE);
        mBinding.llToolbar.ivToolbarLeft.setImageResource(R.drawable.ic_back);

        mBinding.llToolbar.llToolbarRight.setVisibility(View.VISIBLE);
        mBinding.llToolbar.ivToolbarRight.setImageResource(R.drawable.ic_notifications);

        mBinding.llToolbar.tvUserName.setVisibility(View.VISIBLE);
        String userName = DGMDashboardApplication.getPreferencesManager().getUserData().getUserName();
        mBinding.llToolbar.tvUserName.setText(userName.trim().substring(0, 1));

        mBinding.llToolbar.ivToolbarLeft.setOnClickListener(view -> onBackPressed());
    }


    private void setDispositions() {
        leadStatusList.add(0, new StatusListResponseModel(0, false, "Select Dispositions"));
        dispositionsListAdapter = new DispositionsListAdapter(this, leadStatusList);
        mBinding.spDispositions.setAdapter(dispositionsListAdapter);
        if (DGMDashboardApplication.getInstance().isNetworkAvailable()) {
            dashboardViewModel.getLeadStatusList(snakBarView).observe(this, responseModel -> {
                if (responseModel != null) {
                    if (responseModel.size() != 0) {
                        leadStatusList = new ArrayList<>();
                        leadStatusList = responseModel;
                        leadStatusList.add(0, new StatusListResponseModel(0, false, "Select Dispositions"));
                        dispositionsListAdapter = new DispositionsListAdapter(this, leadStatusList);
                        mBinding.spDispositions.setAdapter(dispositionsListAdapter);

                        mBinding.spDispositions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position!=-1){
                                    if (leadStatusList.get(position).getStatusName().equalsIgnoreCase("CALL AGAIN")){
                                        mBinding.llReminder.setVisibility(View.VISIBLE);
                                    }else {
                                        int poStatus=mBinding.spSelect.getSelectedItemPosition();
                                        if (poStatus!=-1){
                                            if (selectList.get(poStatus).equalsIgnoreCase("REMIND")){
                                                mBinding.llReminder.setVisibility(View.VISIBLE);
                                            }else {
                                                mBinding.llReminder.setVisibility(View.GONE);
                                            }
                                        }else {
                                            mBinding.llReminder.setVisibility(View.GONE);
                                        }

                                    }
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                }
            });
        } else {
            SnackBar.showInternetError(this, snakBarView);
        }


    }

    private void setSelectList() {
        selectList = AppConstant.getSelectList();
        MasterSpinnerAdapter interestedCourseAdapter = new MasterSpinnerAdapter(this, selectList);
        mBinding.spSelect.setAdapter(interestedCourseAdapter);

        mBinding.spSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=-1){
                    if(selectList.get(position).equalsIgnoreCase("REMIND")){
                        mBinding.llReminder.setVisibility(View.VISIBLE);
                    }else {
                        int intDes=mBinding.spDispositions.getSelectedItemPosition();
                        if (intDes!=-1){
                            if (leadStatusList.get(intDes).getStatusName().equalsIgnoreCase("CALL AGAIN")){
                                mBinding.llReminder.setVisibility(View.VISIBLE);
                            }else {
                                mBinding.llReminder.setVisibility(View.GONE);
                            }
                        }else {
                            mBinding.llReminder.setVisibility(View.GONE);
                        }

                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void checkValidation() {
        if (mBinding.edtStudentName.getText().toString().equalsIgnoreCase("")) {
            SnackBar.showValidationError(ProAddNotesActivity.this, snakBarView, "Please enter student name");
            return;
        }

        if (mBinding.edtParentsName.getText().toString().equalsIgnoreCase("")) {
            SnackBar.showValidationError(ProAddNotesActivity.this, snakBarView, "Please enter parent name");
            return;
        }

        if (mBinding.edtPhoneNo.getText().toString().equalsIgnoreCase("")) {
            SnackBar.showValidationError(ProAddNotesActivity.this, snakBarView, "Please enter phone no");
            return;
        }
        if(mBinding.edtPhoneNo.getText().length() < 10){
            SnackBar.showValidationError(ProAddNotesActivity.this, snakBarView, "Please enter valid phone no");
            return;
        }
        String firstChar= String.valueOf(mBinding.edtPhoneNo.getText().charAt(0));
        boolean isMobileAllow=false;
        if (firstChar.equalsIgnoreCase("9")){
            isMobileAllow=true;
        }else if(firstChar.equalsIgnoreCase("8")){
            isMobileAllow=true;
        }else if(firstChar.equalsIgnoreCase("7")){
            isMobileAllow=true;
        }else if(firstChar.equalsIgnoreCase("6")){
            isMobileAllow=true;
        }
        if(!isMobileAllow){
            SnackBar.showValidationError(ProAddNotesActivity.this, snakBarView, "Please enter valid phone no");
            return ;
        }

        if (mBinding.spSelect.getSelectedItemPosition() <= 0) {
            SnackBar.showValidationError(ProAddNotesActivity.this, snakBarView, "Please select call status");
            return;
        }

        if (mBinding.spDispositions.getSelectedItemPosition() <= 0) {
            SnackBar.showValidationError(ProAddNotesActivity.this, snakBarView, "Please select Dispositions");
            return;
        }

        if (mBinding.edtComment.getText().toString().equalsIgnoreCase("")) {
            SnackBar.showValidationError(ProAddNotesActivity.this, snakBarView, "Please enter comments");
            return;
        }

        if (leadStatusList.get(mBinding.spDispositions.getSelectedItemPosition()).getStatusName().equalsIgnoreCase("Call Again") || leadStatusList.get(mBinding.spDispositions.getSelectedItemPosition()).getStatusName().equalsIgnoreCase("CALL AGAIN")){
            if (mBinding.tvReminderDate.getText().toString().equalsIgnoreCase("")){
                SnackBar.showValidationError(ProAddNotesActivity.this, snakBarView, "Please select reminder date");
                return;
            }
        }

        ProAddNoteRequest request = new ProAddNoteRequest();
        request.setAssignedTo(0);
        request.setComment(mBinding.edtComment.getText().toString());
        request.setFollowUpType(selectList.get(mBinding.spSelect.getSelectedItemPosition()));
        request.setFollowupStatus(false);
        request.setLeadStatusId(leadStatusList.get(mBinding.spDispositions.getSelectedItemPosition()).getId());
        request.setAssignedTo(leadStatusListResponseModel.getAssignedTo());
        request.setLeadId(leadStatusListResponseModel.getLeadId());
        if (!mBinding.tvReminderDate.getText().toString().equalsIgnoreCase("")){
            request.setReminder(mBinding.tvReminderDate.getText().toString());
        }
        if (DGMDashboardApplication.getInstance().isNetworkAvailable()) {
            dashboardViewModel.addProNotesData(snakBarView, request).observe(this, responseModel -> {
                AlertDialog.Builder builderSaved = new AlertDialog.Builder(ProAddNotesActivity.this, R.style.MaterialAlertDialogStyle);
                builderSaved.setTitle("Note");
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