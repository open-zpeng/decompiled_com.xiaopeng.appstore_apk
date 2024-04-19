package com.xiaopeng.appstore.xpcommon;

import android.os.Build;
import android.text.TextUtils;
import androidx.exifinterface.media.ExifInterface;
import com.google.common.base.Ascii;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.libcommon.utils.MD5Utils;
import com.xiaopeng.appstore.libcommon.utils.NetworkUtils;
import com.xiaopeng.appstore.libcommon.utils.Utils;
import com.xiaopeng.appstore.xpcommon.XpOkHttpClientGenerator;
import com.xiaopeng.lib.http.tls.SSLHelper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.function.IntPredicate;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import kotlin.text.Typography;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
/* loaded from: classes2.dex */
public class XpOkHttpClientGenerator {
    public static final String REQUEST_KEY_APP_ID = "XP-Appid";
    public static final String REQUEST_KEY_CLIENT = "XP-Client";
    public static final String REQUEST_KEY_CLIENT_TYPE = "XP-Client-Type";
    public static final String REQUEST_KEY_LANGUAGE = "xp-lbs-language";
    public static final String REQUEST_KEY_NONCE = "XP-Nonce";
    public static final String REQUEST_KEY_REGION = "xp-lbs-region";
    public static final String REQUEST_KEY_SIGN = "XP-Signature";
    public static final String REQUEST_KEY_VIN = "XP-Vin";
    private static final String TAG = "XpOkHttpClientGenerator";

    public static OkHttpClient.Builder getBuilder(CommonParamsProvider paramsProvider) {
        OkHttpClient.Builder newBuilder = new OkHttpClient().newBuilder();
        newBuilder.connectionSpecs(SSLHelper.getConnectionSpecs()).readTimeout(15L, TimeUnit.SECONDS);
        newBuilder.sslSocketFactory(SSLHelper.getTLS2SocketFactory(null), SSLHelper.getX509TrustManager(null));
        newBuilder.addInterceptor(bizHeaderInterceptor(paramsProvider));
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        newBuilder.addInterceptor(httpLoggingInterceptor);
        return newBuilder;
    }

