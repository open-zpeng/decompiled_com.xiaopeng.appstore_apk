package com.xiaopeng.appstore.appstore_ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.xiaopeng.appstore.appstore_ui.R;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.Delegates;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
/* compiled from: ShadowLayout.kt */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u0000 ;2\u00020\u0001:\u0001;B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0012\u0010(\u001a\u00020)2\b\u0010*\u001a\u0004\u0018\u00010+H\u0014J\u0010\u0010,\u001a\u00020)2\u0006\u0010*\u001a\u00020+H\u0002J$\u0010-\u001a\u00020)2\u0006\u0010*\u001a\u00020+2\u0012\u0010.\u001a\u000e\u0012\u0004\u0012\u00020+\u0012\u0004\u0012\u00020)0/H\u0002J\u0010\u00100\u001a\u00020)2\u0006\u0010*\u001a\u00020+H\u0002J\u001a\u00101\u001a\u00020)2\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0002J\b\u00102\u001a\u00020)H\u0002J(\u00103\u001a\u00020)2\u0006\u00104\u001a\u00020\u00072\u0006\u00105\u001a\u00020\u00072\u0006\u00106\u001a\u00020\u00072\u0006\u00107\u001a\u00020\u0007H\u0014J\b\u00108\u001a\u00020)H\u0002J\u000e\u00109\u001a\u00020)2\u0006\u0010:\u001a\u00020\u0007R\u0012\u0010\t\u001a\u00020\u00078\u0002@\u0002X\u0083\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R+\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000b8B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0016\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u001e\u001a\u00020\u00078\u0002@\u0002X\u0083\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R+\u0010\"\u001a\u00020!2\u0006\u0010\u000e\u001a\u00020!8B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b'\u0010\u0015\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&¨\u0006<"}, d2 = {"Lcom/xiaopeng/appstore/appstore_ui/view/ShadowLayout;", "Landroid/widget/FrameLayout;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "mBorderColor", "mBorderRF", "Landroid/graphics/RectF;", "mBorderWidth", "", "<set-?>", "mContentRF", "getMContentRF", "()Landroid/graphics/RectF;", "setMContentRF", "(Landroid/graphics/RectF;)V", "mContentRF$delegate", "Lkotlin/properties/ReadWriteProperty;", "mCornerRadius", "mDx", "mDy", "mHelpPaint", "Landroid/graphics/Paint;", "mPaint", "mPath", "Landroid/graphics/Path;", "mShadowColor", "mShadowRadius", "mShadowSides", "Landroid/graphics/PorterDuffXfermode;", "mXfermode", "getMXfermode", "()Landroid/graphics/PorterDuffXfermode;", "setMXfermode", "(Landroid/graphics/PorterDuffXfermode;)V", "mXfermode$delegate", "dispatchDraw", "", "canvas", "Landroid/graphics/Canvas;", "drawBorder", "drawChild", "block", "Lkotlin/Function1;", "drawShadow", "initAttributes", "initDrawAttributes", "onSizeChanged", "w", "h", "oldw", "oldh", "processPadding", "setShadowColor", TypedValues.Custom.S_COLOR, "Companion", "appstore_ui_D55V1Release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class ShadowLayout extends FrameLayout {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(ShadowLayout.class, "mXfermode", "getMXfermode()Landroid/graphics/PorterDuffXfermode;", 0)), Reflection.mutableProperty1(new MutablePropertyReference1Impl(ShadowLayout.class, "mContentRF", "getMContentRF()Landroid/graphics/RectF;", 0))};
    public static final Companion Companion = new Companion(null);
    private static final int FLAG_SIDES_ALL = 15;
    private static final int FLAG_SIDES_BOTTOM = 4;
    private static final int FLAG_SIDES_LEFT = 8;
    private static final int FLAG_SIDES_RIGHT = 2;
    private static final int FLAG_SIDES_TOP = 1;
    public static final boolean debug = false;
    public static final int default_borderColor = -65536;
    public static final float default_borderWidth = 0.0f;
    public static final float default_cornerRadius = 0.0f;
    public static final float default_dx = 0.0f;
    public static final float default_dy = 0.0f;
    public static final int default_shadowColor = -16777216;
    public static final float default_shadowRadius = 0.0f;
    public static final int default_shadowSides = 15;
    private int mBorderColor;
    private RectF mBorderRF;
    private float mBorderWidth;
    private final ReadWriteProperty mContentRF$delegate;
    private float mCornerRadius;
    private float mDx;
    private float mDy;
    private Paint mHelpPaint;
    private Paint mPaint;
    private Path mPath;
    private int mShadowColor;
    private float mShadowRadius;
    private int mShadowSides;
    private final ReadWriteProperty mXfermode$delegate;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ShadowLayout(Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ShadowLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ ShadowLayout(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShadowLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.mShadowSides = 15;
        ShadowLayout shadowLayout = this;
        this.mPaint = DrawUtil.createPaint$default(shadowLayout, null, -1, 1, null);
        this.mHelpPaint = DrawUtil.createPaint$default(shadowLayout, null, -65536, 1, null);
        this.mPath = new Path();
        this.mXfermode$delegate = Delegates.INSTANCE.notNull();
        this.mContentRF$delegate = Delegates.INSTANCE.notNull();
        initAttributes(context, attributeSet);
        initDrawAttributes();
        processPadding();
        setLayerType(1, null);
    }

    private final PorterDuffXfermode getMXfermode() {
        return (PorterDuffXfermode) this.mXfermode$delegate.getValue(this, $$delegatedProperties[0]);
    }

    private final void setMXfermode(PorterDuffXfermode porterDuffXfermode) {
        this.mXfermode$delegate.setValue(this, $$delegatedProperties[0], porterDuffXfermode);
    }

    private final RectF getMContentRF() {
        return (RectF) this.mContentRF$delegate.getValue(this, $$delegatedProperties[1]);
    }

    private final void setMContentRF(RectF rectF) {
        this.mContentRF$delegate.setValue(this, $$delegatedProperties[1], rectF);
    }

    private final void initAttributes(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ShadowLayout);
        Intrinsics.checkNotNullExpressionValue(obtainStyledAttributes, "context.obtainStyledAttr…R.styleable.ShadowLayout)");
        try {
            this.mShadowColor = obtainStyledAttributes.getColor(R.styleable.ShadowLayout_sl_shadowColor, -16777216);
            this.mShadowRadius = obtainStyledAttributes.getDimension(R.styleable.ShadowLayout_sl_shadowRadius, DrawUtil.dpf2pxf(context, 0.0f));
            this.mDx = obtainStyledAttributes.getDimension(R.styleable.ShadowLayout_sl_dx, 0.0f);
            this.mDy = obtainStyledAttributes.getDimension(R.styleable.ShadowLayout_sl_dy, 0.0f);
            this.mCornerRadius = obtainStyledAttributes.getDimension(R.styleable.ShadowLayout_sl_cornerRadius, DrawUtil.dpf2pxf(context, 0.0f));
            this.mBorderColor = obtainStyledAttributes.getColor(R.styleable.ShadowLayout_sl_borderColor, -65536);
            this.mBorderWidth = obtainStyledAttributes.getDimension(R.styleable.ShadowLayout_sl_borderWidth, DrawUtil.dpf2pxf(context, 0.0f));
            this.mShadowSides = obtainStyledAttributes.getInt(R.styleable.ShadowLayout_sl_shadowSides, 15);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    private final void initDrawAttributes() {
        setMXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
    }

    private final void processPadding() {
        int abs = (int) (this.mShadowRadius + Math.abs(this.mDx));
        int abs2 = (int) (this.mShadowRadius + Math.abs(this.mDy));
        int i = DrawUtil.containsFlag(this.mShadowSides, 8) ? abs : 0;
        int i2 = DrawUtil.containsFlag(this.mShadowSides, 1) ? abs2 : 0;
        if (!DrawUtil.containsFlag(this.mShadowSides, 2)) {
            abs = 0;
        }
        if (!DrawUtil.containsFlag(this.mShadowSides, 4)) {
            abs2 = 0;
        }
        setPadding(i, i2, abs, abs2);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        setMContentRF(new RectF(getPaddingLeft(), getPaddingTop(), i - getPaddingRight(), i2 - getPaddingBottom()));
        float f = this.mBorderWidth / 3;
        if (f > 0.0f) {
            this.mBorderRF = new RectF(getMContentRF().left + f, getMContentRF().top + f, getMContentRF().right - f, getMContentRF().bottom - f);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        if (canvas == null) {
            return;
        }
        DrawUtil.helpGreenCurtain(canvas, false);
        drawShadow(canvas);
        drawChild(canvas, new Function1<Canvas, Unit>() { // from class: com.xiaopeng.appstore.appstore_ui.view.ShadowLayout$dispatchDraw$1
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Canvas canvas2) {
                invoke2(canvas2);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke  reason: avoid collision after fix types in other method */
            public final void invoke2(Canvas it) {
                Intrinsics.checkNotNullParameter(it, "it");
                super/*android.widget.FrameLayout*/.dispatchDraw(it);
            }
        });
    }

    private final void drawShadow(Canvas canvas) {
        canvas.save();
        this.mPaint.setShadowLayer(this.mShadowRadius, this.mDx, this.mDy, this.mShadowColor);
        this.mPaint.setColor(this.mShadowColor);
        RectF mContentRF = getMContentRF();
        float f = this.mCornerRadius;
        canvas.drawRoundRect(mContentRF, f, f, this.mPaint);
        DrawUtil.utilReset$default(this.mPaint, null, null, 3, null);
        canvas.restore();
    }

    private final void drawChild(Canvas canvas, Function1<? super Canvas, Unit> function1) {
        canvas.saveLayer(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), this.mPaint);
        function1.invoke(canvas);
        Path path = this.mPath;
        path.addRect(getMContentRF(), Path.Direction.CW);
        RectF mContentRF = getMContentRF();
        float f = this.mCornerRadius;
        path.addRoundRect(mContentRF, f, f, Path.Direction.CW);
        path.setFillType(Path.FillType.EVEN_ODD);
        this.mPath = path;
        this.mPaint.setXfermode(getMXfermode());
        canvas.drawPath(this.mPath, this.mPaint);
        DrawUtil.utilReset$default(this.mPaint, null, null, 3, null);
        this.mPath.reset();
        canvas.restore();
    }

    private final void drawBorder(Canvas canvas) {
        RectF rectF = this.mBorderRF;
        if (rectF != null) {
            canvas.save();
            this.mPaint.setStrokeWidth(this.mBorderWidth);
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mPaint.setColor(this.mBorderColor);
            float f = this.mCornerRadius;
            canvas.drawRoundRect(rectF, f, f, this.mPaint);
            DrawUtil.utilReset$default(this.mPaint, null, null, 3, null);
            canvas.restore();
        }
    }

    /* compiled from: ShadowLayout.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\rX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\rX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\rX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/xiaopeng/appstore/appstore_ui/view/ShadowLayout$Companion;", "", "()V", "FLAG_SIDES_ALL", "", "FLAG_SIDES_BOTTOM", "FLAG_SIDES_LEFT", "FLAG_SIDES_RIGHT", "FLAG_SIDES_TOP", "debug", "", "default_borderColor", "default_borderWidth", "", "default_cornerRadius", "default_dx", "default_dy", "default_shadowColor", "default_shadowRadius", "default_shadowSides", "appstore_ui_D55V1Release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final void setShadowColor(int i) {
        this.mShadowColor = i;
        invalidate();
    }
}
