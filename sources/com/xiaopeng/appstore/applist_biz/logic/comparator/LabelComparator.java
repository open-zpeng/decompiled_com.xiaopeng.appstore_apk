package com.xiaopeng.appstore.applist_biz.logic.comparator;

import java.text.Collator;
import java.util.Comparator;
/* loaded from: classes2.dex */
public class LabelComparator implements Comparator<String> {
    private final Collator mCollator = Collator.getInstance();

    @Override // java.util.Comparator
    public int compare(String titleA, String titleB) {
        boolean z = false;
        boolean z2 = titleA.length() > 0 && Character.isLetterOrDigit(titleA.codePointAt(0));
        if (titleB.length() > 0 && Character.isLetterOrDigit(titleB.codePointAt(0))) {
            z = true;
        }
        if (!z2 || z) {
            if (z2 || !z) {
                return this.mCollator.compare(titleA, titleB);
            }
            return 1;
        }
        return -1;
    }
}
