package top.banach.emergency.main;

import android.app.Fragment;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.huawei.android.hms.agent.HMSAgent;
import com.huawei.android.hms.agent.common.handler.ConnectHandler;
import com.huawei.android.hms.agent.push.handler.GetTokenHandler;
import com.tencent.imsdk.utils.IMFunc;

import top.banach.emergency.BaseActivity;
import top.banach.emergency.R;
import top.banach.emergency.api.Urls;
import top.banach.emergency.checkupdate.UpdateAppHttpUtil;
import top.banach.emergency.constants.C;
import top.banach.emergency.contact.ContactFragment;
import top.banach.emergency.conversation.ConversationFragment;
import top.banach.emergency.home.HomeFragment;
import top.banach.emergency.profile.ProfileFragment;
import top.banach.emergency.thirdpush.ThirdPushTokenMgr;
import top.banach.emergency.utils.DemoLog;
import top.banach.emergency.utils.SPUtils;

import com.tencent.qcloud.tim.uikit.modules.chat.GroupChatManagerKit;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationManagerKit;
import com.tencent.qcloud.tim.uikit.utils.FileUtil;
import com.vector.update_app.UpdateAppManager;
import com.vivo.push.IPushActionListener;
import com.vivo.push.PushClient;



public class MainActivity extends BaseActivity implements ConversationManagerKit.MessageUnreadWatcher {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView mHomeBtn;
    private TextView mConversationBtn;
    private TextView mContactBtn;
    private TextView mProfileSelfBtn;
    private TextView mMsgUnread;
    private View mLastTab;

    private LocationClient mLocClient;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    boolean isFirstLoc = true;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;

    private float spXAxis = 23.02f;
    private float spYAxis = 113.11f;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        DemoLog.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        prepareThirdPushToken();

        new UpdateAppManager
                .Builder()
                //当前Activity
                .setActivity(this)
                //更新地址
                .setUpdateUrl(Urls.CHECK_VERSION)
                //实现httpManager接口的对象
                .setHttpManager(new UpdateAppHttpUtil(MainActivity.this))
                .build()
                .update();

