package com.xiaopeng.appstore.xpcommon.eventtracking;

import android.car.Car;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.xpcommon.CarUtils;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventBean;
import com.xiaopeng.datalog.DataLogModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IDataLog;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IMoleEvent;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IMoleEventBuilder;
import java.util.HashMap;
import java.util.Map;
/* loaded from: classes2.dex */
public class EventTrackingHelper {
    public static final int APP_EVENT_TYPE_CANCEL_DOWNLOAD_OR_UPGRADE = 4;
    public static final int APP_EVENT_TYPE_CONTINUE_DOWNLOAD_OR_UPGRADE = 6;
    public static final int APP_EVENT_TYPE_DOWNLOAD = 1;
    public static final int APP_EVENT_TYPE_OPEN = 3;
    public static final int APP_EVENT_TYPE_PAUSE_DOWNLOAD_OR_UPGRADE = 5;
    public static final int APP_EVENT_TYPE_UNINSTALL = 7;
    public static final int APP_EVENT_TYPE_UNKNOWN = -1;
    public static final int APP_EVENT_TYPE_UPGRADE = 2;
    public static final String EVENT_SOURCE_FOR_MINI_PROGRAM = "小程序";
    public static final int STORE_TAB_APP_LIST_FRAGMENT = 1;
    public static final int STORE_TAB_APP_STORE_FRAGMENT = 2;
    public static final int STORE_TAB_MINI_PROGRAM_FRAGMENT = 3;
    private static final String TAG = "EventTrackingHelper";
    private static final String TRACKING_MODULE_NAME = "appstore";

    public static void sendMolecast(PagesEnum page, EventEnum btn, Object... args) {
        HashMap hashMap = new HashMap();
        EventTrackingParamKeys[] paramsIndex = btn.getParamsIndex();
        if (paramsIndex != null && args != null) {
            if (paramsIndex.length != args.length) {
                Logger.t(TAG).i(" params key and value number not match!", new Object[0]);
                return;
            }
            for (int i = 0; i < args.length; i++) {
                Object obj = args[i];
                if (!(obj instanceof Boolean) && !(obj instanceof Character) && !(obj instanceof Number) && !(obj instanceof String)) {
                    Logger.t(TAG).i("args type must be Boolean, Character, Number, String", new Object[0]);
                    return;
                }
                hashMap.put(paramsIndex[i].getParamKey(), obj);
            }
        }
        EventBean.Builder buttonId = new EventBean.Builder().pageId(page.getPageId()).pageName(page.getPageName()).buttonId(btn.getEventId());
        if (hashMap.size() > 0) {
            buttonId.setParams(hashMap);
        }
        sendMolecast(buttonId.build());
    }

    private static IDataLog getDataLog() {
        return (IDataLog) Module.get(DataLogModuleEntry.class).get(IDataLog.class);
    }

    private static void sendMolecast(EventBean data) {
        try {
            if (Car.isExportVersion()) {
                if (!CarUtils.isE28()) {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Logger.t(TAG).i("send molecast event bean  = " + data.toString(), new Object[0]);
        IDataLog dataLog = getDataLog();
        IMoleEventBuilder buttonId = dataLog.buildMoleEvent().setModule("appstore").setPageId(data.getPageId()).setButtonId(data.getButtonId());
        if (data.getParams() != null) {
            for (Map.Entry<String, Object> entry : data.getParams().entrySet()) {
                if (entry.getValue() instanceof String) {
                    buttonId.setProperty(entry.getKey(), (String) entry.getValue());
                } else if (entry.getValue() instanceof Number) {
                    buttonId.setProperty(entry.getKey(), (Number) entry.getValue());
                } else if (entry.getValue() instanceof Character) {
                    buttonId.setProperty(entry.getKey(), ((Character) entry.getValue()).charValue());
                } else if (entry.getValue() instanceof Boolean) {
                    buttonId.setProperty(entry.getKey(), ((Boolean) entry.getValue()).booleanValue());
                } else {
                    Logger.t(TAG).i("params type invalid", new Object[0]);
                }
            }
        }
        buttonId.setProperty("level", Integer.valueOf(data.getLevel()));
        if (dataLog != null) {
            IMoleEvent build = buttonId.build();
            Logger.t(TAG).i("send molecast mole event = " + build.toJson(), new Object[0]);
            dataLog.sendStatData(build);
        }
    }
}
