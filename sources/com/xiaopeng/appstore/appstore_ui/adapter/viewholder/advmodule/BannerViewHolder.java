package com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.logic.CheckUpdateManager;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvAppModel;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvBannerModel;
import com.xiaopeng.appstore.appstore_biz.model2.adv.AdvModel;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.BannerViewHolder;
import com.xiaopeng.appstore.appstore_ui.databinding.ItemBannerPackageBinding;
import com.xiaopeng.appstore.appstore_ui.view.ShadowLayout;
import com.xiaopeng.appstore.appstore_ui.view.ViewPagerIndicator;
import com.xiaopeng.appstore.common_ui.IdleDetectLayout;
import com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder;
import com.xiaopeng.appstore.common_ui.common.adapter.PayloadData;
import com.xiaopeng.appstore.libcommon.utils.IdleViewDetectHelper;
import com.xiaopeng.appstore.libcommon.utils.ImageUtils;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventEnum;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventTrackingHelper;
import com.xiaopeng.appstore.xpcommon.eventtracking.PagesEnum;
import com.xiaopeng.libtheme.ThemeManager;
import java.util.Iterator;
import java.util.List;
/* loaded from: classes2.dex */
public class BannerViewHolder extends BaseBoundViewHolder<AdvBannerModel, ItemBannerPackageBinding> implements IdleViewDetectHelper.IdleCallback {
    private static final String TAG = "BannerViewHolder";
    private static final int UPDATE_BG_DAY_POS = 0;
    private static final int UPDATE_BG_NIGHT_POS = 1;
    private final RecyclerView.AdapterDataObserver mAdapterDataObserver;
    private AdvBannerModel mAdvBannerModel;
    private final BannerPagerAdapter mBannerAdapter;
    private List<AdvModel> mBannerDataList;
    private final ShadowLayout mBannerShadowLayout;
    private final Button mEnterUpgradeBtn;
    private final ViewPagerIndicator mIndicator;
    private final Observer<Integer> mObserver;
    private OnBannerItemListener mOnBannerItemListener;
    private final ViewPager2.OnPageChangeCallback mOnPageChangeCallback;
    private final View mPendingUpdate;
    private int mSavedSelection;
    private final ImageView mUpdateCardBg;
    private final TextView mUpgradeDesc;
    private final ShadowLayout mUpgradeShadowLayout;
    private final ViewPager2 mViewPager;
    private final IdleDetectLayout mViewPagerDetectLayout;

    /* loaded from: classes2.dex */
    public interface OnBannerItemListener {
        void onAppDetailBannerClick(View v, String packageName);

        void onBannerChanged(int position, AdvModel bannerData);

        void onSceneBannerClick(View v, String sceneId);

        void onUpdateLayoutClick();
    }

    @Override // com.xiaopeng.appstore.libcommon.utils.IdleViewDetectHelper.IdleCallback
    public void onIdleDetectStart() {
    }

    @Override // com.xiaopeng.appstore.libcommon.utils.IdleViewDetectHelper.IdleCallback
    public void onIdleDetectStop() {
    }

    @Override // com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder
    public /* bridge */ /* synthetic */ void setData(AdvBannerModel data, List payloads) {
        setData2(data, (List<Object>) payloads);
    }

