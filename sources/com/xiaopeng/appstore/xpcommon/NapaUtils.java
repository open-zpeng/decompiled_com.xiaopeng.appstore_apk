package com.xiaopeng.appstore.xpcommon;

import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.google.gson.reflect.TypeToken;
import com.xiaopeng.appstore.libcommon.utils.FileUtils;
import com.xiaopeng.appstore.libcommon.utils.GsonUtil;
import com.xiaopeng.lib.apirouter.ApiRouter;
import java.util.Map;
/* loaded from: classes2.dex */
public class NapaUtils {
    private static String TAG = "NapaUtils";
    static Map<String, OpenAppConfigBean> mOpenAppConfigs;

    public static void initCOnfig() {
        mOpenAppConfigs = (Map) GsonUtil.fromJson(FileUtils.loadFileFromAsset("openNapaAppConfigs.json"), new TypeToken<Map<String, OpenAppConfigBean>>() { // from class: com.xiaopeng.appstore.xpcommon.NapaUtils.1
        }.getType());
    }

    public static String openNapaApp(String idString, String param) {
        String str = "";
        if (mOpenAppConfigs == null) {
            Log.d(TAG, "mOpenAppConfigs must not null");
            return "";
        }
        Uri.Builder builder = new Uri.Builder();
        OpenAppConfigBean openAppConfigBean = mOpenAppConfigs.get(idString);
        if (openAppConfigBean != null) {
            if (TextUtils.isEmpty(openAppConfigBean.authority)) {
                return "Native";
            }
            builder.authority(openAppConfigBean.authority).path("openNapaApp").appendQueryParameter("param", param);
            try {
                str = (String) ApiRouter.route(builder.build());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return TextUtils.isEmpty(str) ? openAppConfigBean.fallbackUrl : str;
        }
        return "";
    }
}