    private static Interceptor bizHeaderInterceptor(final CommonParamsProvider paramsProvider) {
        return new Interceptor() { // from class: com.xiaopeng.appstore.xpcommon.-$$Lambda$XpOkHttpClientGenerator$qRhTJAer9uvyKWIgyIpjtwBKwYE
            @Override // okhttp3.Interceptor
            public final Response intercept(Interceptor.Chain chain) {
                return XpOkHttpClientGenerator.lambda$bizHeaderInterceptor$0(XpOkHttpClientGenerator.CommonParamsProvider.this, chain);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ Response lambda$bizHeaderInterceptor$0(CommonParamsProvider commonParamsProvider, Interceptor.Chain chain) throws IOException {
        String clientInfo = commonParamsProvider.getClientInfo();
        String nonce = commonParamsProvider.getNonce();
        String sign = getSign(commonParamsProvider, chain, clientInfo, nonce);
        Request.Builder addHeader = chain.request().newBuilder().addHeader(REQUEST_KEY_APP_ID, commonParamsProvider.getAppId()).addHeader(REQUEST_KEY_CLIENT_TYPE, commonParamsProvider.getClientType()).addHeader(REQUEST_KEY_CLIENT, clientInfo).addHeader(REQUEST_KEY_NONCE, nonce);
        if (sign == null) {
            sign = "";
        }
        Request.Builder addHeader2 = addHeader.addHeader(REQUEST_KEY_SIGN, sign).addHeader(REQUEST_KEY_VIN, commonParamsProvider.getVin()).addHeader(REQUEST_KEY_LANGUAGE, Locale.getDefault().getLanguage());
        if (CarUtils.isEURegion()) {
            addHeader2.addHeader(REQUEST_KEY_REGION, Locale.getDefault().getCountry());
        }
        return chain.proceed(addHeader2.build());
    }

    private static String getSign(CommonParamsProvider paramsProvider, Interceptor.Chain chain, String clientInfo, String nonce) {
        HttpUrl url = chain.request().url();
        Set<String> queryParameterNames = url.queryParameterNames();
        TreeMap treeMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        for (String str : queryParameterNames) {
            treeMap.put(str, url.queryParameterValues(str));
        }
        treeMap.put(REQUEST_KEY_APP_ID.toUpperCase(), Collections.singletonList(paramsProvider.getAppId()));
        treeMap.put(REQUEST_KEY_CLIENT_TYPE.toUpperCase(), Collections.singletonList(paramsProvider.getClientType()));
        treeMap.put(REQUEST_KEY_CLIENT.toUpperCase(), Collections.singletonList(clientInfo));
        treeMap.put(REQUEST_KEY_NONCE.toUpperCase(), Collections.singletonList(nonce));
        treeMap.put(REQUEST_KEY_VIN.toUpperCase(), Collections.singletonList(paramsProvider.getVin()));
        treeMap.put(REQUEST_KEY_LANGUAGE.toUpperCase(), Collections.singletonList(Locale.getDefault().getLanguage()));
        if (CarUtils.isEURegion()) {
            treeMap.put(REQUEST_KEY_REGION.toUpperCase(), Collections.singletonList(Locale.getDefault().getCountry()));
        }
        String sortParameterAndValues = sortParameterAndValues(treeMap);
        String method = chain.request().method();
        String str2 = "";
        if ("POST".equals(method)) {
            String bodyToString = bodyToString(chain.request().body());
            if (!TextUtils.isEmpty(bodyToString)) {
                str2 = MD5Utils.getMD5(bodyToString);
            }
        }
        String str3 = method + Typography.amp + sortParameterAndValues + Typography.amp + str2;
        Logger.t(TAG).d("Okhttp generateSignStr=" + str3);
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            byte[] bytes = paramsProvider.getAppSecret().getBytes(StandardCharsets.UTF_8);
            mac.init(new SecretKeySpec(bytes, 0, bytes.length, "HmacSHA256"));
            return parseByte2HexStr(mac.doFinal(str3.getBytes(StandardCharsets.UTF_8))).toLowerCase();
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String bodyToString(final RequestBody request) {
        try {
            Buffer buffer = new Buffer();
            if (request != null) {
                request.writeTo(buffer);
                return buffer.readUtf8();
            }
        } catch (IOException unused) {
        }
        return "";
    }

    private static String sortParameterAndValues(Map<String, List<String>> parameterMap) {
        if (parameterMap == null) {
            return "";
        }
        ArrayList<String> arrayList = new ArrayList(parameterMap.keySet());
        Collections.sort(arrayList);
        ArrayList arrayList2 = new ArrayList();
        for (String str : arrayList) {
            List<String> list = parameterMap.get(str);
            if (list != null) {
                Iterator<String> it = list.iterator();
                while (it.hasNext()) {
                    arrayList2.add(str + "=" + it.next());
                }
            }
        }
        return TextUtils.join("&", arrayList2);
    }

    private static String parseByte2HexStr(byte[] buf) {
        if (buf == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder(buf.length * 2);
        for (int i = 0; i < buf.length; i++) {
            sb.append("0123456789ABCDEF".charAt((buf[i] >> 4) & 15)).append("0123456789ABCDEF".charAt(buf[i] & Ascii.SI));
        }
        return sb.toString();
    }

    /* loaded from: classes2.dex */
    public static abstract class CommonParamsProvider {
        protected static final String ST = "3";
        protected static final String sClientType = "3";
        protected static final String sDeviceId = BuildInfoUtils.getHardwareId();
        protected static final String sPackageName = Utils.getPackageName();
        protected static final String sFirmwareVersion = BuildInfoUtils.getSystemVersion().substring(1);
        protected static final String sBrandName = Build.BRAND;
        protected static final String sVin = BuildInfoUtils.getVin();
        protected static final String sCarType = CarUtils.getAppStoreCarType();
        private static int sNonceCounter = 0;

        /* JADX INFO: Access modifiers changed from: package-private */
        public static /* synthetic */ boolean lambda$getSV$0(int i) {
            return i == 46;
        }

        protected abstract String getAppId();

        protected abstract String getAppSecret();

        public String getClientType() {
            return ExifInterface.GPS_MEASUREMENT_3D;
        }

        private static String getNetType() {
            switch (AnonymousClass1.$SwitchMap$com$xiaopeng$appstore$libcommon$utils$NetworkUtils$NetworkType[NetworkUtils.getNetworkType().ordinal()]) {
                case 1:
                    return "2G";
                case 2:
                    return "3G";
                case 3:
                    return "4G";
                case 4:
                    return "wifi";
                case 5:
                case 6:
                default:
                    return "unknown";
                case 7:
                    return "ethernet";
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public String getModel() {
            return sCarType;
        }

        protected String getVE() {
            return "VV1.6.0".replace(ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "");
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public String getSV() {
            StringBuilder sb = new StringBuilder(sFirmwareVersion);
            for (int count = (int) sb.chars().filter(new IntPredicate() { // from class: com.xiaopeng.appstore.xpcommon.-$$Lambda$XpOkHttpClientGenerator$CommonParamsProvider$ulKjSS5HA0PRFoKNTS9WkEZtsdw
                @Override // java.util.function.IntPredicate
                public final boolean test(int i) {
                    return XpOkHttpClientGenerator.CommonParamsProvider.lambda$getSV$0(i);
                }
            }).count(); count < 3; count++) {
                sb.append(".9");
            }
            return sb.toString();
        }

        public String getVin() {
            return sVin;
        }

        public String getNonce() {
            int i = sNonceCounter + 1;
            sNonceCounter = i;
            if (i == 10000) {
                sNonceCounter = 0;
            }
            return String.format(Locale.getDefault(), "%d%04d", Long.valueOf(System.currentTimeMillis()), Integer.valueOf(sNonceCounter));
        }

        public String getClientInfo() {
            return String.format(Locale.getDefault(), "di=%s;ve=%s;pn=%s;br=%s;mo=%s;sv=%s;nt=%s;t=%d;st=%s", sDeviceId, getVE(), sPackageName, sBrandName, getModel(), getSV(), getNetType(), Long.valueOf(System.currentTimeMillis()), ExifInterface.GPS_MEASUREMENT_3D);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.appstore.xpcommon.XpOkHttpClientGenerator$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$appstore$libcommon$utils$NetworkUtils$NetworkType;

        static {
            int[] iArr = new int[NetworkUtils.NetworkType.values().length];
            $SwitchMap$com$xiaopeng$appstore$libcommon$utils$NetworkUtils$NetworkType = iArr;
            try {
                iArr[NetworkUtils.NetworkType.NETWORK_2G.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$appstore$libcommon$utils$NetworkUtils$NetworkType[NetworkUtils.NetworkType.NETWORK_3G.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$appstore$libcommon$utils$NetworkUtils$NetworkType[NetworkUtils.NetworkType.NETWORK_4G.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$appstore$libcommon$utils$NetworkUtils$NetworkType[NetworkUtils.NetworkType.NETWORK_WIFI.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$appstore$libcommon$utils$NetworkUtils$NetworkType[NetworkUtils.NetworkType.NETWORK_NO.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$xiaopeng$appstore$libcommon$utils$NetworkUtils$NetworkType[NetworkUtils.NetworkType.NETWORK_UNKNOWN.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$xiaopeng$appstore$libcommon$utils$NetworkUtils$NetworkType[NetworkUtils.NetworkType.NETWORK_ETHERNET.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }
}
