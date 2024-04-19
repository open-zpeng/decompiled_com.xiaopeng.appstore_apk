package com.xiaopeng.appstore.applist_ui.adapter;

import android.text.TextUtils;
import android.util.SparseArray;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.applist_biz.model.AppGroupItemModel;
import com.xiaopeng.appstore.applist_biz.model.BaseAppItem;
import com.xiaopeng.appstore.xpcommon.CarUtils;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/* loaded from: classes2.dex */
public class AppGroupHelper {
    private static final int INVALID_GROUP_INDEX = -1;
    private static final String TAG = "AppGroupHelper";
    private List<AppGroupItemModel> mDataList;
    private SparseArray<List<BaseAppItem>> mGroupData;
    private List<Integer> mGroupIdList;
    private List<Integer> mGroupIdListCopy;
    private List<Integer> mTypeList;
    private List<Integer> mTypeListCopy;
    private final Object mDataLock = new Object();
    private IAppGroupStrategy mStrategy = createAppGroupStrategy();

    /* loaded from: classes2.dex */
    public interface IAppGroupStrategy {
        boolean filter(BaseAppItem appItem);

        int getCountsPerRow();

        int getGroup(BaseAppItem appItem);

        int getGroupCount();

        int getGroupIndex(BaseAppItem appItem);

        CharSequence getGroupTitle(int group);
    }

    private IAppGroupStrategy createAppGroupStrategy() {
        return CarUtils.isEURegion() ? new XpInterAppGroupStrategy() : new XpAppGroupStrategy();
    }

    public <T extends BaseAppItem> void initData(List<T> appList) {
        synchronized (this.mDataLock) {
            this.mGroupData = new SparseArray<>(this.mStrategy.getGroupCount());
            this.mDataList = new LinkedList();
            this.mGroupIdList = new ArrayList();
            this.mTypeList = new ArrayList();
            for (T t : appList) {
                if (!this.mStrategy.filter(t)) {
                    int group = this.mStrategy.getGroup(t);
                    List<BaseAppItem> list = this.mGroupData.get(group);
                    if (list == null) {
                        list = new ArrayList<>();
                        this.mGroupData.put(group, list);
                    }
                    int groupIndex = this.mStrategy.getGroupIndex(t);
                    if (groupIndex >= 0 && groupIndex < list.size()) {
                        list.add(groupIndex, t);
                    } else {
                        list.add(t);
                    }
                }
            }
            for (int i = 0; i < this.mGroupData.size(); i++) {
                int keyAt = this.mGroupData.keyAt(i);
                CharSequence groupTitle = this.mStrategy.getGroupTitle(keyAt);
                if (!TextUtils.isEmpty(groupTitle)) {
                    this.mDataList.add(new AppGroupItemModel(101, groupTitle, keyAt));
                    this.mGroupIdList.add(Integer.valueOf(keyAt));
                    this.mTypeList.add(101);
                }
                for (BaseAppItem baseAppItem : this.mGroupData.get(keyAt)) {
                    this.mDataList.add(new AppGroupItemModel(100, baseAppItem, keyAt));
                    this.mGroupIdList.add(Integer.valueOf(keyAt));
                    this.mTypeList.add(100);
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v4, types: [java.lang.CharSequence, T, java.lang.Object] */
    public void refreshTitle() {
        if (this.mDataList == null) {
            Logger.t(TAG).w("refreshTitle, data not ready yet!", new Object[0]);
            return;
        }
        for (int i = 0; i < this.mDataList.size(); i++) {
            AppGroupItemModel appGroupItemModel = this.mDataList.get(i);
            if (appGroupItemModel.type == 101 && (appGroupItemModel.data instanceof CharSequence)) {
                ?? groupTitle = this.mStrategy.getGroupTitle(appGroupItemModel.groupId);
                if (appGroupItemModel.data != 0 && ((CharSequence) appGroupItemModel.data).equals(groupTitle)) {
                    Logger.t(TAG).i("refreshTitle, ignore:" + appGroupItemModel, new Object[0]);
                } else {
                    Logger.t(TAG).i("refreshTitle:" + appGroupItemModel, new Object[0]);
                    appGroupItemModel.data = groupTitle;
                }
            }
        }
    }

    public IAppGroupStrategy getStrategy() {
        return this.mStrategy;
    }

    public List<AppGroupItemModel> getDataList() {
        return this.mDataList;
    }

    public boolean isContentSameGroup(int index1, int index2) {
        List list = this.mTypeListCopy;
        synchronized (this.mDataLock) {
            if (this.mTypeList != null) {
                if (list == null) {
                    list = new ArrayList(this.mTypeList);
                    this.mTypeListCopy = list;
                } else {
                    list.clear();
                    list.addAll(this.mTypeList);
                }
            }
        }
        if (list == null) {
            Logger.t(TAG).w("isContentSameGroup, copyList is null, index1:" + index1 + ", index2:" + index2, new Object[0]);
            return false;
        }
        int size = list.size();
        if (index1 >= size) {
            Logger.t(TAG).w("isContentSameGroup, index1:" + index1 + ", out of size:" + size, new Object[0]);
            return false;
        } else if (index2 >= size) {
            Logger.t(TAG).w("isContentSameGroup, index2:" + index2 + ", out of size:" + size, new Object[0]);
            return false;
        } else {
            if (((Integer) list.get(index1)).intValue() == ((Integer) list.get(index2)).intValue()) {
                List<Integer> threadSafeGroupIdList = getThreadSafeGroupIdList();
                return getGroupId(threadSafeGroupIdList, index1) == getGroupId(threadSafeGroupIdList, index2);
            }
            return false;
        }
    }

    private List<Integer> getThreadSafeGroupIdList() {
        if (this.mGroupIdList == null) {
            return null;
        }
        List<Integer> list = this.mGroupIdListCopy;
        synchronized (this.mDataLock) {
            if (list == null) {
                list = new ArrayList<>(this.mGroupIdList);
                this.mGroupIdListCopy = list;
            } else {
                list.clear();
                list.addAll(this.mGroupIdList);
            }
        }
        return list;
    }

    private int getGroupId(List<Integer> groupIdList, int index) {
        if (groupIdList == null) {
            return -1;
        }
        if (groupIdList.size() <= index) {
            Logger.t(TAG).w("getGroupId, index:" + index + ", out of size:" + groupIdList, new Object[0]);
            return -1;
        }
        return groupIdList.get(index).intValue();
    }

    public int getGroupId(int index) {
        List<Integer> threadSafeGroupIdList;
        if (this.mGroupIdList == null || (threadSafeGroupIdList = getThreadSafeGroupIdList()) == null || threadSafeGroupIdList.size() <= index) {
            return -1;
        }
        return threadSafeGroupIdList.get(index).intValue();
    }
}
