package com.xiaopeng.appstore.xpcommon.eventtracking;
/* loaded from: classes2.dex */
public enum EventEnum {
    OPEN_APP_FROM_DROPDOWN_ENTRANCE("B002", "三方应用入口-下拉菜单", new EventTrackingParamKeys[]{EventTrackingParamKeys.KEY_TYPE, EventTrackingParamKeys.KEY_RESULT, EventTrackingParamKeys.KEY_NAME}),
    OPEN_APP_FROM_STORE_DETAIL_ENTRANCE("B001", "三方应用入口-各应用详情页", new EventTrackingParamKeys[]{EventTrackingParamKeys.KEY_TYPE, EventTrackingParamKeys.KEY_RESULT, EventTrackingParamKeys.KEY_NAME}),
    OPEN_APP_FROM_STORE_MAIN_ENTRANCE("B004", "三方应用入口-商店首页", new EventTrackingParamKeys[]{EventTrackingParamKeys.KEY_TYPE, EventTrackingParamKeys.KEY_RESULT, EventTrackingParamKeys.KEY_NAME}),
    OPEN_APP_FROM_APP_LIST("B002", "三方应用入口-应用列表", new EventTrackingParamKeys[]{EventTrackingParamKeys.KEY_TYPE, EventTrackingParamKeys.KEY_RESULT, EventTrackingParamKeys.KEY_NAME}),
    OPEN_XP_APP_ENTRANCE("B001", "小鹏自研应用入口", new EventTrackingParamKeys[]{EventTrackingParamKeys.KEY_RESULT, EventTrackingParamKeys.KEY_NAME}),
    STORE_APP_SELF_UPGRADE_FAILED("B001", "三方应用自安装失败次数", new EventTrackingParamKeys[]{EventTrackingParamKeys.KEY_INSTALLING_APP_NAME, EventTrackingParamKeys.KEY_INSTALLING_PACKAGE_NAME, EventTrackingParamKeys.KEY_INITIATE_APP_NAME, EventTrackingParamKeys.KEY_INITIATE_PACKAGE_NAME}),
    STORE_APP_DETAIL_ENTRANCE("B003", "第三方应用详情页入口", new EventTrackingParamKeys[]{EventTrackingParamKeys.KEY_RESULT, EventTrackingParamKeys.KEY_NAME}),
    STORE_APP_PENDING_UPGRADE("B002", "待更新应用入口"),
    STORE_BANNER("B001", "banner页面", new EventTrackingParamKeys[]{EventTrackingParamKeys.KEY_RESULT}),
    MAIN_ENTRANCE("B001", "页面入口", new EventTrackingParamKeys[]{EventTrackingParamKeys.KEY_RESULT, EventTrackingParamKeys.KEY_COUNT}),
    OPEN_MINI_PROGRAM("B002", "各支付宝小程序开启入口", new EventTrackingParamKeys[]{EventTrackingParamKeys.KEY_RESULT, EventTrackingParamKeys.KEY_NAME, EventTrackingParamKeys.KEY_SOURCE}),
    LOGOUT_ALIPAY_ACCOUNT("B001", "支付宝账号退出登录", new EventTrackingParamKeys[]{EventTrackingParamKeys.KEY_SOURCE}),
    INSTALLED_APP_LIST("B001", "已安装应用上传埋点", new EventTrackingParamKeys[]{EventTrackingParamKeys.KEY_INSTALL_APP_LIST}),
    MINI_PROGRAM_LOGIN_STATE("B001", "支付宝登录状态", new EventTrackingParamKeys[]{EventTrackingParamKeys.KEY_RESULT}),
    APP_LIST_EDIT("B001", "应用编辑", new EventTrackingParamKeys[]{EventTrackingParamKeys.KEY_RESULT, EventTrackingParamKeys.KEY_START_TIME, EventTrackingParamKeys.KEY_END_TIME}),
    THIRD_PRIVACY_AGREEMENT("B001", "第三方应用隐私政策入口", new EventTrackingParamKeys[]{EventTrackingParamKeys.KEY_RESULT, EventTrackingParamKeys.KEY_NAME}),
    THIRD_APP_PERMISSION("B002", "第三方应用应用权限入口", new EventTrackingParamKeys[]{EventTrackingParamKeys.KEY_RESULT, EventTrackingParamKeys.KEY_NAME}),
    USER_AGREEMENT("B003", "用户协议入口", new EventTrackingParamKeys[]{EventTrackingParamKeys.KEY_SOURCE}),
    FORCE_UPDATE_DIALOG("B001", "强更提示弹窗", new EventTrackingParamKeys[]{EventTrackingParamKeys.KEY_TYPE, EventTrackingParamKeys.KEY_RESULT, EventTrackingParamKeys.KEY_NAME});
    
    private String mEventId;
    private String mEventName;
    private int mLevel;
    private EventTrackingParamKeys[] mParamsKeys;

    EventEnum(String eventId, String eventName, int level) {
        this.mEventId = eventId;
        this.mEventName = eventName;
        this.mLevel = level;
        this.mParamsKeys = null;
    }

    EventEnum(String eventId, String eventName, EventTrackingParamKeys[] paramsIndex) {
        this.mEventId = eventId;
        this.mEventName = eventName;
        this.mLevel = 0;
        this.mParamsKeys = paramsIndex;
    }

    EventEnum(String eventId, String eventName) {
        this.mEventId = eventId;
        this.mEventName = eventName;
        this.mLevel = 0;
        this.mParamsKeys = null;
    }

    EventEnum(String eventId, String eventName, int level, EventTrackingParamKeys[] paramsIndex) {
        this.mEventId = eventId;
        this.mEventName = eventName;
        this.mLevel = level;
        this.mParamsKeys = paramsIndex;
    }

    public String getEventId() {
        return this.mEventId;
    }

    public int getLevel() {
        return this.mLevel;
    }

    public EventTrackingParamKeys[] getParamsIndex() {
        return this.mParamsKeys;
    }
}
