package cn.jinelei.jyhome.base;

import android.app.Application;
import android.util.Log;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;

import com.facebook.stetho.Stetho;

/**
 * @author jinelei
 */
public class BaseApplication extends Application {
    private static final String TAG = BaseApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        Thread.setDefaultUncaughtExceptionHandler(JyCrashHandler.Singleton.INSTANCE.getInstance());
    }

    public enum Singleton implements JySingleton<BaseApplication> {
        INSTANCE {
            public BaseApplication instance = new BaseApplication();

            @Override
            public BaseApplication getInstance() {
                return instance;
            }
        }
    }

}
