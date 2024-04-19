package com.xiaopeng.appstore.appstore_biz.datamodel.db;

import android.database.Cursor;
import androidx.collection.ArrayMap;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.DbItemDependentCrossRef;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.ItemDependent;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.PackageItem;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.PackageWithItems;
import com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.PagePackage;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
/* loaded from: classes2.dex */
public final class HomeDao_Impl extends HomeDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<DbItemDependentCrossRef> __insertionAdapterOfDbItemDependentCrossRef;
    private final EntityInsertionAdapter<ItemDependent> __insertionAdapterOfItemDependent;
    private final EntityInsertionAdapter<PackageItem> __insertionAdapterOfPackageItem;
    private final EntityInsertionAdapter<PagePackage> __insertionAdapterOfPagePackage;
    private final SharedSQLiteStatement __preparedStmtOfClearAppItems;
    private final SharedSQLiteStatement __preparedStmtOfClearDependents;
    private final SharedSQLiteStatement __preparedStmtOfClearItemDependentCrossRefs;
    private final SharedSQLiteStatement __preparedStmtOfClearPackages;

    public HomeDao_Impl(RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfPagePackage = new EntityInsertionAdapter<PagePackage>(__db) { // from class: com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `PagePackage` (`packageId`,`layoutType`,`contentType`,`title`,`pageImageList`) VALUES (?,?,?,?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement stmt, PagePackage value) {
                if (value.packageId == null) {
                    stmt.bindNull(1);
                } else {
                    stmt.bindString(1, value.packageId);
                }
                stmt.bindLong(2, value.layoutType);
                if (value.contentType == null) {
                    stmt.bindNull(3);
                } else {
                    stmt.bindString(3, value.contentType);
                }
                if (value.title == null) {
                    stmt.bindNull(4);
                } else {
                    stmt.bindString(4, value.title);
                }
                String listToString = AdvTypeConverters.listToString(value.pageImageList);
                if (listToString == null) {
                    stmt.bindNull(5);
                } else {
                    stmt.bindString(5, listToString);
                }
            }
        };
        this.__insertionAdapterOfPackageItem = new EntityInsertionAdapter<PackageItem>(__db) { // from class: com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao_Impl.2
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `PackageItem` (`id`,`packageId`,`itemId`,`desc`,`size`,`md5`,`configMd5`,`status`,`title`,`packageName`,`downloadUrl`,`configUrl`,`versionCode`,`icon`,`adImage`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement stmt, PackageItem value) {
                stmt.bindLong(1, value.id);
                if (value.packageId == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.packageId);
                }
                if (value.itemId == null) {
                    stmt.bindNull(3);
                } else {
                    stmt.bindString(3, value.itemId);
                }
                if (value.desc == null) {
                    stmt.bindNull(4);
                } else {
                    stmt.bindString(4, value.desc);
                }
                if (value.size == null) {
                    stmt.bindNull(5);
                } else {
                    stmt.bindString(5, value.size);
                }
                if (value.md5 == null) {
                    stmt.bindNull(6);
                } else {
                    stmt.bindString(6, value.md5);
                }
                if (value.configMd5 == null) {
                    stmt.bindNull(7);
                } else {
                    stmt.bindString(7, value.configMd5);
                }
                if (value.status == null) {
                    stmt.bindNull(8);
                } else {
                    stmt.bindString(8, value.status);
                }
                if (value.title == null) {
                    stmt.bindNull(9);
                } else {
                    stmt.bindString(9, value.title);
                }
                if (value.packageName == null) {
                    stmt.bindNull(10);
                } else {
                    stmt.bindString(10, value.packageName);
                }
                if (value.downloadUrl == null) {
                    stmt.bindNull(11);
                } else {
                    stmt.bindString(11, value.downloadUrl);
                }
                if (value.configUrl == null) {
                    stmt.bindNull(12);
                } else {
                    stmt.bindString(12, value.configUrl);
                }
                if (value.versionCode == null) {
                    stmt.bindNull(13);
                } else {
                    stmt.bindString(13, value.versionCode);
                }
                if (value.icon == null) {
                    stmt.bindNull(14);
                } else {
                    stmt.bindString(14, value.icon);
                }
                String listToString = AdvTypeConverters.listToString(value.adImage);
                if (listToString == null) {
                    stmt.bindNull(15);
                } else {
                    stmt.bindString(15, listToString);
                }
            }
        };
        this.__insertionAdapterOfItemDependent = new EntityInsertionAdapter<ItemDependent>(__db) { // from class: com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao_Impl.3
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `ItemDependent` (`packageName`,`dependentId`,`dependentType`) VALUES (?,?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement stmt, ItemDependent value) {
                if (value.packageName == null) {
                    stmt.bindNull(1);
                } else {
                    stmt.bindString(1, value.packageName);
                }
                if (value.dependentId == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.dependentId);
                }
                stmt.bindLong(3, value.dependentType);
            }
        };
        this.__insertionAdapterOfDbItemDependentCrossRef = new EntityInsertionAdapter<DbItemDependentCrossRef>(__db) { // from class: com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao_Impl.4
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `DbItemDependentCrossRef` (`itemId`,`dependentId`) VALUES (?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement stmt, DbItemDependentCrossRef value) {
                if (value.itemId == null) {
                    stmt.bindNull(1);
                } else {
                    stmt.bindString(1, value.itemId);
                }
                if (value.dependentId == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.dependentId);
                }
            }
        };
        this.__preparedStmtOfClearPackages = new SharedSQLiteStatement(__db) { // from class: com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao_Impl.5
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "delete from PagePackage";
            }
        };
        this.__preparedStmtOfClearAppItems = new SharedSQLiteStatement(__db) { // from class: com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao_Impl.6
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "delete from PackageItem";
            }
        };
        this.__preparedStmtOfClearDependents = new SharedSQLiteStatement(__db) { // from class: com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao_Impl.7
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "delete from ItemDependent";
            }
        };
        this.__preparedStmtOfClearItemDependentCrossRefs = new SharedSQLiteStatement(__db) { // from class: com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao_Impl.8
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "delete from dbitemdependentcrossref";
            }
        };
    }

    @Override // com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao
    public void insertPackage(final List<PagePackage> packageList) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfPagePackage.insert(packageList);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao
    public void insertItem(final List<PackageItem> list) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfPackageItem.insert(list);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao
    public void insertItemDependents(final List<ItemDependent> list) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfItemDependent.insert(list);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao
    public void insertItemDependentCrossRefs(final List<DbItemDependentCrossRef> list) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfDbItemDependentCrossRef.insert(list);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao
    public void clearThenInsert(final List<PagePackage> packageList, final List<PackageItem> itemList, final List<ItemDependent> dependents, final List<DbItemDependentCrossRef> itemDependentCrossRefs) {
        this.__db.beginTransaction();
        try {
            super.clearThenInsert(packageList, itemList, dependents, itemDependentCrossRefs);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao
    public void clearPackages() {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.__preparedStmtOfClearPackages.acquire();
        this.__db.beginTransaction();
        try {
            acquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfClearPackages.release(acquire);
        }
    }

    @Override // com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao
    public void clearAppItems() {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.__preparedStmtOfClearAppItems.acquire();
        this.__db.beginTransaction();
        try {
            acquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfClearAppItems.release(acquire);
        }
    }

    @Override // com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao
    public void clearDependents() {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.__preparedStmtOfClearDependents.acquire();
        this.__db.beginTransaction();
        try {
            acquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfClearDependents.release(acquire);
        }
    }

    @Override // com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao
    public void clearItemDependentCrossRefs() {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.__preparedStmtOfClearItemDependentCrossRefs.acquire();
        this.__db.beginTransaction();
        try {
            acquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfClearItemDependentCrossRefs.release(acquire);
        }
    }

    @Override // com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao
    public LiveData<List<PackageWithItems>> getPackages() {
        final RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("select * from PagePackage", 0);
        return this.__db.getInvalidationTracker().createLiveData(new String[]{"DbItemDependentCrossRef", "ItemDependent", "PackageItem", "PagePackage"}, true, new Callable<List<PackageWithItems>>() { // from class: com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao_Impl.9
            /* JADX WARN: Removed duplicated region for block: B:30:0x00bf A[Catch: all -> 0x00f5, TryCatch #1 {all -> 0x00fa, blocks: (B:3:0x0009, B:36:0x00e8, B:4:0x0017, B:5:0x003a, B:7:0x0040, B:9:0x0046, B:11:0x0052, B:12:0x005b, B:13:0x006d, B:15:0x0073, B:17:0x0079, B:19:0x007f, B:21:0x0085, B:23:0x008b, B:28:0x00b9, B:30:0x00bf, B:33:0x00cd, B:34:0x00d2, B:27:0x0094, B:35:0x00df), top: B:45:0x0009 }] */
            /* JADX WARN: Removed duplicated region for block: B:31:0x00ca  */
            /* JADX WARN: Removed duplicated region for block: B:33:0x00cd A[Catch: all -> 0x00f5, TryCatch #1 {all -> 0x00fa, blocks: (B:3:0x0009, B:36:0x00e8, B:4:0x0017, B:5:0x003a, B:7:0x0040, B:9:0x0046, B:11:0x0052, B:12:0x005b, B:13:0x006d, B:15:0x0073, B:17:0x0079, B:19:0x007f, B:21:0x0085, B:23:0x008b, B:28:0x00b9, B:30:0x00bf, B:33:0x00cd, B:34:0x00d2, B:27:0x0094, B:35:0x00df), top: B:45:0x0009 }] */
            /* JADX WARN: Removed duplicated region for block: B:56:0x00d2 A[SYNTHETIC] */
            @Override // java.util.concurrent.Callable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public java.util.List<com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.PackageWithItems> call() throws java.lang.Exception {
                /*
                    r12 = this;
                    com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao_Impl r0 = com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao_Impl.this
                    androidx.room.RoomDatabase r0 = com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao_Impl.access$100(r0)
                    r0.beginTransaction()
                    com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao_Impl r0 = com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao_Impl.this     // Catch: java.lang.Throwable -> Lfa
                    androidx.room.RoomDatabase r0 = com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao_Impl.access$100(r0)     // Catch: java.lang.Throwable -> Lfa
                    androidx.room.RoomSQLiteQuery r1 = r2     // Catch: java.lang.Throwable -> Lfa
                    r2 = 1
                    r3 = 0
                    android.database.Cursor r0 = androidx.room.util.DBUtil.query(r0, r1, r2, r3)     // Catch: java.lang.Throwable -> Lfa
                    java.lang.String r1 = "packageId"
                    int r1 = androidx.room.util.CursorUtil.getColumnIndexOrThrow(r0, r1)     // Catch: java.lang.Throwable -> Lf5
                    java.lang.String r2 = "layoutType"
                    int r2 = androidx.room.util.CursorUtil.getColumnIndexOrThrow(r0, r2)     // Catch: java.lang.Throwable -> Lf5
                    java.lang.String r4 = "contentType"
                    int r4 = androidx.room.util.CursorUtil.getColumnIndexOrThrow(r0, r4)     // Catch: java.lang.Throwable -> Lf5
                    java.lang.String r5 = "title"
                    int r5 = androidx.room.util.CursorUtil.getColumnIndexOrThrow(r0, r5)     // Catch: java.lang.Throwable -> Lf5
                    java.lang.String r6 = "pageImageList"
                    int r6 = androidx.room.util.CursorUtil.getColumnIndexOrThrow(r0, r6)     // Catch: java.lang.Throwable -> Lf5
                    androidx.collection.ArrayMap r7 = new androidx.collection.ArrayMap     // Catch: java.lang.Throwable -> Lf5
                    r7.<init>()     // Catch: java.lang.Throwable -> Lf5
                L3a:
                    boolean r8 = r0.moveToNext()     // Catch: java.lang.Throwable -> Lf5
                    if (r8 == 0) goto L5b
                    boolean r8 = r0.isNull(r1)     // Catch: java.lang.Throwable -> Lf5
                    if (r8 != 0) goto L3a
                    java.lang.String r8 = r0.getString(r1)     // Catch: java.lang.Throwable -> Lf5
                    java.lang.Object r9 = r7.get(r8)     // Catch: java.lang.Throwable -> Lf5
                    java.util.ArrayList r9 = (java.util.ArrayList) r9     // Catch: java.lang.Throwable -> Lf5
                    if (r9 != 0) goto L3a
                    java.util.ArrayList r9 = new java.util.ArrayList     // Catch: java.lang.Throwable -> Lf5
                    r9.<init>()     // Catch: java.lang.Throwable -> Lf5
                    r7.put(r8, r9)     // Catch: java.lang.Throwable -> Lf5
                    goto L3a
                L5b:
                    r8 = -1
                    r0.moveToPosition(r8)     // Catch: java.lang.Throwable -> Lf5
                    com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao_Impl r8 = com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao_Impl.this     // Catch: java.lang.Throwable -> Lf5
                    com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao_Impl.access$200(r8, r7)     // Catch: java.lang.Throwable -> Lf5
                    java.util.ArrayList r8 = new java.util.ArrayList     // Catch: java.lang.Throwable -> Lf5
                    int r9 = r0.getCount()     // Catch: java.lang.Throwable -> Lf5
                    r8.<init>(r9)     // Catch: java.lang.Throwable -> Lf5
                L6d:
                    boolean r9 = r0.moveToNext()     // Catch: java.lang.Throwable -> Lf5
                    if (r9 == 0) goto Ldf
                    boolean r9 = r0.isNull(r1)     // Catch: java.lang.Throwable -> Lf5
                    if (r9 == 0) goto L94
                    boolean r9 = r0.isNull(r2)     // Catch: java.lang.Throwable -> Lf5
                    if (r9 == 0) goto L94
                    boolean r9 = r0.isNull(r4)     // Catch: java.lang.Throwable -> Lf5
                    if (r9 == 0) goto L94
                    boolean r9 = r0.isNull(r5)     // Catch: java.lang.Throwable -> Lf5
                    if (r9 == 0) goto L94
                    boolean r9 = r0.isNull(r6)     // Catch: java.lang.Throwable -> Lf5
                    if (r9 != 0) goto L92
                    goto L94
                L92:
                    r10 = r3
                    goto Lb9
                L94:
                    java.lang.String r9 = r0.getString(r1)     // Catch: java.lang.Throwable -> Lf5
                    com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.PagePackage r10 = new com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.PagePackage     // Catch: java.lang.Throwable -> Lf5
                    r10.<init>(r9)     // Catch: java.lang.Throwable -> Lf5
                    int r9 = r0.getInt(r2)     // Catch: java.lang.Throwable -> Lf5
                    r10.layoutType = r9     // Catch: java.lang.Throwable -> Lf5
                    java.lang.String r9 = r0.getString(r4)     // Catch: java.lang.Throwable -> Lf5
                    r10.contentType = r9     // Catch: java.lang.Throwable -> Lf5
                    java.lang.String r9 = r0.getString(r5)     // Catch: java.lang.Throwable -> Lf5
                    r10.title = r9     // Catch: java.lang.Throwable -> Lf5
                    java.lang.String r9 = r0.getString(r6)     // Catch: java.lang.Throwable -> Lf5
                    java.util.List r9 = com.xiaopeng.appstore.appstore_biz.datamodel.db.AdvTypeConverters.stringToList(r9)     // Catch: java.lang.Throwable -> Lf5
                    r10.pageImageList = r9     // Catch: java.lang.Throwable -> Lf5
                Lb9:
                    boolean r9 = r0.isNull(r1)     // Catch: java.lang.Throwable -> Lf5
                    if (r9 != 0) goto Lca
                    java.lang.String r9 = r0.getString(r1)     // Catch: java.lang.Throwable -> Lf5
                    java.lang.Object r9 = r7.get(r9)     // Catch: java.lang.Throwable -> Lf5
                    java.util.ArrayList r9 = (java.util.ArrayList) r9     // Catch: java.lang.Throwable -> Lf5
                    goto Lcb
                Lca:
                    r9 = r3
                Lcb:
                    if (r9 != 0) goto Ld2
                    java.util.ArrayList r9 = new java.util.ArrayList     // Catch: java.lang.Throwable -> Lf5
                    r9.<init>()     // Catch: java.lang.Throwable -> Lf5
                Ld2:
                    com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.PackageWithItems r11 = new com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.PackageWithItems     // Catch: java.lang.Throwable -> Lf5
                    r11.<init>()     // Catch: java.lang.Throwable -> Lf5
                    r11.pagePackage = r10     // Catch: java.lang.Throwable -> Lf5
                    r11.items = r9     // Catch: java.lang.Throwable -> Lf5
                    r8.add(r11)     // Catch: java.lang.Throwable -> Lf5
                    goto L6d
                Ldf:
                    com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao_Impl r1 = com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao_Impl.this     // Catch: java.lang.Throwable -> Lf5
                    androidx.room.RoomDatabase r1 = com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao_Impl.access$100(r1)     // Catch: java.lang.Throwable -> Lf5
                    r1.setTransactionSuccessful()     // Catch: java.lang.Throwable -> Lf5
                    r0.close()     // Catch: java.lang.Throwable -> Lfa
                    com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao_Impl r0 = com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao_Impl.this
                    androidx.room.RoomDatabase r0 = com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao_Impl.access$100(r0)
                    r0.endTransaction()
                    return r8
                Lf5:
                    r1 = move-exception
                    r0.close()     // Catch: java.lang.Throwable -> Lfa
                    throw r1     // Catch: java.lang.Throwable -> Lfa
                Lfa:
                    r0 = move-exception
                    com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao_Impl r1 = com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao_Impl.this
                    androidx.room.RoomDatabase r1 = com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao_Impl.access$100(r1)
                    r1.endTransaction()
                    throw r0
                */
                throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao_Impl.AnonymousClass9.call():java.util.List");
            }

            protected void finalize() {
                acquire.release();
            }
        });
    }

    private void __fetchRelationshipItemDependentAscomXiaopengAppstoreAppstoreBizDatamodelEntities2HomeItemDependent(final ArrayMap<String, ArrayList<ItemDependent>> _map) {
        ArrayList<ItemDependent> arrayList;
        Set<String> keySet = _map.keySet();
        if (keySet.isEmpty()) {
            return;
        }
        if (_map.size() > 999) {
            ArrayMap<String, ArrayList<ItemDependent>> arrayMap = new ArrayMap<>(999);
            int size = _map.size();
            int i = 0;
            int i2 = 0;
            while (i < size) {
                arrayMap.put(_map.keyAt(i), _map.valueAt(i));
                i++;
                i2++;
                if (i2 == 999) {
                    __fetchRelationshipItemDependentAscomXiaopengAppstoreAppstoreBizDatamodelEntities2HomeItemDependent(arrayMap);
                    arrayMap = new ArrayMap<>(999);
                    i2 = 0;
                }
            }
            if (i2 > 0) {
                __fetchRelationshipItemDependentAscomXiaopengAppstoreAppstoreBizDatamodelEntities2HomeItemDependent(arrayMap);
                return;
            }
            return;
        }
        StringBuilder newStringBuilder = StringUtil.newStringBuilder();
        newStringBuilder.append("SELECT `ItemDependent`.`packageName` AS `packageName`,`ItemDependent`.`dependentId` AS `dependentId`,`ItemDependent`.`dependentType` AS `dependentType`,_junction.`itemId` FROM `DbItemDependentCrossRef` AS _junction INNER JOIN `ItemDependent` ON (_junction.`dependentId` = `ItemDependent`.`dependentId`) WHERE _junction.`itemId` IN (");
        int size2 = keySet.size();
        StringUtil.appendPlaceholders(newStringBuilder, size2);
        newStringBuilder.append(")");
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire(newStringBuilder.toString(), size2 + 0);
        int i3 = 1;
        for (String str : keySet) {
            if (str == null) {
                acquire.bindNull(i3);
            } else {
                acquire.bindString(i3, str);
            }
            i3++;
        }
        Cursor query = DBUtil.query(this.__db, acquire, false, null);
        try {
            int columnIndex = CursorUtil.getColumnIndex(query, VuiConstants.SCENE_PACKAGE_NAME);
            int columnIndex2 = CursorUtil.getColumnIndex(query, "dependentId");
            int columnIndex3 = CursorUtil.getColumnIndex(query, "dependentType");
            while (query.moveToNext()) {
                if (!query.isNull(3) && (arrayList = _map.get(query.getString(3))) != null) {
                    ItemDependent itemDependent = new ItemDependent(columnIndex2 == -1 ? null : query.getString(columnIndex2));
                    if (columnIndex != -1) {
                        itemDependent.packageName = query.getString(columnIndex);
                    }
                    if (columnIndex3 != -1) {
                        itemDependent.dependentType = query.getInt(columnIndex3);
                    }
                    arrayList.add(itemDependent);
                }
            }
        } finally {
            query.close();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0203  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0207 A[Catch: all -> 0x0328, TryCatch #0 {all -> 0x0328, blocks: (B:24:0x008f, B:29:0x009a, B:30:0x00ff, B:32:0x0105, B:34:0x010b, B:36:0x0119, B:39:0x012b, B:40:0x0136, B:42:0x013c, B:44:0x0142, B:48:0x0151, B:115:0x021d, B:117:0x0225, B:120:0x0234, B:122:0x023c, B:124:0x0244, B:126:0x024c, B:128:0x0254, B:130:0x025c, B:132:0x0264, B:134:0x026c, B:136:0x0274, B:139:0x027d, B:142:0x0287, B:145:0x0291, B:148:0x02a9, B:150:0x02af, B:153:0x02c9, B:154:0x02ce, B:114:0x0215, B:111:0x0207, B:56:0x016c, B:59:0x0174, B:62:0x017c, B:65:0x0184, B:68:0x018c, B:71:0x0194, B:74:0x019c, B:77:0x01a4, B:81:0x01ae, B:87:0x01c0, B:93:0x01d2, B:97:0x01de, B:101:0x01ea, B:107:0x01fb), top: B:165:0x008f }] */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0211  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0215 A[Catch: all -> 0x0328, TryCatch #0 {all -> 0x0328, blocks: (B:24:0x008f, B:29:0x009a, B:30:0x00ff, B:32:0x0105, B:34:0x010b, B:36:0x0119, B:39:0x012b, B:40:0x0136, B:42:0x013c, B:44:0x0142, B:48:0x0151, B:115:0x021d, B:117:0x0225, B:120:0x0234, B:122:0x023c, B:124:0x0244, B:126:0x024c, B:128:0x0254, B:130:0x025c, B:132:0x0264, B:134:0x026c, B:136:0x0274, B:139:0x027d, B:142:0x0287, B:145:0x0291, B:148:0x02a9, B:150:0x02af, B:153:0x02c9, B:154:0x02ce, B:114:0x0215, B:111:0x0207, B:56:0x016c, B:59:0x0174, B:62:0x017c, B:65:0x0184, B:68:0x018c, B:71:0x0194, B:74:0x019c, B:77:0x01a4, B:81:0x01ae, B:87:0x01c0, B:93:0x01d2, B:97:0x01de, B:101:0x01ea, B:107:0x01fb), top: B:165:0x008f }] */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0225 A[Catch: all -> 0x0328, TryCatch #0 {all -> 0x0328, blocks: (B:24:0x008f, B:29:0x009a, B:30:0x00ff, B:32:0x0105, B:34:0x010b, B:36:0x0119, B:39:0x012b, B:40:0x0136, B:42:0x013c, B:44:0x0142, B:48:0x0151, B:115:0x021d, B:117:0x0225, B:120:0x0234, B:122:0x023c, B:124:0x0244, B:126:0x024c, B:128:0x0254, B:130:0x025c, B:132:0x0264, B:134:0x026c, B:136:0x0274, B:139:0x027d, B:142:0x0287, B:145:0x0291, B:148:0x02a9, B:150:0x02af, B:153:0x02c9, B:154:0x02ce, B:114:0x0215, B:111:0x0207, B:56:0x016c, B:59:0x0174, B:62:0x017c, B:65:0x0184, B:68:0x018c, B:71:0x0194, B:74:0x019c, B:77:0x01a4, B:81:0x01ae, B:87:0x01c0, B:93:0x01d2, B:97:0x01de, B:101:0x01ea, B:107:0x01fb), top: B:165:0x008f }] */
    /* JADX WARN: Removed duplicated region for block: B:118:0x022f  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0234 A[Catch: all -> 0x0328, TryCatch #0 {all -> 0x0328, blocks: (B:24:0x008f, B:29:0x009a, B:30:0x00ff, B:32:0x0105, B:34:0x010b, B:36:0x0119, B:39:0x012b, B:40:0x0136, B:42:0x013c, B:44:0x0142, B:48:0x0151, B:115:0x021d, B:117:0x0225, B:120:0x0234, B:122:0x023c, B:124:0x0244, B:126:0x024c, B:128:0x0254, B:130:0x025c, B:132:0x0264, B:134:0x026c, B:136:0x0274, B:139:0x027d, B:142:0x0287, B:145:0x0291, B:148:0x02a9, B:150:0x02af, B:153:0x02c9, B:154:0x02ce, B:114:0x0215, B:111:0x0207, B:56:0x016c, B:59:0x0174, B:62:0x017c, B:65:0x0184, B:68:0x018c, B:71:0x0194, B:74:0x019c, B:77:0x01a4, B:81:0x01ae, B:87:0x01c0, B:93:0x01d2, B:97:0x01de, B:101:0x01ea, B:107:0x01fb), top: B:165:0x008f }] */
    /* JADX WARN: Removed duplicated region for block: B:122:0x023c A[Catch: all -> 0x0328, TryCatch #0 {all -> 0x0328, blocks: (B:24:0x008f, B:29:0x009a, B:30:0x00ff, B:32:0x0105, B:34:0x010b, B:36:0x0119, B:39:0x012b, B:40:0x0136, B:42:0x013c, B:44:0x0142, B:48:0x0151, B:115:0x021d, B:117:0x0225, B:120:0x0234, B:122:0x023c, B:124:0x0244, B:126:0x024c, B:128:0x0254, B:130:0x025c, B:132:0x0264, B:134:0x026c, B:136:0x0274, B:139:0x027d, B:142:0x0287, B:145:0x0291, B:148:0x02a9, B:150:0x02af, B:153:0x02c9, B:154:0x02ce, B:114:0x0215, B:111:0x0207, B:56:0x016c, B:59:0x0174, B:62:0x017c, B:65:0x0184, B:68:0x018c, B:71:0x0194, B:74:0x019c, B:77:0x01a4, B:81:0x01ae, B:87:0x01c0, B:93:0x01d2, B:97:0x01de, B:101:0x01ea, B:107:0x01fb), top: B:165:0x008f }] */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0244 A[Catch: all -> 0x0328, TryCatch #0 {all -> 0x0328, blocks: (B:24:0x008f, B:29:0x009a, B:30:0x00ff, B:32:0x0105, B:34:0x010b, B:36:0x0119, B:39:0x012b, B:40:0x0136, B:42:0x013c, B:44:0x0142, B:48:0x0151, B:115:0x021d, B:117:0x0225, B:120:0x0234, B:122:0x023c, B:124:0x0244, B:126:0x024c, B:128:0x0254, B:130:0x025c, B:132:0x0264, B:134:0x026c, B:136:0x0274, B:139:0x027d, B:142:0x0287, B:145:0x0291, B:148:0x02a9, B:150:0x02af, B:153:0x02c9, B:154:0x02ce, B:114:0x0215, B:111:0x0207, B:56:0x016c, B:59:0x0174, B:62:0x017c, B:65:0x0184, B:68:0x018c, B:71:0x0194, B:74:0x019c, B:77:0x01a4, B:81:0x01ae, B:87:0x01c0, B:93:0x01d2, B:97:0x01de, B:101:0x01ea, B:107:0x01fb), top: B:165:0x008f }] */
    /* JADX WARN: Removed duplicated region for block: B:126:0x024c A[Catch: all -> 0x0328, TryCatch #0 {all -> 0x0328, blocks: (B:24:0x008f, B:29:0x009a, B:30:0x00ff, B:32:0x0105, B:34:0x010b, B:36:0x0119, B:39:0x012b, B:40:0x0136, B:42:0x013c, B:44:0x0142, B:48:0x0151, B:115:0x021d, B:117:0x0225, B:120:0x0234, B:122:0x023c, B:124:0x0244, B:126:0x024c, B:128:0x0254, B:130:0x025c, B:132:0x0264, B:134:0x026c, B:136:0x0274, B:139:0x027d, B:142:0x0287, B:145:0x0291, B:148:0x02a9, B:150:0x02af, B:153:0x02c9, B:154:0x02ce, B:114:0x0215, B:111:0x0207, B:56:0x016c, B:59:0x0174, B:62:0x017c, B:65:0x0184, B:68:0x018c, B:71:0x0194, B:74:0x019c, B:77:0x01a4, B:81:0x01ae, B:87:0x01c0, B:93:0x01d2, B:97:0x01de, B:101:0x01ea, B:107:0x01fb), top: B:165:0x008f }] */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0254 A[Catch: all -> 0x0328, TryCatch #0 {all -> 0x0328, blocks: (B:24:0x008f, B:29:0x009a, B:30:0x00ff, B:32:0x0105, B:34:0x010b, B:36:0x0119, B:39:0x012b, B:40:0x0136, B:42:0x013c, B:44:0x0142, B:48:0x0151, B:115:0x021d, B:117:0x0225, B:120:0x0234, B:122:0x023c, B:124:0x0244, B:126:0x024c, B:128:0x0254, B:130:0x025c, B:132:0x0264, B:134:0x026c, B:136:0x0274, B:139:0x027d, B:142:0x0287, B:145:0x0291, B:148:0x02a9, B:150:0x02af, B:153:0x02c9, B:154:0x02ce, B:114:0x0215, B:111:0x0207, B:56:0x016c, B:59:0x0174, B:62:0x017c, B:65:0x0184, B:68:0x018c, B:71:0x0194, B:74:0x019c, B:77:0x01a4, B:81:0x01ae, B:87:0x01c0, B:93:0x01d2, B:97:0x01de, B:101:0x01ea, B:107:0x01fb), top: B:165:0x008f }] */
    /* JADX WARN: Removed duplicated region for block: B:130:0x025c A[Catch: all -> 0x0328, TryCatch #0 {all -> 0x0328, blocks: (B:24:0x008f, B:29:0x009a, B:30:0x00ff, B:32:0x0105, B:34:0x010b, B:36:0x0119, B:39:0x012b, B:40:0x0136, B:42:0x013c, B:44:0x0142, B:48:0x0151, B:115:0x021d, B:117:0x0225, B:120:0x0234, B:122:0x023c, B:124:0x0244, B:126:0x024c, B:128:0x0254, B:130:0x025c, B:132:0x0264, B:134:0x026c, B:136:0x0274, B:139:0x027d, B:142:0x0287, B:145:0x0291, B:148:0x02a9, B:150:0x02af, B:153:0x02c9, B:154:0x02ce, B:114:0x0215, B:111:0x0207, B:56:0x016c, B:59:0x0174, B:62:0x017c, B:65:0x0184, B:68:0x018c, B:71:0x0194, B:74:0x019c, B:77:0x01a4, B:81:0x01ae, B:87:0x01c0, B:93:0x01d2, B:97:0x01de, B:101:0x01ea, B:107:0x01fb), top: B:165:0x008f }] */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0264 A[Catch: all -> 0x0328, TryCatch #0 {all -> 0x0328, blocks: (B:24:0x008f, B:29:0x009a, B:30:0x00ff, B:32:0x0105, B:34:0x010b, B:36:0x0119, B:39:0x012b, B:40:0x0136, B:42:0x013c, B:44:0x0142, B:48:0x0151, B:115:0x021d, B:117:0x0225, B:120:0x0234, B:122:0x023c, B:124:0x0244, B:126:0x024c, B:128:0x0254, B:130:0x025c, B:132:0x0264, B:134:0x026c, B:136:0x0274, B:139:0x027d, B:142:0x0287, B:145:0x0291, B:148:0x02a9, B:150:0x02af, B:153:0x02c9, B:154:0x02ce, B:114:0x0215, B:111:0x0207, B:56:0x016c, B:59:0x0174, B:62:0x017c, B:65:0x0184, B:68:0x018c, B:71:0x0194, B:74:0x019c, B:77:0x01a4, B:81:0x01ae, B:87:0x01c0, B:93:0x01d2, B:97:0x01de, B:101:0x01ea, B:107:0x01fb), top: B:165:0x008f }] */
    /* JADX WARN: Removed duplicated region for block: B:134:0x026c A[Catch: all -> 0x0328, TryCatch #0 {all -> 0x0328, blocks: (B:24:0x008f, B:29:0x009a, B:30:0x00ff, B:32:0x0105, B:34:0x010b, B:36:0x0119, B:39:0x012b, B:40:0x0136, B:42:0x013c, B:44:0x0142, B:48:0x0151, B:115:0x021d, B:117:0x0225, B:120:0x0234, B:122:0x023c, B:124:0x0244, B:126:0x024c, B:128:0x0254, B:130:0x025c, B:132:0x0264, B:134:0x026c, B:136:0x0274, B:139:0x027d, B:142:0x0287, B:145:0x0291, B:148:0x02a9, B:150:0x02af, B:153:0x02c9, B:154:0x02ce, B:114:0x0215, B:111:0x0207, B:56:0x016c, B:59:0x0174, B:62:0x017c, B:65:0x0184, B:68:0x018c, B:71:0x0194, B:74:0x019c, B:77:0x01a4, B:81:0x01ae, B:87:0x01c0, B:93:0x01d2, B:97:0x01de, B:101:0x01ea, B:107:0x01fb), top: B:165:0x008f }] */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0274 A[Catch: all -> 0x0328, TryCatch #0 {all -> 0x0328, blocks: (B:24:0x008f, B:29:0x009a, B:30:0x00ff, B:32:0x0105, B:34:0x010b, B:36:0x0119, B:39:0x012b, B:40:0x0136, B:42:0x013c, B:44:0x0142, B:48:0x0151, B:115:0x021d, B:117:0x0225, B:120:0x0234, B:122:0x023c, B:124:0x0244, B:126:0x024c, B:128:0x0254, B:130:0x025c, B:132:0x0264, B:134:0x026c, B:136:0x0274, B:139:0x027d, B:142:0x0287, B:145:0x0291, B:148:0x02a9, B:150:0x02af, B:153:0x02c9, B:154:0x02ce, B:114:0x0215, B:111:0x0207, B:56:0x016c, B:59:0x0174, B:62:0x017c, B:65:0x0184, B:68:0x018c, B:71:0x0194, B:74:0x019c, B:77:0x01a4, B:81:0x01ae, B:87:0x01c0, B:93:0x01d2, B:97:0x01de, B:101:0x01ea, B:107:0x01fb), top: B:165:0x008f }] */
    /* JADX WARN: Removed duplicated region for block: B:139:0x027d A[Catch: all -> 0x0328, TryCatch #0 {all -> 0x0328, blocks: (B:24:0x008f, B:29:0x009a, B:30:0x00ff, B:32:0x0105, B:34:0x010b, B:36:0x0119, B:39:0x012b, B:40:0x0136, B:42:0x013c, B:44:0x0142, B:48:0x0151, B:115:0x021d, B:117:0x0225, B:120:0x0234, B:122:0x023c, B:124:0x0244, B:126:0x024c, B:128:0x0254, B:130:0x025c, B:132:0x0264, B:134:0x026c, B:136:0x0274, B:139:0x027d, B:142:0x0287, B:145:0x0291, B:148:0x02a9, B:150:0x02af, B:153:0x02c9, B:154:0x02ce, B:114:0x0215, B:111:0x0207, B:56:0x016c, B:59:0x0174, B:62:0x017c, B:65:0x0184, B:68:0x018c, B:71:0x0194, B:74:0x019c, B:77:0x01a4, B:81:0x01ae, B:87:0x01c0, B:93:0x01d2, B:97:0x01de, B:101:0x01ea, B:107:0x01fb), top: B:165:0x008f }] */
    /* JADX WARN: Removed duplicated region for block: B:142:0x0287 A[Catch: all -> 0x0328, TryCatch #0 {all -> 0x0328, blocks: (B:24:0x008f, B:29:0x009a, B:30:0x00ff, B:32:0x0105, B:34:0x010b, B:36:0x0119, B:39:0x012b, B:40:0x0136, B:42:0x013c, B:44:0x0142, B:48:0x0151, B:115:0x021d, B:117:0x0225, B:120:0x0234, B:122:0x023c, B:124:0x0244, B:126:0x024c, B:128:0x0254, B:130:0x025c, B:132:0x0264, B:134:0x026c, B:136:0x0274, B:139:0x027d, B:142:0x0287, B:145:0x0291, B:148:0x02a9, B:150:0x02af, B:153:0x02c9, B:154:0x02ce, B:114:0x0215, B:111:0x0207, B:56:0x016c, B:59:0x0174, B:62:0x017c, B:65:0x0184, B:68:0x018c, B:71:0x0194, B:74:0x019c, B:77:0x01a4, B:81:0x01ae, B:87:0x01c0, B:93:0x01d2, B:97:0x01de, B:101:0x01ea, B:107:0x01fb), top: B:165:0x008f }] */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0291 A[Catch: all -> 0x0328, TryCatch #0 {all -> 0x0328, blocks: (B:24:0x008f, B:29:0x009a, B:30:0x00ff, B:32:0x0105, B:34:0x010b, B:36:0x0119, B:39:0x012b, B:40:0x0136, B:42:0x013c, B:44:0x0142, B:48:0x0151, B:115:0x021d, B:117:0x0225, B:120:0x0234, B:122:0x023c, B:124:0x0244, B:126:0x024c, B:128:0x0254, B:130:0x025c, B:132:0x0264, B:134:0x026c, B:136:0x0274, B:139:0x027d, B:142:0x0287, B:145:0x0291, B:148:0x02a9, B:150:0x02af, B:153:0x02c9, B:154:0x02ce, B:114:0x0215, B:111:0x0207, B:56:0x016c, B:59:0x0174, B:62:0x017c, B:65:0x0184, B:68:0x018c, B:71:0x0194, B:74:0x019c, B:77:0x01a4, B:81:0x01ae, B:87:0x01c0, B:93:0x01d2, B:97:0x01de, B:101:0x01ea, B:107:0x01fb), top: B:165:0x008f }] */
    /* JADX WARN: Removed duplicated region for block: B:150:0x02af A[Catch: all -> 0x0328, TryCatch #0 {all -> 0x0328, blocks: (B:24:0x008f, B:29:0x009a, B:30:0x00ff, B:32:0x0105, B:34:0x010b, B:36:0x0119, B:39:0x012b, B:40:0x0136, B:42:0x013c, B:44:0x0142, B:48:0x0151, B:115:0x021d, B:117:0x0225, B:120:0x0234, B:122:0x023c, B:124:0x0244, B:126:0x024c, B:128:0x0254, B:130:0x025c, B:132:0x0264, B:134:0x026c, B:136:0x0274, B:139:0x027d, B:142:0x0287, B:145:0x0291, B:148:0x02a9, B:150:0x02af, B:153:0x02c9, B:154:0x02ce, B:114:0x0215, B:111:0x0207, B:56:0x016c, B:59:0x0174, B:62:0x017c, B:65:0x0184, B:68:0x018c, B:71:0x0194, B:74:0x019c, B:77:0x01a4, B:81:0x01ae, B:87:0x01c0, B:93:0x01d2, B:97:0x01de, B:101:0x01ea, B:107:0x01fb), top: B:165:0x008f }] */
    /* JADX WARN: Removed duplicated region for block: B:151:0x02c0  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x02c9 A[Catch: all -> 0x0328, TryCatch #0 {all -> 0x0328, blocks: (B:24:0x008f, B:29:0x009a, B:30:0x00ff, B:32:0x0105, B:34:0x010b, B:36:0x0119, B:39:0x012b, B:40:0x0136, B:42:0x013c, B:44:0x0142, B:48:0x0151, B:115:0x021d, B:117:0x0225, B:120:0x0234, B:122:0x023c, B:124:0x0244, B:126:0x024c, B:128:0x0254, B:130:0x025c, B:132:0x0264, B:134:0x026c, B:136:0x0274, B:139:0x027d, B:142:0x0287, B:145:0x0291, B:148:0x02a9, B:150:0x02af, B:153:0x02c9, B:154:0x02ce, B:114:0x0215, B:111:0x0207, B:56:0x016c, B:59:0x0174, B:62:0x017c, B:65:0x0184, B:68:0x018c, B:71:0x0194, B:74:0x019c, B:77:0x01a4, B:81:0x01ae, B:87:0x01c0, B:93:0x01d2, B:97:0x01de, B:101:0x01ea, B:107:0x01fb), top: B:165:0x008f }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void __fetchRelationshipPackageItemAscomXiaopengAppstoreAppstoreBizDatamodelEntities2HomeItemWithDependents(final androidx.collection.ArrayMap<java.lang.String, java.util.ArrayList<com.xiaopeng.appstore.appstore_biz.datamodel.entities2.home.ItemWithDependents>> r25) {
        /*
            Method dump skipped, instructions count: 813
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.appstore.appstore_biz.datamodel.db.HomeDao_Impl.__fetchRelationshipPackageItemAscomXiaopengAppstoreAppstoreBizDatamodelEntities2HomeItemWithDependents(androidx.collection.ArrayMap):void");
    }
}
