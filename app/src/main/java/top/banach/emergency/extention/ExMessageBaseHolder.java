package top.banach.emergency.extention;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import top.banach.emergency.R;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.MessageListAdapter;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.holder.MessageAudioHolder;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.holder.MessageBaseHolder;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.holder.MessageCustomHolder;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.holder.MessageEmptyHolder;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.holder.MessageFileHolder;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.holder.MessageHeaderHolder;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.holder.MessageImageHolder;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.holder.MessageTextHolder;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.holder.MessageTipsHolder;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfo;

public class ExMessageBaseHolder extends MessageBaseHolder {

    public ExMessageBaseHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void layoutViews(MessageInfo msg, int position) {

    }

    public static class Factory {

        public static RecyclerView.ViewHolder getInstance(ViewGroup parent, RecyclerView.Adapter adapter, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(TUIKit.getAppContext());
            RecyclerView.ViewHolder holder = null;
            View view = null;

            // 头部的holder
            if (viewType == MessageListAdapter.MSG_TYPE_HEADER_VIEW) {
                view = inflater.inflate(R.layout.message_adapter_content_header, parent, false);
                holder = new MessageHeaderHolder(view);
                return holder;
            }

            // 加群消息等holder
            if (viewType >= MessageInfo.MSG_TYPE_TIPS) {
                view = inflater.inflate(R.layout.message_adapter_item_empty, parent, false);
                holder = new MessageTipsHolder(view);
            }

            // 具体消息holder
            view = inflater.inflate(R.layout.message_adapter_item_content, parent, false);
            switch (viewType) {
                case MessageInfo.MSG_TYPE_TEXT:
                    holder = new MessageTextHolder(view);
                    break;
                case MessageInfo.MSG_TYPE_IMAGE:
                case MessageInfo.MSG_TYPE_VIDEO:
                case MessageInfo.MSG_TYPE_CUSTOM_FACE:
                    holder = new MessageImageHolder(view);
                    break;
                case MessageInfo.MSG_TYPE_AUDIO:
                    holder = new MessageAudioHolder(view);
                    break;
                case MessageInfo.MSG_TYPE_FILE:
                    holder = new MessageFileHolder(view);
                    break;
                case MessageInfo.MSG_TYPE_CUSTOM:
                    holder = new MessageCustomHolder(view);
                    break;
                case MessageInfo.MSG_TYPE_LOCATION:
                    Log.i("ExMessageBaseHolder", "MSG_TYPE_LOCATION");
                    holder = new MessageLocationHolder(view);

                    break;
                default:
                    Log.i("ExMessageBaseHolder", "Default");
                    holder = new MessageTextHolder(view);
            }

            if (holder != null) {
                ((MessageEmptyHolder) holder).setAdapter(adapter);
            }

            return holder;
        }
    }
}
