package com.xiaopeng.appstore.common_ui.common;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.common_ui.common.EnhancedListAdapter;
import java.util.List;
/* loaded from: classes.dex */
public abstract class EnhancedListAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    final AsyncListDiffer<T> mDiffer;
    private final AsyncListDiffer.ListListener<T> mListener;

    public void onCurrentListChanged(List<T> previousList, List<T> currentList) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public EnhancedListAdapter(DiffUtil.ItemCallback<T> diffCallback, ListUpdateCallback listUpdateCallback) {
        AsyncListDiffer.ListListener<T> listListener = new AsyncListDiffer.ListListener<T>() { // from class: com.xiaopeng.appstore.common_ui.common.EnhancedListAdapter.1
            @Override // androidx.recyclerview.widget.AsyncListDiffer.ListListener
            public void onCurrentListChanged(List<T> previousList, List<T> currentList) {
                EnhancedListAdapter.this.onCurrentListChanged(previousList, currentList);
            }
        };
        this.mListener = listListener;
        AsyncListDiffer<T> asyncListDiffer = new AsyncListDiffer<>(listUpdateCallback, new AsyncDifferConfig.Builder(diffCallback).build());
        this.mDiffer = asyncListDiffer;
        asyncListDiffer.addListListener(listListener);
    }

    protected EnhancedListAdapter(AsyncDifferConfig<T> config, ListUpdateCallback listUpdateCallback) {
        AsyncListDiffer.ListListener<T> listListener = new AsyncListDiffer.ListListener<T>() { // from class: com.xiaopeng.appstore.common_ui.common.EnhancedListAdapter.1
            @Override // androidx.recyclerview.widget.AsyncListDiffer.ListListener
            public void onCurrentListChanged(List<T> previousList, List<T> currentList) {
                EnhancedListAdapter.this.onCurrentListChanged(previousList, currentList);
            }
        };
        this.mListener = listListener;
        AsyncListDiffer<T> asyncListDiffer = new AsyncListDiffer<>(listUpdateCallback, config);
        this.mDiffer = asyncListDiffer;
        asyncListDiffer.addListListener(listListener);
    }

    public void submitList(List<T> list) {
        this.mDiffer.submitList(list);
    }

    public void submitList(List<T> list, final Runnable commitCallback) {
        this.mDiffer.submitList(list, commitCallback);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public T getItem(int position) {
        return this.mDiffer.getCurrentList().get(position);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mDiffer.getCurrentList().size();
    }

    public List<T> getCurrentList() {
        return this.mDiffer.getCurrentList();
    }

    /* loaded from: classes.dex */
    public static class AdapterListUpdateCallback2 implements ListUpdateCallback {
        private static final String TAG = "AdapterListUpdateCallba";
        private Handler mHandler;
        private final RecyclerView mRecyclerView;

        public AdapterListUpdateCallback2(RecyclerView recyclerView) {
            this.mRecyclerView = recyclerView;
            recyclerView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.xiaopeng.appstore.common_ui.common.EnhancedListAdapter.AdapterListUpdateCallback2.1
                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewAttachedToWindow(View v) {
                    Logger.t(AdapterListUpdateCallback2.TAG).d("onViewAttachedToWindow rv=" + AdapterListUpdateCallback2.this.mRecyclerView + " adapter=" + AdapterListUpdateCallback2.this.mRecyclerView.getAdapter());
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewDetachedFromWindow(View v) {
                    Logger.t(AdapterListUpdateCallback2.TAG).d("onViewDetachedFromWindow rv=" + AdapterListUpdateCallback2.this.mRecyclerView + " adapter=" + AdapterListUpdateCallback2.this.mRecyclerView.getAdapter());
                    AdapterListUpdateCallback2.this.mHandler.removeCallbacksAndMessages(null);
                }
            });
            this.mHandler = new Handler(Looper.getMainLooper());
        }

        @Override // androidx.recyclerview.widget.ListUpdateCallback
        public void onInserted(int position, int count) {
            lambda$tryNotifyItemRangeInserted$3$EnhancedListAdapter$AdapterListUpdateCallback2(position, count);
        }

        @Override // androidx.recyclerview.widget.ListUpdateCallback
        public void onRemoved(int position, int count) {
            lambda$tryNotifyItemRangeRemoved$2$EnhancedListAdapter$AdapterListUpdateCallback2(position, count);
        }

        @Override // androidx.recyclerview.widget.ListUpdateCallback
        public void onMoved(int fromPosition, int toPosition) {
            lambda$tryNotifyItemMoved$1$EnhancedListAdapter$AdapterListUpdateCallback2(fromPosition, toPosition);
        }

        @Override // androidx.recyclerview.widget.ListUpdateCallback
        public void onChanged(int position, int count, Object payload) {
            lambda$tryNotifyItemRangeChanged$0$EnhancedListAdapter$AdapterListUpdateCallback2(position, count, payload);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: tryNotifyItemRangeChanged */
        public void lambda$tryNotifyItemRangeChanged$0$EnhancedListAdapter$AdapterListUpdateCallback2(final int position, final int count, final Object payload) {
            if (!this.mRecyclerView.isAttachedToWindow()) {
                Logger.t(TAG).w("tryNotifyItemRangeChanged not attached, pos=" + position + " count=" + count + " rv=" + this.mRecyclerView, new Object[0]);
                return;
            }
            RecyclerView.Adapter adapter = this.mRecyclerView.getAdapter();
            if (adapter == null) {
                Logger.t(TAG).w("tryNotifyItemRangeChanged adapter is null, pos=" + position + " count=" + count + " rv=" + this.mRecyclerView, new Object[0]);
            } else if (this.mRecyclerView.isComputingLayout()) {
                Logger.t(TAG).w("tryNotifyItemRangeChanged computing layout, post to next traversal. pos=" + position + " count=" + count + " rv=" + this.mRecyclerView + " adapter=" + adapter, new Object[0]);
                this.mHandler.post(new Runnable() { // from class: com.xiaopeng.appstore.common_ui.common.-$$Lambda$EnhancedListAdapter$AdapterListUpdateCallback2$ZyXjmqb9Z6MpeOu2WkFpEAfJvXQ
                    @Override // java.lang.Runnable
                    public final void run() {
                        EnhancedListAdapter.AdapterListUpdateCallback2.this.lambda$tryNotifyItemRangeChanged$0$EnhancedListAdapter$AdapterListUpdateCallback2(position, count, payload);
                    }
                });
            } else {
                adapter.notifyItemRangeChanged(position, count, payload);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: tryNotifyItemMoved */
        public void lambda$tryNotifyItemMoved$1$EnhancedListAdapter$AdapterListUpdateCallback2(final int fromPosition, final int toPosition) {
            if (!this.mRecyclerView.isAttachedToWindow()) {
                Logger.t(TAG).w("tryNotifyItemRangeChanged not attached, fromPosition=" + fromPosition + " toPosition=" + toPosition + " rv=" + this.mRecyclerView, new Object[0]);
                return;
            }
            RecyclerView.Adapter adapter = this.mRecyclerView.getAdapter();
            if (adapter == null) {
                Logger.t(TAG).w("tryNotifyItemMoved adapter is null, from=" + fromPosition + " to=" + toPosition + " rv=" + this.mRecyclerView, new Object[0]);
            } else if (this.mRecyclerView.isComputingLayout()) {
                Logger.t(TAG).w("tryNotifyItemMoved computing layout, post to next traversal. from=" + fromPosition + " to=" + toPosition + " rv=" + this.mRecyclerView + " adapter=" + adapter, new Object[0]);
                this.mHandler.post(new Runnable() { // from class: com.xiaopeng.appstore.common_ui.common.-$$Lambda$EnhancedListAdapter$AdapterListUpdateCallback2$FQf15_AMTyCZ0gSaeHfcs5T5zDI
                    @Override // java.lang.Runnable
                    public final void run() {
                        EnhancedListAdapter.AdapterListUpdateCallback2.this.lambda$tryNotifyItemMoved$1$EnhancedListAdapter$AdapterListUpdateCallback2(fromPosition, toPosition);
                    }
                });
            } else {
                adapter.notifyItemMoved(fromPosition, toPosition);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: tryNotifyItemRangeRemoved */
        public void lambda$tryNotifyItemRangeRemoved$2$EnhancedListAdapter$AdapterListUpdateCallback2(final int position, final int count) {
            if (!this.mRecyclerView.isAttachedToWindow()) {
                Logger.t(TAG).w("tryNotifyItemRangeChanged not attached, pos=" + position + " count=" + count + " rv=" + this.mRecyclerView, new Object[0]);
                return;
            }
            RecyclerView.Adapter adapter = this.mRecyclerView.getAdapter();
            if (adapter == null) {
                Logger.t(TAG).w("tryNotifyItemRangeRemoved adapter is null, pos=" + position + " count=" + count + " rv=" + this.mRecyclerView, new Object[0]);
            } else if (this.mRecyclerView.isComputingLayout()) {
                Logger.t(TAG).w("tryNotifyItemRangeRemoved computing layout, post to next traversal. pos=" + position + " count=" + count + " rv=" + this.mRecyclerView + " adapter=" + adapter, new Object[0]);
                this.mHandler.post(new Runnable() { // from class: com.xiaopeng.appstore.common_ui.common.-$$Lambda$EnhancedListAdapter$AdapterListUpdateCallback2$CTHHF8d9M5ZgoDljXHPnsV5EiCY
                    @Override // java.lang.Runnable
                    public final void run() {
                        EnhancedListAdapter.AdapterListUpdateCallback2.this.lambda$tryNotifyItemRangeRemoved$2$EnhancedListAdapter$AdapterListUpdateCallback2(position, count);
                    }
                });
            } else {
                adapter.notifyItemRangeRemoved(position, count);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: tryNotifyItemRangeInserted */
        public void lambda$tryNotifyItemRangeInserted$3$EnhancedListAdapter$AdapterListUpdateCallback2(final int position, final int count) {
            if (!this.mRecyclerView.isAttachedToWindow()) {
                Logger.t(TAG).w("tryNotifyItemRangeChanged not attached, pos=" + position + " count=" + count + " rv=" + this.mRecyclerView, new Object[0]);
                return;
            }
            RecyclerView.Adapter adapter = this.mRecyclerView.getAdapter();
            if (adapter == null) {
                Logger.t(TAG).w("tryNotifyItemRangeInserted adapter is null, pos=" + position + " count=" + count + " rv=" + this.mRecyclerView, new Object[0]);
            } else if (this.mRecyclerView.isComputingLayout()) {
                Logger.t(TAG).w("tryNotifyItemRangeInserted computing layout, post to next traversal. pos=" + position + " count=" + count + " rv=" + this.mRecyclerView + " adapter=" + adapter, new Object[0]);
                this.mHandler.post(new Runnable() { // from class: com.xiaopeng.appstore.common_ui.common.-$$Lambda$EnhancedListAdapter$AdapterListUpdateCallback2$YpxQa_mJZQM-xE76Pcu5yWdml8Y
                    @Override // java.lang.Runnable
                    public final void run() {
                        EnhancedListAdapter.AdapterListUpdateCallback2.this.lambda$tryNotifyItemRangeInserted$3$EnhancedListAdapter$AdapterListUpdateCallback2(position, count);
                    }
                });
            } else {
                adapter.notifyItemRangeInserted(position, count);
            }
        }
    }
}
