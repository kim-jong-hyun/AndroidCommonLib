package lib.ubint.jp.commonlib.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.util.Linkify;
import android.widget.TextView;

/**
 * フォーマット関係の有用な機能を提供するクラスです。
 */
public class FormatUtil {
    /**
     * タグ
     */
    private static final String TAG = FormatUtil.class.getSimpleName();

    /**
     * 文字列の最後を省略記号でフォーマットを変更して返します。
     *
     * @param text   文字列
     * @param length 省略記号にする文字数
     * @return 省略記号に変更した文字列
     */
    public static String ellipsize(String text, int length) {
        String string = "";
        if (text.length() >= length) {
            string += text.substring(0, length) + "...";
        } else {
            string = text;
        }
        LogUtil.d(TAG, string);
        return string;
    }

    /**
     * 文字列にURLリンクを付けます。(『地図を見る』リンクで利用)
     *
     * @param text    TextView
     * @param linkStr リンク文字列
     * @param linkUrl リンクURL
     */
    public static void addLink(TextView text, String linkStr, final String linkUrl) {
        Pattern pattern = Pattern.compile(linkStr);
        Linkify.TransformFilter filter = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return linkUrl;
            }
        };
        Linkify.addLinks(text, pattern, linkUrl, null, filter);
    }

    /**
     * 文字列にURLリンクを付けます。(『PCサイト』リンクで利用)
     *
     * @param text TextView
     */
    public static void addLink(TextView text) {
        Pattern pattern = Pattern.compile("(http://|https://|www){1}[\\w\\.\\-/:\\#\\?\\=\\&\\;\\%\\~\\+]+", Pattern.CASE_INSENSITIVE);
        Linkify.TransformFilter filter = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return url;
            }
        };
        Linkify.addLinks(text, pattern, "http://", null, filter);
    }

    /**
     * 日付のフォーマットを変更します。(例:2013/12/23 ⇒ 2013-12-23 12:10:10)
     *
     * @param date    日付文字列
     * @param pattern パタン
     * @return フォーマット変更した日付文字列
     */
    public static String dateTime(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String str = "";
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(date));
            sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
            str = sdf.format(c.getTime());
        } catch (ParseException e) {
            LogUtil.e(TAG, "パースー中に予期せぬエラーが発生しました", e);
        }
        return str;
    }
}