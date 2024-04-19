package com.xiaopeng.appstore.xpcommon.eventtracking;
/* loaded from: classes2.dex */
public enum PagesEnum {
    STORE_APP_PANEL("P00001", "应用面板"),
    STORE_APP_LIST("P00002", "应用列表"),
    STORE_STORE_MAIN("P00003", "应用商店首页"),
    STORE_APP_MAIN("P00004", "全局"),
    STORE_APP_DETAIL("P00005", "应用详情页"),
    STORE_MINI_PROGRAM_LIST("P00006", "E28/D21B应用商店-小程序"),
    STORE_INSTALLED_APP_LIST("P10011", "已安装应用列表上传"),
    STORE_MINI_PROGRAM_LOGIN_STATE("P10052", "E28/D21B应用商店-小程序（新）"),
    STORE_MAIN_APP_LIST("P10315", "应用列表"),
    STORE_FORCE_UPDATE_DIALOG("P10311", "强更提示弹窗"),
    STORE_MAIN("P10316", "应用商店首页");
    
    private String mPageId;
    private String mPageName;

    PagesEnum(String pageId, String pageName) {
        this.mPageId = pageId;
        this.mPageName = pageName;
    }

    public String getPageId() {
        return this.mPageId;
    }

    public String getPageName() {
        return this.mPageName;
    }
}
