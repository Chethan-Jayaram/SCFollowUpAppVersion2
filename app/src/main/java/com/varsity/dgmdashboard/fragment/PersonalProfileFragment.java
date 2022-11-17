package com.varsity.dgmdashboard.fragment;

import static com.varsity.dgmdashboard.DGMDashboardApplication.getPreferencesManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.varsity.dgmdashboard.DGMDashboardApplication;
import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.activity.LoginActivity;
import com.varsity.dgmdashboard.databinding.FragmentPersonalProfileBinding;
import com.varsity.dgmdashboard.model.LoginResponseModel;

import org.jetbrains.annotations.NotNull;

public class PersonalProfileFragment extends Fragment {

    private FragmentPersonalProfileBinding mBinding;


    public static PersonalProfileFragment getNewInstance(Context context) {
        PersonalProfileFragment fragment = new PersonalProfileFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_personal_profile, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init(){
        LoginResponseModel userData=DGMDashboardApplication.getPreferencesManager().getUserData();
        mBinding.setUserData(userData);

        mBinding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPreferencesManager().clearData();
                Intent intent=new Intent(getContext(),LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(new Intent(getContext(), LoginActivity.class));
                ActivityCompat.finishAffinity(getActivity());
            }
        });
    }

}
