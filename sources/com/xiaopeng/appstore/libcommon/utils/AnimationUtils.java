package com.xiaopeng.appstore.libcommon.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import com.xiaopeng.libcommon.R;
import java.lang.ref.WeakReference;
/* loaded from: classes2.dex */
public class AnimationUtils {
    private static final int FADE_ANIM_TAG = R.string.fade_anim_listener_tag;
    private static AccelerateInterpolator sAccelerateInterpolator;
    private static DecelerateInterpolator sDecelerateInterpolator;

    private AnimationUtils() {
    }

    public static AccelerateInterpolator getAccelerateInterpolator() {
        if (sAccelerateInterpolator == null) {
            sAccelerateInterpolator = new AccelerateInterpolator();
        }
        return sAccelerateInterpolator;
    }

    public static DecelerateInterpolator getDecelerateInterpolator() {
        if (sDecelerateInterpolator == null) {
            sDecelerateInterpolator = new DecelerateInterpolator();
        }
        return sDecelerateInterpolator;
    }

    public static void fadeIn(View view, long duration, TimeInterpolator interpolator) {
        if (view.getVisibility() == 0) {
            return;
        }
        view.animate().cancel();
        view.animate().alpha(1.0f).setDuration(duration).setInterpolator(interpolator).setListener(getFadeAnimatorListener(view, true)).start();
    }

    public static void fadeOut(View view, long duration, TimeInterpolator interpolator) {
        if (view.getVisibility() == 8) {
            return;
        }
        view.animate().cancel();
        view.animate().alpha(0.0f).setDuration(duration).setInterpolator(interpolator).setListener(getFadeAnimatorListener(view, false)).start();
    }

    private static FadeAnimatorListener getFadeAnimatorListener(View view, boolean fadeIn) {
        FadeAnimatorListener fadeAnimatorListener;
        int i = FADE_ANIM_TAG;
        Object tag = view.getTag(i);
        if (tag instanceof FadeAnimatorListener) {
            fadeAnimatorListener = (FadeAnimatorListener) tag;
        } else {
            fadeAnimatorListener = new FadeAnimatorListener();
            view.setTag(i, fadeAnimatorListener);
        }
        fadeAnimatorListener.setView(view, fadeIn);
        return fadeAnimatorListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class FadeAnimatorListener extends AnimatorListenerAdapter {
        private boolean mCanceled;
        private boolean mFadeIn;
        private float mSavedAlpha;
        private int mSavedVisibility;
        private WeakReference<View> mViewRef;

        private FadeAnimatorListener() {
            this.mCanceled = false;
            this.mSavedAlpha = -1.0f;
            this.mSavedVisibility = 4;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setView(View view, boolean fadeIn) {
            this.mViewRef = new WeakReference<>(view);
            this.mFadeIn = fadeIn;
            this.mSavedAlpha = view.getAlpha();
            this.mSavedVisibility = view.getVisibility();
            if (this.mFadeIn) {
                view.setAlpha(0.0f);
            } else {
                view.setAlpha(1.0f);
            }
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animation) {
            this.mCanceled = false;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animation) {
            View view;
            this.mCanceled = true;
            WeakReference<View> weakReference = this.mViewRef;
            if (weakReference == null || (view = weakReference.get()) == null) {
                return;
            }
            float f = this.mSavedAlpha;
            if (f > 0.0f) {
                view.setAlpha(f);
            }
            int i = this.mSavedVisibility;
            if (i != 4) {
                view.setVisibility(i);
            }
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animation) {
            WeakReference<View> weakReference;
            View view;
            if (this.mCanceled || (weakReference = this.mViewRef) == null || (view = weakReference.get()) == null) {
                return;
            }
            if (this.mFadeIn) {
                view.setAlpha(1.0f);
                view.setVisibility(0);
                return;
            }
            view.setAlpha(0.0f);
            view.setVisibility(8);
        }
    }

    public static void cancelAnimation(Animation animation) {
        if (animation != null) {
            animation.cancel();
            animation.reset();
        }
    }
}
