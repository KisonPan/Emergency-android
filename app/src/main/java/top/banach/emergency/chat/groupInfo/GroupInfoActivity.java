package top.banach.emergency.chat.groupInfo;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

public class GroupInfoActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.tencent.qcloud.tim.uikit.R.layout.group_info_activity);
        GroupInfoFragment fragment = new GroupInfoFragment();
        fragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(com.tencent.qcloud.tim.uikit.R.id.group_manager_base, fragment).commitAllowingStateLoss();
    }

    @Override
    public void finish() {
        super.finish();
        setResult(1001);
    }
}
