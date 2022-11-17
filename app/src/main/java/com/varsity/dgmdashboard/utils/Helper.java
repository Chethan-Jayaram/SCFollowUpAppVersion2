package com.varsity.dgmdashboard.utils;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.ProgressBar;

import androidx.appcompat.widget.AppCompatTextView;

import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.activity.LeadEntryActivity;

import java.io.File;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Helper {

    /*TODO Internet Check*/
    private static boolean isInternetAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //This for Wifi.
        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }

        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        }
        return false;
    }

    public static boolean isCheckInternet(Context context) {
        if (isInternetAvailable(context)) {
            return true;
        } else {
            MessageUtility.showToast(context, "No Internet Connection ");
            return false;
        }
    }

    public static boolean hasInternet(Context context) {
        if (isInternetAvailable(context)) {
            return true;
        } else {
            //MessageUtility.showToast(context, "No Internet Connection ");
            return false;
        }
    }

    private static Dialog progressDialog = null;
    private static ProgressBar progressBar;

    public static void showPopupProgress(Context context, Boolean isShowing) {
        if (isShowing == true) {
            if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog.dismiss();
                progressDialog = null;
            }
            progressDialog = new Dialog(context);
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progressDialog.setContentView(R.layout.popup_progressbar);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT));

            progressBar = (ProgressBar) progressDialog
                    .findViewById(R.id.progressBar1);
            progressBar.getIndeterminateDrawable().setColorFilter(
                    Color.parseColor("#03DAC5"),
                    android.graphics.PorterDuff.Mode.MULTIPLY);
            progressDialog.show();
        } else if (isShowing == false) {
            if (progressDialog!=null){
                progressDialog.dismiss();
            }

        }
    }



    // Delay mechanism

    public interface DelayCallback{
        void afterDelay();
    }

    public static void delay(int secs, final DelayCallback delayCallback){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                delayCallback.afterDelay();
            }
        }, secs * 1000); // afterDelay will be executed after (secs*1000) milliseconds.
    }

    public static void openDatePicker(Context context, AppCompatTextView textView) {
        int mYear, mMonth, mDay;
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DecimalFormat mFormat = new DecimalFormat("00");

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year, monthOfYear, dayOfMonth) -> textView.setText(mFormat.format(Double.valueOf(dayOfMonth)) + "-" + mFormat.format(Double.valueOf((monthOfYear + 1))) + "-" + year), mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
        datePickerDialog.show();

    }

    public static boolean isValidPhoneNumber(Context context, View view,String phoneNo){

        return true;
    }
}
