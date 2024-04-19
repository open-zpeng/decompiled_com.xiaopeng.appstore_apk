package com.xiaopeng.appstore;

import android.util.Log;
import com.xiaopeng.appstore.libcommon.utils.Utils;
import com.xiaopeng.lib.apirouter.server.IServicePublisher;
import com.xiaopeng.speech.vui.VuiEngine;
/* loaded from: classes2.dex */
public class VuiObserver implements IServicePublisher {
    public static final String CLASS_PATH = "com.xiaopeng.appstore.VuiObserver";
    public static final int LOG_LEVEL = 3;

    public void onEvent(String event, String data) {
        Log.i("VuiObserver", "onEvent event== " + event + ", data==" + event);
        VuiEngine.getInstance(Utils.getApp()).dispatchVuiEvent(event, data);
    }

    public String getElementState(String sceneId, String elementId) {
        String elementState = VuiEngine.getInstance(Utils.getApp()).getElementState(sceneId, elementId);
        Log.i("VuiObserver", "getElementState sceneId== " + sceneId + ", elementId==" + elementId + ", result==" + elementState);
        return elementState;
    }
}
