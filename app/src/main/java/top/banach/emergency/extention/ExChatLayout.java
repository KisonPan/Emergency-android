package top.banach.emergency.extention;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.tencent.qcloud.tim.uikit.modules.chat.ChatLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.tencent.qcloud.tim.uikit.modules.chat.interfaces.IChatProvider;
import com.tencent.qcloud.tim.uikit.utils.TUIKitConstants;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;

import top.banach.emergency.chat.groupInfo.GroupInfoActivity;

public class ExChatLayout extends ChatLayout {
    private ExMessageListAdapter mAdapter;
    public ExChatLayout(Context context) {
        super(context);
    }

    public ExChatLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExChatLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initDefault() {
        super.initDefault();

        mAdapter = new ExMessageListAdapter();
        getMessageLayout().setAdapter(mAdapter);
    }

    @Override
    public void setDataProvider(IChatProvider provider) {
        super.setDataProvider(provider);
        if (mAdapter != null) {
            mAdapter.setDataSource(provider);
        }
    }

    @Override
    public void loadMessages() {
        loadChatMessages(mAdapter.getItemCount() > 0 ? mAdapter.getItem(1) : null);
    }

    @Override
    public void setChatInfo(ChatInfo chatInfo) {
        super.setChatInfo(chatInfo);
        getTitleBar().setOnRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chatInfo != null) {
                    Intent intent = new Intent(getContext(), GroupInfoActivity.class);
                    intent.putExtra(TUIKitConstants.Group.GROUP_ID, chatInfo.getId());
                    getContext().startActivity(intent);
                } else {
                    ToastUtil.toastLongMessage("请稍后再试试~");
                }
            }
        });
    }
}
