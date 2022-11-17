package com.varsity.dgmdashboard.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.varsity.dgmdashboard.DGMDashboardApplication;
import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.adapter.DispositionsListAdapter;
import com.varsity.dgmdashboard.databinding.ActivityDoorStepBinding;
import com.varsity.dgmdashboard.model.LeadStatusListResponseModel;
import com.varsity.dgmdashboard.model.SaveDoorStepData;
import com.varsity.dgmdashboard.model.StatusListResponseModel;
import com.varsity.dgmdashboard.utils.Helper;
import com.varsity.dgmdashboard.utils.SnackBar;
import com.varsity.dgmdashboard.viewmodel.DashboardViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DoorStepActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION = 200;
    private Calendar date;
    private Calendar dateOut;
    private ActivityDoorStepBinding mBinding;
    private View snakBarView;
    private DashboardViewModel dashboardViewModel;
    private DispositionsListAdapter dispositionsListAdapter;
    private LeadStatusListResponseModel leadStatusListResponseModel;
    private ArrayList<StatusListResponseModel> leadStatusList = new ArrayList<>();
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private LocationManager locationManager;
    private FusedLocationProviderClient fusedLocationClient;
    private boolean locationCaptured = false;
    private String inTime = "";
    private String outTime = "";
    private double latitude = 0.0, longitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_door_step);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        init();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!locationCaptured) {
            requestLastknownLocation();
        }
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Enable GPS").setCancelable(false).setPositiveButton("Yes", (dialog, which) -> {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//                dialog.cancel();
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void requestLastknownLocation() {
        locationManager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getLocation();
        }
    }

    private void init() {
        String leadData = getIntent().getStringExtra("LEAD_DATA");
        leadStatusListResponseModel = new Gson().fromJson(leadData, LeadStatusListResponseModel.class);
        snakBarView = findViewById(android.R.id.content);
        dashboardViewModel = new DashboardViewModel(this);
        setToolbar();
        setDispositions();

        mBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
            }
        });

        mBinding.tvInTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimePicker(mBinding.tvInTime);
            }
        });

        mBinding.tvOutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOutDateTimePicker(mBinding.tvOutTime);
            }
        });

        requestLocationUpdatesWhenLastknownLocationNull();

        if (leadStatusListResponseModel != null) {
            mBinding.edtStudentName.setText(leadStatusListResponseModel.getName());
            mBinding.edtPhoneNo.setText(leadStatusListResponseModel.getMobile());
            if (leadStatusListResponseModel.getParentName()!=null){
                mBinding.edtParentsName.setText(leadStatusListResponseModel.getParentName());
            }

        }

    }

    private void setToolbar() {
        mBinding.llToolbar.tvToolbarTitle.setText("Door step");
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

    private void requestLocationUpdatesWhenLastknownLocationNull() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(2 * 1000);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        String latitude = String.valueOf(location.getLatitude());
                        String longitude = String.valueOf(location.getLongitude());
                        mBinding.tvLatitude.setText("Latitude  " + latitude);
                        mBinding.tvLongitude.setText("Longitude  " + longitude);
                        fusedLocationClient.removeLocationUpdates(locationCallback);
                        locationCaptured = true;
                    }
                }
            }
        };
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new com.google.android.gms.tasks.OnSuccessListener<Location>() {
                @SuppressLint("MissingPermission")
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        // Logic to handle location object
                        double lat = location.getLatitude();
                        double longi = location.getLongitude();
                        mBinding.tvLatitude.setText("Latitude  " + lat);
                        mBinding.tvLongitude.setText("Longitude  " + longi);
                        fusedLocationClient.removeLocationUpdates(locationCallback);
                        locationCaptured = true;
                    } else {
                        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());

                    }
                }
            });


        }
    }

    public void showDateTimePicker(AppCompatTextView textView) {
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();
        new TimePickerDialog(DoorStepActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                date.set(Calendar.MINUTE, minute);
                SimpleDateFormat df = new SimpleDateFormat("hh:mm a", Locale.US);
                if (mBinding.tvOutTime.getText().toString().equalsIgnoreCase("")){
                    String time = df.format(date.getTime());
                    textView.setText(time);
                }else {
                    if (dateOut!=null){
                        if(date.getTimeInMillis() > dateOut.getTimeInMillis()){
                            SnackBar.showValidationError(DoorStepActivity.this, snakBarView, "Please Select valid time");
                        }else{
                            String time = df.format(date.getTime());
                            textView.setText(time);
                        }
                    }else {
                        String time = df.format(date.getTime());
                        textView.setText(time);
                    }
                }

            }
        }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
    }


    public void showOutDateTimePicker(AppCompatTextView textView) {
        final Calendar currentDate = Calendar.getInstance();
        dateOut = Calendar.getInstance();
        if (mBinding.tvInTime.getText().toString().equalsIgnoreCase("")){
            date=null;
        }
        new TimePickerDialog(DoorStepActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                dateOut.set(Calendar.HOUR_OF_DAY, hourOfDay);
                dateOut.set(Calendar.MINUTE, minute);
                SimpleDateFormat df = new SimpleDateFormat("hh:mm a", Locale.US);
                if (mBinding.tvInTime.getText().toString().equalsIgnoreCase("")){
                    String time = df.format(dateOut.getTime());
                    textView.setText(time);
                }else {
                    if (date!=null){
                        if(date.getTimeInMillis() > dateOut.getTimeInMillis()){
                            SnackBar.showValidationError(DoorStepActivity.this, snakBarView, "Please Select valid time");
                        }else{
                            String time = df.format(dateOut.getTime());
                            textView.setText(time);
                        }
                    }else {
                        String time = df.format(dateOut.getTime());
                        textView.setText(time);
                    }
                }

            }
        }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
    }


    private void checkValidation() {
        inTime = mBinding.tvInTime.getText().toString();
        outTime = mBinding.tvOutTime.getText().toString();

        if (mBinding.edtStudentName.getText().toString().equalsIgnoreCase("")) {
            SnackBar.showValidationError(DoorStepActivity.this, snakBarView, "Please enter student name");
            return;
        }
        if (mBinding.edtPhoneNo.getText().toString().equalsIgnoreCase("")) {
            SnackBar.showValidationError(DoorStepActivity.this, snakBarView, "Please enter mobile no");
            return;
        }
        if (mBinding.edtPhoneNo.getText().toString().length() < 10) {
            SnackBar.showValidationError(DoorStepActivity.this, snakBarView, "Please enter valid mobile no");
            return;
        }

        if (mBinding.edtParentsName.getText().toString().equalsIgnoreCase("")) {
            SnackBar.showValidationError(DoorStepActivity.this, snakBarView, "Please enter parent name");
            return;
        }

        if (inTime.equalsIgnoreCase("")) {
            SnackBar.showValidationError(DoorStepActivity.this, snakBarView, "Please select In Time");
            return;
        }

        if (outTime.equalsIgnoreCase("")) {
            SnackBar.showValidationError(DoorStepActivity.this, snakBarView, "Please select Out Time");
            return;
        }

        if (mBinding.spDispositions.getSelectedItemPosition() <= 0) {
            SnackBar.showValidationError(DoorStepActivity.this, snakBarView, "Please select Dispositions");
            return;
        }
        if (leadStatusList.get(mBinding.spDispositions.getSelectedItemPosition()).getStatusName().equalsIgnoreCase("Call Again")){
            if (mBinding.tvReminderDate.getText().toString().equalsIgnoreCase("")){
                SnackBar.showValidationError(DoorStepActivity.this, snakBarView, "Please select reminder date");
                return;
            }
        }

        if (mBinding.edtComment.getText().toString().equalsIgnoreCase("")) {
            SnackBar.showValidationError(DoorStepActivity.this, snakBarView, "Please enter comments");
            return;
        }
        SaveDoorStepData request = new SaveDoorStepData();
        request.setCheckIn(inTime);
        request.setCheckOut(outTime);
        request.setAssignedTo(leadStatusListResponseModel.getAssignedTo());
        request.setComment(mBinding.edtComment.getText().toString());
        request.setLeadId(Long.valueOf(leadStatusListResponseModel.getLeadId()));
        request.setMobileNo(mBinding.edtPhoneNo.getText().toString());
        request.setLat(latitude);
        request.setLog(longitude);
        request.setSource(leadStatusListResponseModel.getSource());
        request.setFollowUpType(leadStatusList.get(mBinding.spDispositions.getSelectedItemPosition()).getStatusName());
        if (!mBinding.tvReminderDate.getText().toString().equalsIgnoreCase("")){
            request.setReminder(mBinding.tvReminderDate.getText().toString());
        }
        if (DGMDashboardApplication.getInstance().isNetworkAvailable()) {
            dashboardViewModel.submitDoorStep(snakBarView,request).observe(this, responseModel -> {
                AlertDialog.Builder builderSaved = new AlertDialog.Builder(DoorStepActivity.this, R.style.MaterialAlertDialogStyle);
                builderSaved.setTitle("Door Step");
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
}