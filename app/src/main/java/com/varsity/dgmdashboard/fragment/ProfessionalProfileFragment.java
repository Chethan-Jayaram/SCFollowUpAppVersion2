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

import com.varsity.dgmdashboard.DGMDashboardApplication;
import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.databinding.FragmentPersonalProfileBinding;
import com.varsity.dgmdashboard.databinding.FragmentProfessionalProfileBinding;
import com.varsity.dgmdashboard.model.LoginResponseModel;

import org.jetbrains.annotations.NotNull;

public class ProfessionalProfileFragment extends Fragment {

    private FragmentProfessionalProfileBinding mBinding;


    public static ProfessionalProfileFragment getNewInstance(Context context) {
        ProfessionalProfileFragment fragment = new ProfessionalProfileFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_professional_profile, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init(){
        LoginResponseModel userData= DGMDashboardApplication.getPreferencesManager().getUserData();
        mBinding.setUserData(userData);
    }
}
