package com.xiaopeng.appstore.common_ui;

import android.content.Context;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.bizcommon.logic.applauncher.DialogHelper;
import com.xiaopeng.appstore.common_ui.XpengUIFactory;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.app.XToast;
/* loaded from: classes.dex */
public class XpengUIFactory {
    public static DialogHelper.IDialog createDialog(Context context) {
        return new XpengDialog(context);
    }

    public static DialogHelper.IToast createToast(Context context) {
        return new XpengToast();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class XpengDialog implements DialogHelper.IDialog {
        private static final String TAG = "XpengDialog";
        private final Context mContext;
        private XDialog mDialog;

        XpengDialog(Context context) {
            Logger.t(TAG).i("XpengDialog construct:" + hashCode() + ", context:" + context, new Object[0]);
            this.mContext = context;
        }

        private void tryInitDialog() {
            if (this.mDialog == null) {
                this.mDialog = new XDialog(this.mContext);
                Logger.t(TAG).i("XpengDialog init:" + hashCode() + ", dialog:" + this.mDialog.hashCode(), new Object[0]);
                this.mDialog.setSystemDialog(2008);
                this.mDialog.setPositiveButton(this.mContext.getString(R.string.suspend_dialog_dismiss));
            }
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.applauncher.DialogHelper.IDialog
        public void setTitle(CharSequence text) {
            tryInitDialog();
            XDialog xDialog = this.mDialog;
            if (xDialog != null) {
                xDialog.setTitle(text);
            }
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.applauncher.DialogHelper.IDialog
        public void setMessage(CharSequence text) {
            tryInitDialog();
            XDialog xDialog = this.mDialog;
            if (xDialog != null) {
                xDialog.setMessage(text);
            }
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.applauncher.DialogHelper.IDialog
        public void setPositiveButton(CharSequence text, final DialogHelper.DialogButtonClickListener listener) {
            tryInitDialog();
            XDialog xDialog = this.mDialog;
            if (xDialog != null) {
                xDialog.setPositiveButton(text, new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.appstore.common_ui.-$$Lambda$XpengUIFactory$XpengDialog$mHI647S2W5U5flfCAY7kB90r1oU
                    @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                    public final void onClick(XDialog xDialog2, int i) {
                        XpengUIFactory.XpengDialog.this.lambda$setPositiveButton$0$XpengUIFactory$XpengDialog(listener, xDialog2, i);
                    }
                });
            }
        }

        public /* synthetic */ void lambda$setPositiveButton$0$XpengUIFactory$XpengDialog(DialogHelper.DialogButtonClickListener dialogButtonClickListener, XDialog xDialog, int i) {
            if (dialogButtonClickListener != null) {
                dialogButtonClickListener.onClick(this, i);
            }
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.applauncher.DialogHelper.IDialog
        public void setNegativeButton(CharSequence text, final DialogHelper.DialogButtonClickListener listener) {
            tryInitDialog();
            XDialog xDialog = this.mDialog;
            if (xDialog != null) {
                xDialog.setNegativeButton(text, new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.appstore.common_ui.-$$Lambda$XpengUIFactory$XpengDialog$FFc_4gCexv6IwDLn2xQj6e_OosA
                    @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                    public final void onClick(XDialog xDialog2, int i) {
                        XpengUIFactory.XpengDialog.this.lambda$setNegativeButton$1$XpengUIFactory$XpengDialog(listener, xDialog2, i);
                    }
                });
            }
        }

        public /* synthetic */ void lambda$setNegativeButton$1$XpengUIFactory$XpengDialog(DialogHelper.DialogButtonClickListener dialogButtonClickListener, XDialog xDialog, int i) {
            if (dialogButtonClickListener != null) {
                dialogButtonClickListener.onClick(this, i);
            }
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.applauncher.DialogHelper.IDialog
        public void setScreenId(int screenId) {
            tryInitDialog();
            XDialog xDialog = this.mDialog;
            if (xDialog != null) {
                xDialog.setScreenId(screenId);
            }
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.applauncher.DialogHelper.IDialog
        public void show() {
            tryInitDialog();
            XDialog xDialog = this.mDialog;
            if (xDialog != null) {
                xDialog.show();
                Logger.t(TAG).i("show, dialog:" + this.mDialog.hashCode(), new Object[0]);
            }
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.applauncher.DialogHelper.IDialog
        public void dismiss() {
            tryInitDialog();
            XDialog xDialog = this.mDialog;
            if (xDialog != null) {
                xDialog.dismiss();
            }
        }

        private void logDialogNull(String methodName) {
            if (this.mDialog == null) {
                Logger.t(TAG).w("call " + methodName + ", XDialog not init", new Object[0]);
            }
        }
    }

    /* loaded from: classes.dex */
    static class XpengToast implements DialogHelper.IToast {
        XpengToast() {
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.applauncher.DialogHelper.IToast
        public void show(String msg) {
            XToast.show(msg);
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.applauncher.DialogHelper.IToast
        public void showAt(String msg, int screenId) {
            XToast.showAt(msg, screenId);
        }
    }
}
