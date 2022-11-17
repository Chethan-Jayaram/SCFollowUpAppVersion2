package com.varsity.dgmdashboard.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.varsity.dgmdashboard.DGMDashboardApplication;
import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.adapter.DispositionsListAdapter;
import com.varsity.dgmdashboard.adapter.MasterSpinnerAdapter;
import com.varsity.dgmdashboard.adapter.SchoolAdapter;
import com.varsity.dgmdashboard.databinding.ActivityCallDiaFeedbackAddresslBinding;
import com.varsity.dgmdashboard.model.CallDialFeedbackRequest;
import com.varsity.dgmdashboard.model.CallStatusListResponseModel;
import com.varsity.dgmdashboard.model.DistrictsListResponseModel;
import com.varsity.dgmdashboard.model.MandalListResponse;
import com.varsity.dgmdashboard.model.SchoolListResponseModel;
import com.varsity.dgmdashboard.model.StatusListResponseModel;
import com.varsity.dgmdashboard.utils.AppConstant;
import com.varsity.dgmdashboard.utils.Helper;
import com.varsity.dgmdashboard.utils.SnackBar;
import com.varsity.dgmdashboard.viewmodel.DashboardViewModel;

import java.util.ArrayList;
import java.util.List;

public class CallDialFeedbackAddressActivity extends AppCompatActivity {

    String strMobileNo = "";
    private ActivityCallDiaFeedbackAddresslBinding mBinding;
    private View snakBarView;
    private DashboardViewModel dashboardViewModel;
    private DispositionsListAdapter dispositionsListAdapter;
    private ArrayList<StatusListResponseModel> leadStatusList = new ArrayList<>();
    private ArrayList<String> selectList = new ArrayList<>();
    private int assignTo;
    private int leadId;
    private int leadStatusId = 1;
    private String source = "GATHERED BY PRO";
    private String studentName = "", parentName = "";

    private ArrayList<String> interestedCourseList = new ArrayList<>();
    private ArrayList<String> classList = new ArrayList<>();
    private ArrayList<String> yearList = new ArrayList<>();
    private ArrayList<DistrictsListResponseModel> districtsList = new ArrayList<>();
    private ArrayList<MandalListResponse> mandalList = new ArrayList<>();

    private ArrayList<SchoolListResponseModel> schoolNameList = new ArrayList<>();
    private SchoolAdapter schoolListAdapter;

