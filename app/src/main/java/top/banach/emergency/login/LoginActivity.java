package top.banach.emergency.login;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;
import top.banach.emergency.R;
import top.banach.emergency.api.Api;
import top.banach.emergency.callback.StringCallBack;
import top.banach.emergency.constants.C;
import top.banach.emergency.main.MainActivity;
import top.banach.emergency.model.CommonResultBean;
import top.banach.emergency.model.LoginCodeResultBean;
import top.banach.emergency.model.LoginResultBean;
import top.banach.emergency.signature.GenerateTestUserSig;
import top.banach.emergency.utils.Constants;
import top.banach.emergency.utils.DateUtil;
import top.banach.emergency.utils.JSONUtil;
import top.banach.emergency.utils.LogUtils;
import top.banach.emergency.utils.SPUtils;
import top.banach.emergency.utils.StringUtil;
import top.banach.emergency.utils.ToastUtils;
import top.banach.emergency.utils.UUIDGenerator;

import com.tencent.qcloud.tim.uikit.TUIKit;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.bouncycastle.asn1.esf.SPuri;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final int REQ_PERMISSION_CODE = 0x100;
    private static final int REQ_REG_CODE = 1;
    private static final String  TYPE_LOGIN = "LOGIN";
    private static final String  TYPE_REGISTER = "REGISTER";
    private Button btnLogin;
    private Button btnSmsCode;
    private EditText etPhoneNo;
    private EditText etSmsCode;

    private LoadingDialog loadingDialog;

    private static RSAPublicKey publicKey;//公钥
    private static String cid;
    private String type = TYPE_LOGIN;

    private TimeCount time;

    private static String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        getSupportActionBar().hide();


        initData();
        initView();

//        String lastLoginDate = SPUtils.getString(getApplicationContext(),C.Key.LAST_LOGIN_DATE, null);
//        LogUtils.i(TAG, "lastLoginDate:" + lastLoginDate);
//        if (lastLoginDate!= null) {
//             boolean isExpire = DateUtil.isOverForHours(lastLoginDate, 7*24);
//             if (!isExpire) {
//                 autoLogin();
//             }
//        }


    }

    private void autoLogin() {

        String userId = SPUtils.getString(LoginActivity.this.getApplicationContext(),C.params.appUserId, "");
        String userSigure = SPUtils.getString(LoginActivity.this.getApplicationContext(),C.params.userSig, "");
        LogUtils.i(TAG, ".....autoLogin..... " + userId + "-" + userSigure);

        if (!userId.isEmpty() && !userSigure.isEmpty()) {
            LogUtils.i(TAG, ".....loginTIM.....");
            loginTIM(userId, userSigure);
        }
    }

    private void resetState() {
        type = TYPE_LOGIN;
    }

    private void initView() {
        btnLogin = findViewById(R.id.btn_login);
        btnSmsCode = findViewById(R.id.btn_sms_code);
        etPhoneNo = findViewById(R.id.et_phone_no);
        etSmsCode = findViewById(R.id.et_sms_code);


        String userId = SPUtils.getString(LoginActivity.this.getApplicationContext(), C.params.appUserId, "");
        etPhoneNo.setText(userId);
//        etSmsCode.setText("8888");

        etPhoneNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (StringUtil.isCellPhone(s.toString())) {
                    btnSmsCode.setClickable(true);
                    btnSmsCode.setTextColor(getResources().getColor(R.color.tab_selected));
                    resetState();
                }
            }
        });

        btnSmsCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNo = etPhoneNo.getText().toString();
                if (StringUtil.isNull(phoneNo)) {
                    ToastUtils.showLong(LoginActivity.this, "请输入手机号码");
                    return;
                }

                getLoginCode(phoneNo);
                time.start();
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = etPhoneNo.getText().toString();
                String smsCode = etSmsCode.getText().toString();

                //设置loading时显示的文字,开始显示
                loadingDialog.setLoadingText("正在登陆...").show();

                if (type.equals(TYPE_LOGIN)) {
                    login(mobile, smsCode);
                } else if (type.equals(TYPE_REGISTER)) {
                    register(mobile, smsCode);
                }

