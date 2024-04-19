package com.xiaopeng.appstore.xpcommon;

import android.content.Context;
import android.os.storage.DiskInfo;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.util.Log;
import java.io.File;
import java.util.ArrayList;
/* loaded from: classes2.dex */
public class HiddenStorageMgrUtils {
    private static final String TAG = "StorageManagerHideUtils";

    private HiddenStorageMgrUtils() {
    }

    public static ArrayList<File> getUSBFileFromStorage(Context context) {
        ArrayList<File> arrayList = new ArrayList<>();
        try {
            for (VolumeInfo volumeInfo : ((StorageManager) context.getSystemService("storage")).getVolumes()) {
                if (volumeInfo.getType() == 0 && volumeInfo.getState() == 2) {
                    DiskInfo disk = volumeInfo.getDisk();
                    if (disk != null && disk.isUsb()) {
                        File path = volumeInfo.getPath();
                        if (path != null && path.exists() && !path.isHidden()) {
                            arrayList.add(path);
                            Log.i(TAG, " getUSBFileFromStorage(), usb file url : " + path.getPath());
                        } else {
                            Log.d(TAG, "getUSBFileFromStorage, file not available:" + path);
                        }
                    } else {
                        Log.d(TAG, "getUSBFileFromStorage, disk not usb:" + disk);
                    }
                } else {
                    Log.d(TAG, "getUSBFileFromStorage, volume type not available:" + volumeInfo.getType());
                }
            }
            return arrayList;
        } catch (Exception e) {
            Log.e(TAG, "getUSBFileFromStorage:  " + e.getMessage());
            e.printStackTrace();
            return arrayList;
        }
    }
}
