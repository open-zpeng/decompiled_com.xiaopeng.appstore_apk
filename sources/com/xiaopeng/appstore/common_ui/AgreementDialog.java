package com.xiaopeng.appstore.common_ui;

import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CompoundButton;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.common_ui.base.PanelDialog;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.widget.XCheckBox;
import com.xiaopeng.xui.widget.XLoading;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public class AgreementDialog extends PanelDialog implements ComponentCallbacks {
    private static final String AGREEMENT_PROTOCOL_LOCAL_URL = "file:///android_asset/agreement/store_protocol.html";
    private static final String AGREEMENT_PROTOCOL_NIGHT_LOCAL_URL = "file:///android_asset/agreement/store_protocol_night.html";
    private final String AGREEMENT_PRIVACY_LOCAL_URL;
    private final String AGREEMENT_PRIVACY_NIGHT_LOCAL_URL;
    private final int SHOW_PRIVACY;
    private final int SHOW_PROTOCOL;
    private final String TAG;
    private XCheckBox mAgreeChecked;
    private final Context mContext;
    private boolean mIsDayMode;
    private XTextView mPrivacyTextView;
    private XTextView mProtocolTextView;
    private ViewGroup mRootWebView;
    private volatile int mShowState;
    private boolean mShown;
    private WebView mWebView;
    private final int mWebViewMargin;
    private XLoading mXLoading;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$initWebView$3(View view) {
        return true;
    }

    @Override // android.content.ComponentCallbacks
    public void onLowMemory() {
    }

    public AgreementDialog(Context context) {
        super(context, R.style.XDialogView_Large_Custom);
        this.AGREEMENT_PRIVACY_LOCAL_URL = "file:///android_asset/agreement/store_privacy.html";
        this.AGREEMENT_PRIVACY_NIGHT_LOCAL_URL = "file:///android_asset/agreement/store_privacy_night.html";
        this.SHOW_PROTOCOL = 0;
        this.SHOW_PRIVACY = 1;
        this.TAG = "AgreementDialog";
        this.mShowState = 0;
        this.mIsDayMode = true;
        this.mShown = false;
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_agreement, (ViewGroup) null);
        setCustomView(inflate, false);
        this.mContext = context;
        this.mWebViewMargin = context.getResources().getDimensionPixelOffset(R.dimen.x_dialog_content_scrollbar_in_padding);
        init(inflate);
        initListener();
    }

    @Override // com.xiaopeng.xui.app.XDialog
    public void show() {
        this.mShown = true;
        Logger.t("AgreementDialog").i("show, this:" + this, new Object[0]);
        super.show();
        initOnShow();
    }

    private void initOnShow() {
        Context context = this.mContext;
        if (context != null) {
            this.mIsDayMode = isDay(context.getResources().getConfiguration());
            Logger.t("AgreementDialog").i("initOnShow==> isDayMode:" + this.mIsDayMode, new Object[0]);
        }
        Context context2 = this.mContext;
        if (context2 != null) {
            context2.unregisterComponentCallbacks(this);
            this.mContext.registerComponentCallbacks(this);
        }
        if (this.mWebView == null) {
            initWebView();
        } else {
            Logger.t("AgreementDialog").i("initOnShow, webview not null:" + this.mWebView, new Object[0]);
        }
        renderLayoutByDayNightStatus();
        resetUrl();
    }

    private void resetUrl() {
        resetState();
        loadUrl();
    }

    private void resetState() {
        setAgreeCheck(false);
        this.mShowState = 0;
        setPositiveButtonEnable(false);
    }

    private void setAgreeCheck(boolean isCheck) {
        XCheckBox xCheckBox = this.mAgreeChecked;
        if (xCheckBox != null) {
            xCheckBox.setChecked(isCheck);
        }
    }

    public void destroy() {
        Logger.t("AgreementDialog").d("destroy, this:" + this + ", webview:" + this.mWebView);
        ViewGroup viewGroup = this.mRootWebView;
        if (viewGroup != null) {
            viewGroup.removeView(this.mWebView);
        }
        WebView webView = this.mWebView;
        if (webView != null) {
            webView.clearHistory();
            this.mWebView.removeAllViews();
            this.mWebView.destroy();
            this.mWebView = null;
        }
        Context context = this.mContext;
        if (context != null) {
            context.unregisterComponentCallbacks(this);
        }
        XLoading xLoading = this.mXLoading;
        if (xLoading != null) {
            xLoading.setVisibility(8);
        }
        this.mShown = false;
    }

    private void initListener() {
        this.mAgreeChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.appstore.common_ui.AgreementDialog.1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AgreementDialog.this.setPositiveButtonEnable(isChecked);
            }
        });
        this.mProtocolTextView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.appstore.common_ui.-$$Lambda$AgreementDialog$PJuYeOk9kDN_io0TVHPEBCCQy9I
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AgreementDialog.this.lambda$initListener$0$AgreementDialog(view);
            }
        });
        this.mPrivacyTextView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.appstore.common_ui.-$$Lambda$AgreementDialog$0ZL42i5FB197iIUMiqx_GP3jryo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AgreementDialog.this.lambda$initListener$1$AgreementDialog(view);
            }
        });
        getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.appstore.common_ui.-$$Lambda$AgreementDialog$1SMCzqb3tWK6u5jpnNyGEjQoANA
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                AgreementDialog.this.lambda$initListener$2$AgreementDialog(dialogInterface);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$AgreementDialog(View view) {
        if (this.mShowState == 0) {
            return;
        }
        this.mShowState = 0;
        loadUrl();
    }

    public /* synthetic */ void lambda$initListener$1$AgreementDialog(View view) {
        if (this.mShowState == 1) {
            return;
        }
        this.mShowState = 1;
        loadUrl();
    }

    public /* synthetic */ void lambda$initListener$2$AgreementDialog(DialogInterface dialogInterface) {
        destroy();
    }

    private void setSelectState(boolean isSelect) {
        XTextView xTextView = this.mProtocolTextView;
        if (xTextView != null) {
            xTextView.setSelected(isSelect);
        }
        XTextView xTextView2 = this.mPrivacyTextView;
        if (xTextView2 != null) {
            xTextView2.setSelected(!isSelect);
        }
    }

    private void loadUrl() {
        String str = this.mIsDayMode ? "file:///android_asset/agreement/store_protocol.html" : "file:///android_asset/agreement/store_protocol_night.html";
        String string = ResUtils.getString(R.string.agreement_dialog_protocol_title);
        if (this.mShowState == 0) {
            String str2 = this.mIsDayMode ? "file:///android_asset/agreement/store_protocol.html" : "file:///android_asset/agreement/store_protocol_night.html";
            string = ResUtils.getString(R.string.agreement_dialog_protocol_title);
            setSelectState(true);
            str = str2;
        } else if (this.mShowState == 1) {
            str = this.mIsDayMode ? "file:///android_asset/agreement/store_privacy.html" : "file:///android_asset/agreement/store_privacy_night.html";
            string = ResUtils.getString(R.string.agreement_dialog_privacy_title);
            setSelectState(false);
        } else {
            setSelectState(true);
        }
        setTitle(string);
        if (this.mWebView != null) {
            Logger.t("AgreementDialog").i("startLoadUrl:" + str + ", web=" + this.mWebView, new Object[0]);
            this.mWebView.loadUrl(str);
            return;
        }
        Logger.t("AgreementDialog").e("loadUrl error, webview is null. " + str + ", this:" + this, new Object[0]);
    }

    private void init(View rootView) {
        Window window = getDialog().getWindow();
        if (window != null) {
            window.getDecorView().setSystemUiVisibility(5894);
        }
        this.mRootWebView = (ViewGroup) rootView.findViewById(R.id.web_view_parent);
        this.mPrivacyTextView = (XTextView) rootView.findViewById(R.id.agreement_privacy);
        this.mProtocolTextView = (XTextView) rootView.findViewById(R.id.agreement_protocol);
        this.mAgreeChecked = (XCheckBox) rootView.findViewById(R.id.checked_agree);
        this.mXLoading = (XLoading) rootView.findViewById(R.id.loading);
    }

    private void initWebView() {
        if (this.mRootWebView != null) {
            this.mWebView = new WebView(this.mContext);
            Logger.t("AgreementDialog").i("initWebView:" + this.mWebView, new Object[0]);
            this.mXLoading.setVisibility(0);
            this.mWebView.setVisibility(8);
            this.mWebView.setHorizontalScrollBarEnabled(false);
            this.mWebView.setVerticalScrollBarEnabled(false);
            this.mWebView.setScrollbarFadingEnabled(true);
            this.mWebView.setBackgroundColor(ResUtils.getColor(R.color.transparent));
            this.mWebView.setWebViewClient(new WebViewClient() { // from class: com.xiaopeng.appstore.common_ui.AgreementDialog.2
                @Override // android.webkit.WebViewClient
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    super.onReceivedError(view, request, error);
                    Logger.t("AgreementDialog").w("onWebViewReceivedError, " + ((Object) (error != null ? error.getDescription() : "")) + ", " + (request != null ? request.getUrl() : ""), new Object[0]);
                }

                @Override // android.webkit.WebViewClient
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    Logger.t("AgreementDialog").i("onWebViewPageFinished==> url:" + url + ", " + view, new Object[0]);
                    if (AgreementDialog.this.mRootWebView != null) {
                        AgreementDialog.this.mRootWebView.scrollTo(0, 0);
                    }
                    AgreementDialog.this.mXLoading.setVisibility(8);
                    AgreementDialog.this.mWebView.setVisibility(0);
                }

                @Override // android.webkit.WebViewClient
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    Logger.t("AgreementDialog").i("onPageStarted==> url:" + url + ", " + view, new Object[0]);
                }
            });
            this.mWebView.setLongClickable(true);
            this.mWebView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.xiaopeng.appstore.common_ui.-$$Lambda$AgreementDialog$t1OxXi7TRWgKtEzf-sv6Nkxk9ko
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    return AgreementDialog.lambda$initWebView$3(view);
                }
            });
            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(-1, -2);
            marginLayoutParams.setMarginStart(this.mWebViewMargin);
            marginLayoutParams.setMarginEnd(this.mWebViewMargin);
            this.mRootWebView.addView(this.mWebView, marginLayoutParams);
        }
    }

    @Override // com.xiaopeng.xui.app.XDialog
    public boolean isShowing() {
        return getDialog().isShowing() || this.mShown;
    }

    @Override // android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        if (ThemeManager.isThemeChanged(newConfig)) {
            boolean isDay = isDay(newConfig);
            this.mIsDayMode = isDay;
            Logger.t("AgreementDialog").i("onConfigurationChanged==> isDayMode:" + isDay, new Object[0]);
            if (isShowing()) {
                renderLayoutByDayNightStatus();
                loadUrl();
            }
        }
    }

    public void renderLayoutByDayNightStatus() {
        XTextView xTextView = this.mPrivacyTextView;
        if (xTextView != null) {
            xTextView.setTextColor(ResUtils.getColorStateList(R.color.agreement_selector_color));
        }
        XTextView xTextView2 = this.mProtocolTextView;
        if (xTextView2 != null) {
            xTextView2.setTextColor(ResUtils.getColorStateList(R.color.agreement_selector_color));
        }
        XCheckBox xCheckBox = this.mAgreeChecked;
        if (xCheckBox != null) {
            xCheckBox.setTextColor(ResUtils.getColor(R.color.x_theme_text_02));
        }
    }

    private boolean isDay(Configuration configuration) {
        return (configuration.uiMode & 48) == 16;
    }
}
