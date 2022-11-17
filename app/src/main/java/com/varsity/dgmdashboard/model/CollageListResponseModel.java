package com.varsity.dgmdashboard.model;

import java.util.List;

public class CollageListResponseModel {

    private List<CollageData> nationalWiseColleges;
    private List<CollageData> apWiseCollges;
    private List<CollageData> tsWsieColleges;
    private List<CollageData> apAndTsWiseColleges;

    public List<CollageData> getNationalWiseColleges() {
        return nationalWiseColleges;
    }

    public void setNationalWiseColleges(List<CollageData> nationalWiseColleges) {
        this.nationalWiseColleges = nationalWiseColleges;
    }

    public List<CollageData> getApWiseCollges() {
        return apWiseCollges;
    }

    public void setApWiseCollges(List<CollageData> apWiseCollges) {
        this.apWiseCollges = apWiseCollges;
    }

    public List<CollageData> getTsWsieColleges() {
        return tsWsieColleges;
    }

    public void setTsWsieColleges(List<CollageData> tsWsieColleges) {
        this.tsWsieColleges = tsWsieColleges;
    }

    public List<CollageData> getApAndTsWiseColleges() {
        return apAndTsWiseColleges;
    }

    public void setApAndTsWiseColleges(List<CollageData> apAndTsWiseColleges) {
        this.apAndTsWiseColleges = apAndTsWiseColleges;
    }

    public static class CollageData{
        private String state;
        private String university;
        private String college;
        private String category;
        private String marks;
        private String gender;
        private String local;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getUniversity() {
            return university;
        }

        public void setUniversity(String university) {
            this.university = university;
        }

        public String getCollege() {
            return college;
        }

        public void setCollege(String college) {
            this.college = college;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getMarks() {
            return marks;
        }

        public void setMarks(String marks) {
            this.marks = marks;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getLocal() {
            return local;
        }

        public void setLocal(String local) {
            this.local = local;
        }
    }
}
