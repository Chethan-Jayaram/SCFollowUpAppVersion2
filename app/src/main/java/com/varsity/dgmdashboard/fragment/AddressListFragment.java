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
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.varsity.dgmdashboard.DGMDashboardApplication;
import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.adapter.AddressListAdapter;
import com.varsity.dgmdashboard.databinding.FragmentAddressListBinding;
import com.varsity.dgmdashboard.listener.PhoneCallListener;
import com.varsity.dgmdashboard.model.CallStatusListResponseModel;
import com.varsity.dgmdashboard.model.GetProDetailsLeadResponse;
import com.varsity.dgmdashboard.utils.SnackBar;
import com.varsity.dgmdashboard.viewmodel.DashboardViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AddressListFragment extends Fragment {

    private FragmentAddressListBinding mBinding;
    private View snakBarView;
    private DashboardViewModel dashboardViewModel;
    private AddressListAdapter addressListAdapter;
    private String mobileNo = "";
    private ArrayList<CallStatusListResponseModel> addressList;

    public static AddressListFragment getNewInstance(Context context) {
        AddressListFragment fragment = new AddressListFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_address_list, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        snakBarView = getActivity().findViewById(android.R.id.content);
        dashboardViewModel = new DashboardViewModel(getContext());

        getAddressListData();

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

    private void getAddressListData() {
        if (DGMDashboardApplication.getInstance().isNetworkAvailable()) {
            dashboardViewModel.getCallStatusList(snakBarView).observe(getViewLifecycleOwner(), responseModel -> {
                if (responseModel != null) {
                    if (responseModel.size() != 0) {
                        addressList = responseModel;
                        mBinding.tvNoData.setVisibility(View.GONE);
                        addressListAdapter = new AddressListAdapter(responseModel);
                        mBinding.rvToday.setAdapter(addressListAdapter);
                    }
                }
            });
        } else {
            SnackBar.showInternetError(getContext(), snakBarView);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean permissionGranted = false;
        switch (requestCode) {
            case 9:
                permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (permissionGranted) {
            call(mobileNo);
        } else {
            Toast.makeText(getContext(), "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }


    private void call(String mobileNumber) {
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + mobileNumber));
            getActivity().startActivity(callIntent);
        } else {
            Toast.makeText(getContext(), "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }

    void filter(String text) {
        ArrayList<CallStatusListResponseModel> temp = new ArrayList();
        if (addressList != null)
            for (CallStatusListResponseModel d : addressList) {
                if (d.getStudentName().toLowerCase().contains(text.toLowerCase()) || d.getMobileNo().toString().toLowerCase().contains(text.toLowerCase())) {
                    temp.add(d);
                }
            }
        if (temp != null && temp.size() != 0) {
            mBinding.tvNoData.setVisibility(View.GONE);
            mBinding.rvToday.setVisibility(View.VISIBLE);
            addressListAdapter.updateList(temp);
        } else {
            mBinding.rvToday.setVisibility(View.GONE);
            mBinding.tvNoData.setVisibility(View.VISIBLE);
        }
    }

}
