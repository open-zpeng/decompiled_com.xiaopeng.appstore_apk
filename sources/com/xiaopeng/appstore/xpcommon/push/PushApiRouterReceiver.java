package com.xiaopeng.appstore.xpcommon.push;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.xpcommon.push.PushHelper;
import com.xiaopeng.lib.apirouter.server.IServicePublisher;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes2.dex */
public class PushApiRouterReceiver implements IServicePublisher {
    private static final String STRING_MSG = "string_msg";
    private static final String TAG = "PushApiRouterReceiver";
    private static volatile boolean sApplicationReady = false;
    private static PushApiRouterReceiver sInstance;
    private final Set<PushedMessageBean> pendingHandleMsgList = new HashSet();

    public static void setApplicationReady() {
        sApplicationReady = true;
        if (sInstance != null) {
            Logger.t(TAG).i("setApplicationReady", new Object[0]);
            for (PushedMessageBean pushedMessageBean : sInstance.pendingHandleMsgList) {
                Logger.t(TAG).i("Handle pending pushMsg:" + pushedMessageBean, new Object[0]);
                if (!PushHelper.pushMsgListeners.isEmpty()) {
                    for (PushHelper.OnPushMsgListener onPushMsgListener : PushHelper.pushMsgListeners) {
                        onPushMsgListener.onPushMsgReceived(pushedMessageBean);
                    }
                }
            }
            sInstance.pendingHandleMsgList.clear();
        }
    }

    public void onReceiverData(int id, String bundle) {
        if (id != 10010) {
            if (id == 10011) {
                Logger.t(TAG).i("Receive card click event:" + bundle, new Object[0]);
                int parseBundle = parseBundle(bundle);
                if (parseBundle <= -1 || PushHelper.pushMsgListeners.isEmpty()) {
                    return;
                }
                for (PushHelper.OnPushMsgListener onPushMsgListener : PushHelper.pushMsgListeners) {
                    onPushMsgListener.onPushCardClick(parseBundle);
                }
                return;
            }
            Logger.t(TAG).w("Receiver Not Support message from server:" + bundle, new Object[0]);
            return;
        }
        sInstance = this;
        Logger.t(TAG).i("Receiver message from server:" + bundle, new Object[0]);
        if (TextUtils.isEmpty(bundle)) {
            return;
        }
        ApiRouterMessageBean apiRouterMessageBean = null;
        try {
            apiRouterMessageBean = (ApiRouterMessageBean) new Gson().fromJson(bundle, (Class<Object>) ApiRouterMessageBean.class);
        } catch (Exception unused) {
            Logger.t(TAG).e("Parse json error:" + bundle, new Object[0]);
        }
        if (apiRouterMessageBean == null) {
            return;
        }
        PushedMessageBean pushedMessageBean = (PushedMessageBean) new Gson().fromJson(apiRouterMessageBean.getString_msg(), (Class<Object>) PushedMessageBean.class);
        if (pushedMessageBean == null) {
            return;
        }
        if (!sApplicationReady) {
            this.pendingHandleMsgList.add(pushedMessageBean);
        }
        if (PushHelper.pushMsgListeners.isEmpty()) {
            return;
        }
        for (PushHelper.OnPushMsgListener onPushMsgListener2 : PushHelper.pushMsgListeners) {
            onPushMsgListener2.onPushMsgReceived(pushedMessageBean);
        }
    }

    private int parseBundle(String bundle) {
        try {
            return Integer.parseInt(new JSONObject(bundle).getString("string_msg"));
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /* loaded from: classes2.dex */
    public static class ApiRouterMessageBean {
        private String receiverPackageName;
        private String senderPackageName;
        private String string_msg;

        public void setSenderPackageName(String senderPackageName) {
            this.senderPackageName = senderPackageName;
        }

        public String getSenderPackageName() {
            return this.senderPackageName;
        }

        public void setReceiverPackageName(String receiverPackageName) {
            this.receiverPackageName = receiverPackageName;
        }

        public String getReceiverPackageName() {
            return this.receiverPackageName;
        }

        public void setString_msg(String string_msg) {
            this.string_msg = string_msg;
        }

        public String getString_msg() {
            return this.string_msg;
        }
    }
}
