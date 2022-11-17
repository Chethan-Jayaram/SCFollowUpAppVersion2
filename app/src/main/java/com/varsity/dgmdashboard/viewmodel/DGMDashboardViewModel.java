package com.varsity.dgmdashboard.viewmodel;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.varsity.dgmdashboard.model.AddDGMNoteRequest;
import com.varsity.dgmdashboard.model.AssignLeadManualRequest;
import com.varsity.dgmdashboard.model.AssignLeadRequest;
import com.varsity.dgmdashboard.model.CallStatusListResponseModel;
import com.varsity.dgmdashboard.model.DGMDashboardResponse;
import com.varsity.dgmdashboard.model.DistrictsListResponseModel;
import com.varsity.dgmdashboard.model.GetDGMAssignedLeadsListResponse;
import com.varsity.dgmdashboard.model.GetDGMNoteListResponse;
import com.varsity.dgmdashboard.model.GetDashboardLeadResponse;
import com.varsity.dgmdashboard.model.GetProDetailsLeadResponse;
import com.varsity.dgmdashboard.model.LeadStatusListResponseModel;
import com.varsity.dgmdashboard.model.LoginResponseModel;
import com.varsity.dgmdashboard.model.MandalListResponse;
import com.varsity.dgmdashboard.model.ProAddNoteRequest;
import com.varsity.dgmdashboard.model.SchoolListResponseModel;
import com.varsity.dgmdashboard.model.StatusListResponseModel;

import java.util.ArrayList;

public class DGMDashboardViewModel {

    private DGMDashboardRepo dashboardRepo;
    private MutableLiveData<ArrayList<DGMDashboardResponse>> dgmDashboardMutableLiveData;
    private MutableLiveData<ArrayList<GetDGMAssignedLeadsListResponse>> dgmGetAssignLeadsMutableLiveData;
    private MutableLiveData<ArrayList<GetProDetailsLeadResponse>> dgmGetProDetailsMutableLiveData;
    private MutableLiveData<ArrayList<GetDGMNoteListResponse>> dgmGetDGMNoteMutableLiveData;
    private MutableLiveData<Void> addDGMNotesMutableLiveData;
    private MutableLiveData<Void> submitLeadMutableLiveData;
    private MutableLiveData<ArrayList<DistrictsListResponseModel>> districtListMutableLiveData;
    private Context context;

    public DGMDashboardViewModel(Context context) {
        this.context = context;
        dashboardRepo = new DGMDashboardRepo();
    }

    public LiveData<ArrayList<DGMDashboardResponse>> getDGMDashboardData(View snakBarView) {
        return dgmDashboardMutableLiveData=dashboardRepo.getDGMDashboardData(context, snakBarView);
    }

    public LiveData<ArrayList<GetDGMAssignedLeadsListResponse>> getDGMAssignLeads(View snakBarView) {
        return dgmGetAssignLeadsMutableLiveData=dashboardRepo.getAssignedLeadsList(context, snakBarView);
    }

    public LiveData<ArrayList<GetProDetailsLeadResponse>> getDGMProDetailsData(View snakBarView) {
        return dgmGetProDetailsMutableLiveData=dashboardRepo.getDGMProDetails(context, snakBarView);
    }

    public LiveData<ArrayList<GetDGMNoteListResponse>> getDGMNoteListResponse(View snakBarView,String empId) {
        return dgmGetDGMNoteMutableLiveData=dashboardRepo.getDGMNoteList(context, snakBarView,empId);
    }

    public LiveData<Void> addDGMNotesData(View snakBarView, AddDGMNoteRequest request) {
        return addDGMNotesMutableLiveData=dashboardRepo.requestAddDGMNotesData(context, snakBarView, request);
    }

    public LiveData<ArrayList<DistrictsListResponseModel>> getDistrictListData(View snakBarView) {
        return districtListMutableLiveData=dashboardRepo.requestGetDistrictList(context, snakBarView);
    }

    public LiveData<Void> submitLead(View snakBarView, AssignLeadRequest request) {
        return submitLeadMutableLiveData=dashboardRepo.requestSubmitLead(context, snakBarView, request);
    }

    public LiveData<Void> submitLeadManual(View snakBarView, AssignLeadManualRequest request) {
        return submitLeadMutableLiveData=dashboardRepo.requestSubmitLeadManual(context, snakBarView, request);
    }
}
