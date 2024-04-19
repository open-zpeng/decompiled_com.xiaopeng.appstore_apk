package com.xiaopeng.appstore.applist_ui.adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.applist_biz.model.AppGroupItemModel;
import com.xiaopeng.appstore.applist_biz.model.BaseAppItem;
import com.xiaopeng.appstore.applist_biz.model.FixedAppItem;
import com.xiaopeng.appstore.applist_biz.model.LoadingAppItem;
import com.xiaopeng.appstore.applist_biz.model.NapaAppItem;
import com.xiaopeng.appstore.applist_biz.model.NormalAppItem;
import com.xiaopeng.appstore.applist_ui.R;
import com.xiaopeng.appstore.applist_ui.view.IconAnimHelper;
import com.xiaopeng.appstore.bizcommon.entities.AssembleDataContainer;
import com.xiaopeng.appstore.bizcommon.entities.PackageUserKey;
import com.xiaopeng.appstore.bizcommon.logic.AppStoreAssembleManager;
import com.xiaopeng.appstore.bizcommon.logic.ItemAssembleObserver;
import com.xiaopeng.appstore.bizcommon.logic.SimpleItemAssembleObserver;
import com.xiaopeng.appstore.bizcommon.logic.XuiMgrHelper;
import com.xiaopeng.appstore.bizcommon.logic.app.AppComponentManager;
import com.xiaopeng.appstore.bizcommon.utils.AccountUtils;
import com.xiaopeng.appstore.bizcommon.utils.LogicUtils;
import com.xiaopeng.appstore.common_ui.common.adapter.PayloadData;
import com.xiaopeng.appstore.common_ui.icon.AppIconView;
import com.xiaopeng.appstore.common_ui.logic.UIConfig;
import com.xiaopeng.appstore.libcommon.utils.ImageUtils;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
import com.xiaopeng.appstore.xpcommon.CarUtils;
import com.xiaopeng.appstore.xpcommon.VuiEngineUtils;
import com.xiaopeng.vui.commons.VuiMode;
import com.xiaopeng.xui.widget.XLinearLayout;
import com.xiaopeng.xui.widget.XTextView;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
/* loaded from: classes2.dex */
public class AppIconViewHolder extends BaseAppListViewHolder<AppGroupItemModel> implements IAppHolder, IconAnimHelper.OnAnimateListener {
    private static final int APP_NAME_MAX_LENGTH = 28;
    private static final int APP_NAME_MAX_LINE = 2;
    protected static final float GROUP_DRAG_ALPHA = 0.3f;
    protected static final long GROUP_FADE_IN_ANIM_DURATION = 150;
    protected static final long GROUP_FADE_OUT_ANIM_DURATION = 200;
    protected static final long ITEM_RESET_ANIM_DURATION = 300;
    protected static final float MAX_ROTATE_DEGREE = 3.5f;
    protected static final int MSG_PROGRESS_CHANGED = 100;
    protected static final int MSG_STATE_CHANGED = 200;
    protected static final String TAG = "IconVH";
    private static final String VUI_ACTION = "Click";
    protected AppGroupItemModel mAppGroupItemModel;
    protected BaseAppItem mAppItem;
    protected final XTextView mAppNameTv;
    protected int mAssembleState;
    private int mDraggingGroup;
    private final Handler mHandler;
    private final IconAnimHelper mIconAnimHelper;
    protected final AppIconView mIconIv;
    protected final View mIconLayout;
    private boolean mIsDeleting;
    private boolean mIsDragging;
    private boolean mIsInEdit;
    private boolean mIsInternational;
    private boolean mIsLoading;
    private final ItemAssembleObserver mItemAssembleObserver;
    private OnAppInteractCallback mOnAppInteractCallback;
    protected ProgressBar mProgressBar;
    protected final ViewStub mProgressBarViewStub;
    private float mRotateDegree;
    protected final XTextView mUnreadTv;
    private final XLinearLayout mXLinearLayout;
    protected static final String DOWNLOADING_DESC = ResUtils.getString(R.string.icon_downloading_desc);
    protected static final String DELETING_DESC = ResUtils.getString(R.string.icon_uninstalling_desc);
    protected static final String UPDATING_DESC = ResUtils.getString(R.string.icon_upgrading_desc);
    protected static final String INSTALLING_DESC = ResUtils.getString(R.string.icon_installing_desc);
    protected static final String PAUSE_DESC = ResUtils.getString(R.string.icon_pause_desc);
    protected static final int sIconSize = ResUtils.getDimenPixel(R.dimen.icon_size);

