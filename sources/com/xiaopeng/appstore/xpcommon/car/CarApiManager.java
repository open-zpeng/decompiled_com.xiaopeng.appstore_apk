package com.xiaopeng.appstore.xpcommon.car;

import android.car.Car;
import android.car.CarNotConnectedException;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.esp.CarEspManager;
import android.car.hardware.mcu.CarMcuManager;
import android.car.hardware.vcu.CarVcuManager;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.libcommon.utils.AppExecutors;
import com.xiaopeng.appstore.libcommon.utils.Utils;
import com.xiaopeng.appstore.xpcommon.CarUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
/* loaded from: classes2.dex */
public class CarApiManager {
    private static final String TAG = "CarApiManager";
    private static CarApiManager sInstance;
    private Car mCarApiClient;
    private CarEspManager.CarEspEventCallback mCarEspEventCallback;
    private CarEspManager mCarEspManager;
    private final CarMcuManager.CarMcuEventCallback mCarMcuEventCallback;
    private CarMcuManager mCarMcuManager;
    private final ConcurrentHashMap<Integer, CarPropertyValue<?>> mCarPropertyMap = new ConcurrentHashMap<>();
    private CarVcuManager.CarVcuEventCallback mCarVcuEventCallback;
    private CarVcuManager mCarVcuManager;
    private final List<IEspChangeListener> mEspListenerList;
    protected final List<Integer> mEspPropertyIds;
    private boolean mIsConnected;
    private final List<IMcuChangeListener> mMcuListenerList;
    protected final List<Integer> mMcuPropertyIds;
    private final List<IVcuChangeListener> mVcuListenerList;
    protected final List<Integer> mVcuPropertyIds;

    public static CarApiManager getInstance() {
        if (sInstance == null) {
            synchronized (CarApiManager.class) {
                if (sInstance == null) {
                    sInstance = new CarApiManager();
                }
            }
        }
        return sInstance;
    }

    private CarApiManager() {
        ArrayList arrayList = new ArrayList();
        this.mVcuPropertyIds = arrayList;
        ArrayList arrayList2 = new ArrayList();
        this.mEspPropertyIds = arrayList2;
        ArrayList arrayList3 = new ArrayList();
        this.mMcuPropertyIds = arrayList3;
        this.mIsConnected = false;
        this.mVcuListenerList = new ArrayList();
        this.mEspListenerList = new ArrayList();
        this.mMcuListenerList = new CopyOnWriteArrayList();
        this.mCarMcuEventCallback = new CarMcuManager.CarMcuEventCallback() { // from class: com.xiaopeng.appstore.xpcommon.car.CarApiManager.1
            public void onErrorEvent(int i, int i1) {
            }

            public void onChangeEvent(CarPropertyValue carPropertyValue) {
                Logger.t(CarApiManager.TAG).d("onChangeEvent id=" + carPropertyValue.getPropertyId());
                if (carPropertyValue.getPropertyId() != 557847561) {
                    return;
                }
                CarApiManager carApiManager = CarApiManager.this;
                carApiManager.handleIgStatusChanged(((Integer) carApiManager.getValue(carPropertyValue)).intValue());
            }
        };
        arrayList.add(559944229);
        arrayList2.add(1);
        arrayList3.add(Integer.valueOf((int) CarUtils.ID_MCU_IG_STATUS));
    }

