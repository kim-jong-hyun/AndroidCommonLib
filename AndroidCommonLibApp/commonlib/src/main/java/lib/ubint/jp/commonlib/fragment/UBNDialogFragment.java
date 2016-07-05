package lib.ubint.jp.commonlib.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.TextUtils;

/**
 *
 */
public final class UBNDialogFragment extends DialogFragment {
    /**
     * Dialogで何かイベント処理が起こった場合、呼ばれるコールバックリスナー
     */
    public interface OnEventListener {

        /**
         * positiveButton, NegativeButton, リスト選択など押下時、呼ばれる。
         *
         * @param requestCode   MyDialogFragment 実行時 requestCode
         * @param resultCode    DialogInterface.BUTTON_(POSI|NEGA)TIVE若しくはリストのposition
         * @param params        UBNDialogFragmentに受渡した引数
         */
        public void onDialogOK(int requestCode, int resultCode, Bundle params);

        /**
         * キャンセルされた時、呼ばれる。
         *
         * @param requestCode UBNDialogFragment実行時requestCode
         * @param params      UBNDialogFragmentに受渡した引数
         */
        public void onDialogCancel(int requestCode, Bundle params);
    }

    /**
     * UBNDialogFragmentをBuilderパターンで生成する為のクラスです。
     */
    public static class Builder {
        /**
         * Activity
         */
        private final Activity mActivity;

        /**
         * 親 Fragment
         */
        private final Fragment mParentFragment;

        /**
         * タイトル
         */
        private String mTitle;

        /**
         * メッセージ
         */
        private String mMessage;

        /**
         * 選択リスト
         */
        private String[] mItems;

        /**
         * 肯定ボタン
         */
        private String mPositiveLabel;

        /**
         * 否定ボタン
         */
        private String mNegativeLabel;

        /**
         * リクエストコード
         * 親Fragment側の戻りで受け取る。
         */
        private int mRequestCode = -1;

        /**
         * リスナに受け渡す任意のパラメータ
         */
        private Bundle mParams;

        /**
         * DialogFragmentのタグ
         */
        private String mTag = "default";

        /**
         * Dialogをキャンセル可のかどうか
         */
        private boolean mCancelable = true;

        /**
         * コンストラクタ</br>
         * Activity上から生成する場合
         *
         * @param activity
         */
        public <A extends Activity & OnEventListener> Builder(@NonNull final A activity) {
            mActivity = activity;
            mParentFragment = null;
        }

        /**
         * コンストラクタ</br>
         * Fragment上から生成する場合
         *
         * @param parentFragment 親 Fragment
         */
        public <F extends Fragment & OnEventListener> Builder(@NonNull final F parentFragment) {
            mParentFragment = parentFragment;
            mActivity = null;
        }

        /**
         * タイトルを設定する。
         *
         * @param title タイトル
         * @return Builder
         */
        public Builder title(@NonNull final String title) {
            mTitle = title;
            return this;
        }

        /**
         * タイトルを設定する。
         *
         * @param title タイトル
         * @return Builder
         */
        public Builder title(@StringRes final int title) {
            return title(getContext().getString(title));
        }

        /**
         * メッセージを設定する。
         *
         * @param message メッセージ
         * @return Builder
         */
        public Builder message(@NonNull final String message) {
            mMessage = message;
            return this;
        }

        /**
         * メッセージを設定する。
         *
         * @param message メッセージ
         * @return Builder
         */
        public Builder message(@StringRes final int message) {
            return message(getContext().getString(message));
        }

        /**
         * 選択リストを設定する。
         *
         * @param items 選択リスト
         * @return Builder
         */
        public Builder items(@NonNull final String... items) {
            mItems = items;
            return this;
        }

        /**
         * 肯定ボタンを設定する。
         *
         * @param positiveLabel 肯定ボタンのラベル
         * @return Builder
         */
        public Builder positive(@NonNull final String positiveLabel) {
            mPositiveLabel = positiveLabel;
            return this;
        }

        /**
         * 肯定ボタンを設定する.
         *
         * @param positiveLabel 肯定ボタンのラベル
         * @return Builder
         */
        public Builder positive(@StringRes final int positiveLabel) {
            return positive(getContext().getString(positiveLabel));
        }

        /**
         * 否定ボタンを設定する。
         *
         * @param negativeLabel 否定ボタンのラベル
         * @return Builder
         */
        public Builder negative(@NonNull final String negativeLabel) {
            mNegativeLabel = negativeLabel;
            return this;
        }

