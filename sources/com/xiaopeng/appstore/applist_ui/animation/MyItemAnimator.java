package com.xiaopeng.appstore.applist_ui.animation;

import android.view.View;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.appstore.applist_ui.animation.BaseItemAnimator;
import java.util.List;
/* loaded from: classes2.dex */
public class MyItemAnimator extends BaseItemAnimator {
    private static final String TAG = "MyItemAnimator";

    @Override // com.xiaopeng.appstore.applist_ui.animation.BaseItemAnimator
    protected void animateRemoveImpl(RecyclerView.ViewHolder holder) {
        ViewCompat.animate(holder.itemView).alpha(0.0f).setDuration(getRemoveDuration()).setInterpolator(this.mInterpolator).setListener(new BaseItemAnimator.DefaultRemoveVpaListener(holder)).setStartDelay(getRemoveDelay(holder)).start();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.appstore.applist_ui.animation.BaseItemAnimator
    public void preAnimateAdd(RecyclerView.ViewHolder holder) {
        super.preAnimateAdd(holder);
        holder.itemView.setAlpha(0.0f);
    }

    @Override // com.xiaopeng.appstore.applist_ui.animation.BaseItemAnimator, androidx.recyclerview.widget.SimpleItemAnimator
    public boolean animateMove(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        View view = holder.itemView;
        int translationX = (int) (fromX + ViewCompat.getTranslationX(holder.itemView));
        int translationY = (int) (fromY + ViewCompat.getTranslationY(holder.itemView));
        endAnimation(holder);
        int i = toX - translationX;
        int i2 = toY - translationY;
        if (i == 0 && i2 == 0) {
            dispatchMoveFinished(holder);
            return false;
        } else if (i != 0 && i2 != 0) {
            int measuredWidth = holder.itemView.getMeasuredWidth();
            if (i2 > 0) {
                ViewCompat.setTranslationX(view, -measuredWidth);
                this.mPendingMoves.add(new BaseItemAnimator.MoveInfo(holder, translationX, translationY, toX, toY));
                return true;
            }
            ViewCompat.setTranslationX(view, measuredWidth);
            this.mPendingMoves.add(new BaseItemAnimator.MoveInfo(holder, translationX, translationY, toX, toY));
            return true;
        } else {
            return super.animateMove(holder, translationX, translationY, toX, toY);
        }
    }

    @Override // androidx.recyclerview.widget.SimpleItemAnimator, androidx.recyclerview.widget.RecyclerView.ItemAnimator
    public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
        return super.canReuseUpdatedViewHolder(viewHolder);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
    public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder, List<Object> payloads) {
        return super.canReuseUpdatedViewHolder(viewHolder, payloads);
    }

    @Override // androidx.recyclerview.widget.SimpleItemAnimator, androidx.recyclerview.widget.RecyclerView.ItemAnimator
    public boolean animateDisappearance(RecyclerView.ViewHolder viewHolder, RecyclerView.ItemAnimator.ItemHolderInfo preLayoutInfo, RecyclerView.ItemAnimator.ItemHolderInfo postLayoutInfo) {
        return super.animateDisappearance(viewHolder, preLayoutInfo, postLayoutInfo);
    }

    @Override // androidx.recyclerview.widget.SimpleItemAnimator, androidx.recyclerview.widget.RecyclerView.ItemAnimator
    public boolean animateAppearance(RecyclerView.ViewHolder viewHolder, RecyclerView.ItemAnimator.ItemHolderInfo preLayoutInfo, RecyclerView.ItemAnimator.ItemHolderInfo postLayoutInfo) {
        return super.animateAppearance(viewHolder, preLayoutInfo, postLayoutInfo);
    }

    @Override // com.xiaopeng.appstore.applist_ui.animation.BaseItemAnimator
    protected void animateAddImpl(RecyclerView.ViewHolder holder) {
        ViewCompat.animate(holder.itemView).alpha(1.0f).setDuration(getAddDuration()).setInterpolator(this.mInterpolator).setListener(new BaseItemAnimator.DefaultAddVpaListener(holder)).setStartDelay(getAddDelay(holder)).start();
    }

    @Override // com.xiaopeng.appstore.applist_ui.animation.BaseItemAnimator
    protected long getAddDelay(RecyclerView.ViewHolder holder) {
        return getAddDuration() / 4;
    }
}
