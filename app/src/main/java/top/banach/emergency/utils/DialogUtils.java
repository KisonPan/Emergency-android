package top.banach.emergency.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;


public class DialogUtils {
    public static void showListDialog(Context context,
                                               String title,
                                               String[] items,
                                               DialogInterface.OnClickListener listener){
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(context);
        listDialog.setTitle(title);
        listDialog.setItems(items, listener);
        listDialog.show();
    }

    public static void showAlertDialog(Context context,
                                       String message,
                                       String positiveText,
                                       String negativeText,
                                       OnDialogClickListener listener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onPositiveClick(dialog);
                    }
                })
                .setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onNegativeClick(dialog);
                    }
                })
                .show();
    }

    public interface OnDialogClickListener {
        public void onPositiveClick(DialogInterface dialog);
        public void onNegativeClick(DialogInterface dialog);
    }
}
