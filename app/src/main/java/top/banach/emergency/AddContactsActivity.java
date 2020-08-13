package top.banach.emergency;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import top.banach.emergency.api.Api;
import top.banach.emergency.callback.StringCallBack;
import top.banach.emergency.utils.DialogUtils;

public class AddContactsActivity extends BaseActivity {

    private EditText etUserName;
    private EditText etPhoneNo;
    private Button btnSave;
    private LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initData();
        initView();

    }

    private void initData() {
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setLoadingText("正在保存");
        loadingDialog.setSuccessText("保存成功");
        loadingDialog.setFailedText("保存失败");
    }

    private void initView() {
        etUserName = findViewById(R.id.et_user_name);
        etPhoneNo = findViewById(R.id.et_phone_no);
        btnSave = findViewById(R.id.btn_save);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUserName.getText().toString();
                String phoneNo = etPhoneNo.getText().toString();
                requestSaveContacts(username, phoneNo);
            }
        });
    }

    private void requestSaveContacts(String username, String phoneNo) {
        loadingDialog.show();
        Api.saveContact(getApplicationContext(), username, phoneNo, new StringCallBack.HttpCallBack() {
            @Override
            public void httpSucc(String result, Object request) {
                loadingDialog.loadSuccess();
                loadingDialog.close();
//                finish();
                DialogUtils.showAlertDialog(AddContactsActivity.this,
                        "添加成功，是否继续添加?",
                        "是", "否",
                        new DialogUtils.OnDialogClickListener() {
                            @Override
                            public void onPositiveClick(DialogInterface dialog) {
                                etPhoneNo.setText("");
                                etUserName.setText("");
                            }

                            @Override
                            public void onNegativeClick(DialogInterface dialog) {
                                finish();
                            }
                        });
            }

            @Override
            public void httpfalse(String result) {
                loadingDialog.loadFailed();
            }
        });
    }


}
