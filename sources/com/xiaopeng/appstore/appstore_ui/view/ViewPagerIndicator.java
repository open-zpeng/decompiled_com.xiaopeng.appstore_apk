package com.xiaopeng.appstore.appstore_ui.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
/* loaded from: classes2.dex */
public class ViewPagerIndicator extends LinearLayout implements ViewPager.OnAdapterChangeListener, ViewPager.OnPageChangeListener {
    private final RecyclerView.AdapterDataObserver mAdapterDataObserver;
    private final DataSetObserver mDataSetObserver;
    private final ViewPager2.OnPageChangeCallback mOnPageChangeCallback;
    private PagerAdapter mPagerAdapter;
    private RecyclerView.Adapter mRecyclerAdapter;
    private ViewPager2 mViewPager2;

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int state) {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mDataSetObserver = new DataSetObserver() { // from class: com.xiaopeng.appstore.appstore_ui.view.ViewPagerIndicator.1
            @Override // android.database.DataSetObserver
            public void onChanged() {
                super.onChanged();
                ViewPagerIndicator.this.populateFromPagerAdapter();
            }
        };
        this.mAdapterDataObserver = new RecyclerView.AdapterDataObserver() { // from class: com.xiaopeng.appstore.appstore_ui.view.ViewPagerIndicator.2
            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onChanged() {
                super.onChanged();
                ViewPagerIndicator.this.populateFromRecyclerAdapter();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
                ViewPagerIndicator.this.populateFromRecyclerAdapter();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
                super.onItemRangeChanged(positionStart, itemCount, payload);
                ViewPagerIndicator.this.populateFromRecyclerAdapter();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                ViewPagerIndicator.this.populateFromRecyclerAdapter();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                ViewPagerIndicator.this.populateFromRecyclerAdapter();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            }
        };
        this.mOnPageChangeCallback = new ViewPager2.OnPageChangeCallback() { // from class: com.xiaopeng.appstore.appstore_ui.view.ViewPagerIndicator.3
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                ViewPagerIndicator.this.select(position);
            }

            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        };
        init();
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mDataSetObserver = new DataSetObserver() { // from class: com.xiaopeng.appstore.appstore_ui.view.ViewPagerIndicator.1
            @Override // android.database.DataSetObserver
            public void onChanged() {
                super.onChanged();
                ViewPagerIndicator.this.populateFromPagerAdapter();
            }
        };
        this.mAdapterDataObserver = new RecyclerView.AdapterDataObserver() { // from class: com.xiaopeng.appstore.appstore_ui.view.ViewPagerIndicator.2
            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onChanged() {
                super.onChanged();
                ViewPagerIndicator.this.populateFromRecyclerAdapter();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
                ViewPagerIndicator.this.populateFromRecyclerAdapter();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
                super.onItemRangeChanged(positionStart, itemCount, payload);
                ViewPagerIndicator.this.populateFromRecyclerAdapter();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                ViewPagerIndicator.this.populateFromRecyclerAdapter();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                ViewPagerIndicator.this.populateFromRecyclerAdapter();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            }
        };
        this.mOnPageChangeCallback = new ViewPager2.OnPageChangeCallback() { // from class: com.xiaopeng.appstore.appstore_ui.view.ViewPagerIndicator.3
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                ViewPagerIndicator.this.select(position);
            }

            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        };
        init();
    }

    private void init() {
        setOrientation(0);
    }

    public void setupWithViewPager(ViewPager viewPager) {
        if (viewPager == null) {
            return;
        }
        setAdapter(viewPager.getAdapter());
        viewPager.removeOnAdapterChangeListener(this);
        viewPager.addOnAdapterChangeListener(this);
        viewPager.removeOnPageChangeListener(this);
        viewPager.addOnPageChangeListener(this);
    }

    public void setupWithViewPager2(ViewPager2 viewPager2) {
        if (viewPager2 == null) {
            return;
        }
        this.mViewPager2 = viewPager2;
        setRecyclerAdapter(viewPager2.getAdapter());
        viewPager2.unregisterOnPageChangeCallback(this.mOnPageChangeCallback);
        viewPager2.registerOnPageChangeCallback(this.mOnPageChangeCallback);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        int currentItem;
        super.onAttachedToWindow();
        ViewPager2 viewPager2 = this.mViewPager2;
        if (viewPager2 == null || this.mRecyclerAdapter == null || (currentItem = viewPager2.getCurrentItem()) < 0 || this.mRecyclerAdapter.getItemCount() <= 1) {
            return;
        }
        select(currentItem);
    }

    public void select(int position) {
        int childCount = getChildCount();
        if (childCount <= 0 || position >= childCount) {
            return;
        }
        int i = 0;
        while (i < childCount) {
            getChildAt(i).setSelected(i == position);
            i++;
        }
    }

    private void setRecyclerAdapter(RecyclerView.Adapter adapter) {
        if (adapter == null) {
            return;
        }
        this.mRecyclerAdapter = adapter;
        adapter.registerAdapterDataObserver(this.mAdapterDataObserver);
        populateFromRecyclerAdapter();
    }

    private void setAdapter(PagerAdapter adapter) {
        if (adapter == null) {
            return;
        }
        this.mPagerAdapter = adapter;
        adapter.unregisterDataSetObserver(this.mDataSetObserver);
        this.mPagerAdapter.registerDataSetObserver(this.mDataSetObserver);
        populateFromPagerAdapter();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void populateFromPagerAdapter() {
        int count;
        PagerAdapter pagerAdapter = this.mPagerAdapter;
        if (pagerAdapter != null && (count = pagerAdapter.getCount()) > 0) {
            removeAllViews();
            for (int i = 0; i < count; i++) {
                View generateIndicator = generateIndicator();
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                layoutParams.setMarginStart((int) ResUtils.getDimen(R.dimen.indicator_space));
                addView(generateIndicator, layoutParams);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void populateFromRecyclerAdapter() {
        RecyclerView.Adapter adapter = this.mRecyclerAdapter;
        if (adapter == null) {
            return;
        }
        populateItem(adapter.getItemCount());
    }

    public void populateItem(int count) {
        if (count > 0) {
            setVisibility(0);
            removeAllViews();
            int dimen = (int) ResUtils.getDimen(R.dimen.indicator_size);
            for (int i = 0; i < count; i++) {
                View generateIndicator = generateIndicator();
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dimen, dimen);
                layoutParams.setMarginStart((int) ResUtils.getDimen(R.dimen.indicator_space));
                addView(generateIndicator, layoutParams);
            }
            return;
        }
        setVisibility(8);
    }

    private View generateIndicator() {
        View view = new View(getContext());
        view.setBackgroundResource(R.drawable.bg_view_pager_indicator);
        return view;
    }

    @Override // androidx.viewpager.widget.ViewPager.OnAdapterChangeListener
    public void onAdapterChanged(ViewPager viewPager, PagerAdapter oldAdapter, PagerAdapter newAdapter) {
        setAdapter(newAdapter);
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageSelected(int position) {
        select(position);
    }
}
