package com.varsity.dgmdashboard.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.varsity.dgmdashboard.DGMDashboardApplication;
import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.adapter.NotesListAdapter;
import com.varsity.dgmdashboard.databinding.ActivityNoteListBinding;
import com.varsity.dgmdashboard.model.GetProDetailsLeadResponse;
import com.varsity.dgmdashboard.utils.SnackBar;
import com.varsity.dgmdashboard.viewmodel.DGMDashboardViewModel;

public class NoteListActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityNoteListBinding mBinding;
    private View snakBarView;
    private DGMDashboardViewModel dashboardViewModel;
    private GetProDetailsLeadResponse leadResponse;
    private NotesListAdapter notesListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_note_list);
        init();
    }

    private void init() {
        snakBarView = findViewById(android.R.id.content);
        dashboardViewModel = new DGMDashboardViewModel(this);

        String data = getIntent().getStringExtra("DATA");

        if (data != null && !data.equalsIgnoreCase("")) {
            leadResponse = new Gson().fromJson(data, GetProDetailsLeadResponse.class);
        }
        setToolbar();


        mBinding.btnAddNotes.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setNotesList();
    }

    private void setToolbar() {
        if (leadResponse != null) {
            mBinding.llToolbar.tvToolbarTitle.setText("Notes - " + leadResponse.getName());
        }

        mBinding.llToolbar.llToolbarLeft.setVisibility(View.VISIBLE);
        mBinding.llToolbar.ivToolbarLeft.setImageResource(R.drawable.ic_back);

        mBinding.llToolbar.llToolbarRight.setVisibility(View.GONE);
        mBinding.llToolbar.ivToolbarRight.setImageResource(R.drawable.ic_notifications);

        mBinding.llToolbar.ivToolbarLeft.setOnClickListener(view -> onBackPressed());
    }

    private void setNotesList() {
        if (DGMDashboardApplication.getInstance().isNetworkAvailable()) {
            dashboardViewModel.getDGMNoteListResponse(snakBarView, String.valueOf(leadResponse.getId())).observe(this, responseModel -> {
                if (responseModel != null) {
                    if (responseModel.size() != 0) {
                        mBinding.rvNotes.setVisibility(View.VISIBLE);
                        mBinding.tvNoData.setVisibility(View.GONE);
                        notesListAdapter = new NotesListAdapter(responseModel);
                        mBinding.rvNotes.setAdapter(notesListAdapter);
                    } else {
                        mBinding.rvNotes.setVisibility(View.GONE);
                        mBinding.tvNoData.setVisibility(View.VISIBLE);
                    }
                }else {
                    mBinding.rvNotes.setVisibility(View.GONE);
                    mBinding.tvNoData.setVisibility(View.VISIBLE);
                }
            });
        } else {
            SnackBar.showInternetError(this, snakBarView);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddNotes: {
                startActivity(new Intent(this, AddNotesActivity.class).putExtra("DATA", new Gson().toJson(leadResponse)));
                break;
            }
        }
    }
}