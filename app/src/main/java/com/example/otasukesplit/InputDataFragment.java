package com.example.otasukesplit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class InputDataFragment extends Fragment {
    //フィールド
    private EditText etNumOfParticipant;
    private EditText etAmountOfPayment;
    private Button btCalculateButton;
    private OutputDataFragment outputDataFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //フラグメントを表示する画面をXMLファイルからインフレートする
        View view = inflater.inflate(R.layout.fragment_input_data, container, false);

        //ViewのIDを取得し、グローバル変数に格納
        etNumOfParticipant = view.findViewById(R.id.etNumOfParticipant);
        etAmountOfPayment = view.findViewById(R.id.etAmountOfPayment);
        btCalculateButton = view.findViewById(R.id.btCalculate);

        //リスナ登録
        etNumOfParticipant.addTextChangedListener(textWatcher);
        etAmountOfPayment.addTextChangedListener(textWatcher);
        btCalculateButton.setOnClickListener(new ButtonClickListener());

        return view;
    }

    //入力フィールドのイベントハンドラ。
    //二つの入力フィールドに入力値がある時にボタンを活性にする」
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String inputNumOfParticipant =  etNumOfParticipant.getText().toString();
            String inputAmountOfPayment = etAmountOfPayment.getText().toString();
            btCalculateButton.setEnabled(!inputNumOfParticipant.isEmpty() && !inputAmountOfPayment.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    //ボタンのイベントハンドラ
    //「一人あたりの支払額」と「余剰額」を算出し、OutputDataFragmentに渡す
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //入力された参加人数を取得
            String strNumOfPartcipant = "";
            try {
                strNumOfPartcipant = etNumOfParticipant.getText().toString();
            }catch (NullPointerException ex) {
                Log.e("InputDataFragment", "参加人数の入力がNullです。", ex);
            }

            //入力された支払額を取得
            String strAmountOfPayment = "";
            try {
                strAmountOfPayment = etAmountOfPayment.getText().toString();
            }catch (NullPointerException ex) {
                Log.e("InputDataFragment", "支払額の入力がNullです。", ex);
            }

            //参加人数、支払額を整数値に変換
            int numOfPartcipant = Integer.parseInt(strNumOfPartcipant);
            int amountOfPayment = Integer.parseInt(strAmountOfPayment);

            //参加人数に0が入力されている場合は、アラートダイアログを表示
            if(numOfPartcipant == 0) {
                //ダイアログを表示
                DoNotAcceptZeroDialogFragment dialogFragment = new DoNotAcceptZeroDialogFragment();
                dialogFragment.show(getParentFragmentManager(), "DoNotAcceptDialogFragment");

                //参加人数の入力値を削除
                etNumOfParticipant.getEditableText().clear();

                //結果をクリア
                ClearResult();

                //計算処理の終了
                return;
            }

            //一人あたりの支払額を計算
            int amountOfPaymentPerPerson = 0;
            //参加人数が0の場合の例外処理
            try {
                amountOfPaymentPerPerson = (amountOfPayment + numOfPartcipant - 1) / numOfPartcipant;
            } catch(ArithmeticException ex) {
                Log.e("MainActivity", "0で割っています", ex);
            }

            //不足金額をを計算
            int surplusAmount = amountOfPaymentPerPerson * numOfPartcipant - amountOfPayment;

            //OutputDataFragmentへの引き継ぎデータを設定
            Bundle bundle = new Bundle();
            bundle.putString("amountOfPaymentPerPerson", String.valueOf(amountOfPaymentPerPerson));
            bundle.putString("surplusAmount", String.valueOf(surplusAmount));

            //OutputDataFragmentを表示
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            outputDataFragment = new OutputDataFragment();
            outputDataFragment.setArguments(bundle);
            transaction.replace(R.id.outputDataFrame, outputDataFragment)
                    .setReorderingAllowed(true)
                    .commit();

        }
    }

    public void ClearResult() {
        //OutputDataFragmentを削除し、結果をクリアする。
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(outputDataFragment)
                .setReorderingAllowed(true)
                .commit();

    }

}