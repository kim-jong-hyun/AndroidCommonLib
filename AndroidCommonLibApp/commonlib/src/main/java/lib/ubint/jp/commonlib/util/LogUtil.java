package lib.ubint.jp.commonlib.util;


import android.content.Context;
import android.util.Log;

/**
 * ログ関係の有用な機能を提供するクラスです。
 */
public class LogUtil {
    /**
     * タグ
     */
    @SuppressWarnings("unused")
    private static final String TAG = LogUtil.class.getSimpleName();

    /**
     * Debugを出力します。
     *
     * @param tag タグ
     * @param msg 出力内容
     */
    public static void d(String tag, String msg) {
//        if (BuildConfig.DEBUG) {
//            Log.d(tag, msg);
//        }
    }

    /**
     * Debugを出力します。
     *
     * @param tag タグ
     * @param msg 出力内容
     * @param tr  Throwable
     */
    public static void d(String tag, String msg, Throwable tr) {
//        if (BuildConfig.DEBUG) {
//            Log.d(tag, msg, tr);
//        }
    }

    /**
     * Debugを出力します。
     *
     * @param tag      タグ
     * @param variable 確認用変数
     * @param msg      出力内容
     */
    public static void d(String tag, String variable, String msg) {
//        if (BuildConfig.DEBUG) {
//            Log.d(tag, variable + ": " + msg);
//        }
    }

    /**
     * Debugを出力します。
     *
     * @param tag      タグ
     * @param variable 確認用変数
     * @param msg      出力内容
     * @param tr       Throwable
     */
    public static void d(String tag, String variable, String msg, Throwable tr) {
//        if (BuildConfig.DEBUG) {
//            Log.d(tag, variable + ": " + msg, tr);
//        }
    }

    /**
     * Errorを出力します。
     *
     * @param tag タグ
     * @param msg 出力内容
     */
    public static void e(String tag, String msg) {
//        if (BuildConfig.DEBUG) {
//            Log.e(tag, msg);
//        }
    }

    /**
     * Errorを出力します。
     *
     * @param tag タグ
     * @param msg 出力内容
     * @param tr  Throwable
     */
    public static void e(String tag, String msg, Throwable tr) {
//        if (BuildConfig.DEBUG) {
//            Log.e(tag, msg, tr);
//        }
    }

    /**
     * Errorを出力します。
     *
     * @param tag      タグ
     * @param variable 確認用変数
     * @param msg      出力内容
     */
    public static void e(String tag, String variable, String msg) {
//        if (BuildConfig.DEBUG) {
//            Log.e(tag, variable + ": " + msg);
//        }
    }

    /**
     * Errorを出力します。
     *
     * @param tag      タグ
     * @param variable 確認用変数
     * @param msg      出力内容
     * @param tr       Throwable
     */
    public static void e(String tag, String variable, String msg, Throwable tr) {
//        if (BuildConfig.DEBUG) {
//            Log.e(tag, variable + ": " + msg, tr);
//        }
    }

    /**
     * ログをSDカードに出力します。
     *
     * @param context    Context
     * @param className  クラス名
     * @param methodName メソッド名
     * @param fileName   ファイル名
     * @param tag        タグ名
     * @return エラーフラグ
     */
    public static boolean write(Context context, String className, String methodName, String fileName, String tag) {
//        if (!BuildConfig.DEBUG) {
//            return false;
//        }
        String log = "";
        log += SystemUtil.getCurrentDateTime("yyyy-MM-dd HH:mm:ss.SSS") + ",";
        log += className + ",";
        log += methodName + ",";
        log += tag + "\n";
        return FileUtil.write(context, fileName, log);
    }
}