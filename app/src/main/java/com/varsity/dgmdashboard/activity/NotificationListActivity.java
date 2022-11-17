package com.varsity.dgmdashboard.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.varsity.dgmdashboard.DGMDashboardApplication;
import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.adapter.DistrictListAdapter;
import com.varsity.dgmdashboard.adapter.NotificationListAdapter;
import com.varsity.dgmdashboard.databinding.ActivityNotificationListBinding;
import com.varsity.dgmdashboard.model.DistrictsListResponseModel;
import com.varsity.dgmdashboard.utils.AppConstant;
import com.varsity.dgmdashboard.utils.SnackBar;
import com.varsity.dgmdashboard.viewmodel.DashboardViewModel;

import java.util.ArrayList;

public class NotificationListActivity extends AppCompatActivity {

    private ActivityNotificationListBinding mBinding;
    private View snakBarView;
    private DashboardViewModel dashboardViewModel;
    private NotificationListAdapter notificationListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_notification_list);
        init();
    }

    private void init(){
        snakBarView = findViewById(android.R.id.content);
        dashboardViewModel = new DashboardViewModel(this);

        setToolbar();
        getNotificationList();
    }

    private void setToolbar() {
        mBinding.llToolbar.tvToolbarTitle.setText("Notification");
        mBinding.llToolbar.llToolbarLeft.setVisibility(View.VISIBLE);
        mBinding.llToolbar.ivToolbarLeft.setImageResource(R.drawable.ic_back);

        mBinding.llToolbar.llToolbarRight.setVisibility(View.GONE);

        mBinding.llToolbar.tvUserName.setVisibility(View.GONE);

        mBinding.llToolbar.ivToolbarLeft.setOnClickListener(view -> onBackPressed());
    }

    private void getNotificationList(){
        if (DGMDashboardApplication.getInstance().isNetworkAvailable()) {
            String empId="2";
            dashboardViewModel.getNotificationList(snakBarView,empId).observe(this, responseModel -> {
                if (responseModel != null) {
                    if (responseModel.size() != 0) {
                        mBinding.tvNoData.setVisibility(View.GONE);
                        mBinding.rvNotification.setVisibility(View.VISIBLE);
                        notificationListAdapter=new NotificationListAdapter(responseModel);
                        mBinding.rvNotification.setAdapter(notificationListAdapter);

                        updateNotificationList();

                    }else {
                        mBinding.tvNoData.setVisibility(View.VISIBLE);
                        mBinding.rvNotification.setVisibility(View.GONE);
                    }
                }else {
                    mBinding.tvNoData.setVisibility(View.VISIBLE);
                    mBinding.rvNotification.setVisibility(View.GONE);
                }
            });
        } else {
            SnackBar.showInternetError(this, snakBarView);
        }
    }


    private void updateNotificationList(){
        if (DGMDashboardApplication.getInstance().isNetworkAvailable()) {
            String empId="2";
            dashboardViewModel.updateNotificationList(snakBarView,empId).observe(this, responseModel -> {
                if (responseModel != null) {
                    if (responseModel.size() != 0) {
                      DGMDashboardApplication.getPreferencesManager().saveStringValue(AppConstant.NOTIFICATION_COUNT,"0");
                    }
                }
            });
        } else {
            SnackBar.showInternetError(this, snakBarView);
        }
    }

}