        /**
         * 否定ボタンを設定する。
         *
         * @param negativeLabel 否定ボタンのラベル
         * @return Builder
         */
        public Builder negative(@StringRes final int negativeLabel) {
            return negative(getContext().getString(negativeLabel));
        }

        /**
         * リクエストコードを設定する。
         *
         * @param requestCode リクエストコード
         * @return Builder
         */
        public Builder requestCode(final int requestCode) {
            mRequestCode = requestCode;
            return this;
        }

        /**
         * DialogFragmentのタグを設定する。
         *
         * @param tag タグ
         * @return Builder
         */
        public Builder tag(final String tag) {
            mTag = tag;
            return this;
        }

        /**
         * Positive / Negative 押下時のリスナに受け渡すパラメータを設定する。
         *
         * @param params リスナに受け渡すパラメータ
         * @return Builder
         */
        public Builder params(final Bundle params) {
            mParams = new Bundle(params);
            return this;
        }

        /**
         * Dialogをキャンセルできるか否かをセットする。
         *
         * @param cancelable キャンセル可か否か
         * @return Builder
         */
        public Builder cancelable(final boolean cancelable) {
            mCancelable = cancelable;
            return this;
        }

        /**
         * DialogFragmentをBuilderに設定した情報を元にshowする。
         */
        public void show() {
            final Bundle args = new Bundle();
            args.putString("title", mTitle);
            args.putString("message", mMessage);
            args.putStringArray("items", mItems);
            args.putString("positive_label", mPositiveLabel);
            args.putString("negative_label", mNegativeLabel);
            args.putBoolean("cancelable", mCancelable);
            if (mParams != null) {
                args.putBundle("params", mParams);
            }

            final UBNDialogFragment dialogFragment = new UBNDialogFragment();
            if (mParentFragment != null) {
                dialogFragment.setTargetFragment(mParentFragment, mRequestCode);
            } else {
                args.putInt("request_code", mRequestCode);
            }
            dialogFragment.setArguments(args);
            if (mParentFragment != null) {
                dialogFragment.show(mParentFragment.getFragmentManager(), mTag);
            } else {
                dialogFragment.show(mActivity.getFragmentManager(), mTag);
            }
        }

        /**
         * コンテキストを取得する。
         *
         * @return Context
         */
        private Context getContext() {
            return (mActivity == null ? mParentFragment.getActivity() : mActivity).getApplicationContext();
        }
    }

    /**
     * OnEventListener
     */
    private OnEventListener mEventListener;

    /**
     *
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Object callback = getTargetFragment();
        if (callback == null) {
            callback = getActivity();
            if (callback == null || !(callback instanceof OnEventListener)) {
                throw new IllegalStateException();
            }
        }
        mEventListener = (OnEventListener) callback;
    }

    /**
     *
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mEventListener = null;
    }

    /**
     *
     *
     * @param savedInstanceState
     * @return
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
                mEventListener.onDialogOK(getRequestCode(), which, getArguments().getBundle("params"));
            }
        };

        final String title = getArguments().getString("title");
        final String message = getArguments().getString("message");
        final String[] items = getArguments().getStringArray("items");
        final String positiveLabel = getArguments().getString("positive_label");
        final String negativeLabel = getArguments().getString("negative_label");
        setCancelable(getArguments().getBoolean("cancelable"));
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        if (!TextUtils.isEmpty(message)) {
            builder.setMessage(message);
        }
        if (items != null && items.length > 0) {
            builder.setItems(items, listener);
        }
        if (!TextUtils.isEmpty(positiveLabel)) {
            builder.setPositiveButton(positiveLabel, listener);
        }
        if (!TextUtils.isEmpty(negativeLabel)) {
            builder.setNegativeButton(negativeLabel, listener);
        }
        return builder.create();
    }

    /**
     *
     */
    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     *
     * @param dialog
     */
    @Override
    public void onCancel(DialogInterface dialog) {
        mEventListener.onDialogCancel(getRequestCode(), getArguments().getBundle("params"));
    }

    /**
     * リクエストコードを取得する。
     * ActivityとParentFragment双方に対応するため
     *
     * @return requestCode
     */
    private int getRequestCode() {
        return getArguments().containsKey("request_code") ? getArguments().getInt("request_code") : getTargetRequestCode();
    }
}
