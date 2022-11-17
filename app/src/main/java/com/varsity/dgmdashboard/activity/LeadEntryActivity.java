package com.varsity.dgmdashboard.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.varsity.dgmdashboard.DGMDashboardApplication;
import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.adapter.DispositionsListAdapter;
import com.varsity.dgmdashboard.adapter.DistrictListAdapter;
import com.varsity.dgmdashboard.adapter.MandalListAdapter;
import com.varsity.dgmdashboard.adapter.MasterSpinnerAdapter;
import com.varsity.dgmdashboard.adapter.SchoolAdapter;
import com.varsity.dgmdashboard.adapter.SchoolListAdapter;
import com.varsity.dgmdashboard.databinding.ActivityLeadEntryBinding;
import com.varsity.dgmdashboard.model.DistrictsListResponseModel;
import com.varsity.dgmdashboard.model.GetPincodeResponse;
import com.varsity.dgmdashboard.model.MandalListResponse;
import com.varsity.dgmdashboard.model.SaveLeadEntryFormRequest;
import com.varsity.dgmdashboard.model.SchoolListResponseModel;
import com.varsity.dgmdashboard.model.StatusListResponseModel;
import com.varsity.dgmdashboard.utils.AppConstant;
import com.varsity.dgmdashboard.utils.Helper;
import com.varsity.dgmdashboard.utils.SnackBar;
import com.varsity.dgmdashboard.viewmodel.DashboardViewModel;

import java.util.ArrayList;
import java.util.List;

public class LeadEntryActivity extends AppCompatActivity {