        initLocation();
    }

    private void initLocation() {
        mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // gps
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null) {
                return;
            }
            float xAixs = (float)location.getLatitude();
            float yAixs = (float)location.getLongitude();

            if (spXAxis != xAixs || spYAxis != yAixs) {
                SPUtils.putFloat(MainActivity.this, C.params.xAxis, xAixs);
                SPUtils.putFloat(MainActivity.this, C.params.yAxis, yAixs);
                spXAxis = xAixs;
                spYAxis = yAixs;
                Log.i(C.tag, "xAixs =" + xAixs);
                Log.i(C.tag, "yAixs =" + yAixs);
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    private void prepareThirdPushToken() {
        ThirdPushTokenMgr.getInstance().setPushTokenToTIM();

        if (ThirdPushTokenMgr.USER_GOOGLE_FCM) {
            return;
        }
        if (IMFunc.isBrandHuawei()) {
            // 华为离线推送
            HMSAgent.connect(this, new ConnectHandler() {
                @Override
                public void onConnect(int rst) {
                    DemoLog.i(TAG, "huawei push HMS connect end:" + rst);
                }
            });
            getHuaWeiPushToken();
        }
        if (IMFunc.isBrandVivo()) {
            // vivo离线推送
            PushClient.getInstance(getApplicationContext()).turnOnPush(new IPushActionListener() {
                @Override
                public void onStateChanged(int state) {
                    if (state == 0) {
                        String regId = PushClient.getInstance(getApplicationContext()).getRegId();
                        DemoLog.i(TAG, "vivopush open vivo push success regId = " + regId);
                        ThirdPushTokenMgr.getInstance().setThirdPushToken(regId);
                        ThirdPushTokenMgr.getInstance().setPushTokenToTIM();
                    } else {
                        // 根据vivo推送文档说明，state = 101 表示该vivo机型或者版本不支持vivo推送，链接：https://dev.vivo.com.cn/documentCenter/doc/156
                        DemoLog.i(TAG, "vivopush open vivo push fail state = " + state);
                    }
                }
            });
        }
    }

    private void initView() {
        setContentView(R.layout.main_activity);
        mHomeBtn = findViewById(R.id.home);
        mConversationBtn = findViewById(R.id.conversation);
        mContactBtn = findViewById(R.id.contact);
        mProfileSelfBtn = findViewById(R.id.mine);
        mMsgUnread = findViewById(R.id.msg_total_unread);
//        getFragmentManager().beginTransaction().replace(R.id.empty_view, new ConversationFragment()).commitAllowingStateLoss();
        getFragmentManager().beginTransaction().replace(R.id.empty_view, new HomeFragment()).commitAllowingStateLoss();
        FileUtil.initPath(); // 从application移入到这里，原因在于首次装上app，需要获取一系列权限，如创建文件夹，图片下载需要指定创建好的文件目录，否则会下载本地失败，聊天页面从而获取不到图片、表情

        // 未读消息监视器
        ConversationManagerKit.getInstance().addUnreadWatcher(this);
        GroupChatManagerKit.getInstance();
        if (mLastTab == null) {
//            mLastTab = findViewById(R.id.conversation_btn_group);
            mLastTab = findViewById(R.id.home_btn_group);
        } else {
            // 初始化时，强制切换tab到上一次的位置
            View tmp = mLastTab;
            mLastTab = null;
            tabClick(tmp);
            mLastTab = tmp;
        }
    }

    public void tabClick(View view) {
        DemoLog.i(TAG, "tabClick last: " + mLastTab + " current: " + view);
        if (mLastTab != null && mLastTab.getId() == view.getId()) {
            return;
        }
        mLastTab = view;
        resetMenuState();
        Fragment current = null;
        switch (view.getId()) {
            case R.id.home_btn_group:
                current = new HomeFragment();
//                mHomeBtn.setTextColor(getResources().getColor(R.color.tab_text_selected_color));
//                mHomeBtn.setTextColor(getResources().getColor(R.color.tab_selected));
                mHomeBtn.setTextColor(ContextCompat.getColor(this, R.color.tab_selected));
                mHomeBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.home_selected1), null, null);
                break;
            case R.id.conversation_btn_group:
                current = new ConversationFragment();
                mConversationBtn.setTextColor(ContextCompat.getColor(this, R.color.tab_selected));
                mConversationBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.conversation_selected1), null, null);
                break;
            case R.id.contact_btn_group:
                current = new ContactFragment();
                mContactBtn.setTextColor(ContextCompat.getColor(this, R.color.tab_selected));
                mContactBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.contact_selected1), null, null);
                break;
            case R.id.myself_btn_group:
                current = new ProfileFragment();
                mProfileSelfBtn.setTextColor(ContextCompat.getColor(this, R.color.tab_selected));
                mProfileSelfBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.myself_selected1), null, null);
                break;
        }

        if (current != null && !current.isAdded()) {
            getFragmentManager().beginTransaction().replace(R.id.empty_view, current).commitAllowingStateLoss();
            getFragmentManager().executePendingTransactions();
        } else {
            DemoLog.w(TAG, "fragment added!");
        }
    }

    private void resetMenuState() {
        mHomeBtn.setTextColor(getResources().getColor(R.color.tab_text_normal_color));
        mHomeBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.home_normal), null, null);
        mConversationBtn.setTextColor(getResources().getColor(R.color.tab_text_normal_color));
        mConversationBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.conversation_normal), null, null);
        mContactBtn.setTextColor(getResources().getColor(R.color.tab_text_normal_color));
        mContactBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.contact_normal), null, null);
        mProfileSelfBtn.setTextColor(getResources().getColor(R.color.tab_text_normal_color));
        mProfileSelfBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.myself_normal), null, null);
    }


    @Override
    public void updateUnread(int count) {
        if (count > 0) {
            mMsgUnread.setVisibility(View.VISIBLE);
        } else {
            mMsgUnread.setVisibility(View.GONE);
        }
        String unreadStr = "" + count;
        if (count > 100) {
            unreadStr = "99+";
        }
        mMsgUnread.setText(unreadStr);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return true;

    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onStart() {
        DemoLog.i(TAG, "onStart");
        super.onStart();
        initView();
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
        ConversationManagerKit.getInstance().destroyConversation();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        DemoLog.i(TAG, "onDestroy");
        mLastTab = null;
        super.onDestroy();
    }

    private void getHuaWeiPushToken() {
        HMSAgent.Push.getToken(new GetTokenHandler() {
            @Override
            public void onResult(int rtnCode) {
                DemoLog.i(TAG, "huawei push get token result code: " + rtnCode);
            }
        });
    }
}
