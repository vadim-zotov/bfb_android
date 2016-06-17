package com.sphereinc.chairlift.common.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.LightingColorFilter;
import android.widget.ProgressBar;

import com.sphereinc.chairlift.R;

import java.util.ArrayList;
import java.util.List;

public class DialogUtils {
    private static List<ProgressDialog> dialogs = new ArrayList<>();

    public static void showDialog(Context context) {
        showDialog("", context);
    }

    public static void showDialog(String text, Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context,
                R.style.AppTheme_Dark_Dialog);

        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progBar.setIndeterminate(true);
//        progBar.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF, android.graphics.PorterDuff.Mode.MULTIPLY);

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(text);

        dialogs.add(progressDialog);
        progressDialog.show();
    }

    public static void hideProgressDialogs() {
        for (ProgressDialog dialog : dialogs) {
            dialog.dismiss();
        }
        dialogs.clear();
    }
}





