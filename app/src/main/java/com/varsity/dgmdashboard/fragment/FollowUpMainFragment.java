package com.varsity.dgmdashboard.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.databinding.FragmentFollowUpMainBinding;

public class FollowUpMainFragment extends Fragment {
    private FragmentFollowUpMainBinding mBinding;

    public static FollowUpMainFragment getNewInstance(Context context) {
        FollowUpMainFragment fragment = new FollowUpMainFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_follow_up_main, container, false);
        init();
        return mBinding.getRoot();
    }

    private void init(){
        getParentFragmentManager().beginTransaction().replace(R.id.frameView, AddressListFragment.getNewInstance(getContext())).commit();
        mBinding.tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().toString().equalsIgnoreCase("FOLLOW UP")){
                    getParentFragmentManager().beginTransaction().replace(R.id.frameView, FollowUpFragment.getNewInstance(getContext())).commit();
                }

                if (tab.getText().toString().equalsIgnoreCase("ADDRESS")){
                    getParentFragmentManager().beginTransaction().replace(R.id.frameView, AddressListFragment.getNewInstance(getContext())).commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
