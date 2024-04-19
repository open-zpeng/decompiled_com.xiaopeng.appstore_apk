package com.xiaopeng.appstore.bizcommon.utils;

import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.R;
import com.xiaopeng.appstore.bizcommon.logic.Constants;
import com.xiaopeng.appstore.libcommon.utils.DateUtils;
import com.xiaopeng.appstore.libcommon.utils.ImageUtils;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
import com.xiaopeng.appstore.libcommon.utils.SPUtils;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
/* loaded from: classes2.dex */
public class LogicUtils {
    private static final long ONE_DAY_MILLI = 86400000;
    public static final String SIZE_FORMAT = "%.1f";
    private static final String TAG = "LogicUtils";
    private static final int sLegacyBgColor = ResUtils.getColor(17170443);
    private static final String SUSPEND_APP = ResUtils.getString(R.string.app_suspend);
    private static Map<String, Boolean> suspendStatusMap = new ConcurrentHashMap();

    private static int convertAppOperationState(int appState) {
        if (appState != 0) {
            if (appState != 2) {
                if (appState != 4) {
                    return appState != 6 ? -1 : 2;
                }
                return 3;
            }
            return 6;
        }
        return 1;
    }

    public static Map<String, Boolean> getSuspendStatusMap() {
        return suspendStatusMap;
    }

    public static String humanReadableBytes(long bytes, boolean si) {
        int log;
        int i = si ? 1000 : 1024;
        if (bytes < i) {
            return bytes + " B";
        }
        double d = bytes;
        double d2 = i;
        return String.format(Locale.ENGLISH, "%.1f %sB", Double.valueOf(d / Math.pow(d2, (int) (Math.log(d) / Math.log(d2)))), (si ? "kMGTPE" : "KMGTPE").charAt(log - 1) + (si ? "" : "i"));
    }

    public static String getAppSizeString(double size) {
        if (size <= 0.0d) {
            return "";
        }
        double d = 1024;
        double d2 = size / d;
        if (d2 < 1.0d) {
            return String.format(Locale.ENGLISH, SIZE_FORMAT, Double.valueOf(size)) + "B";
        }
        double d3 = d2 / d;
        if (d3 < 1.0d) {
            return String.format(Locale.ENGLISH, SIZE_FORMAT, Double.valueOf(d2)) + "KB";
        }
        double d4 = d3 / d;
        return d4 < 1.0d ? String.format(Locale.ENGLISH, SIZE_FORMAT, Double.valueOf(d3)) + "MB" : d4 / d < 1.0d ? String.format(Locale.ENGLISH, SIZE_FORMAT, Double.valueOf(d4)) + "GB" : "";
    }

    public static String getUpgradeDate(long date) {
        if (System.currentTimeMillis() - date < ONE_DAY_MILLI) {
            return ResUtils.getString(R.string.today);
        }
        return DateUtils.getDate(ResUtils.getString(R.string.date_format), date);
    }

    public static String getLastInstalledApp() {
        return SPUtils.getInstance().getString(Constants.SP_LAST_INSTALLED_APP_KEY);
    }

    public static void setLastInstalledApp(String packageName) {
        SPUtils.getInstance().put(Constants.SP_LAST_INSTALLED_APP_KEY, packageName);
    }

    public static AdaptiveIconDrawable generateAdaptiveIcon(Drawable foreground) {
        return ImageUtils.generateAdaptiveIcon(foreground, sLegacyBgColor);
    }

    public static void loadStoreIcon(final ImageView imageView, String url, Drawable placeholder, Drawable fallback) {
        RequestBuilder<Drawable> createRequestBuilder = ImageUtils.createRequestBuilder(imageView, url, generateAdaptiveIcon(placeholder), generateAdaptiveIcon(fallback), 0, -1);
        if (createRequestBuilder != null) {
            createRequestBuilder.into((RequestBuilder<Drawable>) new ImageViewTarget<Drawable>(imageView) { // from class: com.xiaopeng.appstore.bizcommon.utils.LogicUtils.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.bumptech.glide.request.target.ImageViewTarget
                public void setResource(Drawable resource) {
                    if (resource == null) {
                        return;
                    }
                    imageView.setImageDrawable(LogicUtils.generateAdaptiveIcon(resource));
                }
            });
        }
    }

    public static String getUrlWithoutSchema(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url.replace("http:", "").replace("https:", "");
    }

    public static String replaceHttp(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url.replace("http:", "https:");
    }

    public static void tryExecuteSuspendApp(String packageName, int state, boolean isSuspended, Runnable action, Consumer<String> onFailed) {
        Logger.t(TAG).d("packageName = " + packageName + ", isSuspended = " + isSuspended);
        if (state == 1 || state == 4 || state == 5) {
            action.run();
            return;
        }
        Map<String, Boolean> map = suspendStatusMap;
        if (map != null) {
            Boolean bool = map.get(packageName);
            if (bool != null) {
                isSuspended = bool.booleanValue();
            } else {
                suspendStatusMap.put(packageName, Boolean.valueOf(isSuspended));
            }
        } else {
            isSuspended = false;
        }
        Logger.t(TAG).d("suspendStatusMap = " + suspendStatusMap);
        Logger.t(TAG).d("packageName = " + packageName + ", defaultSuspend = " + isSuspended);
        if (!isSuspended) {
            action.run();
        } else if (onFailed != null) {
            onFailed.accept(SUSPEND_APP);
        } else {
            Logger.t(TAG).w("Prevent execute suspended app, onFailed is null:" + packageName, new Object[0]);
        }
    }

    public static boolean isDefaultSuspendStatus(int statusInt, boolean isSuspended, String packageName) {
        if (statusInt == 1 || statusInt == 4 || statusInt == 5) {
            return false;
        }
        Map<String, Boolean> map = suspendStatusMap;
        if (map != null) {
            Boolean bool = map.get(packageName);
            if (bool != null) {
                isSuspended = bool.booleanValue();
            } else {
                suspendStatusMap.put(packageName, Boolean.valueOf(isSuspended));
            }
        } else {
            isSuspended = false;
        }
        Logger.t(TAG).d("suspendStatusMap = " + suspendStatusMap);
        Logger.t(TAG).d("packageName = " + packageName + ", defaultSuspend = " + isSuspended);
        return isSuspended;
    }

    public static int convertAppOperationStatePause(int appState) {
        int convertAppOperationState = convertAppOperationState(appState);
        if (convertAppOperationState == -1 && appState == 1) {
            return 5;
        }
        return convertAppOperationState;
    }

    public static int convertAppOperationStateCancel(int appState) {
        int convertAppOperationState = convertAppOperationState(appState);
        if (convertAppOperationState == -1 && appState == 1) {
            return 4;
        }
        return convertAppOperationState;
    }
}
