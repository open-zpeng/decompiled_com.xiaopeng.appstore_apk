package com.xiaopeng.appstore.xpcommon.push;

import android.content.Context;
import android.net.Uri;
import android.os.RemoteException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.xpcommon.CarUtils;
import com.xiaopeng.lib.apirouter.ApiRouter;
import com.xiaopeng.lib.framework.aiassistantmodule.AiAssistantModuleEntry;
import com.xiaopeng.lib.framework.ipcmodule.IpcModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.notification.INotification;
import com.xiaopeng.lib.framework.moduleinterface.ipcmodule.IIpcService;
import com.xiaopeng.libconfig.ipc.IpcConfig;
import com.xiaopeng.libconfig.ipc.bean.MessageCenterBean;
import com.xiaopeng.libconfig.ipc.bean.MessageContentBean;
import java.util.HashSet;
import java.util.Set;
/* loaded from: classes2.dex */
public class PushHelper {
    public static final int APP_DOWNLOAD_COMPLETE_CARD_CLICK_SCENE = 55001;
    private static final int BIZTYPE = 47;
    private static final long DEFAULT_MESSAGE_VALID_TIME = 600000;
    private static final String TAG = "PushHelper";
    static Set<OnPushMsgListener> pushMsgListeners = new HashSet();

    /* loaded from: classes2.dex */
    public interface OnPushMsgListener {
        void onPushCardClick(int sceneId);

        void onPushMsgReceived(PushedMessageBean msg);
    }

    public static void init(Context context) {
        Module.register(IpcModuleEntry.class, new IpcModuleEntry(context));
        ((IIpcService) Module.get(IpcModuleEntry.class).get(IIpcService.class)).init();
        Module.register(AiAssistantModuleEntry.class, new AiAssistantModuleEntry(context));
        PushIpcReceiver.init();
    }

    public static void setApplicationReady() {
        PushApiRouterReceiver.setApplicationReady();
    }

    public static void registerListener(OnPushMsgListener onPushMsgListener) {
        Logger.t(TAG).i("registerListener:" + onPushMsgListener, new Object[0]);
        pushMsgListeners.add(onPushMsgListener);
    }

    public static void unregisterListener(OnPushMsgListener onPushMsgListener) {
        Logger.t(TAG).i("unregisterListener:" + onPushMsgListener, new Object[0]);
        pushMsgListeners.remove(onPushMsgListener);
    }

    public static void sendMessageToMessageCenter(Context context, int scene, String title, String subTitle, String promptTts, String wakeWords, String responseTts, String buttonTitle, long validTime, boolean permanent) {
        MessageContentBean createContent = MessageContentBean.createContent();
        createContent.setType(1);
        createContent.setValidTime(0L);
        createContent.addTitle(title);
        createContent.addTitle(subTitle);
        createContent.setTts(promptTts);
        MessageContentBean.MsgButton create = MessageContentBean.MsgButton.create(buttonTitle, context.getPackageName(), scene + "");
        create.setSpeechResponse(true);
        create.setResponseWord(wakeWords);
        create.setResponseTTS(responseTts);
        createContent.addButton(create);
        long currentTimeMillis = System.currentTimeMillis();
        if (validTime <= 0) {
            validTime = DEFAULT_MESSAGE_VALID_TIME;
        }
        long j = validTime + currentTimeMillis;
        createContent.setValidTime(j);
        if (permanent) {
            createContent.setPermanent(1);
        }
        MessageCenterBean create2 = MessageCenterBean.create(47, createContent);
        create2.setScene(scene);
        create2.setValidStartTs(currentTimeMillis);
        create2.setValidEndTs(j);
        sendMessageToMessageCenter(create2);
    }

    public static void sendMessageToMessageCenter(MessageCenterBean bean) {
        if (isDxCar()) {
            ((INotification) Module.get(AiAssistantModuleEntry.class).get(INotification.class)).send(new Gson().toJson(bean));
            return;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(IpcConfig.IPCKey.STRING_MSG, new Gson().toJson(bean));
        try {
            ApiRouter.route(new Uri.Builder().authority("com.xiaopeng.aiassistant.IpcRouterService").path("onReceiverData").appendQueryParameter("id", String.valueOf((int) IpcConfig.MessageCenterConfig.IPC_ID_APP_PUSH_MESSAGE)).appendQueryParameter("bundle", jsonObject.toString()).build());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private static boolean isDxCar() {
        String cduType = CarUtils.getCduType();
        cduType.hashCode();
        char c = 65535;
        switch (cduType.hashCode()) {
            case 2561:
                if (cduType.equals("Q2")) {
                    c = 0;
                    break;
                }
                break;
            case 2562:
                if (cduType.equals("Q3")) {
                    c = 1;
                    break;
                }
                break;
            case 2564:
                if (cduType.equals("Q5")) {
                    c = 2;
                    break;
                }
                break;
            case 2565:
                if (cduType.equals("Q6")) {
                    c = 3;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
                return true;
            default:
                return false;
        }
    }
}
