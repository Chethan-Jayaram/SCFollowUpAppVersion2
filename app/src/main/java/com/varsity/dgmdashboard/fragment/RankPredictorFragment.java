package com.varsity.dgmdashboard.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.activity.RankCollageListActivity;
import com.varsity.dgmdashboard.adapter.MasterSpinnerAdapter;
import com.varsity.dgmdashboard.databinding.FragmentRankPredictorBinding;
import com.varsity.dgmdashboard.utils.AppConstant;
import com.varsity.dgmdashboard.utils.SnackBar;
import com.varsity.dgmdashboard.viewmodel.DashboardViewModel;

import java.util.ArrayList;

public class RankPredictorFragment extends Fragment {

    private FragmentRankPredictorBinding mBinding;
    private View snakBarView;
    private DashboardViewModel dashboardViewModel;
    private ArrayList<String> regionList = new ArrayList<>();
    private ArrayList<String> genderList = new ArrayList<>();
    private ArrayList<String> categoryList = new ArrayList<>();

    public static RankPredictorFragment getNewInstance(Context context) {
        RankPredictorFragment fragment = new RankPredictorFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_rank_predictor, container, false);
        init();
        return mBinding.getRoot();
    }

    private void init() {
        snakBarView = getActivity().findViewById(android.R.id.content);
        dashboardViewModel = new DashboardViewModel(getContext());
        setRegionData();
        setGender();
        setCategoryList();

        mBinding.btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
            }
        });
    }

    private void setRegionData() {
        regionList = AppConstant.getRegionList();
        MasterSpinnerAdapter genderAdapter = new MasterSpinnerAdapter(getContext(), regionList);
        mBinding.spRegion.setAdapter(genderAdapter);
        mBinding.spRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position!=-1){
                    if(regionList.get(position).equalsIgnoreCase("National")){
                        setNationalCategoryList();
                    }else {
                        setCategoryList();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setGender() {
        genderList = AppConstant.getGenderList();
        MasterSpinnerAdapter genderAdapter = new MasterSpinnerAdapter(getContext(), genderList);
        mBinding.spGender.setAdapter(genderAdapter);
    }

    private void setCategoryList() {
        categoryList = AppConstant.getCategoryList();
        MasterSpinnerAdapter genderAdapter = new MasterSpinnerAdapter(getContext(), categoryList);
        mBinding.spCategory.setAdapter(genderAdapter);
    }

    private void setNationalCategoryList() {
        categoryList = AppConstant.getNationalCategoryList();
        MasterSpinnerAdapter genderAdapter = new MasterSpinnerAdapter(getContext(), categoryList);
        mBinding.spCategory.setAdapter(genderAdapter);
    }

    private void checkValidation() {
        if (mBinding.spRegion.getSelectedItemPosition() <= 0) {
            SnackBar.showValidationError(getContext(), snakBarView, "Please select Region");
            return;
        }

       /* if (mBinding.spGender.getSelectedItemPosition() ==-1) {
            SnackBar.showValidationError(getContext(), snakBarView, "Please select Gender");
            return;
        }
*/
        if (mBinding.spCategory.getSelectedItemPosition() <= 0) {
            SnackBar.showValidationError(getContext(), snakBarView, "Please select category");
            return;
        }

        if (mBinding.edtMarks.getText().toString().equalsIgnoreCase("")) {
            SnackBar.showValidationError(getContext(), snakBarView, "Please enter marks");
            return;
        }
        String region = regionList.get(mBinding.spRegion.getSelectedItemPosition());
        String gender = genderList.get(mBinding.spGender.getSelectedItemPosition());
        String category = categoryList.get(mBinding.spCategory.getSelectedItemPosition());
        String marks = mBinding.edtMarks.getText().toString();
        startActivity(new Intent(getContext(), RankCollageListActivity.class)
                .putExtra("REGION", region)
                .putExtra("GENDER", gender)
                .putExtra("CATEGORY", category)
                .putExtra("MARKS", marks));
    }

}
