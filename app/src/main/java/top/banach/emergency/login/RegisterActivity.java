package top.banach.emergency.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import top.banach.emergency.BaseActivity;
import top.banach.emergency.R;
import top.banach.emergency.api.Api;
import top.banach.emergency.callback.StringCallBack;
import top.banach.emergency.constants.C;
import top.banach.emergency.utils.BitmapUtil;
import top.banach.emergency.utils.ToastUtils;

public class RegisterActivity extends BaseActivity {

    private static final String  TYPE_REGISTER = "REGISTER";
    private EditText etConfirmCode;
    private ImageView ivConfirmCode;
    private Button btnGetCode;

    private String mobile;
    private Bitmap confirmBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initData();
        initView();
    }

    private void initView() {
        etConfirmCode = findViewById(R.id.et_confirm_code);
        ivConfirmCode = findViewById(R.id.iv_confirm_code);
        btnGetCode = findViewById(R.id.btn_get_code);


        ivConfirmCode.setImageBitmap(confirmBitmap);
        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imgCode = etConfirmCode.getText().toString();
                Api.getRegisterCode(RegisterActivity.this.getApplicationContext(), mobile, imgCode, new StringCallBack.HttpCallBack() {
                    @Override
                    public void httpSucc(String result, Object request) {
                        Intent intent = new Intent();
                        intent.putExtra("type",TYPE_REGISTER);//要返回的结果
                        setResult(RESULT_OK,intent);
                        RegisterActivity.this.finish();
                    }

                    @Override
                    public void httpfalse(String result) {
                        ToastUtils.showLong(getApplicationContext(), R.string.err_web);
                    }
                });
            }
        });
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        mobile = bundle.getString(C.Key.mobile, "");
        String cofirmImgBase64 = bundle.getString(C.Key.IMG_BASE64);
        confirmBitmap = BitmapUtil.base64toBitmap(cofirmImgBase64);
    }


}
