package com.xiaopeng.appstore.international;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.xiaopeng.appstore.international.databinding.InternationalFragmentItemDetailBindingImpl;
import com.xiaopeng.appstore.international.databinding.InternationalPendingUpgradeFragmentBindingImpl;
import com.xiaopeng.appstore.international.databinding.InternationalPendingUpgradeItemBindingImpl;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/* loaded from: classes.dex */
public class DataBinderMapperImpl extends DataBinderMapper {
    private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP;
    private static final int LAYOUT_INTERNATIONALFRAGMENTITEMDETAIL = 1;
    private static final int LAYOUT_INTERNATIONALPENDINGUPGRADEFRAGMENT = 2;
    private static final int LAYOUT_INTERNATIONALPENDINGUPGRADEITEM = 3;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray(3);
        INTERNAL_LAYOUT_ID_LOOKUP = sparseIntArray;
        sparseIntArray.put(R.layout.international_fragment_item_detail, 1);
        sparseIntArray.put(R.layout.international_pending_upgrade_fragment, 2);
        sparseIntArray.put(R.layout.international_pending_upgrade_item, 3);
    }

    @Override // androidx.databinding.DataBinderMapper
    public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
        int i = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
        if (i > 0) {
            Object tag = view.getTag();
            if (tag != null) {
                if (i == 1) {
                    if ("layout/international_fragment_item_detail_0".equals(tag)) {
                        return new InternationalFragmentItemDetailBindingImpl(component, view);
                    }
                    throw new IllegalArgumentException("The tag for international_fragment_item_detail is invalid. Received: " + tag);
                } else if (i == 2) {
                    if ("layout/international_pending_upgrade_fragment_0".equals(tag)) {
                        return new InternationalPendingUpgradeFragmentBindingImpl(component, view);
                    }
                    throw new IllegalArgumentException("The tag for international_pending_upgrade_fragment is invalid. Received: " + tag);
                } else if (i != 3) {
                    return null;
                } else {
                    if ("layout/international_pending_upgrade_item_0".equals(tag)) {
                        return new InternationalPendingUpgradeItemBindingImpl(component, view);
                    }
                    throw new IllegalArgumentException("The tag for international_pending_upgrade_item is invalid. Received: " + tag);
                }
            }
            throw new RuntimeException("view must have a tag");
        }
        return null;
    }

    @Override // androidx.databinding.DataBinderMapper
    public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
        if (views == null || views.length == 0 || INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId) <= 0 || views[0].getTag() != null) {
            return null;
        }
        throw new RuntimeException("view must have a tag");
    }

    @Override // androidx.databinding.DataBinderMapper
    public int getLayoutId(String tag) {
        Integer num;
        if (tag == null || (num = InnerLayoutIdLookup.sKeys.get(tag)) == null) {
            return 0;
        }
        return num.intValue();
    }

    @Override // androidx.databinding.DataBinderMapper
    public String convertBrIdToString(int localId) {
        return InnerBrLookup.sKeys.get(localId);
    }

    @Override // androidx.databinding.DataBinderMapper
    public List<DataBinderMapper> collectDependencies() {
        ArrayList arrayList = new ArrayList(3);
        arrayList.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
        arrayList.add(new com.xiaopeng.appstore.appstore_ui.DataBinderMapperImpl());
        arrayList.add(new com.xiaopeng.libcommon.DataBinderMapperImpl());
        return arrayList;
    }

    /* loaded from: classes.dex */
    private static class InnerBrLookup {
        static final SparseArray<String> sKeys;

        private InnerBrLookup() {
        }

        static {
            SparseArray<String> sparseArray = new SparseArray<>(12);
            sKeys = sparseArray;
            sparseArray.put(0, "_all");
            sparseArray.put(1, "callback");
            sparseArray.put(2, "dividerColor");
            sparseArray.put(3, "expanded");
            sparseArray.put(4, "model");
            sparseArray.put(5, VuiConstants.ELEMENT_POSITION);
            sparseArray.put(6, "progress");
            sparseArray.put(7, "state");
            sparseArray.put(8, "title");
            sparseArray.put(9, "url");
            sparseArray.put(10, "viewHolder");
            sparseArray.put(11, "viewModel");
        }
    }

    /* loaded from: classes.dex */
    private static class InnerLayoutIdLookup {
        static final HashMap<String, Integer> sKeys;

        private InnerLayoutIdLookup() {
        }

        static {
            HashMap<String, Integer> hashMap = new HashMap<>(3);
            sKeys = hashMap;
            hashMap.put("layout/international_fragment_item_detail_0", Integer.valueOf(R.layout.international_fragment_item_detail));
            hashMap.put("layout/international_pending_upgrade_fragment_0", Integer.valueOf(R.layout.international_pending_upgrade_fragment));
            hashMap.put("layout/international_pending_upgrade_item_0", Integer.valueOf(R.layout.international_pending_upgrade_item));
        }
    }
}
