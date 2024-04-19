package com.xiaopeng.appstore.common_ui.icon;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.common_ui.R;
import com.xiaopeng.appstore.common_ui.common.widget.MaskImageView;
import com.xiaopeng.appstore.libcommon.utils.CheckLongPressHelper;
import com.xiaopeng.libtheme.ThemeManager;
/* loaded from: classes.dex */
public class AppIconView extends MaskImageView implements OnLauncherResumeCallback {
    private static final boolean DEBUG = false;
    private static final boolean LOG_DEBUG = true;
    private static final String TAG = "AppIconView";
    private PaintFlagsDrawFilter drawFilter;
    private boolean mAlwaysInDeleteRegion;
    private boolean mAlwaysInTapRegion;
    private Paint mDebugPaint;
    private Drawable mDeleteDrawable;
    private Rect mDeleteRect;
    private float mDownX;
    private float mDownY;
    private boolean mEditMode;
    private boolean mIsDragging;
    private CheckLongPressHelper mLongPressHelper;
    private OnIconTouchListener mOnIconTouchListener;
    private boolean mShowDelete;
    private float mSlop;
    private boolean mStayPressed;
    private float mTouchSlopSquare;
    private static final int[] STATE_PRESSED = {16842919};
    private static final int MASK_COLOR = Color.argb(92, 0, 0, 0);

    /* loaded from: classes.dex */
    public interface OnIconTouchListener {
        void onDeleteTap();

        void onIconDrag();

        boolean onIconLongClick();

        void onIconTap();
    }

    public AppIconView(Context context) {
        this(context, null);
    }

