package top.banach.emergency.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.tencent.openqq.protocol.imsdk.im_common;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import top.banach.emergency.BaseActivity;
import top.banach.emergency.R;
import top.banach.emergency.api.Api;
import top.banach.emergency.callback.StringCallBack;
import top.banach.emergency.main.MainActivity;
import top.banach.emergency.model.UserInfoBean;
import top.banach.emergency.utils.JSONUtil;
import top.banach.emergency.utils.ToastUtils;

public class UserInfoActivity extends BaseActivity {

    private EditText etName;
    private EditText etIdno;
    private EditText etWorkUnit;
    private EditText etAddr;
    private RadioButton rbMale;
    private RadioButton rbFemale;

    private TextView tvMobile;

    private Button btnSave;

    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        initData();
        initView();
        requestData();

    }

    private void initData() {
        loadingDialog = new LoadingDialog(UserInfoActivity.this);
        loadingDialog.setSuccessText("保存成功");
        loadingDialog.setFailedText("保存失败");
    }

    private void initView() {
        etName = findViewById(R.id.et_name);
        etIdno = findViewById(R.id.et_idno);
        etWorkUnit = findViewById(R.id.et_work_unit);
        etAddr = findViewById(R.id.et_addr);
        tvMobile = findViewById(R.id.tv_mobile);
        btnSave = findViewById(R.id.btn_save);

        rbMale = findViewById(R.id.rb_male);
        rbFemale = findViewById(R.id.rb_female);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String idno = etIdno.getText().toString();
                String workUnit = etWorkUnit.getText().toString();
                String addr = etAddr.getText().toString();
                String sex = rbMale.isChecked() ? "1" : "0";

                HashMap<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("idno", idno);
                params.put("sex", sex);
                params.put("addr", addr);
                params.put("workUnit", workUnit);


                loadingDialog.show();
                Api.saveUserInfo(UserInfoActivity.this, params, new StringCallBack.HttpCallBack() {
                    @Override
                    public void httpSucc(String result, Object request) {
                        ToastUtils.showLong(UserInfoActivity.this, "保存成功");
                        loadingDialog.loadSuccess();
                        loadingDialog.close();
                        Intent intent = new Intent(UserInfoActivity.this, MainActivity.class);
                        startActivity(intent);
                        UserInfoActivity.this.finish();
                    }

                    @Override
                    public void httpfalse(String result) {
                        loadingDialog.loadFailed();
                    }
                });
            }
        });
    }

    private void requestData() {
        Api.getUserInfo(UserInfoActivity.this, new StringCallBack.HttpCallBack() {
            @Override
            public void httpSucc(String result, Object request) {
                try {
                    JSONObject resultObj = new JSONObject(result);
                    JSONObject dataObj = resultObj.getJSONObject("data");
                    UserInfoBean userInfoBean = JSONUtil.fromGsonString(dataObj.toString(), UserInfoBean.class);
                    if (userInfoBean != null) {
                        loadIntoView(userInfoBean);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void httpfalse(String result) {

            }
        });
    }

    private void loadIntoView(UserInfoBean userInfoBean) {
        String name = userInfoBean.getName();
        String sex = userInfoBean.getSex();
        String idno = userInfoBean.getIdno();
        String mobile = userInfoBean.getMobile();
        String workunit = userInfoBean.getWorkunit();
        String addr = userInfoBean.getAddr();

        if (name != null) {
            etName.setText(name);
        }
        if (sex != null) {
            boolean isMale = sex.equals("1") ? true : false;
            rbMale.setChecked(isMale);
        }
        if (idno != null) {
            etIdno.setText(idno);
        }
        if (mobile != null) {
            tvMobile.setText(mobile);
        }
        if (workunit != null) {
            etWorkUnit.setText(workunit);
        }
        if (addr != null) {
            etAddr.setText(addr);
        }

    }


}
