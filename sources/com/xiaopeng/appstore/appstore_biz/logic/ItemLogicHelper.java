package com.xiaopeng.appstore.appstore_biz.logic;

import com.xiaopeng.appstore.appstore_biz.R;
import com.xiaopeng.appstore.bizcommon.utils.LogicUtils;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
/* loaded from: classes2.dex */
public class ItemLogicHelper {
    public static final String DOWNLOADING = "";
    public static final String INSTALLING = "";
    private static final String TAG = "ItemLogicHelper";
    public static final int UI_STATE_LOADING = 1;
    public static final int UI_STATE_NORMAL_BTN = 3;
    public static final int UI_STATE_PROGRESSING = 2;
    public static final String OPEN = ResUtils.getString(R.string.btn_open_app);
    public static final String DOWNLOAD = ResUtils.getString(R.string.btn_download_app);
    public static final String UPGRADE = ResUtils.getString(R.string.btn_upgrade_app);
    public static final String RESUME = ResUtils.getString(R.string.btn_resume_app);
    public static final String CANCEL = ResUtils.getString(R.string.btn_cancel);
    public static final String PAUSE = ResUtils.getString(R.string.btn_pause_app);
    public static final String DELETING = ResUtils.getString(R.string.btn_deleting_app);
    public static final String PENDING = ResUtils.getString(R.string.btn_pending_app);
    public static final String ERROR = ResUtils.getString(R.string.btn_download_app_error);

    public static boolean detailCancelEnable(int state) {
        return (state == 3 || state == 10000 || state == 8) ? false : true;
    }

    public static boolean detailShowCancel(int state) {
        return state == 1 || state == 2 || state == 3 || state == 5 || state == 10001;
    }

    public static boolean isAssembling(int state) {
        return state == 1 || state == 3 || state == 5;
    }

    public static boolean isBtnEnable(int state) {
        return (state == 3 || state == 10000 || state == 8 || state == 10001) ? false : true;
    }

    public static boolean isIndeterminate(int progress) {
        return progress <= 0;
    }

    public static boolean isIndeterminate(int progress, int state) {
        return progress <= 0 ? (state == 2 || state == 0 || state == 6 || state == 4) ? false : true : state == 5 || state == 8 || state == 10001;
    }

    public static boolean isLoadingInDetailPage(int progress, int state) {
        return progress <= 0 ? (state == 2 || state == 0 || state == 6 || state == 4 || state == 10001 || state == 101) ? false : true : state == 5 || state == 8;
    }

    public static boolean isPendingBtnEnable(int state) {
        return (state == 3 || state == 10000 || state == 8) ? false : true;
    }

    public static boolean isProgressEnable(int state) {
        return (state == 3 || state == 10000 || state == 8) ? false : true;
    }

    public static boolean showProgress(int state) {
        return state == 1 || state == 2 || state == 3 || state == 5 || state == 10001;
    }

    public static String getExecuteDesc(int state) {
        if (state != 0) {
            if (state == 1) {
                return PAUSE;
            }
            if (state == 2) {
                return RESUME;
            }
            if (state == 3) {
                return "";
            }
            if (state == 4) {
                return OPEN;
            }
            if (state == 6) {
                return UPGRADE;
            }
            if (state != 7) {
                if (state == 8) {
                    return DELETING;
                }
                if (state == 101) {
                    return ERROR;
                }
                if (state != 10000) {
                    return state != 10001 ? "" : PENDING;
                }
            }
        }
        return DOWNLOAD;
    }

    public static String getStartOrCancelDesc(int state) {
        if (state == 1 || state == 2 || state == 3 || state == 5) {
            return CANCEL;
        }
        return common(state);
    }

    private static String common(int state) {
        if (state != 0) {
            if (state == 4) {
                return OPEN;
            }
            if (state != 101) {
                if (state == 6) {
                    return UPGRADE;
                }
                if (state != 7) {
                    if (state == 8) {
                        return DELETING;
                    }
                    if (state != 10000) {
                        return state != 10001 ? "" : PENDING;
                    }
                }
            }
        }
        return DOWNLOAD;
    }

    public static String getUpgradeOrCancelDesc(int state) {
        if (state == 1 || state == 2 || state == 3 || state == 5 || state == 10001) {
            return CANCEL;
        }
        return common(state);
    }

    public static String readableSpeed(long speed) {
        return LogicUtils.humanReadableBytes(speed, true) + "/s";
    }
}
