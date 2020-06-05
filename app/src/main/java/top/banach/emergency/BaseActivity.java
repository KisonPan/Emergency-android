package top.banach.emergency;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import top.banach.emergency.constants.C;
import top.banach.emergency.login.LoginActivity;
import top.banach.emergency.utils.Constants;
import top.banach.emergency.utils.DemoLog;
import top.banach.emergency.utils.SPUtils;

import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.base.IMEventListener;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * 登录状态的Activity都要集成该类，来完成被踢下线等监听处理。
 */
public class BaseActivity extends FragmentActivity implements View.OnClickListener {

    private static final String TAG = BaseActivity.class.getSimpleName();

    // 监听做成静态可以让每个子类重写时都注册相同的一份。
    private static IMEventListener mIMEventListener = new IMEventListener() {
        @Override
        public void onForceOffline() {
            ToastUtil.toastLongMessage("您的帐号已在其它终端登录");
            logout(DemoApplication.instance(), false);
        }
    };

    public static void logout(Context context, boolean autoLogin) {
        DemoLog.i(TAG, "logout");
//        SharedPreferences shareInfo = context.getSharedPreferences(Constants.USERINFO, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = shareInfo.edit();
//        editor.putBoolean(Constants.AUTO_LOGIN, autoLogin);
//        editor.commit();

        SPUtils.putBoolean(context.getApplicationContext(), Constants.AUTO_LOGIN, autoLogin);
        SPUtils.remove(context.getApplicationContext(), "cid");
        SPUtils.remove(context.getApplicationContext(), C.Key.LAST_LOGIN_DATE);
        SPUtils.remove(context.getApplicationContext(), C.params.userSig);
        SPUtils.remove(context.getApplicationContext(), C.params.appUserId);

//        Intent intent = new Intent(context, LoginForDevActivity.class);
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constants.LOGOUT, true);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        DemoLog.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        TUIKit.addIMEventListener(mIMEventListener);
    }

    @Override
    protected void onStart() {
        DemoLog.i(TAG, "onStart");
        super.onStart();
//        SharedPreferences shareInfo = getSharedPreferences(Constants.USERINFO, Context.MODE_PRIVATE);
//        boolean login = shareInfo.getBoolean(Constants.AUTO_LOGIN, false);
//        if (!login) {
//            BaseActivity.logout(DemoApplication.instance(), false);
//        }
    }

    @Override
    protected void onResume() {
        DemoLog.i(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        DemoLog.i(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        DemoLog.i(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        DemoLog.i(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

    }
}
