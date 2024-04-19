package com.xiaopeng.appstore.bizcommon.logic;

import android.text.TextUtils;
import androidx.core.math.MathUtils;
import com.orhanobut.logger.Logger;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/* loaded from: classes2.dex */
public class ProgressHolder {
    private static final String TAG = "ProgressHolder";
    private OnProgressChangeListener mOnProgressChangeListener;
    private Map<String, Float> mProgressWeightMap;
    private List<String> mTagList;
    private float mTotalProgress = 0.0f;
    private float mProgressOffset = 0.0f;
    private int mCurrentTask = -1;

    /* loaded from: classes2.dex */
    public interface OnProgressChangeListener {
        void onFinish(boolean success);

        void onProgressChange(float progress, long remainTime);

        void onStartProgress();
    }

    public ProgressHolder(Map<String, Float> weightMap) {
        assertWeightMap(weightMap);
        this.mProgressWeightMap = new LinkedHashMap(weightMap);
        this.mTagList = new LinkedList(weightMap.keySet());
    }

    public void setOnProgressChangeListener(OnProgressChangeListener listener) {
        this.mOnProgressChangeListener = listener;
    }

    private void assertWeightMap(Map<String, Float> weightMap) {
        if (weightMap == null || weightMap.isEmpty()) {
            return;
        }
        float f = 0.0f;
        for (Float f2 : weightMap.values()) {
            f += f2.floatValue();
        }
        if (f != 1.0f) {
        }
    }

    public void reset() {
        this.mCurrentTask = -1;
        this.mTotalProgress = 0.0f;
        this.mProgressOffset = 0.0f;
    }

    public float getCurrentTotalProgress() {
        return this.mTotalProgress;
    }

    public boolean hasNext() {
        int i = this.mCurrentTask + 1;
        return i >= 0 && i < this.mTagList.size();
    }

    public void setCurrentTask(int index) {
        if (index != this.mCurrentTask) {
            this.mCurrentTask = index;
            updateOffset();
            this.mTotalProgress = this.mProgressOffset;
        }
    }

    public void startProgress() {
        Logger.t(TAG).d("startProgress");
        dispatchProgressStart();
    }

    public void startNext() {
        if (hasNext()) {
            this.mCurrentTask++;
            updateOffset();
            this.mTotalProgress = this.mProgressOffset;
        }
    }

    public void processCurrentProgress(float progress, long remainTime) {
        float clamp = MathUtils.clamp(progress, 0.0f, 1.0f);
        float weight = getWeight(this.mCurrentTask);
        if (weight >= 0.0f) {
            this.mTotalProgress = (clamp * weight) + this.mProgressOffset;
            Logger.t(TAG).d("processCurrentProgress, progress=" + clamp + " weight=" + weight + " remainTime=" + remainTime + " total=" + this.mTotalProgress);
            dispatchProgressChange(remainTime);
        }
    }

    public void finish(boolean success) {
        Logger.t(TAG).d("finish, success=" + success);
        dispatchProgressFinish(success);
        reset();
    }

    private void updateOffset() {
        this.mProgressOffset = 0.0f;
        int i = this.mCurrentTask - 1;
        if (i < 0 || i >= this.mTagList.size()) {
            return;
        }
        while (i >= 0) {
            float weight = getWeight(this.mTagList.get(i));
            if (weight >= 0.0f) {
                this.mProgressOffset += weight;
            }
            i--;
        }
    }

    private void dispatchProgressStart() {
        OnProgressChangeListener onProgressChangeListener = this.mOnProgressChangeListener;
        if (onProgressChangeListener != null) {
            onProgressChangeListener.onStartProgress();
        }
    }

    private void dispatchProgressChange(long remainTime) {
        OnProgressChangeListener onProgressChangeListener = this.mOnProgressChangeListener;
        if (onProgressChangeListener != null) {
            onProgressChangeListener.onProgressChange(this.mTotalProgress, remainTime);
        }
    }

    private void dispatchProgressFinish(boolean success) {
        OnProgressChangeListener onProgressChangeListener = this.mOnProgressChangeListener;
        if (onProgressChangeListener != null) {
            onProgressChangeListener.onFinish(success);
        }
    }

    private float getWeight(int index) {
        if (index < 0 || index >= this.mTagList.size()) {
            return -1.0f;
        }
        return getWeight(this.mTagList.get(index));
    }

    private float getWeight(String tag) {
        Float f;
        if (TextUtils.isEmpty(tag) || (f = this.mProgressWeightMap.get(tag)) == null) {
            return -1.0f;
        }
        return MathUtils.clamp(f.floatValue(), 0.0f, 1.0f);
    }
}
