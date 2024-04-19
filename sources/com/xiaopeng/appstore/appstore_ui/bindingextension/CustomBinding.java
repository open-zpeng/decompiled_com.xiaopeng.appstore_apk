package com.xiaopeng.appstore.appstore_ui.bindingextension;

import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.logic.ItemLogicHelper;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.view.BizProgressButton;
import com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager;
import com.xiaopeng.xui.widget.XCircularProgressBar;
/* loaded from: classes2.dex */
public class CustomBinding {
    private static final String TAG = "CustomBinding";

    public static void setIndicatorType(XCircularProgressBar circularProgress, int type) {
        circularProgress.setIndicatorType(type);
    }

    public static void setProgressBtnStateBinding(BizProgressButton button, int assembleState) {
        Logger.t(TAG).d("setProgressBtnState:" + assembleState);
        setProgressBtnState(button, assembleState);
    }

    public static void setProgressBtnState(BizProgressButton button, boolean isLoading) {
        button.setLoading(isLoading);
    }

    private static void setProgressBtnState(BizProgressButton btn, int assembleState) {
        Logger.t(TAG).d("setProgressBtnState:" + AppStoreAssembleManager.stateToStr(assembleState));
        if (assembleState == 0) {
            btn.setText(ItemLogicHelper.DOWNLOAD);
            btn.setIsProgress(false);
            btn.setSelected(false);
            return;
        }
        if (assembleState != 1) {
            if (assembleState == 2) {
                btn.setIsProgress(true);
                btn.setText(ItemLogicHelper.RESUME, R.drawable.progress_btn_resume);
                btn.setSelected(true);
                return;
            } else if (assembleState != 3) {
                if (assembleState == 4) {
                    btn.setText(ItemLogicHelper.OPEN);
                    btn.setIsProgress(false);
                    btn.setSelected(false);
                    return;
                } else if (assembleState == 6) {
                    btn.setText(ItemLogicHelper.UPGRADE);
                    btn.setIsProgress(false);
                    btn.setSelected(false);
                    return;
                } else if (assembleState == 101) {
                    btn.setText(ItemLogicHelper.ERROR);
                    btn.setIsProgress(false);
                    btn.setSelected(false);
                    return;
                } else if (assembleState == 10001) {
                    btn.setText(ItemLogicHelper.PENDING);
                    btn.setIsProgress(false);
                    btn.setSelected(false);
                    return;
                } else {
                    Logger.t(TAG).w("setProgressBtnState, unhandled state:" + assembleState, new Object[0]);
                    return;
                }
            }
        }
        btn.setText(ItemLogicHelper.PAUSE, R.drawable.progress_btn_pause);
        btn.setIsProgress(true);
        btn.setSelected(true);
    }
}
