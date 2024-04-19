package com.xiaopeng.appstore.ui_miniprog.miniprog.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.applet_ui.R;
import com.xiaopeng.appstore.bizcommon.logic.IMiniProgramListener;
import com.xiaopeng.appstore.bizcommon.logic.ProgressHolder;
import com.xiaopeng.appstore.bizcommon.logic.XuiMgrHelper;
import com.xiaopeng.appstore.bizcommon.logic.silentoperation.util.AlipayEnginePrepareHelper;
import com.xiaopeng.appstore.common_ui.BaseTabFragment;
import com.xiaopeng.appstore.common_ui.common.Resource;
import com.xiaopeng.appstore.common_ui.common.adapter.AdapterData;
import com.xiaopeng.appstore.common_ui.icon.AppIconView;
import com.xiaopeng.appstore.common_ui.icon.OnLauncherResumeCallback;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
import com.xiaopeng.appstore.libcommon.utils.Utils;
import com.xiaopeng.appstore.ui_miniprog.miniprog.adatper.MiniProgramListAdapter;
import com.xiaopeng.appstore.ui_miniprog.miniprog.fragment.MiniProgramFragment;
import com.xiaopeng.appstore.ui_miniprog.miniprog.viewmodel.MiniProgramViewModel;
import com.xiaopeng.appstore.xpcommon.CarUtils;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventEnum;
import com.xiaopeng.appstore.xpcommon.eventtracking.EventTrackingHelper;
import com.xiaopeng.appstore.xpcommon.eventtracking.PagesEnum;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.app.XToast;
import com.xiaopeng.xuimanager.xapp.MiniProgramResponse;
import java.util.List;
/* loaded from: classes2.dex */
public class MiniProgramFragment extends BaseTabFragment implements MiniProgramListAdapter.OnItemListener, IMiniProgramListener {
    private static int COUNTS_PER_ROW = 0;
    private static final String FORMAT_REMAIN = ResUtils.getString(R.string.alipay_progress_desc);
    private static final String TAG = "MiniProgramFragment";
    public static final long TIME_INTERVAL = 2000;
    private AppIconView currentIcon;
    private MiniProgramListAdapter mAdapter;
    private ImageView mAvatar;
    private String mAvatarUrl;
    private View mErrorLayout;
    private ImageView mLoadingView;
    private ViewGroup mLoginAlipayViewGroup;
    private TextView mNickName;
    private OnLauncherResumeCallback mOnLauncherResumeCallback;
    private ProgressBar mProgressBar;
    private View mProgressLayout;
    private RecyclerView mRecyclerView;
    private TextView mRemainText;
    private Button mRetryBtn;
    private boolean mShowingProgress;
    private MiniProgramViewModel mViewModel;
    private long mPageStayStart = 0;
    private long mLastClickTime = 0;
    private boolean mShouldReset = false;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$initViewStubContent$0(View view) {
    }

    public void setOnLauncherResumeCallback(OnLauncherResumeCallback callback) {
        OnLauncherResumeCallback onLauncherResumeCallback = this.mOnLauncherResumeCallback;
        if (onLauncherResumeCallback != null) {
            onLauncherResumeCallback.onLauncherResume();
        }
        this.mOnLauncherResumeCallback = callback;
    }

    @Override // com.xiaopeng.appstore.common_ui.BaseTabFragment
    protected int getLayoutViewStub() {
        return R.layout.fragment_mini_prog;
    }

    @Override // com.xiaopeng.appstore.common_ui.BaseTabFragment
    protected void initOtherView(View view) {
        this.mLoadingView = (ImageView) view.findViewById(R.id.loading_layout);
        COUNTS_PER_ROW = Utils.getApp().getResources().getInteger(R.integer.store_app_icon_column_size);
    }

    @Override // com.xiaopeng.appstore.common_ui.BaseTabFragment
    protected ViewStub getViewStub(View view) {
        return (ViewStub) view.findViewById(R.id.view_stub);
    }

    @Override // com.xiaopeng.appstore.common_ui.BaseTabFragment
    protected boolean isCurrentTab(int index, Class<?> selectedFragmentClz) {
        return selectedFragmentClz == MiniProgramFragment.class;
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mViewModel = (MiniProgramViewModel) new ViewModelProvider(this).get(MiniProgramViewModel.class);
        subscribeViewModel();
    }

