package lib.ubint.jp.commonlib.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Set;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Base64;

/**
 * AES暗号化処理を行うクラスです。
 */
public class AES {
    /**
     * タグ
     */
    private static final String TAG = AES.class.getSimpleName();

    /**
     * SALT
     */
    private static final byte[] SALT = new byte[]{-47, 66, 32, -127, -102,
            -51, 79, -69, 57, 85, -91, -42, 74, -116, -46, -113, -13, 34, -24,
            39};

    /**
     * IV
     */
    private static final byte[] IV = {16, 74, 71, -80, 32, 101, -47, 72, 117,
            -14, 0, -29, 70, 65, -12, 74};

    /**
     * 使える暗号化情報をログに出力します。
     */
    public void printAlgorithmsCipher() {
        String SERVICE_NAME = "Cipher";
        Set<String> names = Security.getAlgorithms(SERVICE_NAME);
        for (String name : names) {
            LogUtil.d("PRINT ALGORITHUMS: " + SERVICE_NAME + ": ", name);
        }
    }

    /**
     * 暗号化処理を行います。
     *
     * @param context  Context
     * @param contents 暗号化対象データ
     * @return 暗号化したデータ
     */
    public String execEncrypted(Context context, String contents) {
        String encryptedString = null;
        try {
            SecretKey secretKey = generateKey(context);
            byte[] encrypted = encrypt(contents.getBytes(), secretKey);
            encryptedString = Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception e) {
            LogUtil.d(TAG, "暗号化中に予期せぬエラーが発生しました", e);
        }
        return encryptedString;
    }

    /**
     * 暗号化を復号化します。
     *
     * @param context      Context
     * @param encryptedStr 暗号化データ
     * @return 復号化したデータ
     */
    public String execDecrypted(Context context, String encryptedStr) {
        String decryptedString = null;
        try {
            SecretKey secretKey = generateKey(context);
             /* BASE64でデコード */
            byte[] crypted = Base64.decode(encryptedStr, Base64.DEFAULT);
            byte[] decrypted = decrypt(crypted, secretKey);
            decryptedString = new String(decrypted);
        } catch (Exception e) {
            LogUtil.d(TAG, "復号化中に予期せぬエラーが発生しました", e);
        }
        return decryptedString;
    }

    /**
     * パスワードを生成します。
     *
     * @param context Context
     * @return 生成したパスワード
     * @throws NameNotFoundException
     */
    public char[] generatePassword(Context context)
            throws NameNotFoundException {
        PackageInfo packageInfo = context.getPackageManager().getPackageInfo(
                context.getPackageName(), PackageManager.GET_META_DATA);
        long installTime = packageInfo.firstInstallTime;
        String packageName = context.getPackageName();
        String password = String.valueOf(installTime) + packageName;
        return password.toCharArray();
    }

    /**
     * 秘密キーを生成します。
     *
     * @param context Context
     * @return 秘密キー
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NameNotFoundException
     */
    public SecretKey generateKey(Context context)
            throws NoSuchAlgorithmException, InvalidKeySpecException,
            NameNotFoundException {
        char[] password = generatePassword(context);
        KeySpec keySpec = new PBEKeySpec(password, SALT, 1024, 256);
        SecretKeyFactory factory = SecretKeyFactory
                .getInstance("PBEWITHSHAAND256BITAES-CBC-BC");
        SecretKey secretKey = factory.generateSecret(keySpec);
        return secretKey;
    }

    /**
     * 暗号化処理をします。
     *
     * @param src データ
     * @param key キー
     * @return 暗号化したデータ
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public byte[] encrypt(byte[] src, Key key) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException {
        // 暗号アルゴリズムにAESを指定
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        // 暗号化モードに設定し、Keyを指定
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV));
        // 暗号化の実行
        byte[] encrypted = cipher.doFinal(src);
        return encrypted;
    }

    /**
     * 復号化します。
     *
     * @param src データ
     * @param key キー
     * @return 復号化したデータ
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public byte[] decrypt(byte[] src, Key key) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException {
        // 暗号アルゴリズムにAESを指定
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        // 復号化モードに設定し、Keyを指定
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV));
        // 復号化の実行
        byte[] decrypted = cipher.doFinal(src);
        return decrypted;
    }
}