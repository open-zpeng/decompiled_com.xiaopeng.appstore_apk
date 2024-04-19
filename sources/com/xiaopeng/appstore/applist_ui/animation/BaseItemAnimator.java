package com.xiaopeng.appstore.applist_ui.animation;

import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/* loaded from: classes2.dex */
public abstract class BaseItemAnimator extends SimpleItemAnimator {
    private static final boolean DEBUG = false;
    private final ArrayList<RecyclerView.ViewHolder> mPendingRemovals = new ArrayList<>();
    protected ArrayList<RecyclerView.ViewHolder> mPendingAdditions = new ArrayList<>();
    protected ArrayList<MoveInfo> mPendingMoves = new ArrayList<>();
    private final ArrayList<ChangeInfo> mPendingChanges = new ArrayList<>();
    private final ArrayList<ArrayList<RecyclerView.ViewHolder>> mAdditionsList = new ArrayList<>();
    private final ArrayList<ArrayList<MoveInfo>> mMovesList = new ArrayList<>();
    private final ArrayList<ArrayList<ChangeInfo>> mChangesList = new ArrayList<>();
    protected ArrayList<RecyclerView.ViewHolder> mAddAnimations = new ArrayList<>();
    private final ArrayList<RecyclerView.ViewHolder> mMoveAnimations = new ArrayList<>();
    protected ArrayList<RecyclerView.ViewHolder> mRemoveAnimations = new ArrayList<>();
    private final ArrayList<RecyclerView.ViewHolder> mChangeAnimations = new ArrayList<>();
    protected Interpolator mInterpolator = new DecelerateInterpolator();

    protected abstract void animateAddImpl(final RecyclerView.ViewHolder holder);

    protected abstract void animateRemoveImpl(final RecyclerView.ViewHolder holder);

    protected void preAnimateAddImpl(final RecyclerView.ViewHolder holder) {
    }

    protected void preAnimateRemoveImpl(final RecyclerView.ViewHolder holder) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes2.dex */
    public static class MoveInfo {
        public int fromX;
        public int fromY;
        public RecyclerView.ViewHolder holder;
        public int toX;
        public int toY;