    public static boolean isLoading(int state) {
        return state == 1 || state == 3 || state == 5 || state == 10001;
    }

    @Override // com.xiaopeng.appstore.applist_ui.view.IconAnimHelper.OnAnimateListener
    public void onAnimateCancel(float value) {
    }

    @Override // com.xiaopeng.appstore.applist_ui.view.IconAnimHelper.OnAnimateListener
    public void onAnimateStart() {
    }

    public AppIconViewHolder(ViewGroup parent) {
        super(parent, R.layout.layout_app_list_appitem);
        this.mIsInternational = false;
        this.mIsLoading = false;
        this.mIsInEdit = false;
        this.mIsDragging = false;
        this.mIsDeleting = false;
        this.mDraggingGroup = -1;
        this.mItemAssembleObserver = new SimpleItemAssembleObserver() { // from class: com.xiaopeng.appstore.applist_ui.adapter.AppIconViewHolder.1
            @Override // com.xiaopeng.appstore.bizcommon.logic.SimpleItemAssembleObserver, com.xiaopeng.appstore.bizcommon.logic.ItemAssembleObserver
            public void onStateChange(AssembleDataContainer data) {
                super.onStateChange(data);
                if (data == null) {
                    return;
                }
                int state = data.getState();
                Logger.t(AppIconViewHolder.TAG).i("onStateChange:" + AppStoreAssembleManager.stateToStr(state) + ", " + data.getAppLabel(), new Object[0]);
                AppIconViewHolder.this.mHandler.obtainMessage(200, state, 0).sendToTarget();
            }

            @Override // com.xiaopeng.appstore.bizcommon.logic.SimpleItemAssembleObserver, com.xiaopeng.appstore.bizcommon.logic.ItemAssembleObserver
            public void onProgressChange(AssembleDataContainer data) {
                super.onProgressChange(data);
                AppIconViewHolder.this.mHandler.obtainMessage(100, data).sendToTarget();
            }
        };
        this.mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.xiaopeng.appstore.applist_ui.adapter.-$$Lambda$AppIconViewHolder$P_RKlUwc4s9h5Qp9Uo8mq3iYVtk
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                return AppIconViewHolder.this.lambda$new$0$AppIconViewHolder(message);
            }
        });
        this.mIconAnimHelper = IconAnimHelper.IconAnimHelperGenerator.get().obtain();
        XTextView xTextView = (XTextView) this.itemView.findViewById(R.id.tv_app_name);
        this.mAppNameTv = xTextView;
        xTextView.setVuiMode(VuiMode.DISABLED);
        XLinearLayout xLinearLayout = (XLinearLayout) this.itemView.findViewById(R.id.xLinearLayout);
        this.mXLinearLayout = xLinearLayout;
        xLinearLayout.setVuiAction(VUI_ACTION);
        VuiEngineUtils.addHasFeedbackProp(xLinearLayout);
        this.mProgressBarViewStub = (ViewStub) this.itemView.findViewById(R.id.progress_bar_view_stub);
        this.mIconIv = (AppIconView) this.itemView.findViewById(R.id.iv_icon);
        XTextView xTextView2 = (XTextView) this.itemView.findViewById(R.id.tv_unread);
        this.mUnreadTv = xTextView2;
        xTextView2.setVuiMode(VuiMode.DISABLED);
        this.mIconLayout = this.itemView.findViewById(R.id.layout_icon);
        boolean isEURegion = CarUtils.isEURegion();
        this.mIsInternational = isEURegion;
        changeAppNameTvStyle(xTextView, isEURegion);
    }

    public /* synthetic */ boolean lambda$new$0$AppIconViewHolder(Message message) {
        int i = message.what;
        if (i == 100) {
            setProgress(((AssembleDataContainer) message.obj).getProgress());
            return true;
        } else if (i == 200) {
            onStateChanged(message.arg1);
            return false;
        } else {
            return false;
        }
    }

    private void changeAppNameTvStyle(TextView textView, boolean isInternational) {
        if (isInternational) {
            Logger.t(TAG).d("car mode is eu");
            textView.setSingleLine(false);
            textView.setHeight(this.mAppNameTv.getLineHeight() * 2);
            textView.setMaxLines(2);
            return;
        }
        textView.setSingleLine(true);
    }

    public AppIconView getIconView() {
        return this.mIconIv;
    }

    public boolean isDeleting() {
        return this.mIsDeleting;
    }

    public void viewOnDragging() {
        this.mIsDragging = true;
        itemAnimReset();
    }

    public void viewOnDrop() {
        this.mIsDragging = false;
    }

    private void setAlpha(float alpha) {
        this.mIconLayout.setAlpha(alpha);
        this.mAppNameTv.setAlpha(alpha);
        ProgressBar progressBar = this.mProgressBar;
        if (progressBar != null) {
            progressBar.setAlpha(alpha);
        }
    }

    private void animationAlpha(float alpha, long duration) {
        this.mIconLayout.animate().alpha(alpha).setDuration(duration).start();
        this.mAppNameTv.animate().alpha(alpha).setDuration(duration).start();
        ProgressBar progressBar = this.mProgressBar;
        if (progressBar != null) {
            progressBar.animate().alpha(alpha).setDuration(duration).start();
        }
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.BaseAppListViewHolder
    public void onViewRecycled() {
        Logger.t(TAG).i("AppVH onViewRecycled " + this.mAppItem.packageName + " " + this, new Object[0]);
        AppStoreAssembleManager.get().removeSoftObserver(this.mAppItem.packageName, this.mItemAssembleObserver);
        IconAnimHelper iconAnimHelper = this.mIconAnimHelper;
        if (iconAnimHelper != null) {
            iconAnimHelper.stop();
            this.mIconAnimHelper.unregisterListener(this);
        }
    }

    private void onStateChanged(int state) {
        Logger.t(TAG).d("onStateChanged:" + state + ", " + this.mAppItem);
        this.mIsDeleting = false;
        this.mIsLoading = false;
        boolean z = state == 3;
        if ((state == 1) || z || state == 5 || state == 10001) {
            loadStart(z);
        } else if (state == 8) {
            this.mIsDeleting = true;
            this.mIconAnimHelper.stop();
            itemAnimReset();
            this.itemView.setOnClickListener(null);
            hideProgressBar();
            this.mAppNameTv.setText(DELETING_DESC);
            setDeleteBtnVisibility(false);
            refreshNameIndicator(this.mAppItem);
        } else {
            loadFinish();
        }
    }

    public void setInEdit(boolean inEdit) {
        this.mIsInEdit = inEdit;
        switchEditMode();
    }

    public void setDraggingGroup(int group) {
        this.mDraggingGroup = group;
        refreshAlpha(false);
    }

    private void switchEditMode() {
        if (this.mIsInEdit) {
            enterEdit();
        } else {
            exitEdit();
        }
    }

    private void hideProgressBar() {
        ProgressBar progressBar = this.mProgressBar;
        if (progressBar != null) {
            progressBar.setVisibility(8);
        }
    }

    private void showProgressBar() {
        showProgressBar(true);
    }

    private void showProgressBar(boolean indeterminate) {
        tryInitProgressBar();
        ProgressBar progressBar = this.mProgressBar;
        if (progressBar != null) {
            boolean z = false;
            progressBar.setVisibility(0);
            if (this.mProgressBar.getProgress() == 0 && indeterminate) {
                z = true;
            }
            this.mProgressBar.setIndeterminate(z);
        }
    }

    private void setProgressBarIndeterminate(boolean indeterminate) {
        ProgressBar progressBar = this.mProgressBar;
        if (progressBar != null) {
            progressBar.setIndeterminate(indeterminate);
        }
    }

    private void setProgress(int progress) {
        ProgressBar progressBar = this.mProgressBar;
        if (progressBar != null) {
            progressBar.setProgress(progress);
        }
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.BaseAppListViewHolder
    public void setData(AppGroupItemModel appGroupItemModel) {
        this.mIsLoading = false;
        this.mAppGroupItemModel = appGroupItemModel;
        this.mAppItem = (BaseAppItem) appGroupItemModel.data;
        IconAnimHelper iconAnimHelper = this.mIconAnimHelper;
        if (iconAnimHelper != null) {
            iconAnimHelper.registerListener(this);
        }
        String str = this.mAppItem.packageName;
        AppStoreAssembleManager.get().removeSoftObserver(str, new Function() { // from class: com.xiaopeng.appstore.applist_ui.adapter.-$$Lambda$AppIconViewHolder$lBgWvuJqkw3XfoBOAAHJFjiCvR8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AppIconViewHolder.this.lambda$setData$1$AppIconViewHolder((ItemAssembleObserver) obj);
            }
        });
        AppStoreAssembleManager.get().addSoftObserver(str, this.mItemAssembleObserver);
        refreshState(this.mAppItem);
        refreshIcon(this.mAppItem);
        refreshTouchEvent(this.mAppItem);
        refreshAppDesc(this.mAppItem, this.mAssembleState);
    }

    public /* synthetic */ Boolean lambda$setData$1$AppIconViewHolder(ItemAssembleObserver itemAssembleObserver) {
        return Boolean.valueOf(itemAssembleObserver.getClass().equals(this.mItemAssembleObserver.getClass()));
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.BaseAppListViewHolder
    public void setData(AppGroupItemModel appGroupItemModel, PayloadData payloadData) {
        this.mAppGroupItemModel = appGroupItemModel;
        this.mAppItem = (BaseAppItem) appGroupItemModel.data;
        Logger.t(TAG).d("setDataWithPayloads payloadType=" + payloadData.type + " pos=" + getAdapterPosition());
        if (payloadData.type == 3) {
            setInEdit(((Boolean) payloadData.data).booleanValue());
            refreshTouchEvent(this.mAppItem);
        } else if (payloadData.type == 4) {
            BaseAppItem baseAppItem = this.mAppItem;
            if ((baseAppItem instanceof NormalAppItem) || (baseAppItem instanceof NapaAppItem)) {
                refreshIcon(baseAppItem);
            }
            refreshNameIndicator(this.mAppItem);
        } else if (payloadData.type == 5) {
            BaseAppItem baseAppItem2 = this.mAppItem;
            if (baseAppItem2 instanceof NormalAppItem) {
                NormalAppItem normalAppItem = (NormalAppItem) baseAppItem2;
                if (((Set) payloadData.data).contains(new PackageUserKey(normalAppItem.packageName, normalAppItem.user))) {
                    refreshBadge(normalAppItem);
                }
            }
        } else if (payloadData.type == 6) {
            this.mDraggingGroup = ((Integer) payloadData.data).intValue();
            refreshAlpha(true);
        } else if (payloadData.type == 7) {
            setData((AppGroupItemModel) payloadData.data);
            switchEditMode();
        }
        if (payloadData.type == 8) {
            setData(this.mAppGroupItemModel);
        } else {
            int i = payloadData.type;
        }
    }

    protected void refreshBadge(BaseAppItem appItem) {
        setUnreadCount(0);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void refreshIcon(BaseAppItem appItem) {
        boolean z;
        Bitmap bitmap = appItem.iconBitmap;
        if (bitmap != null) {
            this.mIconIv.setIcon(bitmap);
        } else if (appItem instanceof NapaAppItem) {
            NapaAppItem napaAppItem = (NapaAppItem) appItem;
            try {
                if (napaAppItem.mXpAppIcon != null) {
                    boolean z2 = Build.VERSION.SDK_INT <= 28;
                    if (!CarUtils.isD55() && !CarUtils.isD55A()) {
                        z = false;
                        if (!z2 && !z) {
                            this.mIconIv.setImageBitmap(napaAppItem.mXpAppIcon);
                        }
                        this.mIconIv.setIcon(napaAppItem.mXpAppIcon);
                    }
                    z = true;
                    if (!z2) {
                        this.mIconIv.setImageBitmap(napaAppItem.mXpAppIcon);
                    }
                    this.mIconIv.setIcon(napaAppItem.mXpAppIcon);
                } else {
                    int resId = ResUtils.getResId(napaAppItem.resId, R.drawable.class);
                    if (resId > 0) {
                        Logger.t(TAG).w("icon load into resId :" + resId, new Object[0]);
                        this.mIconIv.setImageResource(resId);
                    }
                }
            } catch (Exception e) {
                this.mIconIv.setImageResource(R.drawable.icon_placeholder);
                Logger.t(TAG).w("icon load fail ：" + e.getMessage(), new Object[0]);
            }
        } else {
            refreshNoneBitmap(appItem);
        }
        if ((appItem instanceof NormalAppItem) || (appItem instanceof NapaAppItem)) {
            this.mIconIv.setHideMask(!this.mIsDeleting);
            refreshBadge(appItem);
        } else if (appItem instanceof LoadingAppItem) {
            this.mIconIv.setHideMask(false);
        } else if (appItem instanceof FixedAppItem) {
            this.mIconIv.setHideMask(true);
        }
    }

    protected void refreshNoneBitmap(BaseAppItem appItem) {
        String iconUrl;
        Logger.t(TAG).d("App(%s#%s) icon bitmap is null:" + appItem);
        if (appItem instanceof LoadingAppItem) {
            iconUrl = ((LoadingAppItem) appItem).iconUrl;
        } else {
            iconUrl = appItem instanceof FixedAppItem ? ((FixedAppItem) appItem).getIconUrl() : null;
        }
        String str = iconUrl;
        if (!TextUtils.isEmpty(str)) {
            Drawable drawable = ResUtils.getDrawable(R.drawable.icon_placeholder);
            ImageUtils.loadAdaptiveIcon(this.mIconIv, str, drawable, drawable, sIconSize, UIConfig.iconEnableShadow());
            return;
        }
        this.mIconIv.setImageResource(R.drawable.icon_placeholder);
    }

    protected void refreshTouchEvent(final BaseAppItem appItem) {
        if (this.mOnAppInteractCallback != null) {
            this.mIconIv.setOnIconTouchListener(new AppIconView.OnIconTouchListener() { // from class: com.xiaopeng.appstore.applist_ui.adapter.AppIconViewHolder.2
                @Override // com.xiaopeng.appstore.common_ui.icon.AppIconView.OnIconTouchListener
                public void onDeleteTap() {
                    if (AppIconViewHolder.this.mOnAppInteractCallback == null || !XuiMgrHelper.get().isPrimaryScreen()) {
                        return;
                    }
                    AppIconViewHolder.this.mOnAppInteractCallback.onAppDeleteClick(appItem);
                }

                @Override // com.xiaopeng.appstore.common_ui.icon.AppIconView.OnIconTouchListener
                public void onIconTap() {
                    AppIconViewHolder.this.iconOnClick(appItem);
                }

                @Override // com.xiaopeng.appstore.common_ui.icon.AppIconView.OnIconTouchListener
                public void onIconDrag() {
                    if (AppIconViewHolder.this.mOnAppInteractCallback == null || !XuiMgrHelper.get().isPrimaryScreen()) {
                        return;
                    }
                    AppIconViewHolder.this.mOnAppInteractCallback.onIconDrag(AppIconViewHolder.this);
                }

                @Override // com.xiaopeng.appstore.common_ui.icon.AppIconView.OnIconTouchListener
                public boolean onIconLongClick() {
                    if (AppIconViewHolder.this.mOnAppInteractCallback == null || !XuiMgrHelper.get().isPrimaryScreen()) {
                        return false;
                    }
                    return AppIconViewHolder.this.mOnAppInteractCallback.onAppIconLongClick(AppIconViewHolder.this.itemView, appItem);
                }
            });
            this.mAppNameTv.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.appstore.applist_ui.adapter.-$$Lambda$AppIconViewHolder$3K26HAVBlkmoEgMLrirFwXEifjI
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AppIconViewHolder.this.lambda$refreshTouchEvent$2$AppIconViewHolder(appItem, view);
                }
            });
            return;
        }
        this.mIconIv.setOnIconTouchListener(null);
        this.mAppNameTv.setOnClickListener(null);
    }

    public /* synthetic */ void lambda$refreshTouchEvent$2$AppIconViewHolder(BaseAppItem baseAppItem, View view) {
        OnAppInteractCallback onAppInteractCallback = this.mOnAppInteractCallback;
        if (onAppInteractCallback != null) {
            onAppInteractCallback.onAppIconClick(this, baseAppItem);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean iconOnClick(BaseAppItem appItem) {
        Logger.t(TAG).i("onIconTap, item=" + appItem + " pos=" + getAdapterPosition() + " appName=" + ((Object) this.mAppNameTv.getText()), new Object[0]);
        if (this.mIsDeleting) {
            Logger.t(TAG).i("onIconTap ignore deleting item:" + appItem, new Object[0]);
        } else {
            OnAppInteractCallback onAppInteractCallback = this.mOnAppInteractCallback;
            if (onAppInteractCallback != null) {
                return onAppInteractCallback.onAppIconClick(this, appItem);
            }
        }
        return false;
    }

    public void refreshNameIndicator(BaseAppItem appItem) {
        int i;
        int i2;
        if (this.mIsInternational) {
            this.mAppNameTv.setSingleLine(false);
            XTextView xTextView = this.mAppNameTv;
            xTextView.setHeight(xTextView.getLineHeight() * 2);
            this.mAppNameTv.setMaxLines(2);
        }
        if (!this.mIsLoading && !this.mIsDeleting) {
            if (appItem instanceof NormalAppItem) {
                i = ((NormalAppItem) appItem).newInstalled ? R.drawable.drawable_new_app_indicator : 0;
                i2 = 0;
            } else if (appItem instanceof FixedAppItem) {
                if (!this.mIsInternational) {
                    this.mAppNameTv.setSingleLine(true);
                }
                i2 = R.drawable.app_preload_indicator;
                i = 0;
            }
            this.mAppNameTv.setCompoundDrawablesRelativeWithIntrinsicBounds(i, 0, i2, 0);
        }
        i = 0;
        i2 = 0;
        this.mAppNameTv.setCompoundDrawablesRelativeWithIntrinsicBounds(i, 0, i2, 0);
    }

    protected void refreshAlpha(boolean useAnim) {
        float f;
        long j;
        int i = this.mDraggingGroup;
        if (i == -1 || i == this.mAppGroupItemModel.groupId) {
            f = 1.0f;
            j = GROUP_FADE_OUT_ANIM_DURATION;
        } else {
            f = GROUP_DRAG_ALPHA;
            j = 150;
        }
        if (useAnim) {
            animationAlpha(f, j);
        } else {
            setAlpha(f);
        }
    }

    public void setOnAppInteractCallback(OnAppInteractCallback callback) {
        this.mOnAppInteractCallback = callback;
    }

    protected void refreshState(BaseAppItem appItem) {
        String str = appItem.packageName;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        int state = AppStoreAssembleManager.get().getState(str, null, null);
        this.mAssembleState = state;
        this.mIsLoading = isLoading(state);
        this.mIsDeleting = AppStoreAssembleManager.get().isDeleting(str);
    }

    public void loadStart(boolean isInstall) {
        this.mIsLoading = true;
        refreshNameIndicator(this.mAppItem);
        String str = DOWNLOADING_DESC;
        if (this.mAppItem instanceof NormalAppItem) {
            str = UPDATING_DESC;
        }
        if (isInstall) {
            str = INSTALLING_DESC;
            this.itemView.setOnClickListener(null);
            setDeleteBtnVisibility(false);
            hideProgressBar();
        } else {
            showProgressBar();
        }
        this.mAppNameTv.setText(str);
    }

    private void tryInitProgressBar() {
        if (this.mProgressBar == null) {
            Logger.t(TAG).i("initProgressBar, " + this.mAppItem, new Object[0]);
            this.mProgressBar = (ProgressBar) this.mProgressBarViewStub.inflate();
        }
    }

    public void loadPause() {
        this.mAppNameTv.setText(PAUSE_DESC);
    }

    public void loadFinish() {
        this.mIsLoading = false;
        refreshTouchEvent(this.mAppItem);
        refreshNameIndicator(this.mAppItem);
        BaseAppItem baseAppItem = this.mAppItem;
        if ((baseAppItem instanceof NormalAppItem) || (baseAppItem instanceof FixedAppItem)) {
            hideProgressBar();
            this.mAppNameTv.setText(this.mAppItem.title);
            return;
        }
        setProgressBarIndeterminate(true);
    }

    public void setProgress(float progress) {
        tryInitProgressBar();
        if (this.mProgressBar == null) {
            Logger.t(TAG).w("setProgress error progressBar is null:" + this.mAppItem, new Object[0]);
        } else if (progress <= 0.0f) {
            setProgressBarIndeterminate(true);
        } else {
            setProgressBarIndeterminate(false);
            setProgress((int) (progress * 100.0f));
        }
    }

    public void setDeleteBtnVisibility(boolean visible) {
        this.mIconIv.setShowDelete(visible);
    }

    public void setUnreadCount(int count) {
        if (count > 0) {
            this.mUnreadTv.setText(String.valueOf(count));
            this.mUnreadTv.setVisibility(0);
            return;
        }
        this.mUnreadTv.setVisibility(8);
    }

    public void itemAnimReset() {
        if (this.mIconLayout.getRotation() != 0.0f) {
            this.mIconLayout.animate().rotation(0.0f).setDuration(ITEM_RESET_ANIM_DURATION).start();
        }
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.IAppHolder
    public void enterEdit() {
        this.mIconAnimHelper.start();
        BaseAppItem baseAppItem = this.mAppItem;
        boolean z = false;
        if (baseAppItem instanceof NormalAppItem) {
            NormalAppItem normalAppItem = (NormalAppItem) baseAppItem;
            if (!AppComponentManager.get().isSystemApp(normalAppItem.intent) && !AppComponentManager.get().isInstalling(normalAppItem.packageName) && !AppStoreAssembleManager.get().isDeleting(normalAppItem.packageName)) {
                z = true;
            }
        } else if (!(baseAppItem instanceof NapaAppItem) && (baseAppItem instanceof LoadingAppItem)) {
            z = !AppComponentManager.get().isInstalling(((LoadingAppItem) baseAppItem).packageName);
        }
        this.mIconIv.setEditMode(true);
        setDeleteBtnVisibility(z);
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.IAppHolder
    public void exitEdit() {
        this.mIconAnimHelper.stop();
        this.mIconIv.setEditMode(false);
        setDeleteBtnVisibility(false);
        itemAnimReset();
    }

    @Override // com.xiaopeng.appstore.applist_ui.view.IconAnimHelper.OnAnimateListener
    public void onAnimateUpdate(float value) {
        if (this.mIsDragging || this.mIsDeleting) {
            return;
        }
        float f = (value / 50.0f) * MAX_ROTATE_DEGREE;
        this.mRotateDegree = f;
        this.mIconLayout.setRotation(f);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void refreshAppDesc(BaseAppItem appItem, int state) {
        this.mXLinearLayout.setVuiLabel(appItem.title.toString());
        if (this.mIsDeleting) {
            hideProgressBar();
            this.mAppNameTv.setText(DELETING_DESC);
        } else if (this.mIsLoading) {
            String str = appItem instanceof NormalAppItem ? UPDATING_DESC : DOWNLOADING_DESC;
            if (state == 3) {
                str = INSTALLING_DESC;
                hideProgressBar();
            } else {
                showProgressBar();
                setProgress(AppStoreAssembleManager.get().getProgress(appItem.packageName, null));
            }
            this.mAppNameTv.setText(str);
        } else {
            hideProgressBar();
            adjustTvStyle(this.mAppNameTv, appItem.title.toString());
            this.mAppNameTv.setText(appItem.title);
        }
        refreshNameIndicator(appItem);
    }

    private void adjustTvStyle(TextView textView, String content) {
        if (!this.mIsInternational || content.length() <= 28) {
            return;
        }
        Logger.t(TAG).d("App name is more than 28");
        textView.setEllipsize(TextUtils.TruncateAt.END);
    }

    public void onVuiEvent(XLinearLayout xLinearLayout) {
        BaseAppItem baseAppItem = this.mAppItem;
        if (baseAppItem instanceof FixedAppItem) {
            FixedAppItem fixedAppItem = (FixedAppItem) baseAppItem;
            if (AppStoreAssembleManager.get().isRunning(fixedAppItem.packageName)) {
                VuiEngineUtils.speakSliceByVuiEngine(xLinearLayout.getContext(), xLinearLayout);
                return;
            } else if (!AccountUtils.get().isLogin()) {
                Logger.t(TAG).i("tryEnterEdit, not login.", new Object[0]);
                VuiEngineUtils.speakSliceByVuiEngine(xLinearLayout.getContext(), xLinearLayout);
                AccountUtils.get().goLoginByService();
                return;
            } else if (LogicUtils.isDefaultSuspendStatus(AppStoreAssembleManager.get().getState(fixedAppItem.packageName, fixedAppItem.getDownloadUrl(), fixedAppItem.getConfigUrl()), fixedAppItem.isSuspended(), fixedAppItem.packageName)) {
                Logger.t(TAG).i("isDefaultSuspendStatus is true", new Object[0]);
                VuiEngineUtils.speakSliceByVuiEngine(xLinearLayout.getContext(), xLinearLayout);
                return;
            }
        }
        if (this.mIsInEdit) {
            VuiEngineUtils.speakExceptionByVuiEngine(xLinearLayout.getContext(), xLinearLayout, R.string.vui_engine_tips_exit_list, new Object[0]);
        } else {
            isLoadingStatus(xLinearLayout);
        }
    }

    private int getPositionByLabel(String name, List<AppGroupItemModel> appGroupItemModels) {
        for (int i = 0; i < appGroupItemModels.size(); i++) {
            AppGroupItemModel appGroupItemModel = appGroupItemModels.get(i);
            if (appGroupItemModel.type == 100 && (appGroupItemModel.data instanceof BaseAppItem) && ((BaseAppItem) appGroupItemModel.data).title.equals(name)) {
                return i;
            }
        }
        return -1;
    }

    private boolean isLogin(XLinearLayout xLinearLayout) {
        if (AccountUtils.get().isLogin()) {
            return true;
        }
        Logger.t(TAG).i("tryEnterEdit, not login.", new Object[0]);
        VuiEngineUtils.speakSliceByVuiEngine(xLinearLayout.getContext(), xLinearLayout);
        AccountUtils.get().goLoginByService();
        return false;
    }

    private void isLoadingStatus(XLinearLayout xLinearLayout) {
        if (iconOnClick(this.mAppItem)) {
            VuiEngineUtils.speakExceptionByVuiEngine(xLinearLayout.getContext(), xLinearLayout, R.string.vui_engine_tips_loading, xLinearLayout.getVuiLabel());
        }
    }
}
