package com.example.otasukesplit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class DoNotAcceptZeroDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //ダイアログビルダーを生成
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //ダイアログのタイトルを設定
        builder.setTitle(R.string.do_not_accept_zero_dialog_title);

        //ダイアログのメッセージを設定
        builder.setMessage(R.string.do_not_accept_zero_dialog_msg);

        //Positive Buttonを設定
        builder.setNegativeButton(R.string.do_not_accept_zero_dialog_btn_close, new DialogButtonClickListener());

        //ダイアログオブジェクトを生成し、リターン
        AlertDialog dialog = builder.create();
        return dialog;
    }

    //アクションボタンリスナ
    private class DialogButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_NEGATIVE:
                    dismiss();
            }
        }
    }

}
