package com.varsity.dgmdashboard.activity;

import static com.varsity.dgmdashboard.DGMDashboardApplication.getPreferencesManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.navigation.NavigationView;
import com.varsity.dgmdashboard.DGMDashboardApplication;
import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.databinding.ActivityMainBinding;
import com.varsity.dgmdashboard.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    public static ActivityMainBinding mBinding;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        init();
    }

    private void init() {
        setToolbar();

        /*toggle = new ActionBarDrawerToggle(this, mBinding.drawer, R.string.nav_open, R.string.nav_close);
        mBinding.drawer.addDrawerListener(toggle);
        toggle.syncState();
        mBinding.navView.setNavigationItemSelectedListener(this);*/

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, MainFragment.getNewInstance(this, true)).commit();

        /*mBinding.btnLogout.setOnClickListener(v -> {
            getPreferencesManager().clearData();
            startActivity(new Intent(this, LoginActivity.class));
        });*/

      /*  View header = mBinding.navView.getHeaderView(0);
        TextView tvUserName = (TextView) header.findViewById(R.id.tvUserName);
        TextView tvId = (TextView) header.findViewById(R.id.tvId);

        TextView tvFirst = (TextView) header.findViewById(R.id.tvFirst);
        String userName = DGMDashboardApplication.getPreferencesManager().getUserData().getUserName();
        tvUserName.setText(userName);
        tvId.setText("ID : " + DGMDashboardApplication.getPreferencesManager().getUserData().getPayrollId());
        tvFirst.setText(userName.trim().substring(0, 1));*/

    }

    private void setToolbar() {
        mBinding.llToolbar.tvToolbarTitle.setText(R.string.str_dgm_dashboard);
        mBinding.llToolbar.llToolbarLeft.setVisibility(View.GONE);
        mBinding.llToolbar.ivToolbarLeft.setImageResource(R.drawable.ic_menu);

        mBinding.llToolbar.llToolbarRight.setVisibility(View.GONE);
        mBinding.llToolbar.ivToolbarRight.setImageResource(R.drawable.ic_notifications);

        mBinding.llToolbar.tvUserName.setVisibility(View.VISIBLE);
        String userName = DGMDashboardApplication.getPreferencesManager().getUserData().getUserName();
        mBinding.llToolbar.tvUserName.setText(userName.trim().substring(0, 1));

        mBinding.llToolbar.tvUserName.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, ProfileActivity.class)));

    }

   /* @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navLeadAllotment: {
                startActivity(new Intent(this, LeadAllocationManuallyActivity.class));
                break;
            }

            case R.id.navProDetails: {
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, MainFragment.getNewInstance(this, false)).commit();
                break;
            }
        }
        mBinding.drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/
}