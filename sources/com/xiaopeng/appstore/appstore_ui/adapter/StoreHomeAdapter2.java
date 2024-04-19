package com.xiaopeng.appstore.appstore_ui.adapter;

import android.content.Context;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvModel;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.BannerViewHolder;
import com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder;
import com.xiaopeng.appstore.common_ui.common.adapter.PayloadData;
/* loaded from: classes2.dex */
public class StoreHomeAdapter2 extends BaseAdvListAdapter implements BannerViewHolder.OnBannerItemListener {
    private static final int GRID_NARROW_SPAN_SIZE = 3;
    private static final int GRID_SPAN_SIZE = 4;
    private static final int GRID_WIDE_SPAN_SIZE = 6;
    private static final int SPAN_COUNT = 12;
    private final GridLayoutManager mLayoutManager;
    private BannerViewHolder.OnBannerItemListener mOnBannerItemListener;

    public StoreHomeAdapter2(Context context) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 12);
        this.mLayoutManager = gridLayoutManager;
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.xiaopeng.appstore.appstore_ui.adapter.StoreHomeAdapter2.1
            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int position) {
                int itemViewType = StoreHomeAdapter2.this.getItemViewType(position);
                if (itemViewType == 7) {
                    return 4;
                }
                if (itemViewType == 9) {
                    return 6;
                }
                return itemViewType == 13 ? 3 : 12;
            }
        });
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return this.mLayoutManager;
    }

    public void setOnBannerItemListener(BannerViewHolder.OnBannerItemListener onItemListener) {
        this.mOnBannerItemListener = onItemListener;
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [T, java.lang.Integer] */
    public void refreshPendingUpgradeCount(int count) {
        PayloadData payloadData = new PayloadData();
        payloadData.type = 1001;
        payloadData.data = Integer.valueOf(count);
        notifyItemRangeChanged(0, getItemCount(), payloadData);
    }

    @Override // com.xiaopeng.appstore.appstore_ui.adapter.BaseAdvListAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(BaseBoundViewHolder<? extends AdvModel, ?> holder, int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof BannerViewHolder) {
            ((BannerViewHolder) holder).setOnBannerItemListener(this);
        }
    }

    @Override // com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.BannerViewHolder.OnBannerItemListener
    public void onUpdateLayoutClick() {
        BannerViewHolder.OnBannerItemListener onBannerItemListener = this.mOnBannerItemListener;
        if (onBannerItemListener != null) {
            onBannerItemListener.onUpdateLayoutClick();
        }
    }

    @Override // com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.BannerViewHolder.OnBannerItemListener
    public void onSceneBannerClick(View v, String sceneId) {
        BannerViewHolder.OnBannerItemListener onBannerItemListener = this.mOnBannerItemListener;
        if (onBannerItemListener != null) {
            onBannerItemListener.onSceneBannerClick(v, sceneId);
        }
    }

    @Override // com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.BannerViewHolder.OnBannerItemListener
    public void onAppDetailBannerClick(View v, String packageName) {
        BannerViewHolder.OnBannerItemListener onBannerItemListener = this.mOnBannerItemListener;
        if (onBannerItemListener != null) {
            onBannerItemListener.onAppDetailBannerClick(v, packageName);
        }
    }

    @Override // com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.BannerViewHolder.OnBannerItemListener
    public void onBannerChanged(int position, AdvModel bannerData) {
        BannerViewHolder.OnBannerItemListener onBannerItemListener = this.mOnBannerItemListener;
        if (onBannerItemListener != null) {
            onBannerItemListener.onBannerChanged(position, bannerData);
        }
    }
}
