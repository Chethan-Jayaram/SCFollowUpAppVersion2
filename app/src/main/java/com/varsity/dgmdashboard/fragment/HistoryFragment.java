package com.varsity.dgmdashboard.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.varsity.dgmdashboard.DGMDashboardApplication;
import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.adapter.FollowupListAdapter;
import com.varsity.dgmdashboard.adapter.HistoryListAdapter;
import com.varsity.dgmdashboard.databinding.FragmentHistoryBinding;
import com.varsity.dgmdashboard.model.CallStatusListResponseModel;
import com.varsity.dgmdashboard.model.LeadStatusListResponseModel;
import com.varsity.dgmdashboard.utils.SnackBar;
import com.varsity.dgmdashboard.viewmodel.DashboardViewModel;

import java.util.ArrayList;
import java.util.Random;

public class HistoryFragment extends Fragment {
    private FragmentHistoryBinding mBinding;
    private View snakBarView;
    private DashboardViewModel dashboardViewModel;
    private HistoryListAdapter historyListAdapter;
    ArrayList<LeadStatusListResponseModel> historyList;
    ArrayList<Integer> colorCodeList;
    public static HistoryFragment getNewInstance(Context context) {
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false);
        init();
        return mBinding.getRoot();
    }

    private void init() {
        snakBarView = getActivity().findViewById(android.R.id.content);
        dashboardViewModel = new DashboardViewModel(getContext());
        getHistoryListData();

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

    private void getHistoryListData() {
        if (DGMDashboardApplication.getInstance().isNetworkAvailable()) {
            dashboardViewModel.getStatusWiseLeadsList(snakBarView,"Communicated").observe(getViewLifecycleOwner(), responseModel -> {
                if (responseModel != null) {
                    if (responseModel.size() != 0) {
                        mBinding.tvNoData.setVisibility(View.GONE);
                        historyList=responseModel;
                        colorCodeList=new ArrayList<>();
                        for (LeadStatusListResponseModel data:historyList){
                            Random rnd = new Random();
                            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                            colorCodeList.add(color);
                        }
                        historyListAdapter=new HistoryListAdapter(responseModel,colorCodeList);
                        mBinding.rvHistory.setAdapter(historyListAdapter);
                    }else {
                        mBinding.tvNoData.setVisibility(View.VISIBLE);
                    }
                }
            });
        } else {
            SnackBar.showInternetError(getContext(), snakBarView);
        }
    }

    void filter(String text){
        ArrayList<LeadStatusListResponseModel> temp = new ArrayList();
        for(LeadStatusListResponseModel d: historyList){
            if(d.getName().toLowerCase().contains(text.toLowerCase()) || d.getMobile().toLowerCase().contains(text.toLowerCase())){
                temp.add(d);
            }
        }
        if (temp!=null && temp.size()!=0){
            mBinding.tvNoData.setVisibility(View.GONE);
            mBinding.rvHistory.setVisibility(View.VISIBLE);
            historyListAdapter.updateList(temp);
        }else {
            mBinding.rvHistory.setVisibility(View.GONE);
            mBinding.tvNoData.setVisibility(View.VISIBLE);
        }
    }
}
