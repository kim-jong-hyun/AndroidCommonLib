package lib.ubint.jp.commonlib.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * ネットワーク関係の有用な機能を提供するクラスです。
 */
public class NetworkUtil {
    /**
     * ネットワークが接続してあるかないかを確認できます。接続してある場合、trueを返します。
     *
     * @param context Context
     * @return 接続状態フラグ
     */
    public static boolean isConnected(Context context) {
        boolean isConnected = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null) {
            if (info.isConnected()) {
                isConnected = true;
            }
        }
        return isConnected;
    }
}
