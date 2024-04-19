package com.xiaopeng.appstore.ui.tabs;

import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import com.xiaopeng.appstore.R;
import com.xiaopeng.appstore.international.InternationalRootFragment;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
import com.xiaopeng.appstore.libcommon.utils.Utils;
/* loaded from: classes2.dex */
public class InterAppListTab extends HomeTabAbs {
    @Override // com.xiaopeng.appstore.ui.tabs.HomeTab
    public void initTitle() {
        SpannableString spannableString = new SpannableString(ResUtils.getString(R.string.applist_label));
        spannableString.setSpan(new TextAppearanceSpan(Utils.getApp(), R.style.TabIconAppearance), 0, 1, 33);
        setTitle(spannableString);
    }

    @Override // com.xiaopeng.appstore.ui.tabs.HomeTab
    public void initFragment() {
        setFragment(new InternationalRootFragment());
    }
}
