package com.xiaopeng.appstore.appstore_ui.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.libtheme.ThemeViewModel;
/* loaded from: classes2.dex */
public class BizProgressButton extends ProgressBar {
    private static final String TAG = "BizProgressButton";
    private Drawable mIcon;
    private int mIconPaddingStart;
    private int mIconRes;
    private int mIconSize;
    private final int mIndeterminateDrId;
    private boolean mIsLoading;
    private boolean mIsProgress;
    private String mText;
    private ColorStateList mTextColors;
    private final Paint mTextPaint;
    private final ThemeViewModel mThemeViewModel;

    public BizProgressButton(Context context) {
        this(context, null);
    }

    public BizProgressButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BizProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public BizProgressButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mText = "";
        this.mIconSize = 0;
        this.mIconPaddingStart = 0;
        this.mIsProgress = false;
        this.mIsLoading = false;
        this.mThemeViewModel = ThemeViewModel.create(context, attrs, defStyleAttr, defStyleRes);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.BizProgressButton, defStyleAttr, defStyleRes);
        this.mIndeterminateDrId = obtainStyledAttributes.getResourceId(R.styleable.BizProgressButton_android_indeterminateDrawable, 0);
        obtainStyledAttributes.recycle();
        setIndeterminate(false);
        setClickable(true);
        Paint paint = new Paint(1);
        this.mTextPaint = paint;
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(getContext().getResources().getDimensionPixelSize(R.dimen.x_font_body_02_size));
        refreshTextColor();
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.progress_btn_icon_size);
        setIconSize(dimensionPixelSize);
        setIconPaddingStart(dimensionPixelSize);
    }

    private void refreshTextColor() {
        setTextColor(getContext().getColorStateList(R.color.progress_btn_text_color));
    }

    private void refreshDrawable() {
        if (this.mIconRes != 0) {
            this.mIcon = getContext().getDrawable(this.mIconRes);
        }
        if (this.mIndeterminateDrId != 0) {
            setIndeterminateDrawable(getContext().getDrawable(this.mIndeterminateDrId));
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected void drawableStateChanged() {
        int[] drawableState = getDrawableState();
        Drawable drawable = this.mIcon;
        if (drawable != null && drawable.isStateful()) {
            this.mIcon.setState(drawableState);
        }
        this.mTextPaint.setColor(this.mTextColors.getColorForState(drawableState, 0));
        super.drawableStateChanged();
        Logger.t(TAG).d("drawableStateChanged, btn:" + this.mText + ", selected:" + isSelected() + " enabled:" + isEnabled());
    }

    @Override // android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mThemeViewModel.onAttachedToWindow(this);
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mThemeViewModel.onDetachedFromWindow(this);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (ThemeManager.isThemeChanged(newConfig)) {
            refreshTextColor();
            refreshDrawable();
            this.mThemeViewModel.onConfigurationChanged(this, newConfig);
        }
    }

    public void setText(String text) {
        setText(text, 0);
    }

    public void setText(String text, int icon) {
        this.mText = text;
        this.mIconRes = icon;
        if (icon != 0) {
            this.mIcon = getContext().getDrawable(this.mIconRes);
        } else {
            this.mIcon = null;
        }
        invalidate();
    }

    public void setIcon(int icon) {
        this.mIconRes = icon;
        if (icon != 0) {
            Drawable drawable = getContext().getDrawable(this.mIconRes);
            this.mIcon = drawable;
            if (drawable != null) {
                invalidate();
                return;
            }
            return;
        }
        this.mIcon = null;
    }

    public void setIconSize(int iconSize) {
        this.mIconSize = iconSize;
    }

    public void setIconPaddingStart(int iconPaddingStart) {
        this.mIconPaddingStart = iconPaddingStart;
    }

    private void setIconBounds(Drawable icon) {
        if (icon != null) {
            int i = this.mIconPaddingStart;
            int height = getHeight();
            int i2 = this.mIconSize;
            int i3 = (height - i2) / 2;
            icon.setBounds(i, i3, i + i2, i2 + i3);
        }
    }

    public void setTextColor(ColorStateList colors) {
        this.mTextColors = colors;
        int colorForState = colors.getColorForState(getDrawableState(), 0);
        if (this.mTextPaint.getColor() != colorForState) {
            setTextColor(colorForState);
            invalidate();
        }
    }

    public void setTextColor(int color) {
        this.mTextPaint.setColor(color);
    }

    public void setLoading(boolean loading) {
        if (this.mIsLoading != loading) {
            Logger.t(TAG).d("setLoading: " + loading);
            this.mIsLoading = loading;
            invalidate();
        }
    }

    public void setIsProgress(boolean isProgress) {
        if (this.mIsProgress != isProgress) {
            Logger.t(TAG).d("setIsProgress:" + isProgress);
            this.mIsProgress = isProgress;
            if (isProgress) {
                return;
            }
            super.setProgress(0);
        }
    }

    @Override // android.widget.ProgressBar
    public synchronized void setProgress(int progress) {
        if (!this.mIsProgress) {
            Logger.t(TAG).d("setProgress intercept, not a progress btn: " + progress);
        } else {
            super.setProgress(progress);
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!this.mIsLoading) {
            Paint.FontMetrics fontMetrics = this.mTextPaint.getFontMetrics();
            float height = (getHeight() / 2.0f) + (((fontMetrics.bottom - fontMetrics.top) / 2.0f) - fontMetrics.bottom);
            float width = getWidth() / 2.0f;
            Drawable drawable = this.mIcon;
            if (drawable != null) {
                setIconBounds(drawable);
                this.mIcon.draw(canvas);
                width += this.mIconSize / 2.0f;
            }
            canvas.drawText(this.mText, width, height, this.mTextPaint);
        } else {
            Drawable indeterminateDrawable = getIndeterminateDrawable();
            if (indeterminateDrawable != null) {
                indeterminateDrawable.draw(canvas);
            }
        }
    }
}
