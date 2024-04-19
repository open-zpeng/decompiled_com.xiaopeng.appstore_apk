package com.xiaopeng.appstore.applet_biz.model;
/* loaded from: classes2.dex */
public class SpeechCommand {
    private String action;
    private String category;
    private String miniprog;

    public SpeechCommand(String action, String miniprog, String category) {
        this.action = action;
        this.miniprog = miniprog;
        this.category = category;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMiniprog() {
        return this.miniprog;
    }

    public void setMiniprog(String miniprog) {
        this.miniprog = miniprog;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
