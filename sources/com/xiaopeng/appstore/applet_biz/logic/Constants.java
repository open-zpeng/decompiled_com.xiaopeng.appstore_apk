package com.xiaopeng.appstore.applet_biz.logic;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
/* loaded from: classes2.dex */
public class Constants {
    private static final String[] FAVOR_ARRAY;
    public static final Set<String> FAVOR_LIST;

    static {
        String[] strArr = {"2018040202492080", "2018111662149799", "2019042664330096", "2018031302367666", "2017112700194371", "2017082508366771", "2019090266794548"};
        FAVOR_ARRAY = strArr;
        FAVOR_LIST = new HashSet(Arrays.asList(strArr));
    }
}
