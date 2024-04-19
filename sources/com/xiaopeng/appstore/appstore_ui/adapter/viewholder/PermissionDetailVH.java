package com.xiaopeng.appstore.appstore_ui.adapter.viewholder;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.appstore.appstore_biz.model2.PermissionItemModel;
import com.xiaopeng.appstore.appstore_ui.R;
/* loaded from: classes2.dex */
public class PermissionDetailVH extends RecyclerView.ViewHolder {
    private TextView mTvContent;
    private TextView mTvTitle;

    public PermissionDetailVH(View itemView) {
        super(itemView);
        this.mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        this.mTvContent = (TextView) itemView.findViewById(R.id.tv_content);
    }

    public void updateView(PermissionItemModel data) {
        this.mTvTitle.setText(data.getTitleName());
        this.mTvContent.setText(data.getContent());
        if (data.isShowTitle()) {
            this.mTvTitle.setVisibility(0);
        } else {
            this.mTvTitle.setVisibility(4);
        }
    }
}
