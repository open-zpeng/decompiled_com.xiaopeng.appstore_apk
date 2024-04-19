package com.xiaopeng.appstore.common_ui;

import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.common_ui.base.PanelDialog;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.widget.XLoading;
/* loaded from: classes.dex */
public class UserProtocolDialog extends PanelDialog implements DialogInterface.OnDismissListener, ComponentCallbacks, DialogInterface.OnShowListener {
    private static final String AGREEMENT_PROTOCOL_LOCAL_URL = "file:///android_asset/agreement/store_protocol.html";
    private static final String AGREEMENT_PROTOCOL_NIGHT_LOCAL_URL = "file:///android_asset/agreement/store_protocol_night.html";
    private static final String TAG = "UserProtocolDialog";
    private Context mContext;
    private XLoading mLoading;
    private ViewGroup mRootWebView;
    private WebView mWebView;

    @Override // android.content.ComponentCallbacks
    public void onLowMemory() {
    }

    public UserProtocolDialog(Context context) {
        super(context, R.style.XDialogView_Large_Custom);
        this.mContext = context;
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_protocol, (ViewGroup) null);
        setCustomView(inflate, false);
        init(inflate);
    }

    private void init(View rootView) {
        this.mRootWebView = (ViewGroup) rootView.findViewById(R.id.web_view_parent);
        this.mLoading = (XLoading) rootView.findViewById(R.id.loading);
        setCloseVisibility(true);
        setTitle(ResUtils.getString(R.string.agreement_dialog_protocol_title));
        initWebView();
        initListener();
    }

    private void loadUrl() {
        String str = isDay(this.mContext.getResources().getConfiguration()) ? "file:///android_asset/agreement/store_protocol.html" : "file:///android_asset/agreement/store_protocol_night.html";
        if (this.mWebView != null) {
            Logger.t(TAG).i("startLoadUrl:" + str, new Object[0]);
            this.mWebView.loadUrl(str);
            return;
        }
        Logger.t(TAG).e("loadUrl error, webView is null. " + str + ", this:" + this, new Object[0]);
    }

    private void initListener() {
        setOnDismissListener(this);
        setOnShowListener(this);
    }

    private void initWebView() {
        if (this.mWebView != null || this.mContext == null) {
            return;
        }
        this.mLoading.setVisibility(0);
        WebView webView = new WebView(this.mContext);
        this.mWebView = webView;
        webView.setBackgroundColor(ResUtils.getColor(R.color.transparent));
        this.mWebView.setWebViewClient(new WebViewClient() { // from class: com.xiaopeng.appstore.common_ui.UserProtocolDialog.1
            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView2, String s) {
                super.onPageFinished(webView2, s);
                UserProtocolDialog.this.mLoading.setVisibility(4);
            }
        });
        this.mRootWebView.addView(this.mWebView);
    }

    private void releaseWebView() {
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
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialog) {
        releaseWebView();
        Context context = this.mContext;
        if (context != null) {
            context.unregisterComponentCallbacks(this);
        }
    }

    @Override // android.content.DialogInterface.OnShowListener
    public void onShow(DialogInterface dialog) {
        Context context = this.mContext;
        if (context != null) {
            context.registerComponentCallbacks(this);
        }
        if (this.mWebView == null) {
            initWebView();
        }
        loadUrl();
    }

    private boolean isDay(Configuration configuration) {
        return (configuration.uiMode & 48) == 16;
    }

    @Override // android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        if (ThemeManager.isThemeChanged(newConfig)) {
            Logger.t(TAG).i("onConfigurationChanged==> isDayMode:" + isDay(newConfig), new Object[0]);
            if (isShowing()) {
                loadUrl();
            }
        }
    }
}
