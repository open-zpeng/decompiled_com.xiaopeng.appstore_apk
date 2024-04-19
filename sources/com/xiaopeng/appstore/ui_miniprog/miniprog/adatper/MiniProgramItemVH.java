package com.xiaopeng.appstore.ui_miniprog.miniprog.adatper;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.TextView;
import com.xiaopeng.appstore.applet_biz.model.MiniProgramModel;
import com.xiaopeng.appstore.applet_ui.R;
import com.xiaopeng.appstore.common_ui.common.base.ViewHolderBase;
import com.xiaopeng.appstore.common_ui.icon.AppIconView;
import com.xiaopeng.appstore.libcommon.utils.ImageUtils;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
import com.xiaopeng.appstore.ui_miniprog.miniprog.adatper.MiniProgramListAdapter;
import com.xiaopeng.xui.app.XToast;
/* loaded from: classes2.dex */
public class MiniProgramItemVH extends ViewHolderBase<MiniProgramModel> {
    private static final int ICON_SIZE = ResUtils.getDimenPixel(R.dimen.icon_size);
    private MiniProgramListAdapter.OnItemListener mOnItemListener;

    public MiniProgramItemVH(ViewGroup parent, MiniProgramListAdapter.OnItemListener listener) {
        super(parent, R.layout.layout_app_list_appitem);
        this.mOnItemListener = listener;
    }

    @Override // com.xiaopeng.appstore.common_ui.common.base.ViewHolderBase
    public void setData(final MiniProgramModel data) {
        ((TextView) this.itemView.findViewById(R.id.tv_app_name)).setText(data.getName());
        final AppIconView appIconView = (AppIconView) this.itemView.findViewById(R.id.iv_icon);
        ImageUtils.loadRound(appIconView, data.getIconUrl(), R.drawable.icon_placeholder, R.drawable.icon_placeholder, ICON_SIZE);
        appIconView.setOnIconTouchListener(new AppIconView.OnIconTouchListener() { // from class: com.xiaopeng.appstore.ui_miniprog.miniprog.adatper.MiniProgramItemVH.1
            @Override // com.xiaopeng.appstore.common_ui.icon.AppIconView.OnIconTouchListener
            public void onDeleteTap() {
            }

            @Override // com.xiaopeng.appstore.common_ui.icon.AppIconView.OnIconTouchListener
            public void onIconDrag() {
            }

            @Override // com.xiaopeng.appstore.common_ui.icon.AppIconView.OnIconTouchListener
            public void onIconTap() {
                if (MiniProgramItemVH.this.mOnItemListener != null) {
                    MiniProgramItemVH.this.mOnItemListener.onItemClick(appIconView, data.getId(), data.getName());
                }
            }

            @Override // com.xiaopeng.appstore.common_ui.icon.AppIconView.OnIconTouchListener
            public boolean onIconLongClick() {
                if (MiniProgramItemVH.this.itemView.getContext() instanceof Activity) {
                    XToast.show(ResUtils.getString(R.string.edit_mode_not_support_tips));
                    return true;
                }
                return true;
            }
        });
    }
}
