package com.varsity.dgmdashboard.fragment;

import android.content.Context;
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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.varsity.dgmdashboard.DGMDashboardApplication;
import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.adapter.FollowupListAdapter;
import com.varsity.dgmdashboard.databinding.FragmentFollowupBinding;
import com.varsity.dgmdashboard.model.CallStatusListResponseModel;
import com.varsity.dgmdashboard.model.LeadStatusListResponseModel;
import com.varsity.dgmdashboard.utils.PaginationListener;
import com.varsity.dgmdashboard.utils.SnackBar;
import com.varsity.dgmdashboard.viewmodel.DashboardViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FollowUpFragment extends Fragment {

    private FragmentFollowupBinding mBinding;
    private View snakBarView;
    private DashboardViewModel dashboardViewModel;
    private FollowupListAdapter followupListAdapter;
    private int pageNo = 0;
    private LinearLayoutManager layoutManager;
    public static boolean isLoadMore = false;
    private ArrayList<LeadStatusListResponseModel> followList;

    public static FollowUpFragment getNewInstance(Context context) {
        FollowUpFragment fragment = new FollowUpFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_followup, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        followList = new ArrayList<>();
        snakBarView = getActivity().findViewById(android.R.id.content);
        dashboardViewModel = new DashboardViewModel(getContext());
        layoutManager = new LinearLayoutManager(getContext());
        mBinding.rvFollowup.setLayoutManager(layoutManager);
        getFollowupListData();

        mBinding.rvFollowup.addOnScrollListener(new PaginationListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                if (isLoadMore) {
                    pageNo++;
                    getLoadMoreFollowupListData();
                }
            }

            @Override
            public boolean isLastPage() {
                return false;
            }

            @Override
            public boolean isLoading() {
                return false;
            }
        });

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

    private void setRecyclerVisibility(boolean isVisible) {
        if (isVisible) {
            mBinding.mcvPending.setVisibility(View.VISIBLE);
            mBinding.tvNoData.setVisibility(View.GONE);
            mBinding.rvFollowup.setVisibility(View.VISIBLE);
        }
        else{
            mBinding.mcvPending.setVisibility(View.GONE);
            mBinding.tvNoData.setVisibility(View.VISIBLE);
            mBinding.rvFollowup.setVisibility(View.GONE);
        }

    }

    private void getFollowupListData() {
        if (DGMDashboardApplication.getInstance().isNetworkAvailable()) {
            dashboardViewModel.getFollowupList(snakBarView, "Pending", pageNo).observe(getViewLifecycleOwner(), responseModel -> {
                if (responseModel != null) {
                    if (responseModel.size() != 0) {
                        followList.addAll(responseModel);
                        isLoadMore = true;
                        mBinding.tvNoData.setVisibility(View.GONE);
                        followupListAdapter = new FollowupListAdapter(responseModel);
                        mBinding.rvFollowup.setAdapter(followupListAdapter);
                        setRecyclerVisibility(true);
                    } else {
                        setRecyclerVisibility(false);
                        isLoadMore = false;
                    }
                } else {
                    setRecyclerVisibility(false);
                    isLoadMore = false;
                }
            });
        } else {
            SnackBar.showInternetError(getContext(), snakBarView);
        }
    }


    private void getLoadMoreFollowupListData() {
        if (DGMDashboardApplication.getInstance().isNetworkAvailable()) {
            dashboardViewModel.getFollowupList(snakBarView, "Pending", pageNo).observe(getViewLifecycleOwner(), responseModel -> {
                if (responseModel != null) {
                    if (responseModel.size() != 0) {
                        followList.addAll(responseModel);
                        isLoadMore = true;
                        followupListAdapter.addItems(responseModel);
                        followupListAdapter.notifyDataSetChanged();
                    } else {
                        isLoadMore = false;
                    }
                } else {
                    isLoadMore = false;
                }
            });
        } else {
            SnackBar.showInternetError(getContext(), snakBarView);
        }
    }

    void filter(String text) {
        ArrayList<LeadStatusListResponseModel> temp = new ArrayList();
        for (LeadStatusListResponseModel d : followList) {
            if (d.getName().toLowerCase().contains(text.toLowerCase()) || d.getMobile().toString().toLowerCase().contains(text.toLowerCase())) {
                temp.add(d);
            }
        }
        if (temp != null && temp.size() != 0) {
//            mBinding.tvNoData.setVisibility(View.GONE);
//            mBinding.rvFollowup.setVisibility(View.VISIBLE);
            setRecyclerVisibility(true);
            followupListAdapter.updateList(temp);
        } else {
            setRecyclerVisibility(false);
//            mBinding.rvFollowup.setVisibility(View.GONE);
//            mBinding.tvNoData.setVisibility(View.VISIBLE);
        }
    }


}
