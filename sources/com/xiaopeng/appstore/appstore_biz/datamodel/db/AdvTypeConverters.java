package com.xiaopeng.appstore.appstore_biz.datamodel.db;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.DependentAppBean;
import java.util.Arrays;
import java.util.List;
/* loaded from: classes2.dex */
public class AdvTypeConverters {
    private static final String sSplitChar = ",";

    public static String listToString(List<String> stringList) {
        return stringList == null ? "" : TextUtils.join(sSplitChar, stringList);
    }

    public static List<String> stringToList(String string) {
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        return Arrays.asList(TextUtils.split(string, sSplitChar));
    }

    public static List<DependentAppBean> jsonToDependentAppList(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }
        return (List) new Gson().fromJson(jsonString, new TypeToken<List<DependentAppBean>>() { // from class: com.xiaopeng.appstore.appstore_biz.datamodel.db.AdvTypeConverters.1
        }.getType());
    }

    public static String dependentAppListToString(List<DependentAppBean> list) {
        if (list == null) {
            return null;
        }
        return new Gson().toJson(list, new TypeToken<List<DependentAppBean>>() { // from class: com.xiaopeng.appstore.appstore_biz.datamodel.db.AdvTypeConverters.2
        }.getType());
    }
}
