package com.xiaopeng.lib.framework.moduleinterface.demoservice;

import io.reactivex.Observable;
/* loaded from: classes2.dex */
public interface IDemoService {

    /* loaded from: classes2.dex */
    public static class DemoMessageEvent {
        public String mEvent;
        public long mParam1;
        public String mParam2;
    }

    void fireMessageEventInMainThread();

    void fireMessageEventInWorkThread();

    void getPage(String URL, ICallback callback);

    Observable<String> getPageObservable(String URL);

    String getWelcomeMessage();
}
