package com.xiaopeng.appstore.appstore_ui.common;

import android.text.TextUtils;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.bizcommon.utils.LogicUtils;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
/* loaded from: classes2.dex */
public class BindingHelper {
    public static int getProgressType(int state) {
        return state == 2 ? 0 : 2;
    }

    private BindingHelper() {
    }

    public static String getVersionDesc(String versionName) {
        return !TextUtils.isEmpty(versionName) ? "" + String.format(ResUtils.getString(R.string.item_detail_version), versionName) : "";
    }

    public static String getVersionDate(long versionDate) {
        return versionDate > 0 ? "" + LogicUtils.getUpgradeDate(versionDate) : "";
    }

    public static String getDetailVersionDesc(String versionName, long versionDate) {
        String str = TextUtils.isEmpty(versionName) ? "" : "" + String.format(ResUtils.getString(R.string.item_detail_version), versionName);
        if (versionDate > 0) {
            if (!TextUtils.isEmpty(str)) {
                str = str + "   ";
            }
            return str + LogicUtils.getUpgradeDate(versionDate);
        }
        return str;
    }
}
