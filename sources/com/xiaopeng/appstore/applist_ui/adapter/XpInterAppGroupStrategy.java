package com.xiaopeng.appstore.applist_ui.adapter;

import android.graphics.drawable.Drawable;
import com.xiaopeng.appstore.applist_biz.model.BaseAppItem;
import com.xiaopeng.appstore.applist_biz.model.NapaAppItem;
import com.xiaopeng.appstore.applist_ui.R;
import com.xiaopeng.appstore.applist_ui.adapter.AppGroupHelper;
import com.xiaopeng.appstore.bizcommon.logic.Constants;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
import com.xiaopeng.appstore.libcommon.utils.Utils;
import java.util.Arrays;
import java.util.List;
/* loaded from: classes2.dex */
public class XpInterAppGroupStrategy implements AppGroupHelper.IAppGroupStrategy {
    private static final int GROUP_COUNT = 2;
    private static final int GROUP_SYSTEM_APP = 1;
    private static final int GROUP_THIRD_APP = 2;
    private static int S_COLUMN = 0;
    private static final String TAG = "XpInterAppGroupStrategy";
    private final List<String> mXpAppList;
    private static final Drawable TIPS_DRAWABLE = ResUtils.getDrawable(R.drawable.ic_tips);
    private static final int STR_DESC_IC_SIZE = ResUtils.getInteger(R.integer.other_app_desc_ic_size);

    @Override // com.xiaopeng.appstore.applist_ui.adapter.AppGroupHelper.IAppGroupStrategy
    public boolean filter(BaseAppItem appItem) {
        return false;
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.AppGroupHelper.IAppGroupStrategy
    public int getGroupCount() {
        return 2;
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.AppGroupHelper.IAppGroupStrategy
    public int getGroupIndex(BaseAppItem appItem) {
        return -1;
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.AppGroupHelper.IAppGroupStrategy
    public CharSequence getGroupTitle(int group) {
        return "";
    }

    public XpInterAppGroupStrategy() {
        S_COLUMN = Utils.getApp().getResources().getInteger(R.integer.store_app_icon_column_size);
        this.mXpAppList = Arrays.asList(Constants.XP_INTER_APP_LIST);
        Drawable drawable = TIPS_DRAWABLE;
        int i = STR_DESC_IC_SIZE;
        drawable.setBounds(0, 0, i, i);
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.AppGroupHelper.IAppGroupStrategy
    public int getCountsPerRow() {
        return S_COLUMN;
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.AppGroupHelper.IAppGroupStrategy
    public int getGroup(BaseAppItem appItem) {
        return isTop(appItem) ? 1 : 2;
    }

    private boolean isTop(BaseAppItem appItem) {
        if (appItem == null) {
            return false;
        }
        if (appItem instanceof NapaAppItem) {
            return true;
        }
        return this.mXpAppList.contains(appItem.packageName);
    }
}
