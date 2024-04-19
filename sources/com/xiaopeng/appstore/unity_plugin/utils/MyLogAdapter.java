package com.xiaopeng.appstore.unity_plugin.utils;

import android.text.TextUtils;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogAdapter;
import com.orhanobut.logger.LogStrategy;
import com.orhanobut.logger.LogcatLogStrategy;
import com.orhanobut.logger.Logger;
/* loaded from: classes2.dex */
public class MyLogAdapter implements LogAdapter {
    private final SimpleLogFormatStrategy formatStrategy;
    private final String logTag;

    public MyLogAdapter(SimpleLogFormatStrategy formatStrategy, String filterLogTag) {
        this.formatStrategy = formatStrategy;
        this.logTag = filterLogTag;
    }

    @Override // com.orhanobut.logger.LogAdapter
    public boolean isLoggable(int priority, String tag) {
        return this.logTag.equals(this.formatStrategy.getTag());
    }

    @Override // com.orhanobut.logger.LogAdapter
    public void log(int priority, String tag, String message) {
        this.formatStrategy.log(priority, tag, message);
    }

    /* loaded from: classes2.dex */
    public static class SimpleLogFormatStrategy implements FormatStrategy {
        private static final int CHUNK_SIZE = 4000;
        private static final int MIN_STACK_OFFSET = 5;
        private final LogStrategy logStrategy;
        private final int methodCount;
        private final int methodOffset;
        private final boolean showMethodInfo;
        private final boolean showThreadInfo;
        private final String tag;

        private SimpleLogFormatStrategy(Builder builder) {
            this.methodCount = builder.methodCount;
            this.methodOffset = builder.methodOffset;
            this.showThreadInfo = builder.showThreadInfo;
            this.showMethodInfo = builder.showMethodInfo;
            this.logStrategy = builder.logStrategy;
            this.tag = builder.tag;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public String getTag() {
            return this.tag;
        }

        @Override // com.orhanobut.logger.FormatStrategy
        public void log(int priority, String onceOnlyTag, String message) {
            String formatTag = formatTag(onceOnlyTag);
            if (this.showThreadInfo) {
                message = message + " ->>[Thread: " + Thread.currentThread().getName() + "]";
            }
            if (this.showMethodInfo && this.methodCount > 0) {
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                int stackOffset = getStackOffset(stackTrace) + this.methodOffset;
                int i = this.methodCount;
                if (i + stackOffset > stackTrace.length) {
                    i = (stackTrace.length - stackOffset) - 1;
                }
                StringBuilder sb = new StringBuilder();
                sb.append(" ->>[Method: ");
                for (int i2 = 1; i2 <= i; i2++) {
                    int i3 = i2 + stackOffset;
                    if (i3 < stackTrace.length) {
                        sb.append("->").append(stackTrace[i3].getMethodName()).append("(").append(stackTrace[i3].getFileName()).append(":").append(stackTrace[i3].getLineNumber()).append(")");
                    }
                }
                message = message + " " + ((Object) sb) + "]";
            }
            byte[] bytes = message.getBytes();
            int length = bytes.length;
            if (length <= 4000) {
                logContent(priority, formatTag, message);
                return;
            }
            for (int i4 = 0; i4 < length; i4 += 4000) {
                logContent(priority, formatTag, new String(bytes, i4, Math.min(length - i4, 4000)));
            }
        }

        private void logContent(int logType, String tag, String chunk) {
            logChunk(logType, tag, chunk);
        }

        private void logChunk(int priority, String tag, String chunk) {
            this.logStrategy.log(priority, tag, chunk);
        }

        private int getStackOffset(StackTraceElement[] trace) {
            for (int i = 5; i < trace.length; i++) {
                String className = trace[i].getClassName();
                if (!className.equals("com.orhanobut.logger.LoggerPrinter") && !className.equals(Logger.class.getName())) {
                    return i - 1;
                }
            }
            return -1;
        }

        private String formatTag(String tag) {
            if (!TextUtils.isEmpty(tag) && !TextUtils.equals(this.tag, tag)) {
                return this.tag + "-" + tag;
            }
            return this.tag;
        }

        /* loaded from: classes2.dex */
        public static class Builder {
            LogStrategy logStrategy;
            int methodCount;
            int methodOffset;
            boolean showMethodInfo;
            boolean showThreadInfo;
            String tag;

            private Builder() {
                this.methodCount = 1;
                this.methodOffset = 0;
                this.showThreadInfo = false;
                this.showMethodInfo = false;
                this.tag = "SIMPLE_LOGGER";
            }

            public Builder methodCount(int val) {
                this.methodCount = val;
                return this;
            }

            public Builder methodOffset(int val) {
                this.methodOffset = val;
                return this;
            }

            public Builder showThreadInfo(boolean val) {
                this.showThreadInfo = val;
                return this;
            }

            public Builder showMethodInfo(boolean showMethodInfo) {
                this.showMethodInfo = showMethodInfo;
                return this;
            }

            public Builder logStrategy(LogStrategy val) {
                this.logStrategy = val;
                return this;
            }

            public Builder tag(String tag) {
                this.tag = tag;
                return this;
            }

            public SimpleLogFormatStrategy build() {
                if (this.logStrategy == null) {
                    this.logStrategy = new LogcatLogStrategy();
                }
                return new SimpleLogFormatStrategy(this);
            }
        }
    }
}
