package com.xiaopeng.appstore.common_ui.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.common_ui.R;
import com.xiaopeng.libtheme.ThemeViewModel;
import com.xiaopeng.xui.theme.XThemeManager;
/* loaded from: classes.dex */
public class XTabLayout extends FrameLayout {
    private static final boolean DEBUG = false;
    private static final long DURATION = 300;
    private static final float INDICATOR_DAY_HEIGHT_PERCENT = 0.8f;
    private static final double K = 1.048d;
    private static final double K2 = 4.648d;
    public static final int STYLE_DAY = 1;
    public static final int STYLE_NIGHT = 2;
    private static final String TAG = "XTabLayout";
    private final int MARGIN_DAY;
    private final Paint mBlurPaint;
    private final Paint mBlurPaint2;
    private final View.OnClickListener mChildClickListener;
    private int mCurrentEnd;
    private int mCurrentEndNight;
    private int mCurrentStart;
    private int mCurrentStartNight;
    private final float mDivideValue;
    private boolean mIndicatorAnimatorEnable;
    private final int mIndicatorColor;
    private final int mIndicatorColor2;
    private final int mIndicatorColorFrom;
    private final int mIndicatorColorTo;
    private float mIndicatorHeight;
    private final float mIndicatorMarginBottom;
    private final float mIndicatorMaxHeight;
    private final float mIndicatorMinHeight;
    private final int mIndicatorShadowColor;
    private final int mIndicatorShadowColor2;
    private final float mIndicatorShadowRadius;
    private final float mIndicatorShadowRadius2;
    private final float mIndicatorWidth;
    private final float mIndicatorWidthPercent;
    private final boolean mIndicatorWrapContent;
    private boolean mIsDetachedFromWindow;
    private OnTabChangeListener mOnTabChangeListener;
    private final int mPaddingNight;
    private final Paint mPaint;
    private final Paint mPaint2;
    private int mSelectTabIndex;
    private int mStyle;
    private LinearLayout mTabContainer;
    private final boolean mTabCustomBackground;
    private final boolean mTabsBarStyle;
    private int mTempEnd;
    private int mTempEndNight;
    private int mTempStart;
    private int mTempStartNight;
    private final ThemeViewModel mThemeViewModel;
    private final float mTitleTextSize;
    private int mToEnd;
    private int mToEndNight;
    private int mToStart;
    private int mToStartNight;
    private ValueAnimator mValueAnimator;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public interface OnMoveIndicatorListener {
        void onEnd();

        void onStart();
    }

    /* loaded from: classes.dex */
    public interface OnTabChangeListener {
        boolean onInterceptTabChange(XTabLayout tabLayout, int index, boolean tabChange, boolean fromUser);

        void onTabChangeEnd(XTabLayout tabLayout, int index, boolean tabChange, boolean fromUser);

        void onTabChangeStart(XTabLayout tabLayout, int index, boolean tabChange, boolean fromUser);
    }

    /* loaded from: classes.dex */
    public static abstract class OnTabChangeListenerAdapter implements OnTabChangeListener {
        @Override // com.xiaopeng.appstore.common_ui.widget.XTabLayout.OnTabChangeListener
        public boolean onInterceptTabChange(XTabLayout tabLayout, int index, boolean tabChange, boolean fromUser) {
            return false;
        }

        @Override // com.xiaopeng.appstore.common_ui.widget.XTabLayout.OnTabChangeListener
        public void onTabChangeStart(XTabLayout tabLayout, int index, boolean tabChange, boolean fromUser) {
        }
    }

    private boolean isNight() {
        return true;
    }

    public XTabLayout(Context context) {
        this(context, null);
    }

