package com.xiaopeng.appstore.libcommon.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.orhanobut.logger.Logger;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
/* loaded from: classes2.dex */
public class GsonUtil {
    private static final String TAG = "GsonUtil";
    private static Gson sGSon = new GsonBuilder().registerTypeAdapterFactory(new CustomTypeAdapterFactory()).registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
    private static Gson sGsonExclude = new GsonBuilder().registerTypeAdapterFactory(new CustomTypeAdapterFactory()).excludeFieldsWithoutExposeAnnotation().create();
    private static Gson sGsonMap = new GsonBuilder().registerTypeAdapter(new TypeToken<Map<String, Object>>() { // from class: com.xiaopeng.appstore.libcommon.utils.GsonUtil.1
    }.getType(), new MapTypeAdapter()).create();

    public static Map<String, Object> fromJson2Map(String json) {
        return (Map) sGsonMap.fromJson(json, new TypeToken<Map<String, Object>>() { // from class: com.xiaopeng.appstore.libcommon.utils.GsonUtil.2
        }.getType());
    }

    public static String toJson(Object o) {
        return sGSon.toJson(o);
    }

    public static String toJson(Object o, boolean isExclude) {
        return (isExclude ? sGsonExclude : sGSon).toJson(o);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        try {
            return (T) sGSon.fromJson(json, (Class<Object>) classOfT);
        } catch (Exception unused) {
            Logger.t(TAG).w("not json string: " + json, new Object[0]);
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v5, types: [T, java.lang.Object] */
    public static <T> T fromJson(String json, Class<T> classOfT, boolean isExclude) {
        try {
            json = (T) (isExclude ? sGsonExclude : sGSon).fromJson(json, (Class<Object>) classOfT);
            return json;
        } catch (Exception unused) {
            Logger.t(TAG).w("not json string: " + json, new Object[0]);
            return null;
        }
    }

    public static <T> T fromJson(JsonElement json, Class<T> classOfT) {
        return (T) sGSon.fromJson(json, (Class<Object>) classOfT);
    }

    public static <T> T fromJson(JsonElement json, Class<T> classOfT, boolean isExclude) {
        return (T) (isExclude ? sGsonExclude : sGSon).fromJson(json, (Class<Object>) classOfT);
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        return (T) sGSon.fromJson(json, typeOfT);
    }

    /* loaded from: classes2.dex */
    public static class FloatAdapter extends TypeAdapter<Float> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.gson.TypeAdapter
        public Float read(JsonReader reader) throws IOException {
            float f = 0.0f;
            if (reader.peek() == JsonToken.STRING || reader.peek() == JsonToken.NUMBER) {
                String nextString = reader.nextString();
                try {
                    f = new BigDecimal(nextString).floatValue();
                } catch (Exception unused) {
                    Logger.t(GsonUtil.TAG).w("FloatAdapter json error value:" + nextString, new Object[0]);
                }
            } else {
                reader.skipValue();
            }
            return Float.valueOf(f);
        }

        @Override // com.google.gson.TypeAdapter
        public void write(JsonWriter writer, Float value) throws IOException {
            writer.value(value);
        }
    }

    /* loaded from: classes2.dex */
    public static class DoubleAdapter extends TypeAdapter<Double> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.gson.TypeAdapter
        public Double read(JsonReader reader) throws IOException {
            double d = 0.0d;
            if (reader.peek() == JsonToken.STRING || reader.peek() == JsonToken.NUMBER) {
                String nextString = reader.nextString();
                try {
                    d = new BigDecimal(nextString).doubleValue();
                } catch (Exception unused) {
                    Logger.t(GsonUtil.TAG).w("DoubleAdapter json error value:" + nextString, new Object[0]);
                }
            } else {
                reader.skipValue();
            }
            return Double.valueOf(d);
        }

        @Override // com.google.gson.TypeAdapter
        public void write(JsonWriter writer, Double value) throws IOException {
            writer.value(value);
        }
    }

    /* loaded from: classes2.dex */
    public static class CustomTypeAdapterFactory<T> implements TypeAdapterFactory {
        @Override // com.google.gson.TypeAdapterFactory
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            Class<? super T> rawType = type.getRawType();
            if (rawType == Float.class || rawType == Float.TYPE) {
                return new FloatAdapter();
            }
            if (rawType == Double.class || rawType == Double.TYPE) {
                return new DoubleAdapter();
            }
            return null;
        }
    }

    /* loaded from: classes2.dex */
    public static class NullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {
        @Override // com.google.gson.TypeAdapterFactory
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            if (type.getRawType() != String.class) {
                return null;
            }
            return new StringNullAdapter();
        }
    }

    /* loaded from: classes2.dex */
    public static class MapTypeAdapter extends TypeAdapter<Object> {
        @Override // com.google.gson.TypeAdapter
        public void write(JsonWriter out, Object value) throws IOException {
        }

        @Override // com.google.gson.TypeAdapter
        public Object read(JsonReader in) throws IOException {
            switch (AnonymousClass3.$SwitchMap$com$google$gson$stream$JsonToken[in.peek().ordinal()]) {
                case 1:
                    ArrayList arrayList = new ArrayList();
                    in.beginArray();
                    while (in.hasNext()) {
                        arrayList.add(read(in));
                    }
                    in.endArray();
                    return arrayList;
                case 2:
                    LinkedTreeMap linkedTreeMap = new LinkedTreeMap();
                    in.beginObject();
                    while (in.hasNext()) {
                        linkedTreeMap.put(in.nextName(), read(in));
                    }
                    in.endObject();
                    return linkedTreeMap;
                case 3:
                    return in.nextString();
                case 4:
                    double nextDouble = in.nextDouble();
                    if (nextDouble > 9.223372036854776E18d) {
                        return Double.valueOf(nextDouble);
                    }
                    long j = (long) nextDouble;
                    if (nextDouble == j) {
                        return Long.valueOf(j);
                    }
                    return Double.valueOf(nextDouble);
                case 5:
                    return Boolean.valueOf(in.nextBoolean());
                case 6:
                    in.nextNull();
                    return null;
                default:
                    throw new IllegalStateException();
            }
        }
    }

    /* renamed from: com.xiaopeng.appstore.libcommon.utils.GsonUtil$3  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$google$gson$stream$JsonToken;

        static {
            int[] iArr = new int[JsonToken.values().length];
            $SwitchMap$com$google$gson$stream$JsonToken = iArr;
            try {
                iArr[JsonToken.BEGIN_ARRAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.BEGIN_OBJECT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.STRING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.NUMBER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.BOOLEAN.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.NULL.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    /* loaded from: classes2.dex */
    public static class StringNullAdapter extends TypeAdapter<String> {
        @Override // com.google.gson.TypeAdapter
        public String read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return "";
            }
            String nextString = reader.nextString();
            return "null".equals(nextString) ? "" : nextString;
        }

        @Override // com.google.gson.TypeAdapter
        public void write(JsonWriter writer, String value) throws IOException {
            if (value == null) {
                writer.nullValue();
            } else if ("null".equals(value)) {
                writer.value("");
            } else {
                writer.value(value);
            }
        }
    }
}
