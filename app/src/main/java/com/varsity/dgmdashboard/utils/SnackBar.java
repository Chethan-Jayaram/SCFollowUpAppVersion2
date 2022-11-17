package com.varsity.dgmdashboard.utils;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import com.google.android.material.snackbar.Snackbar;
import com.varsity.dgmdashboard.R;


public class SnackBar {


    public static void showInternetError(Context context, View view) {
        Snackbar snackbar = Snackbar
                .make(view, "Please check your internet connection.", Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(context, R.color.warning));
        snackbar.show();
    }

    public static void showValidationError(Context context, View view, String msg) {
        Snackbar snackbar = Snackbar
                .make(view, msg, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        TextView textView = (TextView) snackbarView.findViewById(R.id.snackbar_text);
        textView.setMaxLines(5);
        snackbarView.setBackgroundColor(ContextCompat.getColor(context, R.color.warning));
        snackbar.show();
    }


    public static void showError(Context context, View view, String msg) {
        Snackbar snackbar = Snackbar
                .make(view, msg, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        TextView textView = (TextView) snackbarView.findViewById(R.id.snackbar_text);
        textView.setMaxLines(5);
        snackbarView.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
        snackbar.show();
    }

    public static void showSuccess(Context context, View view, String msg) {
        Snackbar snackbar = Snackbar
                .make(view, msg, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        TextView textView = (TextView) snackbarView.findViewById(R.id.snackbar_text);
        textView.setMaxLines(5);
        snackbarView.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
        snackbar.show();
    }


}