//                loginTIM(mobile, null);
//                ActivityUtil.openActivity(LoginActivity.this, MainActivity.class);
            }
        });
    }


    class TimeCount extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btnSmsCode.setClickable(false);
            btnSmsCode.setTextColor(getResources().getColor(R.color.main_gray));
            btnSmsCode.setText(millisUntilFinished/1000 +"s后重新获取");
        }

        @Override
        public void onFinish() {
            btnSmsCode.setClickable(true);
            btnSmsCode.setTextColor(getResources().getColor(R.color.tab_selected));
            btnSmsCode.setText("获取验证码");
        }
    }

    private void initData() {
        time = new TimeCount(60000, 1000);
        try {
            //生成加密所用的公钥
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("RSAPublic.dat");
            ObjectInputStream OIS = new ObjectInputStream(inputStream);
            publicKey = (RSAPublicKey) OIS.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        cid = SPUtils.getString(getApplicationContext(), "cid", null);
        if (cid==null || cid.isEmpty()) {
            cid = UUIDGenerator.generateUUID();
            SPUtils.putString(getApplicationContext(),"cid", cid);
        }

        loadingDialog = new LoadingDialog(LoginActivity.this);
        loadingDialog.setSuccessText("登录成功");
        loadingDialog.setFailedText("登录失败");

        checkPermission(this);
    }


    //--以下是登陆页的接口--
    private void getLoginCode(final String phoneNo) {
        Api.getLoginCode(LoginActivity.this, phoneNo, new StringCallBack.HttpCallBack() {
            @Override
            public void httpSucc(String result, Object request) {
                LoginCodeResultBean loginCodeResultBean = JSONUtil.fromGsonString(result, LoginCodeResultBean.class);

            }

            @Override
            public void httpfalse(String result) {
                if (JSONUtil.isJson(result)) {
                    LoginCodeResultBean loginCodeResultBean = JSONUtil.fromGsonString(result, LoginCodeResultBean.class);
                    String imgBase64 = loginCodeResultBean.getImgbase64();
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(C.Key.mobile, phoneNo);
                    bundle.putString(C.Key.IMG_BASE64, imgBase64);
                    intent.putExtras(bundle);
//                    startActivity(intent);
                    startActivityForResult(intent, REQ_REG_CODE);
                } else {
                    ToastUtils.showLong(getApplicationContext(), R.string.err_web);
                    time.onFinish();
                    time.cancel();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_REG_CODE && resultCode == RESULT_OK) {
            type = TYPE_REGISTER;
        }
    }

    public void login(final String mobile, String loginCode) {
        final String userId = mobile;

        Api.login(LoginActivity.this, mobile, loginCode, new StringCallBack.HttpCallBack() {
            @Override
            public void httpSucc(String result, Object request) {
                Log.i("HomeFragment",  cid + "--Success!!-- " + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("code").equals("0")) {
                        LogUtils.i(TAG, jsonObject.getString("msg"));
                        ToastUtils.showLong(getApplicationContext(), jsonObject.getString("msg"));
                        SPUtils.remove(LoginActivity.this.getApplicationContext(), C.Key.LAST_LOGIN_DATE);
                        loadingDialog.loadFailed();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                LoginResultBean loginResultBean = JSONUtil.fromGsonString(result, LoginResultBean.class);

                String userSig = loginResultBean.getUsersig();
                if (userSig == null) {
                    ToastUtils.showLong(getApplicationContext(), "登陆失败");
                    SPUtils.remove(LoginActivity.this.getApplicationContext(), C.Key.LAST_LOGIN_DATE);
                    loadingDialog.loadFailed();
                    return;
                }
                LogUtils.i(TAG,userSig);
                loginTIM(userId, userSig);
            }

            @Override
            public void httpfalse(String result) {
                Toast.makeText(LoginActivity.this, "连接服务器失败！", Toast.LENGTH_SHORT).show();
                SPUtils.remove(LoginActivity.this.getApplicationContext(), C.Key.LAST_LOGIN_DATE);
                loadingDialog.loadFailed();
            }
        });

    }


    private void loginTIM(final String userId, final String userSig) {
        TIMManager.getInstance().login(userId, userSig, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 列表请参见错误码表
                Log.d(TAG, "login failed. code: " + code + " errmsg: " + desc);
                Toast.makeText(LoginActivity.this, "登陆聊天模块失败！", Toast.LENGTH_SHORT).show();
                SPUtils.remove(LoginActivity.this.getApplicationContext(), C.Key.LAST_LOGIN_DATE);
                loadingDialog.close();
            }

            @Override
            public void onSuccess() {
                Log.d(TAG, "login succ");
                SPUtils.putString(LoginActivity.this.getApplicationContext(),C.params.appUserId, userId);
                SPUtils.putString(LoginActivity.this.getApplicationContext(),C.params.userSig, userSig);
                SPUtils.putBoolean(LoginActivity.this.getApplicationContext(), Constants.AUTO_LOGIN, true);
                SPUtils.putString(LoginActivity.this.getApplicationContext(), C.Key.LAST_LOGIN_DATE, DateUtil.getCurrentTime());

                Toast.makeText(LoginActivity.this, "登陆成功！", Toast.LENGTH_SHORT).show();
                loadingDialog.loadSuccess();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                Intent intent = new Intent(LoginActivity.this, UserInfoActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });
    }

    private void register(final String mobile, String registerCode) {
        Api.register(LoginActivity.this, mobile, registerCode, new StringCallBack.HttpCallBack() {
            @Override
            public void httpSucc(String result, Object request) {
                CommonResultBean resultBean = JSONUtil.fromGsonString(result, CommonResultBean.class);
                loadingDialog.loadSuccess();
                Intent intent = new Intent(LoginActivity.this, UserInfoActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }

            @Override
            public void httpfalse(String result) {
                loadingDialog.loadFailed();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (loadingDialog != null) {
            loadingDialog.close();
        }
    }

    //权限检查
    public static boolean checkPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(TUIKit.getAppContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(TUIKit.getAppContext(), Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(TUIKit.getAppContext(), Manifest.permission.RECORD_AUDIO)) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(TUIKit.getAppContext(), Manifest.permission.READ_PHONE_STATE)) {
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(TUIKit.getAppContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(TUIKit.getAppContext(), Manifest.permission.CALL_PHONE)) {
                permissions.add(Manifest.permission.CALL_PHONE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(TUIKit.getAppContext(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }

            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(TUIKit.getAppContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }

            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(TUIKit.getAppContext(), Manifest.permission.READ_CONTACTS)) {
                permissions.add(Manifest.permission.READ_CONTACTS);
            }

            if (permissions.size() != 0) {
                String[] permissionsArray = permissions.toArray(new String[1]);
                ActivityCompat.requestPermissions(activity,
                        permissionsArray,
                        REQ_PERMISSION_CODE);
                return false;
            }
        }

        return true;
    }
}

