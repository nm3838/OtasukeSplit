package com.example.otasukesplit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class OutputDataFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //フラグメントで表示する画面をXMLファイルからインフレートする
        View view = inflater.inflate(R.layout.fragment_output_data, container, false);

        //InputDataFragmentからの引き継ぎデータを取得
        Bundle extras = getArguments();

        String strAmountOfPaymentPerPerson = "";
        try {
            strAmountOfPaymentPerPerson = extras.getString("amountOfPaymentPerPerson");
        }catch (NullPointerException ex) {
            Log.e("OutputDataFrame", "一人あたりの支払額の引継ぎデータがNullです。", ex);
        }

        String strSurplusAmount = "";
        try {
            strSurplusAmount = extras.getString("surplusAmount");
        }catch (NullPointerException ex) {
            Log.e("OutputDataFrame", "余剰額の引継ぎデータがNullです。", ex);
        }

        //Viewに引継ぎデータを設定
        TextView tvAmoutOfPaymentPerPerson = view.findViewById(R.id.tvAmountOfPaymentPerPerson);
        TextView tvSurplusAmount = view.findViewById(R.id.tvSurplusAmount);

        tvAmoutOfPaymentPerPerson.setText(strAmountOfPaymentPerPerson);
        tvSurplusAmount.setText(strSurplusAmount);

        return view;
    }
}