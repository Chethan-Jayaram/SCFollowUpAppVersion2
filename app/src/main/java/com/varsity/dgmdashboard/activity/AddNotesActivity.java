package com.varsity.dgmdashboard.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.varsity.dgmdashboard.DGMDashboardApplication;
import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.databinding.ActivityAddNotesBinding;
import com.varsity.dgmdashboard.model.AddDGMNoteRequest;
import com.varsity.dgmdashboard.model.GetProDetailsLeadResponse;
import com.varsity.dgmdashboard.utils.SnackBar;
import com.varsity.dgmdashboard.viewmodel.DGMDashboardViewModel;

public class AddNotesActivity extends AppCompatActivity {
    private ActivityAddNotesBinding mBinding;
    private View snakBarView;
    private DGMDashboardViewModel dashboardViewModel;
    private GetProDetailsLeadResponse leadResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_notes);
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

        mBinding.btnSave.setOnClickListener(v -> addNote());
    }

    private void setToolbar() {
        mBinding.llToolbar.tvToolbarTitle.setText(R.string.add_note);
        mBinding.llToolbar.llToolbarLeft.setVisibility(View.VISIBLE);
        mBinding.llToolbar.ivToolbarLeft.setImageResource(R.drawable.ic_back);


        mBinding.llToolbar.ivToolbarLeft.setOnClickListener(view -> onBackPressed());
    }


    private void addNote() {
        if (mBinding.edtSubject.getText().toString().equalsIgnoreCase("")){
            SnackBar.showValidationError(this, snakBarView, "Please enter Subject");
            return;
        }

        if (mBinding.edtNotes.getText().toString().equalsIgnoreCase("")){
            SnackBar.showValidationError(this, snakBarView, "Please enter Notes");
            return;
        }
        if (DGMDashboardApplication.getInstance().isNetworkAvailable()) {

            AddDGMNoteRequest request = new AddDGMNoteRequest();
            request.setEmpID(String.valueOf(leadResponse.getId()));
            request.setSubject(mBinding.edtSubject.getText().toString());
            request.setNotes(mBinding.edtNotes.getText().toString());
            dashboardViewModel.addDGMNotesData(snakBarView, request).observe(this, responseModel -> {
                AlertDialog.Builder builderSaved = new AlertDialog.Builder(this, R.style.MaterialAlertDialogStyle);
                builderSaved.setTitle("Note");
                builderSaved.setMessage("Details successfully submitted");
                builderSaved.setCancelable(false);
                builderSaved.setPositiveButton("OK", (dialogInterface, i) -> {
                    setResult(333, new Intent());
                    finish();
                });
                builderSaved.show();
                builderSaved.create();
            });
        } else {
            SnackBar.showInternetError(this, snakBarView);
        }
    }

}