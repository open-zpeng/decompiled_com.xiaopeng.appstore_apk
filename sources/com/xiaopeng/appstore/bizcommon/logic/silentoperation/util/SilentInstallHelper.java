package com.xiaopeng.appstore.bizcommon.logic.silentoperation.util;

import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.ArraySet;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.SilentCellBean;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.element.alipayengine.SilentOperationAlipayEngineElement;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.task.SilentOperationTask;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.bean.task.alipayengine.SilentOperationAlipayEngineTask;
import com.xiaopeng.appstore.libcommon.utils.GsonUtil;
import com.xiaopeng.appstore.libcommon.utils.Utils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;
/* loaded from: classes2.dex */
public class SilentInstallHelper {
    private static ArrayList<SilentCellBean> SILENT_INSTALL_BEANS = null;
    public static final int SILENT_INSTALL_OPERATION = 1;
    public static final int SILENT_UNINSTALL_OPERATION = 2;
    public static final int SILENT_UPGRADE_OPERATION = 3;
    private static final String TAG = "SilentInstallHelper";
    private static SilentCellBean sAlipayEngineSilentCellBean;
    private static SilentOperationAlipayEngineElement.SilentOperationListener sAlipayListener;
    private static final CopyOnWriteArraySet<String> HIDDEN_PACKAGE_LIST = new CopyOnWriteArraySet<>();
    public static boolean USE_DETAILS_LIST_API = false;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface SilentOperation {
    }

    public static void setSilentInstallBeans(ArrayList<SilentCellBean> silentInstallBeans) {
        SILENT_INSTALL_BEANS = new ArrayList<>(new ArraySet(silentInstallBeans));
        Logger.t(TAG).d("SILENT_INSTALL_BEANS = " + GsonUtil.toJson(SILENT_INSTALL_BEANS));
    }

    public static void injectUpstreamOperation(String packageName) {
        injectUpstreamOperation(shouldUpstreamOperation(packageName));
    }

    public static void injectUpstreamOperation(SilentCellBean silentCellBean) {
        if (silentCellBean != null) {
            silentOperation(silentCellBean);
        }
    }

    public static void silentOperation(SilentCellBean silentCellBean) {
        if (AlipayEnginePrepareHelper.isAlipayAppOperation(silentCellBean.getOperationPackageName())) {
            sAlipayEngineSilentCellBean = silentCellBean;
            AlipayEnginePrepareHelper.getInstance().setAlipayEngineDownloadUrl(silentCellBean.getApkFileUrl());
            AlipayEnginePrepareHelper.getInstance().setAlipayEnginePackageName(silentCellBean.getOperationPackageName());
            SilentOperationAlipayEngineTask silentOperationAlipayEngineTask = new SilentOperationAlipayEngineTask(silentCellBean);
            Logger.t(TAG).d("silentOperation setAlipayListener listener = " + sAlipayListener);
            silentOperationAlipayEngineTask.setSilentOperationListener(sAlipayListener);
            silentOperationAlipayEngineTask.runTask();
            return;
        }
        new SilentOperationTask(silentCellBean).runTask();
    }

    public static void tryRunAlipayOperation() {
        if (sAlipayEngineSilentCellBean != null) {
            SilentOperationAlipayEngineTask silentOperationAlipayEngineTask = new SilentOperationAlipayEngineTask(sAlipayEngineSilentCellBean);
            Logger.t(TAG).d("tryRunAlipayOperation setAlipayListener listener = " + sAlipayListener);
            silentOperationAlipayEngineTask.setSilentOperationListener(sAlipayListener);
            silentOperationAlipayEngineTask.runTask();
            return;
        }
        Logger.t(TAG).w("tryRunAlipayOperation error, no task or no data.", new Object[0]);
    }

    public static void setAlipayListener(SilentOperationAlipayEngineElement.SilentOperationListener listener) {
        Logger.t(TAG).d("setAlipayListener listener = " + listener);
        sAlipayListener = listener;
    }

    public static SilentCellBean shouldUpstreamOperation(String packageName) {
        ArrayList<SilentCellBean> arrayList = SILENT_INSTALL_BEANS;
        if (arrayList != null) {
            Iterator<SilentCellBean> it = arrayList.iterator();
            while (it.hasNext()) {
                SilentCellBean next = it.next();
                if (packageName.equals(next.getOperationPackageName())) {
                    return next;
                }
            }
            return null;
        }
        return null;
    }

    public static boolean isPackageInstalled(String packageName) {
        try {
            Utils.getApp().getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static void appendHiddenPackageName(String packageName) {
        CopyOnWriteArraySet<String> copyOnWriteArraySet = HIDDEN_PACKAGE_LIST;
        copyOnWriteArraySet.add(packageName);
        Logger.t(TAG).d("append hidden package list = " + copyOnWriteArraySet);
    }

    public static void removeHiddenPackageName(String packageName) {
        CopyOnWriteArraySet<String> copyOnWriteArraySet = HIDDEN_PACKAGE_LIST;
        copyOnWriteArraySet.remove(packageName);
        Logger.t(TAG).d("remove hidden package list = " + copyOnWriteArraySet);
    }

    public static boolean isHiddenPackage(String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            Logger.t(TAG).w("isHiddenPackage, empty pn," + packageName, new Object[0]);
            return false;
        }
        Iterator<String> it = HIDDEN_PACKAGE_LIST.iterator();
        while (it.hasNext()) {
            if (packageName.toLowerCase().equals(it.next().toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