        /* JADX INFO: Access modifiers changed from: protected */
        public MoveInfo(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
            this.holder = holder;
            this.fromX = fromX;
            this.fromY = fromY;
            this.toX = toX;
            this.toY = toY;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class ChangeInfo {
        public int fromX;
        public int fromY;
        public RecyclerView.ViewHolder newHolder;
        public RecyclerView.ViewHolder oldHolder;
        public int toX;
        public int toY;

        private ChangeInfo(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder) {
            this.oldHolder = oldHolder;
            this.newHolder = newHolder;
        }

        private ChangeInfo(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder, int fromX, int fromY, int toX, int toY) {
            this(oldHolder, newHolder);
            this.fromX = fromX;
            this.fromY = fromY;
            this.toX = toX;
            this.toY = toY;
        }

        public String toString() {
            return "ChangeInfo{oldHolder=" + this.oldHolder + ", newHolder=" + this.newHolder + ", fromX=" + this.fromX + ", fromY=" + this.fromY + ", toX=" + this.toX + ", toY=" + this.toY + '}';
        }
    }

    public BaseItemAnimator() {
        setSupportsChangeAnimations(false);
    }

    public void setInterpolator(Interpolator mInterpolator) {
        this.mInterpolator = mInterpolator;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
    public void runPendingAnimations() {
        boolean z = !this.mPendingRemovals.isEmpty();
        boolean z2 = !this.mPendingMoves.isEmpty();
        boolean z3 = !this.mPendingChanges.isEmpty();
        boolean z4 = !this.mPendingAdditions.isEmpty();
        if (z || z2 || z4 || z3) {
            Iterator<RecyclerView.ViewHolder> it = this.mPendingRemovals.iterator();
            while (it.hasNext()) {
                doAnimateRemove(it.next());
            }
            this.mPendingRemovals.clear();
            if (z2) {
                final ArrayList<MoveInfo> arrayList = new ArrayList<>();
                arrayList.addAll(this.mPendingMoves);
                this.mMovesList.add(arrayList);
                this.mPendingMoves.clear();
                Runnable runnable = new Runnable() { // from class: com.xiaopeng.appstore.applist_ui.animation.BaseItemAnimator.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (BaseItemAnimator.this.mMovesList.remove(arrayList)) {
                            Iterator it2 = arrayList.iterator();
                            while (it2.hasNext()) {
                                MoveInfo moveInfo = (MoveInfo) it2.next();
                                BaseItemAnimator.this.animateMoveImpl(moveInfo.holder, moveInfo.fromX, moveInfo.fromY, moveInfo.toX, moveInfo.toY);
                            }
                            arrayList.clear();
                        }
                    }
                };
                if (z) {
                    ViewCompat.postOnAnimationDelayed(arrayList.get(0).holder.itemView, runnable, getRemoveDuration());
                } else {
                    runnable.run();
                }
            }
            if (z3) {
                final ArrayList<ChangeInfo> arrayList2 = new ArrayList<>();
                arrayList2.addAll(this.mPendingChanges);
                this.mChangesList.add(arrayList2);
                this.mPendingChanges.clear();
                Runnable runnable2 = new Runnable() { // from class: com.xiaopeng.appstore.applist_ui.animation.BaseItemAnimator.2
                    @Override // java.lang.Runnable
                    public void run() {
                        if (BaseItemAnimator.this.mChangesList.remove(arrayList2)) {
                            Iterator it2 = arrayList2.iterator();
                            while (it2.hasNext()) {
                                BaseItemAnimator.this.animateChangeImpl((ChangeInfo) it2.next());
                            }
                            arrayList2.clear();
                        }
                    }
                };
                if (z) {
                    ViewCompat.postOnAnimationDelayed(arrayList2.get(0).oldHolder.itemView, runnable2, getRemoveDuration());
                } else {
                    runnable2.run();
                }
            }
            if (z4) {
                final ArrayList<RecyclerView.ViewHolder> arrayList3 = new ArrayList<>();
                arrayList3.addAll(this.mPendingAdditions);
                this.mAdditionsList.add(arrayList3);
                this.mPendingAdditions.clear();
                Runnable runnable3 = new Runnable() { // from class: com.xiaopeng.appstore.applist_ui.animation.BaseItemAnimator.3
                    @Override // java.lang.Runnable
                    public void run() {
                        if (BaseItemAnimator.this.mAdditionsList.remove(arrayList3)) {
                            Iterator it2 = arrayList3.iterator();
                            while (it2.hasNext()) {
                                BaseItemAnimator.this.doAnimateAdd((RecyclerView.ViewHolder) it2.next());
                            }
                            arrayList3.clear();
                        }
                    }
                };
                if (z || z2 || z3) {
                    ViewCompat.postOnAnimationDelayed(arrayList3.get(0).itemView, runnable3, (z ? getRemoveDuration() : 0L) + Math.max(z2 ? getMoveDuration() : 0L, z3 ? getChangeDuration() : 0L));
                } else {
                    runnable3.run();
                }
            }
        }
    }

