package com.xiaopeng.appstore.appstore_biz.logic;

import com.xiaopeng.appstore.bizcommon.logic.Constants;
import com.xiaopeng.appstore.libcommon.utils.SPUtils;
/* loaded from: classes2.dex */
public class AppStoreLogicUtils {
    public static final int TYPE_GRID = 3000;
    public static final int TYPE_HORIZONTAL = 3002;
    public static final int TYPE_WIDE_GRID = 3001;

    public static int getPackageItemType(int itemListSize) {
        if (itemListSize == 3 || itemListSize == 6 || itemListSize == 9) {
            return 3000;
        }
        return (itemListSize == 2 || itemListSize == 4 || itemListSize == 8) ? 3001 : 3002;
    }

    public static String getLastViewPackage() {
        return SPUtils.getInstance().getString(Constants.SP_LAST_VIEW_ITEM_PACKAGE_NAME);
    }

    public static void setLastViewPackage(String packageName) {
        SPUtils.getInstance().put(Constants.SP_LAST_VIEW_ITEM_PACKAGE_NAME, packageName);
    }

    public static void removeLastView() {
        SPUtils.getInstance().remove(Constants.SP_LAST_VIEW_ITEM_PACKAGE_NAME);
    }
}
