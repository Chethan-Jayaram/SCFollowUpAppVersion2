package com.varsity.dgmdashboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AssignLeadRequest {

    @SerializedName("assignTo")
    @Expose
    private ArrayList<AssignTo> assignTo = null;
    @SerializedName("totalLead")
    @Expose
    private Integer totalLead;

    public List<AssignTo> getAssignTo() {
        return assignTo;
    }

    public void setAssignTo(ArrayList<AssignTo> assignTo) {
        this.assignTo = assignTo;
    }

    public Integer getTotalLead() {
        return totalLead;
    }

    public void setTotalLead(Integer totalLead) {
        this.totalLead = totalLead;
    }

    public static class AssignTo {

        @SerializedName("empID")
        @Expose
        private Integer empID;

        public Integer getEmpID() {
            return empID;
        }

        public void setEmpID(Integer empID) {
            this.empID = empID;
        }
    }
}


