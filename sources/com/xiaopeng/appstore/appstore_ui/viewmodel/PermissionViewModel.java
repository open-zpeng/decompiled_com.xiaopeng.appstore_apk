package com.xiaopeng.appstore.appstore_ui.viewmodel;

import androidx.lifecycle.ViewModel;
import com.xiaopeng.appstore.appstore_biz.model2.PermissionItemModel;
import com.xiaopeng.appstore.appstore_biz.model2.PermissionModel;
import com.xiaopeng.appstore.appstore_biz.model2.PermissionViewData;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes2.dex */
public class PermissionViewModel extends ViewModel {
    public static final int DEFAULT_START_POSITION = 0;

    public PermissionModel parseData(PermissionViewData data) {
        PermissionModel permissionModel = new PermissionModel();
        permissionModel.setIconUrl(data.getIconUrl());
        ArrayList<PermissionItemModel> arrayList = new ArrayList<>();
        parsePermissionList(arrayList, data.getImportantPermission(), ResUtils.getString(R.string.permission_type_important));
        parsePermissionList(arrayList, data.getGeneralPermission(), ResUtils.getString(R.string.permission_type_general));
        permissionModel.setItemModels(arrayList);
        return permissionModel;
    }

    private void parsePermissionList(ArrayList<PermissionItemModel> itemModels, List<String> importantPermission, String title) {
        if (importantPermission == null || importantPermission.isEmpty()) {
            return;
        }
        for (int i = 0; i < importantPermission.size(); i++) {
            PermissionItemModel permissionItemModel = new PermissionItemModel();
            permissionItemModel.setContent(importantPermission.get(i));
            permissionItemModel.setTitleName("");
            permissionItemModel.setShowTitle(false);
            if (i == 0) {
                permissionItemModel.setShowTitle(true);
                permissionItemModel.setTitleName(title);
            }
            itemModels.add(permissionItemModel);
        }
    }
}
