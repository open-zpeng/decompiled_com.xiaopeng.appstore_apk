package com.xiaopeng.appstore.appstore_biz.datamodel.entities;

import java.util.List;
/* loaded from: classes2.dex */
public class NewsData {
    private boolean error;
    private List<ResultsBean> results;

    public boolean isError() {
        return this.error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return this.results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    /* loaded from: classes2.dex */
    public static class ResultsBean {
        private String _id;
        private String createdAt;
        private String desc;
        private List<String> images;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;

        public String get_id() {
            return this._id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return this.createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return this.desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return this.publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return this.source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return this.used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return this.who;
        }

        public void setWho(String who) {
            this.who = who;
        }

        public List<String> getImages() {
            return this.images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
