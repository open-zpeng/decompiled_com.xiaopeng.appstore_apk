package com.xiaopeng.appstore.appstore_ui;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.xiaopeng.appstore.appstore_ui.databinding.FragmentItemDetailBindingImpl;
import com.xiaopeng.appstore.appstore_ui.databinding.FragmentItemDetailBindingLandImpl;
import com.xiaopeng.appstore.appstore_ui.databinding.FragmentPendingUpdateBindingImpl;
import com.xiaopeng.appstore.appstore_ui.databinding.FragmentSceneBindingImpl;
import com.xiaopeng.appstore.appstore_ui.databinding.ItemActivityFooterBindingImpl;
import com.xiaopeng.appstore.appstore_ui.databinding.ItemActivityFooterBindingLandImpl;
import com.xiaopeng.appstore.appstore_ui.databinding.ItemActivityHeadBindingImpl;
import com.xiaopeng.appstore.appstore_ui.databinding.ItemBannerPackageBindingImpl;
import com.xiaopeng.appstore.appstore_ui.databinding.ItemCardAppBindingImpl;
import com.xiaopeng.appstore.appstore_ui.databinding.ItemDetailImageBindingImpl;
import com.xiaopeng.appstore.appstore_ui.databinding.ItemGridAppBindingBindingImpl;
import com.xiaopeng.appstore.appstore_ui.databinding.ItemGridAppBindingBindingLandImpl;
import com.xiaopeng.appstore.appstore_ui.databinding.ItemGridNarrowAppBindingImpl;
import com.xiaopeng.appstore.appstore_ui.databinding.ItemHorizontalAppBindingImpl;
import com.xiaopeng.appstore.appstore_ui.databinding.ItemHorizontalAppBindingLandImpl;
import com.xiaopeng.appstore.appstore_ui.databinding.ItemNormalCardAppBindingImpl;
import com.xiaopeng.appstore.appstore_ui.databinding.ItemPackageBindingBindingImpl;
import com.xiaopeng.appstore.appstore_ui.databinding.ItemTitleLayoutBindingImpl;
import com.xiaopeng.appstore.appstore_ui.databinding.ItemUpgradeLayoutBindingImpl;
import com.xiaopeng.appstore.appstore_ui.databinding.ItemUpgradeLayoutBindingLandImpl;
import com.xiaopeng.appstore.appstore_ui.databinding.ItemWideAppBindingImpl;
import com.xiaopeng.appstore.appstore_ui.databinding.ItemWideAppBindingLandImpl;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/* loaded from: classes2.dex */
public class DataBinderMapperImpl extends DataBinderMapper {
    private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP;
    private static final int LAYOUT_FRAGMENTITEMDETAIL = 1;
    private static final int LAYOUT_FRAGMENTPENDINGUPDATE = 2;
    private static final int LAYOUT_FRAGMENTSCENE = 3;
    private static final int LAYOUT_ITEMACTIVITYFOOTER = 4;
    private static final int LAYOUT_ITEMACTIVITYHEAD = 5;
    private static final int LAYOUT_ITEMBANNERPACKAGE = 6;
    private static final int LAYOUT_ITEMCARDAPP = 7;
    private static final int LAYOUT_ITEMDETAILIMAGE = 8;
    private static final int LAYOUT_ITEMGRIDAPPBINDING = 9;
    private static final int LAYOUT_ITEMGRIDNARROWAPP = 10;
    private static final int LAYOUT_ITEMHORIZONTALAPP = 11;
    private static final int LAYOUT_ITEMNORMALCARDAPP = 12;
    private static final int LAYOUT_ITEMPACKAGEBINDING = 13;
    private static final int LAYOUT_ITEMTITLELAYOUT = 14;
    private static final int LAYOUT_ITEMUPGRADELAYOUT = 15;
    private static final int LAYOUT_ITEMWIDEAPP = 16;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray(16);
        INTERNAL_LAYOUT_ID_LOOKUP = sparseIntArray;
        sparseIntArray.put(R.layout.fragment_item_detail, 1);
        sparseIntArray.put(R.layout.fragment_pending_update, 2);
        sparseIntArray.put(R.layout.fragment_scene, 3);
        sparseIntArray.put(R.layout.item_activity_footer, 4);
        sparseIntArray.put(R.layout.item_activity_head, 5);
        sparseIntArray.put(R.layout.item_banner_package, 6);
        sparseIntArray.put(R.layout.item_card_app, 7);
        sparseIntArray.put(R.layout.item_detail_image, 8);
        sparseIntArray.put(R.layout.item_grid_app_binding, 9);
        sparseIntArray.put(R.layout.item_grid_narrow_app, 10);
        sparseIntArray.put(R.layout.item_horizontal_app, 11);
        sparseIntArray.put(R.layout.item_normal_card_app, 12);
        sparseIntArray.put(R.layout.item_package_binding, 13);
        sparseIntArray.put(R.layout.item_title_layout, 14);
        sparseIntArray.put(R.layout.item_upgrade_layout, 15);
        sparseIntArray.put(R.layout.item_wide_app, 16);
    }

    @Override // androidx.databinding.DataBinderMapper
    public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
        int i = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
        if (i > 0) {
            Object tag = view.getTag();
            if (tag == null) {
                throw new RuntimeException("view must have a tag");
            }
            switch (i) {
                case 1:
                    if ("layout/fragment_item_detail_0".equals(tag)) {
                        return new FragmentItemDetailBindingImpl(component, view);
                    }
                    if ("layout-land/fragment_item_detail_0".equals(tag)) {
                        return new FragmentItemDetailBindingLandImpl(component, view);
                    }
                    throw new IllegalArgumentException("The tag for fragment_item_detail is invalid. Received: " + tag);
                case 2:
                    if ("layout/fragment_pending_update_0".equals(tag)) {
                        return new FragmentPendingUpdateBindingImpl(component, view);
                    }
                    throw new IllegalArgumentException("The tag for fragment_pending_update is invalid. Received: " + tag);
                case 3:
                    if ("layout/fragment_scene_0".equals(tag)) {
                        return new FragmentSceneBindingImpl(component, view);
                    }
                    throw new IllegalArgumentException("The tag for fragment_scene is invalid. Received: " + tag);
                case 4:
                    if ("layout-land/item_activity_footer_0".equals(tag)) {
                        return new ItemActivityFooterBindingLandImpl(component, view);
                    }
                    if ("layout/item_activity_footer_0".equals(tag)) {
                        return new ItemActivityFooterBindingImpl(component, view);
                    }
                    throw new IllegalArgumentException("The tag for item_activity_footer is invalid. Received: " + tag);
                case 5:
                    if ("layout/item_activity_head_0".equals(tag)) {
                        return new ItemActivityHeadBindingImpl(component, view);
                    }
                    throw new IllegalArgumentException("The tag for item_activity_head is invalid. Received: " + tag);
                case 6:
                    if ("layout/item_banner_package_0".equals(tag)) {
                        return new ItemBannerPackageBindingImpl(component, view);
                    }
                    throw new IllegalArgumentException("The tag for item_banner_package is invalid. Received: " + tag);
                case 7:
                    if ("layout/item_card_app_0".equals(tag)) {
                        return new ItemCardAppBindingImpl(component, view);
                    }
                    throw new IllegalArgumentException("The tag for item_card_app is invalid. Received: " + tag);
                case 8:
                    if ("layout/item_detail_image_0".equals(tag)) {
                        return new ItemDetailImageBindingImpl(component, view);
                    }
                    throw new IllegalArgumentException("The tag for item_detail_image is invalid. Received: " + tag);
                case 9:
                    if ("layout-land/item_grid_app_binding_0".equals(tag)) {
                        return new ItemGridAppBindingBindingLandImpl(component, view);
                    }
                    if ("layout/item_grid_app_binding_0".equals(tag)) {
                        return new ItemGridAppBindingBindingImpl(component, view);
                    }
                    throw new IllegalArgumentException("The tag for item_grid_app_binding is invalid. Received: " + tag);
                case 10:
                    if ("layout/item_grid_narrow_app_0".equals(tag)) {
                        return new ItemGridNarrowAppBindingImpl(component, view);
                    }
                    throw new IllegalArgumentException("The tag for item_grid_narrow_app is invalid. Received: " + tag);
                case 11:
                    if ("layout-land/item_horizontal_app_0".equals(tag)) {
                        return new ItemHorizontalAppBindingLandImpl(component, view);
                    }
                    if ("layout/item_horizontal_app_0".equals(tag)) {
                        return new ItemHorizontalAppBindingImpl(component, view);
                    }
                    throw new IllegalArgumentException("The tag for item_horizontal_app is invalid. Received: " + tag);
                case 12:
                    if ("layout/item_normal_card_app_0".equals(tag)) {
                        return new ItemNormalCardAppBindingImpl(component, view);
                    }
                    throw new IllegalArgumentException("The tag for item_normal_card_app is invalid. Received: " + tag);
                case 13:
                    if ("layout/item_package_binding_0".equals(tag)) {
                        return new ItemPackageBindingBindingImpl(component, view);
                    }
                    throw new IllegalArgumentException("The tag for item_package_binding is invalid. Received: " + tag);
                case 14:
                    if ("layout/item_title_layout_0".equals(tag)) {
                        return new ItemTitleLayoutBindingImpl(component, view);
                    }
                    throw new IllegalArgumentException("The tag for item_title_layout is invalid. Received: " + tag);
                case 15:
                    if ("layout/item_upgrade_layout_0".equals(tag)) {
                        return new ItemUpgradeLayoutBindingImpl(component, view);
                    }
                    if ("layout-land/item_upgrade_layout_0".equals(tag)) {
                        return new ItemUpgradeLayoutBindingLandImpl(component, view);
                    }
                    throw new IllegalArgumentException("The tag for item_upgrade_layout is invalid. Received: " + tag);
                case 16:
                    if ("layout-land/item_wide_app_0".equals(tag)) {
                        return new ItemWideAppBindingLandImpl(component, view);
                    }
                    if ("layout/item_wide_app_0".equals(tag)) {
                        return new ItemWideAppBindingImpl(component, view);
                    }
                    throw new IllegalArgumentException("The tag for item_wide_app is invalid. Received: " + tag);
                default:
                    return null;
            }
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
        ArrayList arrayList = new ArrayList(2);
        arrayList.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
        arrayList.add(new com.xiaopeng.libcommon.DataBinderMapperImpl());
        return arrayList;
    }

    /* loaded from: classes2.dex */
    private static class InnerBrLookup {
        static final SparseArray<String> sKeys;

        private InnerBrLookup() {
        }

        static {
            SparseArray<String> sparseArray = new SparseArray<>(10);
            sKeys = sparseArray;
            sparseArray.put(0, "_all");
            sparseArray.put(1, "callback");
            sparseArray.put(2, "dividerColor");
            sparseArray.put(3, "model");
            sparseArray.put(4, VuiConstants.ELEMENT_POSITION);
            sparseArray.put(5, "progress");
            sparseArray.put(6, "state");
            sparseArray.put(7, "title");
            sparseArray.put(8, "url");
            sparseArray.put(9, "viewModel");
        }
    }

    /* loaded from: classes2.dex */
    private static class InnerLayoutIdLookup {
        static final HashMap<String, Integer> sKeys;

        private InnerLayoutIdLookup() {
        }

        static {
            HashMap<String, Integer> hashMap = new HashMap<>(22);
            sKeys = hashMap;
            hashMap.put("layout/fragment_item_detail_0", Integer.valueOf(R.layout.fragment_item_detail));
            hashMap.put("layout-land/fragment_item_detail_0", Integer.valueOf(R.layout.fragment_item_detail));
            hashMap.put("layout/fragment_pending_update_0", Integer.valueOf(R.layout.fragment_pending_update));
            hashMap.put("layout/fragment_scene_0", Integer.valueOf(R.layout.fragment_scene));
            hashMap.put("layout-land/item_activity_footer_0", Integer.valueOf(R.layout.item_activity_footer));
            hashMap.put("layout/item_activity_footer_0", Integer.valueOf(R.layout.item_activity_footer));
            hashMap.put("layout/item_activity_head_0", Integer.valueOf(R.layout.item_activity_head));
            hashMap.put("layout/item_banner_package_0", Integer.valueOf(R.layout.item_banner_package));
            hashMap.put("layout/item_card_app_0", Integer.valueOf(R.layout.item_card_app));
            hashMap.put("layout/item_detail_image_0", Integer.valueOf(R.layout.item_detail_image));
            hashMap.put("layout-land/item_grid_app_binding_0", Integer.valueOf(R.layout.item_grid_app_binding));
            hashMap.put("layout/item_grid_app_binding_0", Integer.valueOf(R.layout.item_grid_app_binding));
            hashMap.put("layout/item_grid_narrow_app_0", Integer.valueOf(R.layout.item_grid_narrow_app));
            hashMap.put("layout-land/item_horizontal_app_0", Integer.valueOf(R.layout.item_horizontal_app));
            hashMap.put("layout/item_horizontal_app_0", Integer.valueOf(R.layout.item_horizontal_app));
            hashMap.put("layout/item_normal_card_app_0", Integer.valueOf(R.layout.item_normal_card_app));
            hashMap.put("layout/item_package_binding_0", Integer.valueOf(R.layout.item_package_binding));
            hashMap.put("layout/item_title_layout_0", Integer.valueOf(R.layout.item_title_layout));
            hashMap.put("layout/item_upgrade_layout_0", Integer.valueOf(R.layout.item_upgrade_layout));
            hashMap.put("layout-land/item_upgrade_layout_0", Integer.valueOf(R.layout.item_upgrade_layout));
            hashMap.put("layout-land/item_wide_app_0", Integer.valueOf(R.layout.item_wide_app));
            hashMap.put("layout/item_wide_app_0", Integer.valueOf(R.layout.item_wide_app));
        }
    }
}