    private CallStatusListResponseModel addressData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_call_dia_feedback_addressl);
        init();
    }

    private void init() {
        String address = getIntent().getStringExtra("Address_Data");
        addressData = new Gson().fromJson(address, CallStatusListResponseModel.class);
        setToolbar();

        strMobileNo = getIntent().getStringExtra("MOBILE_NO");
        leadId = getIntent().getIntExtra("Lead_ID", 0);
        assignTo = getIntent().getIntExtra("Assigned_ID", 0);
        studentName = getIntent().getStringExtra("STUDENT_NAME");
        parentName = getIntent().getStringExtra("PARENT_NAME");

        mBinding.tvStudentName.setText(studentName);
        mBinding.edtFullName.setText(studentName);
        mBinding.tvParentName.setText(parentName);
        mBinding.tvMobileNo.setText(strMobileNo);
        mBinding.edtPhoneNo.setText(""+strMobileNo);
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

                    if (ActivityCompat.checkSelfPermission(CallDialFeedbackAddressActivity.this,
                            Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                        call(strMobileNo);
                    } else {
                        final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};

                        ActivityCompat.requestPermissions(CallDialFeedbackAddressActivity.this, PERMISSIONS_STORAGE, 9);
                    }
                }

            }
        });

        mBinding.tvReminderDate.setOnClickListener(v -> Helper.openDatePicker(CallDialFeedbackAddressActivity.this, mBinding.tvReminderDate));

        setInterestedCourseData();
        setClassData();
        setYearData();
        setDistrictData();
        setGender();

        mBinding.edtDistrict.setOnItemClickListener((adapterView, view, i, l) -> {
            int districtID = 0;
            for (DistrictsListResponseModel data : districtsList) {
                if (data.getDistrictName().equalsIgnoreCase(mBinding.edtDistrict.getText().toString())) {
                    districtID = data.getDistrictId();
                    break;
                }
            }

            mandalList.clear();
            mBinding.edtMandal.getText().clear();
            setMandalListData(String.valueOf(districtID));


        });


        mBinding.edtSchool.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 3) {
                    setSchoolNameData(String.valueOf(s));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        setData();
    }

    private void setData(){
        if (addressData!=null){
            mBinding.edtFullName.setText(addressData.getStudentName());
            mBinding.edtParentsName.setText(addressData.getParentName());
            mBinding.edtPhoneNo.setText(String.valueOf(addressData.getMobileNo()));

            if (addressData.getGender().equalsIgnoreCase("Male")){
                mBinding.edtGender.setText("Male",false);
            }
            if (addressData.getGender().equalsIgnoreCase("Female")){
                mBinding.edtGender.setText("Female",false);
            }
            if (!addressData.getIntrestedCourse().equalsIgnoreCase("") || !addressData.getIntrestedCourse().equalsIgnoreCase("-")){
                mBinding.edtCourse.setText(addressData.getIntrestedCourse(),false);
            }

            if (!addressData.getStuClass().equalsIgnoreCase("") || !addressData.getStuClass().equalsIgnoreCase("-")){
                mBinding.edtClass.setText(addressData.getStuClass(),false);
            }

            if (!addressData.getBatchYear().equalsIgnoreCase("") || !addressData.getBatchYear().equalsIgnoreCase("-")){
                mBinding.edtYear.setText(addressData.getBatchYear(),false);
            }

            if (addressData.getLeadDistrict()!=0){
                String districtName = "";
                for (DistrictsListResponseModel data : districtsList) {
                    if (data.getDistrictId().equals(addressData.getLeadDistrict())) {
                        districtName = data.getDistrictName();
                    }
                }

                if (!districtName.equalsIgnoreCase("")){
                    mBinding.edtDistrict.setText(districtName,false);
                }
            }

            if (addressData.getMandal_id()!=0){
                String mandalName = "";
                for (MandalListResponse data : mandalList) {
                    if (data.getMandalId().equals(addressData.getMandal_id())) {
                        mandalName = data.getMandalName();
                    }
                }

                if (!mandalName.equalsIgnoreCase("")){
                    mBinding.edtMandal.setText(mandalName,false);
                }
            }


            if (!addressData.getAlternateMobile().equalsIgnoreCase("") || !addressData.getAlternateMobile().equalsIgnoreCase("-")){
                mBinding.edtAlternetPhoneNo.setText(addressData.getAlternateMobile());
            }

            if (!addressData.getHallTicketNo().equalsIgnoreCase("") || !addressData.getHallTicketNo().equalsIgnoreCase("-")){
                mBinding.edtHallTicketNo.setText(addressData.getHallTicketNo());
            }

            if (!addressData.getStudentName().equalsIgnoreCase("") || !addressData.getStudentName().equalsIgnoreCase("-")){
                mBinding.edtSchool.setText(addressData.getStudentName(),false);
            }

            if (!addressData.getRelationStudent().equalsIgnoreCase("") || !addressData.getRelationStudent().equalsIgnoreCase("-")){
                mBinding.edtRelationWithStudent.setText(addressData.getRelationStudent());
            }

            if (!addressData.getFeedback().equalsIgnoreCase("") || !addressData.getFeedback().equalsIgnoreCase("-")){
                mBinding.edtComment.setText(addressData.getFeedback());
            }

            if (!addressData.getDispositions().equalsIgnoreCase("") ||!addressData.getDispositions().equalsIgnoreCase("-")  ){
                int dispositionsPosition=0;
                if (leadStatusList!=null && leadStatusList.size()!=0){
                    for (int i=0;i<leadStatusList.size();i++){
                        if (addressData.getDispositions().equalsIgnoreCase(leadStatusList.get(i).getStatusName())){
                            dispositionsPosition=i;
                            break;
                        }
                    }

                    mBinding.spDispositions.setSelection(dispositionsPosition);
                }
            }
        }
    }

    private void setSchoolNameData(String schoolname) {

        if (DGMDashboardApplication.getInstance().isNetworkAvailable()) {
            dashboardViewModel.getSchoolNameList(snakBarView, schoolname, false).observe(this, responseModel -> {
                if (responseModel != null) {
                    if (responseModel.size() != 0) {
                        schoolNameList = new ArrayList<>();
                        schoolNameList = responseModel;

                        if (schoolNameList != null && schoolNameList.size() != 0) {
                            List<String> list = new ArrayList<>();
                            for (SchoolListResponseModel dto : schoolNameList) {
                                list.add(dto.getSchoolName());
                            }

                            schoolListAdapter = new SchoolAdapter(CallDialFeedbackAddressActivity.this, android.R.layout.simple_dropdown_item_1line, list);
                            mBinding.edtSchool.setThreshold(1);
                            mBinding.edtSchool.setAdapter(schoolListAdapter);
                            schoolListAdapter.notifyDataSetChanged();
                        }


                    }
                }
            });
        } else {
            SnackBar.showInternetError(this, snakBarView);
        }
    }

    private void setGender() {
        MasterSpinnerAdapter genderAdapter = new MasterSpinnerAdapter(this, AppConstant.getGenderList());
        mBinding.edtGender.setAdapter(genderAdapter);
    }

    private void setSelectList() {
        selectList = AppConstant.getSelectList();
        MasterSpinnerAdapter interestedCourseAdapter = new MasterSpinnerAdapter(this, selectList);
        mBinding.spStatus.setAdapter(interestedCourseAdapter);
        mBinding.spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != -1) {
                    if (selectList.get(position).equalsIgnoreCase("REMIND")) {
                        mBinding.llReminder.setVisibility(View.VISIBLE);
                    } else {
                        int intDes = mBinding.spDispositions.getSelectedItemPosition();
                        if (intDes != -1) {
                            if (leadStatusList.get(intDes).getStatusName().equalsIgnoreCase("CALL AGAIN")) {
                                mBinding.llReminder.setVisibility(View.VISIBLE);
                            } else {
                                mBinding.llReminder.setVisibility(View.GONE);
                            }
                        } else {
                            mBinding.llReminder.setVisibility(View.GONE);
                        }

                    }

                    if (selectList.get(position).equalsIgnoreCase("Answered")) {
                        mBinding.llStudentDetails.setVisibility(View.VISIBLE);
                    } else {
                        mBinding.llStudentDetails.setVisibility(View.GONE);
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
                                if (position != -1) {
                                    if (leadStatusList.get(position).getStatusName().equalsIgnoreCase("CALL AGAIN")) {
                                        mBinding.llReminder.setVisibility(View.VISIBLE);
                                    } else {
                                        int poStatus = mBinding.spStatus.getSelectedItemPosition();
                                        if (poStatus != -1) {
                                            if (selectList.get(poStatus).equalsIgnoreCase("REMIND")) {
                                                mBinding.llReminder.setVisibility(View.VISIBLE);
                                            } else {
                                                mBinding.llReminder.setVisibility(View.GONE);
                                            }
                                        } else {
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
            SnackBar.showValidationError(CallDialFeedbackAddressActivity.this, snakBarView, "Please select What happen on call");
            return;
        }

        if (selectList.get(mBinding.spStatus.getSelectedItemPosition()).equalsIgnoreCase("Answered")) {
            if (mBinding.edtFullName.getText().toString().equalsIgnoreCase("")) {
                SnackBar.showValidationError(CallDialFeedbackAddressActivity.this, snakBarView, "Please Enter full name");
                return;
            }

            if (mBinding.edtParentsName.getText().toString().equalsIgnoreCase("")) {
                SnackBar.showValidationError(CallDialFeedbackAddressActivity.this, snakBarView, "Please Enter parent name");
                return;
            }

            if (mBinding.edtPhoneNo.getText().toString().equalsIgnoreCase("")) {
                SnackBar.showValidationError(CallDialFeedbackAddressActivity.this, snakBarView, "Please Enter phone number");
                return;
            }

            if (mBinding.edtCourse.getText().toString().equalsIgnoreCase("")) {
                SnackBar.showValidationError(CallDialFeedbackAddressActivity.this, snakBarView, "Please select Interest Course");
                return;
            }

            if (mBinding.edtClass.getText().toString().equalsIgnoreCase("")) {
                SnackBar.showValidationError(CallDialFeedbackAddressActivity.this, snakBarView, "Please select class");
                return;
            }

            if (mBinding.edtYear.getText().toString().equalsIgnoreCase("")) {
                SnackBar.showValidationError(CallDialFeedbackAddressActivity.this, snakBarView, "Please select year");
                return;
            }

            if (mBinding.edtDistrict.getText().toString().equalsIgnoreCase("")) {
                SnackBar.showValidationError(CallDialFeedbackAddressActivity.this, snakBarView, "Please enter district");
                return;
            }

            if (mBinding.edtMandal.getText().toString().equalsIgnoreCase("")) {
                SnackBar.showValidationError(CallDialFeedbackAddressActivity.this, snakBarView, "Please enter Mandal");
                return;
            }

            if (mBinding.edtSchool.getText().toString().equalsIgnoreCase("")) {
                SnackBar.showValidationError(CallDialFeedbackAddressActivity.this, snakBarView, "Please enter School");
                return;
            }
        }

        if (mBinding.spDispositions.getSelectedItemPosition() <= 0) {
            SnackBar.showValidationError(CallDialFeedbackAddressActivity.this, snakBarView, "Please select Dispositions");
            return;
        }
        if (mBinding.edtComment.getText().toString().equalsIgnoreCase("")) {
            SnackBar.showValidationError(CallDialFeedbackAddressActivity.this, snakBarView, "Please enter comments");
            return;
        }

        if (leadStatusList.get(mBinding.spDispositions.getSelectedItemPosition()).getStatusName().equalsIgnoreCase("Call Again") || leadStatusList.get(mBinding.spDispositions.getSelectedItemPosition()).getStatusName().equalsIgnoreCase("CALL AGAIN")) {
            if (mBinding.tvReminderDate.getText().toString().equalsIgnoreCase("")) {
                SnackBar.showValidationError(CallDialFeedbackAddressActivity.this, snakBarView, "Please select reminder date");
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
        if (leadStatusList.get(mBinding.spDispositions.getSelectedItemPosition()).getStatusName().equalsIgnoreCase("Call Again")) {
            callDialFeedbackRequest.setReminder(mBinding.tvReminderDate.getText().toString());
        }
        if (DGMDashboardApplication.getInstance().isNetworkAvailable()) {
            dashboardViewModel.submitCallDialFeedback(snakBarView, callDialFeedbackRequest).observe(this, responseModel -> {
                AlertDialog.Builder builderSaved = new AlertDialog.Builder(CallDialFeedbackAddressActivity.this, R.style.MaterialAlertDialogStyle);
                builderSaved.setTitle("Feedback");
                builderSaved.setMessage("Details successfully submitted");
                builderSaved.setCancelable(false);
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


    private void setInterestedCourseData() {
        interestedCourseList = AppConstant.getInterestedCourseList();
        MasterSpinnerAdapter interestedCourseAdapter = new MasterSpinnerAdapter(this, interestedCourseList);
        mBinding.edtCourse.setAdapter(interestedCourseAdapter);
    }

    private void setClassData() {
        classList = AppConstant.getClassList();
        MasterSpinnerAdapter classAdapter = new MasterSpinnerAdapter(this, classList);
        mBinding.edtClass.setAdapter(classAdapter);
    }

    private void setYearData() {
        yearList = AppConstant.getYearList();
        MasterSpinnerAdapter classAdapter = new MasterSpinnerAdapter(this, yearList);
        mBinding.edtYear.setAdapter(classAdapter);
    }


    private void setDistrictData() {

        if (DGMDashboardApplication.getInstance().isNetworkAvailable()) {
            dashboardViewModel.getDistrictListData(snakBarView).observe(this, responseModel -> {
                if (responseModel != null) {
                    if (responseModel.size() != 0) {
                        districtsList = new ArrayList<>();
                        districtsList = responseModel;
                        ArrayAdapter<DistrictsListResponseModel> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, districtsList);
                        mBinding.edtDistrict.setThreshold(1);
                        mBinding.edtDistrict.setAdapter(arrayAdapter);

                    }
                }
            });
        } else {
            SnackBar.showInternetError(this, snakBarView);
        }
    }

    private void setMandalListData(String districtId) {
        if (!districtId.equalsIgnoreCase("")) {
            if (DGMDashboardApplication.getInstance().isNetworkAvailable()) {
                dashboardViewModel.getMandalsList(snakBarView, Long.valueOf(districtId), true).observe(this, responseModel -> {
                    if (responseModel != null) {
                        if (responseModel.size() != 0) {
                            mandalList = new ArrayList<>();
                            mandalList = responseModel;
                            ArrayAdapter<MandalListResponse> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, mandalList);
                            mBinding.edtMandal.setAdapter(arrayAdapter);
                        }
                    }
                });
            } else {
                SnackBar.showInternetError(this, snakBarView);
            }
        }

    }
}