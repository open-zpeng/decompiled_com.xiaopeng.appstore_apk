package com.xiaopeng.appstore.debug;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.AppUtils;
import com.xiaopeng.appstore.R;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.util.SilentInstallHelper;
import com.xiaopeng.appstore.bizcommon.utils.AccountUtils;
import com.xiaopeng.appstore.common_ui.base.PanelDialog;
import com.xiaopeng.appstore.common_ui.widget.XTabLayout;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
import com.xiaopeng.appstore.libcommon.utils.SPUtils;
import com.xiaopeng.appstore.libcommon.utils.Utils;
import com.xiaopeng.appstore.xpcommon.ApiEnvHelper;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.widget.XSwitch;
/* loaded from: classes.dex */
public class DebugDialog implements CompoundButton.OnCheckedChangeListener, XTabLayout.OnTabChangeListener {
    public static final String DEBUG_DIALOG_ACCOUNT_SWITCH_KEY = "debug_dialog_account_switch_key";
    public static final String DEBUG_DIALOG_USE_DETAIL_SWITCH_KEY = "debug_dialog_use_detail_switch_key";
    private static final String RESOURCE_PROCESS_NAME = "com.xiaopeng.appstore:resource";
    private static final String TAG = "DebugDialog";
    private final PanelDialog mDialog;

    @Override // com.xiaopeng.appstore.common_ui.widget.XTabLayout.OnTabChangeListener
    public void onTabChangeEnd(XTabLayout xTabLayout, int i, boolean b, boolean b1) {
    }

    @Override // com.xiaopeng.appstore.common_ui.widget.XTabLayout.OnTabChangeListener
    public void onTabChangeStart(XTabLayout xTabLayout, int i, boolean b, boolean b1) {
    }

    public DebugDialog(Context context) {
        PanelDialog panelDialog = new PanelDialog(context, 2132017976);
        this.mDialog = panelDialog;
        panelDialog.setTitle(ResUtils.getString(R.string.debug_title)).setNegativeButtonEnable(true).setNegativeButton(ResUtils.getString(R.string.debug_close), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.appstore.debug.-$$Lambda$DebugDialog$-sRfdbwE2uFj9loiKmnRMX1uCZg
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                xDialog.dismiss();
            }
        });
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_debug, (ViewGroup) null);
        panelDialog.setCustomView(inflate);
        panelDialog.getDialog().setCanceledOnTouchOutside(false);
        ((TextView) inflate.findViewById(R.id.version_content)).setText(AppUtils.getVerDesc(context));
        XSwitch xSwitch = (XSwitch) inflate.findViewById(R.id.switch_account);
        AccountUtils.DEBUG = SPUtils.getInstance().getBoolean(DEBUG_DIALOG_ACCOUNT_SWITCH_KEY, false);
        xSwitch.setChecked(!AccountUtils.DEBUG);
        xSwitch.setOnCheckedChangeListener(this);
        XSwitch xSwitch2 = (XSwitch) inflate.findViewById(R.id.switch_use_details);
        SilentInstallHelper.USE_DETAILS_LIST_API = SPUtils.getInstance().getBoolean(DEBUG_DIALOG_USE_DETAIL_SWITCH_KEY, false);
        xSwitch2.setChecked(SilentInstallHelper.USE_DETAILS_LIST_API);
        xSwitch2.setOnCheckedChangeListener(this);
        XTabLayout xTabLayout = (XTabLayout) inflate.findViewById(R.id.host_tab);
        String host = ApiEnvHelper.getHost();
        if (ApiEnvHelper.APP_STORE_HOST_TEST.equals(host)) {
            xTabLayout.selectTab(1);
        } else if (ApiEnvHelper.APP_STORE_HOST_PRE.equals(host)) {
            xTabLayout.selectTab(2);
        } else {
            xTabLayout.selectTab(0);
        }
        xTabLayout.setOnTabChangeListener(this);
        ((XSwitch) inflate.findViewById(R.id.switch_dm_log)).setOnCheckedChangeListener(this);
    }

    public void show() {
        if (this.mDialog.getDialog().isShowing()) {
            return;
        }
        this.mDialog.show();
    }

    public void dismiss() {
        this.mDialog.dismiss();
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        if (id == R.id.switch_account) {
            AccountUtils.DEBUG = !isChecked;
            SPUtils.getInstance().put(DEBUG_DIALOG_ACCOUNT_SWITCH_KEY, AccountUtils.DEBUG);
            Logger.t(TAG).d("onDebugModeChanged, account debug=%s.", Boolean.valueOf(AccountUtils.DEBUG));
        } else if (id != R.id.switch_use_details) {
        } else {
            SilentInstallHelper.USE_DETAILS_LIST_API = isChecked;
            SPUtils.getInstance().put(DEBUG_DIALOG_USE_DETAIL_SWITCH_KEY, SilentInstallHelper.USE_DETAILS_LIST_API);
            Logger.t(TAG).d("onDebugModeChanged, details debug=%s.", Boolean.valueOf(SilentInstallHelper.USE_DETAILS_LIST_API));
        }
    }

    @Override // com.xiaopeng.appstore.common_ui.widget.XTabLayout.OnTabChangeListener
    public boolean onInterceptTabChange(XTabLayout xTabLayout, final int index, boolean tabChange, boolean fromUser) {
        if (xTabLayout.getId() == R.id.host_tab && fromUser) {
            if (index == 1) {
                ApiEnvHelper.switchTest();
            } else if (index == 2) {
                ApiEnvHelper.switchPre();
            } else {
                ApiEnvHelper.resetEnv();
            }
            this.mDialog.setNegativeButton(ResUtils.getString(R.string.debug_close_kill), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.appstore.debug.-$$Lambda$DebugDialog$W4ibOcLX6s78SbYe798PG7u9Qfs
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    DebugDialog.this.lambda$onInterceptTabChange$1$DebugDialog(xDialog, i);
                }
            });
            return false;
        }
        return false;
    }

    public /* synthetic */ void lambda$onInterceptTabChange$1$DebugDialog(XDialog xDialog, int i) {
        closeApp();
    }

    private void closeApp() {
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) Utils.getApp().getSystemService("activity")).getRunningAppProcesses()) {
            if (runningAppProcessInfo.processName.equals("com.xiaopeng.appstore:resource")) {
                Process.killProcess(runningAppProcessInfo.pid);
            }
        }
        Process.killProcess(Process.myPid());
    }
}
