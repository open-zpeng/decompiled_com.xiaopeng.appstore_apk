package com.xiaopeng.appstore.xpcommon.push;

import android.os.Bundle;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.libcommon.utils.NumberUtils;
import com.xiaopeng.appstore.xpcommon.push.PushHelper;
import com.xiaopeng.lib.framework.moduleinterface.ipcmodule.IIpcService;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
/* loaded from: classes2.dex */
class PushIpcReceiver {
    private static final String MSG_KEY = "string_msg";
    private static final String TAG = "PushIpcReceiver";
    private static final PushIpcReceiver sPushIpcReceiver = new PushIpcReceiver();

    PushIpcReceiver() {
    }

    public static void init() {
        EventBus.getDefault().register(sPushIpcReceiver);
    }

    public static void release() {
        EventBus.getDefault().unregister(sPushIpcReceiver);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(IIpcService.IpcMessageEvent event) {
        Logger.t(TAG).i("Receiver push msg:" + event, new Object[0]);
        int msgID = event.getMsgID();
        Bundle payloadData = event.getPayloadData();
        if (payloadData == null) {
            Logger.t(TAG).w("PayloadData is empty!", new Object[0]);
            return;
        }
        String string = payloadData.getString("string_msg");
        if (TextUtils.isEmpty(string)) {
            Logger.t(TAG).w("Content is empty!", new Object[0]);
        } else if (msgID == 10010) {
            PushedMessageBean pushedMessageBean = null;
            try {
                pushedMessageBean = (PushedMessageBean) new Gson().fromJson(string, (Class<Object>) PushedMessageBean.class);
            } catch (Exception unused) {
                Logger.t(TAG).e("Parse json error:" + string, new Object[0]);
            }
            if (pushedMessageBean == null || PushHelper.pushMsgListeners.isEmpty()) {
                return;
            }
            for (PushHelper.OnPushMsgListener onPushMsgListener : PushHelper.pushMsgListeners) {
                onPushMsgListener.onPushMsgReceived(pushedMessageBean);
            }
        } else if (msgID == 10011) {
            Logger.t(TAG).i("Receive card click event:" + string, new Object[0]);
            int stringToInt = NumberUtils.stringToInt(string, -1);
            if (stringToInt <= -1 || PushHelper.pushMsgListeners.isEmpty()) {
                return;
            }
            for (PushHelper.OnPushMsgListener onPushMsgListener2 : PushHelper.pushMsgListeners) {
                onPushMsgListener2.onPushCardClick(stringToInt);
            }
        } else {
            Logger.t(TAG).w("Receiver Not Support message from server:" + string, new Object[0]);
        }
    }
}
