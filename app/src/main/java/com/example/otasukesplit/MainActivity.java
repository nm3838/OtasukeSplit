package com.example.otasukesplit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onCalculateButtonClick(View view) {
        //トースト用メッセージ文字列を初期化
        String toastMessage = "";

        //入力された参加人数を取得
        EditText etNumOfParticipant = findViewById(R.id.etNumOfParticipant);
        String strNumOfPartcipant = etNumOfParticipant.getText().toString();

        //入力された支払額を取得
        EditText etAmountOfPayment = findViewById(R.id.etAmountOfPayment);
        String strAmountOfPayment = etAmountOfPayment.getText().toString();

        //参加人数が未入力の場合に、
        if(strNumOfPartcipant.length() == 0){
            //トースト用メッセージに参加人数が入力されていない旨を追記
            toastMessage += getResources().getString(R.string.main_activity_num_of_participant_not_entered_toast_msg);
        }

        //支払が未入力の場合に、
        if(strAmountOfPayment.length() == 0) {
            //トーストメッセージが空でない、つまり、参加人数が未入力の場合は
            if(toastMessage.length() != 0) {
                //"と"を追加
                toastMessage += getResources().getString(R.string.main_activity_and_toast_msg);
            }
            //トースト用メッセージに支払が入力されていない旨を追記
            toastMessage += getResources().getString(R.string.main_activity_amount_of_payment_not_entered_toast_msg);
        }

        //トースト用メッセージが空でない、つまり、未入力の項目がある場合
        if(toastMessage.length() != 0) {
            //トーストメッセージに入力されていない旨を追加
            toastMessage += getResources().getString(R.string.main_activity_not_entered_toast_msg);

            //トーストの表示
            Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();

            //計算処理の終了
            return;
        }

        //参加人数、支払額を整数値に変換
        int numOfPartcipant = Integer.parseInt(strNumOfPartcipant);
        int amountOfPayment = Integer.parseInt(strAmountOfPayment);

        //参加人数に0が入力されている場合は、アラートダイアログを表示
        if(numOfPartcipant == 0) {
            //ダイアログを表示
            DoNotAcceptZeroDialogFragment dialogFragment = new DoNotAcceptZeroDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "DoNotAcceptDialogFragment");

            //参加人数の入力値を削除
            etNumOfParticipant.getEditableText().clear();

            //計算処理の終了
            return;
        }

        //一人あたりの支払額を表示
        TextView tvAmountOfPaymentPerPerson =  findViewById(R.id.tvAmountOfPaymentPerPerson);
        int amountOfPaymentPerPerson = 0;
        //参加人数が0の場合の例外処理
        try {
            amountOfPaymentPerPerson = (amountOfPayment + numOfPartcipant - 1) / numOfPartcipant;
        } catch(ArithmeticException ex) {
            Log.e("MainActivity", "0で割っています", ex);
        }
        tvAmountOfPaymentPerPerson.setText(String.valueOf(amountOfPaymentPerPerson));

        //不足金額を表示
        TextView tvSurplusAmount = findViewById(R.id.tvSurplusAmount);
        int surplusAmount = amountOfPaymentPerPerson * numOfPartcipant - amountOfPayment;
        tvSurplusAmount.setText(String.valueOf(surplusAmount));

    }
}