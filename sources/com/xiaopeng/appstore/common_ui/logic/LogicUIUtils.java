package com.xiaopeng.appstore.common_ui.logic;

import android.content.Context;
import com.xiaopeng.appstore.bizcommon.logic.XuiMgrHelper;
import com.xiaopeng.appstore.bizcommon.utils.LogicUtils;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import com.xiaopeng.xui.app.XToast;
import java.util.function.Consumer;
/* loaded from: classes.dex */
public class LogicUIUtils {
    private LogicUIUtils() {
    }

    public static void tryExecuteSuspendApp(String packageName, int state, Context context, boolean isSuspended, Runnable action) {
        LogicUtils.tryExecuteSuspendApp(packageName, state, isSuspended, action, new Consumer() { // from class: com.xiaopeng.appstore.common_ui.logic.-$$Lambda$qdz_AyhFuYZ2ofUS8lZCB9dugvc
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                LogicUIUtils.toastError((String) obj);
            }
        });
    }

    public static void toastError(final String msg) {
        AppExecutors.get().mainThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.common_ui.logic.-$$Lambda$LogicUIUtils$NPxRj5NFJywMuNFjL187FaeL2k0
            @Override // java.lang.Runnable
            public final void run() {
                XToast.show(msg, 0, XuiMgrHelper.get().getShareId());
            }
        });
    }
}
