package lib.ubint.jp.commonlib.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.location.LocationManager;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.view.inputmethod.InputMethodManager;

/**
 * システム関係の有用な機能を提供するクラスです。
 */
public class SystemUtil {
    /**
     * タグ
     */
    @SuppressWarnings("unused")
    private static final String TAG = SystemUtil.class.getSimpleName();

    /**
     * 現在のシステム日付時間を取得します。
     *
     * @param pattern パタン
     * @return 現在のシステム日付時間
     */
    public static String getCurrentDateTime(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(Calendar.getInstance().getTime());
    }

    /**
     * デバイスIDを取得します。
     *
     * @param context Context
     * @return デバイスID
     */
    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * キーボードを非表示にします。
     *
     * @param context     Context
     * @param windowToken IBinder
     * @param flags       フラグ
     */
    public static void hideKeyboard(Context context, IBinder windowToken, int flags) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(windowToken, flags);
    }

    /**
     * 位置情報サービスがOnの状態なのかを確認できます。Onの場合、trueを返します。
     *
     * @param context Context
     * @return 位置情報サービス状態フラグ
     */
    public static boolean isLocationServiceEnabled(Context context) {
        boolean isEnabled = false;
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            isEnabled = true;
        }
        return isEnabled;
    }

    /**
     * 現在表示しているActivity名を取得します。(例: jp.co.cte.kyuzin.activity.TopActivity)
     *
     * @param context Context
     * @return Activity名
     */
    public static String getCurrentActivityName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Service.ACTIVITY_SERVICE);
        String activityName = am.getRunningTasks(3).get(0).topActivity.getClassName();
        return activityName;
    }
}