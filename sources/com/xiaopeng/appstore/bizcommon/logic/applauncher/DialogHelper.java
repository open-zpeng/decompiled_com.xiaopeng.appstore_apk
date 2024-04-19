package com.xiaopeng.appstore.bizcommon.logic.applauncher;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.R;
import com.xiaopeng.appstore.bizcommon.logic.AppStateContract;
import com.xiaopeng.appstore.bizcommon.logic.applauncher.DialogHelper;
import com.xiaopeng.appstore.common_ui.Constants;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import com.xiaopeng.appstore.storeprovider.AssembleRequest;
import com.xiaopeng.appstore.storeprovider.AssembleResult;
import com.xiaopeng.appstore.storeprovider.StoreProviderManager;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventEnum;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventTrackingHelper;
import com.xiaopeng.appstore.xpcommon.eventtracking.PagesEnum;
import java.util.Iterator;
import java.util.List;
/* loaded from: classes2.dex */
public class DialogHelper {
    private static final String TAG = "LaunchDialogHelper";
    private IDialog mDialog;
    private final Handler mMainHandler = new Handler(Looper.getMainLooper());
    private IToast mToast;

    /* loaded from: classes2.dex */
    public interface DialogButtonClickListener {
        public static final int BUTTON_NEGATIVE = -2;
        public static final int BUTTON_POSITIVE = -1;

        void onClick(IDialog dialog, int buttonId);
    }

    /* loaded from: classes2.dex */
    public enum DialogType {
        NONE,
        SUSPEND,
        FORCE_UPDATE_GO_APPSTORE,
        FORCE_UPDATE_SILENT_DOWNLOAD,
        FORCE_UPDATING
    }

    /* loaded from: classes2.dex */
    public interface IDialog {
        void dismiss();

        void setMessage(CharSequence text);

        void setNegativeButton(CharSequence text, DialogButtonClickListener listener);

        void setPositiveButton(CharSequence text, DialogButtonClickListener listener);

        void setScreenId(int screenId);

        void setTitle(CharSequence text);

        void show();
    }

    /* loaded from: classes2.dex */
    public interface IToast {
        void show(String msg);

        void showAt(String msg, int screenId);
    }

    public DialogHelper(IDialog dialog, IToast toast) {
        this.mDialog = dialog;
        this.mToast = toast;
    }

