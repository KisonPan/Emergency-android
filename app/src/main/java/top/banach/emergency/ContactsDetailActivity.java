package top.banach.emergency;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import top.banach.emergency.constants.C;

public class ContactsDetailActivity extends BaseActivity {

    private TextView tvName;
    private TextView tvPhoneNo;
    private TextView tvRelation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_detail);


        initData();
        initView();
    }

    private void initView() {
        tvName = (TextView)findViewById(R.id.tv_username);
        tvPhoneNo = (TextView)findViewById(R.id.tv_phone_no);
        tvRelation = (TextView)findViewById(R.id.tv_relation);

    }

    private void initData() {

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String name = bundle.getString(C.params.name);
        String phoneNo = bundle.getString(C.params.phone_no);
        String relation = bundle.getString(C.params.relation);

        tvName.setText(name);
        tvPhoneNo.setText(phoneNo);
        tvRelation.setText(relation);
    }
}
