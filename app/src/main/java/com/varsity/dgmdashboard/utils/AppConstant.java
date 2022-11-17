package com.varsity.dgmdashboard.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class AppConstant {
    public static String USER_DATA="user_data";
    public static String IS_LOGIN="is_login";
    public static String TOKEN = "token";
    public static String NOTIFICATION_COUNT = "notification_count";



    public static ArrayList<String> getInterestedCourseList(){
        ArrayList<String> list=new ArrayList<>();
        list.add("CIVILS");
        list.add("COMMERCE");
        list.add("ENGINEERING");
        list.add("MEDICINE");
        return list;
    }

    public static ArrayList<String> getGenderList(){
        ArrayList<String> list = new ArrayList<>();
        list.add("Male");
        list.add("Female");
        return list;
    }

    public static ArrayList<String> getClassList(){
        ArrayList<String> list=new ArrayList<>();
        list.add("Junior Intermediate");
        list.add("Senior Intermediate");
        list.add("Class I");
        list.add("Class II");
        list.add("Class III");
        list.add("Class IV");
        list.add("Class V");
        list.add("Class VI");
        list.add("Class VII");
        list.add("Class VIII");
        list.add("Class IX");
        list.add("Class X");
        list.add("Long Term");
        return list;
    }


    public static ArrayList<String> getYearList(){
        ArrayList<String> list=new ArrayList<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = -1; i <= 1; i++) {
            String year = new StringBuilder().append(String.valueOf(currentYear + i)).append("-").append(String.valueOf(currentYear + i + 1).substring(Math.max(String.valueOf(currentYear + i + 1).length() - 2, 0))).toString();
            list.add(year);
        }
        /*int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1900; i <= thisYear; i++) {
            list.add(String.valueOf(i));
        }
        Collections.reverse(list);*/

        return list;
    }

    public static ArrayList<String> getCategoryList(){
        ArrayList<String> list=new ArrayList<>();
        list.add("Select Category");
        list.add("OC");
        list.add("EWS");
        list.add("BC-A");
        list.add("BC-B");
        list.add("BC-C");
        list.add("BC-D");
        list.add("BC-E");
        list.add("SC");
        list.add("ST");
        return list;
    }

    public static ArrayList<String> getNationalCategoryList(){
        ArrayList<String> list=new ArrayList<>();
        list.add("Select Category");
        list.add("OC");
        list.add("EWS");
        list.add("OBC");
        list.add("SC");
        list.add("ST");
        return list;
    }


    public static ArrayList<String> getRegionList(){
        ArrayList<String> list=new ArrayList<>();
        list.add("Select Region");
        list.add("National");
        list.add("TS");
        list.add("AP");
        list.add("TS & AP");
        return list;
    }

    public static ArrayList<String> getSelectList(){
        ArrayList<String> list=new ArrayList<>();
        list.add("Select here");
        list.add("ANSWERED");
        list.add("NOT ANSWERED");
        list.add("REMIND");
        list.add("REJECTED");
        list.add("SWITCH OFF");
        list.add("BUSY");
        return list;
    }
}
