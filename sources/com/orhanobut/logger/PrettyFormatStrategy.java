package com.orhanobut.logger;
/* loaded from: classes2.dex */
public class PrettyFormatStrategy implements FormatStrategy {
    private static final String BOTTOM_BORDER = "└────────────────────────────────────────────────────────────────────────────────────────────────────────────────";
    private static final char BOTTOM_LEFT_CORNER = 9492;
    private static final int CHUNK_SIZE = 4000;
    private static final String DOUBLE_DIVIDER = "────────────────────────────────────────────────────────";
    private static final char HORIZONTAL_LINE = 9474;
    private static final String MIDDLE_BORDER = "├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄";
    private static final char MIDDLE_CORNER = 9500;
    private static final int MIN_STACK_OFFSET = 5;
    private static final String SINGLE_DIVIDER = "┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄";
    private static final String TOP_BORDER = "┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────";
    private static final char TOP_LEFT_CORNER = 9484;
    private final LogStrategy logStrategy;
    private final int methodCount;
    private final int methodOffset;
    private final boolean showThreadInfo;
    private final String tag;

    private PrettyFormatStrategy(Builder builder) {
        Utils.checkNotNull(builder);
        this.methodCount = builder.methodCount;
        this.methodOffset = builder.methodOffset;
        this.showThreadInfo = builder.showThreadInfo;
        this.logStrategy = builder.logStrategy;
        this.tag = builder.tag;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override // com.orhanobut.logger.FormatStrategy
    public void log(int i, String str, String str2) {
        Utils.checkNotNull(str2);
        String formatTag = formatTag(str);
        logTopBorder(i, formatTag);
        logHeaderContent(i, formatTag, this.methodCount);
        byte[] bytes = str2.getBytes();
        int length = bytes.length;
        if (length <= 4000) {
            if (this.methodCount > 0) {
                logDivider(i, formatTag);
            }
            logContent(i, formatTag, str2);
            logBottomBorder(i, formatTag);
            return;
        }
        if (this.methodCount > 0) {
            logDivider(i, formatTag);
        }
        for (int i2 = 0; i2 < length; i2 += 4000) {
            logContent(i, formatTag, new String(bytes, i2, Math.min(length - i2, 4000)));
        }
        logBottomBorder(i, formatTag);
    }

    private void logTopBorder(int i, String str) {
        logChunk(i, str, TOP_BORDER);
    }

    private void logHeaderContent(int i, String str, int i2) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (this.showThreadInfo) {
            logChunk(i, str, "│ Thread: " + Thread.currentThread().getName());
            logDivider(i, str);
        }
        int stackOffset = getStackOffset(stackTrace) + this.methodOffset;
        if (i2 + stackOffset > stackTrace.length) {
            i2 = (stackTrace.length - stackOffset) - 1;
        }
        String str2 = "";
        while (i2 > 0) {
            int i3 = i2 + stackOffset;
            if (i3 < stackTrace.length) {
                StringBuilder sb = new StringBuilder();
                sb.append(HORIZONTAL_LINE).append(' ').append(str2).append(getSimpleClassName(stackTrace[i3].getClassName())).append(".").append(stackTrace[i3].getMethodName()).append(" ").append(" (").append(stackTrace[i3].getFileName()).append(":").append(stackTrace[i3].getLineNumber()).append(")");
                str2 = str2 + "   ";
                logChunk(i, str, sb.toString());
            }
            i2--;
        }
    }

    private void logBottomBorder(int i, String str) {
        logChunk(i, str, BOTTOM_BORDER);
    }

    private void logDivider(int i, String str) {
        logChunk(i, str, MIDDLE_BORDER);
    }

    private void logContent(int i, String str, String str2) {
        Utils.checkNotNull(str2);
        String[] split = str2.split(System.getProperty("line.separator"));
        int length = split.length;
        for (int i2 = 0; i2 < length; i2++) {
            logChunk(i, str, "│ " + split[i2]);
        }
    }

    private void logChunk(int i, String str, String str2) {
        Utils.checkNotNull(str2);
        this.logStrategy.log(i, str, str2);
    }

    private String getSimpleClassName(String str) {
        Utils.checkNotNull(str);
        return str.substring(str.lastIndexOf(".") + 1);
    }

    private int getStackOffset(StackTraceElement[] stackTraceElementArr) {
        Utils.checkNotNull(stackTraceElementArr);
        for (int i = 5; i < stackTraceElementArr.length; i++) {
            String className = stackTraceElementArr[i].getClassName();
            if (!className.equals(LoggerPrinter.class.getName()) && !className.equals(Logger.class.getName())) {
                return i - 1;
            }
        }
        return -1;
    }

    private String formatTag(String str) {
        if (!Utils.isEmpty(str) && !Utils.equals(this.tag, str)) {
            return this.tag + "-" + str;
        }
        return this.tag;
    }

    /* loaded from: classes2.dex */
    public static class Builder {
        LogStrategy logStrategy;
        int methodCount;
        int methodOffset;
        boolean showThreadInfo;
        String tag;

        private Builder() {
            this.methodCount = 2;
            this.methodOffset = 0;
            this.showThreadInfo = true;
            this.tag = "PRETTY_LOGGER";
        }

        public Builder methodCount(int i) {
            this.methodCount = i;
            return this;
        }

        public Builder methodOffset(int i) {
            this.methodOffset = i;
            return this;
        }

        public Builder showThreadInfo(boolean z) {
            this.showThreadInfo = z;
            return this;
        }

        public Builder logStrategy(LogStrategy logStrategy) {
            this.logStrategy = logStrategy;
            return this;
        }

        public Builder tag(String str) {
            this.tag = str;
            return this;
        }

        public PrettyFormatStrategy build() {
            if (this.logStrategy == null) {
                this.logStrategy = new LogcatLogStrategy();
            }
            return new PrettyFormatStrategy(this);
        }
    }
}
