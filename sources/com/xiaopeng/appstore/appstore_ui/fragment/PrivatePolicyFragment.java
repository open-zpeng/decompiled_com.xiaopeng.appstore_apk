package com.xiaopeng.appstore.appstore_ui.fragment;

import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.appstore_biz.model2.PrivatePolicyModel;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.common_ui.BaseBizFragment;
import com.xiaopeng.appstore.libcommon.utils.ImageUtils;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
/* loaded from: classes2.dex */
public class PrivatePolicyFragment extends BaseBizFragment {
    public static final String PRIVATE_DATA = "private_data";
    public static final String TAG = "PrivatePolicyFragment";
    private View mErrorView;
    private ImageView mIvIconView;
    private View mLineView;
    private PrivatePolicyModel mModel;
    private WebView mWebView;
    private ViewGroup mWebViewLayout;

    @Override // com.xiaopeng.appstore.common_ui.BaseBizFragment, com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mModel = getPrivatePolicyModel();
    }

    private PrivatePolicyModel getPrivatePolicyModel() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            return (PrivatePolicyModel) arguments.getSerializable(PRIVATE_DATA);
        }
        Logger.t(TAG).i("getPrivatePolicyModel arguments is  null", new Object[0]);
        return null;
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_private_police, container, false);
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mWebViewLayout = (ViewGroup) view.findViewById(R.id.policy_web_view_layout);
        this.mErrorView = view.findViewById(R.id.linear_view);
        this.mLineView = view.findViewById(R.id.line);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_icon);
        this.mIvIconView = imageView;
        PrivatePolicyModel privatePolicyModel = this.mModel;
        if (privatePolicyModel != null) {
            ImageUtils.loadAdaptiveIcon(imageView, privatePolicyModel.getIconUrl(), null, null);
            String webUrl = this.mModel.getWebUrl();
            if (!TextUtils.isEmpty(webUrl)) {
                Logger.t(TAG).i("loadUrl webUrl is " + webUrl, new Object[0]);
                initWebView();
                showContentView();
                this.mWebView.loadUrl(webUrl);
                return;
            }
            showErrorView();
            return;
        }
        Logger.t(TAG).i("mModel  is  null , showErrorView", new Object[0]);
        showErrorView();
    }

    private void initWebView() {
        if (this.mWebView != null || getActivity() == null) {
            return;
        }
        WebView webView = new WebView(getActivity());
        this.mWebView = webView;
        webView.setVerticalFadingEdgeEnabled(false);
        this.mWebView.setBackgroundColor(ResUtils.getColor(R.color.transparent));
        this.mWebView.setWebViewClient(new WebViewClient() { // from class: com.xiaopeng.appstore.appstore_ui.fragment.PrivatePolicyFragment.1
            @Override // android.webkit.WebViewClient
            public void onReceivedSslError(WebView webView2, SslErrorHandler sslErrorHandler, SslError sslError) {
                sslErrorHandler.proceed();
            }
        });
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebViewLayout.setClipToOutline(true);
        this.mWebViewLayout.addView(this.mWebView);
        this.mWebViewLayout.setVisibility(0);
    }

    private void releaseWebView() {
        WebView webView;
        ViewGroup viewGroup = this.mWebViewLayout;
        if (viewGroup != null && (webView = this.mWebView) != null) {
            viewGroup.removeView(webView);
        }
        WebView webView2 = this.mWebView;
        if (webView2 != null) {
            webView2.clearHistory();
            this.mWebView.removeAllViews();
            this.mWebView.destroy();
            this.mWebView = null;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        releaseWebView();
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        getRootActivity().setCanNavBack(true);
    }

    private void showContentView() {
        View view = this.mLineView;
        if (view != null) {
            view.setVisibility(8);
        }
        this.mErrorView.setVisibility(8);
        WebView webView = this.mWebView;
        if (webView != null) {
            webView.setVisibility(0);
        }
    }

    private void showErrorView() {
        View view = this.mLineView;
        if (view != null) {
            view.setVisibility(0);
        }
        this.mErrorView.setVisibility(0);
        WebView webView = this.mWebView;
        if (webView != null) {
            webView.setVisibility(4);
        }
    }
}
