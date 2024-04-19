package com.xiaopeng.appstore.installer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.orhanobut.logger.Logger;
/* loaded from: classes.dex */
public class XpInstallForbiddenActivity extends AppCompatActivity {
    public static final String EXTRA_CALLING_PACKAGE = "EXTRA_CALLING_PACKAGE";
    private static final String TAG = "XpInstallForbiddenActivity";

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int intExtra = intent.getIntExtra(Constants.EXTRA_ORIGINATING_UID, -1);
        Uri data = intent.getData();
        Logger.t(TAG).i("XpInstallForbiddenActivity, uri:" + data + ", originCalling:" + intExtra, new Object[0]);
        StartInstallService.startActionInstall(this, data, intExtra);
        finish();
    }
}
