package top.banach.emergency.extention;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.tencent.qcloud.tim.uikit.modules.chat.ChatLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.interfaces.IChatProvider;

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
}
