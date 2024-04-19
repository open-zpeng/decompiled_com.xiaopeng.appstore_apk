package com.xiaopeng.appstore.installer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.R;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: InstallDialogActivity.kt */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0002J\u0012\u0010\u0007\u001a\u00020\u00042\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0014J\b\u0010\n\u001a\u00020\u0004H\u0014J\u0012\u0010\u000b\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0002J\b\u0010\f\u001a\u00020\u0004H\u0002J\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0012\u0010\u000e\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0002J\u0012\u0010\u000f\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0002J\u0018\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002¨\u0006\u0016"}, d2 = {"Lcom/xiaopeng/appstore/installer/InstallDialogActivity;", "Lcom/xiaopeng/appstore/installer/DialogActivityAbs;", "()V", "installForbidden", "", "pkgInfo", "Landroid/content/pm/PackageInfo;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "refreshIcon", "showError", "showGoAppStore", "showNetError", "showUpgradeForbidden", "startAppStoreDetail", "context", "Landroid/content/Context;", "pkgName", "", "Companion", "xpAppStore-V1.6.0_20230915224106_Release_D55V1Release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class InstallDialogActivity extends DialogActivityAbs {
    public static final Companion Companion = new Companion(null);
    public static final String EXTRA_RESULT = "extra_result";
    public static final String TAG = "InstallDialogActivity";

    /* compiled from: InstallDialogActivity.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lcom/xiaopeng/appstore/installer/InstallDialogActivity$Companion;", "", "()V", "EXTRA_RESULT", "", "TAG", "xpAppStore-V1.6.0_20230915224106_Release_D55V1Release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0049, code lost:
        if ((r0 != null && r0.retCode == 0) == false) goto L12;
     */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onCreate(android.os.Bundle r7) {
        /*
            Method dump skipped, instructions count: 229
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.appstore.installer.InstallDialogActivity.onCreate(android.os.Bundle):void");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        Logger.t(TAG).i("onDestroy " + hashCode(), new Object[0]);
    }

    private final void showNetError(PackageInfo packageInfo) {
        refreshIcon(packageInfo);
        this.mDialogTitle.setText(R.string.update_forbidden_title);
        this.mDialogMsg.setText(R.string.update_forbidden_msg2);
        this.mDialogBtn1.setText(R.string.btn_go_retry);
        this.mDialogBtn1.setVisibility(0);
        this.mDialogBtn1.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.appstore.installer.-$$Lambda$InstallDialogActivity$CQSi8Z-EVfui0Pqwp4Udgf13XnU
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                InstallDialogActivity.showNetError$lambda$0(InstallDialogActivity.this, view);
            }
        });
        this.mDialogBtn2.setText(R.string.btn_dismiss);
        this.mDialogBtn2.setVisibility(0);
        this.mDialogBtn2.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.appstore.installer.-$$Lambda$InstallDialogActivity$aKUZFRjoaiRma_fpEdY5_5mapfQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                InstallDialogActivity.showNetError$lambda$1(InstallDialogActivity.this, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showNetError$lambda$0(InstallDialogActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        int intExtra = this$0.getIntent().getIntExtra(Constants.EXTRA_ORIGINATING_UID, -1);
        Uri data = this$0.getIntent().getData();
        if (data != null) {
            StartInstallService.startActionInstall(this$0, data, intExtra);
        } else {
            Logger.t(TAG).w("showNetErrorDialog, click, uri is null", new Object[0]);
        }
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showNetError$lambda$1(InstallDialogActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    private final void showUpgradeForbidden(PackageInfo packageInfo) {
        Logger.t(TAG).d("showUpgradeForbidden", new Object[0]);
        refreshIcon(packageInfo);
        this.mDialogTitle.setText(R.string.update_forbidden_title);
        this.mDialogMsg.setText(R.string.update_forbidden_msg);
        this.mDialogBtn1.setText(R.string.btn_dismiss);
        this.mDialogBtn1.setVisibility(0);
        this.mDialogBtn1.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.appstore.installer.-$$Lambda$InstallDialogActivity$oFKlI_UWV2GbmyVYqgU_EVJyH4w
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                InstallDialogActivity.showUpgradeForbidden$lambda$2(InstallDialogActivity.this, view);
            }
        });
        this.mDialogBtn2.setText("");
        this.mDialogBtn2.setVisibility(8);
        this.mDialogBtn2.setOnClickListener(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showUpgradeForbidden$lambda$2(InstallDialogActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    private final void showError() {
        Logger.t(TAG).d("showError", new Object[0]);
        refreshIcon(null);
        this.mDialogTitle.setText(R.string.installer_install_failed);
        this.mDialogMsg.setText(R.string.Parse_error_dlg_text);
        this.mDialogBtn1.setText(R.string.btn_dismiss);
        this.mDialogBtn1.setVisibility(0);
        this.mDialogBtn1.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.appstore.installer.-$$Lambda$InstallDialogActivity$X3-T8ByrcaEx995O5KwGSL3QM4o
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                InstallDialogActivity.showError$lambda$3(InstallDialogActivity.this, view);
            }
        });
        this.mDialogBtn2.setText("");
        this.mDialogBtn2.setVisibility(8);
        this.mDialogBtn2.setOnClickListener(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showError$lambda$3(InstallDialogActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    private final void installForbidden(PackageInfo packageInfo) {
        refreshIcon(packageInfo);
        this.mDialogTitle.setText(R.string.installer_install_failed);
        this.mDialogMsg.setText(R.string.unknown_apps_admin_dlg_text);
        this.mDialogBtn1.setText(R.string.btn_dismiss);
        this.mDialogBtn1.setVisibility(0);
        this.mDialogBtn1.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.appstore.installer.-$$Lambda$InstallDialogActivity$WY031DViQrxZrCl1OOoF88km0U4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                InstallDialogActivity.installForbidden$lambda$4(InstallDialogActivity.this, view);
            }
        });
        this.mDialogBtn2.setText("");
        this.mDialogBtn2.setVisibility(8);
        this.mDialogBtn2.setOnClickListener(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void installForbidden$lambda$4(InstallDialogActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    private final void showGoAppStore(final PackageInfo packageInfo) {
        refreshIcon(packageInfo);
        this.mDialogTitle.setText(R.string.update_forbidden_title);
        this.mDialogMsg.setText(R.string.update_forbidden_msg1);
        this.mDialogBtn1.setText(R.string.btn_go_update);
        this.mDialogBtn1.setVisibility(0);
        this.mDialogBtn1.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.appstore.installer.-$$Lambda$InstallDialogActivity$YF5wglC__Rl6tKQDccR-qRt7-2E
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                InstallDialogActivity.showGoAppStore$lambda$5(InstallDialogActivity.this, packageInfo, view);
            }
        });
        this.mDialogBtn2.setText(R.string.btn_dismiss);
        this.mDialogBtn2.setVisibility(0);
        this.mDialogBtn2.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.appstore.installer.-$$Lambda$InstallDialogActivity$L0QKoLCzzfQy4Czg_Mv8mZjRfXs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                InstallDialogActivity.showGoAppStore$lambda$6(InstallDialogActivity.this, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showGoAppStore$lambda$5(InstallDialogActivity this$0, PackageInfo pkgInfo, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(pkgInfo, "$pkgInfo");
        String str = pkgInfo.packageName;
        Intrinsics.checkNotNullExpressionValue(str, "pkgInfo.packageName");
        this$0.startAppStoreDetail(this$0, str);
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showGoAppStore$lambda$6(InstallDialogActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    private final void refreshIcon(PackageInfo packageInfo) {
        if (this.mIconView == null) {
            return;
        }
        if (packageInfo != null) {
            Drawable loadIcon = packageInfo.applicationInfo.loadIcon(getPackageManager());
            this.mIconView.setVisibility(0);
            this.mIconView.setImageDrawable(loadIcon);
            return;
        }
        this.mIconView.setVisibility(8);
    }

    private final void startAppStoreDetail(Context context, String str) {
        Intent intent = new Intent();
        intent.setData(Uri.parse(com.xiaopeng.appstore.common_ui.Constants.DETAILS_URL_PREFIX + str));
        intent.setPackage("com.xiaopeng.appstore");
        if (!(context instanceof Activity)) {
            intent.addFlags(268435456);
        }
        context.startActivity(intent);
    }
}
