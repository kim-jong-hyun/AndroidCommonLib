package lib.ubint.jp.commonlib.util;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 変換する有用な機能を提供するクラスです。
 */
public class ConvertUtil {
    /**
     * タグ
     */
    @SuppressWarnings("unused")
    private static final String TAG = ConvertUtil.class.getSimpleName();

    /**
     * dipをPixcelに変換します。
     *
     * @param context Context
     * @param dip
     * @return Pixcel
     */
    public static int dipToPixcel(Context context, float dip) {
        // density(比率)を取得
        float density = context.getResources().getDisplayMetrics().density;
        // dipをpixelに変換(dip × density + 0.5f(四捨五入))
        return (int) (dip * density + 0.5f);
    }

    /**
     * Pixcelをdipに変換します。
     *
     * @param context Context
     * @param pixel
     * @return dip
     */
    public static int pixelToDip(Context context, float pixel) {
        // density(比率)を取得
        float density = context.getResources().getDisplayMetrics().density;
        // pixelをdipに変換(pixel / density + 0.5f(四捨五入))
        return (int) (pixel / density + 0.5f);
    }

    /**
     * BitmapをBytes[]に変換します。
     *
     * @param bitmap
     * @return byte[]
     */
    public static byte[] bitmapToBytes(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Bytes[]をBitmapに変換します。
     *
     * @param bytes[]
     * @return Bitmap
     */
    public static Bitmap bytesToBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 日付文字列からCalendarに変換します。
     *
     * @param string  日付文字列
     * @param pattern
     * @return Calendar
     */
    public static Calendar stringToCalendar(String string, String pattern) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            calendar.setTime(sdf.parse(string));
        } catch (ParseException e) {
            calendar = null;
        }
        return calendar;
    }

    /**
     * Calendarから日付文字列に変換します。
     *
     * @param calendar
     * @param pattern
     * @return 日付文字列
     */
    public static String calendarToString(Calendar calendar, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(calendar.getTime());
    }

    /**
     * 日付時間からCalendarに変換し、取得します。
     *
     * @param year   年
     * @param month  月
     * @param day    日
     * @param hour   時間
     * @param minute 分
     * @param second 秒
     * @return Calendar
     */
    public static Calendar getCalendar(int year, int month, int day, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, hour, minute, second);
        return calendar;
    }

    /**
     * 画像を指定したサイズに変換します。
     *
     * @param image
     * @param viewHeight 高さ
     * @param viewWidth  幅
     * @return リサイズした画像
     */
    public static Bitmap getResizedImage(byte[] image, int viewHeight, int viewWidth) {
        // Optionsインスタンスを取得
        BitmapFactory.Options options = new BitmapFactory.Options();

        // Bitmapを生成せずにサイズを取得する
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(image, 0, image.length, options);

        if (viewHeight == 0 || viewWidth == 0) {
            // 等倍（リサイズしない）
            options.inSampleSize = 1;
        } else {
            // 設定するImageViewのサイズにあわせてリサイズ
            options.inSampleSize = Math.max(options.outHeight / viewHeight, options.outWidth / viewWidth);
        }

        // 実際にBitmapを生成する
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(image, 0, image.length, options);
    }
}