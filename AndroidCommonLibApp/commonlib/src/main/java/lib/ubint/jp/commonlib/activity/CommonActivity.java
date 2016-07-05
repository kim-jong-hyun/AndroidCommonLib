package lib.ubint.jp.commonlib.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.lang.annotation.Inherited;

import lib.ubint.jp.commonlib.R;
import lib.ubint.jp.commonlib.fragment.UBNDialogFragment;
import lib.ubint.jp.commonlib.util.NetworkUtil;


/**
 * 共通Activiyクラス
 */
public class CommonActivity extends AppCompatActivity implements UBNDialogFragment.OnEventListener {
    /**
     * タグ
     */
    private static final String TAG = CommonActivity.class.getSimpleName();

    private static final int REQUEST_CODE = 100;

    private static final int REQUEST_CODE_NOT_CONNECTED = -100;

    /**
     * ネットワーク接続確認フラグ
     */
    private boolean mIsNetworkConnected;

    private boolean mIsCloseActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkNetworkConnection();
    }

    /**
     * ネットワーク接続を確認します。
     */
    protected void checkNetworkConnection() {
        // ネットワークが接続してない場合、エラーメッセージを表示
        if (!NetworkUtil.isConnected(this)) {
            mIsNetworkConnected = false;
            showAlertDialog(REQUEST_CODE_NOT_CONNECTED, "ネットワーク状態を確認してください");
        // ネットワークが接続している場合
        } else {
            mIsNetworkConnected = true;
        }
    }

    /**
     * ネットワーク接続状態を取得します。checkNetworkConnectionを呼出してから確認ができます。
     *
     * @return ネットワーク接続フラグ
     */
    protected boolean isNetworkConnected() {
        return mIsNetworkConnected;
    }

    /**
     * UBNDialogFragment.Builderのオブジェクトを取得します。
     *
     * @return AlertDialog.Builder
     */
    protected UBNDialogFragment.Builder getAlertDialog() {
        return new UBNDialogFragment.Builder(this);
    }

    /**
     * AlertDialogを表示します。
     *
     * @param message メッセージ
     */
    protected void showAlertDialog(@NonNull final String message) {
        getAlertDialog()
                .requestCode(REQUEST_CODE)
                .message(message)
                .cancelable(false)
                .positive("確認")
                .show();
    }

    /**
     * AlertDialogを表示します。
     *
     * @param message メッセージ
     * @param closeActivity Activity終了フラグ
     */
    protected void showAlertDialog(@NonNull final String message, final boolean closeActivity) {
        getAlertDialog()
                .requestCode(REQUEST_CODE)
                .message(message)
                .cancelable(false)
                .positive("確認")
                .show();
        mIsCloseActivity = closeActivity;
    }

    /**
     * AlertDialogを表示します。
     *
     * @param requestCode
     * @param message メッセージ
     */
    protected void showAlertDialog(final int requestCode, @NonNull final String message) {
        getAlertDialog()
                .requestCode(requestCode)
                .message(message)
                .cancelable(false)
                .positive("確認")
                .show();
    }

    /**
     * AlertDialogを表示します。
     *
     * @param requestCode
     * @param message メッセージ
     * @param closeActivity Activity終了フラグ
     */
    protected void showAlertDialog(final int requestCode, @NonNull final String message, final boolean closeActivity) {
        getAlertDialog()
                .requestCode(requestCode)
                .message(message)
                .cancelable(false)
                .positive("確認")
                .show();
        mIsCloseActivity = closeActivity;
    }

    /**
     *
     * @param requestCode   MyDialogFragment 実行時 requestCode
     * @param resultCode    DialogInterface.BUTTON_(POSI|NEGA)TIVE若しくはリストのposition
     * @param params        UBNDialogFragmentに受渡した引数
     */
    @Override
    public void onDialogOK(int requestCode, int resultCode, Bundle params) {
        switch (requestCode) {
            case REQUEST_CODE: {
                if (mIsCloseActivity) {
                    finish();
                }
            }
                break;
            case REQUEST_CODE_NOT_CONNECTED: {
                try {
                        // システム『設定』画面を呼び出す
                        startActivity(new Intent("android.settings.SETTINGS"));
                    } catch (Exception e) {
                        showAlertDialog("システムの設定画面を開けませんでした。設定画面でネットワーク状態を確認してください", true);
                    }
            }
                break;
            default:
                break;
        }
    }

    /**
     *
     *
     * @param requestCode UBNDialogFragment実行時requestCode
     * @param params      UBNDialogFragmentに受渡した引数
     */
    @Override
    public void onDialogCancel(int requestCode, Bundle params) {

    }
}
