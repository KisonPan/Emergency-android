package top.banach.emergency.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tencent.imsdk.TIMConversationType;
import top.banach.emergency.BanachApplication;
import top.banach.emergency.R;
import top.banach.emergency.constants.C;
import top.banach.emergency.contact.FriendProfileActivity;
import top.banach.emergency.extention.ExChatLayout;
import top.banach.emergency.extention.MoreMessageInfoUtil;
import top.banach.emergency.helper.ChatLayoutHelper;
import top.banach.emergency.utils.Constants;
import com.tencent.qcloud.tim.uikit.base.BaseFragment;
import com.tencent.qcloud.tim.uikit.component.AudioPlayer;
import com.tencent.qcloud.tim.uikit.component.TitleBarLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.input.InputLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.inputmore.InputMoreActionUnit;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.MessageLayout;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfo;
import com.tencent.qcloud.tim.uikit.utils.TUIKitConstants;


public class ChatFragment extends BaseFragment {

    private View mBaseView;
//    private ChatLayout mChatLayout;
    private ExChatLayout mChatLayout;
    private TitleBarLayout mTitleBar;
    private ChatInfo mChatInfo;
    private static final int REQUEST_CODE_SEND_LOCACTION = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        mChatInfo = (ChatInfo) bundle.getSerializable(Constants.CHAT_INFO);
        if (mChatInfo == null) {
            return null;
        }
        mBaseView = inflater.inflate(R.layout.chat_fragment, container, false);
        initView();
        return mBaseView;
    }

    private void initView() {
        //从布局文件中获取聊天面板组件
        mChatLayout = mBaseView.findViewById(R.id.chat_layout);

        //单聊组件的默认UI和交互初始化
        mChatLayout.initDefault();

        // TODO 通过api设置ChatLayout各种属性的样例
        ChatLayoutHelper.customizeChatLayout(getActivity(), mChatLayout);

        MessageLayout messageLayout = mChatLayout.getMessageLayout();
        // 设置自定义的消息渲染时的回调
        messageLayout.setOnCustomMessageDrawListener(new ChatLayoutHelper.CustomMessageDraw());
        //====== InputLayout使用范例 ======//
        InputLayout inputLayout = mChatLayout.getInputLayout();
        InputMoreActionUnit locationUnit = new InputMoreActionUnit();
        locationUnit.setIconResId(R.drawable.app_icon);
        locationUnit.setTitleId(R.string.position);
        locationUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MessageInfo info = MoreMessageInfoUtil.buildLocationMessage("广州天河区",Double.valueOf("23.0"), Double.valueOf("113"));
//                layout.sendMessage(info, false);

                Intent intent = new Intent(getActivity(), SendLocationActivity.class);
                startActivityForResult(intent, C.Code.REQUEST_CODE_SEND_LOCACTION);
//                getActivity().startActivity(intent);
            }
        });
        inputLayout.addAction(locationUnit);

        /*
         * 需要聊天的基本信息
         */
        mChatLayout.setChatInfo(mChatInfo);

        //获取单聊面板的标题栏
        mTitleBar = mChatLayout.getTitleBar();

        //单聊面板标记栏返回按钮点击事件，这里需要开发者自行控制
        mTitleBar.setOnLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        if (mChatInfo.getType() == TIMConversationType.C2C) {
            mTitleBar.setOnRightClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BanachApplication.instance(), FriendProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(TUIKitConstants.ProfileType.CONTENT, mChatInfo);
                    BanachApplication.instance().startActivity(intent);
                }
            });
        }
        mChatLayout.getMessageLayout().setOnItemClickListener(new MessageLayout.OnItemClickListener() {
            @Override
            public void onMessageLongClick(View view, int position, MessageInfo messageInfo) {
                //因为adapter中第一条为加载条目，位置需减1
                mChatLayout.getMessageLayout().showItemPopMenu(position - 1, messageInfo, view);
            }

            @Override
            public void onUserIconClick(View view, int position, MessageInfo messageInfo) {
                if (null == messageInfo) {
                    return;
                }
                ChatInfo info = new ChatInfo();
                info.setId(messageInfo.getFromUser());
                Intent intent = new Intent(BanachApplication.instance(), FriendProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(TUIKitConstants.ProfileType.CONTENT, info);
                BanachApplication.instance().startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == C.Code.REQUEST_CODE_SEND_LOCACTION) {
            if (resultCode == Activity.RESULT_OK) {
                String latitude = data.getStringExtra(C.params.latitude);
                String longitude = data.getStringExtra(C.params.longitude);
                String name = data.getStringExtra(C.params.name);
                String address = data.getStringExtra(C.params.address);

                MessageInfo info = MoreMessageInfoUtil.buildLocationMessage(name, Double.valueOf(latitude), Double.valueOf(longitude));
                mChatLayout.sendMessage(info, false);
//                sendReplyLoc(latitude, longitude, name, address);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        AudioPlayer.getInstance().stopPlay();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mChatLayout.exitChat();
    }

}
