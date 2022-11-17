package com.varsity.dgmdashboard.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.varsity.dgmdashboard.DGMDashboardApplication;
import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.adapter.CollageListAdapter;
import com.varsity.dgmdashboard.databinding.ActivityRankCollageListBinding;
import com.varsity.dgmdashboard.model.CollageListResponseModel;
import com.varsity.dgmdashboard.viewmodel.DashboardViewModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class RankCollageListActivity extends AppCompatActivity {

    private ActivityRankCollageListBinding mBinding;
    private CollageListResponseModel rankCollageModel;
    private ArrayList<CollageListResponseModel.CollageData> rankCollageList = new ArrayList<>();
    private CollageListAdapter rankCollageAdapter;
    private View snakBarView;
    private String strRegion = "", strGender = "", strCategory = "", strMarks = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_rank_collage_list);
        init();
    }

    private void init() {
        setToolbar();
        snakBarView = findViewById(android.R.id.content);
        strRegion = getIntent().getStringExtra("REGION");
        strCategory = getIntent().getStringExtra("CATEGORY");
        strGender = getIntent().getStringExtra("GENDER");
        strMarks = getIntent().getStringExtra("MARKS");

        mBinding.tvRegion.setText(strRegion);
        mBinding.tvGender.setText(strGender);
        mBinding.tvCategory.setText(strCategory);
        mBinding.tvMarks.setText(strMarks);

        loadJSONFromAsset();
    }


    private void setToolbar() {
        mBinding.llToolbar.tvToolbarTitle.setText("Collage List");
        mBinding.llToolbar.llToolbarLeft.setVisibility(View.VISIBLE);
        mBinding.llToolbar.ivToolbarLeft.setImageResource(R.drawable.ic_back);

        mBinding.llToolbar.llToolbarRight.setVisibility(View.VISIBLE);
        mBinding.llToolbar.ivToolbarRight.setImageResource(R.drawable.ic_notifications);

        mBinding.llToolbar.tvUserName.setVisibility(View.VISIBLE);
        String userName= DGMDashboardApplication.getPreferencesManager().getUserData().getUserName();
        mBinding.llToolbar.tvUserName.setText(userName.trim().substring(0, 1));

        mBinding.llToolbar.ivToolbarLeft.setOnClickListener(view -> onBackPressed());
    }


    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = RankCollageListActivity.this.getAssets().open("collegewisecutoff.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            rankCollageModel = new Gson().fromJson(json, CollageListResponseModel.class);
            setRankCollageList();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void setRankCollageList() {
        if (strRegion.equalsIgnoreCase("TS & AP")) {
            rankCollageList = (ArrayList<CollageListResponseModel.CollageData>) rankCollageModel.getApAndTsWiseColleges();
        } else if (strRegion.equalsIgnoreCase("TS")) {
            rankCollageList = (ArrayList<CollageListResponseModel.CollageData>) rankCollageModel.getTsWsieColleges();
        } else if (strRegion.equalsIgnoreCase("AP")) {
            rankCollageList = (ArrayList<CollageListResponseModel.CollageData>) rankCollageModel.getApWiseCollges();
        } else {
            rankCollageList = (ArrayList<CollageListResponseModel.CollageData>) rankCollageModel.getNationalWiseColleges();
        }
        ArrayList<CollageListResponseModel.CollageData> filterCollageList=new ArrayList<>();
        for (CollageListResponseModel.CollageData collageData:rankCollageList){
            if (collageData.getCategory().equalsIgnoreCase(strCategory)){
                filterCollageList.add(collageData);
            }
        }
        if (strRegion.equalsIgnoreCase("TS")|| strRegion.equalsIgnoreCase("AP")){
            ArrayList<CollageListResponseModel.CollageData> genderFilterCollageList=new ArrayList<>();
            String type="";
            if (strGender.equalsIgnoreCase("Male")){
                type="M";
            }else {
                type="F";
            }
            for (CollageListResponseModel.CollageData collageData:filterCollageList){
                if (collageData.getGender().equalsIgnoreCase(type)){
                    genderFilterCollageList.add(collageData);
                }
            }

            rankCollageAdapter = new CollageListAdapter(genderFilterCollageList);
        }else {
            rankCollageAdapter = new CollageListAdapter(filterCollageList);
        }
        mBinding.rvRankCollage.setAdapter(rankCollageAdapter);

    }


}