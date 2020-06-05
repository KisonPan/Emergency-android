package top.banach.emergency.thirdpush;

import com.google.firebase.messaging.FirebaseMessagingService;
import top.banach.emergency.utils.DemoLog;

public class GoogleFCMMsgService extends FirebaseMessagingService {
    private final String TAG = GoogleFCMMsgService.class.getSimpleName();

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        DemoLog.i(TAG, "google fcm onNewToken : " + token);

        ThirdPushTokenMgr.getInstance().setThirdPushToken(token);
        ThirdPushTokenMgr.getInstance().setPushTokenToTIM();
    }
}
