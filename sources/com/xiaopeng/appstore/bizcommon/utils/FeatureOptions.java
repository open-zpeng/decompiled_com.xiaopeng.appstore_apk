package com.xiaopeng.appstore.bizcommon.utils;

import java.util.HashSet;
import java.util.Set;
/* loaded from: classes2.dex */
public class FeatureOptions {
    public static final String FEATURE_SHOW_PRELOAD_APPS = "FEATURE_SHOW_PRELOAD_APPS";
    private static final Set<String> sFeatures = new HashSet();

    public static void addFeature(String featureKey) {
        sFeatures.add(featureKey);
    }

    public static void removeFeature(String featureKey) {
        sFeatures.remove(featureKey);
    }

    public static boolean hasFeature(String featureKey) {
        return sFeatures.contains(featureKey);
    }
}
