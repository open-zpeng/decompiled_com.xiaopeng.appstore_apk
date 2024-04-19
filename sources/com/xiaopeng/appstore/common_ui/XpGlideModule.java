package com.xiaopeng.appstore.common_ui;

import android.content.Context;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.AppGlideModule;
import com.xiaopeng.appstore.libcommon.utils.ImageUtils;
/* loaded from: classes.dex */
public class XpGlideModule extends AppGlideModule {
    @Override // com.bumptech.glide.module.AppGlideModule, com.bumptech.glide.module.AppliesOptions
    public void applyOptions(Context context, GlideBuilder builder) {
        InternalCacheDiskCacheFactory internalCacheDiskCacheFactory = new InternalCacheDiskCacheFactory(context);
        builder.setDiskCache(internalCacheDiskCacheFactory);
        ImageUtils.setGlideDiskCache(internalCacheDiskCacheFactory.build());
    }
}
