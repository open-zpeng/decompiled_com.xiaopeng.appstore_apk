package com.xiaopeng.appstore.libcommon.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableDecoderCompat;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.libcommon.ui.FixedScaleDrawable;
import java.io.File;
import java.security.MessageDigest;
import java.util.concurrent.ExecutionException;
/* loaded from: classes2.dex */
public class ImageUtils {
    private static final AdaptiveIconTransform ADAPTIVE_ICON_TRANSFORM = new AdaptiveIconTransform();
    private static final AdaptiveIconTransform ADAPTIVE_SHADOW_ICON_TRANSFORM = new AdaptiveIconTransform(true);
    private static final String IO_EXCEPTION_MSG = "java.lang.RuntimeException: setDataSource failed: status = 0x80000000";
    private static final String TAG = "ImageUtils";
    private static DiskCache sGlideDiskCache;

    private ImageUtils() {
    }

    public static void setGlideDiskCache(DiskCache glideDiskCache) {
        sGlideDiskCache = glideDiskCache;
    }

    public static void removeGlideDiskCacheAsync(final String url) {
        AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.libcommon.utils.-$$Lambda$ImageUtils$d87LDom685X7rYi58EvsTEJ6uy8
            @Override // java.lang.Runnable
            public final void run() {
                ImageUtils.removeGlideDiskCache(url);
            }
        });
    }

    public static void removeGlideDiskCache(String url) {
        if (sGlideDiskCache != null) {
            sGlideDiskCache.delete(new GlideUrl(url));
            Logger.t(TAG).i("removeGlideCache " + url, new Object[0]);
            return;
        }
        Logger.t(TAG).w("removeGlideCache must set DiskCache first " + url, new Object[0]);
    }

    public static File getDiskCache(String url) {
        if (sGlideDiskCache != null) {
            return sGlideDiskCache.get(new GlideUrl(url));
        }
        Logger.t(TAG).w("getDiskCache must set DiskCache first " + url, new Object[0]);
        return null;
    }

    public static void load(ImageView imageView, String url) {
        load(imageView, url, -1);
    }

    public static void load(ImageView imageView, String url, int size) {
        load(imageView, url, null, null, 0, size);
    }

    public static void load(ImageView imageView, String url, int placeholderRes, int fallbackRes) {
        load(imageView, url, placeholderRes, fallbackRes, 0);
    }

    public static void load(ImageView imageView, String url, int placeholderRes, int fallbackRes, int roundingRadius) {
        RequestOptions requestOptions = new RequestOptions();
        if (roundingRadius > 0) {
            requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(roundingRadius));
        }
        Resources.Theme theme = requestOptions.getTheme() != null ? requestOptions.getTheme() : imageView.getContext().getTheme();
        load(imageView, url, DrawableDecoderCompat.getDrawable(imageView.getContext(), placeholderRes, theme), DrawableDecoderCompat.getDrawable(imageView.getContext(), fallbackRes, theme), roundingRadius);
    }

    public static void loadRound(ImageView imageView, String url, int placeholder, int fallback, int size) {
        RequestBuilder<Drawable> createRequestBuilder = createRequestBuilder(imageView.getContext(), url, placeholder, fallback, size, RequestOptions.circleCropTransform());
        if (createRequestBuilder != null) {
            createRequestBuilder.into(imageView);
        }
    }

    public static void load(ImageView imageView, String url, Drawable placeholder, Drawable fallback) {
        load(imageView, url, placeholder, fallback, 0);
    }

    public static void load(ImageView imageView, String url, Drawable placeholder, Drawable fallback, int roundingRadius) {
        load(imageView, url, placeholder, fallback, roundingRadius, -1);
    }

    public static void load(ImageView imageView, String url, Drawable placeholder, Drawable fallback, int roundingRadius, int size) {
        load(imageView, url, placeholder, fallback, roundingRadius, false, false, size);
    }

    public static void loadAdaptiveIcon(ImageView imageView, String url, Drawable placeholder, Drawable fallback, int size, boolean enableShadow) {
        load(imageView, url, placeholder, fallback, -1, true, enableShadow, size);
    }

    public static void loadAdaptiveIcon(ImageView imageView, String url, Drawable placeholder, Drawable fallback) {
        loadAdaptiveIcon(imageView, url, placeholder, fallback, -1, false);
    }

    public static void load(ImageView imageView, String url, Drawable placeholder, Drawable fallback, int roundingRadius, boolean adaptiveIcon, boolean enableShadow, int size) {
        RequestBuilder<Drawable> requestBuilder;
        RequestBuilder<Drawable> createRequestBuilder = createRequestBuilder(imageView, url, placeholder, fallback, roundingRadius, adaptiveIcon, enableShadow, size);
        if (createRequestBuilder != null) {
            if (!enableShadow) {
                requestBuilder = createRequestBuilder.transition(DrawableTransitionOptions.withCrossFade(new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()));
            } else {
                requestBuilder = (RequestBuilder) createRequestBuilder.dontAnimate();
            }
            requestBuilder.into(imageView);
        }
    }

    public static RequestBuilder<Drawable> createRequestBuilder(ImageView imageView, String url, Drawable placeholder, Drawable fallback, int roundingRadius, int size) {
        return createRequestBuilder(imageView, url, placeholder, fallback, roundingRadius, false, false, size);
    }

    public static RequestBuilder<Drawable> createRequestBuilder(ImageView imageView, String url, Drawable placeholder, Drawable fallback, int roundingRadius, boolean adaptiveIcon, boolean enableShadow, int size) {
        RequestOptions requestOptions = new RequestOptions();
        if (roundingRadius > 0) {
            requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(roundingRadius));
        }
        if (adaptiveIcon) {
            requestOptions = requestOptions.transform(enableShadow ? ADAPTIVE_SHADOW_ICON_TRANSFORM : ADAPTIVE_ICON_TRANSFORM);
        }
        return createRequestBuilder(imageView.getContext(), url, placeholder, fallback, size, requestOptions);
    }

    private static RequestBuilder<Drawable> createRequestBuilder(Context context, String url, int placeholder, int fallback, int size, RequestOptions options) {
        if (!isValidContextForGlide(context)) {
            Logger.t(TAG).w("createRequestBuilder error, invalid context," + url, new Object[0]);
            return null;
        }
        if (size <= 0) {
            size = -1;
        }
        if (options == null) {
            options = new RequestOptions();
        }
        return Glide.with(context).load(url).override(size).placeholder(placeholder).fallback(fallback).listener(DrawableRequestListener.get()).apply((BaseRequestOptions<?>) options);
    }

    private static RequestBuilder<Drawable> createRequestBuilder(Context context, String url, Drawable placeholder, Drawable fallback, int size, RequestOptions options) {
        if (!isValidContextForGlide(context)) {
            Logger.t(TAG).w("createRequestBuilder error, invalid context," + url, new Object[0]);
            return null;
        }
        if (size <= 0) {
            size = -1;
        }
        if (options == null) {
            options = new RequestOptions();
        }
        return Glide.with(context).load(url).override(size).placeholder(placeholder).fallback(fallback).listener(DrawableRequestListener.get()).apply((BaseRequestOptions<?>) options);
    }

    public static boolean isValidContextForGlide(final Context context) {
        if (context == null) {
            return false;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isDestroyed() || activity.isFinishing()) {
                Logger.t(TAG).i("isValidContextForGlide, activity destroyed or finishing:" + activity, new Object[0]);
                return false;
            }
            return true;
        }
        return true;
    }

    public static Drawable loadDrawable(String url, int width, int height) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        try {
            return Glide.with(Utils.getApp().getApplicationContext()).asDrawable().load(url).listener(DrawableRequestListener.get()).submit(width, height).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap loadBitmap(String url) {
        return loadBitmap(url, Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    public static Bitmap loadBitmapThrow(String url) throws ExecutionException, InterruptedException {
        return loadBitmapThrow(url, Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    public static Bitmap loadAdaptiveIconBitmap(String url, int width, int height) {
        return loadBitmap(url, width, height, true);
    }

    public static Bitmap loadBitmap(String url, int width, int height) {
        return loadBitmap(url, width, height, false);
    }

    public static Bitmap loadBitmap(String url, int width, int height, boolean adaptiveIcon) {
        return loadBitmap(url, width, height, adaptiveIcon, false);
    }

    public static Bitmap loadBitmapFromCache(String url, int width, int height, boolean adaptiveIcon) {
        return loadBitmap(url, width, height, adaptiveIcon, true);
    }

    public static Bitmap loadBitmap(String url, int width, int height, boolean adaptiveIcon, boolean onlyCache) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        try {
            return loadBitmapThrow(url, width, height, adaptiveIcon, onlyCache);
        } catch (Exception e) {
            Logger.t(TAG).w("loadBitmap failed " + url + " " + e, new Object[0]);
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap loadBitmapThrow(String url, int width, int height) throws ExecutionException, InterruptedException {
        return loadBitmapThrow(url, width, height, false, false);
    }

    public static Bitmap loadBitmapThrow(String url, int width, int height, boolean adaptiveIcon) throws ExecutionException, InterruptedException {
        return loadBitmapThrow(url, width, height, adaptiveIcon, false);
    }

    public static Bitmap loadBitmapThrow(String url, int width, int height, boolean adaptiveIcon, boolean onlyCache) throws ExecutionException, InterruptedException {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        RequestOptions requestOptions = new RequestOptions();
        if (adaptiveIcon) {
            requestOptions = requestOptions.transform(ADAPTIVE_ICON_TRANSFORM);
        }
        return (Bitmap) Glide.with(Utils.getApp().getApplicationContext()).asBitmap().load(url).listener(BitmapRequestListener.get()).apply((BaseRequestOptions<?>) requestOptions).onlyRetrieveFromCache(onlyCache).submit(width, height).get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0018  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static boolean isIOException(com.bumptech.glide.load.engine.GlideException r4) {
        /*
            r0 = 0
            if (r4 == 0) goto L30
            java.util.List r4 = r4.getRootCauses()
            boolean r1 = r4.isEmpty()
            if (r1 != 0) goto L30
            r1 = 1
            java.util.Iterator r4 = r4.iterator()
        L12:
            boolean r2 = r4.hasNext()
            if (r2 == 0) goto L2f
            java.lang.Object r2 = r4.next()
            java.lang.Throwable r2 = (java.lang.Throwable) r2
            boolean r3 = r2 instanceof java.io.IOException
            if (r3 == 0) goto L30
            java.lang.String r2 = r2.getMessage()
            java.lang.String r3 = "java.lang.RuntimeException: setDataSource failed: status = 0x80000000"
            boolean r2 = r2.equals(r3)
            if (r2 != 0) goto L12
            goto L30
        L2f:
            r0 = r1
        L30:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.appstore.libcommon.utils.ImageUtils.isIOException(com.bumptech.glide.load.engine.GlideException):boolean");
    }

    public static AdaptiveIconDrawable generateAdaptiveIcon(Drawable foreground) {
        return generateAdaptiveIcon(foreground, -1);
    }

    public static AdaptiveIconDrawable generateAdaptiveIcon(Drawable foreground, int bgColor) {
        return new AdaptiveIconDrawable(new ColorDrawable(bgColor), new FixedScaleDrawable(foreground));
    }

    /* loaded from: classes2.dex */
    private static class CustomRequestListener<R> implements RequestListener<R> {
        @Override // com.bumptech.glide.request.RequestListener
        public boolean onResourceReady(R resource, Object model, Target<R> target, DataSource dataSource, boolean isFirstResource) {
            return false;
        }

        private CustomRequestListener() {
        }

        @Override // com.bumptech.glide.request.RequestListener
        public boolean onLoadFailed(GlideException e, Object model, Target<R> target, boolean isFirstResource) {
            if (ImageUtils.isIOException(e)) {
                Logger.t(ImageUtils.TAG).w("onLoadFail with IOException " + model, new Object[0]);
                ImageUtils.removeGlideDiskCacheAsync((String) model);
            } else {
                Logger.t(ImageUtils.TAG).w("onLoadFail " + e + " " + model, new Object[0]);
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class DrawableRequestListener extends CustomRequestListener<Drawable> {
        private DrawableRequestListener() {
            super();
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes2.dex */
        public static class SingletonHolder {
            static DrawableRequestListener sInstance = new DrawableRequestListener();

            private SingletonHolder() {
            }
        }

        static DrawableRequestListener get() {
            return SingletonHolder.sInstance;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class BitmapRequestListener extends CustomRequestListener<Bitmap> {

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes2.dex */
        public static class SingletonHolder {
            static BitmapRequestListener sInstance = new BitmapRequestListener();

            private SingletonHolder() {
            }
        }

        private BitmapRequestListener() {
            super();
        }

        static BitmapRequestListener get() {
            return SingletonHolder.sInstance;
        }
    }

    /* loaded from: classes2.dex */
    public static class AdaptiveIconTransform extends BitmapTransformation {
        private static final String ID = "com.xiaopeng.appstore.libcommon.utils.ImageUtils.AdaptiveIconTransform";
        private static final byte[] ID_BYTES = ID.getBytes(CHARSET);
        private static final int KEY_SHADOW_ALPHA = 77;
        private static final float KEY_SHADOW_DISTANCE = 0.052083332f;
        private static final int SHADOW_BLUR_RADIUS = 6;
        private static final String TAG = "AdaptiveIconTransform";
        private Paint mBlurPaint;
        private final ThreadLocal<Canvas> mCanvas;
        private final boolean mEnableShadow;
        private Paint mShadowPaint;

        @Override // com.bumptech.glide.load.Key
        public int hashCode() {
            return 1584796762;
        }

        AdaptiveIconTransform() {
            this(false);
        }

        AdaptiveIconTransform(boolean enableShadow) {
            this.mCanvas = new ThreadLocal<>();
            this.mEnableShadow = enableShadow;
            if (enableShadow) {
                if (this.mShadowPaint == null) {
                    this.mShadowPaint = new Paint(3);
                }
                if (this.mBlurPaint == null) {
                    Paint paint = new Paint(3);
                    this.mBlurPaint = paint;
                    paint.setMaskFilter(new BlurMaskFilter(6.0f, BlurMaskFilter.Blur.NORMAL));
                }
            }
        }

        private Canvas getCanvas() {
            Canvas canvas = this.mCanvas.get();
            if (canvas == null) {
                Canvas canvas2 = new Canvas();
                canvas2.setDrawFilter(new PaintFlagsDrawFilter(4, 2));
                this.mCanvas.set(canvas2);
                return canvas2;
            }
            return canvas;
        }

        @Override // com.bumptech.glide.load.resource.bitmap.BitmapTransformation
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return adaptiveIcon(pool, toTransform);
        }

        private Bitmap adaptiveIcon(BitmapPool pool, Bitmap source) {
            if (source == null) {
                return null;
            }
            Canvas canvas = getCanvas();
            AdaptiveIconDrawable generateAdaptiveIcon = ImageUtils.generateAdaptiveIcon(new BitmapDrawable((Resources) null, source));
            int width = source.getWidth();
            int height = source.getHeight();
            int max = Math.max(width, height);
            Logger.t(TAG).d("adaptiveIcon transform: " + source + ", size:" + max);
            generateAdaptiveIcon.setBounds(0, 0, max, max);
            Bitmap bitmap = pool.get(width, height, Bitmap.Config.ARGB_8888);
            canvas.setBitmap(bitmap);
            generateAdaptiveIcon.draw(canvas);
            if (this.mEnableShadow) {
                canvas.setBitmap(null);
                int[] iArr = new int[2];
                Bitmap extractAlpha = bitmap.extractAlpha(this.mBlurPaint, iArr);
                Bitmap bitmap2 = pool.get(extractAlpha.getWidth(), extractAlpha.getHeight(), Bitmap.Config.ARGB_8888);
                canvas.setBitmap(bitmap2);
                this.mShadowPaint.setAlpha(77);
                canvas.drawBitmap(extractAlpha, 0.0f, height * KEY_SHADOW_DISTANCE, this.mShadowPaint);
                this.mShadowPaint.setAlpha(255);
                canvas.drawBitmap(bitmap, -iArr[0], -iArr[1], this.mShadowPaint);
                canvas.setBitmap(null);
                return bitmap2;
            }
            canvas.setBitmap(null);
            return bitmap;
        }

        @Override // com.bumptech.glide.load.Key
        public boolean equals(Object o) {
            return o instanceof AdaptiveIconTransform;
        }

        @Override // com.bumptech.glide.load.Key
        public void updateDiskCacheKey(MessageDigest messageDigest) {
            messageDigest.update(ID_BYTES);
        }
    }
}
