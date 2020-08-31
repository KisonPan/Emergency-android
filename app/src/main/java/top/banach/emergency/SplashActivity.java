package top.banach.emergency;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;

import top.banach.emergency.R;
import top.banach.emergency.constants.C;
import top.banach.emergency.login.LoginActivity;
import top.banach.emergency.main.MainActivity;
import top.banach.emergency.utils.Constants;
import top.banach.emergency.utils.DateUtil;
import top.banach.emergency.utils.LogUtils;
import top.banach.emergency.utils.SPUtils;

public class SplashActivity extends Activity {

    private static final String TAG = SplashActivity.class.getSimpleName();
    private static final int SPLASH_TIME = 1000;
//    private View mFlashView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

//        mFlashView = findViewById(R.id.flash_view);
//        handleData();
        startLogin();
    }

//    private void handleData() {
//        mFlashView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startLogin();
//            }
//        }, SPLASH_TIME);
//    }

    private void startLogin() {
        String lastLoginDate = SPUtils.getString(getApplicationContext(), C.Key.LAST_LOGIN_DATE, null);
        LogUtils.i(TAG, "lastLoginDate:" + lastLoginDate);
        if (lastLoginDate!= null) {
//            boolean isExpire = DateUtil.isOverForHours(lastLoginDate, 7*24);
//            if (!isExpire) {
                autoLogin();
                return;
//            }
        }

        startLoginActivity();


    }

    private void startLoginActivity() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void autoLogin() {
        String userId = SPUtils.getString(SplashActivity.this.getApplicationContext(),C.params.appUserId, "");
        String userSigure = SPUtils.getString(SplashActivity.this.getApplicationContext(),C.params.userSig, "");
        LogUtils.i(TAG, ".....autoLogin..... " + userId + "-" + userSigure);

        if (!userId.isEmpty() && !userSigure.isEmpty()) {
            LogUtils.i(TAG, ".....loginTIM.....");
            loginTIM(userId, userSigure);
        } else {
            startLoginActivity();
        }
    }

    private void loginTIM(final String userId, final String userSig) {
        TIMManager.getInstance().login(userId, userSig, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 列表请参见错误码表
                Log.d(TAG, "login failed. code: " + code + " errmsg: " + desc);
                startLoginActivity();
            }


            @Override
            public void onSuccess() {
                Log.d(TAG, "autologin succ");
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        });
    }

}
