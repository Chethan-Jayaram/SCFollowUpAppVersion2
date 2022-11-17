package com.varsity.dgmdashboard.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.varsity.dgmdashboard.DGMDashboardApplication;
import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.adapter.DispositionsListAdapter;
import com.varsity.dgmdashboard.adapter.MasterSpinnerAdapter;
import com.varsity.dgmdashboard.databinding.ActivityCallDialBinding;
import com.varsity.dgmdashboard.model.CallDialFeedbackRequest;
import com.varsity.dgmdashboard.model.StatusListResponseModel;
import com.varsity.dgmdashboard.utils.AppConstant;
import com.varsity.dgmdashboard.utils.Helper;
import com.varsity.dgmdashboard.utils.SnackBar;
import com.varsity.dgmdashboard.viewmodel.DashboardViewModel;

import java.util.ArrayList;

public class CallDialActivity extends AppCompatActivity {

    String strMobileNo = "";
    private ActivityCallDialBinding mBinding;
    private View snakBarView;
    private DashboardViewModel dashboardViewModel;
    private DispositionsListAdapter dispositionsListAdapter;
    private ArrayList<StatusListResponseModel> leadStatusList = new ArrayList<>();
    private ArrayList<String> selectList = new ArrayList<>();
    private int assignTo;
    private int leadId;
    private int leadStatusId = 1;
    private String source = "GATHERED BY PRO";
    private String studentName="",parentName="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_call_dial);
        init();
    }

    private void init() {
        setToolbar();

        strMobileNo = getIntent().getStringExtra("MOBILE_NO");
        leadId = getIntent().getIntExtra("Lead_ID", 0);
        assignTo = getIntent().getIntExtra("Assigned_ID", 0);
        studentName = getIntent().getStringExtra("STUDENT_NAME");
        parentName = getIntent().getStringExtra("PARENT_NAME");

        mBinding.tvStudentName.setText(studentName);
        mBinding.tvParentName.setText(parentName);
        mBinding.tvMobileNo.setText(strMobileNo);
        snakBarView = findViewById(android.R.id.content);
        dashboardViewModel = new DashboardViewModel(this);
        setDispositions();
        setSelectList();

        if (Build.VERSION.SDK_INT < 23) {
            call(strMobileNo);
        } else {

            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                call(strMobileNo);
            } else {
                final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};

                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 9);
            }
        }

        mBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });

        mBinding.btnRedial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT < 23) {
                    call(strMobileNo);
                } else {

                    if (ActivityCompat.checkSelfPermission(CallDialActivity.this,
                            Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                        call(strMobileNo);
                    } else {
                        final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};

                        ActivityCompat.requestPermissions(CallDialActivity.this, PERMISSIONS_STORAGE, 9);
                    }
                }

            }
        });

        mBinding.tvReminderDate.setOnClickListener(v -> Helper.openDatePicker(CallDialActivity.this, mBinding.tvReminderDate));
    }

    private void setSelectList() {
        selectList = AppConstant.getSelectList();
        MasterSpinnerAdapter interestedCourseAdapter = new MasterSpinnerAdapter(this, selectList);
        mBinding.spStatus.setAdapter(interestedCourseAdapter);
        mBinding.spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean permissionGranted = false;
        switch (requestCode) {
            case 9:
                permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (permissionGranted) {
            call(strMobileNo);
        } else {
            Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }


    private void call(String mobileNumber) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + mobileNumber));
            startActivity(callIntent);
        } else {
            Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }


    private void setToolbar() {
        mBinding.llToolbar.tvToolbarTitle.setText("Dial Call");
        mBinding.llToolbar.llToolbarLeft.setVisibility(View.VISIBLE);
        mBinding.llToolbar.ivToolbarLeft.setImageResource(R.drawable.ic_back);

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
                                        int poStatus=mBinding.spStatus.getSelectedItemPosition();
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

    private void checkValidation() {
        if (mBinding.spStatus.getSelectedItemPosition() <= 0) {
            SnackBar.showValidationError(CallDialActivity.this, snakBarView, "Please select What happen on call");
            return;
        }
        if (mBinding.spDispositions.getSelectedItemPosition() <= 0) {
            SnackBar.showValidationError(CallDialActivity.this, snakBarView, "Please select Dispositions");
            return;
        }
        if (mBinding.edtComment.getText().toString().equalsIgnoreCase("")) {
            SnackBar.showValidationError(CallDialActivity.this, snakBarView, "Please enter comments");
            return;
        }

        if (leadStatusList.get(mBinding.spDispositions.getSelectedItemPosition()).getStatusName().equalsIgnoreCase("Call Again") || leadStatusList.get(mBinding.spDispositions.getSelectedItemPosition()).getStatusName().equalsIgnoreCase("CALL AGAIN")){
            if (mBinding.tvReminderDate.getText().toString().equalsIgnoreCase("")){
                SnackBar.showValidationError(CallDialActivity.this, snakBarView, "Please select reminder date");
                return;
            }
        }

        CallDialFeedbackRequest callDialFeedbackRequest = new CallDialFeedbackRequest();
        callDialFeedbackRequest.setMobileNo(strMobileNo);
        callDialFeedbackRequest.setAssignedTo(assignTo);
        callDialFeedbackRequest.setLeadId(leadId);
        callDialFeedbackRequest.setLeadStatusId(leadStatusId);
        callDialFeedbackRequest.setSource(source);
        callDialFeedbackRequest.setComment(mBinding.edtComment.getText().toString());
        callDialFeedbackRequest.setFollowUpType(leadStatusList.get(mBinding.spDispositions.getSelectedItemPosition()).getStatusName());
        callDialFeedbackRequest.setFeedBack(selectList.get(mBinding.spStatus.getSelectedItemPosition()));
       if (leadStatusList.get(mBinding.spDispositions.getSelectedItemPosition()).getStatusName().equalsIgnoreCase("Call Again")){
           callDialFeedbackRequest.setReminder(mBinding.tvReminderDate.getText().toString());
       }
        if (DGMDashboardApplication.getInstance().isNetworkAvailable()) {
            dashboardViewModel.submitCallDialFeedback(snakBarView, callDialFeedbackRequest).observe(this, responseModel -> {
                AlertDialog.Builder builderSaved = new AlertDialog.Builder(CallDialActivity.this, R.style.MaterialAlertDialogStyle);
                builderSaved.setTitle("Feedback");
                builderSaved.setMessage("Details successfully submitted");
                builderSaved.setPositiveButton("OK", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    onBackPressed();
                });
                builderSaved.show();
                builderSaved.create();
            });
        } else {
            SnackBar.showInternetError(this, snakBarView);
        }

    }

}