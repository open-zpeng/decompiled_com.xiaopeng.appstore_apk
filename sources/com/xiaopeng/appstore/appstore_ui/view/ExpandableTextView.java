package com.xiaopeng.appstore.appstore_ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.xui.widget.XLinearLayout;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes2.dex */
public class ExpandableTextView extends XLinearLayout {
    private static final int MAX_COLLAPSED_LINES = 4;
    private static final String TAG = "ExpandableTextView";
    private CharSequence mCharSequence;
    private XTextView mContentTextView;
    private XTextView mExpansionView;
    private boolean mIsCollapsed;
    private int mMaxCollapsedLines;

    public ExpandableTextView(Context context) {
        this(context, null);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mMaxCollapsedLines = 4;
        this.mIsCollapsed = true;
        init(context, attrs);
    }

    public ExpandableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mMaxCollapsedLines = 4;
        this.mIsCollapsed = true;
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_expandable, this);
        this.mContentTextView = (XTextView) findViewById(R.id.tv_content);
        this.mExpansionView = (XTextView) findViewById(R.id.tv_expansion);
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.ExpandableTextView);
        this.mMaxCollapsedLines = obtainStyledAttributes.getInt(R.styleable.ExpandableTextView_maxCollapsedLines, 4);
        this.mCharSequence = obtainStyledAttributes.getString(R.styleable.ExpandableTextView_contentText);
        obtainStyledAttributes.recycle();
        setOrientation(1);
        this.mExpansionView.setVisibility(8);
        initListener();
    }

    private void initListener() {
        setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.appstore.appstore_ui.view.-$$Lambda$ExpandableTextView$YtOA3P88TnFJ3Ij5P0t9huT3kvY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ExpandableTextView.this.lambda$initListener$0$ExpandableTextView(view);
            }
        });
        this.mExpansionView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.appstore.appstore_ui.view.-$$Lambda$ExpandableTextView$-AfEEff3nynQWtVZwMn6uYN-dLc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ExpandableTextView.this.lambda$initListener$1$ExpandableTextView(view);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$ExpandableTextView(View view) {
        toggleExpansionStatus();
    }

    public /* synthetic */ void lambda$initListener$1$ExpandableTextView(View view) {
        toggleExpansionStatus();
    }

    private void toggleExpansionStatus() {
        if (this.mIsCollapsed) {
            this.mExpansionView.setVisibility(8);
            this.mContentTextView.setMaxLines(Integer.MAX_VALUE);
            this.mIsCollapsed = false;
        }
    }

    public void setContentText(CharSequence text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        this.mContentTextView.setMaxLines(Integer.MAX_VALUE);
        this.mContentTextView.setText(text);
        this.mContentTextView.post(new Runnable() { // from class: com.xiaopeng.appstore.appstore_ui.view.-$$Lambda$ExpandableTextView$vNVZUsnGv66bzmtbuVe32onleh8
            @Override // java.lang.Runnable
            public final void run() {
                ExpandableTextView.this.lambda$setContentText$2$ExpandableTextView();
            }
        });
    }

    public /* synthetic */ void lambda$setContentText$2$ExpandableTextView() {
        if (this.mContentTextView.getLineCount() > this.mMaxCollapsedLines) {
            this.mExpansionView.setVisibility(0);
            this.mExpansionView.setText(R.string.view_expand);
            this.mContentTextView.setMaxLines(this.mMaxCollapsedLines);
            this.mContentTextView.setEllipsize(TextUtils.TruncateAt.END);
            this.mIsCollapsed = true;
            return;
        }
        this.mExpansionView.setVisibility(8);
    }

    @Override // android.widget.LinearLayout
    public void setOrientation(int orientation) {
        if (orientation == 0) {
            Logger.t(TAG).w("LinearLayout should be VERTICAL", new Object[0]);
        }
        super.setOrientation(orientation);
    }
}
