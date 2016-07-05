package lib.ubint.jp.commonlib.util;

import java.util.regex.Pattern;

import android.util.Patterns;

/**
 * バリデート関係の有用な機能を提供するクラスです。
 */
public class ValidateUtil {
    /**
     * タグ
     */
    @SuppressWarnings("unused")
    private static final String TAG = ValidateUtil.class.getSimpleName();

    /**
     * 半角なのかを確認できます。半角の場合、trueを返します。
     *
     * @param text 文字列
     * @return 判断フラグ
     */
    public static boolean isHankaku(String text) {
        boolean isHankaku = false;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if ((c <= '\u007e') || // 英数字
                    (c == '\u00a5') || // \記号
                    (c == '\u203e') || // ~記号
                    (c >= '\uff61' && c <= '\uff9f') // 半角カナ
                    ) {
                isHankaku = true;
            } else {
                isHankaku = false;
            }
        }
        return isHankaku;
    }

    /**
     * Eメール形式なのかを確認できます。Eメール形式の場合、trueを返します。
     *
     * @param email 対象
     * @return 判断フラグ
     */
    public static boolean isEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * 電話番号形式なのかを確認できます。電話番号形式の場合、trueを返します。
     *
     * @param phone 対象
     * @return 判断フラグ
     */
    public static boolean isPhone(String phone) {
        return Patterns.PHONE.matcher(phone).matches();
    }

    /**
     * 数値なのかを確認できます。数値の場合、trueを返します。
     *
     * @param text 対象
     * @return 判断フラグ
     */
    public static boolean isNumber(String text) {
        boolean isNumber = false;
        if (Pattern.compile("^\\d$").matcher(text).matches()) {
            isNumber = true;
        }
        return isNumber;
    }
}