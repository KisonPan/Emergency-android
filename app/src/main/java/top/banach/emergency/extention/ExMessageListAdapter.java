package top.banach.emergency.extention;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.MessageListAdapter;

public class ExMessageListAdapter extends MessageListAdapter {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return super.onCreateViewHolder(parent, viewType);
        RecyclerView.ViewHolder holder = ExMessageBaseHolder.Factory.getInstance(parent, this, viewType);
        return holder;
    }
}
