package com.xiaopeng.appstore.bizcommon.entities;

import android.text.TextUtils;
/* loaded from: classes2.dex */
public class Parser {
    public static AssembleDataContainer parseAssembleLocal(AssembleLocalInfo info) {
        if (info == null) {
            return null;
        }
        AssembleDataContainer assembleDataContainer = new AssembleDataContainer(info.getAssembleId());
        assembleDataContainer.setPackageName(info.getPackageName());
        assembleDataContainer.setDownloadUrl(info.getDownloadUrl());
        assembleDataContainer.setMd5(info.getMd5());
        assembleDataContainer.setConfigUrl(info.getConfigUrl());
        assembleDataContainer.setConfigMd5(info.getConfigMd5());
        assembleDataContainer.setAppLabel(info.getLabel());
        assembleDataContainer.setIconUrl(info.getIconUrl());
        assembleDataContainer.setDownloadId(info.getDownloadId());
        assembleDataContainer.setState(info.getState());
        assembleDataContainer.setDownloadBytes(info.getDownloadedBytes());
        assembleDataContainer.setTotalBytes(info.getTotalBytes());
        assembleDataContainer.setProgress(info.getProgress());
        return assembleDataContainer;
    }

    public static AssembleLocalInfo parseAssembleData(AssembleDataContainer data) {
        if (data == null) {
            return null;
        }
        AssembleLocalInfo assembleLocalInfo = new AssembleLocalInfo();
        assembleLocalInfo.setAssembleId(data.getId());
        assembleLocalInfo.setPackageName(data.getPackageName());
        assembleLocalInfo.setDownloadUrl(data.getDownloadUrl());
        assembleLocalInfo.setMd5(data.getMd5());
        assembleLocalInfo.setConfigUrl(data.getConfigUrl());
        assembleLocalInfo.setConfigMd5(data.getConfigMd5());
        assembleLocalInfo.setIconUrl(data.getIconUrl());
        assembleLocalInfo.setLabel(data.getAppLabel());
        assembleLocalInfo.setDownloadId(data.getDownloadId());
        assembleLocalInfo.setState(data.getState());
        assembleLocalInfo.setDownloadedBytes(data.getDownloadBytes());
        assembleLocalInfo.setTotalBytes(data.getTotalBytes());
        assembleLocalInfo.setProgress(data.getProgress());
        return assembleLocalInfo;
    }

    public static DownloadCenterLocalInfo parseAssembleData2DownloadLocal(AssembleDataContainer data) {
        if (data == null || TextUtils.isEmpty(data.getPackageName())) {
            return null;
        }
        DownloadCenterLocalInfo downloadCenterLocalInfo = new DownloadCenterLocalInfo();
        downloadCenterLocalInfo.setId(data.getId());
        downloadCenterLocalInfo.setDownloadId(data.getDownloadId());
        downloadCenterLocalInfo.setDownloadUrl(data.getDownloadUrl());
        downloadCenterLocalInfo.setConfigUrl(data.getConfigUrl());
        downloadCenterLocalInfo.setPackageName(data.getPackageName());
        downloadCenterLocalInfo.setAppLabel(data.getAppLabel());
        downloadCenterLocalInfo.setIconUrl(data.getIconUrl());
        downloadCenterLocalInfo.setTotalBytes(data.getTotalBytes());
        return downloadCenterLocalInfo;
    }

    public static AssembleDataContainer parseDownloadCenterLocal(DownloadCenterLocalInfo info) {
        if (info == null) {
            return null;
        }
        AssembleDataContainer assembleDataContainer = new AssembleDataContainer(info.getId());
        assembleDataContainer.setPackageName(info.getPackageName());
        assembleDataContainer.setIconUrl(info.getIconUrl());
        assembleDataContainer.setAppLabel(info.getAppLabel());
        assembleDataContainer.setTotalBytes(info.getTotalBytes());
        assembleDataContainer.setDownloadId(info.getDownloadId());
        assembleDataContainer.setDownloadUrl(info.getDownloadUrl());
        assembleDataContainer.setConfigUrl(info.getConfigUrl());
        return assembleDataContainer;
    }
}
