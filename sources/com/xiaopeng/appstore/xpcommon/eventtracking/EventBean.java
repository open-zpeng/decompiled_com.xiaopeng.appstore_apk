package com.xiaopeng.appstore.xpcommon.eventtracking;

import com.orhanobut.logger.Logger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/* loaded from: classes2.dex */
class EventBean {
    private String mButtonId;
    private String mButtonName;
    private int mLevel;
    private String mPageId;
    private String mPageName;
    private Map<String, Object> mParams;

    public String toString() {
        StringBuilder sb = new StringBuilder("EventBean{");
        sb.append("mPageId='").append(this.mPageId).append('\'');
        sb.append(", mPageName='").append(this.mPageName).append('\'');
        sb.append(", mButtonId='").append(this.mButtonId).append('\'');
        sb.append(", mButtonName='").append(this.mButtonName).append('\'');
        sb.append(", mLevel=").append(this.mLevel);
        sb.append(", mParams=").append(this.mParams);
        sb.append('}');
        return sb.toString();
    }

    private EventBean(Builder builder) {
        this.mPageId = builder.mPageId;
        this.mPageName = builder.mPageName;
        this.mButtonId = builder.mButtonId;
        this.mButtonName = builder.mButtonName;
        this.mLevel = builder.mLevel;
        this.mParams = builder.mParams;
    }

    public String getPageId() {
        return this.mPageId;
    }

    public String getButtonId() {
        return this.mButtonId;
    }

    public int getLevel() {
        return this.mLevel;
    }

    public Map<String, Object> getParams() {
        return this.mParams;
    }

    /* loaded from: classes2.dex */
    public static final class Builder {
        private static final String TAG = "Builder";
        private String mButtonId;
        private String mButtonName;
        private int mLevel;
        private String mPageId;
        private String mPageName;
        private Map<String, Object> mParams = new ConcurrentHashMap();

        public Builder pageId(String val) {
            this.mPageId = val;
            return this;
        }

        public Builder pageName(String val) {
            this.mPageName = val;
            return this;
        }

        public Builder buttonId(String val) {
            this.mButtonId = val;
            return this;
        }

        public Builder buttonName(String val) {
            this.mButtonName = val;
            return this;
        }

        public Builder level(int level) {
            this.mLevel = level;
            return this;
        }

        public Builder putParam(String name, String value) {
            this.mParams.put(name, value);
            return this;
        }

        public Builder putParam(String name, Boolean value) {
            this.mParams.put(name, value);
            return this;
        }

        public Builder putParam(String name, Character value) {
            this.mParams.put(name, value);
            return this;
        }

        public Builder putParam(String name, Number value) {
            this.mParams.put(name, value);
            return this;
        }

        public Builder setParams(Map<String, Object> params) {
            if (params != null) {
                this.mParams = params;
            } else {
                Logger.t(TAG).e("setParams value can not be null", new Object[0]);
            }
            return this;
        }

        public EventBean build() {
            return new EventBean(this);
        }
    }
}
