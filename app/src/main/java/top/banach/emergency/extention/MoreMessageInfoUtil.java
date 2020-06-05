package top.banach.emergency.extention;


import com.tencent.imsdk.TIMLocationElem;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfo;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfoUtil;

public class MoreMessageInfoUtil extends MessageInfoUtil {
    /**
     * 创建一条位置消息
     * @param desc
     * @param lat
     * @param lng
     * @return
     */
    public static MessageInfo buildLocationMessage(String desc, Double lat, Double lng) {
        MessageInfo info = new MessageInfo();
        TIMMessage TIMMsg = new TIMMessage();
        TIMLocationElem ele = new TIMLocationElem();
        ele.setDesc(desc);
        ele.setLatitude(lat);
        ele.setLongitude(lng);
        TIMMsg.addElement(ele);
        info.setExtra(desc);
        info.setMsgTime(System.currentTimeMillis() / 1000);
        info.setElement(ele);
        info.setSelf(true);
        info.setTIMMessage(TIMMsg);
        info.setFromUser(TIMManager.getInstance().getLoginUser());
        info.setMsgType(MessageInfo.MSG_TYPE_LOCATION);
        return info;
    }
}
