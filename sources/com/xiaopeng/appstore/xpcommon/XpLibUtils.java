package com.xiaopeng.appstore.xpcommon;

import android.content.Context;
import com.xiaopeng.appstore.xpcommon.push.PushHelper;
import com.xiaopeng.lib.bughunter.BugHunter;
/* loaded from: classes2.dex */
public class XpLibUtils {
    private XpLibUtils() {
    }

    public static void initBugHunter(Context context) {
        BugHunter.init(context);
    }

    /* loaded from: classes2.dex */
    public static class PushInit {
        public PushInit(Context context) {
            PushHelper.init(context);
        }

        public void registerPushListener(PushHelper.OnPushMsgListener listener) {
            PushHelper.registerListener(listener);
        }

        public void unregisterPushListener(PushHelper.OnPushMsgListener listener) {
            PushHelper.unregisterListener(listener);
        }

        public void setApplicationReady() {
            PushHelper.setApplicationReady();
        }
    }

    public static PushInit initPush(Context context) {
        return new PushInit(context);
    }
}
