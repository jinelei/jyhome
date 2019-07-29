package cn.jinelei.jyhome.base;

import android.app.Application;
import android.util.Log;

import com.facebook.stetho.Stetho;

import java.text.SimpleDateFormat;
import java.util.WeakHashMap;

import cn.jinelei.jyhome.page.base.BaseActivity;
import cn.jinelei.jyhome.page.base.BaseFragment;

/**
 * @author jinelei
 */
public class BaseApplication extends Application {
    private static final String TAG = "BaseApplication";
    public static final SimpleDateFormat sdf_yyyy_MM_dd_hh_mm_ss = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public static final SimpleDateFormat sdf_yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat sdf_hh_mm_ss = new SimpleDateFormat("hh:mm:ss");

    public final WeakHashMap<BaseFragment, Object> mFragmentSilenceLoadingMap = new WeakHashMap<>();
    public final WeakHashMap<BaseActivity, Object> mActivitySilenceLoadingMap = new WeakHashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
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
