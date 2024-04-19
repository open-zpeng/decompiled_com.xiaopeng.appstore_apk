package com.xiaopeng.appstore.bizcommon.entities;

import android.os.Process;
import android.os.UserHandle;
/* loaded from: classes2.dex */
public class ItemInfo {
    public CharSequence contentDescription;
    public CharSequence title;
    public UserHandle user = Process.myUserHandle();
}