    private ActivityLeadEntryBinding mBinding;
    private View snakBarView;
    private DashboardViewModel dashboardViewModel;
    private DispositionsListAdapter dispositionsListAdapter;
    private ArrayList<String> interestedCourseList = new ArrayList<>();
    private ArrayList<String> classList = new ArrayList<>();
    private ArrayList<String> yearList = new ArrayList<>();
    private ArrayList<DistrictsListResponseModel> districtsList = new ArrayList<>();
    private ArrayList<SchoolListResponseModel> schoolNameList = new ArrayList<>();
    private ArrayList<StatusListResponseModel> leadStatusList = new ArrayList<>();
    private ArrayList<MandalListResponse> mandalList = new ArrayList<>();
    private ArrayList<GetPincodeResponse.City> cityArrayList;
    private ArrayList<GetPincodeResponse.PinOffice> postOfficeList;
    private SchoolAdapter schoolListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_lead_entry);
        init();
    }

    private void init() {
        snakBarView = findViewById(android.R.id.content);
        dashboardViewModel = new DashboardViewModel(this);
        setToolbar();
        setDistrictData();
        setInterestedCourseData();
        setGender();
        setClassData();
        setYearData();
        setDispositions();


        mBinding.btnSave.setOnClickListener(view -> checkValidation());
        mBinding.edtDistrict.setOnItemClickListener((adapterView, view, i, l) -> {
            int districtID = 0;
            for (DistrictsListResponseModel data : districtsList) {
                if (data.getDistrictName().equalsIgnoreCase(mBinding.edtDistrict.getText().toString())) {
                    districtID = data.getDistrictId();
                    break;
                }
            }

            if (!mBinding.edtPincode.getText().toString().equalsIgnoreCase("")){
                mBinding.edtPincode.getText().clear();
                mBinding.edtState.getText().clear();
                mBinding.edtCity.getText().clear();
                mBinding.edtPostOffice.getText().clear();
            }
            mandalList.clear();
            mBinding.edtMandal.getText().clear();
            setMandalListData(String.valueOf(districtID));


        });

        mBinding.edtPincode.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() == 6) {
                    getPincodeData(s.toString());
                }else if(s.length()==0){
                    mBinding.edtCity.setText("");
                    mBinding.edtState.setText("");
                    mBinding.edtPostOffice.setText("");
                }else {
                    mBinding.edtCity.setText("");
                    mBinding.edtState.setText("");
                    mBinding.edtPostOffice.setText("");
                }
            }
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

    }

    private void getPincodeData(String pincode) {
        if (DGMDashboardApplication.getInstance().isNetworkAvailable()) {
            dashboardViewModel.getPinCodeList(snakBarView, pincode).observe(this, responseModel -> {
                if (responseModel != null) {
                    mBinding.edtState.setText(responseModel.getState());
                    mBinding.edtCity.setText(responseModel.getCities().get(0).getCityName());
                    mBinding.edtPostOffice.setText(responseModel.getPinOffices().get(0).getOfficeName());
                    mBinding.edtDistrict.setText(responseModel.getDistrictName());

                    mandalList.clear();
                    mBinding.edtMandal.getText().clear();
                    cityArrayList = responseModel.getCities();
                    postOfficeList = responseModel.getPinOffices();

                    ArrayAdapter<GetPincodeResponse.City> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, cityArrayList);
                    mBinding.edtCity.setAdapter(arrayAdapter);

                    ArrayAdapter<GetPincodeResponse.PinOffice> arrayAdapter1 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, postOfficeList);
                    mBinding.edtPostOffice.setAdapter(arrayAdapter1);

                    setMandalListData(String.valueOf(responseModel.getDistrictId()));
                }
            });
        } else {
            SnackBar.showInternetError(this, snakBarView);
        }
    }

    private void setToolbar() {
        mBinding.llToolbar.tvToolbarTitle.setText(R.string.lead_entry_form);
        mBinding.llToolbar.llToolbarLeft.setVisibility(View.VISIBLE);
        mBinding.llToolbar.ivToolbarLeft.setImageResource(R.drawable.ic_back);

        mBinding.llToolbar.llNotification.setVisibility(View.VISIBLE);
        String count = DGMDashboardApplication.getPreferencesManager().getStringValue(AppConstant.NOTIFICATION_COUNT);
        if (count != null) {
            mBinding.llToolbar.tvNotificationCount.setText("" + count);
        }
        mBinding.llToolbar.llNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LeadEntryActivity.this, NotificationListActivity.class));
            }
        });


        mBinding.llToolbar.tvUserName.setVisibility(View.VISIBLE);
        String userName = DGMDashboardApplication.getPreferencesManager().getUserData().getUserName();
        mBinding.llToolbar.tvUserName.setText(userName.trim().substring(0, 1));
        mBinding.llToolbar.tvUserName.setOnClickListener(view -> startActivity(new Intent(LeadEntryActivity.this, ProfileActivity.class)));

        mBinding.llToolbar.ivToolbarLeft.setOnClickListener(view -> onBackPressed());
    }

    private void setGender() {
        MasterSpinnerAdapter genderAdapter = new MasterSpinnerAdapter(this, AppConstant.getGenderList());
        mBinding.edtGender.setAdapter(genderAdapter);
    }

    private void setDistrictData() {
        if (DGMDashboardApplication.getInstance().isNetworkAvailable()) {
            dashboardViewModel.getDistrictListData(snakBarView).observe(this, responseModel -> {
                if (responseModel != null) {
                    if (responseModel.size() != 0) {
                        districtsList = new ArrayList<>();
                        districtsList = responseModel;
                        ArrayAdapter<DistrictsListResponseModel> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, districtsList);
                        mBinding.edtDistrict.setAdapter(arrayAdapter);
                    }
                }
            });
        } else {
            SnackBar.showInternetError(this, snakBarView);
        }


    }


    private void setSchoolNameData(String schoolname) {

        if (DGMDashboardApplication.getInstance().isNetworkAvailable()) {
            dashboardViewModel.getSchoolNameList(snakBarView, schoolname,false).observe(this, responseModel -> {
                if (responseModel != null) {
                    if (responseModel.size() != 0) {
                        schoolNameList = new ArrayList<>();
                        schoolNameList = responseModel;

                        if (schoolNameList!=null && schoolNameList.size()!=0){
                            List<String> list = new ArrayList<>();
                            for (SchoolListResponseModel dto : schoolNameList) {
                                list.add(dto.getSchoolName());
                            }

                            schoolListAdapter=new SchoolAdapter(LeadEntryActivity.this,android.R.layout.simple_dropdown_item_1line,list);
                           // ArrayAdapter<SchoolListResponseModel> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, schoolNameList);
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

    private void setMandalListData(String districtId) {
        if (!districtId.equalsIgnoreCase("")) {
            if (DGMDashboardApplication.getInstance().isNetworkAvailable()) {
                dashboardViewModel.getMandalsList(snakBarView, Long.valueOf(districtId), false).observe(this, responseModel -> {
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
                                        mBinding.llReminder.setVisibility(View.GONE);
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

    private void checkValidation() {

        if (mBinding.edtPhoneNo.getText().toString().equalsIgnoreCase("")) {
            SnackBar.showValidationError(LeadEntryActivity.this, snakBarView, "Please enter phone no");
            return;
        }
        if(mBinding.edtPhoneNo.getText().length() < 10){
            SnackBar.showValidationError(LeadEntryActivity.this, snakBarView, "Please enter valid phone no");
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
            SnackBar.showValidationError(LeadEntryActivity.this, snakBarView, "Please enter valid phone no");
            return ;
        }
        if (mBinding.edtFullName.getText().toString().equalsIgnoreCase("")) {
            SnackBar.showValidationError(LeadEntryActivity.this, snakBarView, "Please enter full name");
            return;
        }
        if (mBinding.edtParentsName.getText().toString().equalsIgnoreCase("")) {
            SnackBar.showValidationError(LeadEntryActivity.this, snakBarView, "Please enter parent name");
            return;
        }



        if (mBinding.edtGender.getText().toString().equalsIgnoreCase("")) {
            SnackBar.showValidationError(LeadEntryActivity.this, snakBarView, "Please select gender");
            return;
        }

        if (mBinding.edtCourse.getText().toString().equalsIgnoreCase("")) {
            SnackBar.showValidationError(LeadEntryActivity.this, snakBarView, "Please select Interest Course");
            return;
        }

        if (mBinding.edtClass.getText().toString().equalsIgnoreCase("")) {
            SnackBar.showValidationError(LeadEntryActivity.this, snakBarView, "Please select class");
            return;
        }

        if (mBinding.edtYear.getText().toString().equalsIgnoreCase("")) {
            SnackBar.showValidationError(LeadEntryActivity.this, snakBarView, "Please select year");
            return;
        }

        if (mBinding.edtDistrict.getText().toString().equalsIgnoreCase("")) {
            SnackBar.showValidationError(LeadEntryActivity.this, snakBarView, "Please enter district");
            return;
        }

        if (mBinding.edtMandal.getText().toString().equalsIgnoreCase("")) {
            SnackBar.showValidationError(LeadEntryActivity.this, snakBarView, "Please enter Mandal");
            return;
        }
        if (mBinding.edtPincode.getText().toString().equalsIgnoreCase("")) {
            SnackBar.showValidationError(LeadEntryActivity.this, snakBarView, "Please enter pincode");
            return;
        }

        if (mBinding.edtLandmark.getText().toString().equalsIgnoreCase("")) {
            SnackBar.showValidationError(LeadEntryActivity.this, snakBarView, "Please enter Landmark");
            return;
        }

        if (mBinding.edtSchool.getText().toString().equalsIgnoreCase("")) {
            SnackBar.showValidationError(LeadEntryActivity.this, snakBarView, "Please enter School");
            return;
        }

        if (mBinding.spDispositions.getSelectedItemPosition() <= 0) {
            SnackBar.showValidationError(LeadEntryActivity.this, snakBarView, "Please select Dispositions");
            return;
        }

        if (leadStatusList.get(mBinding.spDispositions.getSelectedItemPosition()).getStatusName().equalsIgnoreCase("Call Again")) {
            if (mBinding.tvReminderDate.getText().toString().equalsIgnoreCase("")) {
                SnackBar.showValidationError(LeadEntryActivity.this, snakBarView, "Please select reminder date");
                return;
            }
        }

        if (mBinding.edtComment.getText().toString().equalsIgnoreCase("")) {
            SnackBar.showValidationError(LeadEntryActivity.this, snakBarView, "Please enter Feedback");
            return;
        }

        SaveLeadEntryFormRequest request = new SaveLeadEntryFormRequest();
        request.setPhoneNo(mBinding.edtPhoneNo.getText().toString());
        request.setStudentName(mBinding.edtFullName.getText().toString());
        request.setParentName(mBinding.edtParentsName.getText().toString());
        int districtID = 0;
        for (DistrictsListResponseModel data : districtsList) {
            if (data.getDistrictName().equalsIgnoreCase(mBinding.edtDistrict.getText().toString())) {
                districtID = data.getDistrictId();
            }
        }
        request.setCommDistrict(districtID);
        request.setInterestedOn(mBinding.edtCourse.getText().toString());
        request.setStudentClass(mBinding.edtClass.getText().toString());
        request.setBatchYear(mBinding.edtYear.getText().toString());
        request.setPinCode(mBinding.edtPincode.getText().toString());

        int mandalId = 0;
        for (MandalListResponse data : mandalList) {
            if (data.getMandalName().equalsIgnoreCase(mBinding.edtMandal.getText().toString())) {
                mandalId = data.getMandalId();
            }
        }
        request.setCommMandal(mandalId);
        request.setAreaName(mBinding.edtLandmark.getText().toString());
        request.setAlternateMobile(mBinding.edtAlternetPhoneNo.getText().toString());
        request.setSchoolName(mBinding.edtSchool.getText().toString());

        int schoolId = 0;
        for (SchoolListResponseModel data : schoolNameList) {
            if (data.getSchoolName().equalsIgnoreCase(mBinding.edtSchool.getText().toString())) {
                schoolId = data.getSchoolId();
            }
        }
        request.setSchoolId(schoolId);
        request.setLandLineNo(mBinding.edtLandlineNo.getText().toString());
        request.setRelationStudent(mBinding.edtRelationWithStudent.getText().toString());
        request.setFeedback(mBinding.edtComment.getText().toString());
        if (!mBinding.tvReminderDate.getText().toString().equalsIgnoreCase("")) {
            request.setReminder(mBinding.tvReminderDate.getText().toString());
        }

        if (DGMDashboardApplication.getInstance().isNetworkAvailable()) {
            dashboardViewModel.saveLeadEntryData(snakBarView, request).observe(this, responseModel -> {
                if (responseModel != null) {
                    AlertDialog.Builder builderSaved = new AlertDialog.Builder(this, R.style.MaterialAlertDialogStyle);
                    builderSaved.setTitle("Lead Entry");
                    builderSaved.setMessage("Details successfully submitted");
                    builderSaved.setCancelable(false);
                    builderSaved.setPositiveButton("OK", (dialogInterface, i) -> {
                        setResult(333, new Intent());
                        finish();
                    });
                    builderSaved.show();
                    builderSaved.create();
                   // SnackBar.showSuccess(this, snakBarView, "Details Successfully saved");
                }
            });
        } else {
            SnackBar.showInternetError(this, snakBarView);
        }
    }
}