package com.xiaopeng.appstore.bizcommon.http;

import androidx.lifecycle.LiveData;
import com.xiaopeng.appstore.bizcommon.entities.common.ApiResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
/* loaded from: classes2.dex */
public class LiveDataCallAdapterFactory extends CallAdapter.Factory {
    @Override // retrofit2.CallAdapter.Factory
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (getRawType(returnType) != LiveData.class) {
            return null;
        }
        Type parameterUpperBound = getParameterUpperBound(0, (ParameterizedType) returnType);
        if (getRawType(parameterUpperBound) != ApiResponse.class) {
            throw new IllegalArgumentException("type must be a resource");
        }
        if (!(parameterUpperBound instanceof ParameterizedType)) {
            throw new IllegalArgumentException("resource must be parameterized");
        }
        return new LiveDataCallAdapter(getParameterUpperBound(0, (ParameterizedType) parameterUpperBound));
    }
}
