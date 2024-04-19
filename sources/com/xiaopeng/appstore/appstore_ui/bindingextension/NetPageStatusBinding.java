package com.xiaopeng.appstore.appstore_ui.bindingextension;

import android.view.View;
import com.xiaopeng.appstore.common_ui.common.Resource;
/* loaded from: classes2.dex */
public class NetPageStatusBinding {
    public static void bindErrorVisibility(View view, Resource<?> resource) {
        if (resource == null) {
            return;
        }
        view.setVisibility(resource.status == Resource.Status.ERROR ? 0 : 8);
    }

    public static void bindEmptyVisibility(View view, Resource<?> resource) {
        if (resource == null) {
            return;
        }
        view.setVisibility(resource.status == Resource.Status.EMPTY ? 0 : 8);
    }

    public static void bindLoadingVisibility(View view, Resource<?> resource) {
        if (resource == null) {
            return;
        }
        view.setVisibility(resource.status == Resource.Status.LOADING ? 0 : 8);
    }

    public static void bindContentVisibility(View view, Resource<?> resource) {
        if (resource == null) {
            return;
        }
        view.setVisibility(resource.status == Resource.Status.SUCCESS ? 0 : 8);
    }
}
