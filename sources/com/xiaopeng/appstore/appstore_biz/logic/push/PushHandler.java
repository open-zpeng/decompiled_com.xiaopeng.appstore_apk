package com.xiaopeng.appstore.appstore_biz.logic.push;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.xpcommon.push.PushedMessageBean;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
/* loaded from: classes2.dex */
public class PushHandler {
    private static final int APPSTORE_MSG_TYPE = 49;
    private static final int PUSH_DOWNLOAD_FROM_MOBILE_APP = 4901;
    private static final String TAG = "PushHandler";
    private static final ExecutorService sBgExecutor = Executors.newFixedThreadPool(3, new ThreadFactory() { // from class: com.xiaopeng.appstore.appstore_biz.logic.push.PushHandler.1
        private static final String THREAD_NAME_STEM = "PushHandler_%d";
        private final AtomicInteger mThreadId = new AtomicInteger(0);

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName(String.format(THREAD_NAME_STEM, Integer.valueOf(this.mThreadId.getAndIncrement())));
            return thread;
        }
    });

    public static void handleClick(Context context, int sceneId) {
        Logger.t(TAG).i("handleClick, scene:" + sceneId, new Object[0]);
        if (sceneId == 55001) {
            Intent intent = new Intent("com.xiaopeng.intent.action.SHOW_APP_LIST");
            intent.setPackage("com.xiaopeng.appstore");
            context.sendBroadcast(intent);
        }
    }

    public static void handlePush(final Context context, final PushedMessageBean msg) {
        Logger.t(TAG).i("Handle push:" + msg, new Object[0]);
        if (msg == null) {
            return;
        }
        if (msg.getMessageType() == 49) {
            if (msg.getScene() == PUSH_DOWNLOAD_FROM_MOBILE_APP) {
                sBgExecutor.execute(new Runnable() { // from class: com.xiaopeng.appstore.appstore_biz.logic.push.-$$Lambda$PushHandler$7voUU51-ORQm0jU-PjqZcPS5GMo
                    @Override // java.lang.Runnable
                    public final void run() {
                        PushHandler.lambda$handlePush$0(PushedMessageBean.this, context);
                    }
                });
                return;
            } else {
                Logger.t(TAG).w("Not support scene:" + msg, new Object[0]);
                return;
            }
        }
        Logger.t(TAG).w("Not appstore msgType:" + msg, new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$handlePush$0(PushedMessageBean pushedMessageBean, Context context) {
        DownloadAppMsg downloadAppMsg;
        try {
            downloadAppMsg = (DownloadAppMsg) new Gson().fromJson(pushedMessageBean.getContent(), (Class<Object>) DownloadAppMsg.class);
        } catch (JsonSyntaxException e) {
            Logger.t(TAG).w("DownloadAppMsg JsonSyntaxException ", new Object[0]);
            e.printStackTrace();
            downloadAppMsg = null;
        }
        PushDownloadService.actionStart(context, downloadAppMsg);
    }

    /* loaded from: classes2.dex */
    public static class DownloadAppMsg implements Parcelable {
        public static final Parcelable.Creator<DownloadAppMsg> CREATOR = new Parcelable.Creator<DownloadAppMsg>() { // from class: com.xiaopeng.appstore.appstore_biz.logic.push.PushHandler.DownloadAppMsg.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DownloadAppMsg createFromParcel(Parcel in) {
                return new DownloadAppMsg(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DownloadAppMsg[] newArray(int size) {
                return new DownloadAppMsg[size];
            }
        };
        public Icon app_icons;
        public String app_name;
        public String config_md5;
        public String config_url;
        public String download_url;
        public String md5;
        public String package_name;
        public String version_code;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        protected DownloadAppMsg(Parcel in) {
            this.app_name = in.readString();
            this.package_name = in.readString();
            this.version_code = in.readString();
            this.download_url = in.readString();
            this.md5 = in.readString();
            this.config_url = in.readString();
            this.config_md5 = in.readString();
            this.app_icons = (Icon) in.readParcelable(Icon.class.getClassLoader());
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.app_name);
            dest.writeString(this.package_name);
            dest.writeString(this.version_code);
            dest.writeString(this.download_url);
            dest.writeString(this.md5);
            dest.writeString(this.config_url);
            dest.writeString(this.config_md5);
            dest.writeParcelable(this.app_icons, flags);
        }

        public String toString() {
            return "DownloadAppMsg{app_name='" + this.app_name + "', package_name='" + this.package_name + "', version_code='" + this.version_code + "', download_url='" + this.download_url + "', md5='" + this.md5 + "', config_url='" + this.config_url + "', config_md5='" + this.config_md5 + "', app_icons=" + this.app_icons + '}';
        }
    }

    /* loaded from: classes2.dex */
    public static class Icon implements Parcelable {
        public static final Parcelable.Creator<Icon> CREATOR = new Parcelable.Creator<Icon>() { // from class: com.xiaopeng.appstore.appstore_biz.logic.push.PushHandler.Icon.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Icon createFromParcel(Parcel in) {
                return new Icon(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Icon[] newArray(int size) {
                return new Icon[size];
            }
        };
        public String large_icon;
        public String small_icon;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        protected Icon(Parcel in) {
            this.small_icon = in.readString();
            this.large_icon = in.readString();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.small_icon);
            dest.writeString(this.large_icon);
        }

        public String toString() {
            return "Icon{small_icon='" + this.small_icon + "', large_icon='" + this.large_icon + "'}";
        }
    }
}
