package com.varsity.dgmdashboard.api;

import com.varsity.dgmdashboard.model.AddDGMNoteRequest;
import com.varsity.dgmdashboard.model.AssignLeadManualRequest;
import com.varsity.dgmdashboard.model.AssignLeadRequest;
import com.varsity.dgmdashboard.model.CallDialFeedbackRequest;
import com.varsity.dgmdashboard.model.CallStatusListResponseModel;
import com.varsity.dgmdashboard.model.DGMDashboardResponse;
import com.varsity.dgmdashboard.model.DistrictsListResponseModel;
import com.varsity.dgmdashboard.model.GetDGMAssignedLeadsListResponse;
import com.varsity.dgmdashboard.model.GetDGMNoteListResponse;
import com.varsity.dgmdashboard.model.GetDashboardLeadResponse;
import com.varsity.dgmdashboard.model.GetNotificationCount;
import com.varsity.dgmdashboard.model.GetNotificationListResponse;
import com.varsity.dgmdashboard.model.GetPincodeResponse;
import com.varsity.dgmdashboard.model.GetProDetailsLeadResponse;
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
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("oauth/token?")
    Call<LoginResponseModel> getLoginUser(@Field("username") String username, @Field("password") String password, @Field("grant_type") String grantype, @Field("app_name") String app_name, @Field("deviceId") String deviceId, @Field("fcm_id") String fcm_id);

    @GET("scfollowup/v3/dgm/dashbord")
    Call<ArrayList<DGMDashboardResponse>> getDGMDashboardData();


    @GET("scfollowup/v3/pro/dashbord")
    Call<ArrayList<DGMDashboardResponse>> getPRODashboardData();

    @POST("scfollowup/leads?")
    Call<ArrayList<GetDashboardLeadResponse>> getDashboardLeadData();

    @GET("scfollowup/leadEntry/districts")
    Call<ArrayList<DistrictsListResponseModel>> getDistrictsList();

    @POST("scfollowup/lead/mobileNoByLead")
    Call<ArrayList<CallStatusListResponseModel>> getCallStatusList();

    @POST("scfollowup/statusWiseleads")
    Call<ArrayList<LeadStatusListResponseModel>> getStatusWiseLeadsList(@Query("pageNo") int pageNo, @Query("status") String status);

    @GET("scfollowup/mandalsByDistricts")
    Call<ArrayList<MandalListResponse>> getDistrictWiseMandalsList(@Query("districtId") Long districtIds);

    @GET("scfollowup/schoolNameSearch")
    Call<ArrayList<SchoolListResponseModel>> getSchoolNameList(@Query("schoolName") String schoolName);

    @POST("student/leadStatus")
    Call<ArrayList<StatusListResponseModel>> getLeadStatusList();

    @PUT("scfollowup/v2/addressColl/leadUpdate")
    Call<Long> saveLeadEntryFormData(@Body SaveLeadEntryFormRequest request);

    @POST("scfollowup/saveFollowUp")
    Call<Void> addProNoteData(@Body ProAddNoteRequest request);

    @POST("scfollowup/leadMandals")
    Call<ArrayList<GetDGMAssignedLeadsListResponse>> getDGMAssignedLeads();

    @POST("scfollowup/leadsStatusEmpWise")
    Call<ArrayList<GetProDetailsLeadResponse>> getDGMProDetails();

    @GET("scfollowup/dgm/notes/{empID}")
    Call<ArrayList<GetDGMNoteListResponse>> getDGMNoteList(@Path("empID") String empID);

    @POST("scfollowup/dgm/notes")
    Call<Void> addDGMNote(@Body AddDGMNoteRequest request);

    @POST("scfollowup/assignLeadToPro")
    Call<Void> submitLead(@Body AssignLeadRequest request);

    @POST("scfollowup/assignLeadMannualToPro")
    Call<Void> submitLeadManual(@Body AssignLeadManualRequest request);

    @GET("scfollowup/pro/assignedLeadNotification/{empID}")
    Call<ArrayList<GetNotificationListResponse>> getNotificationList(@Path("empID") String empID);

    @GET("scfollowup/pro/assignedLeadNotificationUnread/{empID}")
    Call<GetNotificationCount> getNotificationCount(@Path("empID") String empID);

    @GET("scfollowup/pro/updateAllNotificationStatus/{empID}")
    Call<ArrayList<UpdateNotificationResponse>> updateNotificationData(@Path("empID") String empID);

    @POST("scfollowup/saveFollowUp")
    Call<Void> saveDoorStepData(@Body SaveDoorStepData requestData);

    @GET("scfollowup/adrressColl/pincode")
    Call<GetPincodeResponse> getPinCodeList(@Query("pincode") String pincode);


    @POST("scfollowup/saveFollowUp")
    Call<Void> addCallDialFeedback(@Body CallDialFeedbackRequest request);
}
