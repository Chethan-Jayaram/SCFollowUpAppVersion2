package com.varsity.dgmdashboard.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.varsity.dgmdashboard.DGMDashboardApplication;
import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.databinding.ActivitySplashBinding;
import com.varsity.dgmdashboard.utils.AppConstant;
import com.varsity.dgmdashboard.utils.MyUtils;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        init();
    }

    private void init() {
        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    redirectToNext();
                }
            }
        };
        timerThread.start();
    }

    private void redirectToNext() {
        if (DGMDashboardApplication.getPreferencesManager().getBooleanValue(AppConstant.IS_LOGIN)) {
            if (DGMDashboardApplication.getPreferencesManager().getUserData().getRoles().contains("DGM_ROLE")){
                startActivity(new Intent(this, MainActivity.class));
            }else {
                startActivity(new Intent(this, ProDashboardMainActivity.class));
            }
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
        finish();
    }
}