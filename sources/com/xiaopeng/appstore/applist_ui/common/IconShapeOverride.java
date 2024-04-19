package com.xiaopeng.appstore.applist_ui.common;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.preference.ListPreference;
import android.preference.Preference;
import android.text.TextUtils;
import android.util.Log;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.xiaopeng.appstore.applist_ui.R;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import com.xiaopeng.appstore.libcommon.utils.SPUtils;
import java.lang.reflect.Field;
/* loaded from: classes2.dex */
public class IconShapeOverride {
    public static final String KEY_PREFERENCE = "pref_override_icon_shape";
    private static final long PROCESS_KILL_DELAY_MS = 1000;
    private static final int RESTART_REQUEST_CODE = 42;
    private static final String TAG = "IconShapeOverride";

    public static boolean isSupported(Context context) {
        if (Build.VERSION.SDK_INT < 26) {
            return false;
        }
        try {
            return getSystemResField().get(null) == Resources.getSystem() && getConfigResId() != 0;
        } catch (Exception unused) {
            return false;
        }
    }

    public static void apply(Context context) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        String appliedValue = getAppliedValue(context);
        if (!TextUtils.isEmpty(appliedValue) && isSupported(context)) {
            try {
                getSystemResField().set(null, new ResourcesOverride(Resources.getSystem(), getConfigResId(), appliedValue));
            } catch (Exception e) {
                Log.e(TAG, "Unable to override icon shape", e);
                SPUtils.getInstance().remove(KEY_PREFERENCE);
            }
        }
    }

    private static Field getSystemResField() throws Exception {
        Field declaredField = Resources.class.getDeclaredField("mSystem");
        declaredField.setAccessible(true);
        return declaredField;
    }

    private static int getConfigResId() {
        return Resources.getSystem().getIdentifier("config_icon_mask", TypedValues.Custom.S_STRING, "android");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getAppliedValue(Context context) {
        return SPUtils.getInstance().getString(KEY_PREFERENCE, context.getResources().getString(R.string.app_config_icon_mask));
    }

    public static void setIconShape(Context context, String newValue) {
        if (getAppliedValue(context).equals(newValue)) {
            return;
        }
        AppExecutors.get().backgroundThread().execute(new OverrideApplyHandler(context, newValue));
    }

    public static void handlePreferenceUi(ListPreference preference) {
        Context context = preference.getContext();
        preference.setValue(getAppliedValue(context));
        preference.setOnPreferenceChangeListener(new PreferenceChangeHandler(context));
    }

    /* loaded from: classes2.dex */
    private static class ResourcesOverride extends Resources {
        private final int mOverrideId;
        private final String mOverrideValue;

        public ResourcesOverride(Resources parent, int overrideId, String overrideValue) {
            super(parent.getAssets(), parent.getDisplayMetrics(), parent.getConfiguration());
            this.mOverrideId = overrideId;
            this.mOverrideValue = overrideValue;
        }

        @Override // android.content.res.Resources
        public String getString(int id) throws Resources.NotFoundException {
            if (id == this.mOverrideId) {
                return this.mOverrideValue;
            }
            return super.getString(id);
        }
    }

    /* loaded from: classes2.dex */
    private static class PreferenceChangeHandler implements Preference.OnPreferenceChangeListener {
        private final Context mContext;

        private PreferenceChangeHandler(Context context) {
            this.mContext = context;
        }

        @Override // android.preference.Preference.OnPreferenceChangeListener
        public boolean onPreferenceChange(Preference preference, Object o) {
            String str = (String) o;
            if (IconShapeOverride.getAppliedValue(this.mContext).equals(str)) {
                return false;
            }
            AppExecutors.get().backgroundThread().execute(new OverrideApplyHandler(this.mContext, str));
            return false;
        }
    }

    /* loaded from: classes2.dex */
    private static class OverrideApplyHandler implements Runnable {
        private final Context mContext;
        private final String mValue;

        private OverrideApplyHandler(Context context, String value) {
            this.mContext = context;
            this.mValue = value;
        }

        @Override // java.lang.Runnable
        public void run() {
            SPUtils.getInstance().put(IconShapeOverride.KEY_PREFERENCE, this.mValue);
        }
    }
}
