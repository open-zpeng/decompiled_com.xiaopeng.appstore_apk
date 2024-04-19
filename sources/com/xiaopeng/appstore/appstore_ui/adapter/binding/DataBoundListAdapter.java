package com.xiaopeng.appstore.appstore_ui.adapter.binding;

import android.view.ViewGroup;
import androidx.databinding.ViewDataBinding;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: DataBoundListAdapter.kt */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\b&\u0018\u0000*\u0004\b\u0000\u0010\u0001*\b\b\u0001\u0010\u0002*\u00020\u00032\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00050\u0004B\u001b\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\t¢\u0006\u0002\u0010\nJ+\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00028\u00012\u0006\u0010\u000e\u001a\u00028\u00002\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00010\u0005H$¢\u0006\u0002\u0010\u0010J9\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00028\u00012\u0006\u0010\u000e\u001a\u00028\u00002\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00010\u00052\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\u0014¢\u0006\u0002\u0010\u0014J\u0015\u0010\u0015\u001a\u00028\u00012\u0006\u0010\u0016\u001a\u00020\u0017H$¢\u0006\u0002\u0010\u0018J\u001e\u0010\u0019\u001a\u00020\f2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00010\u00052\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J,\u0010\u0019\u001a\u00020\f2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00010\u00052\u0006\u0010\u001a\u001a\u00020\u001b2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\u0016J\u001e\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00010\u00052\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u001d\u001a\u00020\u001bH\u0016J\u0016\u0010\u001e\u001a\u00020\f2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00010\u0005H\u0016J\u0016\u0010\u001f\u001a\u00020\f2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00010\u0005H\u0016¨\u0006 "}, d2 = {"Lcom/xiaopeng/appstore/appstore_ui/adapter/binding/DataBoundListAdapter;", ExifInterface.GPS_DIRECTION_TRUE, ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Landroidx/databinding/ViewDataBinding;", "Landroidx/recyclerview/widget/ListAdapter;", "Lcom/xiaopeng/appstore/appstore_ui/adapter/binding/DataBoundViewHolder;", "appExecutors", "Lcom/xiaopeng/appstore/libcommon/utils/AppExecutors;", "diffCallback", "Landroidx/recyclerview/widget/DiffUtil$ItemCallback;", "(Lcom/xiaopeng/appstore/libcommon/utils/AppExecutors;Landroidx/recyclerview/widget/DiffUtil$ItemCallback;)V", "bind", "", "binding", "item", "holder", "(Landroidx/databinding/ViewDataBinding;Ljava/lang/Object;Lcom/xiaopeng/appstore/appstore_ui/adapter/binding/DataBoundViewHolder;)V", "payloads", "", "", "(Landroidx/databinding/ViewDataBinding;Ljava/lang/Object;Lcom/xiaopeng/appstore/appstore_ui/adapter/binding/DataBoundViewHolder;Ljava/util/List;)V", "createBinding", "parent", "Landroid/view/ViewGroup;", "(Landroid/view/ViewGroup;)Landroidx/databinding/ViewDataBinding;", "onBindViewHolder", VuiConstants.ELEMENT_POSITION, "", "onCreateViewHolder", "viewType", "onViewAttachedToWindow", "onViewDetachedFromWindow", "appstore_ui_D55V1Release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes2.dex */
public abstract class DataBoundListAdapter<T, V extends ViewDataBinding> extends ListAdapter<T, DataBoundViewHolder<? extends V>> {
    protected abstract void bind(V v, T t, DataBoundViewHolder<? extends V> dataBoundViewHolder);

    protected void bind(V binding, T t, DataBoundViewHolder<? extends V> holder, List<Object> payloads) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(payloads, "payloads");
    }

    protected abstract V createBinding(ViewGroup viewGroup);

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        onBindViewHolder((DataBoundViewHolder) ((DataBoundViewHolder) viewHolder), i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i, List list) {
        onBindViewHolder((DataBoundViewHolder) ((DataBoundViewHolder) viewHolder), i, (List<Object>) list);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ void onViewAttachedToWindow(RecyclerView.ViewHolder viewHolder) {
        onViewAttachedToWindow((DataBoundViewHolder) ((DataBoundViewHolder) viewHolder));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ void onViewDetachedFromWindow(RecyclerView.ViewHolder viewHolder) {
        onViewDetachedFromWindow((DataBoundViewHolder) ((DataBoundViewHolder) viewHolder));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataBoundListAdapter(AppExecutors appExecutors, DiffUtil.ItemCallback<T> diffCallback) {
        super(new AsyncDifferConfig.Builder(diffCallback).setBackgroundThreadExecutor(appExecutors.diskIO()).build());
        Intrinsics.checkNotNullParameter(appExecutors, "appExecutors");
        Intrinsics.checkNotNullParameter(diffCallback, "diffCallback");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public DataBoundViewHolder<V> onCreateViewHolder(ViewGroup parent, int i) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        V createBinding = createBinding(parent);
        DataBoundViewHolder<V> dataBoundViewHolder = new DataBoundViewHolder<>(createBinding);
        createBinding.setLifecycleOwner(dataBoundViewHolder);
        return dataBoundViewHolder;
    }

    public void onBindViewHolder(DataBoundViewHolder<? extends V> holder, int i) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        bind(holder.getBinding(), getItem(holder.getAdapterPosition()), holder);
        holder.getBinding().executePendingBindings();
    }

    public void onBindViewHolder(DataBoundViewHolder<? extends V> holder, int i, List<Object> payloads) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(payloads, "payloads");
        if (payloads.isEmpty()) {
            onBindViewHolder((DataBoundViewHolder) holder, i);
            return;
        }
        bind(holder.getBinding(), getItem(holder.getAdapterPosition()), holder, payloads);
        holder.getBinding().executePendingBindings();
    }

    public void onViewAttachedToWindow(DataBoundViewHolder<? extends V> holder) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        super.onViewAttachedToWindow((DataBoundListAdapter<T, V>) holder);
        holder.markAttach();
    }

    public void onViewDetachedFromWindow(DataBoundViewHolder<? extends V> holder) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        super.onViewDetachedFromWindow((DataBoundListAdapter<T, V>) holder);
        holder.markDetach();
    }
}