    public void connect() {
        Car createCar = Car.createCar(Utils.getApp(), new ServiceConnection() { // from class: com.xiaopeng.appstore.xpcommon.car.CarApiManager.2
            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName name) {
            }

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName name, IBinder service) {
                Logger.t(CarApiManager.TAG).d("onCarServiceConnected");
                CarApiManager.this.mIsConnected = true;
                CarApiManager.this.initCarMcuManager();
            }
        });
        this.mCarApiClient = createCar;
        createCar.connect();
    }

    public boolean isConnected() {
        return this.mIsConnected;
    }

    private void initCarVcuManager() {
        try {
            this.mCarVcuManager = (CarVcuManager) this.mCarApiClient.getCarManager("xp_vcu");
            Log.d(TAG, "carservice init vcu manager");
        } catch (Exception e) {
            e.printStackTrace();
        } catch (CarNotConnectedException unused) {
            Log.e(TAG, "Car not connected");
        }
        this.mCarVcuEventCallback = new CarVcuManager.CarVcuEventCallback() { // from class: com.xiaopeng.appstore.xpcommon.car.CarApiManager.3
            public void onChangeEvent(CarPropertyValue carPropertyValue) {
                if (carPropertyValue != null) {
                    CarApiManager.this.mCarPropertyMap.put(Integer.valueOf(carPropertyValue.getPropertyId()), carPropertyValue);
                    if (carPropertyValue.getPropertyId() != 559944229) {
                        return;
                    }
                    CarApiManager carApiManager = CarApiManager.this;
                    carApiManager.handleRawSpdUpdate(((Float) carApiManager.getValue(carPropertyValue)).floatValue());
                }
            }

            public void onErrorEvent(int i, int i1) {
                Log.d(CarApiManager.TAG, "carservice mCarVcuEventCallback onErrorEvent:" + i + " " + i1);
            }
        };
        AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.xpcommon.car.-$$Lambda$CarApiManager$18Agwn6nuaLY2fmgjZrf-2Kwz0A
            @Override // java.lang.Runnable
            public final void run() {
                CarApiManager.this.lambda$initCarVcuManager$0$CarApiManager();
            }
        });
    }

    public /* synthetic */ void lambda$initCarVcuManager$0$CarApiManager() {
        CarVcuManager.CarVcuEventCallback carVcuEventCallback;
        try {
            CarVcuManager carVcuManager = this.mCarVcuManager;
            if (carVcuManager == null || (carVcuEventCallback = this.mCarVcuEventCallback) == null) {
                return;
            }
            carVcuManager.registerPropCallback(this.mVcuPropertyIds, carVcuEventCallback);
        } catch (CarNotConnectedException e) {
            Log.e(TAG, "Car not connected");
            e.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void initEspCarManager() {
        try {
            this.mCarEspManager = (CarEspManager) this.mCarApiClient.getCarManager("xp_esp");
            Log.d(TAG, "carservice init esp manager");
        } catch (Exception e) {
            e.printStackTrace();
        } catch (CarNotConnectedException e2) {
            Log.e(TAG, "Car not connected");
            e2.printStackTrace();
        }
        this.mCarEspEventCallback = new CarEspManager.CarEspEventCallback() { // from class: com.xiaopeng.appstore.xpcommon.car.CarApiManager.4
            public void onChangeEvent(CarPropertyValue carPropertyValue) {
                if (carPropertyValue != null) {
                    CarApiManager.this.mCarPropertyMap.put(Integer.valueOf(carPropertyValue.getPropertyId()), carPropertyValue);
                    if (carPropertyValue.getPropertyId() != 1) {
                        return;
                    }
                    CarApiManager carApiManager = CarApiManager.this;
                    carApiManager.handleSpdUpdate(((Float) carApiManager.getValue(carPropertyValue)).floatValue());
                }
            }

            public void onErrorEvent(int i, int i1) {
                Log.d(CarApiManager.TAG, "carservice mCarEspEventCallback onErrorEvent:" + i + " " + i1);
            }
        };
        AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.xpcommon.car.-$$Lambda$CarApiManager$Z6QHb6tozCLEeLTTZ4dtV2iJRQc
            @Override // java.lang.Runnable
            public final void run() {
                CarApiManager.this.lambda$initEspCarManager$1$CarApiManager();
            }
        });
    }

    public /* synthetic */ void lambda$initEspCarManager$1$CarApiManager() {
        CarEspManager.CarEspEventCallback carEspEventCallback;
        try {
            CarEspManager carEspManager = this.mCarEspManager;
            if (carEspManager == null || (carEspEventCallback = this.mCarEspEventCallback) == null) {
                return;
            }
            carEspManager.registerPropCallback(this.mEspPropertyIds, carEspEventCallback);
        } catch (CarNotConnectedException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initCarMcuManager() {
        AppExecutors.get().backgroundThread().execute(new Runnable() { // from class: com.xiaopeng.appstore.xpcommon.car.-$$Lambda$CarApiManager$fK54huk3HwNTmyYLFdG2ux0wLVE
            @Override // java.lang.Runnable
            public final void run() {
                CarApiManager.this.lambda$initCarMcuManager$2$CarApiManager();
            }
        });
    }

    public /* synthetic */ void lambda$initCarMcuManager$2$CarApiManager() {
        try {
            this.mCarMcuManager = (CarMcuManager) this.mCarApiClient.getCarManager("xp_mcu");
            Logger.t(TAG).d("carservice init mcu manager , mCarMcuManager = " + this.mCarMcuManager);
        } catch (CarNotConnectedException e) {
            Logger.t(TAG).e("CarMcuManagerCar not connected", e);
        } catch (Exception e2) {
            Logger.t(TAG).e("CarMcuManager get exception", e2);
        }
        try {
            CarMcuManager carMcuManager = this.mCarMcuManager;
            if (carMcuManager != null) {
                carMcuManager.registerPropCallback(this.mMcuPropertyIds, this.mCarMcuEventCallback);
            }
        } catch (Throwable th) {
            Logger.t(TAG).w("McuManager registerPropCallback exception:" + th.getMessage(), new Object[0]);
        }
    }

    private float getFloatProperty(int propertyId) throws Exception {
        return ((Float) getValue(getCarProperty(propertyId))).floatValue();
    }

    private CarPropertyValue<?> getCarProperty(int propertyId) throws Exception {
        CarPropertyValue<?> carPropertyValue = this.mCarPropertyMap.get(Integer.valueOf(propertyId));
        if (carPropertyValue != null) {
            return carPropertyValue;
        }
        throw new Exception("Car property not found");
    }

    protected final <E> E getValue(CarPropertyValue<?> value) {
        return (E) value.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRawSpdUpdate(float value) {
        for (IVcuChangeListener iVcuChangeListener : this.mVcuListenerList) {
            iVcuChangeListener.onCarRawSpdChanged(value);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSpdUpdate(float value) {
        for (IEspChangeListener iEspChangeListener : this.mEspListenerList) {
            iEspChangeListener.onCarSpdChanged(value);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleIgStatusChanged(int state) {
        if (state != 0) {
            return;
        }
        for (IMcuChangeListener iMcuChangeListener : this.mMcuListenerList) {
            iMcuChangeListener.onIgOff();
        }
    }

    public void releaseCar() {
        releaseEsp();
        releaseVcu();
        Car car = this.mCarApiClient;
        if (car != null) {
            car.disconnect();
        }
        this.mIsConnected = false;
    }

    private void releaseVcu() {
        CarVcuManager carVcuManager = this.mCarVcuManager;
        if (carVcuManager != null) {
            try {
                CarVcuManager.CarVcuEventCallback carVcuEventCallback = this.mCarVcuEventCallback;
                if (carVcuEventCallback != null) {
                    carVcuManager.unregisterCallback(carVcuEventCallback);
                }
            } catch (CarNotConnectedException e) {
                e.printStackTrace();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    private void releaseEsp() {
        CarEspManager carEspManager = this.mCarEspManager;
        if (carEspManager != null) {
            try {
                CarEspManager.CarEspEventCallback carEspEventCallback = this.mCarEspEventCallback;
                if (carEspEventCallback != null) {
                    carEspManager.unregisterCallback(carEspEventCallback);
                }
            } catch (CarNotConnectedException e) {
                e.printStackTrace();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    public void addVcuChangeListener(IVcuChangeListener listener) {
        this.mVcuListenerList.add(listener);
    }

    public void removeVcuChangeListener(IVcuChangeListener listener) {
        this.mVcuListenerList.remove(listener);
    }

    public void addEspChangeListener(IEspChangeListener listener) {
        this.mEspListenerList.add(listener);
    }

    public void removeEspChangeListener(IEspChangeListener listener) {
        this.mEspListenerList.remove(listener);
    }

    public void addMcuChangeListener(IMcuChangeListener listener) {
        this.mMcuListenerList.add(listener);
    }

    public void removeMcuChangeListener(IMcuChangeListener listener) {
        this.mMcuListenerList.remove(listener);
    }

    protected void handleException(Exception e) {
        if (e instanceof IllegalArgumentException) {
            Log.e(TAG, "IllegalArgumentException:" + e);
        } else if (e instanceof CarNotConnectedException) {
            Log.e(TAG, "CarNotConnectedException:" + e);
        } else {
            Log.e(TAG, e.toString());
        }
    }

    public float getCarSpeed() {
        try {
            try {
                return getFloatProperty(559948801);
            } catch (Exception unused) {
                return 0.0f;
            }
        } catch (Exception unused2) {
            return this.mCarEspManager.getCarSpeed();
        }
    }

    public float getCarRawSpeed() {
        try {
            try {
                return getFloatProperty(559944229);
            } catch (Exception unused) {
                return 0.0f;
            }
        } catch (Exception unused2) {
            return this.mCarVcuManager.getRawCarSpeed();
        }
    }
}
