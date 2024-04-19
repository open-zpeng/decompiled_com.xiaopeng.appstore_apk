package com.xiaopeng.appstore.common_ui.base;

import android.content.Context;
import com.xiaopeng.xui.app.XDialog;
/* loaded from: classes.dex */
public class PanelDialog extends XDialog {
    public PanelDialog(Context context) {
        this(context, 0);
        init();
    }

    public PanelDialog(Context context, int style) {
        super(context, style, XDialog.Parameters.Builder().setFullScreen(true));
        init();
    }

    private void init() {
        setSystemDialog(2008);
    }
}