    private void runOnUiThread(Runnable action) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            action.run();
        } else {
            this.mMainHandler.post(action);
        }
    }

    public /* synthetic */ void lambda$show$0$DialogHelper(Context context, String str, String str2, DialogType dialogType) {
        lambda$show$1$DialogHelper(context, str, str2, dialogType, 0);
    }

    public void show(final Context context, final String packageName, final String name, final DialogType dialogType) {
        runOnUiThread(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.applauncher.-$$Lambda$DialogHelper$plgaZSCFpOnbWLe4wJrLLJDUB90
            @Override // java.lang.Runnable
            public final void run() {
                DialogHelper.this.lambda$show$0$DialogHelper(context, packageName, name, dialogType);
            }
        });
    }

    public void show(final Context context, final String packageName, final String name, final DialogType dialogType, final int screenId) {
        runOnUiThread(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.applauncher.-$$Lambda$DialogHelper$plD5RKxCS8GIz0g3YYagngavkRI
            @Override // java.lang.Runnable
            public final void run() {
                DialogHelper.this.lambda$show$1$DialogHelper(context, packageName, name, dialogType, screenId);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: showInner */
    public void lambda$show$1$DialogHelper(final Context context, final String packageName, final String name, final DialogType dialogType, final int screenId) {
        Logger.t(TAG).i("show dialog, pn:" + packageName + ", name:" + name + ", type:" + dialogType + ", screenId:" + screenId, new Object[0]);
        if (dialogType == DialogType.SUSPEND) {
            showDialog(context.getString(R.string.app_suspend_title), context.getString(R.string.app_suspend), null, context.getString(R.string.suspend_dialog_dismiss), null, screenId);
        } else if (dialogType == DialogType.FORCE_UPDATE_GO_APPSTORE || dialogType == DialogType.FORCE_UPDATE_SILENT_DOWNLOAD) {
            showDialog(context.getString(R.string.app_suspend_update_title), context.getString(R.string.app_suspend_force_update), context.getString(R.string.dialog_negative_btn), context.getString(R.string.suspend_dialog_go_update), new DialogButtonClickListener() { // from class: com.xiaopeng.appstore.bizcommon.logic.applauncher.-$$Lambda$DialogHelper$CedWxg66D3EoTNL-9pqY6hcIYiQ
                @Override // com.xiaopeng.appstore.bizcommon.logic.applauncher.DialogHelper.DialogButtonClickListener
                public final void onClick(DialogHelper.IDialog iDialog, int i) {
                    DialogHelper.this.lambda$showInner$2$DialogHelper(packageName, name, context, dialogType, screenId, iDialog, i);
                }
            }, screenId);
        } else if (dialogType == DialogType.FORCE_UPDATING) {
            showDialog(context.getString(R.string.app_suspend_update_title), context.getString(R.string.app_suspend_force_updating), null, context.getString(R.string.suspend_dialog_dismiss), null, screenId);
        } else {
            Logger.t(TAG).i("Undefined status " + dialogType, new Object[0]);
        }
    }

    public /* synthetic */ void lambda$showInner$2$DialogHelper(String str, String str2, Context context, DialogType dialogType, int i, IDialog iDialog, int i2) {
        if (i2 == -1) {
            sendDialogClickLog(str, str2, "1");
            goUpdate(context, str, dialogType == DialogType.FORCE_UPDATE_SILENT_DOWNLOAD, i);
        } else if (i2 == -2) {
            sendDialogClickLog(str, str2, "2");
        }
    }

    private void showDialog(String title, String message, String negative, String positive, DialogButtonClickListener clickListener) {
        showDialog(title, message, negative, positive, clickListener, 0);
    }

    private void showDialog(String title, String message, String negative, String positive, DialogButtonClickListener clickListener, int screenId) {
        IDialog iDialog = this.mDialog;
        if (iDialog == null) {
            Logger.t(TAG).e("showDialog error, dialog is null. title:" + title + ", msg:" + message, new Object[0]);
            return;
        }
        iDialog.setTitle(title);
        this.mDialog.setMessage(message);
        this.mDialog.setPositiveButton(positive, clickListener);
        this.mDialog.setNegativeButton(negative, clickListener);
        this.mDialog.setScreenId(screenId);
        this.mDialog.show();
    }

    private void sendDialogClickLog(String packageName, String name, String args) {
        EventTrackingHelper.sendMolecast(PagesEnum.STORE_FORCE_UPDATE_DIALOG, EventEnum.FORCE_UPDATE_DIALOG, args, packageName, name);
    }

    private void goUpdate(Context context, String packageName, boolean silentDownload, int screenId) {
        int i;
        List<AppStateContract.AppState> appStateList = AppStateContract.Cache.get().getAppStateList();
        Logger.t(TAG).i("goUpdate, pn:" + packageName + ", stateList:" + appStateList, new Object[0]);
        if (appStateList != null) {
            Iterator<AppStateContract.AppState> it = appStateList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    i = 0;
                    break;
                }
                AppStateContract.AppState next = it.next();
                if (next.getPackageName().equals(packageName)) {
                    i = next.getState();
                    break;
                }
            }
            if (i == 1001) {
                tryToastStateError(context, screenId);
            } else if (i != 1051) {
                Logger.t(TAG).i("appState = " + i, new Object[0]);
            } else if (silentDownload) {
                startUpdatingSilent(packageName);
            } else {
                goToDetailPage(context, packageName, screenId);
            }
        }
    }

    private void startUpdatingSilent(final String packageName) {
        AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.applauncher.-$$Lambda$DialogHelper$E6719g99lbcfuTyu4rIRJ3HSzks
            @Override // java.lang.Runnable
            public final void run() {
                DialogHelper.lambda$startUpdatingSilent$3(packageName);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$startUpdatingSilent$3(String str) {
        Logger.t(TAG).i("start updating " + str, new Object[0]);
        AssembleResult assemble = StoreProviderManager.get().assemble(AssembleRequest.enqueue(1000, str, null).request());
        if (assemble == null || !assemble.isSuccessful()) {
            Logger.t(TAG).i("startUpdating fail:" + str + ", " + assemble, new Object[0]);
        } else {
            Logger.t(TAG).i("startUpdating success:" + str + ", " + assemble, new Object[0]);
        }
    }

    private void goToDetailPage(Context context, String packageName, int screenId) {
        Logger.t(TAG).d("startAppStore, pn:" + packageName);
        Intent intent = new Intent(Constants.ACTION_TO_APP_STORE);
        intent.setData(Uri.parse(Constants.DETAILS_URL_PREFIX + packageName));
        intent.setPackage("com.xiaopeng.appstore");
        intent.putExtra("com.xiaopeng.appstore.intent.extra.SCREEN", screenId);
        context.sendBroadcast(intent);
    }

    private void tryToastStateError(final Context context, final int screenId) {
        if (this.mToast != null) {
            runOnUiThread(new Runnable() { // from class: com.xiaopeng.appstore.bizcommon.logic.applauncher.-$$Lambda$DialogHelper$QvTbD0B1TpQdh5lIFOIvlxeBVCE
                @Override // java.lang.Runnable
                public final void run() {
                    DialogHelper.this.lambda$tryToastStateError$4$DialogHelper(context, screenId);
                }
            });
        } else {
            Logger.t(TAG).e("tryToastStateError, toast is null.", new Object[0]);
        }
    }

    public /* synthetic */ void lambda$tryToastStateError$4$DialogHelper(Context context, int i) {
        this.mToast.showAt(context.getString(R.string.app_state_error), i);
    }

    public void dismissDialog() {
        IDialog iDialog = this.mDialog;
        if (iDialog != null) {
            iDialog.dismiss();
        }
    }
}
