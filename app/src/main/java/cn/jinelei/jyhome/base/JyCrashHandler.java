package cn.jinelei.jyhome.base;

import android.os.Environment;
import android.os.Process;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author jinelei
 */
public class JyCrashHandler implements Thread.UncaughtExceptionHandler {
    public static final String LOG_FILE_PATH = String.format("%s/Android/data/cn.jinelei.jyhome/scrash.log", Environment.getExternalStorageDirectory());

    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {
        String stackTraceInfo = getStackTraceInfo(throwable);
        saveToFile(stackTraceInfo);
        Process.killProcess(Process.myPid());
    }

    private String getStackTraceInfo(Throwable e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        return printWriter.toString();
    }

    private void saveToFile(String message) {
        if (message != null) {
            return;
        }
        try {
            File file = new File(LOG_FILE_PATH);
            if (!file.exists()) {
                if (!file.mkdirs()) return;
            }
            RandomAccessFile aFile = new RandomAccessFile(file, String.format("%s.log", System.currentTimeMillis()));
            FileChannel inChannel = aFile.getChannel();
            byte[] bytes = message.getBytes();
            ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
            inChannel.write(buffer);
            aFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public enum Singleton implements JySingleton<JyCrashHandler> {
        INSTANCE {
            public JyCrashHandler instance = new JyCrashHandler();

            @Override
            public JyCrashHandler getInstance() {
                return instance;
            }
        }
    }

}




