package com.xiaopeng.privacymanager;

import android.content.Context;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;
import com.google.gson.reflect.TypeToken;
import com.xiaopeng.lib.apirouter.ApiRouter;
import com.xiaopeng.lib.apirouter.server.IServicePublisher;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes3.dex */
public class PrivacySettings implements IServicePublisher {
    public static final String AUTHORITY = "com.xiaopeng.privacyservice.PrivacySettingsServer";
    public static final String PROTOCOL_URI = "content://com.xiaopeng.privacyservice.PrivacySettingsServer/protocol";
    private static final String TAG = "[PrivacySettings] ";

    public static Uri getProtocolUri() {
        return Uri.parse(PROTOCOL_URI);
    }

    public static void setAgreed(Context context, int i, boolean z) {
        String packageName = context.getPackageName();
        int[] subTypes = ProtocolType.getSubTypes(i);
        if (subTypes != null) {
            try {
                for (int i2 : subTypes) {
                    Uri build = new Uri.Builder().authority(AUTHORITY).path("setAgreed").appendQueryParameter(VuiConstants.ELEMENT_TYPE, String.valueOf(i2)).appendQueryParameter("agreed", String.valueOf(z)).build();
                    try {
                        Log.i(TAG, "Calling package: " + packageName + ", uri:" + build);
                        ApiRouter.route(build);
                    } catch (RemoteException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            } catch (Exception e2) {
                Log.e(TAG, e2.getMessage());
                Log.e(TAG, "maybe no PrivacyService apk in ROM");
            }
        }
    }

    public static boolean isAgreed(Context context, int i) {
        String packageName = context.getPackageName();
        int[] subTypes = ProtocolType.getSubTypes(i);
        if (subTypes == null || subTypes.length <= 0) {
            return false;
        }
        boolean z = true;
        for (int i2 : subTypes) {
            Uri build = new Uri.Builder().authority(AUTHORITY).path("isAgreed").appendQueryParameter(VuiConstants.ELEMENT_TYPE, String.valueOf(i2)).build();
            try {
                boolean booleanValue = ((Boolean) ApiRouter.route(build)).booleanValue();
                Log.i(TAG, "Calling package: " + packageName + ", uri:" + build + ", isTypeAgreed:" + booleanValue);
                z &= booleanValue;
            } catch (RemoteException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        return z;
    }

    public static void setEnabled(Context context, int i, boolean z) {
        String packageName = context.getPackageName();
        int[] subTypes = ProtocolType.getSubTypes(i);
        if (subTypes != null) {
            try {
                for (int i2 : subTypes) {
                    Uri build = new Uri.Builder().authority(AUTHORITY).path("setEnabled").appendQueryParameter(VuiConstants.ELEMENT_TYPE, String.valueOf(i2)).appendQueryParameter(VuiConstants.ELEMENT_ENABLED, String.valueOf(z)).build();
                    try {
                        Log.i(TAG, "Calling package: " + packageName + ", uri:" + build);
                        ApiRouter.route(build);
                    } catch (RemoteException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            } catch (Exception e2) {
                Log.e(TAG, e2.getMessage());
                Log.e(TAG, "maybe no PrivacyService apk in ROM");
            }
        }
    }

    public static boolean isEnabled(Context context, int i) {
        String packageName = context.getPackageName();
        int[] subTypes = ProtocolType.getSubTypes(i);
        if (subTypes == null || subTypes.length <= 0) {
            return false;
        }
        boolean z = true;
        for (int i2 : subTypes) {
            Uri build = new Uri.Builder().authority(AUTHORITY).path("isEnabled").appendQueryParameter(VuiConstants.ELEMENT_TYPE, String.valueOf(i2)).build();
            try {
                boolean booleanValue = ((Boolean) ApiRouter.route(build)).booleanValue();
                Log.i(TAG, "Calling package: " + packageName + ", uri:" + build + ", isTypeEnabled:" + booleanValue);
                z &= booleanValue;
            } catch (RemoteException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        return z;
    }

    public static boolean isNeedReconfirm(Context context, int i) {
        String packageName = context.getPackageName();
        int[] subTypes = ProtocolType.getSubTypes(i);
        if (subTypes == null || subTypes.length <= 0) {
            return false;
        }
        boolean z = true;
        for (int i2 : subTypes) {
            Uri build = new Uri.Builder().authority(AUTHORITY).path("isNeedReconfirm").appendQueryParameter(VuiConstants.ELEMENT_TYPE, String.valueOf(i2)).build();
            try {
                boolean booleanValue = ((Boolean) ApiRouter.route(build)).booleanValue();
                Log.i(TAG, "Calling package: " + packageName + ", uri:" + build + ", isTypeNeedReconfirm:" + booleanValue);
                z &= booleanValue;
            } catch (RemoteException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        return z;
    }

    public static void setNeedReconfirm(Context context, int i, boolean z) {
        String packageName = context.getPackageName();
        int[] subTypes = ProtocolType.getSubTypes(i);
        if (subTypes != null) {
            try {
                for (int i2 : subTypes) {
                    Uri build = new Uri.Builder().authority(AUTHORITY).path("setNeedReconfirm").appendQueryParameter(VuiConstants.ELEMENT_TYPE, String.valueOf(i2)).appendQueryParameter("needReconfirm", String.valueOf(z)).build();
                    try {
                        Log.i(TAG, "Calling package: " + packageName + ", uri:" + build);
                        ApiRouter.route(build);
                    } catch (RemoteException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            } catch (Exception e2) {
                Log.e(TAG, e2.getMessage());
                Log.e(TAG, "maybe no PrivacyService apk in ROM");
            }
        }
    }

    public static boolean hasUnsignedPrivacy(Context context) {
        List<Integer> unsignedPrivacy = getUnsignedPrivacy(context);
        return unsignedPrivacy != null && unsignedPrivacy.size() > 0;
    }

    public static boolean hasUnsignedPrivacy(Context context, int i) {
        List<Integer> unsignedPrivacy = getUnsignedPrivacy(context);
        if (unsignedPrivacy == null) {
            return false;
        }
        return unsignedPrivacy.contains(Integer.valueOf(i));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static List<Integer> getUnsignedPrivacy(Context context) {
        String packageName = context.getPackageName();
        List arrayList = new ArrayList();
        Uri build = new Uri.Builder().authority(AUTHORITY).path("getUnsignedPrivacy").build();
        try {
            List list = (List) GsonUtil.fromJson((String) ApiRouter.route(build), new TypeToken<List<Integer>>() { // from class: com.xiaopeng.privacymanager.PrivacySettings.1
            });
            try {
                Log.i(TAG, "Calling package: " + packageName + ", uri:" + build + ", list:" + list);
                return list;
            } catch (RemoteException e) {
                e = e;
                arrayList = list;
                Log.e(TAG, e.getMessage());
                return arrayList;
            }
        } catch (RemoteException e2) {
            e = e2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static List<Integer> getUnsignedPrivacyForPowerOn(Context context) {
        String packageName = context.getPackageName();
        List arrayList = new ArrayList();
        Uri build = new Uri.Builder().authority(AUTHORITY).path("getUnsignedPrivacyForPowerOn").build();
        try {
            List list = (List) GsonUtil.fromJson((String) ApiRouter.route(build), new TypeToken<List<Integer>>() { // from class: com.xiaopeng.privacymanager.PrivacySettings.2
            });
            try {
                Log.i(TAG, "Calling package: " + packageName + ", uri:" + build + ", list:" + list);
                return list;
            } catch (RemoteException e) {
                e = e;
                arrayList = list;
                Log.e(TAG, e.getMessage());
                return arrayList;
            }
        } catch (RemoteException e2) {
            e = e2;
        }
    }
}
