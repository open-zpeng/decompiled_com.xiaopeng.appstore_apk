package com.xiaopeng.appstore.ui_miniprog.miniprog.adatper;

import android.view.ViewGroup;
import com.xiaopeng.appstore.common_ui.common.base.RecyclerAdapterBase;
import com.xiaopeng.appstore.common_ui.common.base.ViewHolderBase;
import com.xiaopeng.appstore.common_ui.icon.AppIconView;
/* loaded from: classes2.dex */
public class MiniProgramListAdapter extends RecyclerAdapterBase {
    public static final int TYPE_MINI_FOOTER = 3;
    public static final int TYPE_MINI_PROGRAM = 1;
    public static final int TYPE_MINI_PROGRAM_GROUP_TITLE = 2;
    private OnItemListener mOnItemListener;

    /* loaded from: classes2.dex */
    public interface OnItemListener {
        void onItemClick(AppIconView icon, String miniAppId, String name);
    }

    public MiniProgramListAdapter(OnItemListener itemListener) {
        this.mOnItemListener = itemListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolderBase<?> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new MiniProgramItemVH(parent, this.mOnItemListener);
        }
        if (viewType == 2) {
            return new MiniProgramTitleVH(parent);
        }
        if (viewType == 3) {
            return new MiniFooterViewHolder(parent);
        }
        return new MiniProgramItemVH(parent, this.mOnItemListener);
    }
}