    public BannerViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_banner_package);
        this.mObserver = new Observer<Integer>() { // from class: com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.BannerViewHolder.1
            @Override // androidx.lifecycle.Observer
            public void onChanged(Integer count) {
                BannerViewHolder.this.refreshUpgradeUI(count.intValue());
            }
        };
        this.mOnPageChangeCallback = new ViewPager2.OnPageChangeCallback() { // from class: com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.BannerViewHolder.2
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int position) {
                int realPosition = BannerViewHolder.this.getRealPosition(position);
                if (realPosition >= 0) {
                    if (BannerViewHolder.this.mOnBannerItemListener != null && BannerViewHolder.this.mBannerDataList != null && BannerViewHolder.this.mBannerDataList.size() > position) {
                        BannerViewHolder.this.mOnBannerItemListener.onBannerChanged(position, (AdvModel) BannerViewHolder.this.mBannerDataList.get(position));
                    }
                    BannerViewHolder.this.selectIndicator(realPosition);
                }
            }

            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (state == 0) {
                    int currentItem = BannerViewHolder.this.mViewPager.getCurrentItem();
                    if (currentItem == 0) {
                        BannerViewHolder.this.mViewPager.setCurrentItem(BannerViewHolder.this.mBannerAdapter.getItemCount() - 2, false);
                    } else if (currentItem == BannerViewHolder.this.mBannerAdapter.getItemCount() - 1) {
                        BannerViewHolder.this.mViewPager.setCurrentItem(1, false);
                    }
                }
            }
        };
        RecyclerView.AdapterDataObserver adapterDataObserver = new RecyclerView.AdapterDataObserver() { // from class: com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.BannerViewHolder.3
            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onChanged() {
                super.onChanged();
                BannerViewHolder.this.populateIndicator();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
                BannerViewHolder.this.populateIndicator();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
                super.onItemRangeChanged(positionStart, itemCount, payload);
                BannerViewHolder.this.populateIndicator();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                BannerViewHolder.this.populateIndicator();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                BannerViewHolder.this.populateIndicator();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            }
        };
        this.mAdapterDataObserver = adapterDataObserver;
        this.mSavedSelection = 1;
        this.mPendingUpdate = getBinding().layoutPendingUpdate;
        this.mUpdateCardBg = getBinding().ivUpdateBg;
        this.mUpgradeDesc = getBinding().tvUpgradeDesc;
        this.mUpgradeShadowLayout = getBinding().shadowLayout;
        this.mBannerShadowLayout = getBinding().bannerShadowParent;
        ViewPager2 viewPager2 = getBinding().vpBanner;
        this.mViewPager = viewPager2;
        BannerPagerAdapter bannerPagerAdapter = new BannerPagerAdapter();
        this.mBannerAdapter = bannerPagerAdapter;
        bannerPagerAdapter.registerAdapterDataObserver(adapterDataObserver);
        viewPager2.setAdapter(bannerPagerAdapter);
        this.mIndicator = getBinding().indicator;
        this.mEnterUpgradeBtn = getBinding().btnEnter;
        populateIndicator();
        IdleDetectLayout idleDetectLayout = getBinding().viewPagerDetectLayout;
        this.mViewPagerDetectLayout = idleDetectLayout;
        idleDetectLayout.setIdleDelay(6000L);
        idleDetectLayout.setIdleListener(this);
    }

    @Override // com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder
    public void setData(AdvBannerModel data) {
        this.mAdvBannerModel = data;
        List<AdvModel> list = data.getList();
        this.mBannerDataList = list;
        this.mBannerAdapter.setDataList(list);
        refreshBannerSelection();
        this.mBannerAdapter.setOnBannerClickListener(new OnBannerItemListener() { // from class: com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.BannerViewHolder.4
            @Override // com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.BannerViewHolder.OnBannerItemListener
            public void onBannerChanged(int position, AdvModel bannerData) {
            }

            @Override // com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.BannerViewHolder.OnBannerItemListener
            public void onUpdateLayoutClick() {
            }

            @Override // com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.BannerViewHolder.OnBannerItemListener
            public void onSceneBannerClick(View v, String id) {
                if (BannerViewHolder.this.mOnBannerItemListener != null) {
                    BannerViewHolder.this.mOnBannerItemListener.onSceneBannerClick(v, id);
                }
            }

            @Override // com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.BannerViewHolder.OnBannerItemListener
            public void onAppDetailBannerClick(View v, String packageName) {
                if (BannerViewHolder.this.mOnBannerItemListener != null) {
                    BannerViewHolder.this.mOnBannerItemListener.onAppDetailBannerClick(v, packageName);
                }
            }
        });
        if (autoScrollValid()) {
            this.mViewPagerDetectLayout.enableDetect();
        } else {
            this.mViewPagerDetectLayout.disableDetect();
        }
        refreshTheme();
    }

    /* renamed from: setData  reason: avoid collision after fix types in other method */
    public void setData2(AdvBannerModel data, List<Object> payloads) {
        super.setData((BannerViewHolder) data, payloads);
        if (payloads.isEmpty()) {
            return;
        }
        Iterator<Object> it = payloads.iterator();
        while (it.hasNext()) {
            PayloadData payloadData = (PayloadData) it.next();
            if (payloadData.type == 1000) {
                this.mBannerAdapter.setDataList((List) payloadData.data);
                refreshBannerSelection();
            } else if (payloadData.type == 1001) {
                if (payloadData.data instanceof Integer) {
                    refreshUpgradeUI(((Integer) payloadData.data).intValue());
                }
            } else if (payloadData.type == 1003) {
                refreshTheme();
            }
        }
    }

    private void refreshBannerSelection() {
        int itemCount;
        if (this.mBannerAdapter.getDataCount() <= 1 || (itemCount = this.mBannerAdapter.getItemCount()) <= 1) {
            return;
        }
        int i = this.mSavedSelection;
        if (i < 0 || i >= itemCount) {
            this.mSavedSelection = 1;
        }
        scrollToPosition(this.mSavedSelection);
    }

    private void scrollToPosition(int position) {
        View childAt = this.mViewPager.getChildAt(0);
        if (childAt instanceof RecyclerView) {
            ((RecyclerView) childAt).scrollToPosition(position);
        }
    }

    private void refreshUpgradeClick() {
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.-$$Lambda$BannerViewHolder$H6WoOgy6AeHoIrdDlls7skR1VdI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BannerViewHolder.this.lambda$refreshUpgradeClick$0$BannerViewHolder(view);
            }
        };
        this.mPendingUpdate.setOnClickListener(onClickListener);
        this.mEnterUpgradeBtn.setOnClickListener(onClickListener);
    }

    public /* synthetic */ void lambda$refreshUpgradeClick$0$BannerViewHolder(View view) {
        OnBannerItemListener onBannerItemListener = this.mOnBannerItemListener;
        if (onBannerItemListener != null) {
            onBannerItemListener.onUpdateLayoutClick();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshUpgradeUI(int count) {
        if (count > 0) {
            this.mEnterUpgradeBtn.setVisibility(0);
            this.mUpgradeDesc.setText(String.format(ResUtils.getString(R.string.upgrade_count_desc), Integer.valueOf(count)));
            return;
        }
        this.mEnterUpgradeBtn.setVisibility(8);
        this.mUpgradeDesc.setText(ResUtils.getString(R.string.no_upgrade_desc));
    }

    private void refreshTheme() {
        refreshUpdateCardBg();
        this.mUpgradeDesc.setTextColor(ResUtils.getColor(R.color.x_theme_text_01));
        this.mUpgradeShadowLayout.setShadowColor(ResUtils.getColor(R.color.card_shadow_color));
        this.mBannerShadowLayout.setShadowColor(ResUtils.getColor(R.color.card_shadow_color));
        BannerPagerAdapter bannerPagerAdapter = this.mBannerAdapter;
        if (bannerPagerAdapter != null) {
            bannerPagerAdapter.notifyItemChanged(0, Integer.valueOf(bannerPagerAdapter.getItemCount()));
        }
        selectIndicator();
    }

    private void refreshUpdateCardBg() {
        String updateOnlineBg = getUpdateOnlineBg();
        if (!TextUtils.isEmpty(updateOnlineBg)) {
            ImageUtils.load(this.mUpdateCardBg, updateOnlineBg);
        } else {
            this.mUpdateCardBg.setImageDrawable(ResUtils.getDrawable(R.drawable.update_card_bg_default));
        }
    }

    private String getUpdateOnlineBg() {
        List<String> bgList;
        AdvBannerModel advBannerModel = this.mAdvBannerModel;
        if (advBannerModel == null || (bgList = advBannerModel.getBgList()) == null || bgList.isEmpty()) {
            return null;
        }
        if (!ThemeManager.isNightMode(this.mViewPager.getContext())) {
            return bgList.get(0);
        }
        if (bgList.size() > 1) {
            return bgList.get(1);
        }
        return null;
    }

    public void setOnBannerItemListener(OnBannerItemListener listener) {
        this.mOnBannerItemListener = listener;
    }

    public void startBannerTimer() {
        Logger.t(TAG).d("startBannerTimer");
        if (autoScrollValid()) {
            this.mViewPagerDetectLayout.tryStart();
        }
    }

    public void stopBannerTimer() {
        Logger.t(TAG).d("stopBannerTimer");
        this.mViewPagerDetectLayout.stop();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getRealPosition(int position) {
        BannerPagerAdapter bannerPagerAdapter = this.mBannerAdapter;
        if (bannerPagerAdapter == null || position < 0 || position > bannerPagerAdapter.getItemCount() - 1 || position == 0 || position == this.mBannerAdapter.getItemCount() - 1) {
            return -1;
        }
        return position - 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void populateIndicator() {
        BannerPagerAdapter bannerPagerAdapter = this.mBannerAdapter;
        if (bannerPagerAdapter == null) {
            return;
        }
        int dataCount = bannerPagerAdapter.getDataCount();
        if (dataCount > 1) {
            this.mIndicator.populateItem(dataCount);
        } else {
            this.mIndicator.populateItem(0);
        }
    }

    private void selectIndicator() {
        selectIndicatorFromPager(this.mViewPager.getCurrentItem());
    }

    private void selectIndicatorFromPager(int position) {
        int realPosition;
        if (this.mIndicator != null && (realPosition = getRealPosition(position)) >= 0) {
            this.mIndicator.select(realPosition);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void selectIndicator(int position) {
        ViewPagerIndicator viewPagerIndicator = this.mIndicator;
        if (viewPagerIndicator == null || position < 0) {
            return;
        }
        viewPagerIndicator.select(position);
    }

    @Override // com.xiaopeng.appstore.libcommon.utils.IdleViewDetectHelper.IdleCallback
    public void onIdle() {
        int currentItem = this.mViewPager.getCurrentItem();
        if (currentItem < this.mBannerAdapter.getItemCount() - 1) {
            this.mViewPager.setCurrentItem(currentItem + 1);
        } else {
            this.mViewPager.setCurrentItem(0);
        }
        this.mViewPagerDetectLayout.tryStart();
    }

    @Override // com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder
    public void markAttached() {
        super.markAttached();
        refreshUpgradeClick();
        CheckUpdateManager.get().getPendingUpgradeCount().observeForever(this.mObserver);
        this.mViewPager.registerOnPageChangeCallback(this.mOnPageChangeCallback);
        startBannerTimer();
    }

    @Override // com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder
    public void markDetached() {
        super.markDetached();
        CheckUpdateManager.get().getPendingUpgradeCount().removeObserver(this.mObserver);
        this.mViewPager.unregisterOnPageChangeCallback(this.mOnPageChangeCallback);
        stopBannerTimer();
    }

    @Override // com.xiaopeng.appstore.common_ui.common.adapter.BaseBoundViewHolder
    public void onViewRecycled() {
        super.onViewRecycled();
        this.mSavedSelection = this.mViewPager.getCurrentItem();
    }

    private boolean autoScrollValid() {
        return !this.mBannerDataList.isEmpty();
    }

    /* loaded from: classes2.dex */
    public static class BannerPagerAdapter extends RecyclerView.Adapter<BannerPagerHolder> {
        public static final int BANNER_PAGER_EXTENTS_SIZE = 2;
        private List<AdvModel> mDataList;
        private OnBannerItemListener mOnClickListener;

        public BannerPagerAdapter() {
        }

        public BannerPagerAdapter(List<AdvModel> dataList) {
            this.mDataList = dataList;
        }

        public void setOnBannerClickListener(OnBannerItemListener listener) {
            this.mOnClickListener = listener;
        }

        public void setDataList(List<AdvModel> dataList) {
            this.mDataList = dataList;
            notifyDataSetChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public BannerPagerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BannerPagerHolder(parent);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(BannerPagerHolder holder, int position) {
            final int i;
            List<AdvModel> list;
            if (position == 0) {
                position = this.mDataList.size();
            } else if (position == getItemCount() - 1) {
                i = 0;
                list = this.mDataList;
                if (list != null || list.get(i) == null) {
                }
                final AdvModel advModel = this.mDataList.get(i);
                holder.setData(advModel);
                holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.appstore.appstore_ui.adapter.viewholder.advmodule.-$$Lambda$BannerViewHolder$BannerPagerAdapter$2-BClb7qUPIP9Ey8FTmJnll2OFs
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        BannerViewHolder.BannerPagerAdapter.this.lambda$onBindViewHolder$0$BannerViewHolder$BannerPagerAdapter(advModel, i, view);
                    }
                });
                return;
            }
            i = position - 1;
            list = this.mDataList;
            if (list != null) {
            }
        }

        public /* synthetic */ void lambda$onBindViewHolder$0$BannerViewHolder$BannerPagerAdapter(AdvModel advModel, int i, View view) {
            OnBannerItemListener onBannerItemListener = this.mOnClickListener;
            if (onBannerItemListener != null) {
                if (advModel instanceof AdvAppModel) {
                    EventTrackingHelper.sendMolecast(PagesEnum.STORE_STORE_MAIN, EventEnum.STORE_BANNER, Integer.valueOf(i + 1));
                    this.mOnClickListener.onAppDetailBannerClick(view, ((AdvAppModel) advModel).getPackageName());
                    return;
                }
                onBannerItemListener.onSceneBannerClick(view, advModel.getDataId());
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            List<AdvModel> list = this.mDataList;
            if (list != null) {
                if (list.size() > 1) {
                    return this.mDataList.size() + 2;
                }
                if (this.mDataList.size() == 1) {
                    return 1;
                }
            }
            return 0;
        }

        public int getDataCount() {
            List<AdvModel> list = this.mDataList;
            if (list != null) {
                return list.size();
            }
            return 0;
        }
    }

    /* loaded from: classes2.dex */
    public static class BannerPagerHolder extends RecyclerView.ViewHolder {
        private final ImageView mBackgroundImage;

        public BannerPagerHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner_layout, parent, false));
            this.mBackgroundImage = (ImageView) this.itemView;
        }

        public void setData(AdvModel data) {
            if (data == null || data.getImageList() == null || data.getImageList().isEmpty()) {
                return;
            }
            ImageUtils.load(this.mBackgroundImage, data.getImageList().get(0), R.drawable.banner_image_placeholder, R.drawable.banner_image_placeholder);
        }
    }
}
