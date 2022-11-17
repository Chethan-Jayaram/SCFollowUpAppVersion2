package com.varsity.dgmdashboard.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.varsity.dgmdashboard.DGMDashboardApplication;
import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.databinding.ActivityProDashboardMainBinding;
import com.varsity.dgmdashboard.fragment.FollowUpMainFragment;
import com.varsity.dgmdashboard.fragment.HistoryFragment;
import com.varsity.dgmdashboard.fragment.ProDashboardFragment;
import com.varsity.dgmdashboard.fragment.RankPredictorFragment;
import com.varsity.dgmdashboard.utils.AppConstant;
import com.varsity.dgmdashboard.viewmodel.DashboardViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProDashboardMainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static ActivityProDashboardMainBinding mBinding;
    private View snakBarView;
    private DashboardViewModel dashboardViewModel;
    private int selectedTab=1;
    private int mMenuItemSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_pro_dashboard_main);
        init();
    }

    private void init() {
        snakBarView = findViewById(android.R.id.content);
        dashboardViewModel = new DashboardViewModel(this);

        mBinding.llToolbar.llNotification.setVisibility(View.VISIBLE);
        mBinding.llToolbar.llNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProDashboardMainActivity.this, NotificationListActivity.class));
            }
        });

        mBinding.llToolbar.tvUserName.setVisibility(View.VISIBLE);
        String userName = DGMDashboardApplication.getPreferencesManager().getUserData().getUserName();
        mBinding.llToolbar.tvUserName.setText(userName.trim().substring(0, 1));
        mBinding.bottomNavigationView.setOnNavigationItemSelectedListener(this);
        mBinding.bottomNavigationView.setSelectedItemId(R.id.dashboard);

        mBinding.llToolbar.tvUserName.setOnClickListener(view -> startActivity(new Intent(ProDashboardMainActivity.this, ProfileActivity.class)));
        getNotificationCount();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        selectFragment(item);

        /*switch (item.getItemId()) {
            case R.id.dashboard: {
                mBinding.llToolbar.tvToolbarTitle.setText(R.string.str_pro_dashboard);
                getSupportFragmentManager().beginTransaction().replace(R.id.flFrame, ProDashboardFragment.getNewInstance(this),"Dashboard").addToBackStack("Dashboard").commit();
                break;
            }
            case R.id.followUp: {
                mBinding.llToolbar.tvToolbarTitle.setText(R.string.str_follow_up);
                getSupportFragmentManager().beginTransaction().replace(R.id.flFrame, FollowUpMainFragment.getNewInstance(this)).addToBackStack("FollowUp").commit();
                break;
            }
            case R.id.history: {
                mBinding.llToolbar.tvToolbarTitle.setText(getString(R.string.str_history));
                getSupportFragmentManager().beginTransaction().replace(R.id.flFrame, HistoryFragment.getNewInstance(this)).commit();
                break;
            }
            case R.id.rankPredictor: {
                mBinding.llToolbar.tvToolbarTitle.setText(getString(R.string.str_rank_predictor));
                getSupportFragmentManager().beginTransaction().replace(R.id.flFrame, RankPredictorFragment.getNewInstance(this)).commit();
                break;
            }
        }*/
        return true;
    }


    private void getNotificationCount() {
        dashboardViewModel.getNotificationCount(snakBarView, "2").observe(this, responseModel -> {
            if (responseModel != null) {
                mBinding.llToolbar.tvNotificationCount.setText("" + responseModel.getUnreadCount());
                DGMDashboardApplication.getPreferencesManager().saveStringValue(AppConstant.NOTIFICATION_COUNT, "" + responseModel.getUnreadCount());
            } else {
                mBinding.llToolbar.tvNotificationCount.setText("0");
                DGMDashboardApplication.getPreferencesManager().saveStringValue(AppConstant.NOTIFICATION_COUNT, "0");
            }
        });
    }

    public static void updateCount(){
        mBinding.llToolbar.tvNotificationCount.setText("0");
    }


    private void selectFragment(MenuItem item) {
        mMenuItemSelected=item.getItemId();
        Fragment fragment = null;
        Class fragmentClass;
        switch (item.getItemId()) {
            case R.id.dashboard:
                mBinding.llToolbar.tvToolbarTitle.setText(R.string.str_pro_dashboard);
                fragmentClass = ProDashboardFragment.class;
                break;
            case R.id.followUp:
                mBinding.llToolbar.tvToolbarTitle.setText(R.string.str_follow_up);
                fragmentClass = FollowUpMainFragment.class;
                break;
            case R.id.history:
                mBinding.llToolbar.tvToolbarTitle.setText(getString(R.string.str_history));
                fragmentClass = HistoryFragment.class;
                break;
            case R.id.rankPredictor:
                mBinding.llToolbar.tvToolbarTitle.setText(getString(R.string.str_rank_predictor));
                fragmentClass = RankPredictorFragment.class;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.flFrame, fragment).commit();
    }


    @Override
    public void onBackPressed() {
        MenuItem homeItem = mBinding.bottomNavigationView.getMenu().getItem(0);
        if (mMenuItemSelected != homeItem.getItemId()) {
            mBinding.bottomNavigationView.setSelectedItemId(R.id.dashboard);
            //selectFragment(homeItem);
        } else {
            super.onBackPressed();
        }
    }


}