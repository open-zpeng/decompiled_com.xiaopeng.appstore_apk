package com.xiaopeng.appstore.bizcommon.logic;

import com.xiaopeng.appstore.bizcommon.logic.silentoperation.util.AlipayEnginePrepareHelper;
import com.xiaopeng.libconfig.ipc.IpcConfig;
import java.util.Arrays;
import java.util.List;
/* loaded from: classes2.dex */
public class Constants {
    public static final int ACTION_TO_HOME = 100;
    public static final int ACTION_TO_ITEMDETAIL = 200;
    public static final int DEFAULT_ACTION = 0;
    public static final int DOWNLOAD_DIRECTLY = 2;
    public static final String INTENT_EXTRA_IGNORE_LAUNCH_ANIMATION = "com.xiaopeng.applist.INGORE_LAUNCH_ANIMATION";
    public static final String LAZY_LOADING_DATA = "lazy_loading_data";
    public static final String NAVIGATOR_TYPE = "navigator_type";
    public static final int OPEN_AND_DOWNLOAD = 1;
    public static final String OPEN_PAGE_SCREEN = "com.xiaopeng.appstore.intent.extra.SCREEN";
    public static final String OPEN_PAGE_WITH_ACTION_ = "action";
    public static final String REMOVE_STACK_LAST = "remove_stack_last";
    public static final String SP_IS_NEW = "sp_is_new_";
    public static final String SP_LAST_INSTALLED_APP_KEY = "sp_last_installed_app_key";
    public static final String SP_LAST_VIEW_ITEM_PACKAGE_NAME = "sp_last_view_item_package_name";
    public static final String SP_UPDATE_CONFIG_TIME = "sp_update_config_time";
    public static final String STORE_FRAGMENT_PARAMS = "store_fragment_params";
    public static final long UPDATE_CONFIG_TIME = 1800000;
    public static final String XP_APP_PREFIX = "com.xiaopeng";
    public static final String[] XP_APP_LIST = {"com.xiaopeng.xsportapp", "com.xiaopeng.musicradio", IpcConfig.App.CAR_BT_PHONE, "com.xiaopeng.chargecontrol", IpcConfig.App.CAR_CAMERA, "com.xiaopeng.carcontrol", "com.xiaopeng.car.settings", "com.xiaopeng.aiassistant", "com.xiaopeng.caraccount", "com.xiaopeng.usermanual", "com.xiaopeng.xpcarlife"};
    public static final String[] XP_INTER_APP_LIST = {"com.xiaopeng.musicradio.spotify", "com.xiaopeng.music.intl", "com.xiaopeng.musicradio", "com.ex.dabplayer.pad", IpcConfig.App.CAR_BT_PHONE, "com.xiaopeng.chargecontrol", IpcConfig.App.CAR_CAMERA, "com.xiaopeng.carcontrol", "com.xiaopeng.car.settings", "com.xiaopeng.caraccount", "com.xiaopeng.carmedia", "com.xiaopeng.aiassistant", "com.spotify.music", "com.amazon.mp3.automotiveOS", "com.aspiro.tidal", "tunein.player", "com.xiaopeng.musicradio.tunein", "com.xiaopeng.dabservice", "com.xiaopeng.btmusicservice", "com.xiaopeng.homespace"};
    public static final String[] XP_INTER_MEDIA_APP_BLACK_LIST = {"com.android.bluetooth"};
    public static final String[] XP_INTER_SECOND_SCREEN_APP_BLACK_LIST = {"com.xiaopeng.carcontrol"};
    public static final String[] XP_APP_BLACK_LIST = {AlipayEnginePrepareHelper.ALIPAY_MINI_PROGRAM_PACKAGE_NAME};
    public static final List<String> CLOSE_PANEL_APP_LIST = Arrays.asList(AlipayEnginePrepareHelper.ALIPAY_MINI_PROGRAM_PACKAGE_NAME);
}
