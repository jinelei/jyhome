package cn.jinelei.jyhome.base;

import android.app.Application;

import com.facebook.stetho.Stetho;

import java.text.SimpleDateFormat;

/**
 * @author jinelei
 */
public class BaseApplication extends Application {
    private static final String TAG = "BaseApplication";
    public static final SimpleDateFormat sdfyyyyMMdd_hhmmss = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        Thread.setDefaultUncaughtExceptionHandler(JyCrashHandler.Singleton.INSTANCE.getInstance());
    }

    public enum Singleton implements JySingleton<BaseApplication> {
        INSTANCE {
            BaseApplication instance = new BaseApplication();

            @Override
            public BaseApplication getInstance() {
                return instance;
            }
        }
    }

}
