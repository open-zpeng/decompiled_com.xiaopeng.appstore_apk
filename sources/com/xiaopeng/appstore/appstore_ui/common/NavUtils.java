package com.xiaopeng.appstore.appstore_ui.common;

import android.os.Bundle;
import androidx.navigation.NavController;
import com.xiaopeng.appstore.appstore_biz.logic.AppStoreLogicUtils;
import com.xiaopeng.appstore.appstore_ui.R;
/* loaded from: classes2.dex */
public class NavUtils {
    private NavUtils() {
    }

    public static void goToDetail(NavController navController, String packageName) {
        AppStoreLogicUtils.removeLastView();
        Bundle bundle = new Bundle();
        bundle.putSerializable("detail_page_app_package_name", packageName);
        navController.navigate(R.id.action_to_itemDetailFragment, bundle);
    }
}
