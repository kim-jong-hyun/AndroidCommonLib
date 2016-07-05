package lib.ubint.jp.commonlib.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Environment;

/**
 * ファイル関係の有用な機能を提供するクラスです。
 */
public class FileUtil {
    /**
     * タグ
     */
    private static final String TAG = FileUtil.class.getSimpleName();

    /**
     * DBファイルが存在するか確認します。存在する場合、trueを返します。
     *
     * @param context Context
     * @param dbName  DB名
     * @return DB存在フラグ
     */
    public static boolean isDB(Context context, String dbName) {
        String dbPath = context.getDatabasePath(dbName).getAbsolutePath();
        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            LogUtil.d(TAG, "DBが存在しません", e);
        }

        if (db != null) {
            db.close();
        }
        return ((db != null) ? true : false);
    }

    /**
     * AssetsにあるDBファイルをローカルにコピーします。コピー成功時、trueを返します。
     *
     * @param context Context
     * @param dbName  DB名
     * @return 成功フラグ
     */
    public static boolean copyAssetsDB(Context context, String dbName) {
        boolean isSucceed = false;
        String outPath = context.getDatabasePath(dbName).getAbsolutePath();
        try {
            InputStream is = context.getAssets().open(dbName);
            OutputStream os = new FileOutputStream(outPath);

            byte[] buffer = new byte[1024];
            int size;
            while ((size = is.read(buffer)) > 0) {
                os.write(buffer, 0, size);
            }
            os.flush();
            os.close();
            is.close();
            isSucceed = true;
        } catch (IOException e) {
            isSucceed = false;
            LogUtil.e(TAG, "DBファイルをコピー中に予期せぬエラーが発生しました: " + e.getMessage());
        }
        return isSucceed;
    }

    /**
     * AssetsにあるZipのDBファイルをローカルにコピーします。コピー成功時、trueを返します。
     *
     * @param context Context
     * @param zipName Zip名
     * @param dbName  DB名
     * @return 成功フラグ
     */
    public static boolean copyAssetsDBZip(Context context, String zipName, String dbName) {
        boolean isSucceed = false;
        String outPath = context.getDatabasePath(dbName).getAbsolutePath();
        ;
        try {
            AssetManager am = context.getResources().getAssets();
            InputStream is = am.open(zipName, AssetManager.ACCESS_STREAMING);
            ZipInputStream zis = new ZipInputStream(is);
            ZipEntry ze = zis.getNextEntry();

            if (ze != null) {
                FileOutputStream fos = new FileOutputStream(outPath, false);
                byte[] buf = new byte[1024];
                int size = 0;

                while ((size = zis.read(buf, 0, buf.length)) > -1) {
                    fos.write(buf, 0, size);
                }
                fos.close();
                zis.closeEntry();
            }
            zis.close();
            isSucceed = true;
        } catch (Exception e) {
            isSucceed = false;
            LogUtil.e(TAG, "DB圧縮ファイルをコピー中に予期せぬエラーが発生しました", e);
        }
        return isSucceed;
    }

    /**
     * AssetsにあるHTMLファイルを詠み込んで、返します。
     *
     * @param cxt Context
     * @param url Url
     * @return HTML文字列
     */
    public static String getHTMLFromAssets(Context cxt, String url) {
        InputStream htmlStream;
        try {
            String tempPath = url.replace("file:///android_asset/", "");
            htmlStream = cxt.getAssets().open(tempPath);
            BufferedReader br = new BufferedReader(new InputStreamReader(htmlStream, "UTF8"));
            final char[] buffer = new char[1024];
            StringBuilder out = new StringBuilder();
            int read;
            do {
                read = br.read(buffer, 0, buffer.length);
                if (read > 0) {
                    out.append(buffer, 0, read);
                }
            } while (read >= 0);
            return out.toString();
        } catch (UnsupportedEncodingException e) {
            LogUtil.e(TAG, "HTMLを読込中に予期せぬエラーが発生しました", e);
        } catch (FileNotFoundException e) {
            LogUtil.e(TAG, "HTMLを読込中に予期せぬエラーが発生しました", e);
        } catch (IOException e) {
            LogUtil.e(TAG, "HTMLを読込中に予期せぬエラーが発生しました", e);
        }
        return null;
    }

    /**
     * SDカードに書き込みます。
     *
     * @param context Context
     * @param path    パス
     * @param text    テキスト
     * @return エラーフラグ
     */
    public static boolean write(Context context, String path, String text) {
        boolean isError = false;
        try {
            String SDFile = Environment.getExternalStorageDirectory().getPath() + "/" + path;
            File file = new File(SDFile);
            file.getParentFile().mkdir();
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file, true));
            bos.write(text.getBytes());
            bos.close();
        } catch (Exception e) {
            isError = true;
            LogUtil.e(TAG, path + "ファイルを書き込み中に予期せぬエラーが発生しました", e);
        }
        return isError;
    }

    /**
     * SDカードから読み込みます。
     *
     * @param context Context
     * @param path    パス
     * @return テキスト
     */
    public static String read(Context context, String path) {
        String text = null;
        try {
            String SDFile = Environment.getExternalStorageDirectory().getPath() + "/" + path;
            File file = new File(SDFile);
            file.getParentFile().mkdir();
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[32];
            bis.read(buffer);
            text = new String(buffer);
            bis.close();
        } catch (Exception e) {
            text = null;
            LogUtil.e(TAG, path + "ファイルを読込み中に予期せぬエラーが発生しました", e);
        }
        return text;
    }
}