    @Override // com.xiaopeng.appstore.common_ui.BaseBizFragment, com.xiaopeng.appstore.common_ui.BizRootActivity.OnActivityEventListener
    public void onWindowReset() {
        this.mShouldReset = true;
        ImageView imageView = this.mLoadingView;
        if (imageView != null) {
            imageView.setVisibility(0);
        }
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null) {
            recyclerView.scrollToPosition(0);
        }
    }

    @Override // com.xiaopeng.appstore.common_ui.BaseTabFragment, com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        String str = TAG;
        Logger.t(str).i("MiniProgramFragment onResume Start", new Object[0]);
        super.onResume();
        this.mPageStayStart = System.currentTimeMillis();
        this.mRootActivity.setCanNavBack(false);
        setOnLauncherResumeCallback(null);
        XuiMgrHelper.get().addMiniProgramListener(this);
        Logger.t(str).i("MiniProgramFragment onResume End", new Object[0]);
    }

    @Override // com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onPause() {
        String str = TAG;
        Logger.t(str).i("MiniProgramFragment onPause Start", new Object[0]);
        super.onPause();
        EventTrackingHelper.sendMolecast(PagesEnum.STORE_APP_PANEL, EventEnum.MAIN_ENTRANCE, 3, Long.valueOf(System.currentTimeMillis() - this.mPageStayStart));
        XuiMgrHelper.get().removeMiniProgramListener(this);
        Logger.t(str).i("MiniProgramFragment onPause End", new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.appstore.common_ui.BaseTabFragment
    public boolean inflateViewStub() {
        boolean inflateViewStub = super.inflateViewStub();
        Logger.t(TAG).d("inflateViewStub, result = " + inflateViewStub);
        if (!inflateViewStub) {
            reloadData();
        }
        return inflateViewStub;
    }

    @Override // com.xiaopeng.appstore.common_ui.BaseTabFragment
    protected void initViewStubContent(View inflatedViewStub) {
        Logger.t(TAG).d("initViewStubContent");
        this.mRecyclerView = (RecyclerView) inflatedViewStub.findViewById(R.id.root_layout);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), COUNTS_PER_ROW);
        gridLayoutManager.setSpanSizeLookup(new GridSpanSizer());
        this.mRecyclerView.setLayoutManager(gridLayoutManager);
        this.mRecyclerView.getRecycledViewPool().setMaxRecycledViews(1, 50);
        MiniProgramListAdapter miniProgramListAdapter = new MiniProgramListAdapter(this);
        this.mAdapter = miniProgramListAdapter;
        this.mRecyclerView.setAdapter(miniProgramListAdapter);
        this.mProgressLayout = inflatedViewStub.findViewById(R.id.progress_layout);
        this.mProgressBar = (ProgressBar) inflatedViewStub.findViewById(R.id.progress_bar);
        this.mRemainText = (TextView) inflatedViewStub.findViewById(R.id.tv_remain);
        this.mErrorLayout = inflatedViewStub.findViewById(R.id.layout_error);
        Button button = (Button) inflatedViewStub.findViewById(R.id.btn_retry);
        this.mRetryBtn = button;
        button.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.appstore.ui_miniprog.miniprog.fragment.-$$Lambda$MiniProgramFragment$6U0KqIanHAhQO__6jWPVs_V6XkY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MiniProgramFragment.lambda$initViewStubContent$0(view);
            }
        });
        this.mLoginAlipayViewGroup = (ViewGroup) inflatedViewStub.findViewById(R.id.btn_alipay_authorize);
        this.mAvatar = (ImageView) inflatedViewStub.findViewById(R.id.alipay_avatar);
        this.mNickName = (TextView) inflatedViewStub.findViewById(R.id.alipay_nickname);
        this.mLoginAlipayViewGroup.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.appstore.ui_miniprog.miniprog.fragment.-$$Lambda$MiniProgramFragment$_XrEoqO0jYOsb4SIZKFNDyIGJzw
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MiniProgramFragment.lambda$initViewStubContent$1(view);
            }
        });
        XuiMgrHelper.get().requestLoginInfo();
        attachProgressListener();
        this.mViewModel.loadData();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$initViewStubContent$1(View view) {
        EventTrackingHelper.sendMolecast(PagesEnum.STORE_MINI_PROGRAM_LIST, EventEnum.LOGOUT_ALIPAY_ACCOUNT, EventTrackingHelper.EVENT_SOURCE_FOR_MINI_PROGRAM);
        XuiMgrHelper.get().exitMini();
        XuiMgrHelper.get().logoutMini();
        if (CarUtils.isD55()) {
            XToast.showShort(ResUtils.getString(R.string.exit_the_account));
        }
    }

    private void reloadData() {
        Logger.t(TAG).d("reloadData, shouldReset = " + this.mShouldReset);
        if (this.mShouldReset) {
            this.mShouldReset = false;
            MiniProgramViewModel miniProgramViewModel = this.mViewModel;
            if (miniProgramViewModel != null) {
                miniProgramViewModel.loadData();
                XuiMgrHelper.get().requestLoginInfo();
            }
        }
    }

    @Override // com.xiaopeng.appstore.common_ui.BaseBizFragment, com.xiaopeng.appstore.common_ui.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        String str = TAG;
        Logger.t(str).i("MiniProgramFragment onDestroy Start", new Object[0]);
        super.onDestroy();
        release();
        Logger.t(str).i("MiniProgramFragment onDestroy End", new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.appstore.ui_miniprog.miniprog.fragment.MiniProgramFragment$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class AnonymousClass1 implements ProgressHolder.OnProgressChangeListener {
        AnonymousClass1() {
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.ProgressHolder.OnProgressChangeListener
        public void onStartProgress() {
            MiniProgramFragment.this.mShowingProgress = true;
            MiniProgramFragment.this.mErrorLayout.setVisibility(8);
            MiniProgramFragment.this.mRecyclerView.setVisibility(8);
            MiniProgramFragment.this.mProgressLayout.setVisibility(0);
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.ProgressHolder.OnProgressChangeListener
        public void onProgressChange(final float progress, final long remainTime) {
            if (MiniProgramFragment.this.mProgressLayout.getVisibility() != 0) {
                return;
            }
            MiniProgramFragment.this.requireActivity().runOnUiThread(new Runnable() { // from class: com.xiaopeng.appstore.ui_miniprog.miniprog.fragment.-$$Lambda$MiniProgramFragment$1$CsbRi6GbMeAxmhWaAYoaiMhLZto
                @Override // java.lang.Runnable
                public final void run() {
                    MiniProgramFragment.AnonymousClass1.this.lambda$onProgressChange$0$MiniProgramFragment$1(progress, remainTime);
                }
            });
        }

        public /* synthetic */ void lambda$onProgressChange$0$MiniProgramFragment$1(float f, long j) {
            if (f > 0.0f) {
                MiniProgramFragment.this.mProgressBar.setIndeterminate(false);
                MiniProgramFragment.this.mProgressBar.setProgress(Math.round(f * 100.0f));
            } else {
                MiniProgramFragment.this.mProgressBar.setIndeterminate(true);
            }
            MiniProgramFragment.this.mRemainText.setText(String.format(MiniProgramFragment.FORMAT_REMAIN, MiniProgramFragment.this.surplusTime(j / 1000)));
            MiniProgramFragment.this.mRecyclerView.setVisibility(8);
        }

        @Override // com.xiaopeng.appstore.bizcommon.logic.ProgressHolder.OnProgressChangeListener
        public void onFinish(final boolean success) {
            MiniProgramFragment.this.mShowingProgress = false;
            MiniProgramFragment.this.requireActivity().runOnUiThread(new Runnable() { // from class: com.xiaopeng.appstore.ui_miniprog.miniprog.fragment.-$$Lambda$MiniProgramFragment$1$KOMiHuZ2pLIJIWq7bOMoAXS-Prk
                @Override // java.lang.Runnable
                public final void run() {
                    MiniProgramFragment.AnonymousClass1.this.lambda$onFinish$1$MiniProgramFragment$1(success);
                }
            });
        }

        public /* synthetic */ void lambda$onFinish$1$MiniProgramFragment$1(boolean z) {
            Logger.t(MiniProgramFragment.TAG).d("progress on finished callback, success = " + z);
            if (z) {
                MiniProgramFragment.this.hideError();
                MiniProgramFragment.this.mProgressLayout.setVisibility(8);
                MiniProgramFragment.this.mRecyclerView.setVisibility(0);
                return;
            }
            MiniProgramFragment.this.showError();
        }
    }

    private void attachProgressListener() {
        AlipayEnginePrepareHelper.getInstance().sProgressHolder.setOnProgressChangeListener(new AnonymousClass1());
    }

    private void release() {
        AlipayEnginePrepareHelper.getInstance().sProgressHolder.setOnProgressChangeListener(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showError() {
        this.mErrorLayout.setVisibility(0);
        this.mProgressLayout.setVisibility(8);
        this.mRecyclerView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideError() {
        this.mErrorLayout.setVisibility(8);
    }

    private void subscribeViewModel() {
        MiniProgramViewModel miniProgramViewModel = this.mViewModel;
        if (miniProgramViewModel == null) {
            Logger.t(TAG).w("subscribeViewModel, viewModel is null", new Object[0]);
        } else {
            miniProgramViewModel.getListLiveData().observe(getViewLifecycleOwner(), new Observer() { // from class: com.xiaopeng.appstore.ui_miniprog.miniprog.fragment.-$$Lambda$MiniProgramFragment$tMRoSfKkSkOmZxwWx8SswpJYPAU
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    MiniProgramFragment.this.lambda$subscribeViewModel$2$MiniProgramFragment((Resource) obj);
                }
            });
        }
    }

    public /* synthetic */ void lambda$subscribeViewModel$2$MiniProgramFragment(Resource resource) {
        if (resource.status == Resource.Status.SUCCESS) {
            List<AdapterData<?>> list = (List) resource.data;
            if (list == null || list.isEmpty()) {
                Logger.t(TAG).i("viewModel changed, success, but data is empty:" + list, new Object[0]);
                return;
            }
            String str = TAG;
            Logger.t(str).i("viewModel changed, success, data size:" + list.size(), new Object[0]);
            if (!this.mShowingProgress) {
                ImageView imageView = this.mLoadingView;
                if (imageView != null) {
                    imageView.setVisibility(8);
                }
                RecyclerView recyclerView = this.mRecyclerView;
                if (recyclerView != null) {
                    recyclerView.setVisibility(0);
                } else {
                    Logger.t(str).e("Rv is null, when success liveData notify", new Object[0]);
                }
            } else {
                Logger.t(str).i("mini program list data update finished. But showing engine prepare view now.Will show list after progress finished", new Object[0]);
            }
            MiniProgramListAdapter miniProgramListAdapter = this.mAdapter;
            if (miniProgramListAdapter != null) {
                miniProgramListAdapter.clearData();
                this.mAdapter.addAll(list);
                this.mAdapter.notifyDataSetChanged();
                return;
            }
            Logger.t(str).w("subscribeViewModel, adapter is null", new Object[0]);
        } else if (resource.status == Resource.Status.LOADING) {
            String str2 = TAG;
            Logger.t(str2).i("viewModel changed, loading, res:" + resource, new Object[0]);
            ImageView imageView2 = this.mLoadingView;
            if (imageView2 != null) {
                imageView2.setVisibility(0);
            }
            RecyclerView recyclerView2 = this.mRecyclerView;
            if (recyclerView2 != null) {
                recyclerView2.setVisibility(8);
            } else {
                Logger.t(str2).w("Rv is null, when loading liveData notify", new Object[0]);
            }
        } else {
            Logger.t(TAG).i("viewModel changed, not handled status, res:" + resource, new Object[0]);
        }
    }

    @Override // com.xiaopeng.appstore.ui_miniprog.miniprog.adatper.MiniProgramListAdapter.OnItemListener
    public void onItemClick(AppIconView icon, String miniAppId, String name) {
        String str = TAG;
        Logger.t(str).i("onItemClick " + miniAppId + " " + name, new Object[0]);
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.mLastClickTime > TIME_INTERVAL) {
            this.mLastClickTime = currentTimeMillis;
            icon.setStayPressed(true);
            this.currentIcon = icon;
            MiniProgramViewModel miniProgramViewModel = this.mViewModel;
            if (miniProgramViewModel != null) {
                miniProgramViewModel.launch(miniAppId, name);
                return;
            }
            return;
        }
        Logger.t(str).d("not fast click ...");
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (ThemeManager.isThemeChanged(newConfig)) {
            Logger.t(TAG).d("MiniProgramFragment onConfigurationChanged theme");
            loadAvatarUrl(this.mAvatarUrl);
        }
    }

    private void loadAvatarUrl(String url) {
        if (getActivity() == null || this.mAvatar == null || TextUtils.isEmpty(url)) {
            return;
        }
        Glide.with(getActivity()).load(url).apply((BaseRequestOptions<?>) RequestOptions.bitmapTransform(new CircleCrop())).into(this.mAvatar);
    }

    @Override // com.xiaopeng.appstore.bizcommon.logic.IMiniProgramListener
    public void onMiniProgramCallBack(int type, MiniProgramResponse response) {
        if (type != 0) {
            if (type != 6) {
                if (type == 2) {
                    if (this.mLoginAlipayViewGroup == null || getRootActivity().isClose() || response == null) {
                        return;
                    }
                    if (response.getCode() == 0) {
                        showAlipayInfo(false, "", getString(R.string.alipay_authorize_text));
                        return;
                    } else {
                        showAlipayInfo(true, response.getUserAvatar(), response.getUserNick());
                        return;
                    }
                } else if (type != 3) {
                    return;
                }
            }
            doLogic(type, response);
            return;
        }
        XuiMgrHelper.get().requestLoginInfo();
    }

    private void doLogic(int type, MiniProgramResponse response) {
        AppIconView appIconView = this.currentIcon;
        if (appIconView != null) {
            appIconView.setStayPressed(false);
            this.currentIcon = null;
        }
        if (response == null || getRootActivity().isClose()) {
            return;
        }
        if (type == 6) {
            if (response.isLogin()) {
                showAlipayInfo(true, response.getUserAvatar(), response.getUserNick());
            } else {
                showAlipayInfo(false, "", getString(R.string.alipay_authorize_text));
            }
            Logger.t(TAG).d("doLogic response=>" + response.toString());
            if (response.isLogin() || TextUtils.isEmpty(response.getMiniAppId())) {
                return;
            }
        }
        if (response.getCode() == 0) {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.HOME");
            startActivity(intent);
            getRootActivity().moveTaskToBack();
            setOnLauncherResumeCallback(this.currentIcon);
        } else if (!TextUtils.isEmpty(response.getMessage())) {
            XToast.show(getString(R.string.applet_open_error));
        } else {
            Logger.t(TAG).w("doLogic resp error:" + response, new Object[0]);
        }
    }

    private void showAlipayInfo(boolean isVisible, String avatarUrl, String name) {
        ViewGroup viewGroup = this.mLoginAlipayViewGroup;
        if (viewGroup != null) {
            viewGroup.setVisibility(isVisible ? 0 : 8);
        }
        this.mAvatarUrl = avatarUrl;
        loadAvatarUrl(avatarUrl);
        if (this.mNickName != null) {
            if (TextUtils.isEmpty(name)) {
                name = getString(R.string.alipay_authorize_text);
            }
            this.mNickName.setText(name);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String surplusTime(long seconds) {
        if (seconds < 0) {
            return getString(R.string.downlond_remaining_time_none);
        }
        long j = seconds / 3600;
        long j2 = seconds % 3600;
        long j3 = j2 / 60;
        long j4 = j2 % 60;
        if (j > 12) {
            return getString(R.string.downlond_remaining_time_greater_hour12);
        }
        return j > 1 ? getString(R.string.downlond_remaining_time_greater_hour1, Long.valueOf(j), Long.valueOf(j3), Long.valueOf(j4)) : j3 > 0 ? getString(R.string.downlond_remaining_time_greater_minute1, Long.valueOf(j3), Long.valueOf(j4)) : getString(R.string.downlond_remaining_time_minute0, Long.valueOf(j4));
    }

    /* loaded from: classes2.dex */
    private class GridSpanSizer extends GridLayoutManager.SpanSizeLookup {
        public GridSpanSizer() {
            setSpanIndexCacheEnabled(true);
        }

        @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
        public int getSpanSize(int position) {
            int itemViewType = MiniProgramFragment.this.mAdapter.getItemViewType(position);
            if (itemViewType == 2 || itemViewType == 3) {
                return MiniProgramFragment.COUNTS_PER_ROW;
            }
            return 1;
        }
    }
}
