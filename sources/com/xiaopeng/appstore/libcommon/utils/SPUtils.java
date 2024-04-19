package com.xiaopeng.appstore.libcommon.utils;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
/* loaded from: classes2.dex */
public final class SPUtils {
    private static final String DEFAULT_SP_NAME = "DefaultSpName";
    private static final Map<String, SPUtils> SP_UTILS_MAP = new HashMap();
    private SharedPreferences sp;

    public static SPUtils getInstance() {
        return getInstance(DEFAULT_SP_NAME, 0);
    }

    public static SPUtils getInstance(final int mode) {
        return getInstance(DEFAULT_SP_NAME, mode);
    }

    public static SPUtils getInstance(String spName) {
        return getInstance(spName, 0);
    }

    public static SPUtils getInstance(Context context, String spName) {
        return getInstance(context, spName, 0);
    }

    public static SPUtils getInstance(String spName, final int mode) {
        return getInstance(Utils.getApp(), spName, mode);
    }

    public static SPUtils getInstance(Context context, String spName, final int mode) {
        if (isSpace(spName)) {
            spName = "spUtils";
        }
        Map<String, SPUtils> map = SP_UTILS_MAP;
        SPUtils sPUtils = map.get(spName);
        if (sPUtils == null) {
            synchronized (SPUtils.class) {
                sPUtils = map.get(spName);
                if (sPUtils == null) {
                    sPUtils = new SPUtils(context, spName, mode);
                    map.put(spName, sPUtils);
                }
            }
        }
        return sPUtils;
    }

    private SPUtils(final String spName, final int mode) {
        this(Utils.getApp(), spName, mode);
    }

    private SPUtils(Context context, final String spName, final int mode) {
        this.sp = context.getSharedPreferences(spName, mode);
    }

    public void put(final String key, final String value) {
        put(key, value, false);
    }

    public void put(final String key, final String value, final boolean isCommit) {
        if (isCommit) {
            this.sp.edit().putString(key, value).commit();
        } else {
            this.sp.edit().putString(key, value).apply();
        }
    }

    public String getString(final String key) {
        return getString(key, "");
    }

    public String getString(final String key, final String defaultValue) {
        return this.sp.getString(key, defaultValue);
    }

    public void put(final String key, final int value) {
        put(key, value, false);
    }

    public void put(final String key, final int value, final boolean isCommit) {
        if (isCommit) {
            this.sp.edit().putInt(key, value).commit();
        } else {
            this.sp.edit().putInt(key, value).apply();
        }
    }

    public int getInt(final String key) {
        return getInt(key, -1);
    }

    public int getInt(final String key, final int defaultValue) {
        return this.sp.getInt(key, defaultValue);
    }

    public void put(final String key, final long value) {
        put(key, value, false);
    }

    public void put(final String key, final long value, final boolean isCommit) {
        if (isCommit) {
            this.sp.edit().putLong(key, value).commit();
        } else {
            this.sp.edit().putLong(key, value).apply();
        }
    }

    public long getLong(final String key) {
        return getLong(key, -1L);
    }

    public long getLong(final String key, final long defaultValue) {
        return this.sp.getLong(key, defaultValue);
    }

    public void put(final String key, final float value) {
        put(key, value, false);
    }

    public void put(final String key, final float value, final boolean isCommit) {
        if (isCommit) {
            this.sp.edit().putFloat(key, value).commit();
        } else {
            this.sp.edit().putFloat(key, value).apply();
        }
    }

    public float getFloat(final String key) {
        return getFloat(key, -1.0f);
    }

    public float getFloat(final String key, final float defaultValue) {
        return this.sp.getFloat(key, defaultValue);
    }

    public void put(final String key, final boolean value) {
        put(key, value, false);
    }

    public void put(final String key, final boolean value, final boolean isCommit) {
        if (isCommit) {
            this.sp.edit().putBoolean(key, value).commit();
        } else {
            this.sp.edit().putBoolean(key, value).apply();
        }
    }

    public boolean getBoolean(final String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(final String key, final boolean defaultValue) {
        return this.sp.getBoolean(key, defaultValue);
    }

    public void put(final String key, final Set<String> value) {
        put(key, value, false);
    }

    public void put(final String key, final Set<String> value, final boolean isCommit) {
        if (isCommit) {
            this.sp.edit().putStringSet(key, value).commit();
        } else {
            this.sp.edit().putStringSet(key, value).apply();
        }
    }

    public Set<String> getStringSet(final String key) {
        return getStringSet(key, Collections.emptySet());
    }

    public Set<String> getStringSet(final String key, final Set<String> defaultValue) {
        return this.sp.getStringSet(key, defaultValue);
    }

    public Map<String, ?> getAll() {
        return this.sp.getAll();
    }

    public boolean contains(final String key) {
        return this.sp.contains(key);
    }

    public void remove(final String key) {
        remove(key, false);
    }

    public void remove(final String key, final boolean isCommit) {
        if (isCommit) {
            this.sp.edit().remove(key).commit();
        } else {
            this.sp.edit().remove(key).apply();
        }
    }

    public void clear() {
        clear(false);
    }

    public void clear(final boolean isCommit) {
        if (isCommit) {
            this.sp.edit().clear().commit();
        } else {
            this.sp.edit().clear().apply();
        }
    }

    private static boolean isSpace(final String s) {
        if (s == null) {
            return true;
        }
        int length = s.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
