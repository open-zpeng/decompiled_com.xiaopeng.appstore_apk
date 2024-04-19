package com.xiaopeng.appstore.appstore_ui.bizusb.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.UserHandle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.TextView;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.bizusb.model.LocalAppModel;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.bizusb.INotifyDataChangedCallback;
import com.xiaopeng.appstore.bizcommon.entities.AppInfo;
import com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager;
import com.xiaopeng.appstore.bizcommon.logic.XuiMgrHelper;
import com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager;
import com.xiaopeng.appstore.bizcommon.utils.PackageUtils;
import com.xiaopeng.appstore.common_ui.OpenAppMgr;
import com.xiaopeng.appstore.common_ui.common.base.ViewHolderBase;
import com.xiaopeng.appstore.common_ui.icon.AppIconView;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
import com.xiaopeng.appstore.libcommon.utils.Utils;
import com.xiaopeng.xui.app.XToast;
import java.util.List;
/* loaded from: classes2.dex */
public class LocalAppItemVH extends ViewHolderBase<LocalAppModel> implements AppComponentManager.OnAppInstallCallback, AppComponentManager.OnAppsChangedCallback, AppComponentManager.OnApplyConfigCallback {
    private static final String TAG = "LocalAppItemVH";
    private final AppIconView iconView;
    private boolean isInstalling;
    private LocalAppModel itemData;
    INotifyDataChangedCallback mCallback;
    private final TextView tvAppName;

