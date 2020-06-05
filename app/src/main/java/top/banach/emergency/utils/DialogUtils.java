package top.banach.emergency.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


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
}
