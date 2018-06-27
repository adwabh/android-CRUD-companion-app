package com.adwait.crud.presentation.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;

public class Alert {
    private final static AlertDialog.Builder createAlert(Activity context,
                                                         String title, String message) {

        AlertDialog.Builder dialog;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
            dialog = new AlertDialog.Builder(new ContextThemeWrapper(context,
                    android.R.style.Theme_Material_Light_Dialog_Alert));

        else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            dialog = new AlertDialog.Builder(new ContextThemeWrapper(context,
                    android.R.style.Theme_Holo_Light_Dialog));
        else
            dialog = new AlertDialog.Builder(context);

      //  dialog.setIcon(R.mipmap.ic_launcher);
        if (title != null)
            dialog.setTitle(title);
        else
            dialog.setTitle("Information");
        dialog.setMessage(message);
        dialog.setCancelable(false);
        return dialog;
    }
    private final static AlertDialog.Builder createCustomAlert(Activity context,
                                                               String title, String message, int layout) {

        AlertDialog.Builder dialog;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
            dialog = new AlertDialog.Builder(new ContextThemeWrapper(context,
                    android.R.style.Theme_Material_Light_Dialog_Alert));

        else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            dialog = new AlertDialog.Builder(new ContextThemeWrapper(context,
                    android.R.style.Theme_Holo_Light_Dialog));
        else
            dialog = new AlertDialog.Builder(context);

        LayoutInflater inflater = context.getLayoutInflater();
        View dialogView = inflater.inflate(layout, null);
        dialog.setView(dialogView);
        if (title != null)
            dialog.setTitle(title);
        else
            dialog.setTitle("Information");
        dialog.setMessage(message);
        dialog.setCancelable(false);
        return dialog;
    }
    public static void alert(Context context, String title, String message,
                             String negativeButton, String positiveButton,
                             final Runnable negativeRunnable, final Runnable positiveRunnable) {
        AlertDialog.Builder dialog = createAlert((Activity) context, title, message);
        if (negativeButton != null) {
            dialog.setNegativeButton(negativeButton,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            if (negativeRunnable != null)
                                negativeRunnable.run();
                        }
                    });
        }
        if (positiveButton != null) {
            dialog.setPositiveButton(positiveButton,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            if (positiveRunnable != null)
                                positiveRunnable.run();
                        }
                    });
        }
        dialog.show();
    } public static void alert(Context context, String title, String message,
                               String negativeButton, String positiveButton, String neutralButton,
                               final Runnable negativeRunnable, final Runnable positiveRunnable, final Runnable neutralRunnable) {
        AlertDialog.Builder dialog = createAlert((Activity) context, title, message);
        if (neutralButton != null) {
            dialog.setNeutralButton(neutralButton,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            if (neutralRunnable != null)
                                neutralRunnable.run();
                        }
                    });
        } if (negativeButton != null) {
            dialog.setNegativeButton(negativeButton,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            if (negativeRunnable != null)
                                negativeRunnable.run();
                        }
                    });
        }
        if (positiveButton != null) {
            dialog.setPositiveButton(positiveButton,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            if (positiveRunnable != null)
                                positiveRunnable.run();
                        }
                    });
        }
        dialog.show();
    }


    public static void alertCustomView(Activity context, String title, String message,
                                       String negativeButton, String positiveButton,
                                       final Runnable negativeRunnable, final Runnable positiveRunnable, int layout) {
        AlertDialog.Builder dialog = createCustomAlert(context, title, message,layout);
        if (negativeButton != null) {
            dialog.setNegativeButton(negativeButton,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            if (negativeRunnable != null)
                                negativeRunnable.run();
                        }
                    });
        }
        if (positiveButton != null) {
            dialog.setPositiveButton(positiveButton,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            if (positiveRunnable != null)
                                positiveRunnable.run();
                        }
                    });
        }
        dialog.show();
    }
}