    public XTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public XTabLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes == 0 ? R.style.XTabLayoutAppearance : defStyleRes);
        this.MARGIN_DAY = dp(8);
        this.mPaint = new Paint(1);
        this.mBlurPaint = new Paint(1);
        this.mPaint2 = new Paint(1);
        this.mBlurPaint2 = new Paint(1);
        this.mCurrentStart = 0;
        this.mCurrentEnd = 0;
        this.mCurrentStartNight = 0;
        this.mCurrentEndNight = 0;
        this.mToStart = 0;
        this.mToStartNight = 0;
        this.mToEnd = 0;
        this.mToEndNight = 0;
        this.mTempStart = 0;
        this.mTempStartNight = 0;
        this.mTempEnd = 0;
        this.mTempEndNight = 0;
        this.mDivideValue = 0.6f;
        this.mSelectTabIndex = -1;
        this.mStyle = 2;
        this.mIndicatorWrapContent = true;
        this.mChildClickListener = new View.OnClickListener() { // from class: com.xiaopeng.appstore.common_ui.widget.XTabLayout.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Logger.t(XTabLayout.TAG).i("onClick, animator=" + (XTabLayout.this.mValueAnimator != null ? Integer.valueOf(XTabLayout.this.mValueAnimator.hashCode()) : null), new Object[0]);
                XTabLayout xTabLayout = XTabLayout.this;
                xTabLayout.selectTab(xTabLayout.mTabContainer.indexOfChild(v), true, true);
            }
        };
        this.mIsDetachedFromWindow = true;
        this.mThemeViewModel = ThemeViewModel.create(context, attrs, defStyleAttr, 0, null);
        if (Build.VERSION.SDK_INT <= 26) {
            setLayerType(1, null);
        }
        Resources.Theme theme = getContext().getTheme();
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.XTabLayout, defStyleAttr == 0 ? R.style.XTabLayoutAppearance : defStyleAttr, R.style.XTabLayoutAppearance);
        CharSequence[] textArray = obtainStyledAttributes.getTextArray(R.styleable.XTabLayout_titles);
        this.mTitleTextSize = obtainStyledAttributes.getDimension(R.styleable.XTabLayout_titleTextSize, 24.0f);
        int integer = obtainStyledAttributes.getInteger(R.styleable.XTabLayout_selectTab, 0);
        this.mIndicatorWidth = obtainStyledAttributes.getDimension(R.styleable.XTabLayout_indicatorWidth, 0.0f);
        this.mIndicatorWidthPercent = obtainStyledAttributes.getFraction(R.styleable.XTabLayout_indicatorWidthPercent, 1, 1, 0.7f);
        float dimension = obtainStyledAttributes.getDimension(R.styleable.XTabLayout_indicatorMaxHeight, dp(4));
        this.mIndicatorMaxHeight = dimension;
        this.mIndicatorMinHeight = obtainStyledAttributes.getDimension(R.styleable.XTabLayout_indicatorMinHeight, dp(2));
        this.mIndicatorHeight = dimension;
        this.mIndicatorMarginBottom = obtainStyledAttributes.getDimension(R.styleable.XTabLayout_indicatorMarginBottom, dp(6));
        this.mIndicatorColor = obtainStyledAttributes.getColor(R.styleable.XTabLayout_indicatorColor, getResources().getColor(R.color.x_theme_primary_normal, theme));
        this.mIndicatorColorFrom = obtainStyledAttributes.getColor(R.styleable.XTabLayout_indicatorColorFrom, -1);
        this.mIndicatorColorTo = obtainStyledAttributes.getColor(R.styleable.XTabLayout_indicatorColorTo, -1);
        this.mIndicatorShadowColor = obtainStyledAttributes.getColor(R.styleable.XTabLayout_indicatorShadowColor, -15880455);
        this.mIndicatorShadowRadius = obtainStyledAttributes.getDimension(R.styleable.XTabLayout_indicatorShadowRadius, dp(4));
        this.mIndicatorColor2 = obtainStyledAttributes.getColor(R.styleable.XTabLayout_indicatorColor2, -11243894);
        this.mIndicatorShadowColor2 = obtainStyledAttributes.getColor(R.styleable.XTabLayout_indicatorShadowColor2, -15880455);
        this.mIndicatorShadowRadius2 = obtainStyledAttributes.getDimension(R.styleable.XTabLayout_indicatorShadowRadius2, dp(4));
        this.mIndicatorAnimatorEnable = obtainStyledAttributes.getBoolean(R.styleable.XTabLayout_indicatorAnimatorEnable, true);
        this.mTabsBarStyle = obtainStyledAttributes.getBoolean(R.styleable.XTabLayout_tabsBarStyle, false);
        this.mTabCustomBackground = obtainStyledAttributes.getBoolean(R.styleable.XTabLayout_tabCustomBackground, false);
        this.mPaddingNight = obtainStyledAttributes.getDimensionPixelSize(R.styleable.XTabLayout_tabPaddingNight, dp(40));
        obtainStyledAttributes.recycle();
        setWillNotDraw(false);
        init(textArray);
        selectTab(integer, false, false);
        setStyle(isNight() ? 2 : 1);
    }

    private View getSelectView() {
        return this.mTabContainer.getChildAt(this.mSelectTabIndex);
    }

    public void setStyle(int style) {
        this.mStyle = style;
        if (this.mTabsBarStyle) {
            this.mStyle = 2;
        }
        if (this.mStyle == 2) {
            int i = this.mPaddingNight;
            setPadding(i, 0, i, 0);
        } else {
            setPadding(0, 0, 0, 0);
        }
        moveIndicatorTo(false, null);
    }

    private void init(CharSequence[] titles) {
        this.mPaint.setStrokeWidth(0.0f);
        this.mPaint.setColor(this.mIndicatorColor);
        this.mBlurPaint.setStrokeWidth(0.0f);
        this.mBlurPaint.setMaskFilter(new BlurMaskFilter(this.mIndicatorShadowRadius, BlurMaskFilter.Blur.OUTER));
        this.mBlurPaint.setColor(this.mIndicatorShadowColor);
        this.mPaint2.setStrokeWidth(0.0f);
        this.mPaint2.setColor(this.mIndicatorColor2);
        this.mBlurPaint2.setStrokeWidth(0.0f);
        this.mBlurPaint2.setMaskFilter(new BlurMaskFilter(this.mIndicatorShadowRadius2, BlurMaskFilter.Blur.OUTER));
        this.mBlurPaint2.setColor(this.mIndicatorShadowColor2);
        if (isNight() && !this.mTabsBarStyle) {
            this.mPaint.setMaskFilter(new BlurMaskFilter(this.mIndicatorShadowRadius, BlurMaskFilter.Blur.SOLID));
            this.mPaint2.setMaskFilter(new BlurMaskFilter(this.mIndicatorShadowRadius2, BlurMaskFilter.Blur.SOLID));
        } else {
            this.mPaint.setMaskFilter(null);
            this.mPaint2.setMaskFilter(null);
        }
        this.mTabContainer = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.x_tab_layout_linear_layout, (ViewGroup) this, false);
        if (titles != null) {
            for (CharSequence charSequence : titles) {
                addTab(charSequence);
            }
        }
        addView(this.mTabContainer, new FrameLayout.LayoutParams(-1, -1));
        for (int i = 0; i < this.mTabContainer.getChildCount(); i++) {
            this.mTabContainer.getChildAt(i).setOnClickListener(this.mChildClickListener);
        }
        this.mTabContainer.setOnHierarchyChangeListener(new AnonymousClass2());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.appstore.common_ui.widget.XTabLayout$2  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass2 implements ViewGroup.OnHierarchyChangeListener {
        AnonymousClass2() {
        }

        private void refreshIndicator(final boolean change) {
            XTabLayout.this.post(new Runnable() { // from class: com.xiaopeng.appstore.common_ui.widget.XTabLayout.2.1
                @Override // java.lang.Runnable
                public void run() {
                    XTabLayout.this.moveIndicatorTo(false, new OnMoveIndicatorListener() { // from class: com.xiaopeng.appstore.common_ui.widget.XTabLayout.2.1.1
                        @Override // com.xiaopeng.appstore.common_ui.widget.XTabLayout.OnMoveIndicatorListener
                        public void onStart() {
                            if (XTabLayout.this.mOnTabChangeListener != null) {
                                XTabLayout.this.mOnTabChangeListener.onTabChangeStart(XTabLayout.this, XTabLayout.this.mSelectTabIndex, change, false);
                            }
                        }

                        @Override // com.xiaopeng.appstore.common_ui.widget.XTabLayout.OnMoveIndicatorListener
                        public void onEnd() {
                            if (XTabLayout.this.mOnTabChangeListener != null) {
                                XTabLayout.this.mOnTabChangeListener.onTabChangeEnd(XTabLayout.this, XTabLayout.this.mSelectTabIndex, change, false);
                            }
                        }
                    });
                }
            });
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public void onChildViewAdded(View parent, View child) {
            child.setOnClickListener(XTabLayout.this.mChildClickListener);
            if (XTabLayout.this.mSelectTabIndex < 0) {
                XTabLayout xTabLayout = XTabLayout.this;
                xTabLayout.mSelectTabIndex = xTabLayout.mTabContainer.indexOfChild(child);
            }
            Object tag = child.getTag();
            refreshIndicator(tag != null && ((Boolean) tag).booleanValue());
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public void onChildViewRemoved(View parent, View child) {
            child.setOnClickListener(null);
            Object tag = child.getTag();
            refreshIndicator(tag != null && ((Boolean) tag).booleanValue());
        }
    }

    public boolean isIndicatorAnimatorEnable() {
        return this.mIndicatorAnimatorEnable;
    }

    public void setIndicatorAnimatorEnable(boolean indicatorAnimatorEnable) {
        this.mIndicatorAnimatorEnable = indicatorAnimatorEnable;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        int i = this.mStyle;
        if (i == 1) {
            drawDayIndicator(canvas);
        } else if (i != 2) {
        } else {
            drawNightIndicator(canvas);
        }
    }

    public int addTab(CharSequence title, int index) {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.x_tab_layout_title_view, (ViewGroup) this.mTabContainer, false);
        TextView textView = (TextView) inflate.findViewById(R.id.tv_title);
        textView.setText(title);
        textView.setTextSize(0, this.mTitleTextSize);
        textView.setTag(Boolean.valueOf(index == this.mSelectTabIndex));
        int i = this.mSelectTabIndex;
        if (index <= i) {
            this.mSelectTabIndex = i + 1;
        }
        textView.setSoundEffectsEnabled(isSoundEffectsEnabled());
        this.mTabContainer.addView(inflate, index);
        return index;
    }

    public void changeDotState(int visibity, int index) {
        View childAt = this.mTabContainer.getChildAt(index);
        if (childAt instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) childAt;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View childAt2 = viewGroup.getChildAt(i);
                if (!(childAt2 instanceof TextView)) {
                    childAt2.setVisibility(visibity);
                }
            }
        }
    }

    public boolean isTabClickable(int index) {
        return this.mTabContainer.getChildAt(index).isClickable();
    }

    public boolean isAllTabClickable() {
        int childCount = this.mTabContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (!this.mTabContainer.getChildAt(i).isClickable()) {
                return false;
            }
        }
        return true;
    }

    public void setAllTabClickable(boolean clickable) {
        int childCount = this.mTabContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            this.mTabContainer.getChildAt(i).setClickable(clickable);
        }
    }

    public void setTabClickable(int index, boolean clickable) {
        this.mTabContainer.getChildAt(index).setClickable(clickable);
    }

    public int addTab(CharSequence title) {
        return addTab(title, this.mTabContainer.getChildCount());
    }

    public void removeTab(int index, int selectIndex) {
        if (index >= getTabCount() || selectIndex >= getTabCount()) {
            return;
        }
        if (index == selectIndex) {
            removeTab(index);
            return;
        }
        int i = this.mSelectTabIndex;
        boolean z = selectIndex == i;
        if (z) {
            this.mTabContainer.getChildAt(i).setSelected(false);
            this.mTabContainer.getChildAt(selectIndex).setSelected(true);
        }
        this.mSelectTabIndex = selectIndex;
        this.mTabContainer.getChildAt(index).setTag(Boolean.valueOf(z));
        this.mTabContainer.removeViewAt(index);
    }

    public void removeTab(final int index) {
        if (this.mTabContainer.getChildAt(index) == null) {
            throw new IllegalArgumentException("targetView is not exits. index = " + index + ", tabCount = " + this.mTabContainer.getChildCount());
        }
        int i = this.mSelectTabIndex;
        boolean z = i == index;
        if (i < getTabCount() - 2) {
            this.mTabContainer.getChildAt(this.mSelectTabIndex).setSelected(false);
            this.mTabContainer.getChildAt(index).setTag(Boolean.valueOf(z));
            this.mTabContainer.removeViewAt(index);
            this.mTabContainer.getChildAt(this.mSelectTabIndex).setSelected(true);
            return;
        }
        this.mTabContainer.getChildAt(this.mSelectTabIndex).setSelected(false);
        this.mSelectTabIndex--;
        this.mTabContainer.getChildAt(index).setTag(Boolean.valueOf(z));
        this.mTabContainer.removeViewAt(index);
        View childAt = this.mTabContainer.getChildAt(this.mSelectTabIndex);
        if (childAt != null) {
            childAt.setSelected(true);
        }
    }

    public void selectedNoneTab(boolean animator, final boolean needCallback) {
        View selectView = getSelectView();
        if (selectView != null) {
            selectView.setSelected(false);
        }
        this.mSelectTabIndex = -1;
        moveIndicatorTo(animator, new OnMoveIndicatorListener() { // from class: com.xiaopeng.appstore.common_ui.widget.XTabLayout.3
            @Override // com.xiaopeng.appstore.common_ui.widget.XTabLayout.OnMoveIndicatorListener
            public void onStart() {
                if (!needCallback || XTabLayout.this.mOnTabChangeListener == null) {
                    return;
                }
                XTabLayout.this.mOnTabChangeListener.onTabChangeStart(XTabLayout.this, -1, true, false);
            }

            @Override // com.xiaopeng.appstore.common_ui.widget.XTabLayout.OnMoveIndicatorListener
            public void onEnd() {
                if (!needCallback || XTabLayout.this.mOnTabChangeListener == null) {
                    return;
                }
                XTabLayout.this.mOnTabChangeListener.onTabChangeEnd(XTabLayout.this, -1, true, false);
            }
        });
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void selectTab(final int index, final boolean animator, final boolean fromUser) {
        final boolean z;
        Logger.t(TAG).i("selectTab: index = [" + index + "], animator = [" + animator + "], fromUser = [" + fromUser + "],mSelectTabIndex:" + this.mSelectTabIndex, new Object[0]);
        if (index >= getTabCount() || index < 0) {
            return;
        }
        final View childAt = this.mTabContainer.getChildAt(index);
        if (index == this.mSelectTabIndex) {
            childAt.setSelected(true);
            return;
        }
        OnTabChangeListener onTabChangeListener = this.mOnTabChangeListener;
        if (onTabChangeListener == null || !onTabChangeListener.onInterceptTabChange(this, index, true, fromUser)) {
            View selectView = getSelectView();
            if (childAt != selectView) {
                if (childAt != null) {
                    childAt.setSelected(true);
                }
                if (selectView != null) {
                    selectView.setSelected(false);
                }
                this.mSelectTabIndex = index;
                z = true;
            } else {
                z = false;
            }
            moveIndicatorTo(animator, new OnMoveIndicatorListener() { // from class: com.xiaopeng.appstore.common_ui.widget.XTabLayout.4
                @Override // com.xiaopeng.appstore.common_ui.widget.XTabLayout.OnMoveIndicatorListener
                public void onStart() {
                    if (!z || XTabLayout.this.mOnTabChangeListener == null) {
                        return;
                    }
                    if (childAt == null) {
                        XTabLayout.this.mOnTabChangeListener.onTabChangeStart(XTabLayout.this, -1, true, fromUser);
                    } else {
                        XTabLayout.this.mOnTabChangeListener.onTabChangeStart(XTabLayout.this, index, true, fromUser);
                    }
                }

                @Override // com.xiaopeng.appstore.common_ui.widget.XTabLayout.OnMoveIndicatorListener
                public void onEnd() {
                    if (!z || XTabLayout.this.mOnTabChangeListener == null) {
                        return;
                    }
                    if (childAt == null) {
                        XTabLayout.this.mOnTabChangeListener.onTabChangeEnd(XTabLayout.this, -1, true, fromUser);
                    } else {
                        XTabLayout.this.mOnTabChangeListener.onTabChangeEnd(XTabLayout.this, index, true, fromUser);
                    }
                }
            });
        }
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ThemeViewModel themeViewModel = this.mThemeViewModel;
        if (themeViewModel != null) {
            themeViewModel.onConfigurationChanged(this, newConfig);
        }
        if (XThemeManager.isThemeChanged(newConfig)) {
            setStyle(XThemeManager.isNight(getContext()) ? 2 : 1);
        }
    }

    private void getTabViewPosition(int selectedTabIndex, int[] outPos) {
        if (outPos == null || outPos.length < 2 || selectedTabIndex < 0 || getWidth() <= 0) {
            return;
        }
        if (this.mStyle == 2) {
            TextView childTextView = getChildTextView(selectedTabIndex);
            View childAt = this.mTabContainer.getChildAt(selectedTabIndex);
            int left = childTextView.getLeft() + childAt.getLeft() + this.mPaddingNight;
            int right = childTextView.getRight() + childAt.getLeft() + this.mPaddingNight;
            float f = ((right - left) * (1.0f - this.mIndicatorWidthPercent)) / 2.0f;
            int i = (int) (left + f);
            int i2 = (int) (right - f);
            Logger.t(TAG).d("getTabViewPosition: index=" + selectedTabIndex + " start=" + i + " end=" + i2);
            outPos[0] = i;
            outPos[1] = i2;
            return;
        }
        int width = getWidth() / getTabCount();
        int i3 = this.MARGIN_DAY;
        outPos[0] = (selectedTabIndex * width) + i3;
        outPos[1] = ((selectedTabIndex + 1) * width) - i3;
    }

    private int getTabViewStart(int selectedTabIndex) {
        if (selectedTabIndex < 0 || getWidth() <= 0) {
            return 0;
        }
        if (this.mStyle == 2) {
            TextView childTextView = getChildTextView(selectedTabIndex);
            View childAt = this.mTabContainer.getChildAt(selectedTabIndex);
            int left = childTextView.getLeft() + childAt.getLeft();
            Logger.t(TAG).d("getTabViewStart: index=" + selectedTabIndex + " start=" + left + " end=" + (childTextView.getRight() + childAt.getRight()));
            return left;
        }
        return (selectedTabIndex * (getWidth() / getTabCount())) + this.MARGIN_DAY;
    }

    private int getTabViewEnd(int selectedTabIndex) {
        if (selectedTabIndex < 0 || getWidth() <= 0) {
            return 0;
        }
        if (this.mStyle == 2) {
            return getChildTextView(selectedTabIndex).getRight() + this.mTabContainer.getChildAt(selectedTabIndex).getLeft();
        }
        return ((selectedTabIndex + 1) * (getWidth() / getTabCount())) - this.MARGIN_DAY;
    }

    private int getIndicatorOffset(int tabWidth) {
        float f;
        float f2 = this.mIndicatorWidth;
        if (f2 != 0.0f) {
            f = (tabWidth - f2) / 2.0f;
        } else {
            f = tabWidth * ((1.0f - this.mIndicatorWidthPercent) / 2.0f);
        }
        return (int) f;
    }

    private void getIndicatorPosition() {
        int selectedTabIndex = getSelectedTabIndex();
        if (selectedTabIndex < 0) {
            this.mToStart = 0;
            this.mToEnd = 0;
            this.mToStartNight = 0;
            this.mToEndNight = 0;
            return;
        }
        int[] iArr = new int[2];
        getTabViewPosition(selectedTabIndex, iArr);
        int i = iArr[0];
        this.mToStart = i;
        int i2 = iArr[1];
        this.mToEnd = i2;
        this.mToStartNight = i;
        this.mToEndNight = i2;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void moveIndicatorTo(boolean animator, final OnMoveIndicatorListener listener) {
        boolean z = animator && this.mIndicatorAnimatorEnable;
        getIndicatorPosition();
        if (z) {
            ValueAnimator valueAnimator = this.mValueAnimator;
            if (valueAnimator == null) {
                ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                this.mValueAnimator = ofFloat;
                ofFloat.setDuration(DURATION);
                this.mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaopeng.appstore.common_ui.widget.XTabLayout.5
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float floatValue = ((Float) animation.getAnimatedValue()).floatValue();
                        float min = Math.min(floatValue, 0.6f) / 0.6f;
                        if (floatValue < 0.6f) {
                            XTabLayout xTabLayout = XTabLayout.this;
                            xTabLayout.mIndicatorHeight = (int) (xTabLayout.mIndicatorMaxHeight - ((floatValue / 0.6f) * (XTabLayout.this.mIndicatorMaxHeight - XTabLayout.this.mIndicatorMinHeight)));
                        } else {
                            XTabLayout xTabLayout2 = XTabLayout.this;
                            xTabLayout2.mIndicatorHeight = (int) (xTabLayout2.mIndicatorMaxHeight + ((((floatValue - 0.6f) / 0.39999998f) - 1.0f) * (XTabLayout.this.mIndicatorMaxHeight - XTabLayout.this.mIndicatorMinHeight)));
                        }
                        if (XTabLayout.this.mStyle == 2) {
                            if (XTabLayout.this.mToStartNight > XTabLayout.this.mCurrentStartNight) {
                                XTabLayout xTabLayout3 = XTabLayout.this;
                                xTabLayout3.mTempStartNight = (int) (xTabLayout3.mCurrentStartNight + (Math.pow(floatValue, XTabLayout.K2) * (XTabLayout.this.mToStartNight - XTabLayout.this.mCurrentStartNight)));
                                XTabLayout xTabLayout4 = XTabLayout.this;
                                xTabLayout4.mTempEndNight = (int) (xTabLayout4.mCurrentEndNight + (min * (XTabLayout.this.mToEndNight - XTabLayout.this.mCurrentEndNight)));
                            } else {
                                XTabLayout xTabLayout5 = XTabLayout.this;
                                xTabLayout5.mTempStartNight = (int) (xTabLayout5.mCurrentStartNight + (min * (XTabLayout.this.mToStartNight - XTabLayout.this.mCurrentStartNight)));
                                XTabLayout xTabLayout6 = XTabLayout.this;
                                xTabLayout6.mTempEndNight = (int) (xTabLayout6.mCurrentEndNight + (Math.pow(floatValue, XTabLayout.K2) * (XTabLayout.this.mToEndNight - XTabLayout.this.mCurrentEndNight)));
                            }
                        } else if (XTabLayout.this.mStyle == 1) {
                            if (XTabLayout.this.mToStart > XTabLayout.this.mCurrentStart) {
                                XTabLayout xTabLayout7 = XTabLayout.this;
                                xTabLayout7.mTempStart = (int) (xTabLayout7.mCurrentStart + (Math.pow(floatValue, XTabLayout.K) * (XTabLayout.this.mToStart - XTabLayout.this.mCurrentStart)));
                                XTabLayout xTabLayout8 = XTabLayout.this;
                                xTabLayout8.mTempEnd = (int) (xTabLayout8.mCurrentEnd + (min * (XTabLayout.this.mToEnd - XTabLayout.this.mCurrentEnd)));
                            } else {
                                XTabLayout xTabLayout9 = XTabLayout.this;
                                xTabLayout9.mTempStart = (int) (xTabLayout9.mCurrentStart + (min * (XTabLayout.this.mToStart - XTabLayout.this.mCurrentStart)));
                                XTabLayout xTabLayout10 = XTabLayout.this;
                                xTabLayout10.mTempEnd = (int) (xTabLayout10.mCurrentEnd + (Math.pow(floatValue, XTabLayout.K) * (XTabLayout.this.mToEnd - XTabLayout.this.mCurrentEnd)));
                            }
                        } else {
                            Logger.t(XTabLayout.TAG).e("Error style:" + XTabLayout.this.mStyle, new Object[0]);
                        }
                        XTabLayout.this.invalidate();
                    }
                });
                this.mValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            } else if (valueAnimator.isRunning()) {
                Logger.t(TAG).i("moveIndicatorTo cancel running animator:" + this.mValueAnimator.hashCode(), new Object[0]);
                this.mValueAnimator.cancel();
            }
            post(new Runnable() { // from class: com.xiaopeng.appstore.common_ui.widget.-$$Lambda$XTabLayout$WzRsJG-yrkS734CidmSoRdo5Mhw
                @Override // java.lang.Runnable
                public final void run() {
                    XTabLayout.this.lambda$moveIndicatorTo$0$XTabLayout(listener);
                }
            });
            return;
        }
        Logger.t(TAG).i("moveIndicatorTo with no animation, index=" + this.mSelectTabIndex, new Object[0]);
        assignPosition();
        if (listener != null) {
            listener.onStart();
            listener.onEnd();
        }
        invalidate();
    }

    public /* synthetic */ void lambda$moveIndicatorTo$0$XTabLayout(final OnMoveIndicatorListener onMoveIndicatorListener) {
        this.mValueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.xiaopeng.appstore.common_ui.widget.XTabLayout.6
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                Logger.t(XTabLayout.TAG).i("onAnimationStart " + XTabLayout.this.mValueAnimator.hashCode() + ", listener=" + hashCode() + " select=" + XTabLayout.this.mSelectTabIndex, new Object[0]);
                OnMoveIndicatorListener onMoveIndicatorListener2 = onMoveIndicatorListener;
                if (onMoveIndicatorListener2 != null) {
                    onMoveIndicatorListener2.onStart();
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Logger.t(XTabLayout.TAG).i("onAnimationEnd " + XTabLayout.this.mValueAnimator.hashCode() + ", listener=" + hashCode() + " select=" + XTabLayout.this.mSelectTabIndex, new Object[0]);
                XTabLayout.this.mValueAnimator.removeListener(this);
                XTabLayout.this.assignPosition();
                XTabLayout.this.invalidate();
                OnMoveIndicatorListener onMoveIndicatorListener2 = onMoveIndicatorListener;
                if (onMoveIndicatorListener2 != null) {
                    onMoveIndicatorListener2.onEnd();
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                Logger.t(XTabLayout.TAG).i("onAnimationCancel " + XTabLayout.this.mValueAnimator.hashCode() + ", listener=" + hashCode() + " select=" + XTabLayout.this.mSelectTabIndex, new Object[0]);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorPauseListener
            public void onAnimationPause(Animator animation) {
                super.onAnimationPause(animation);
                Logger.t(XTabLayout.TAG).i("onAnimationPause " + XTabLayout.this.mValueAnimator.hashCode() + ", listener=" + hashCode() + " select=" + XTabLayout.this.mSelectTabIndex, new Object[0]);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorPauseListener
            public void onAnimationResume(Animator animation) {
                super.onAnimationResume(animation);
                Logger.t(XTabLayout.TAG).i("onAnimationResume " + XTabLayout.this.mValueAnimator.hashCode() + ", listener=" + hashCode() + " select=" + XTabLayout.this.mSelectTabIndex, new Object[0]);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                Logger.t(XTabLayout.TAG).i("onAnimationRepeat " + XTabLayout.this.mValueAnimator.hashCode() + ", listener=" + hashCode() + " select=" + XTabLayout.this.mSelectTabIndex, new Object[0]);
            }
        });
        this.mValueAnimator.start();
    }

    private boolean hasDistance() {
        int i = this.mStyle;
        if (i == 2) {
            return Math.abs(this.mToStartNight - this.mCurrentStartNight) > 0 || Math.abs(this.mToEndNight - this.mCurrentEndNight) > 0;
        } else if (i == 1) {
            return Math.abs(this.mToStart - this.mCurrentStart) > 0 || Math.abs(this.mToEnd - this.mCurrentEnd) > 0;
        } else {
            Logger.t(TAG).e("hasDistance error style:" + this.mStyle, new Object[0]);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void assignPosition() {
        int i = this.mToStart;
        this.mCurrentStart = i;
        int i2 = this.mToEnd;
        this.mCurrentEnd = i2;
        int i3 = this.mToStartNight;
        this.mCurrentStartNight = i3;
        int i4 = this.mToEndNight;
        this.mCurrentEndNight = i4;
        this.mTempStart = i;
        this.mTempEnd = i2;
        this.mTempStartNight = i3;
        this.mTempEndNight = i4;
    }

    public void updateTabTitle(int index, CharSequence title) {
        TextView childTextView = getChildTextView(index);
        if (childTextView != null) {
            childTextView.setText(title);
        }
    }

    public CharSequence getTabTitle(int index) {
        TextView childTextView = getChildTextView(index);
        if (childTextView != null) {
            return childTextView.getText();
        }
        return null;
    }

    public void updateTabTitle(int index, int title) {
        TextView childTextView = getChildTextView(index);
        if (childTextView != null) {
            childTextView.setText(title);
        }
    }

    public TextView getChildTextView(int index) {
        View childAt = this.mTabContainer.getChildAt(index);
        if (childAt instanceof ViewGroup) {
            View childAt2 = ((ViewGroup) childAt).getChildAt(0);
            if (childAt2 instanceof TextView) {
                return (TextView) childAt2;
            }
            return null;
        }
        return null;
    }

    @Override // android.view.View
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        LinearLayout linearLayout = this.mTabContainer;
        if (linearLayout != null) {
            int childCount = linearLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                this.mTabContainer.getChildAt(i).setEnabled(enabled);
            }
        }
        int i2 = enabled ? 255 : 92;
        this.mPaint.setAlpha(i2);
        this.mBlurPaint.setAlpha(i2);
        this.mPaint2.setAlpha(i2);
        this.mBlurPaint2.setAlpha(i2);
        invalidate();
    }

    public void setEnabled(boolean enable, int position) {
        LinearLayout linearLayout = this.mTabContainer;
        if (linearLayout != null) {
            int childCount = linearLayout.getChildCount();
            View childAt = this.mTabContainer.getChildAt(position);
            if (position >= childCount || childAt == null) {
                return;
            }
            childAt.setEnabled(enable);
            invalidate();
        }
    }

    public boolean isEnabled(int position) {
        LinearLayout linearLayout = this.mTabContainer;
        if (linearLayout != null) {
            int childCount = linearLayout.getChildCount();
            View childAt = this.mTabContainer.getChildAt(position);
            if (position >= childCount || childAt == null) {
                return false;
            }
            return childAt.isEnabled();
        }
        return false;
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        post(new Runnable() { // from class: com.xiaopeng.appstore.common_ui.widget.XTabLayout.7
            @Override // java.lang.Runnable
            public void run() {
                XTabLayout.this.moveIndicatorTo(false, null);
            }
        });
    }

    public void selectTab(final int index, final boolean animator) {
        selectTab(index, animator, false);
    }

    public int getSelectedTabIndex() {
        return this.mSelectTabIndex;
    }

    public void selectTab(int index) {
        selectTab(index, true);
    }

    public int getTabCount() {
        return this.mTabContainer.getChildCount();
    }

    private void drawDayIndicator(Canvas canvas) {
        this.mPaint.setMaskFilter(null);
        this.mPaint.setAlpha(isEnabled(this.mSelectTabIndex) ? 255 : 92);
        float height = (getHeight() * INDICATOR_DAY_HEIGHT_PERCENT) / 2.0f;
        float height2 = getHeight() >> 1;
        int i = this.mTempStart;
        int i2 = this.mTempEnd;
        if (i < i2) {
            canvas.drawRoundRect(i, height2 - height, i2, height2 + height, height, height, this.mPaint);
        } else {
            canvas.drawRoundRect(i2, height2 - height, i, height2 + height, height, height, this.mPaint);
        }
    }

    private void drawNightIndicator(Canvas canvas) {
        this.mPaint.setAlpha(isEnabled(this.mSelectTabIndex) ? 255 : 92);
        this.mPaint2.setAlpha(isEnabled(this.mSelectTabIndex) ? 255 : 92);
        float f = this.mIndicatorHeight / 2.0f;
        float height = getHeight() - this.mIndicatorMarginBottom;
        if (this.mTempStartNight < this.mTempEndNight) {
            canvas.drawRoundRect(this.mTempStartNight, height - Math.max(this.mIndicatorHeight, 1.0f), this.mTempEndNight, height, f, f, this.mPaint2);
        } else {
            canvas.drawRoundRect(this.mTempEndNight, height - Math.max(this.mIndicatorHeight, 1.0f), this.mTempStartNight, height, f, f, this.mPaint2);
        }
    }

    protected int dp(int dp) {
        return (int) TypedValue.applyDimension(1, dp, getResources().getDisplayMetrics());
    }

    protected float dpF(float dp) {
        return TypedValue.applyDimension(1, dp, getResources().getDisplayMetrics());
    }

    public void setOnTabChangeListener(OnTabChangeListener onTabChangeListener) {
        this.mOnTabChangeListener = onTabChangeListener;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ThemeViewModel themeViewModel = this.mThemeViewModel;
        if (themeViewModel != null) {
            themeViewModel.onAttachedToWindow(this);
        }
        if (this.mIsDetachedFromWindow) {
            this.mIsDetachedFromWindow = false;
            setStyle(isNight() ? 2 : 1);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ValueAnimator valueAnimator = this.mValueAnimator;
        if (valueAnimator != null) {
            valueAnimator.removeAllUpdateListeners();
            this.mValueAnimator.removeAllListeners();
            if (this.mValueAnimator.isStarted() || this.mValueAnimator.isRunning()) {
                this.mValueAnimator.cancel();
            }
        }
        this.mIsDetachedFromWindow = true;
        ThemeViewModel themeViewModel = this.mThemeViewModel;
        if (themeViewModel != null) {
            themeViewModel.onDetachedFromWindow(this);
        }
    }

    @Override // android.view.View
    public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
        super.setSoundEffectsEnabled(soundEffectsEnabled);
        LinearLayout linearLayout = this.mTabContainer;
        if (linearLayout != null) {
            int childCount = linearLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = this.mTabContainer.getChildAt(i);
                if (childAt != null) {
                    childAt.setSoundEffectsEnabled(soundEffectsEnabled);
                }
            }
        }
    }
}
