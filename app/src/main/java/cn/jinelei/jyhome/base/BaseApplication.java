package cn.jinelei.jyhome.base;

import android.app.Application;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;

import com.facebook.stetho.Stetho;

import java.text.SimpleDateFormat;
import java.util.Optional;
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
    public Optional<ClipboardManager> optClipboardManager = Optional.empty();


    private ClipboardManager.OnPrimaryClipChangedListener mPrimaryClipChangedListener = () -> {
        String clip = optClipboardManager
                .flatMap(clipboardManager -> Optional.ofNullable(clipboardManager.getPrimaryClip()))
                .filter(c -> c.getItemCount() > 0)
                .flatMap(c -> Optional.ofNullable(c.getItemAt(0)))
                .flatMap(i -> Optional.ofNullable(i.getText()))
                .map(CharSequence::toString)
                .orElse("nothing");
        switch (clip) {
            case "nothing":
                Log.d(TAG, "mPrimaryClipChangedListener: nothing");
                break;
            default:
                Log.d(TAG, "mPrimaryClipChangedListener: " + clip);
                break;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        Stetho.initializeWithDefaults(this);
        Thread.setDefaultUncaughtExceptionHandler(JyCrashHandler.Singleton.INSTANCE.getInstance());
        optClipboardManager = Optional.ofNullable((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE));
        optClipboardManager.ifPresent(clipboardManager -> clipboardManager.addPrimaryClipChangedListener(mPrimaryClipChangedListener));
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
