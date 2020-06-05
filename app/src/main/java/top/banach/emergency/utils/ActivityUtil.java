package top.banach.emergency.utils;

import android.app.Activity;
import android.content.Intent;

public class ActivityUtil {

    /**
     * 跳转至新Activity
     * @param activity
     * @param cls
     */
    public static void openActivity(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
        activity.finish();
    }

}
