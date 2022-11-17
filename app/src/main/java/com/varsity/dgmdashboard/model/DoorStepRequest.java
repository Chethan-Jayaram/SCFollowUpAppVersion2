package com.varsity.dgmdashboard.model;

public class DoorStepRequest {
    private Long appNo;
    private int assignedTo;
    private String checkIn;
    private String checkOut;
    private String comment;
    private String feedBack;
    private boolean followupStatus;
    private double lat;
    private double log;
    private int leadId;
    private int leadStatusId;
    private String mobileNo;

    public Long getAppNo() {
        return appNo;
    }

    public void setAppNo(Long appNo) {
        this.appNo = appNo;
    }

    public int getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(int assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

    public boolean isFollowupStatus() {
        return followupStatus;
    }

    public void setFollowupStatus(boolean followupStatus) {
        this.followupStatus = followupStatus;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLog() {
        return log;
    }

    public void setLog(double log) {
        this.log = log;
    }

    public int getLeadId() {
        return leadId;
    }

    public void setLeadId(int leadId) {
        this.leadId = leadId;
    }

    public int getLeadStatusId() {
        return leadStatusId;
    }

    public void setLeadStatusId(int leadStatusId) {
        this.leadStatusId = leadStatusId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
