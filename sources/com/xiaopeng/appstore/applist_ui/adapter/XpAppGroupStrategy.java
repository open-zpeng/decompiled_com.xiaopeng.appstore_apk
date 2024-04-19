package com.xiaopeng.appstore.applist_ui.adapter;

import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.text.style.TextAppearanceSpan;
import com.xiaopeng.appstore.applist_biz.model.BaseAppItem;
import com.xiaopeng.appstore.applist_biz.model.NapaAppItem;
import com.xiaopeng.appstore.applist_ui.R;
import com.xiaopeng.appstore.applist_ui.adapter.AppGroupHelper;
import com.xiaopeng.appstore.bizcommon.logic.Constants;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.util.SilentInstallHelper;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
import com.xiaopeng.appstore.libcommon.utils.Utils;
import com.xiaopeng.appstore.xpcommon.CarUtils;
import com.xiaopeng.appstore.xpcommon.SystemPropUtils;
import java.util.Arrays;
import java.util.List;
/* loaded from: classes2.dex */
public class XpAppGroupStrategy implements AppGroupHelper.IAppGroupStrategy {
    private static final int GROUP_COUNT = 2;
    private static final int GROUP_SYSTEM_APP = 1;
    private static final int GROUP_THIRD_APP = 2;
    private static final int STR_DESC_IC_SIZE = ResUtils.getInteger(R.integer.other_app_desc_ic_size);
    private static int S_COLUMN = 0;
    private static final String TAG = "XpAppGroupStrategy";
    private final List<String> mXpAppList;

    @Override // com.xiaopeng.appstore.applist_ui.adapter.AppGroupHelper.IAppGroupStrategy
    public int getGroupCount() {
        return 2;
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.AppGroupHelper.IAppGroupStrategy
    public int getGroupIndex(BaseAppItem appItem) {
        return -1;
    }

    public XpAppGroupStrategy() {
        S_COLUMN = Utils.getApp().getResources().getInteger(R.integer.store_app_icon_column_size);
        this.mXpAppList = CarUtils.isEURegion() ? Arrays.asList(Constants.XP_INTER_APP_LIST) : Arrays.asList(Constants.XP_APP_LIST);
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.AppGroupHelper.IAppGroupStrategy
    public int getCountsPerRow() {
        return S_COLUMN;
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.AppGroupHelper.IAppGroupStrategy
    public int getGroup(BaseAppItem appItem) {
        return isTop(appItem) ? 1 : 2;
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.AppGroupHelper.IAppGroupStrategy
    public CharSequence getGroupTitle(int group) {
        if (group == 1) {
            return ResUtils.getString(R.string.xp_app_title);
        }
        String string = ResUtils.getString(R.string.other_app_title);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(string);
        String string2 = ResUtils.getString(R.string.other_app_desc);
        spannableStringBuilder.append((CharSequence) string2);
        int length = string.length();
        int length2 = string2.length();
        if (!CarUtils.isEURegion()) {
            Drawable drawable = ResUtils.getDrawable(R.drawable.ic_tips);
            int i = STR_DESC_IC_SIZE;
            drawable.setBounds(0, 0, i, i);
            spannableStringBuilder.setSpan(new ImageSpan(drawable), length, length + 1, 18);
        }
        spannableStringBuilder.setSpan(new TextAppearanceSpan(Utils.getApp(), CarUtils.isEURegion() ? R.style.Inter_OtherAppDescTextAppearance : R.style.OtherAppDescTextAppearance), length, length2 + length, 33);
        return spannableStringBuilder;
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.AppGroupHelper.IAppGroupStrategy
    public boolean filter(BaseAppItem appItem) {
        if (filterXSport(appItem)) {
            return true;
        }
        return SilentInstallHelper.isHiddenPackage(appItem.packageName);
    }

    private boolean filterXSport(BaseAppItem appItem) {
        return appItem.packageName.equals("com.xiaopeng.xsport") && !"1".equals(SystemPropUtils.get("persist.sys.xiaopeng.XSPORT"));
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
