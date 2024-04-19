package com.xiaopeng.appstore.bizcommon.logic.base;

import android.net.Uri;
import java.io.File;
/* loaded from: classes2.dex */
public abstract class BaseComponentManager {
    public abstract int commitInstall(File file, String id, String iconUrl);

    public abstract void install(Uri fileUri);

    public abstract boolean isInstalled(String id);

    public abstract boolean isInstalling(String id);

    public abstract void uninstall(String id);
}
