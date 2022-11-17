package com.varsity.dgmdashboard.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.varsity.dgmdashboard.DGMDashboardApplication;
import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.databinding.ActivityLoginBinding;
import com.varsity.dgmdashboard.model.LoginResponseModel;
import com.varsity.dgmdashboard.utils.AppConstant;
import com.varsity.dgmdashboard.utils.MyUtils;
import com.varsity.dgmdashboard.utils.SnackBar;
import com.varsity.dgmdashboard.viewmodel.DashboardViewModel;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityLoginBinding mBinding;
    private View snakBarView;
    private DashboardViewModel dashboardViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        init();
    }

    private void init() {
        snakBarView = findViewById(android.R.id.content);
        dashboardViewModel = new DashboardViewModel(this);
        mBinding.btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin: {
                callLoginAPI();
                break;
            }
        }
    }

    private void callLoginAPI() {
        if (mBinding.edtUserId.getText().toString().equalsIgnoreCase("")) {
            SnackBar.showValidationError(LoginActivity.this, snakBarView, getString(R.string.please_enter_userid));
            return;
        }
        if (mBinding.edtPassword.getText().toString().equalsIgnoreCase("")) {
            SnackBar.showValidationError(LoginActivity.this, snakBarView, getString(R.string.please_enter_password));
            return;
        }

        //hyd301298
        //0110563a3349a47a3874fc38355ec8a2


        //NLR255645
        //799fa852b4b243443434bae655da5260

        if (DGMDashboardApplication.getInstance().isNetworkAvailable()) {

            dashboardViewModel.callLoginUser(mBinding.edtUserId.getText().toString(),MyUtils.getMd5(mBinding.edtPassword.getText().toString()), snakBarView).observe(this, new Observer<LoginResponseModel>() {
                @Override
                public void onChanged(LoginResponseModel responseModel) {
                    if (responseModel != null) {
                        DGMDashboardApplication.getPreferencesManager().saveUserData(responseModel);
                        DGMDashboardApplication.getPreferencesManager().saveBooleanValue(AppConstant.IS_LOGIN, true);
                        DGMDashboardApplication.getPreferencesManager().saveStringValue(AppConstant.TOKEN, "Bearer " + responseModel.getAccessToken());

                        if (DGMDashboardApplication.getPreferencesManager().getUserData().getRoles().contains("DGM_ROLE")){
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }else {
                            startActivity(new Intent(LoginActivity.this, ProDashboardMainActivity.class));
                        }
                        finish();
                    }
                }
            });
        } else {
            SnackBar.showInternetError(LoginActivity.this, snakBarView);
        }
        /*if (DGMDashboardApplication.getInstance().isNetworkAvailable()) {

            dashboardViewModel.callLoginUser("hyd301298"*//*mBinding.edtUserId.getText().toString()*//*,"0110563a3349a47a3874fc38355ec8a2"*//*MyUtils.getMd5(mBinding.edtPassword.getText().toString())*//*, snakBarView).observe(this, new Observer<LoginResponseModel>() {
                @Override
                public void onChanged(LoginResponseModel responseModel) {
                    if (responseModel != null) {
                        DGMDashboardApplication.getPreferencesManager().saveUserData(responseModel);
                        DGMDashboardApplication.getPreferencesManager().saveBooleanValue(AppConstant.IS_LOGIN, true);
                        DGMDashboardApplication.getPreferencesManager().saveStringValue(AppConstant.TOKEN, "Bearer " + responseModel.getAccessToken());

                        if (DGMDashboardApplication.getPreferencesManager().getUserData().getRoles().contains("DGM_ROLE")){
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }else {
                            startActivity(new Intent(LoginActivity.this, ProDashboardMainActivity.class));
                        }
                        finish();
                    }
                }
            });
        } else {
            SnackBar.showInternetError(LoginActivity.this, snakBarView);
        }*/
    }
}