    public AppIconView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AppIconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mAlwaysInDeleteRegion = false;
        this.mAlwaysInTapRegion = false;
        this.mEditMode = false;
        this.mShowDelete = false;
        this.mDownX = 0.0f;
        this.mDownY = 0.0f;
        init();
    }

    @Override // com.xiaopeng.appstore.common_ui.common.widget.MaskImageView
    public int getMaskColor() {
        return MASK_COLOR;
    }

    public void setHideMask(boolean hide) {
        setShowMask(!hide);
    }

    private void init() {
        setClickable(true);
        float scaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        this.mSlop = scaledTouchSlop;
        this.mTouchSlopSquare = scaledTouchSlop * scaledTouchSlop;
        this.mDeleteDrawable = getContext().getDrawable(R.drawable.app_delete_ic);
        this.drawFilter = new PaintFlagsDrawFilter(0, 3);
        setCustomLongClickListener();
    }

    private void setCustomLongClickListener() {
        CheckLongPressHelper checkLongPressHelper = new CheckLongPressHelper(this, new View.OnLongClickListener() { // from class: com.xiaopeng.appstore.common_ui.icon.-$$Lambda$AppIconView$ZWIsoM4hUssatqFnJoPm9EfIx_g
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return AppIconView.this.lambda$setCustomLongClickListener$0$AppIconView(view);
            }
        });
        this.mLongPressHelper = checkLongPressHelper;
        checkLongPressHelper.setLongPressTimeout(1000);
    }

    public /* synthetic */ boolean lambda$setCustomLongClickListener$0$AppIconView(View view) {
        setStayPressed(false);
        this.mAlwaysInDeleteRegion = false;
        this.mAlwaysInTapRegion = false;
        OnIconTouchListener onIconTouchListener = this.mOnIconTouchListener;
        if (onIconTouchListener != null) {
            return onIconTouchListener.onIconLongClick();
        }
        return false;
    }

    public void setOnIconTouchListener(OnIconTouchListener onIconTouchListener) {
        this.mOnIconTouchListener = onIconTouchListener;
    }

    public void setEditMode(boolean editMode) {
        this.mEditMode = editMode;
    }

    public void setShowDelete(boolean showDelete) {
        if (showDelete) {
            updateDeleteDrawable();
        }
        this.mShowDelete = showDelete;
        invalidateDrawable(this.mDeleteDrawable);
    }

    public void setIcon(Bitmap bm) {
        setImageDrawable(new AppIconDrawable(getResources(), bm));
    }

    private void updateDeleteDrawable() {
        Drawable drawable = getContext().getDrawable(R.drawable.app_delete_ic);
        this.mDeleteDrawable = drawable;
        Rect rect = this.mDeleteRect;
        if (rect != null) {
            drawable.setBounds(rect);
        }
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (ThemeManager.isThemeChanged(newConfig)) {
            updateDeleteDrawable();
            invalidateDrawable(this.mDeleteDrawable);
        }
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = (int) (View.MeasureSpec.getSize(widthMeasureSpec) * 0.5f);
        int size2 = (int) (View.MeasureSpec.getSize(heightMeasureSpec) * 0.5f);
        Rect rect = this.mDeleteRect;
        if (rect == null) {
            this.mDeleteRect = new Rect(0, 0, size, size2);
        } else {
            rect.set(0, 0, size, size2);
        }
        this.mDeleteDrawable.setBounds(0, 0, size, size2);
    }

    @Override // android.widget.ImageView, android.view.View
    protected boolean verifyDrawable(Drawable dr) {
        return dr == this.mDeleteDrawable || super.verifyDrawable(dr);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        canvas.setDrawFilter(this.drawFilter);
        super.onDraw(canvas);
        if (this.mShowDelete) {
            int save = canvas.save();
            this.mDeleteDrawable.draw(canvas);
            canvas.restoreToCount(save);
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public int[] onCreateDrawableState(int extraSpace) {
        int[] onCreateDrawableState = super.onCreateDrawableState(extraSpace + 1);
        if (this.mStayPressed) {
            mergeDrawableStates(onCreateDrawableState, STATE_PRESSED);
        }
        return onCreateDrawableState;
    }

    @Override // android.view.View
    public boolean dispatchTouchEvent(MotionEvent event) {
        OnIconTouchListener onIconTouchListener;
        int actionMasked = event.getActionMasked();
        logd("dispatchTouchEvent, action=%s.", Integer.valueOf(actionMasked));
        if (actionMasked == 0) {
            this.mDownX = event.getX();
            this.mDownY = event.getY();
            this.mLongPressHelper.postCheckForLongPress();
            if (isTouchInDelete(this.mDownX, this.mDownY)) {
                this.mAlwaysInDeleteRegion = true;
                this.mAlwaysInTapRegion = false;
            } else if (!this.mEditMode) {
                setStayPressed(true);
                this.mAlwaysInTapRegion = true;
                this.mAlwaysInDeleteRegion = false;
            } else {
                this.mAlwaysInTapRegion = false;
                this.mAlwaysInDeleteRegion = false;
            }
        } else if (actionMasked == 1) {
            setStayPressed(false);
            cancelLongPress();
            if (this.mAlwaysInDeleteRegion) {
                if (this.mOnIconTouchListener != null) {
                    playSoundEffect(0);
                    this.mOnIconTouchListener.onDeleteTap();
                }
            } else if (this.mAlwaysInTapRegion && this.mOnIconTouchListener != null) {
                playSoundEffect(0);
                this.mOnIconTouchListener.onIconTap();
            }
            this.mIsDragging = false;
            this.mAlwaysInDeleteRegion = false;
        } else if (actionMasked == 2) {
            float x = event.getX();
            float y = event.getY();
            int i = (int) (x - this.mDownX);
            int i2 = (int) (y - this.mDownY);
            if ((i * i) + (i2 * i2) > this.mTouchSlopSquare) {
                setStayPressed(false);
                if (!pointInView(this, event.getX(), event.getY(), this.mSlop)) {
                    cancelLongPress();
                }
                boolean z = this.mEditMode;
                this.mIsDragging = z;
                if (z && (onIconTouchListener = this.mOnIconTouchListener) != null) {
                    onIconTouchListener.onIconDrag();
                }
                if (this.mAlwaysInDeleteRegion) {
                    this.mAlwaysInDeleteRegion = false;
                }
                if (this.mAlwaysInTapRegion) {
                    this.mAlwaysInTapRegion = false;
                }
            }
        } else if (actionMasked == 3) {
            cancelTouch();
        }
        return true;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        logd("onTouchEvent, isDragging=%s, action=%s.", Boolean.valueOf(this.mIsDragging), Integer.valueOf(event.getActionMasked()));
        boolean onTouchEvent = super.onTouchEvent(event);
        logd("onTouchEvent result, isDragging=%s, action=%s, handled=%s.", Boolean.valueOf(this.mIsDragging), Integer.valueOf(event.getActionMasked()), Boolean.valueOf(onTouchEvent));
        return onTouchEvent;
    }

    @Override // android.view.View
    public void cancelLongPress() {
        super.cancelLongPress();
        this.mLongPressHelper.cancelLongPress();
    }

    private boolean isTouchInDelete(float x, float y) {
        return this.mEditMode && this.mShowDelete && pointIn(this.mDeleteRect, x, y, 0.0f);
    }

    private void cancelTouch() {
        setStayPressed(false);
        cancelLongPress();
        this.mAlwaysInDeleteRegion = false;
        this.mIsDragging = false;
    }

    public void setStayPressed(boolean stayPressed) {
        if (this.mStayPressed != stayPressed) {
            this.mStayPressed = stayPressed;
            refreshDrawableState();
        }
    }

    @Override // com.xiaopeng.appstore.common_ui.icon.OnLauncherResumeCallback
    public void onLauncherResume() {
        setStayPressed(false);
    }

    public static boolean pointInView(View v, float localX, float localY, float slop) {
        float f = -slop;
        return localX >= f && localY >= f && localX < ((float) v.getWidth()) + slop && localY < ((float) v.getHeight()) + slop;
    }

    public static boolean pointIn(Rect rect, float localX, float localY, float slop) {
        float f = -slop;
        return localX >= f && localY >= f && rect.contains((int) (localX - slop), (int) (localY - slop));
    }

    private static void logd(String message, Object... args) {
        Logger.t(TAG).d(message, args);
    }
}
