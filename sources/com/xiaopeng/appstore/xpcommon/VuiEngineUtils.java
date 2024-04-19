package com.xiaopeng.appstore.xpcommon;

import android.content.Context;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.speech.vui.model.VuiFeedback;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.vui.commons.IVuiElementChangedListener;
import com.xiaopeng.vui.commons.IVuiSceneListener;
import com.xiaopeng.vui.commons.VuiUpdateType;
/* loaded from: classes2.dex */
public class VuiEngineUtils {
    public static void setVuiConfig(Context appContext, int logLevel, String className) {
        VuiEngine.getInstance(appContext).setLoglevel(logLevel);
        VuiEngine.getInstance(appContext).subscribe(className);
    }

    public static void addHasFeedbackProp(IVuiElement vuiView) {
        VuiUtils.addHasFeedbackProp(vuiView);
    }

    public static void initScene(final Context appContext, Lifecycle lifecycle, final String sceneId, View view, IVuiSceneListener listener) {
        VuiEngine.getInstance(appContext).initScene(lifecycle, sceneId, view, listener, new IVuiElementChangedListener() { // from class: com.xiaopeng.appstore.xpcommon.-$$Lambda$VuiEngineUtils$LZ0vVfEsR305ftj0p9crg97nU4M
            @Override // com.xiaopeng.vui.commons.IVuiElementChangedListener
            public final void onVuiElementChaned(View view2, VuiUpdateType vuiUpdateType) {
                VuiEngineUtils.lambda$initScene$0(appContext, sceneId, view2, vuiUpdateType);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$initScene$0(Context context, String str, View view, VuiUpdateType vuiUpdateType) {
        if (VuiUpdateType.UPDATE_VIEW.equals(vuiUpdateType)) {
            VuiEngine.getInstance(context).updateScene(str, view);
        } else {
            VuiEngine.getInstance(context).updateElementAttribute(str, view);
        }
    }

    private static void speakByVuiEngine(Context appContext, View view, String content) {
        VuiEngine.getInstance(appContext).vuiFeedback(view, new VuiFeedback.Builder().state(3).content(content).build());
    }

    public static void speakSliceByVuiEngine(Context appContext, View view) {
        speakByVuiEngine(appContext, view, "");
    }

    public static void speakExceptionByVuiEngine(Context appContext, View view, int contentId, Object... formatArgs) {
        speakByVuiEngine(appContext, view, appContext.getResources().getString(contentId, formatArgs));
    }
}
