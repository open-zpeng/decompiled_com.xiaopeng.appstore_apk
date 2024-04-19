package com.orhanobut.logger;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/* loaded from: classes2.dex */
public class DiskLogStrategy implements LogStrategy {
    private final Handler handler;

    public DiskLogStrategy(Handler handler) {
        this.handler = (Handler) Utils.checkNotNull(handler);
    }

    @Override // com.orhanobut.logger.LogStrategy
    public void log(int i, String str, String str2) {
        Utils.checkNotNull(str2);
        Handler handler = this.handler;
        handler.sendMessage(handler.obtainMessage(i, str2));
    }

    /* loaded from: classes2.dex */
    static class WriteHandler extends Handler {
        private final String folder;
        private final int maxFileSize;

        /* JADX INFO: Access modifiers changed from: package-private */
        public WriteHandler(Looper looper, String str, int i) {
            super((Looper) Utils.checkNotNull(looper));
            this.folder = (String) Utils.checkNotNull(str);
            this.maxFileSize = i;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            String str = (String) message.obj;
            FileWriter fileWriter = null;
            try {
                FileWriter fileWriter2 = new FileWriter(getLogFile(this.folder, "logs"), true);
                try {
                    writeLog(fileWriter2, str);
                    fileWriter2.flush();
                    fileWriter2.close();
                } catch (IOException unused) {
                    fileWriter = fileWriter2;
                    if (fileWriter != null) {
                        try {
                            fileWriter.flush();
                            fileWriter.close();
                        } catch (IOException unused2) {
                        }
                    }
                }
            } catch (IOException unused3) {
            }
        }

        private void writeLog(FileWriter fileWriter, String str) throws IOException {
            Utils.checkNotNull(fileWriter);
            Utils.checkNotNull(str);
            fileWriter.append((CharSequence) str);
        }

        private File getLogFile(String str, String str2) {
            File file;
            Utils.checkNotNull(str);
            Utils.checkNotNull(str2);
            File file2 = new File(str);
            if (!file2.exists()) {
                file2.mkdirs();
            }
            File file3 = null;
            File file4 = new File(file2, String.format("%s_%s.csv", str2, 0));
            int i = 0;
            while (true) {
                File file5 = file4;
                file = file3;
                file3 = file5;
                if (!file3.exists()) {
                    break;
                }
                i++;
                file4 = new File(file2, String.format("%s_%s.csv", str2, Integer.valueOf(i)));
            }
            return (file == null || file.length() >= ((long) this.maxFileSize)) ? file3 : file;
        }
    }
}
