package top.banach.emergency.extention;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.imsdk.TIMLocationElem;

import top.banach.emergency.BanachApplication;
import top.banach.emergency.R;
import top.banach.emergency.chat.ShowLocationActivity;
import top.banach.emergency.constants.C;

import com.tencent.qcloud.tim.uikit.component.face.FaceManager;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.holder.MessageContentHolder;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfo;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MessageLocationHolder extends MessageContentHolder {

//    private TextView msgBodyText;
    private TextView tvLocationDesc;
    private ImageView ivLocationImage;
    private LinearLayout llLocation;

    public MessageLocationHolder(View itemView) {
        super(itemView);
    }

    @Override
    public int getVariableLayout() {
//        return com.tencent.qcloud.tim.uikit.R.layout.message_adapter_content_text;
        return R.layout.message_adapter_content_location;

    }

    @Override
    public void initVariableViews() {
//        msgBodyText = rootView.findViewById(com.tencent.qcloud.tim.uikit.R.id.msg_body_tv);
        tvLocationDesc = rootView.findViewById(R.id.location_desc_tv);
        ivLocationImage = rootView.findViewById(R.id.location_map_shot_iv);
        llLocation = rootView.findViewById(R.id.ll_location);
//        ivLocationImage.setClickable(true);
    }

    @Override
    public void layoutVariableViews(MessageInfo msg, int position) {
//        msgBodyText.setVisibility(View.VISIBLE);
//        FaceManager.handlerEmojiText(msgBodyText, msg.getExtra().toString());
        tvLocationDesc.setVisibility(View.VISIBLE);
        FaceManager.handlerEmojiText(tvLocationDesc, "位置信息位置信息", false);

        if (msg != null) {
            TIMLocationElem elem = (TIMLocationElem) msg.getElement();
            final String desc = elem.getDesc();
            final String latitude = String.valueOf(elem.getLatitude());
            final String longitude = String.valueOf(elem.getLongitude());

            if (desc != null) {
                Log.i("MessageLocationHolder", "desc = " + desc);
                FaceManager.handlerEmojiText(tvLocationDesc, desc, false);
            }

            if (latitude != null) {
                Log.i("MessageLocationHolder", "lat = " + latitude);
            }

            if (longitude != null) {
                Log.i("MessageLocationHolder", "lng = " + longitude);
            }

            llLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BanachApplication.instance().getApplicationContext(), ShowLocationActivity.class);
                    Bundle bundle = new Bundle();

                    bundle.putString(C.params.latitude, latitude);
                    bundle.putString(C.params.longitude, longitude);
//                    bundle.putString(C.params.title, desc);
                    bundle.putString(C.params.name, desc);

                    intent.putExtras(bundle);
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    BanachApplication.instance().getApplicationContext().startActivity(intent);
                }
            });
        }

        if (properties.getChatContextFontSize() != 0) {
            tvLocationDesc.setTextSize(properties.getChatContextFontSize());
        }
        if (msg.isSelf()) {
            if (properties.getRightChatContentFontColor() != 0) {
                tvLocationDesc.setTextColor(properties.getRightChatContentFontColor());
            }
        } else {
            if (properties.getLeftChatContentFontColor() != 0) {
                tvLocationDesc.setTextColor(properties.getLeftChatContentFontColor());
            }
        }


    }
}
