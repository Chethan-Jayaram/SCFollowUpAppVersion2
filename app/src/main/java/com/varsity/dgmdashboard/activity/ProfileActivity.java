package com.varsity.dgmdashboard.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.adapter.UserProfileAdapter;
import com.varsity.dgmdashboard.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {


    private ActivityProfileBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_profile);
        init();
    }

    private void init(){
        mBinding.llToolbar.tvToolbarTitle.setText("Profile");
        mBinding.llToolbar.llToolbarLeft.setVisibility(View.VISIBLE);
        mBinding.llToolbar.ivToolbarLeft.setImageResource(R.drawable.ic_back);
        mBinding.llToolbar.ivToolbarLeft.setOnClickListener(view -> onBackPressed());

        mBinding.tabLayoutProfile.addTab(mBinding.tabLayoutProfile.newTab().setText("Personal"));
        mBinding.tabLayoutProfile.addTab(mBinding.tabLayoutProfile.newTab().setText("Professional"));

        UserProfileAdapter tabUserProfileAdapter = new UserProfileAdapter(ProfileActivity.this, mBinding.tabLayoutProfile.getTabCount());
        mBinding.viewPagerProfile.setAdapter(tabUserProfileAdapter);

        new TabLayoutMediator(mBinding.tabLayoutProfile,  mBinding.viewPagerProfile,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Personal");
                            break;

                        case 1:
                            tab.setText("Professional");
                            break;

                    }
                }
        ).attach();


        mBinding.tabLayoutProfile.setTabGravity(TabLayout.GRAVITY_FILL);
    }
}