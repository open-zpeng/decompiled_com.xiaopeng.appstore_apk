package com.xiaopeng.appstore.common_ui;

import android.text.TextUtils;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.entities.AssembleDataContainer;
import com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager;
import com.xiaopeng.appstore.libcommon.utils.Utils;
/* loaded from: classes.dex */
public class AppExecuteHelper {
    private static final String TAG = "AppExecuteHelper";

    private AppExecuteHelper() {
    }

    public static void startOrCancel(int state, String packageName, String downloadUrl, String configUrl, String label, String iconUrl) {
        startOrCancel(state, packageName, downloadUrl, null, configUrl, null, label, iconUrl);
    }

    public static void startOrCancel(int state, String packageName, String downloadUrl, String md5, String configUrl, String configMd5, String label, String iconUrl) {
        if (TextUtils.isEmpty(packageName) || TextUtils.isEmpty(downloadUrl) || state < 0) {
            Logger.t(TAG).d("do nothing state=" + state + " url=" + downloadUrl + " pn=" + packageName);
            return;
        }
        Logger.t(TAG).d("startOrCancel: state=" + AppStoreAssembleManager.stateToStr(state));
        if (state != 0) {
            if (state != 1 && state != 2) {
                if (state == 4) {
                    Logger.t(TAG).i("open app: " + packageName, new Object[0]);
                    OpenAppMgr.get().open(Utils.getApp(), packageName, label);
                    return;
                } else if (state != 5) {
                    if (state != 6) {
                        if (state == 7) {
                            Logger.t(TAG).i("resume downloaded app(%s).", packageName);
                            AppStoreAssembleManager.get().startWithConfig(downloadUrl, md5, configUrl, configMd5, packageName, label, iconUrl);
                            return;
                        } else if (state != 101) {
                            if (state != 10001) {
                                Logger.t(TAG).w("startOrCancel case not handled. state=" + state + " url=" + downloadUrl + " pn=" + packageName, new Object[0]);
                                return;
                            }
                        }
                    }
                }
            }
            Logger.t(TAG).i("cancel app: " + packageName, new Object[0]);
            AppStoreAssembleManager.get().cancelWithPackage(packageName);
            return;
        }
        Logger.t(TAG).i("download app: " + downloadUrl, new Object[0]);
        AppStoreAssembleManager.get().startWithConfig(downloadUrl, md5, configUrl, configMd5, packageName, label, iconUrl);
    }

    public static void execute(int state, String packageName, String downloadUrl, String md5, String configUrl, String configMd5, String label, String iconUrl) {
        String str;
        String str2;
        String str3 = downloadUrl;
        if (TextUtils.isEmpty(packageName) || TextUtils.isEmpty(downloadUrl) || state < 0) {
            Logger.t(TAG).d("do nothing");
            return;
        }
        Logger.t(TAG).d("execute: state=" + AppStoreAssembleManager.stateToStr(state));
        if (state != 0) {
            if (state != 1) {
                if (state == 2) {
                    String str4 = label;
                    AssembleDataContainer findWithPn = AppStoreAssembleManager.get().findWithPn(packageName);
                    if (findWithPn != null) {
                        str3 = findWithPn.getDownloadUrl();
                        str = findWithPn.getConfigUrl();
                        str4 = findWithPn.getAppLabel();
                        str2 = findWithPn.getIconUrl();
                    } else {
                        str = configUrl;
                        str2 = iconUrl;
                    }
                    Logger.t(TAG).i("resume paused app: " + str3, new Object[0]);
                    AppStoreAssembleManager.get().startWithConfig(str3, md5, str, configMd5, packageName, str4, str2);
                    return;
                } else if (state == 4) {
                    Logger.t(TAG).i("open app: " + packageName, new Object[0]);
                    OpenAppMgr.get().open(CommonUI.getApplication(), packageName, label);
                    return;
                } else if (state != 5) {
                    if (state != 6) {
                        if (state == 7) {
                            Logger.t(TAG).i("resume succeed app: " + downloadUrl, new Object[0]);
                            AppStoreAssembleManager.get().startWithConfig(downloadUrl, md5, configUrl, configMd5, packageName, label, iconUrl);
                            return;
                        } else if (state != 101) {
                            Logger.t(TAG).w("execute case not handled. state=" + state + " url=" + downloadUrl + " pn=" + packageName, new Object[0]);
                            return;
                        }
                    }
                }
            }
            Logger.t(TAG).i("pause app: " + packageName, new Object[0]);
            AppStoreAssembleManager.get().pauseWithPackage(packageName);
            return;
        }
        Logger.t(TAG).i("download app: " + downloadUrl, new Object[0]);
        AppStoreAssembleManager.get().startWithConfig(downloadUrl, md5, configUrl, configMd5, packageName, label, iconUrl);
    }
}
