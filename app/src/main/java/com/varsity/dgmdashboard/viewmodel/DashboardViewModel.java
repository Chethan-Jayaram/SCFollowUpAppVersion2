package com.varsity.dgmdashboard.viewmodel;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.varsity.dgmdashboard.model.AssignLeadManualRequest;
import com.varsity.dgmdashboard.model.CallDialFeedbackRequest;
import com.varsity.dgmdashboard.model.CallStatusListResponseModel;
import com.varsity.dgmdashboard.model.DGMDashboardResponse;
import com.varsity.dgmdashboard.model.DistrictsListResponseModel;
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

import java.util.ArrayList;

public class DashboardViewModel extends ViewModel {

    private DashboardRepo dashboardRepo;
    private MutableLiveData<LoginResponseModel> loginMutableLiveData;
    private MutableLiveData<ArrayList<DGMDashboardResponse>> proDashboardMutableLiveData;
    private MutableLiveData<ArrayList<GetDashboardLeadResponse>> dashboardLeadDataMutableLiveData;
    private MutableLiveData<ArrayList<DistrictsListResponseModel>> districtListMutableLiveData;
    private MutableLiveData<ArrayList<CallStatusListResponseModel>> callStatusListMutableLiveData;
    private MutableLiveData<ArrayList<LeadStatusListResponseModel>> statusWiseLeadsListMutableLiveData;
    private MutableLiveData<ArrayList<MandalListResponse>> districtWiseMandalListMutableLiveData;
    private MutableLiveData<ArrayList<SchoolListResponseModel>> schoolNameListMutableLiveData;
    private MutableLiveData<GetPincodeResponse> pinCodeListMutableLiveData;
    private MutableLiveData<ArrayList<StatusListResponseModel>> leadStatusListMutableLiveData;
    private MutableLiveData<ArrayList<GetNotificationListResponse>> getNotificationMutableLiveData;
    private MutableLiveData<ArrayList<UpdateNotificationResponse>> updateNotificationMutableLiveData;
    private MutableLiveData<GetNotificationCount> getNotificationCountMutableLiveData;
    private MutableLiveData<Long> saveLeadEntryMutableLiveData;
    private MutableLiveData<Void> addProNotesMutableLiveData;
    private MutableLiveData<Void> saveDoorStepMutableLiveData;
    private Context context;

    public DashboardViewModel(Context context) {
        this.context = context;
        dashboardRepo = new DashboardRepo();
    }

    /**
     * Login API request call
     */
    public LiveData<LoginResponseModel> callLoginUser(String userName, String password, View snakBarView) {
        return loginMutableLiveData=dashboardRepo.requestLogin(context, userName, password, snakBarView);
    }

    public LiveData<ArrayList<DGMDashboardResponse>> getPRODashboardData(View snakBarView) {
        return proDashboardMutableLiveData=dashboardRepo.getPRODashboardData(context, snakBarView);
    }


    /**
     * District data API call
     */
    public LiveData<ArrayList<DistrictsListResponseModel>> getDistrictListData(View snakBarView) {
        return districtListMutableLiveData=dashboardRepo.requestGetDistrictList(context, snakBarView);
    }

    /**
     * Get Address followup list API
     */
    public LiveData<ArrayList<CallStatusListResponseModel>> getCallStatusList(View snakBarView) {
        return callStatusListMutableLiveData= dashboardRepo.requestGetCallStatusList(context, snakBarView);
    }


    public LiveData<ArrayList<LeadStatusListResponseModel>> getStatusWiseLeadsList(View snakBarView, String status) {
        return statusWiseLeadsListMutableLiveData= dashboardRepo.requestGetStatusWiseLeadsList(context, snakBarView, status);
    }

    public LiveData<ArrayList<LeadStatusListResponseModel>> getFollowupList(View snakBarView, String status,int page) {
        return statusWiseLeadsListMutableLiveData= dashboardRepo.requestGetFollowupList(context, snakBarView,page, status);
    }

    /**
     * Get District Wise Mandals List
     */
    public LiveData<ArrayList<MandalListResponse>> getMandalsList(View snakBarView, Long districtIds,boolean isProgress) {

        return dashboardRepo.requestGetDistrictWiseMandalsList(context, snakBarView, districtIds,isProgress);
    }


    /**
     * Get School Name
     */
    public LiveData<ArrayList<SchoolListResponseModel>> getSchoolNameList(View snakBarView, String schoolName,boolean isProgress) {
        return dashboardRepo.requestGetSchoolNameList(context, snakBarView, schoolName,isProgress);
    }

    public LiveData<GetPincodeResponse> getPinCodeList(View snakBarView,String pinCode) {
        return dashboardRepo.requestGetPinCodeList(context, snakBarView,pinCode);
    }


    /**
     * Get Lead status List
     */
    public LiveData<ArrayList<StatusListResponseModel>> getLeadStatusList(View snakBarView) {

        return dashboardRepo.requestGetLeadStatusList(context, snakBarView);
    }

    /**
     * Save Lead entry data
     */
    public LiveData<Long> saveLeadEntryData(View snakBarView, SaveLeadEntryFormRequest request) {

        return saveLeadEntryMutableLiveData= dashboardRepo.requestSaveLeadEntryData(context, snakBarView, request);
    }

    /**
     * Add Pro Notes data
     */
    public LiveData<Void> addProNotesData(View snakBarView, ProAddNoteRequest request) {
        return addProNotesMutableLiveData=dashboardRepo.requestAddProNotesData(context, snakBarView, request);
    }

    public LiveData<ArrayList<GetNotificationListResponse>> getNotificationList(View snakBarView, String empid) {
        if (getNotificationMutableLiveData == null) {
            getNotificationMutableLiveData = dashboardRepo.requestGetNotificationList(context, snakBarView, empid);
        }
        return getNotificationMutableLiveData;
    }

    public LiveData<ArrayList<UpdateNotificationResponse>> updateNotificationList(View snakBarView, String empid) {
        if (updateNotificationMutableLiveData == null) {
            updateNotificationMutableLiveData = dashboardRepo.requestUpdateNotificationList(context, snakBarView, empid);
        }
        return updateNotificationMutableLiveData;
    }

    public LiveData<GetNotificationCount> getNotificationCount(View snakBarView, String empid) {
        if (getNotificationCountMutableLiveData == null) {
            getNotificationCountMutableLiveData = dashboardRepo.requestGetNotificationCount(context, snakBarView, empid);
        }
        return getNotificationCountMutableLiveData;
    }



    public LiveData<Void> submitDoorStep(View snakBarView, SaveDoorStepData request) {
        return saveDoorStepMutableLiveData=dashboardRepo.requestDoorStep(context, snakBarView, request);
    }

    public LiveData<Void> submitCallDialFeedback(View snakBarView, CallDialFeedbackRequest request) {
        return saveDoorStepMutableLiveData=dashboardRepo.requestCallDialFeedback(context, snakBarView, request);
    }
}
