package com.varsity.dgmdashboard.viewmodel;

import static com.varsity.dgmdashboard.DGMDashboardApplication.getPreferencesManager;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.varsity.dgmdashboard.DGMDashboardApplication;
import com.varsity.dgmdashboard.activity.LoginActivity;
import com.varsity.dgmdashboard.api.ApiInterface;
import com.varsity.dgmdashboard.model.AddDGMNoteRequest;
import com.varsity.dgmdashboard.model.AssignLeadManualRequest;
import com.varsity.dgmdashboard.model.AssignLeadRequest;
import com.varsity.dgmdashboard.model.DGMDashboardResponse;
import com.varsity.dgmdashboard.model.DistrictsListResponseModel;
import com.varsity.dgmdashboard.model.ErrorMessageModel;
import com.varsity.dgmdashboard.model.GetDGMAssignedLeadsListResponse;
import com.varsity.dgmdashboard.model.GetDGMNoteListResponse;
import com.varsity.dgmdashboard.model.GetProDetailsLeadResponse;
import com.varsity.dgmdashboard.model.ProAddNoteRequest;
import com.varsity.dgmdashboard.model.SaveDoorStepData;
import com.varsity.dgmdashboard.utils.Helper;
import com.varsity.dgmdashboard.utils.SnackBar;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DGMDashboardRepo {

    private void logout(Context context){
        getPreferencesManager().clearData();
        Intent intent=new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(new Intent(context, LoginActivity.class));
    }
    public MutableLiveData<ArrayList<DGMDashboardResponse>> getDGMDashboardData(Context context, View snakBarView) {

        Helper.showPopupProgress(context,true);
        final MutableLiveData<ArrayList<DGMDashboardResponse>> mutableLiveData = new MutableLiveData<>();
        ApiInterface apiService = DGMDashboardApplication.getRetrofitClient().create(ApiInterface.class);
        apiService.getDGMDashboardData().enqueue(new Callback<ArrayList<DGMDashboardResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<DGMDashboardResponse>> call, Response<ArrayList<DGMDashboardResponse>> response) {
                Helper.showPopupProgress(context,false);
                if (response.isSuccessful() && response.body()!=null ) {
                    if (response.code()==200){
                        mutableLiveData.setValue(response.body());
                    }else if(response.code()==500){
                        SnackBar.showError(context, snakBarView, "Access Token expire");
                        logout(context);
                    }else {
                        try {
                            if (response.errorBody()!=null){
                                ErrorMessageModel errorMessageModel = new Gson().fromJson(response.errorBody().string(), ErrorMessageModel.class);
                                SnackBar.showError(context, snakBarView, errorMessageModel.getError_description());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }else{
                    try {
                        if(response.code()==500){
                            SnackBar.showError(context, snakBarView, "Access Token expire");
                            logout(context);
                        }else {
                            if (response.errorBody()!=null){
                                ErrorMessageModel errorMessageModel = new Gson().fromJson(response.errorBody().string(), ErrorMessageModel.class);
                                SnackBar.showError(context, snakBarView, errorMessageModel.getError());
                            }
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DGMDashboardResponse>> call, Throwable t) {
                Helper.showPopupProgress(context,false);
                if (t.getMessage() != null) {
                    SnackBar.showError(context, snakBarView, t.getMessage());
                }
            }
        });
        return mutableLiveData;
    }


    public MutableLiveData<ArrayList<GetDGMAssignedLeadsListResponse>> getAssignedLeadsList(Context context, View snakBarView) {

        Helper.showPopupProgress(context,true);
        final MutableLiveData<ArrayList<GetDGMAssignedLeadsListResponse>> mutableLiveData = new MutableLiveData<>();
        ApiInterface apiService = DGMDashboardApplication.getRetrofitClient().create(ApiInterface.class);
        apiService.getDGMAssignedLeads().enqueue(new Callback<ArrayList<GetDGMAssignedLeadsListResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<GetDGMAssignedLeadsListResponse>> call, Response<ArrayList<GetDGMAssignedLeadsListResponse>> response) {
                Helper.showPopupProgress(context,false);
                if (response.isSuccessful() && response.body()!=null ) {
                    if (response.code()==200){
                        mutableLiveData.setValue(response.body());
                    }else {
                        try {
                            assert response.errorBody() != null;
                            ErrorMessageModel errorMessageModel = new Gson().fromJson(response.errorBody().string(), ErrorMessageModel.class);
                            SnackBar.showError(context, snakBarView, errorMessageModel.getError_description());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }else{
                    try {
                        assert response.errorBody() != null;
                        ErrorMessageModel errorMessageModel = new Gson().fromJson(response.errorBody().string(), ErrorMessageModel.class);
                        SnackBar.showError(context, snakBarView, errorMessageModel.getError());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetDGMAssignedLeadsListResponse>> call, Throwable t) {
                Helper.showPopupProgress(context,false);
                if (t.getMessage() != null) {
                    SnackBar.showError(context, snakBarView, t.getMessage());
                }
            }
        });
        return mutableLiveData;
    }


    public MutableLiveData<ArrayList<GetProDetailsLeadResponse>> getDGMProDetails(Context context, View snakBarView) {

        Helper.showPopupProgress(context,true);
        final MutableLiveData<ArrayList<GetProDetailsLeadResponse>> mutableLiveData = new MutableLiveData<>();
        ApiInterface apiService = DGMDashboardApplication.getRetrofitClient().create(ApiInterface.class);
        apiService.getDGMProDetails().enqueue(new Callback<ArrayList<GetProDetailsLeadResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<GetProDetailsLeadResponse>> call, Response<ArrayList<GetProDetailsLeadResponse>> response) {
                Helper.showPopupProgress(context,false);
                if (response.isSuccessful() && response.body()!=null ) {
                    if (response.code()==200){
                        mutableLiveData.setValue(response.body());
                    }else {
                        try {
                            assert response.errorBody() != null;
                            ErrorMessageModel errorMessageModel = new Gson().fromJson(response.errorBody().string(), ErrorMessageModel.class);
                            SnackBar.showError(context, snakBarView, errorMessageModel.getError_description());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }else{
                    try {
                        assert response.errorBody() != null;
                        ErrorMessageModel errorMessageModel = new Gson().fromJson(response.errorBody().string(), ErrorMessageModel.class);
                        SnackBar.showError(context, snakBarView, errorMessageModel.getError());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetProDetailsLeadResponse>> call, Throwable t) {
                Helper.showPopupProgress(context,false);
                if (t.getMessage() != null) {
                    SnackBar.showError(context, snakBarView, t.getMessage());
                }
            }
        });
        return mutableLiveData;
    }


    public MutableLiveData<ArrayList<GetDGMNoteListResponse>> getDGMNoteList(Context context, View snakBarView,String empID) {

        Helper.showPopupProgress(context,true);
        final MutableLiveData<ArrayList<GetDGMNoteListResponse>> mutableLiveData = new MutableLiveData<>();
        ApiInterface apiService = DGMDashboardApplication.getRetrofitClient().create(ApiInterface.class);
        apiService.getDGMNoteList(empID).enqueue(new Callback<ArrayList<GetDGMNoteListResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<GetDGMNoteListResponse>> call, Response<ArrayList<GetDGMNoteListResponse>> response) {
                Helper.showPopupProgress(context,false);
                if (response.isSuccessful() && response.body()!=null ) {
                    if (response.code()==200){
                        mutableLiveData.setValue(response.body());
                    }else {
                        try {
                            assert response.errorBody() != null;
                            ErrorMessageModel errorMessageModel = new Gson().fromJson(response.errorBody().string(), ErrorMessageModel.class);
                            SnackBar.showError(context, snakBarView, errorMessageModel.getError_description());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }else{
                    try {
                        assert response.errorBody() != null;
                        ErrorMessageModel errorMessageModel = new Gson().fromJson(response.errorBody().string(), ErrorMessageModel.class);
                        SnackBar.showError(context, snakBarView, errorMessageModel.getError());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetDGMNoteListResponse>> call, Throwable t) {
                Helper.showPopupProgress(context,false);
                if (t.getMessage() != null) {
                    SnackBar.showError(context, snakBarView, t.getMessage());
                }
            }
        });
        return mutableLiveData;
    }


    public MutableLiveData<Void> requestAddDGMNotesData(Context context, View snakBarView, AddDGMNoteRequest request) {

        Helper.showPopupProgress(context,true);
        final MutableLiveData<Void> mutableLiveData = new MutableLiveData<>();
        ApiInterface apiService = DGMDashboardApplication.getRetrofitClient().create(ApiInterface.class);
        apiService.addDGMNote(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Helper.showPopupProgress(context,false);
                if (response.code()==200){
                    mutableLiveData.setValue(response.body());
                }else {
                    try {
                        assert response.errorBody() != null;
                        ErrorMessageModel errorMessageModel = new Gson().fromJson(response.errorBody().string(), ErrorMessageModel.class);
                        SnackBar.showError(context, snakBarView, errorMessageModel.getError_description());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Helper.showPopupProgress(context,false);
                if (t.getMessage() != null) {
                    SnackBar.showError(context, snakBarView, t.getMessage());
                }
            }
        });
        return mutableLiveData;
    }

    public MutableLiveData<ArrayList<DistrictsListResponseModel>> requestGetDistrictList(Context context, View snakBarView) {

        Helper.showPopupProgress(context,true);
        final MutableLiveData<ArrayList<DistrictsListResponseModel>> mutableLiveData = new MutableLiveData<>();
        ApiInterface apiService = DGMDashboardApplication.getRetrofitClient().create(ApiInterface.class);
        apiService.getDistrictsList().enqueue(new Callback<ArrayList<DistrictsListResponseModel>>() {
            @Override
            public void onResponse(Call<ArrayList<DistrictsListResponseModel>> call, Response<ArrayList<DistrictsListResponseModel>> response) {
                Helper.showPopupProgress(context,false);
                if (response.isSuccessful() && response.body()!=null ) {
                    if (response.code()==200){
                        mutableLiveData.setValue(response.body());
                    }else {
                        try {
                            assert response.errorBody() != null;
                            ErrorMessageModel errorMessageModel = new Gson().fromJson(response.errorBody().string(), ErrorMessageModel.class);
                            SnackBar.showError(context, snakBarView, errorMessageModel.getError_description());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }else{
                    try {
                        assert response.errorBody() != null;
                        ErrorMessageModel errorMessageModel = new Gson().fromJson(response.errorBody().string(), ErrorMessageModel.class);
                        SnackBar.showError(context, snakBarView, errorMessageModel.getError());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DistrictsListResponseModel>> call, Throwable t) {
                Helper.showPopupProgress(context,false);
                if (t.getMessage() != null) {
                    SnackBar.showError(context, snakBarView, t.getMessage());
                }
            }
        });
        return mutableLiveData;
    }


    public MutableLiveData<Void> requestSubmitLead(Context context, View snakBarView, AssignLeadRequest request) {

        Helper.showPopupProgress(context,true);
        final MutableLiveData<Void> mutableLiveData = new MutableLiveData<>();
        ApiInterface apiService = DGMDashboardApplication.getRetrofitClient().create(ApiInterface.class);
        apiService.submitLead(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Helper.showPopupProgress(context,false);
                if (response.code()==200){
                    mutableLiveData.setValue(response.body());
                }else {
                    try {
                        assert response.errorBody() != null;
                        ErrorMessageModel errorMessageModel = new Gson().fromJson(response.errorBody().string(), ErrorMessageModel.class);
                        SnackBar.showError(context, snakBarView, errorMessageModel.getError_description());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Helper.showPopupProgress(context,false);
                if (t.getMessage() != null) {
                    SnackBar.showError(context, snakBarView, t.getMessage());
                }
            }
        });
        return mutableLiveData;
    }

    public MutableLiveData<Void> requestSubmitLeadManual(Context context, View snakBarView, AssignLeadManualRequest request) {

        Helper.showPopupProgress(context,true);
        final MutableLiveData<Void> mutableLiveData = new MutableLiveData<>();
        ApiInterface apiService = DGMDashboardApplication.getRetrofitClient().create(ApiInterface.class);

        for (int i = 0;i<request.getAssignTo().size();i++){
            if (request.getAssignTo().get(i).getLeadCount() == 0){
                request.getAssignTo().get(i).setLeadCount(1);
            }
        }
        apiService.submitLeadManual(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Helper.showPopupProgress(context,false);
                if (response.code()==200){
                    mutableLiveData.setValue(response.body());
                }else {
                    try {
                        assert response.errorBody() != null;
                        ErrorMessageModel errorMessageModel = new Gson().fromJson(response.errorBody().string(), ErrorMessageModel.class);
                        SnackBar.showError(context, snakBarView, errorMessageModel.getError_description());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Helper.showPopupProgress(context,false);
                if (t.getMessage() != null) {
                    SnackBar.showError(context, snakBarView, t.getMessage());
                }
            }
        });
        return mutableLiveData;
    }




}