    @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppInstallCallback
    public void onInstallCreate(int sessionId, String pkgName) {
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppInstallCallback
    public void onInstallProgressChanged(int sessionId, String pkgName, float progress) {
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
    public void onPackageAdded(List<AppInfo> appInfoList) {
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
    public void onPackageChanged(String packageName, UserHandle user) {
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
    public void onPackageUpdated(String packageName) {
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
    public void onPackagesAvailable(String[] packageNames, UserHandle user, boolean replacing) {
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
    public void onPackagesUnavailable(String[] packageNames, UserHandle user, boolean replacing) {
    }

    public LocalAppItemVH(INotifyDataChangedCallback callback, ViewGroup parent) {
        super(parent, R.layout.layout_app_list_appitem);
        this.isInstalling = false;
        this.mCallback = callback;
        this.tvAppName = (TextView) this.itemView.findViewById(R.id.tv_app_name);
        this.iconView = (AppIconView) this.itemView.findViewById(R.id.iv_icon);
    }

    @Override // com.xiaopeng.appstore.common_ui.common.base.ViewHolderBase
    public void setData(LocalAppModel data) {
        Logger.t(TAG).d(data.toString());
        this.itemData = data;
        this.tvAppName.setText(data.getName());
        Bitmap bitmap = this.itemData.getBitmap();
        if (bitmap != null) {
            this.iconView.setIcon(bitmap);
        } else {
            this.iconView.setImageResource(R.drawable.icon_placeholder);
        }
        this.iconView.setActivated(this.itemData.isInstalled());
        if (!this.itemData.isInstalled()) {
            this.tvAppName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_uninstall, 0);
        } else {
            this.tvAppName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        this.iconView.setOnIconTouchListener(new AppIconView.OnIconTouchListener() { // from class: com.xiaopeng.appstore.appstore_ui.bizusb.adapter.LocalAppItemVH.1
            @Override // com.xiaopeng.appstore.common_ui.icon.AppIconView.OnIconTouchListener
            public void onDeleteTap() {
            }

            @Override // com.xiaopeng.appstore.common_ui.icon.AppIconView.OnIconTouchListener
            public void onIconDrag() {
            }

            @Override // com.xiaopeng.appstore.common_ui.icon.AppIconView.OnIconTouchListener
            public void onIconTap() {
                if (LocalAppItemVH.this.itemData.isInstalled()) {
                    Logger.t(LocalAppItemVH.TAG).d("onIconTap, open:" + LocalAppItemVH.this.itemData.getPackageName());
                    OpenAppMgr.get().open(LocalAppItemVH.this.iconView.getContext(), LocalAppItemVH.this.itemData.getPackageName(), LocalAppItemVH.this.itemData.getName());
                } else if (LocalAppItemVH.this.isInstalling) {
                    Logger.t(LocalAppItemVH.TAG).d("onIconTap, installing:" + LocalAppItemVH.this.itemData.getPackageName());
                } else {
                    Logger.t(LocalAppItemVH.TAG).d("onIconTap, start installing:" + LocalAppItemVH.this.itemData.getPackageName());
                    LocalAppItemVH.this.isInstalling = true;
                    ((TextView) LocalAppItemVH.this.itemView.findViewById(R.id.tv_app_name)).setText("正在安裝...");
                    if (TextUtils.isEmpty(LocalAppItemVH.this.itemData.getConfigUrl())) {
                        PackageUtils.installAsync(Utils.getApp(), LocalAppItemVH.this.itemData.getFile(), LocalAppItemVH.this.itemData.getPackageName(), null);
                    } else {
                        AppStoreAssembleManager.get().startLocalWithConfig(LocalAppItemVH.this.itemData.getConfigUrl(), LocalAppItemVH.this.itemData.getConfigMd5(), LocalAppItemVH.this.itemData.getPackageName(), LocalAppItemVH.this.itemData.getApkMd5(), LocalAppItemVH.this.itemData.getFile(), LocalAppItemVH.this);
                    }
                }
            }

            @Override // com.xiaopeng.appstore.common_ui.icon.AppIconView.OnIconTouchListener
            public boolean onIconLongClick() {
                if (LocalAppItemVH.this.itemView.getContext() instanceof Activity) {
                    XToast.show(ResUtils.getString(R.string.list_edit_not_support));
                    return true;
                }
                return true;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.appstore.common_ui.common.base.ViewHolderBase
    public void onDetach() {
        super.onDetach();
        AppComponentManager.get().removeOnAppsChangedSoftCallback(this);
        AppComponentManager.get().removeOnAppInstallSoftCallback(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.appstore.common_ui.common.base.ViewHolderBase
    public void onAttach() {
        super.onAttach();
        AppComponentManager.get().addOnAppsChangedSoftCallback(this);
        AppComponentManager.get().addOnAppInstallSoftCallback(this);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppInstallCallback
    public void onInstallFinished(int sessionId, String pkgName, boolean success) {
        Logger.t(TAG).v("onInstallFinished  packageName :" + pkgName + " success=" + success, new Object[0]);
        if (this.itemData.getPackageName().equals(pkgName)) {
            this.itemData.setInstalled(success);
            this.isInstalling = false;
            this.mCallback.dataChanged(getAdapterPosition());
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnAppsChangedCallback
    public void onPackageRemoved(final String packageName, UserHandle user) {
        Logger.t(TAG).v("onPackageRemoved  packageName :" + packageName, new Object[0]);
        AppExecutors.get().mainThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.appstore_ui.bizusb.adapter.-$$Lambda$LocalAppItemVH$t9IR8QLseCzCsH7XbHIb-N1S9Dg
            @Override // java.lang.Runnable
            public final void run() {
                LocalAppItemVH.this.lambda$onPackageRemoved$0$LocalAppItemVH(packageName);
            }
        });
    }

    public /* synthetic */ void lambda$onPackageRemoved$0$LocalAppItemVH(String str) {
        if (this.itemData.getPackageName().equals(str)) {
            this.itemData.setInstalled(false);
            this.itemView.setEnabled(true);
            this.mCallback.dataChanged(getAdapterPosition());
        }
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager.OnApplyConfigCallback
    public void applyConfigError(final String packageName) {
        Logger.t(TAG).v("applyConfigError  packageName :" + packageName, new Object[0]);
        AppExecutors.get().mainThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.appstore_ui.bizusb.adapter.-$$Lambda$LocalAppItemVH$Rn0w5GeQxdmC7roWZzAadJ_LthU
            @Override // java.lang.Runnable
            public final void run() {
                LocalAppItemVH.this.lambda$applyConfigError$1$LocalAppItemVH(packageName);
            }
        });
    }

    public /* synthetic */ void lambda$applyConfigError$1$LocalAppItemVH(String str) {
        if (this.itemData.getPackageName().equals(str)) {
            XToast.show("安装" + this.itemData.getName() + "应用失败，请重试", 0, XuiMgrHelper.get().getShareId());
            this.itemView.setEnabled(true);
            this.isInstalling = false;
            this.mCallback.dataChanged(getAdapterPosition());
        }
    }
}
