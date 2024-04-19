package com.xiaopeng.appstore.appstore_ui.bindingextension;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.appstore.appstore_ui.R;
import com.xiaopeng.appstore.appstore_ui.adapter.binding.BindableAdapter;
import com.xiaopeng.appstore.bizcommon.utils.LogicUtils;
import com.xiaopeng.appstore.common_ui.common.widget.MaskImageView;
import com.xiaopeng.appstore.libcommon.utils.ImageUtils;
import com.xiaopeng.appstore.libcommon.utils.ResUtils;
import java.util.List;
/* loaded from: classes2.dex */
public class CommonBindingExtension {
    private static int sCurrentUiMode;
    private static Drawable sIconPlaceHolder;

    private static Drawable getIconPlaceHolder(View view) {
        Context context;
        if (view == null || (context = view.getContext()) == null) {
            return null;
        }
        Resources resources = context.getResources();
        int i = resources != null ? resources.getConfiguration().uiMode : 0;
        if (sIconPlaceHolder == null || sCurrentUiMode != i) {
            sCurrentUiMode = i;
            sIconPlaceHolder = context.getDrawable(R.drawable.ic_app_store_holder);
        }
        return sIconPlaceHolder;
    }

    public static <T> void setRecyclerData(RecyclerView recyclerView, List<T> list) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter instanceof BindableAdapter) {
            ((BindableAdapter) adapter).setList(list);
        }
    }

    public static void setImageSrc(ImageView imageView, String imageUrl, Drawable placeholder, Drawable fallback, float roundingRadius) {
        ImageUtils.load(imageView, imageUrl, placeholder, fallback, (int) roundingRadius);
    }

    public static void setIcon(ImageView imageView, String imageUrl, Drawable placeholder, Drawable fallback) {
        if (placeholder == null) {
            placeholder = getIconPlaceHolder(imageView).mutate();
        }
        if (fallback == null) {
            fallback = getIconPlaceHolder(imageView).mutate();
        }
        ImageUtils.loadAdaptiveIcon(imageView, imageUrl, placeholder, fallback);
    }

    public static void setXpIcon(ImageView imageView, String imageUrl, Drawable placeholder, Drawable fallback) {
        LogicUtils.loadStoreIcon(imageView, imageUrl, placeholder, fallback);
    }

    public static void setFirstImage(ImageView imageView, List<String> imageList, Drawable placeholder, Drawable fallback, float roundingRadius) {
        if (imageList == null || imageList.isEmpty()) {
            return;
        }
        ImageUtils.load(imageView, imageList.get(0), placeholder, fallback, (int) roundingRadius);
    }

    public static void setBackground(View view, int drawableId) {
        view.setBackground(ResUtils.getDrawable(drawableId));
    }

    public static void setBackgroundColor(View view, int colorId) {
        view.setBackgroundColor(ResUtils.getColor(colorId));
    }

    public static void setTextColor(TextView textView, int color) {
        textView.setTextColor(ResUtils.getColor(color));
    }

    public static void setImageShowMask(MaskImageView imageView, boolean showMask) {
        imageView.setShowMask(showMask);
    }
}
