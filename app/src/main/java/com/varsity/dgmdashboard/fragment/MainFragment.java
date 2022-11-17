package com.varsity.dgmdashboard.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.activity.AssignedLeadsActivity;
import com.varsity.dgmdashboard.activity.MainActivity;
import com.varsity.dgmdashboard.databinding.FragmentMainBinding;

public class MainFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {
    private FragmentMainBinding mBinding;
    private boolean isMainSelected = true;


    public static MainFragment getNewInstance(Context context, boolean isMainSelected) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("SELECT", isMainSelected);
        MainFragment fragment = new MainFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        init();
        return mBinding.getRoot();
    }

    private void init() {
        isMainSelected = getArguments().getBoolean("SELECT");

        mBinding.bottomNavigationView.setOnNavigationItemSelectedListener(this);

        if (isMainSelected){
            mBinding.bottomNavigationView.setSelectedItemId(R.id.dashboard);
        }else {
            mBinding.bottomNavigationView.setSelectedItemId(R.id.proDetails);
        }
        mBinding.btnAssignLeads.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AssignedLeadsActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dashboard: {
                MainActivity.mBinding.llToolbar.tvToolbarTitle.setText(R.string.str_dgm_dashboard);
                mBinding.btnAssignLeads.setVisibility(View.VISIBLE);
                getParentFragmentManager().beginTransaction().replace(R.id.flFrame, DashboardFragment.getNewInstance(getContext())).commit();
                break;
            }
            case R.id.proDetails: {
                MainActivity.mBinding.llToolbar.tvToolbarTitle.setText(R.string.str_pro_details);
                mBinding.btnAssignLeads.setVisibility(View.VISIBLE);
                getParentFragmentManager().beginTransaction().replace(R.id.flFrame, ProDetailsFragment.getNewInstance(getContext())).commit();
                break;
            }

            case R.id.leadAllocation: {
                MainActivity.mBinding.llToolbar.tvToolbarTitle.setText("Lead Allotment");
                mBinding.btnAssignLeads.setVisibility(View.GONE);
                getParentFragmentManager().beginTransaction().replace(R.id.flFrame, LeadAllotmentFragment.getNewInstance(getContext())).commit();
                break;
            }
        }
        return true;
    }

}
