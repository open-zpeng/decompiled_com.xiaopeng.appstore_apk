package com.orhanobut.logger;
/* loaded from: classes2.dex */
public class DiskLogAdapter implements LogAdapter {
    private final FormatStrategy formatStrategy;

    @Override // com.orhanobut.logger.LogAdapter
    public boolean isLoggable(int i, String str) {
        return true;
    }

    public DiskLogAdapter() {
        this.formatStrategy = CsvFormatStrategy.newBuilder().build();
    }

    public DiskLogAdapter(FormatStrategy formatStrategy) {
        this.formatStrategy = (FormatStrategy) Utils.checkNotNull(formatStrategy);
    }

    @Override // com.orhanobut.logger.LogAdapter
    public void log(int i, String str, String str2) {
        this.formatStrategy.log(i, str, str2);
    }
}
