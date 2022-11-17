package com.varsity.dgmdashboard.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.varsity.dgmdashboard.DGMDashboardApplication;
import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.adapter.HistoryListAdapter;
import com.varsity.dgmdashboard.adapter.ProDetailsAdapter;
import com.varsity.dgmdashboard.databinding.FragmentProDetailsBinding;
import com.varsity.dgmdashboard.listener.PhoneCallListener;
import com.varsity.dgmdashboard.model.GetProDetailsLeadResponse;
import com.varsity.dgmdashboard.utils.SnackBar;
import com.varsity.dgmdashboard.viewmodel.DGMDashboardViewModel;

import java.util.ArrayList;

public class ProDetailsFragment extends Fragment implements PhoneCallListener {

    private FragmentProDetailsBinding mBinding;
    private View snakBarView;
    private DGMDashboardViewModel dashboardViewModel;
    private ProDetailsAdapter proDetailsAdapter;
    private ArrayList<GetProDetailsLeadResponse> arrProList;
    private String mobileNo="";


    public static ProDetailsFragment getNewInstance(Context context) {
        ProDetailsFragment fragment = new ProDetailsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_pro_details, container, false);
        init();
        return mBinding.getRoot();
    }



    private void init() {
        snakBarView = getActivity().findViewById(android.R.id.content);
        dashboardViewModel = new DGMDashboardViewModel(getContext());
        setProDetailsData();


        mBinding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }

    private void setProDetailsData() {
        if (DGMDashboardApplication.getInstance().isNetworkAvailable()) {
            dashboardViewModel.getDGMProDetailsData(snakBarView).observe(getViewLifecycleOwner(), responseModel -> {
                if (responseModel != null) {
                    if (responseModel.size() != 0) {
                        arrProList=new ArrayList<>();
                        arrProList=responseModel;
                        mBinding.rvProDetails.setVisibility(View.VISIBLE);
                        mBinding.tvNoData.setVisibility(View.GONE);
                        proDetailsAdapter= new ProDetailsAdapter(arrProList,this);
                        mBinding.rvProDetails.setAdapter(proDetailsAdapter);
                    }else {
                        arrProList=new ArrayList<>();
                        mBinding.rvProDetails.setVisibility(View.GONE);
                        mBinding.tvNoData.setVisibility(View.VISIBLE);
                    }
                }
            });
        } else {
            SnackBar.showInternetError(getContext(), snakBarView);
        }
    }

    void filter(String text){
        ArrayList<GetProDetailsLeadResponse> temp = new ArrayList();
        for(GetProDetailsLeadResponse d: arrProList){
            if(d.getName().toLowerCase().contains(text)){
                temp.add(d);
            }
        }
        if (temp!=null && temp.size()!=0){
            mBinding.tvNoData.setVisibility(View.GONE);
            mBinding.rvProDetails.setVisibility(View.VISIBLE);
            proDetailsAdapter.updateList(temp);
        }else {
            mBinding.rvProDetails.setVisibility(View.GONE);
            mBinding.tvNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void phoneCall(String mobileNumber) {
        mobileNo=mobileNumber;
        if (Build.VERSION.SDK_INT < 23) {
            call(mobileNumber);
        }else {

            if (ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                call(mobileNumber);
            }else {
                final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};

                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS_STORAGE, 9);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean permissionGranted = false;
        switch(requestCode){
            case 9:
                permissionGranted = grantResults[0]== PackageManager.PERMISSION_GRANTED;
                break;
        }
        if(permissionGranted){
           call(mobileNo);
        }else {
            Toast.makeText(getContext(), "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }


    private void call(String mobileNumber){
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+mobileNumber));
            getActivity().startActivity(callIntent);
        }else{
            Toast.makeText(getContext(), "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }
}
