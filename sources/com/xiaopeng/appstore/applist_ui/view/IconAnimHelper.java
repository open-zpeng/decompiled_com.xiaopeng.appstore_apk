package com.xiaopeng.appstore.applist_ui.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.util.FloatProperty;
import android.view.animation.LinearInterpolator;
import androidx.lifecycle.DefaultLifecycleObserver;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
/* loaded from: classes2.dex */
public class IconAnimHelper implements Animator.AnimatorListener {
    private static final String TAG = "IconAnimHelper";
    private final FloatProperty<IconAnimHelper> VALUE;
    private final Set<OnAnimateListener> mListenerList;
    private final ObjectAnimator mObjectAnimator;
    private float mValue;

    /* loaded from: classes2.dex */
    public interface OnAnimateListener {
        void onAnimateCancel(float value);

        void onAnimateStart();

        void onAnimateUpdate(float value);
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animation) {
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationRepeat(Animator animation) {
    }

    public void registerListener(OnAnimateListener listener) {
        this.mListenerList.add(listener);
    }

    public void unregisterListener(OnAnimateListener listener) {
        this.mListenerList.remove(listener);
    }

    public IconAnimHelper(float from, float to, long duration) {
        FloatProperty<IconAnimHelper> floatProperty = new FloatProperty<IconAnimHelper>("value") { // from class: com.xiaopeng.appstore.applist_ui.view.IconAnimHelper.1
            @Override // android.util.FloatProperty
            public void setValue(IconAnimHelper object, float value) {
                object.mValue = value;
                int size = IconAnimHelper.this.mListenerList.size();
                OnAnimateListener[] onAnimateListenerArr = new OnAnimateListener[size];
                IconAnimHelper.this.mListenerList.toArray(onAnimateListenerArr);
                for (int i = 0; i < size; i++) {
                    onAnimateListenerArr[i].onAnimateUpdate(IconAnimHelper.this.mValue);
                }
            }

            @Override // android.util.Property
            public Float get(IconAnimHelper object) {
                return Float.valueOf(object.mValue);
            }
        };
        this.VALUE = floatProperty;
        this.mListenerList = new HashSet();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, floatProperty, 0.0f, to, 0.0f, from, 0.0f);
        this.mObjectAnimator = ofFloat;
        ofFloat.setAutoCancel(true);
        ofFloat.setDuration(duration);
        ofFloat.setRepeatMode(1);
        ofFloat.setRepeatCount(-1);
        ofFloat.setInterpolator(new LinearInterpolator());
        ofFloat.addListener(this);
    }

    public void start() {
        this.mObjectAnimator.start();
    }

    public void stop() {
        this.mObjectAnimator.cancel();
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animation) {
        int size = this.mListenerList.size();
        OnAnimateListener[] onAnimateListenerArr = new OnAnimateListener[size];
        this.mListenerList.toArray(onAnimateListenerArr);
        for (int i = 0; i < size; i++) {
            onAnimateListenerArr[i].onAnimateStart();
        }
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animation) {
        int size = this.mListenerList.size();
        OnAnimateListener[] onAnimateListenerArr = new OnAnimateListener[size];
        this.mListenerList.toArray(onAnimateListenerArr);
        for (int i = 0; i < size; i++) {
            onAnimateListenerArr[i].onAnimateCancel(this.mValue);
        }
    }

    /* loaded from: classes2.dex */
    public static class IconAnimHelperGenerator implements DefaultLifecycleObserver {
        public static final int ANIMATE_MAX_VALUE = 50;
        private static final int ANIMATION_DURATION = 280;
        private static final String TAG = "IconAnimHelperGenerator";
        private static final int[] ANIMATE_MAX_VALUE_RANGE = {40, 50, 60};
        private static final Random sRandomStartValue = new Random();
        private static volatile IconAnimHelperGenerator sInstance = null;

        public static IconAnimHelperGenerator get() {
            if (sInstance == null) {
                synchronized (IconAnimHelperGenerator.class) {
                    if (sInstance == null) {
                        sInstance = new IconAnimHelperGenerator();
                    }
                }
            }
            return sInstance;
        }

        public IconAnimHelper obtain() {
            int[] iArr = ANIMATE_MAX_VALUE_RANGE;
            Random random = sRandomStartValue;
            int i = iArr[random.nextInt(iArr.length)] * (random.nextBoolean() ? 1 : -1);
            return new IconAnimHelper(i, i * (-1), 280L);
        }
    }
}