    private void preAnimateRemove(final RecyclerView.ViewHolder holder) {
        ViewHelper.clear(holder.itemView);
        preAnimateRemoveImpl(holder);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void preAnimateAdd(final RecyclerView.ViewHolder holder) {
        ViewHelper.clear(holder.itemView);
        preAnimateAddImpl(holder);
    }

    private void doAnimateRemove(final RecyclerView.ViewHolder holder) {
        animateRemoveImpl(holder);
        this.mRemoveAnimations.add(holder);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doAnimateAdd(final RecyclerView.ViewHolder holder) {
        animateAddImpl(holder);
        this.mAddAnimations.add(holder);
    }

    @Override // androidx.recyclerview.widget.SimpleItemAnimator
    public boolean animateRemove(final RecyclerView.ViewHolder holder) {
        endAnimation(holder);
        preAnimateRemove(holder);
        this.mPendingRemovals.add(holder);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public long getRemoveDelay(final RecyclerView.ViewHolder holder) {
        return Math.abs((holder.getOldPosition() * getRemoveDuration()) / 4);
    }

    @Override // androidx.recyclerview.widget.SimpleItemAnimator
    public boolean animateAdd(final RecyclerView.ViewHolder holder) {
        endAnimation(holder);
        preAnimateAdd(holder);
        this.mPendingAdditions.add(holder);
        return true;
    }

    protected long getAddDelay(final RecyclerView.ViewHolder holder) {
        return Math.abs((holder.getAdapterPosition() * getAddDuration()) / 4);
    }

    @Override // androidx.recyclerview.widget.SimpleItemAnimator
    public boolean animateMove(final RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        View view = holder.itemView;
        int translationX = (int) (fromX + ViewCompat.getTranslationX(holder.itemView));
        int translationY = (int) (fromY + ViewCompat.getTranslationY(holder.itemView));
        endAnimation(holder);
        int i = toX - translationX;
        int i2 = toY - translationY;
        if (i == 0 && i2 == 0) {
            dispatchMoveFinished(holder);
            return false;
        }
        if (i != 0) {
            ViewCompat.setTranslationX(view, -i);
        }
        if (i2 != 0) {
            ViewCompat.setTranslationY(view, -i2);
        }
        this.mPendingMoves.add(new MoveInfo(holder, translationX, translationY, toX, toY));
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animateMoveImpl(final RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        View view = holder.itemView;
        final int i = toX - fromX;
        final int i2 = toY - fromY;
        if (i != 0) {
            ViewCompat.animate(view).translationX(0.0f);
        }
        if (i2 != 0) {
            ViewCompat.animate(view).translationY(0.0f);
        }
        this.mMoveAnimations.add(holder);
        final ViewPropertyAnimatorCompat animate = ViewCompat.animate(view);
        animate.setDuration(getMoveDuration()).setListener(new VpaListenerAdapter() { // from class: com.xiaopeng.appstore.applist_ui.animation.BaseItemAnimator.4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.xiaopeng.appstore.applist_ui.animation.BaseItemAnimator.VpaListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
            public void onAnimationStart(View view2) {
                BaseItemAnimator.this.dispatchMoveStarting(holder);
            }

            @Override // com.xiaopeng.appstore.applist_ui.animation.BaseItemAnimator.VpaListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
            public void onAnimationCancel(View view2) {
                if (i != 0) {
                    ViewCompat.setTranslationX(view2, 0.0f);
                }
                if (i2 != 0) {
                    ViewCompat.setTranslationY(view2, 0.0f);
                }
            }

            @Override // com.xiaopeng.appstore.applist_ui.animation.BaseItemAnimator.VpaListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
            public void onAnimationEnd(View view2) {
                animate.setListener(null);
                BaseItemAnimator.this.dispatchMoveFinished(holder);
                BaseItemAnimator.this.mMoveAnimations.remove(holder);
                BaseItemAnimator.this.dispatchFinishedWhenDone();
            }
        }).start();
    }

    @Override // androidx.recyclerview.widget.SimpleItemAnimator
    public boolean animateChange(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder, int fromX, int fromY, int toX, int toY) {
        if (oldHolder == newHolder) {
            return animateMove(oldHolder, fromX, fromY, toX, toY);
        }
        float translationX = ViewCompat.getTranslationX(oldHolder.itemView);
        float translationY = ViewCompat.getTranslationY(oldHolder.itemView);
        float alpha = ViewCompat.getAlpha(oldHolder.itemView);
        endAnimation(oldHolder);
        int i = (int) ((toX - fromX) - translationX);
        int i2 = (int) ((toY - fromY) - translationY);
        ViewCompat.setTranslationX(oldHolder.itemView, translationX);
        ViewCompat.setTranslationY(oldHolder.itemView, translationY);
        ViewCompat.setAlpha(oldHolder.itemView, alpha);
        if (newHolder != null && newHolder.itemView != null) {
            endAnimation(newHolder);
            ViewCompat.setTranslationX(newHolder.itemView, -i);
            ViewCompat.setTranslationY(newHolder.itemView, -i2);
            ViewCompat.setAlpha(newHolder.itemView, 0.0f);
        }
        this.mPendingChanges.add(new ChangeInfo(oldHolder, newHolder, fromX, fromY, toX, toY));
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animateChangeImpl(final ChangeInfo changeInfo) {
        RecyclerView.ViewHolder viewHolder = changeInfo.oldHolder;
        View view = viewHolder == null ? null : viewHolder.itemView;
        RecyclerView.ViewHolder viewHolder2 = changeInfo.newHolder;
        final View view2 = viewHolder2 != null ? viewHolder2.itemView : null;
        if (view != null) {
            this.mChangeAnimations.add(changeInfo.oldHolder);
            final ViewPropertyAnimatorCompat duration = ViewCompat.animate(view).setDuration(getChangeDuration());
            duration.translationX(changeInfo.toX - changeInfo.fromX);
            duration.translationY(changeInfo.toY - changeInfo.fromY);
            duration.alpha(0.0f).setListener(new VpaListenerAdapter() { // from class: com.xiaopeng.appstore.applist_ui.animation.BaseItemAnimator.5
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super();
                }

                @Override // com.xiaopeng.appstore.applist_ui.animation.BaseItemAnimator.VpaListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
                public void onAnimationStart(View view3) {
                    BaseItemAnimator.this.dispatchChangeStarting(changeInfo.oldHolder, true);
                }

                @Override // com.xiaopeng.appstore.applist_ui.animation.BaseItemAnimator.VpaListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
                public void onAnimationEnd(View view3) {
                    duration.setListener(null);
                    ViewCompat.setAlpha(view3, 1.0f);
                    ViewCompat.setTranslationX(view3, 0.0f);
                    ViewCompat.setTranslationY(view3, 0.0f);
                    BaseItemAnimator.this.dispatchChangeFinished(changeInfo.oldHolder, true);
                    BaseItemAnimator.this.mChangeAnimations.remove(changeInfo.oldHolder);
                    BaseItemAnimator.this.dispatchFinishedWhenDone();
                }
            }).start();
        }
        if (view2 != null) {
            this.mChangeAnimations.add(changeInfo.newHolder);
            final ViewPropertyAnimatorCompat animate = ViewCompat.animate(view2);
            animate.translationX(0.0f).translationY(0.0f).setDuration(getChangeDuration()).alpha(1.0f).setListener(new VpaListenerAdapter() { // from class: com.xiaopeng.appstore.applist_ui.animation.BaseItemAnimator.6
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super();
                }

                @Override // com.xiaopeng.appstore.applist_ui.animation.BaseItemAnimator.VpaListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
                public void onAnimationStart(View view3) {
                    BaseItemAnimator.this.dispatchChangeStarting(changeInfo.newHolder, false);
                }

                @Override // com.xiaopeng.appstore.applist_ui.animation.BaseItemAnimator.VpaListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
                public void onAnimationEnd(View view3) {
                    animate.setListener(null);
                    ViewCompat.setAlpha(view2, 1.0f);
                    ViewCompat.setTranslationX(view2, 0.0f);
                    ViewCompat.setTranslationY(view2, 0.0f);
                    BaseItemAnimator.this.dispatchChangeFinished(changeInfo.newHolder, false);
                    BaseItemAnimator.this.mChangeAnimations.remove(changeInfo.newHolder);
                    BaseItemAnimator.this.dispatchFinishedWhenDone();
                }
            }).start();
        }
    }

    private void endChangeAnimation(List<ChangeInfo> infoList, RecyclerView.ViewHolder item) {
        for (int size = infoList.size() - 1; size >= 0; size--) {
            ChangeInfo changeInfo = infoList.get(size);
            if (endChangeAnimationIfNecessary(changeInfo, item) && changeInfo.oldHolder == null && changeInfo.newHolder == null) {
                infoList.remove(changeInfo);
            }
        }
    }

    private void endChangeAnimationIfNecessary(ChangeInfo changeInfo) {
        if (changeInfo.oldHolder != null) {
            endChangeAnimationIfNecessary(changeInfo, changeInfo.oldHolder);
        }
        if (changeInfo.newHolder != null) {
            endChangeAnimationIfNecessary(changeInfo, changeInfo.newHolder);
        }
    }

    private boolean endChangeAnimationIfNecessary(ChangeInfo changeInfo, RecyclerView.ViewHolder item) {
        boolean z = false;
        if (changeInfo.newHolder == item) {
            changeInfo.newHolder = null;
        } else if (changeInfo.oldHolder != item) {
            return false;
        } else {
            changeInfo.oldHolder = null;
            z = true;
        }
        ViewCompat.setAlpha(item.itemView, 1.0f);
        ViewCompat.setTranslationX(item.itemView, 0.0f);
        ViewCompat.setTranslationY(item.itemView, 0.0f);
        dispatchChangeFinished(item, z);
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
    public void endAnimation(RecyclerView.ViewHolder item) {
        View view = item.itemView;
        ViewCompat.animate(view).cancel();
        int size = this.mPendingMoves.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            } else if (this.mPendingMoves.get(size).holder == item) {
                ViewCompat.setTranslationY(view, 0.0f);
                ViewCompat.setTranslationX(view, 0.0f);
                dispatchMoveFinished(item);
                this.mPendingMoves.remove(size);
            }
        }
        endChangeAnimation(this.mPendingChanges, item);
        if (this.mPendingRemovals.remove(item)) {
            ViewHelper.clear(item.itemView);
            dispatchRemoveFinished(item);
        }
        if (this.mPendingAdditions.remove(item)) {
            ViewHelper.clear(item.itemView);
            dispatchAddFinished(item);
        }
        for (int size2 = this.mChangesList.size() - 1; size2 >= 0; size2--) {
            ArrayList<ChangeInfo> arrayList = this.mChangesList.get(size2);
            endChangeAnimation(arrayList, item);
            if (arrayList.isEmpty()) {
                this.mChangesList.remove(size2);
            }
        }
        for (int size3 = this.mMovesList.size() - 1; size3 >= 0; size3--) {
            ArrayList<MoveInfo> arrayList2 = this.mMovesList.get(size3);
            int size4 = arrayList2.size() - 1;
            while (true) {
                if (size4 < 0) {
                    break;
                } else if (arrayList2.get(size4).holder == item) {
                    ViewCompat.setTranslationY(view, 0.0f);
                    ViewCompat.setTranslationX(view, 0.0f);
                    dispatchMoveFinished(item);
                    arrayList2.remove(size4);
                    if (arrayList2.isEmpty()) {
                        this.mMovesList.remove(size3);
                    }
                } else {
                    size4--;
                }
            }
        }
        for (int size5 = this.mAdditionsList.size() - 1; size5 >= 0; size5--) {
            ArrayList<RecyclerView.ViewHolder> arrayList3 = this.mAdditionsList.get(size5);
            if (arrayList3.remove(item)) {
                ViewHelper.clear(item.itemView);
                dispatchAddFinished(item);
                if (arrayList3.isEmpty()) {
                    this.mAdditionsList.remove(size5);
                }
            }
        }
        this.mRemoveAnimations.remove(item);
        this.mAddAnimations.remove(item);
        this.mChangeAnimations.remove(item);
        this.mMoveAnimations.remove(item);
        dispatchFinishedWhenDone();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
    public boolean isRunning() {
        return (this.mPendingAdditions.isEmpty() && this.mPendingChanges.isEmpty() && this.mPendingMoves.isEmpty() && this.mPendingRemovals.isEmpty() && this.mMoveAnimations.isEmpty() && this.mRemoveAnimations.isEmpty() && this.mAddAnimations.isEmpty() && this.mChangeAnimations.isEmpty() && this.mMovesList.isEmpty() && this.mAdditionsList.isEmpty() && this.mChangesList.isEmpty()) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchFinishedWhenDone() {
        if (isRunning()) {
            return;
        }
        dispatchAnimationsFinished();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
    public void endAnimations() {
        int size = this.mPendingMoves.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            MoveInfo moveInfo = this.mPendingMoves.get(size);
            View view = moveInfo.holder.itemView;
            ViewCompat.setTranslationY(view, 0.0f);
            ViewCompat.setTranslationX(view, 0.0f);
            dispatchMoveFinished(moveInfo.holder);
            this.mPendingMoves.remove(size);
        }
        for (int size2 = this.mPendingRemovals.size() - 1; size2 >= 0; size2--) {
            dispatchRemoveFinished(this.mPendingRemovals.get(size2));
            this.mPendingRemovals.remove(size2);
        }
        for (int size3 = this.mPendingAdditions.size() - 1; size3 >= 0; size3--) {
            RecyclerView.ViewHolder viewHolder = this.mPendingAdditions.get(size3);
            ViewHelper.clear(viewHolder.itemView);
            dispatchAddFinished(viewHolder);
            this.mPendingAdditions.remove(size3);
        }
        for (int size4 = this.mPendingChanges.size() - 1; size4 >= 0; size4--) {
            endChangeAnimationIfNecessary(this.mPendingChanges.get(size4));
        }
        this.mPendingChanges.clear();
        if (isRunning()) {
            for (int size5 = this.mMovesList.size() - 1; size5 >= 0; size5--) {
                ArrayList<MoveInfo> arrayList = this.mMovesList.get(size5);
                for (int size6 = arrayList.size() - 1; size6 >= 0; size6--) {
                    MoveInfo moveInfo2 = arrayList.get(size6);
                    View view2 = moveInfo2.holder.itemView;
                    ViewCompat.setTranslationY(view2, 0.0f);
                    ViewCompat.setTranslationX(view2, 0.0f);
                    dispatchMoveFinished(moveInfo2.holder);
                    arrayList.remove(size6);
                    if (arrayList.isEmpty()) {
                        this.mMovesList.remove(arrayList);
                    }
                }
            }
            for (int size7 = this.mAdditionsList.size() - 1; size7 >= 0; size7--) {
                ArrayList<RecyclerView.ViewHolder> arrayList2 = this.mAdditionsList.get(size7);
                for (int size8 = arrayList2.size() - 1; size8 >= 0; size8--) {
                    RecyclerView.ViewHolder viewHolder2 = arrayList2.get(size8);
                    ViewCompat.setAlpha(viewHolder2.itemView, 1.0f);
                    dispatchAddFinished(viewHolder2);
                    if (size8 < arrayList2.size()) {
                        arrayList2.remove(size8);
                    }
                    if (arrayList2.isEmpty()) {
                        this.mAdditionsList.remove(arrayList2);
                    }
                }
            }
            for (int size9 = this.mChangesList.size() - 1; size9 >= 0; size9--) {
                ArrayList<ChangeInfo> arrayList3 = this.mChangesList.get(size9);
                for (int size10 = arrayList3.size() - 1; size10 >= 0; size10--) {
                    endChangeAnimationIfNecessary(arrayList3.get(size10));
                    if (arrayList3.isEmpty()) {
                        this.mChangesList.remove(arrayList3);
                    }
                }
            }
            cancelAll(this.mRemoveAnimations);
            cancelAll(this.mMoveAnimations);
            cancelAll(this.mAddAnimations);
            cancelAll(this.mChangeAnimations);
            dispatchAnimationsFinished();
        }
    }

    void cancelAll(List<RecyclerView.ViewHolder> viewHolders) {
        for (int size = viewHolders.size() - 1; size >= 0; size--) {
            ViewCompat.animate(viewHolders.get(size).itemView).cancel();
        }
    }

    /* loaded from: classes2.dex */
    private static class VpaListenerAdapter implements ViewPropertyAnimatorListener {
        @Override // androidx.core.view.ViewPropertyAnimatorListener
        public void onAnimationCancel(View view) {
        }

        @Override // androidx.core.view.ViewPropertyAnimatorListener
        public void onAnimationEnd(View view) {
        }

        @Override // androidx.core.view.ViewPropertyAnimatorListener
        public void onAnimationStart(View view) {
        }

        private VpaListenerAdapter() {
        }
    }

    /* loaded from: classes2.dex */
    protected class DefaultAddVpaListener extends VpaListenerAdapter {
        RecyclerView.ViewHolder mViewHolder;

        public DefaultAddVpaListener(final RecyclerView.ViewHolder holder) {
            super();
            this.mViewHolder = holder;
        }

        @Override // com.xiaopeng.appstore.applist_ui.animation.BaseItemAnimator.VpaListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
        public void onAnimationStart(View view) {
            BaseItemAnimator.this.dispatchAddStarting(this.mViewHolder);
        }

        @Override // com.xiaopeng.appstore.applist_ui.animation.BaseItemAnimator.VpaListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
        public void onAnimationCancel(View view) {
            ViewHelper.clear(view);
        }

        @Override // com.xiaopeng.appstore.applist_ui.animation.BaseItemAnimator.VpaListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
        public void onAnimationEnd(View view) {
            ViewHelper.clear(view);
            BaseItemAnimator.this.dispatchAddFinished(this.mViewHolder);
            BaseItemAnimator.this.mAddAnimations.remove(this.mViewHolder);
            BaseItemAnimator.this.dispatchFinishedWhenDone();
        }
    }

    /* loaded from: classes2.dex */
    protected class DefaultRemoveVpaListener extends VpaListenerAdapter {
        RecyclerView.ViewHolder mViewHolder;

        public DefaultRemoveVpaListener(final RecyclerView.ViewHolder holder) {
            super();
            this.mViewHolder = holder;
        }

        @Override // com.xiaopeng.appstore.applist_ui.animation.BaseItemAnimator.VpaListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
        public void onAnimationStart(View view) {
            BaseItemAnimator.this.dispatchRemoveStarting(this.mViewHolder);
        }

        @Override // com.xiaopeng.appstore.applist_ui.animation.BaseItemAnimator.VpaListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
        public void onAnimationCancel(View view) {
            ViewHelper.clear(view);
        }

        @Override // com.xiaopeng.appstore.applist_ui.animation.BaseItemAnimator.VpaListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
        public void onAnimationEnd(View view) {
            ViewHelper.clear(view);
            BaseItemAnimator.this.dispatchRemoveFinished(this.mViewHolder);
            BaseItemAnimator.this.mRemoveAnimations.remove(this.mViewHolder);
            BaseItemAnimator.this.dispatchFinishedWhenDone();
        }
    }
}
