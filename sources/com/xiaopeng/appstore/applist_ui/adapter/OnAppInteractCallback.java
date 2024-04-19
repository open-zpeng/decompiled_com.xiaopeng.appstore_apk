package com.xiaopeng.appstore.applist_ui.adapter;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.appstore.applist_biz.model.BaseAppItem;
/* loaded from: classes2.dex */
public interface OnAppInteractCallback {
    void onAppDeleteClick(BaseAppItem info);

    boolean onAppIconClick(AppIconViewHolder viewHolder, BaseAppItem info);

    boolean onAppIconLongClick(View v, BaseAppItem info);

    void onIconDrag(RecyclerView.ViewHolder viewHolder);

    void onIconDragChanged(RecyclerView.ViewHolder viewHolder, int pos);

    void onIconDrop(RecyclerView.ViewHolder viewHolder, int fromPos, int toPos);

    void onIconMove(RecyclerView.ViewHolder viewHolder, int fromPos, int toPos);
}
