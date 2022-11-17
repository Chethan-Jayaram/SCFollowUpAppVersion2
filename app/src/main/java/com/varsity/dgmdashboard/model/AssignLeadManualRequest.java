package com.varsity.dgmdashboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AssignLeadManualRequest {

    @SerializedName("assignTo")
    @Expose
    private ArrayList<AssignTo> assignTo = null;

    public List<AssignTo> getAssignTo() {
        return assignTo;
    }

    public void setAssignTo(ArrayList<AssignTo> assignTo) {
        this.assignTo = assignTo;
    }

    public static class AssignTo {

        @SerializedName("empID")
        @Expose
        private Integer empID;

        @SerializedName("leadCount")
        @Expose
        private Integer leadCount;

        public Integer getLeadCount() {
            return leadCount;
        }

        public void setLeadCount(Integer leadCount) {
            this.leadCount = leadCount;
        }

        public Integer getEmpID() {
            return empID;
        }

        public void setEmpID(Integer empID) {
            this.empID = empID;
        }
    }
}


