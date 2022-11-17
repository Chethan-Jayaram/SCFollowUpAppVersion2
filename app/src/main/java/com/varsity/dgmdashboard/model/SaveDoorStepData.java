package com.varsity.dgmdashboard.model;

public class SaveDoorStepData {
    private String comment;
    private String followUpType;
    private Long appNo;
    private long assignedTo;
    private String reminder;
    private String feedBack;
    private Long leadId;
    private String source;
    private boolean followupStatus;
    private String mobileNo;
    private String checkIn;
    private String checkOut;
    private Double lat;
    private Double log;
    private int leadStatusId =0;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFollowUpType() {
        return followUpType;
    }

    public void setFollowUpType(String followUpType) {
        this.followUpType = followUpType;
    }

    public Long getAppNo() {
        return appNo;
    }

    public void setAppNo(Long appNo) {
        this.appNo = appNo;
    }

    public long getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(long assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

    public Long getLeadId() {
        return leadId;
    }

    public void setLeadId(Long leadId) {
        this.leadId = leadId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isFollowupStatus() {
        return followupStatus;
    }

    public void setFollowupStatus(boolean followupStatus) {
        this.followupStatus = followupStatus;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
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

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLog() {
        return log;
    }

    public void setLog(Double log) {
        this.log = log;
    }

    public int getLeadStatusId() {
        return leadStatusId;
    }

    public void setLeadStatusId(int leadStatusId) {
        this.leadStatusId = leadStatusId;
    }
}
