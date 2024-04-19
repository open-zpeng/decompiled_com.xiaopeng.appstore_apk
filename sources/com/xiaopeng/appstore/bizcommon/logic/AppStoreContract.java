package com.xiaopeng.appstore.bizcommon.logic;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
/* loaded from: classes2.dex */
public class AppStoreContract {
    public static final String AUTHORITY = "appstore";
    public static final Uri AUTHORITY_URI = Uri.parse("content://appstore");
    private static final String TAG = "AppStoreContract";

    /* loaded from: classes2.dex */
    public static class AgreementStage {
        public static final int AGREED = 2;
        public static final int AGREED_DEFAULT = 0;
        public static final int INDEX_AGREED = 1;
        public static final int INDEX_USER_ID = 0;
        public static final String USER_ID_COLUMN = "userId";
        public static final String PATH = "agreement_states";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AppStoreContract.AUTHORITY_URI, PATH);
    }

    /* loaded from: classes2.dex */
    public static class RemoteState {
        private static final int INDEX_HAS_UPDATE = 2;
        private static final int INDEX_PACKAGE_NAME = 0;
        private static final int INDEX_STATE = 1;
        private static final String PACKAGE_NAME_COLUMN = "packageName";
        public static final int STATE_DEFAULT = 0;
        public static final int STATE_FORCE_UPDATE = 1051;
        public static final int STATE_SUSPENDED = 1001;
        public static final String PATH = "remote_states";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AppStoreContract.AUTHORITY_URI, PATH);
    }

    /* loaded from: classes2.dex */
    public static class AppLocalState {
        public static final String COLUMN_CAN_START_DOWNLOAD = "canStartDownload";
        @Deprecated
        public static final int DOWNLOAD_START_NOT_DONE_AGREEMENT = 105;
        public static final int DOWNLOAD_START_NOT_LOGIN = 100;
        public static final int DOWNLOAD_START_SATISFY = 0;
        public static final int INDEX_CAN_START_DOWNLOAD = 0;
        public static final String PATH = "app_local_states";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AppStoreContract.AUTHORITY_URI, PATH);

        public static int canStartDownload(ContentResolver contentResolver) {
            Cursor query = contentResolver.query(CONTENT_URI, null, null, null, null);
            if (query != null) {
                try {
                    if (query.getCount() != 0 && query.moveToFirst()) {
                        int i = query.getInt(0);
                        if (query != null) {
                            query.close();
                        }
                        return i;
                    }
                } catch (Throwable th) {
                    if (query != null) {
                        try {
                            query.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            if (query != null) {
                query.close();
            }
            return 0;
        }
    }
}
