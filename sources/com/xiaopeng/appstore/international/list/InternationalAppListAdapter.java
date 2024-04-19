package com.xiaopeng.appstore.international.list;

import android.content.Context;
import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.appstore.applist_biz.model.AppGroupItemModel;
import com.xiaopeng.appstore.applist_biz.model.FixedAppItem;
import com.xiaopeng.appstore.applist_ui.adapter.AppGroupHelper;
import com.xiaopeng.appstore.applist_ui.adapter.AppListAdapter;
import com.xiaopeng.appstore.applist_ui.adapter.BaseAppListViewHolder;
import com.xiaopeng.appstore.bizcommon.logic.Constants;
import com.xiaopeng.appstore.xpcommon.CarUtils;
import java.util.Arrays;
import java.util.List;
/* loaded from: classes.dex */
public class InternationalAppListAdapter extends AppListAdapter {
    private static final String TAG = "InternationalAppListAdapter";
    private IProtocolClickListener mProtocolClickListener;

    public InternationalAppListAdapter(Context context, AppGroupHelper appGroupHelper, ListUpdateCallback listUpdateCallback) {
        super(context, appGroupHelper, listUpdateCallback);
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.AppListAdapter
    protected GridLayoutManager initLayoutManager(Context context, int countsPerRow) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, countsPerRow);
        gridLayoutManager.setSpanSizeLookup(new GridSpanSizer());
        return gridLayoutManager;
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.AppListAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public BaseAppListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 103) {
            return new InternationalAppListFooterViewHolder(parent, this.mProtocolClickListener);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override // com.xiaopeng.appstore.applist_ui.adapter.AppListAdapter, com.xiaopeng.appstore.applist_ui.adapter.IconTouchHelperCallback.IconDragCallback
    public void onIconMove(RecyclerView.ViewHolder viewHolder, int fromPos, int toPos) {
        if (viewHolder instanceof InternationalAppIconViewHolder) {
            InternationalAppIconViewHolder internationalAppIconViewHolder = (InternationalAppIconViewHolder) viewHolder;
            if ((internationalAppIconViewHolder.getBaseAppItem() instanceof FixedAppItem) || !Arrays.asList(Constants.XP_INTER_APP_LIST).contains(internationalAppIconViewHolder.getBaseAppItem().packageName)) {
                return;
            }
        }
        super.onIconMove(viewHolder, fromPos, toPos);
    }

    /* loaded from: classes.dex */
    public class GridSpanSizer extends GridLayoutManager.SpanSizeLookup {
        public GridSpanSizer() {
            setSpanIndexCacheEnabled(true);
        }

        @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
        public int getSpanSize(int position) {
            if (InternationalAppListAdapter.this.getItemViewType(position) == 101 || InternationalAppListAdapter.this.getItemViewType(position) == 103) {
                return InternationalAppListAdapter.this.mCountsPerRow;
            }
            return 1;
        }
    }

    @Override // com.xiaopeng.appstore.common_ui.common.EnhancedListAdapter
    public void submitList(List<AppGroupItemModel> list) {
        if (list != null && !list.isEmpty()) {
            int size = list.size();
            while (true) {
                size--;
                if (size < 0) {
                    break;
                } else if (list.get(size).type == 103) {
                    list.remove(size);
                }
            }
            if ("Q8".equals(CarUtils.getCduType()) || CarUtils.isE38()) {
                list.add(new AppGroupItemModel(103, null, 0));
            }
        }
        super.submitList(list);
    }

    public void setOnProtocolClickListener(IProtocolClickListener protocolClickListener) {
        this.mProtocolClickListener = protocolClickListener;
    }
}
