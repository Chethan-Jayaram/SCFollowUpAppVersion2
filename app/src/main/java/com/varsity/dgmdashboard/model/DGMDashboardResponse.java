package com.varsity.dgmdashboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DGMDashboardResponse {

    @SerializedName("studentCampingName")
    @Expose
    private String studentCampingName;
    @SerializedName("total")
    @Expose
    private Total total;
    @SerializedName("districtsList")
    @Expose
    private ArrayList<Districts> districtsList = null;

    public String getStudentCampingName() {
        return studentCampingName;
    }

    public void setStudentCampingName(String studentCampingName) {
        this.studentCampingName = studentCampingName;
    }

    public Total getTotal() {
        return total;
    }

    public void setTotal(Total total) {
        this.total = total;
    }

    public ArrayList<Districts> getDistrictsList() {
        return districtsList;
    }

    public void setDistrictsList(ArrayList<Districts> districtsList) {
        this.districtsList = districtsList;
    }


    public class Dispostions {

        @SerializedName("dispostionName")
        @Expose
        private String dispostionName;
        @SerializedName("count")
        @Expose
        private Integer count;
        @SerializedName("percentage")
        @Expose
        private Double percentage;
        @SerializedName("colourCode")
        @Expose
        private String colourCode;

        public String getColourCode() {
            return colourCode;
        }

        public void setColourCode(String colourCode) {
            this.colourCode = colourCode;
        }
        public String getDispostionName() {
            return dispostionName;
        }

        public void setDispostionName(String dispostionName) {
            this.dispostionName = dispostionName;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public Double getPercentage() {
            return percentage;
        }

        public void setPercentage(Double percentage) {
            this.percentage = percentage;
        }
    }

    public class Districts {

        @SerializedName("districtName")
        @Expose
        private String districtName;
        @SerializedName("pending")
        @Expose
        private Integer pending;
        @SerializedName("notAssigned")
        @Expose
        private Integer notAssigned;
        @SerializedName("assigned")
        @Expose
        private Integer assigned;
        @SerializedName("communicated")
        @Expose
        private Integer communicated;
        @SerializedName("joined")
        @Expose
        private Integer joined;
        @SerializedName("todays")
        @Expose
        private Integer todays;
        @SerializedName("notCommunicated")
        @Expose
        private Integer notCommunicated;
        @SerializedName("pendingPer")
        @Expose
        private Double pendingPer;
        @SerializedName("notAssignedPer")
        @Expose
        private Double notAssignedPer;
        @SerializedName("assignedPer")
        @Expose
        private Double assignedPer;
        @SerializedName("communicatedPer")
        @Expose
        private Double communicatedPer;
        @SerializedName("joinedPer")
        @Expose
        private Double joinedPer;
        @SerializedName("todaysPer")
        @Expose
        private Double todaysPer;
        @SerializedName("notCommunicatedPer")
        @Expose
        private Double notCommunicatedPer;
        @SerializedName("pendingColorCode")
        @Expose
        private String pendingColorCode;
        @SerializedName("notAssignedColorCode")
        @Expose
        private String notAssignedColorCode;
        @SerializedName("assignedColorCode")
        @Expose
        private String assignedColorCode;
        @SerializedName("communicatedColorCode")
        @Expose
        private String communicatedColorCode;
        @SerializedName("joinedColorCode")
        @Expose
        private String joinedColorCode;
        @SerializedName("todaysColorCode")
        @Expose
        private String todaysColorCode;
        @SerializedName("notCommunicatedColorCode")
        @Expose
        private String notCommunicatedColorCode;
        @SerializedName("callStatusList")
        @Expose
        private ArrayList<CallStatus> callStatusList = null;
        @SerializedName("dispostionsList")
        @Expose
        private ArrayList<Dispostions> dispostionsList = null;

        public String getDistrictName() {
            return districtName;
        }

        public void setDistrictName(String districtName) {
            this.districtName = districtName;
        }

        public Integer getPending() {
            return pending;
        }

        public void setPending(Integer pending) {
            this.pending = pending;
        }

        public Integer getNotAssigned() {
            return notAssigned;
        }

        public void setNotAssigned(Integer notAssigned) {
            this.notAssigned = notAssigned;
        }

        public Integer getAssigned() {
            return assigned;
        }

        public void setAssigned(Integer assigned) {
            this.assigned = assigned;
        }

        public Integer getCommunicated() {
            return communicated;
        }

        public void setCommunicated(Integer communicated) {
            this.communicated = communicated;
        }

        public Integer getJoined() {
            return joined;
        }

        public void setJoined(Integer joined) {
            this.joined = joined;
        }

        public Integer getTodays() {
            return todays;
        }

        public void setTodays(Integer todays) {
            this.todays = todays;
        }

        public Integer getNotCommunicated() {
            return notCommunicated;
        }

        public void setNotCommunicated(Integer notCommunicated) {
            this.notCommunicated = notCommunicated;
        }

        public Double getPendingPer() {
            return pendingPer;
        }

        public void setPendingPer(Double pendingPer) {
            this.pendingPer = pendingPer;
        }

        public Double getNotAssignedPer() {
            return notAssignedPer;
        }

        public void setNotAssignedPer(Double notAssignedPer) {
            this.notAssignedPer = notAssignedPer;
        }

        public Double getAssignedPer() {
            return assignedPer;
        }

        public void setAssignedPer(Double assignedPer) {
            this.assignedPer = assignedPer;
        }

        public Double getCommunicatedPer() {
            return communicatedPer;
        }

        public void setCommunicatedPer(Double communicatedPer) {
            this.communicatedPer = communicatedPer;
        }

        public Double getJoinedPer() {
            return joinedPer;
        }

        public void setJoinedPer(Double joinedPer) {
            this.joinedPer = joinedPer;
        }

        public Double getTodaysPer() {
            return todaysPer;
        }

        public void setTodaysPer(Double todaysPer) {
            this.todaysPer = todaysPer;
        }

        public Double getNotCommunicatedPer() {
            return notCommunicatedPer;
        }

        public void setNotCommunicatedPer(Double notCommunicatedPer) {
            this.notCommunicatedPer = notCommunicatedPer;
        }

        public String getPendingColorCode() {
            return pendingColorCode;
        }

        public void setPendingColorCode(String pendingColorCode) {
            this.pendingColorCode = pendingColorCode;
        }

        public String getNotAssignedColorCode() {
            return notAssignedColorCode;
        }

        public void setNotAssignedColorCode(String notAssignedColorCode) {
            this.notAssignedColorCode = notAssignedColorCode;
        }

        public String getAssignedColorCode() {
            return assignedColorCode;
        }

        public void setAssignedColorCode(String assignedColorCode) {
            this.assignedColorCode = assignedColorCode;
        }

        public String getCommunicatedColorCode() {
            return communicatedColorCode;
        }

        public void setCommunicatedColorCode(String communicatedColorCode) {
            this.communicatedColorCode = communicatedColorCode;
        }

        public String getJoinedColorCode() {
            return joinedColorCode;
        }

        public void setJoinedColorCode(String joinedColorCode) {
            this.joinedColorCode = joinedColorCode;
        }

        public String getTodaysColorCode() {
            return todaysColorCode;
        }

        public void setTodaysColorCode(String todaysColorCode) {
            this.todaysColorCode = todaysColorCode;
        }

        public String getNotCommunicatedColorCode() {
            return notCommunicatedColorCode;
        }

        public void setNotCommunicatedColorCode(String notCommunicatedColorCode) {
            this.notCommunicatedColorCode = notCommunicatedColorCode;
        }

        public ArrayList<CallStatus> getCallStatusList() {
            return callStatusList;
        }

        public void setCallStatusList(ArrayList<CallStatus> callStatusList) {
            this.callStatusList = callStatusList;
        }

        public ArrayList<Dispostions> getDispostionsList() {
            return dispostionsList;
        }

        public void setDispostionsList(ArrayList<Dispostions> dispostionsList) {
            this.dispostionsList = dispostionsList;
        }
    }

    public class Total {

        @SerializedName("pending")
        @Expose
        private Integer pending;
        @SerializedName("notAssigned")
        @Expose
        private Integer notAssigned;
        @SerializedName("assigned")
        @Expose
        private Integer assigned;
        @SerializedName("communicated")
        @Expose
        private Integer communicated;
        @SerializedName("joined")
        @Expose
        private Integer joined;
        @SerializedName("todays")
        @Expose
        private Integer todays;
        @SerializedName("notCommunicated")
        @Expose
        private Integer notCommunicated;

        public Integer getPending() {
            return pending;
        }

        public void setPending(Integer pending) {
            this.pending = pending;
        }

        public Integer getNotAssigned() {
            return notAssigned;
        }

        public void setNotAssigned(Integer notAssigned) {
            this.notAssigned = notAssigned;
        }

        public Integer getAssigned() {
            return assigned;
        }

        public void setAssigned(Integer assigned) {
            this.assigned = assigned;
        }

        public Integer getCommunicated() {
            return communicated;
        }

        public void setCommunicated(Integer communicated) {
            this.communicated = communicated;
        }

        public Integer getJoined() {
            return joined;
        }

        public void setJoined(Integer joined) {
            this.joined = joined;
        }

        public Integer getTodays() {
            return todays;
        }

        public void setTodays(Integer todays) {
            this.todays = todays;
        }

        public Integer getNotCommunicated() {
            return notCommunicated;
        }

        public void setNotCommunicated(Integer notCommunicated) {
            this.notCommunicated = notCommunicated;
        }

    }

    public class CallStatus {

        @SerializedName("callStatusName")
        @Expose
        private String callStatusName;
        @SerializedName("count")
        @Expose
        private Integer count;
        @SerializedName("percentage")
        @Expose
        private Double percentage;
        @SerializedName("colourCode")
        @Expose
        private String colourCode;

        public String getColourCode() {
            return colourCode;
        }

        public void setColourCode(String colourCode) {
            this.colourCode = colourCode;
        }

        public String getCallStatusName() {
            return callStatusName;
        }

        public void setCallStatusName(String callStatusName) {
            this.callStatusName = callStatusName;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public Double getPercentage() {
            return percentage;
        }

        public void setPercentage(Double percentage) {
            this.percentage = percentage;
        }

    }
}


