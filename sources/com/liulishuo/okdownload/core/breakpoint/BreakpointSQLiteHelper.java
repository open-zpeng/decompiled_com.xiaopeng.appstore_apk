package com.liulishuo.okdownload.core.breakpoint;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.SparseArray;
import com.liulishuo.okdownload.core.exception.SQLiteException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
/* loaded from: classes2.dex */
public class BreakpointSQLiteHelper extends SQLiteOpenHelper {
    private static final String BLOCK_TABLE_NAME = "block";
    private static final String BREAKPOINT_TABLE_NAME = "breakpoint";
    private static final String NAME = "okdownload-breakpoint.db";
    private static final String RESPONSE_FILENAME_TABLE_NAME = "okdownloadResponseFilename";
    static final String TASK_FILE_DIRTY_TABLE_NAME = "taskFileDirty";
    private static final int VERSION = 3;

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public BreakpointSQLiteHelper(Context context) {
        super(context, NAME, (SQLiteDatabase.CursorFactory) null, 3);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onOpen(SQLiteDatabase sQLiteDatabase) {
        super.onOpen(sQLiteDatabase);
        if (Build.VERSION.SDK_INT >= 16) {
            setWriteAheadLoggingEnabled(true);
        } else {
            sQLiteDatabase.enableWriteAheadLogging();
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS breakpoint( id INTEGER PRIMARY KEY, url VARCHAR NOT NULL, etag VARCHAR, parent_path VARCHAR NOT NULL, filename VARCHAR, task_only_parent_path TINYINT(1) DEFAULT 0, chunked TINYINT(1) DEFAULT 0)");
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS block( id INTEGER PRIMARY KEY AUTOINCREMENT, breakpoint_id INTEGER, block_index INTEGER, start_offset INTEGER, content_length INTEGER, current_offset INTEGER)");
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS okdownloadResponseFilename( url VARCHAR NOT NULL PRIMARY KEY, filename VARCHAR NOT NULL)");
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS taskFileDirty( id INTEGER PRIMARY KEY)");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i == 1 && i2 == 2) {
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS okdownloadResponseFilename( url VARCHAR NOT NULL PRIMARY KEY, filename VARCHAR NOT NULL)");
        }
        if (i <= 2) {
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS taskFileDirty( id INTEGER PRIMARY KEY)");
        }
    }

    public void markFileDirty(int i) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues(1);
        contentValues.put("id", Integer.valueOf(i));
        writableDatabase.insert(TASK_FILE_DIRTY_TABLE_NAME, null, contentValues);
    }

    public void markFileClear(int i) {
        getWritableDatabase().delete(TASK_FILE_DIRTY_TABLE_NAME, "id = ?", new String[]{String.valueOf(i)});
    }

