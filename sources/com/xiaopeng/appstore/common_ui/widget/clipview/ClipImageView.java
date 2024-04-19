package com.xiaopeng.appstore.common_ui.widget.clipview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.graphics.PathParser;
import com.xiaopeng.appstore.common_ui.R;
/* loaded from: classes.dex */
public class ClipImageView extends AppCompatImageView {
    private static final String TAG = "ClipImageView";
    private static Path sMaskPath;
    private static float sPathHeight;
    private static float sPathWidth;
    private BaseClipViewHelper mClipViewHelper;
    private Path mPath;
    private float mPathHeight;
    private float mPathWidth;

    protected void drawOverlay(Canvas canvas) {
    }

    public static void setPathDataGlobal(String pathData) {
        setPathGlobal(PathParser.createPathFromPathData(pathData));
    }

    public static void setPathGlobal(Path path) {
        sMaskPath = path;
        RectF rectF = new RectF();
        sMaskPath.computeBounds(rectF, true);
        sPathWidth = rectF.width();
        sPathHeight = rectF.height();
    }

    public ClipImageView(Context context) {
        this(context, null);
    }

    public ClipImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClipImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.ClipImageView);
        int color = obtainStyledAttributes.getColor(R.styleable.ClipImageView_bgColor, 0);
        Path path = sMaskPath;
        if (path == null) {
            String string = obtainStyledAttributes.getString(R.styleable.ClipImageView_clipPath);
            if (string != null) {
                setPathData(string);
            }
        } else {
            this.mPath = path;
            this.mPathWidth = sPathWidth;
            this.mPathHeight = sPathHeight;
        }
        obtainStyledAttributes.recycle();
        if (this.mPath != null) {
            ClipViewHelperXfermode clipViewHelperXfermode = new ClipViewHelperXfermode(this.mPath, this.mPathWidth, this.mPathHeight, new ClipDrawCallback() { // from class: com.xiaopeng.appstore.common_ui.widget.clipview.ClipImageView.1
                @Override // com.xiaopeng.appstore.common_ui.widget.clipview.ClipDrawCallback
                public void drawContent(Canvas canvas) {
                    ClipImageView.super.onDraw(canvas);
                    ClipImageView.this.drawOverlay(canvas);
                }
            });
            this.mClipViewHelper = clipViewHelperXfermode;
            clipViewHelperXfermode.setBgColor(color);
        }
    }

    public void setPathData(String pathData) {
        setPath(PathParser.createPathFromPathData(pathData));
    }

    public void setPath(Path path) {
        this.mPath = path;
        RectF rectF = new RectF();
        this.mPath.computeBounds(rectF, true);
        this.mPathWidth = rectF.width();
        this.mPathHeight = rectF.height();
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        BaseClipViewHelper baseClipViewHelper = this.mClipViewHelper;
        if (baseClipViewHelper != null) {
            baseClipViewHelper.onSizeChanged(w, h);
        }
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        BaseClipViewHelper baseClipViewHelper = this.mClipViewHelper;
        if (baseClipViewHelper != null) {
            baseClipViewHelper.draw(canvas);
        } else {
            super.onDraw(canvas);
        }
    }
}
