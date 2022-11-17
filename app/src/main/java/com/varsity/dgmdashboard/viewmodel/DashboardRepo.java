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
import com.varsity.dgmdashboard.fragment.FollowUpFragment;
import com.varsity.dgmdashboard.model.CallDialFeedbackRequest;
import com.varsity.dgmdashboard.model.CallStatusListResponseModel;
import com.varsity.dgmdashboard.model.DGMDashboardResponse;
import com.varsity.dgmdashboard.model.DistrictsListResponseModel;
import com.varsity.dgmdashboard.model.ErrorMessageModel;
import com.varsity.dgmdashboard.model.GetDashboardLeadResponse;
import com.varsity.dgmdashboard.model.GetNotificationCount;
import com.varsity.dgmdashboard.model.GetNotificationListResponse;
import com.varsity.dgmdashboard.model.GetPincodeResponse;
import com.varsity.dgmdashboard.model.LeadStatusListResponseModel;
import com.varsity.dgmdashboard.model.LoginResponseModel;
import com.varsity.dgmdashboard.model.MandalListResponse;
import com.varsity.dgmdashboard.model.ProAddNoteRequest;
import com.varsity.dgmdashboard.model.SaveDoorStepData;
import com.varsity.dgmdashboard.model.SaveLeadEntryFormRequest;
import com.varsity.dgmdashboard.model.SchoolListResponseModel;
import com.varsity.dgmdashboard.model.StatusListResponseModel;
import com.varsity.dgmdashboard.model.UpdateNotificationResponse;
import com.varsity.dgmdashboard.utils.Helper;
import com.varsity.dgmdashboard.utils.MyUtils;
import com.varsity.dgmdashboard.utils.SnackBar;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardRepo {

    private void logout(Context context){
        getPreferencesManager().clearData();
        Intent intent=new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(new Intent(context, LoginActivity.class));
    }
    /**
     * Login API call request
     */
    public MutableLiveData<LoginResponseModel> requestLogin(Context context, String userName, String password, View snakBarView) {

        Helper.showPopupProgress(context,true);
        final MutableLiveData<LoginResponseModel> mutableLiveData = new MutableLiveData<>();
        ApiInterface apiService = DGMDashboardApplication.getRetrofitClient().create(ApiInterface.class);
        String deviceId= MyUtils.getDeviceId();
        apiService.getLoginUser(userName,password,"password","SCFollowUP APP",deviceId,"").enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
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
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                Helper.showPopupProgress(context,false);
                if (t.getMessage() != null) {
                    SnackBar.showError(context, snakBarView, t.getMessage());
                }
            }
        });
        return mutableLiveData;
    }


    /**
     * Get Dashboard leads data API call
     */
    public MutableLiveData<ArrayList<DGMDashboardResponse>> getPRODashboardData(Context context, View snakBarView) {

        Helper.showPopupProgress(context,true);
        final MutableLiveData<ArrayList<DGMDashboardResponse>> mutableLiveData = new MutableLiveData<>();
        ApiInterface apiService = DGMDashboardApplication.getRetrofitClient().create(ApiInterface.class);
        apiService.getPRODashboardData().enqueue(new Callback<ArrayList<DGMDashboardResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<DGMDashboardResponse>> call, Response<ArrayList<DGMDashboardResponse>> response) {
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


    /**
     * Get District data API call
     */
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


    /**
     * Get Call Status List API
     */
    public MutableLiveData<ArrayList<CallStatusListResponseModel>> requestGetCallStatusList(Context context, View snakBarView) {

        Helper.showPopupProgress(context,true);
        final MutableLiveData<ArrayList<CallStatusListResponseModel>> mutableLiveData = new MutableLiveData<>();
        ApiInterface apiService = DGMDashboardApplication.getRetrofitClient().create(ApiInterface.class);
        apiService.getCallStatusList().enqueue(new Callback<ArrayList<CallStatusListResponseModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CallStatusListResponseModel>> call, Response<ArrayList<CallStatusListResponseModel>> response) {
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

                }
            }

            @Override
            public void onFailure(Call<ArrayList<CallStatusListResponseModel>> call, Throwable t) {
                Helper.showPopupProgress(context,false);
                if (t.getMessage() != null) {
                    SnackBar.showError(context, snakBarView, t.getMessage());
                }
            }
        });
        return mutableLiveData;
    }

    /**
     * Get Call Status List API
     */
    public MutableLiveData<ArrayList<LeadStatusListResponseModel>> requestGetStatusWiseLeadsList(Context context, View snakBarView,String status) {

        Helper.showPopupProgress(context,true);
        final MutableLiveData<ArrayList<LeadStatusListResponseModel>> mutableLiveData = new MutableLiveData<>();
        ApiInterface apiService = DGMDashboardApplication.getRetrofitClient().create(ApiInterface.class);
        apiService.getStatusWiseLeadsList(0,status).enqueue(new Callback<ArrayList<LeadStatusListResponseModel>>() {
            @Override
            public void onResponse(Call<ArrayList<LeadStatusListResponseModel>> call, Response<ArrayList<LeadStatusListResponseModel>> response) {
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

                }
            }

            @Override
            public void onFailure(Call<ArrayList<LeadStatusListResponseModel>> call, Throwable t) {
                Helper.showPopupProgress(context,false);
                if (t.getMessage() != null) {
                    SnackBar.showError(context, snakBarView, t.getMessage());
                }
            }
        });
        return mutableLiveData;
    }


    public MutableLiveData<ArrayList<LeadStatusListResponseModel>> requestGetFollowupList(Context context, View snakBarView,int page,String status) {

        Helper.showPopupProgress(context,true);
        final MutableLiveData<ArrayList<LeadStatusListResponseModel>> mutableLiveData = new MutableLiveData<>();
        ApiInterface apiService = DGMDashboardApplication.getRetrofitClient().create(ApiInterface.class);
        apiService.getStatusWiseLeadsList(page,status).enqueue(new Callback<ArrayList<LeadStatusListResponseModel>>() {
            @Override
            public void onResponse(Call<ArrayList<LeadStatusListResponseModel>> call, Response<ArrayList<LeadStatusListResponseModel>> response) {
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

                }else {
                    FollowUpFragment.isLoadMore=false;
                }
            }

            @Override
            public void onFailure(Call<ArrayList<LeadStatusListResponseModel>> call, Throwable t) {
                Helper.showPopupProgress(context,false);
                if (t.getMessage() != null) {
                    SnackBar.showError(context, snakBarView, t.getMessage());
                }
            }
        });
        return mutableLiveData;
    }


    /**
     * Get District Wise Mandals List
     */
    public MutableLiveData<ArrayList<MandalListResponse>> requestGetDistrictWiseMandalsList(Context context, View snakBarView,Long districtIds,boolean isProgress) {

        Helper.showPopupProgress(context,isProgress);
        final MutableLiveData<ArrayList<MandalListResponse>> mutableLiveData = new MutableLiveData<>();
        ApiInterface apiService = DGMDashboardApplication.getRetrofitClient().create(ApiInterface.class);
        apiService.getDistrictWiseMandalsList(districtIds).enqueue(new Callback<ArrayList<MandalListResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<MandalListResponse>> call, Response<ArrayList<MandalListResponse>> response) {
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

                }
            }

            @Override
            public void onFailure(Call<ArrayList<MandalListResponse>> call, Throwable t) {
                Helper.showPopupProgress(context,false);
                if (t.getMessage() != null) {
                    SnackBar.showError(context, snakBarView, t.getMessage());
                }
            }
        });
        return mutableLiveData;
    }



    /**
     * Get School Name list
     */
    public MutableLiveData<ArrayList<SchoolListResponseModel>> requestGetSchoolNameList(Context context, View snakBarView, String schollname,boolean isProgress) {

        Helper.showPopupProgress(context,isProgress);
        final MutableLiveData<ArrayList<SchoolListResponseModel>> mutableLiveData = new MutableLiveData<>();
        ApiInterface apiService = DGMDashboardApplication.getRetrofitClient().create(ApiInterface.class);
        apiService.getSchoolNameList(schollname).enqueue(new Callback<ArrayList<SchoolListResponseModel>>() {
            @Override
            public void onResponse(Call<ArrayList<SchoolListResponseModel>> call, Response<ArrayList<SchoolListResponseModel>> response) {
                Helper.showPopupProgress(context,false);
                if (response.isSuccessful() && response.body()!=null ) {
                    if (response.code()==200){
                        mutableLiveData.setValue(response.body());
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
            }

            @Override
            public void onFailure(Call<ArrayList<SchoolListResponseModel>> call, Throwable t) {
                Helper.showPopupProgress(context,false);
                if (t.getMessage() != null) {
                    SnackBar.showError(context, snakBarView, t.getMessage());
                }
            }
        });
        return mutableLiveData;
    }


    public MutableLiveData<GetPincodeResponse> requestGetPinCodeList(Context context, View snakBarView,String pincode) {

        Helper.showPopupProgress(context,true);
        final MutableLiveData<GetPincodeResponse> mutableLiveData = new MutableLiveData<>();
        ApiInterface apiService = DGMDashboardApplication.getRetrofitClient().create(ApiInterface.class);
        apiService.getPinCodeList(pincode).enqueue(new Callback<GetPincodeResponse>() {
            @Override
            public void onResponse(Call<GetPincodeResponse> call, Response<GetPincodeResponse> response) {
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
            }

            @Override
            public void onFailure(Call<GetPincodeResponse> call, Throwable t) {
                Helper.showPopupProgress(context,false);
                if (t.getMessage() != null) {
                    SnackBar.showError(context, snakBarView, t.getMessage());
                }
            }
        });
        return mutableLiveData;
    }


    /**
     * Get Lead status list
     */
    public MutableLiveData<ArrayList<StatusListResponseModel>> requestGetLeadStatusList(Context context, View snakBarView) {

        Helper.showPopupProgress(context,true);
        final MutableLiveData<ArrayList<StatusListResponseModel>> mutableLiveData = new MutableLiveData<>();
        ApiInterface apiService = DGMDashboardApplication.getRetrofitClient().create(ApiInterface.class);
        apiService.getLeadStatusList().enqueue(new Callback<ArrayList<StatusListResponseModel>>() {
            @Override
            public void onResponse(Call<ArrayList<StatusListResponseModel>> call, Response<ArrayList<StatusListResponseModel>> response) {
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
            public void onFailure(Call<ArrayList<StatusListResponseModel>> call, Throwable t) {
                Helper.showPopupProgress(context,false);
                if (t.getMessage() != null) {
                    SnackBar.showError(context, snakBarView, t.getMessage());
                }
            }
        });
        return mutableLiveData;
    }



    /**
     * Save Lead entry data
     */
    public MutableLiveData<Long> requestSaveLeadEntryData(Context context, View snakBarView, SaveLeadEntryFormRequest request) {

        Helper.showPopupProgress(context,true);
        final MutableLiveData<Long> mutableLiveData = new MutableLiveData<>();
        ApiInterface apiService = DGMDashboardApplication.getRetrofitClient().create(ApiInterface.class);
        apiService.saveLeadEntryFormData(request).enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
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

                }else {
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
            public void onFailure(Call<Long> call, Throwable t) {
                Helper.showPopupProgress(context,false);
                if (t.getMessage() != null) {
                    SnackBar.showError(context, snakBarView, t.getMessage());
                }
            }
        });
        return mutableLiveData;
    }


    /**
     * Add pro notes data
     */
    public MutableLiveData<Void> requestAddProNotesData(Context context, View snakBarView, ProAddNoteRequest request) {

        Helper.showPopupProgress(context,true);
        final MutableLiveData<Void> mutableLiveData = new MutableLiveData<>();
        ApiInterface apiService = DGMDashboardApplication.getRetrofitClient().create(ApiInterface.class);
        apiService.addProNoteData(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Helper.showPopupProgress(context,false);
                if (response.code()==202 || response.code()==200){
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


    public MutableLiveData<ArrayList<GetNotificationListResponse>> requestGetNotificationList(Context context, View snakBarView, String empid) {

        Helper.showPopupProgress(context,true);
        final MutableLiveData<ArrayList<GetNotificationListResponse>> mutableLiveData = new MutableLiveData<>();
        ApiInterface apiService = DGMDashboardApplication.getRetrofitClient().create(ApiInterface.class);
        apiService.getNotificationList(empid).enqueue(new Callback<ArrayList<GetNotificationListResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<GetNotificationListResponse>> call, Response<ArrayList<GetNotificationListResponse>> response) {
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
            public void onFailure(Call<ArrayList<GetNotificationListResponse>> call, Throwable t) {
                Helper.showPopupProgress(context,false);
                if (t.getMessage() != null) {
                    SnackBar.showError(context, snakBarView, t.getMessage());
                }
            }
        });
        return mutableLiveData;
    }

    public MutableLiveData<ArrayList<UpdateNotificationResponse>> requestUpdateNotificationList(Context context, View snakBarView, String empid) {

        Helper.showPopupProgress(context,true);
        final MutableLiveData<ArrayList<UpdateNotificationResponse>> mutableLiveData = new MutableLiveData<>();
        ApiInterface apiService = DGMDashboardApplication.getRetrofitClient().create(ApiInterface.class);
        apiService.updateNotificationData(empid).enqueue(new Callback<ArrayList<UpdateNotificationResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<UpdateNotificationResponse>> call, Response<ArrayList<UpdateNotificationResponse>> response) {
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
            public void onFailure(Call<ArrayList<UpdateNotificationResponse>> call, Throwable t) {
                Helper.showPopupProgress(context,false);
                if (t.getMessage() != null) {
                    SnackBar.showError(context, snakBarView, t.getMessage());
                }
            }
        });
        return mutableLiveData;
    }


    public MutableLiveData<GetNotificationCount> requestGetNotificationCount(Context context, View snakBarView, String empid) {

        Helper.showPopupProgress(context,true);
        final MutableLiveData<GetNotificationCount> mutableLiveData = new MutableLiveData<>();
        ApiInterface apiService = DGMDashboardApplication.getRetrofitClient().create(ApiInterface.class);
        apiService.getNotificationCount(empid).enqueue(new Callback<GetNotificationCount>() {
            @Override
            public void onResponse(Call<GetNotificationCount> call, Response<GetNotificationCount> response) {
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
            public void onFailure(Call<GetNotificationCount> call, Throwable t) {
                Helper.showPopupProgress(context,false);
                if (t.getMessage() != null) {
                    SnackBar.showError(context, snakBarView, t.getMessage());
                }
            }
        });
        return mutableLiveData;
    }

    public MutableLiveData<Void> requestDoorStep(Context context, View snakBarView, SaveDoorStepData request) {

        Helper.showPopupProgress(context,true);
        final MutableLiveData<Void> mutableLiveData = new MutableLiveData<>();
        ApiInterface apiService = DGMDashboardApplication.getRetrofitClient().create(ApiInterface.class);
        apiService.saveDoorStepData(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Helper.showPopupProgress(context,false);
                if (response.code()==200 || response.code()==202){
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


    public MutableLiveData<Void> requestCallDialFeedback(Context context, View snakBarView, CallDialFeedbackRequest request) {

        Helper.showPopupProgress(context,true);
        final MutableLiveData<Void> mutableLiveData = new MutableLiveData<>();
        ApiInterface apiService = DGMDashboardApplication.getRetrofitClient().create(ApiInterface.class);
        apiService.addCallDialFeedback(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Helper.showPopupProgress(context,false);
                if (response.code()==200 || response.code()==202){
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