    public List<Integer> loadDirtyFileList() {
        ArrayList arrayList = new ArrayList();
        Cursor cursor = null;
        try {
            cursor = getWritableDatabase().rawQuery("SELECT * FROM taskFileDirty", null);
            while (cursor.moveToNext()) {
                arrayList.add(Integer.valueOf(cursor.getInt(cursor.getColumnIndex("id"))));
            }
            return arrayList;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public SparseArray<BreakpointInfo> loadToCache() {
        Cursor cursor;
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ArrayList<BreakpointInfoRow> arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Cursor cursor2 = null;
        try {
            Cursor rawQuery = writableDatabase.rawQuery("SELECT * FROM breakpoint", null);
            while (rawQuery.moveToNext()) {
                try {
                    arrayList.add(new BreakpointInfoRow(rawQuery));
                } catch (Throwable th) {
                    th = th;
                    cursor = cursor2;
                    cursor2 = rawQuery;
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
            cursor2 = writableDatabase.rawQuery("SELECT * FROM block", null);
            while (cursor2.moveToNext()) {
                arrayList2.add(new BlockInfoRow(cursor2));
            }
            if (rawQuery != null) {
                rawQuery.close();
            }
            if (cursor2 != null) {
                cursor2.close();
            }
            SparseArray<BreakpointInfo> sparseArray = new SparseArray<>();
            for (BreakpointInfoRow breakpointInfoRow : arrayList) {
                BreakpointInfo info = breakpointInfoRow.toInfo();
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    BlockInfoRow blockInfoRow = (BlockInfoRow) it.next();
                    if (blockInfoRow.getBreakpointId() == info.id) {
                        info.addBlock(blockInfoRow.toInfo());
                        it.remove();
                    }
                }
                sparseArray.put(info.id, info);
            }
            return sparseArray;
        } catch (Throwable th2) {
            th = th2;
            cursor = null;
        }
    }

    public HashMap<String, String> loadResponseFilenameToMap() {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        HashMap<String, String> hashMap = new HashMap<>();
        Cursor cursor = null;
        try {
            cursor = writableDatabase.rawQuery("SELECT * FROM okdownloadResponseFilename", null);
            while (cursor.moveToNext()) {
                hashMap.put(cursor.getString(cursor.getColumnIndex("url")), cursor.getString(cursor.getColumnIndex(BreakpointSQLiteKey.FILENAME)));
            }
            return hashMap;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void updateFilename(String str, String str2) {
        Cursor rawQuery;
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues(2);
        contentValues.put("url", str);
        contentValues.put(BreakpointSQLiteKey.FILENAME, str2);
        synchronized (str.intern()) {
            Cursor cursor = null;
            try {
                rawQuery = writableDatabase.rawQuery("SELECT filename FROM okdownloadResponseFilename WHERE url = ?", new String[]{str});
            } catch (Throwable th) {
                th = th;
            }
            try {
                if (rawQuery.moveToFirst()) {
                    if (!str2.equals(rawQuery.getString(rawQuery.getColumnIndex(BreakpointSQLiteKey.FILENAME)))) {
                        writableDatabase.replace(RESPONSE_FILENAME_TABLE_NAME, null, contentValues);
                    }
                } else {
                    writableDatabase.insert(RESPONSE_FILENAME_TABLE_NAME, null, contentValues);
                }
                if (rawQuery != null) {
                    rawQuery.close();
                }
            } catch (Throwable th2) {
                th = th2;
                cursor = rawQuery;
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        }
    }

    public void insert(BreakpointInfo breakpointInfo) throws IOException {
        int blockCount = breakpointInfo.getBlockCount();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        for (int i = 0; i < blockCount; i++) {
            BlockInfo block = breakpointInfo.getBlock(i);
            if (writableDatabase.insert(BLOCK_TABLE_NAME, null, toValues(breakpointInfo.id, i, block)) == -1) {
                throw new SQLiteException("insert block " + block + " failed!");
            }
        }
        if (writableDatabase.insert(BREAKPOINT_TABLE_NAME, null, toValues(breakpointInfo)) == -1) {
            throw new SQLiteException("insert info " + breakpointInfo + " failed!");
        }
    }

    public void updateBlockIncrease(BreakpointInfo breakpointInfo, int i, long j) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BreakpointSQLiteKey.CURRENT_OFFSET, Long.valueOf(j));
        getWritableDatabase().update(BLOCK_TABLE_NAME, contentValues, "breakpoint_id = ? AND block_index = ?", new String[]{Integer.toString(breakpointInfo.id), Integer.toString(i)});
    }

    public void updateInfo(BreakpointInfo breakpointInfo) throws IOException {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.beginTransaction();
        Cursor cursor = null;
        try {
            cursor = getWritableDatabase().rawQuery("SELECT id FROM breakpoint WHERE id =" + breakpointInfo.id + " LIMIT 1", null);
            if (cursor.moveToNext()) {
                removeInfo(breakpointInfo.id);
                insert(breakpointInfo);
                writableDatabase.setTransactionSuccessful();
                if (cursor != null) {
                    cursor.close();
                }
                writableDatabase.endTransaction();
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            writableDatabase.endTransaction();
        }
    }

    public void removeInfo(int i) {
        getWritableDatabase().delete(BREAKPOINT_TABLE_NAME, "id = ?", new String[]{String.valueOf(i)});
        removeBlock(i);
    }

    public void removeBlock(int i) {
        getWritableDatabase().delete(BLOCK_TABLE_NAME, "breakpoint_id = ?", new String[]{String.valueOf(i)});
    }

    private static ContentValues toValues(BreakpointInfo breakpointInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", Integer.valueOf(breakpointInfo.id));
        contentValues.put("url", breakpointInfo.getUrl());
        contentValues.put(BreakpointSQLiteKey.ETAG, breakpointInfo.getEtag());
        contentValues.put(BreakpointSQLiteKey.PARENT_PATH, breakpointInfo.parentFile.getAbsolutePath());
        contentValues.put(BreakpointSQLiteKey.FILENAME, breakpointInfo.getFilename());
        contentValues.put(BreakpointSQLiteKey.TASK_ONLY_PARENT_PATH, Integer.valueOf(breakpointInfo.isTaskOnlyProvidedParentPath() ? 1 : 0));
        contentValues.put("chunked", Integer.valueOf(breakpointInfo.isChunked() ? 1 : 0));
        return contentValues;
    }

    private static ContentValues toValues(int i, int i2, BlockInfo blockInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BreakpointSQLiteKey.HOST_ID, Integer.valueOf(i));
        contentValues.put(BreakpointSQLiteKey.BLOCK_INDEX, Integer.valueOf(i2));
        contentValues.put(BreakpointSQLiteKey.START_OFFSET, Long.valueOf(blockInfo.getStartOffset()));
        contentValues.put(BreakpointSQLiteKey.CONTENT_LENGTH, Long.valueOf(blockInfo.getContentLength()));
        contentValues.put(BreakpointSQLiteKey.CURRENT_OFFSET, Long.valueOf(blockInfo.getCurrentOffset()));
        return contentValues;
    }
}
